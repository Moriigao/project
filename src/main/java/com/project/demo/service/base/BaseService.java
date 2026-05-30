package com.project.demo.service.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.demo.dao.base.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Slf4j
public class BaseService<E>{
    @Autowired
    private BaseMapper<E> baseMapper;

    //使用反射获取当前 BaseService 实例化时所使用的泛型类型 E 的 Class 对象，如 ArticleService 的 Article Class
    Class<E> eClass = (Class<E>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    //用于将类名中的驼峰命名转换为下划线命名
    private final String table = humpToLine(eClass.getSimpleName());

    public static String humpToLine(String str) {
        if (str == null) {
            return null;
        }
        // 将驼峰字符串转换成数组
        char[] charArray = str.toCharArray();
        StringBuilder buffer = new StringBuilder();
        //处理字符串
        for (int i = 0, l = charArray.length; i < l; i++) {
            //如果遇到大写字母，则在该字母前加上下划线并将其转换为小写字母
            if (charArray[i] >= 65 && charArray[i] <= 90) {
                buffer.append("_").append(charArray[i] += 32);
            } else {
                buffer.append(charArray[i]);
            }
        }
        String s = buffer.toString();
        //如果结果字符串以 _ 开头，则去掉前面的下划线
        if (s.startsWith("_")){
            return s.substring(1);
        }else {
            return s;
        }
    }

    public List selectBaseList(String select) {
        List<Map<String,Object>> mapList = baseMapper.selectBaseList(select);
        List<E> list = new ArrayList<>();
        //遍历每一行数据库查询结果，将每一行的 Map<String, Object> 数据转换为 E 类型的对象，并将其添加到 list 中
        for (Map<String,Object> map:mapList) {
            list.add(JSON.parseObject(JSON.toJSONString(map),eClass));
        }
        return list;
    }

    public List<Map<String,Object>> selectMapBaseList(String select) {
        return baseMapper.selectBaseList(select);
    }

    public int selectBaseCount(String sql) {
        return baseMapper.selectBaseCount(sql);
    }

    public void insert(Map<String,Object> body){
        E entity = JSON.parseObject(JSON.toJSONString(body),eClass);
        baseMapper.insert(entity);
        log.info("[{}] - 插入操作：{}",entity);
    }

    @Transactional
    public void save(E e){
        String s = JSONObject.toJSONString(e);
        Map map = JSONObject.parseObject(s, Map.class);
        insert(map);
    }

    @Transactional
    public void update(Map<String,String> query,Map<String,String> config,Map<String,Object> body){
        QueryWrapper wrapper = new QueryWrapper<E>();
        toWhereWrapper(query,"0".equals(config.get("like")),wrapper);
        E entity = JSON.parseObject(JSON.toJSONString(body),eClass);
        baseMapper.update(entity,wrapper);
        log.info("[{}] - 更新操作：{}",entity);
    }

    @Transactional
    public void delete(Map<String,String> query,Map<String,String> config){
        QueryWrapper wrapper = new QueryWrapper<E>();
        toWhereWrapper(query, "0".equals(config.get("groupby")),wrapper);
        baseMapper.delete(wrapper);
        log.info("[{}] - 删除操作：{}",wrapper.getSqlSelect());
    }

    public void toWhereWrapper(Map<String,String> query, Boolean like, QueryWrapper wrapper) {
        if (query.size() > 0) {
            try {
                for (Map.Entry<String, String> entry : query.entrySet()) {
                    if (entry.getKey().contains("_min")) {
                        String min = humpToLine(entry.getKey()).replace("_min", "");
                        wrapper.ge(min,URLDecoder.decode(entry.getValue(), "UTF-8"));   //大于等于
                        continue;
                    }
                    if (entry.getKey().contains("_max")) {
                        String max = humpToLine(entry.getKey()).replace("_max", "");
                        wrapper.le(max,URLDecoder.decode(entry.getValue(), "UTF-8"));   //小于等于
                        continue;
                    }
                    if (like == true) {
                        if (entry.getValue()!=null)
                            wrapper.like(humpToLine(entry.getKey()),"%"+URLDecoder.decode(entry.getValue(), "UTF-8")+"%");
                    } else {
                        if (entry.getValue()!=null)
                            wrapper.eq(humpToLine(entry.getKey()),URLDecoder.decode(entry.getValue(), "UTF-8"));
                    }
                }
            } catch (UnsupportedEncodingException e) {
                log.info("拼接sql 失败：{}", e.getMessage());
            }
        }
    }

    /**
     * 分页查询
     * @param query 查询条件
     * @param config    分页的页码、每页的记录数
     * @return  查询结果列表和记录总数
     */
    public Map<String,Object> selectToPage(Map<String,String> query,Map<String,String> config){
        Map<String,Object> map = new HashMap<>();
        List list = baseMapper.selectBaseList(select(query, config));
        map.put("list",list);
        map.put("count",baseMapper.selectBaseCount(count(query,config)));
        return map;
    }

    //生成具有选择字段、查询条件、排序、分组、分页等功能的 SQL 语句
    public String select(Map<String,String> query,Map<String,String> config){
        StringBuffer sql = new StringBuffer("select ");
        //如果 field 参数为空或 null，默认选择所有字段（*）
        sql.append(config.get("field") == null || "".equals(config.get("field")) ? "*" : config.get("field")).append(" ");
        sql.append("from ").append("`").append(table).append("`").append(toWhereSql(query, "0".equals(config.get("like")),config.get("sqlwhere")));
        //如果 config 中包含 groupby 参数，并且该参数不为空
        if (config.get("groupby") != null && !"".equals(config.get("groupby"))){
            sql.append("group by ").append(config.get("groupby")).append(" ");
        }
        if (config.get("orderby") != null && !"".equals(config.get("orderby"))){
            sql.append("order by ").append(config.get("orderby")).append(" ");
        }
        if (config.get("page") != null && !"".equals(config.get("page"))){
            //计算分页的起始记录位置 (page - 1) * limit 和每页的记录数 limit
            int page = config.get("page") != null && !"".equals(config.get("page")) ? Integer.parseInt(config.get("page")) : 1;
            int limit = config.get("size") != null && !"".equals(config.get("size")) ? Integer.parseInt(config.get("size")) : 10;
            sql.append(" limit ").append( (page-1)*limit ).append(" , ").append(limit);
        }
        log.info("[{}] - 查询操作，sql: {}",table,sql);
        return sql.toString();
    }

    public String toWhereSql(Map<String,String> query, Boolean like,String sqlwhere) {
        if (query.size() > 0) {
            try {
                StringBuilder sql = new StringBuilder(" WHERE ");
                for (Map.Entry<String, String> entry : query.entrySet()) {
                    //如果条件的字段名包含 _min 后缀，表示这是一个范围查询的最小值条件（例如：start_date_min）
                    if (entry.getKey().contains("_min")) {
                        String min = humpToLine(entry.getKey()).replace("_min", "");
                        sql.append("`"+min+"`").append(" >= '").append(URLDecoder.decode(entry.getValue(), "UTF-8")).append("' and ");
                        continue;
                    }
                    if (entry.getKey().contains("_max")) {
                        String max = humpToLine(entry.getKey()).replace("_max", "");
                        sql.append("`"+max+"`").append(" <= '").append(URLDecoder.decode(entry.getValue(), "UTF-8")).append("' and ");
                        continue;
                    }
                    if (like == true) {
                        sql.append("`"+humpToLine(entry.getKey())+"`").append(" LIKE '%").append(URLDecoder.decode(entry.getValue(), "UTF-8")).append("%'").append(" and ");
                    } else {
                        sql.append("`"+humpToLine(entry.getKey())+"`").append(" = '").append(URLDecoder.decode(entry.getValue(), "UTF-8")).append("'").append(" and ");
                    }
                }
                if (sqlwhere!=null && !sqlwhere.trim().equals("")) {
                    sql.append(sqlwhere).append(" and ");
                }
                //由于每个条件后面都附加了 and，最后一个条件后多余的 and 会被删除。
                sql.delete(sql.length() - 4, sql.length());
                sql.append(" ");
                return sql.toString();
            } catch (UnsupportedEncodingException e) {
                log.info("拼接sql 失败：{}", e.getMessage());
            }
        }else {
            if (sqlwhere!=null && !sqlwhere.trim().equals("")) {
                StringBuilder sql = new StringBuilder(" WHERE ");
                sql.append(sqlwhere);
                return sql.toString();
            }
        }
        return "";
    }

    public String count(Map<String,String> query,Map<String,String> config){
        StringBuffer sql = new StringBuffer("SELECT ");
        if (config.get("groupby") != null && !"".equals(config.get("groupby"))){
            sql.append("COUNT(").append(config.get("groupby")).append(") FROM ").append("`").append(table).append("`");
            sql.append(toWhereSql(query, "0".equals(config.get("like")),config.get("sqlwhere")));
        }else {
            sql.append("COUNT(*) FROM ").append("`").append(table).append("`");
            sql.append(toWhereSql(query, "0".equals(config.get("like")),config.get("sqlwhere")));
        }
        log.info("[{}] - 统计操作，sql: {}",table,sql);
        return sql.toString();
    }

    public Integer selectSqlToInteger(String sql){
        Integer value = baseMapper.selectBaseCount(sql);
        return value;
    }

    public String groupCount(Map<String,String> query,Map<String,String> config){
        StringBuffer sql = new StringBuffer("SELECT ");
        log.info("拼接统计函数前");
        if (config.get("groupby") != null && !"".equals(config.get("groupby"))){
            sql.append("COUNT(").append(config.get("groupby")).append(") FROM ").append("`").append(table).append("`");
            sql.append(toWhereSql(query, "0".equals(config.get("like")),config.get("sqlwhere")));
            sql.append(" ").append("GROUP BY ").append(config.get("groupby"));
        }else {
            sql.append("COUNT(*) FROM ").append("`").append(table).append("`");
            sql.append(toWhereSql(query, "0".equals(config.get("like")),config.get("sqlwhere")));
        }
        log.info("[{}] - 统计操作，sql: {}",table,sql);
        return sql.toString();
    }

    public String sum(Map<String,String> query,Map<String,String> config){
        StringBuffer sql = new StringBuffer(" SELECT ");
        if (config.get("groupby") != null && !"".equals(config.get("groupby"))){
            sql.append("SUM(").append(config.get("field")).append(") FROM ").append("`").append(table).append("`");
            sql.append(toWhereSql(query, "0".equals(config.get("like")),config.get("sqlwhere")));
            sql.append(" ").append("GROUP BY ").append(config.get("groupby"));
        }else {
            sql.append(" SUM(").append(config.get("field")).append(") FROM ").append("`").append(table).append("`");
            sql.append(toWhereSql(query, "0".equals(config.get("like")),config.get("sqlwhere")));
        }
        log.info("[{}] - 查询操作，sql: {}",table,sql);
        return sql.toString();
    }

    //用于从一个 BufferedReader 对象中读取数据，并将其解析为一个 Map<String, Object> 对象
    public Map<String, Object> readBody(BufferedReader reader) {
        try (BufferedReader br = reader) {
            StringBuilder sb = new StringBuilder();
            String str;
            //通过 br.readLine() 持续读取每一行数据，直到没有更多数据
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            String json = sb.toString();
            return JSONObject.parseObject(json, Map.class);
        } catch (IOException e) {
            // 使用日志记录异常而不是打印堆栈
            log.error("Error reading the body", e);
        }
        return null;
    }

    //从 HttpServletRequest 获取查询字符串并将其转化为 Map<String, String>
    public Map<String, String> readQuery(HttpServletRequest request) {
        String queryString = request.getQueryString();
        if (queryString != null && !queryString.isEmpty()) {
            // 使用一个集合存储需要移除的查询参数
            Set<String> excludedParams = new HashSet<>(Arrays.asList(
               "page", "size", "like", "orderby", "field", "groupby", "_max", "_min", "sqlwhere"
            ));
            // 分割查询字符串，生成Map
            Map<String, String> map = new HashMap<>();
            String[] querys = queryString.split("&");
            for (String query : querys) {
                String[] q = query.split("=");
                if (q.length == 2) {  // 防止没有值的参数
                    String key = decodeValue(q[0]);
                    String value = decodeValue(q[1]);
                    // 只将不在排除列表中的参数加入Map
                    if (!excludedParams.contains(key)) {
                        map.put(key, value);
                    }
                }
            }
            return map;
        }
        return Collections.emptyMap();  // 返回空的不可修改的Map
    }

    public Map<String, String> readConfig(HttpServletRequest request) {
        String[] paramKeys = {
         "page", "size", "like", "orderby", "field", "groupby", "_max", "_min", "sqlwhere"
        };
        Map<String, String> map = new HashMap<>();
        for (String key : paramKeys) {
            String value = request.getParameter(key);
            if (value != null) {
                value = decodeValue(value);
            }
            map.put(key, value != null ? value : "");
        }
        return map;
    }

    private String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return value;  // 如果解码失败，返回原始值
        }
    }

    public String encryption(String plainText) {
        try {
            // 获取 MD5 MessageDigest 实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 更新 MessageDigest，指定字符编码为 UTF-8
            md.update(plainText.getBytes("UTF-8"));
            // 计算摘要
            byte[] b = md.digest();
            // 使用 StringBuilder 拼接结果字符串
            StringBuilder buf = new StringBuilder();
            for (byte value : b) {
                int i = value & 0xff; // 处理负值，确保结果为非负
                if (i < 16) {
                    buf.append("0"); // 保证两位十六进制数
                }
                buf.append(Integer.toHexString(i));
            }
            // 返回加密后的字符串
            return buf.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    //将 JSONObject 中的所有字段名从驼峰命名法转换为下划线命名法
    public JSONObject covertObject(JSONObject object) {
        if (object == null) {
            return null;
        }
        JSONObject newObject = new JSONObject();
        Set<String> set = object.keySet();
        for (String key : set) {
            Object value = object.get(key);
            if (value instanceof JSONArray) {   //数组
                value = covertArray(object.getJSONArray(key));
            } else if (value instanceof JSONObject) {   //对象
                value = covertObject(object.getJSONObject(key));
            }
            key = humpToLine(key);
            newObject.put(key, value);
        }
        return newObject;
    }

    public JSONArray covertArray(JSONArray array) {
        if (array == null) {
            return null;
        }
        JSONArray newArray = new JSONArray();
        for (int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = covertArray(array.getJSONArray(i));
            } else if (value instanceof JSONObject) {
                value = covertObject(array.getJSONObject(i));
            }
            newArray.add(value);
        }
        return newArray;
    }
}
