package com.project.demo.controller;

import com.project.demo.controller.base.BaseController;
import com.project.demo.entity.MusicLibrary;
import com.project.demo.service.MusicLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/music_library")
public class MusicLibraryController extends BaseController<MusicLibrary, MusicLibraryService> {
    @Autowired
    public MusicLibraryController(MusicLibraryService service) {
        setService(service);
    }
    /**
     * 混合推荐算法
     */
    @RequestMapping("/get_hits_list")
    public Map<String, Object> getHits(HttpServletRequest request) {
        Map<String,String> paramMap = service.readQuery(request);
        //用户不存在
        if (paramMap.get("user_id")==null||paramMap.get("user_id").equals("")){
            return this.getList(request);
        }

        //查询该用户常点击
        String hitsSource = "SELECT COUNT( hits_id ) AS hits_count, source_id FROM hits WHERE source_table = 'music_library' AND user_id = "+paramMap.get("user_id")+" GROUP BY source_id";
        List<Map<String,Object>> hitsSourceList = service.selectMapBaseList(hitsSource);

        //如果查询到的结果为空，则查询所有用户的点击数据
        if (hitsSourceList.isEmpty()){
            hitsSource = "SELECT COUNT( hits_id ) AS hits_count, source_id FROM hits WHERE source_table = 'music_library' GROUP BY source_id";
            hitsSourceList = service.selectMapBaseList(hitsSource);
        }

        String table_name = "music_library";
        String table_id = "music_library_id";
        String type_name = "song_classification";

        //如果所有用户的点击数据依旧为空则取添加时间前12条数据
        if (hitsSourceList.isEmpty()){
            Map<String, Object> map=new HashMap<>();
            map = addTop12(paramMap,table_name);
            return success(map);
        }

        //对用户点击量进行排序取得最大值
        int max = 0;
        int maxSourceId = 0;
        for (Map<String,Object> map:hitsSourceList) {
            int hitsCount = Integer.parseInt(String.valueOf(map.get("hits_count")));
            if (hitsCount > max){
                max = hitsCount;
                maxSourceId = Integer.parseInt(String.valueOf(map.get("source_id")));
            }
        }

        //查询该用户点击最大值的种类
        String typeSql = "SELECT " + type_name + " FROM "  + table_name + " WHERE " + table_id + " = " + maxSourceId;
        List<Map<String,Object>> typeList = service.selectMapBaseList(typeSql);

        //如果为空则取添加时间前12条数据
        if (typeList.isEmpty()){
            Map<String, Object> map=new HashMap<>();
            map = addTop12(paramMap,table_name);
            return success(map);
        }

        String typeName = String.valueOf(typeList.get(0).get(type_name));
        // 获取所有与当前用户点击数据相似的用户（包括当前用户和其他用户）
        String similarUsersSql = "SELECT DISTINCT user_id FROM hits WHERE source_table = 'music_library' AND source_id = " + maxSourceId;
        List<Map<String, Object>> similarUsers = service.selectMapBaseList(similarUsersSql);
        List<Integer> hitIds = new ArrayList<>();
        if(similarUsers.size()!=1) {
            // 查询其他相似用户点击的数据
            List<Map<String, Object>> recommendedSongs = new ArrayList<>();
            for (Map<String, Object> user : similarUsers) {
                int userId = Integer.parseInt(String.valueOf(user.get("user_id")));
                if (userId != Integer.parseInt(paramMap.get("user_id"))) {
                    // 获取当前用户与目标用户的点击数据
                    List<Map<String, Object>> currentUserHits = getUserHits(Integer.parseInt(paramMap.get("user_id")));
                    List<Map<String, Object>> otherUserHits = getUserHits(userId);
                    // 计算当前用户与其他用户的相似度
                    double similarity = CosineSimilarity(currentUserHits, otherUserHits);
                    // 根据相似度筛选推荐歌曲
                    if (similarity > 0.3) {  // 设置一个阈值，只有相似度高于此值的用户才会影响推荐
                        String userHitsSql = "SELECT source_id FROM hits WHERE user_id = " + userId + " AND source_table = 'music_library' GROUP BY source_id ORDER BY Count(source_id) DESC";
                        List<Map<String, Object>> userHits = service.selectMapBaseList(userHitsSql);
                        for (Map<String, Object> hit : userHits) {
                            int sourceId = Integer.parseInt(String.valueOf(hit.get("source_id")));
                            if(!hitIds.contains(sourceId)&&recommendedSongs.size()<12) {
                                hitIds.add(sourceId);
                                String songSql = "SELECT * FROM " + table_name + " WHERE " + table_id + " = " + sourceId;
                                List<Map<String, Object>> songs = service.selectMapBaseList(songSql);
                                recommendedSongs.addAll(songs);
                            }
                            if (recommendedSongs.size() >= 12) break;
                        }
                    }
                }
            }
            if(recommendedSongs.size()<12){
                String sql = "SELECT * FROM "  + table_name + " WHERE " + type_name + " = '"+typeName+"'"+"GROUP BY hits ORDER BY hits DESC";
                List<Map<String, Object>> typeSongList = service.selectMapBaseList(sql);
                if (!typeSongList.isEmpty()) {
                    for (Map<String, Object> hit : typeSongList) {
                        int sourceId = Integer.parseInt(String.valueOf(hit.get("music_library_id")));
                        if (!hitIds.contains(sourceId)) {
                            hitIds.add(sourceId);
                            String songSql = "SELECT * FROM " + table_name + " WHERE music_library_id = " + sourceId;
                            List<Map<String, Object>> songs = service.selectMapBaseList(songSql);
                            recommendedSongs.addAll(songs);
                        }
                        if (recommendedSongs.size() >= 12) break;
                    }
                }
                if(recommendedSongs.size()<12){
                    Map<String, Object> map= queryAndSortDataByType(recommendedSongs,table_name,type_name,typeName,paramMap,hitIds);
                    return success(map);
                }
            }
            Map<String, Object> map=new HashMap<>();
            map.put("list",recommendedSongs);
            map.put("count",recommendedSongs.size());
            return success(map);
        }
        //查询该种类的数据
        String sql = "SELECT * FROM "  + table_name + " WHERE " + type_name + " = '"+typeName+"'" ;
        List<Map<String, Object>> resultList = service.selectMapBaseList(sql);
        Map<String, Object> map= queryAndSortDataByType(resultList,table_name,type_name,typeName,paramMap,hitIds);

        return success(map);
    }

    private Map<String, Object> queryAndSortDataByType(List<Map<String, Object>> resultList,String table_name,String type_name,String typeName,Map<String,String> paramMap,List<Integer> hitIds){
        //如果为空则取添加时间前12条数据
        if (resultList==null || resultList.size()<=0){
            Map<String, Object> map=new HashMap<>();
            map = addTop12(paramMap,table_name);
            return map;
        }
        if(resultList.size()<12){
            String typeCosSql = "SELECT " + type_name + " FROM "  + table_name + " WHERE " + type_name + " <> '" + typeName + "' GROUP BY " + type_name;
            List<Map<String, Object>> typeCosList = service.selectMapBaseList(typeCosSql);
            if (!typeCosList.isEmpty()) {
                for (Map<String, Object> typeCos : typeCosList) {
                    typeCos.put("cosSim", calculateCosineSimilarity(typeName, String.valueOf(typeCos.get(type_name))));
                }
                //冒泡排序
                for (int i = typeCosList.size() - 1; i > 0; i--) {
                    // 是否发生交换
                    boolean swapped = false;
                    for (int j = 0; j < i; j++) {
                        Map<String, Object> leftMap = typeCosList.get(j);
                        Map<String, Object> rightMap = typeCosList.get(j + 1);
                        if (Double.parseDouble(String.valueOf(leftMap.get("cosSim"))) < Double.parseDouble(String.valueOf(rightMap.get("cosSim")))) {
                            Map<String, Object> temp = rightMap;
                            rightMap = leftMap;
                            leftMap = temp;
                            swapped = true;
                        }
                    }
                    if (!swapped) {
                        // 没有发生交换，则说明数组已有序,停止冒泡
                        break;
                    }
                }
                //逐条加入数据
                for (Map<String, Object> typeCos : typeCosList) {
                    String sql = "SELECT * FROM "  + table_name + " WHERE " + type_name + " = '" + String.valueOf(typeCos.get(type_name)) + "' ORDER BY hits DESC";
                    List<Map<String, Object>> list = service.selectMapBaseList(sql);
                    for (Map<String, Object> hit : list) {
                        int sourceId = Integer.parseInt(String.valueOf(hit.get("music_library_id")));
                        if(!hitIds.contains(sourceId)&&resultList.size()<12) {
                            hitIds.add(sourceId);
                            String songSql = "SELECT * FROM " + table_name + " WHERE music_library_id = " + sourceId;
                            List<Map<String, Object>> songs = service.selectMapBaseList(songSql);
                            resultList.addAll(songs);
                        }
                        if (resultList.size() >= 12) break;
                    }
                }
            }
        }
        Map<String, Object> map=new HashMap<>();
        map.put("list",resultList);
        map.put("count",resultList.size());
        return map;
    }
    private Map<String, Object> addTop12(Map<String,String> paramMap,String table_name){
        String sql = "SELECT * FROM " + table_name + " ORDER BY create_time DESC LIMIT 0,12";
        List list = service.selectBaseList(sql);
        Map<String, Object> map=new HashMap<>();
        map.put("list",list);
        map.put("count",list.size());
        return map;
    }
    // 获取用户点击数据
    private List<Map<String, Object>> getUserHits(int userId) {
        String hitsSql = "SELECT DISTINCT source_id FROM hits WHERE user_id = " + userId + " AND source_table = 'music_library' GROUP BY source_id";
        return service.selectMapBaseList(hitsSql);
    }

    // 计算余弦相似度
    private double CosineSimilarity(List<Map<String, Object>> user1Hits, List<Map<String, Object>> user2Hits) {
        Set<Integer> user1Set = new HashSet<>();
        Set<Integer> user2Set = new HashSet<>();
        for (Map<String, Object> hit : user1Hits) {
            user1Set.add(Integer.parseInt(String.valueOf(hit.get("source_id"))));
        }
        for (Map<String, Object> hit : user2Hits) {
            user2Set.add(Integer.parseInt(String.valueOf(hit.get("source_id"))));
        }

        // 计算交集
        Set<Integer> intersection = new HashSet<>(user1Set);
        intersection.retainAll(user2Set);

        // 余弦相似度公式：cosine_similarity = |A ∩ B| / (|A| * |B|)
        return (double) intersection.size() / Math.sqrt(user1Set.size() * user2Set.size());
    }

    private double calculateCosineSimilarity(String text1, String text2) {
        // 将文本分词并转换为向量表示
        Map<Character, Integer> wordCount1 = getWordCount(text1);
        Map<Character, Integer> wordCount2 = getWordCount(text2);
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        //  计算向量的点积和模
        for (Character word : wordCount1.keySet()) {
            int count1 = wordCount1.get(word);
            int count2 = wordCount2.getOrDefault(word, 0);
            dotProduct += count1 * count2;
            magnitude1 += Math.pow(count1, 2);
        }
        for (Integer count : wordCount2.values()) {
            magnitude2 += Math.pow(count, 2);
        }
        magnitude1 = Math.sqrt(magnitude1);
        magnitude2 = Math.sqrt(magnitude2);
        if (magnitude1 == 0.0 || magnitude2 == 0.0) {
            return 0.0;
        }
        return dotProduct / (magnitude1 * magnitude2);
    }

    /**
     *  统计给定字符串 text 中每个字符的出现次数
     */
    private static Map<Character, Integer> getWordCount(String text) {
        Map<Character, Integer> wordCount = new HashMap<>();
        for (char c : text.toCharArray()) {
            wordCount.put(c, wordCount.getOrDefault(c, 0) + 1);
        }
        return wordCount;
    }
}
