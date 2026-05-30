package com.project.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("`music_library`")
@Data
@EqualsAndHashCode(callSuper = false)
public class MusicLibrary implements Serializable {

    @TableId(value = "music_library_id", type = IdType.AUTO)
    private Integer music_library_id;  // MusicLibrary编号
    @TableField(value = "`song_name`")
    private String song_name;   // 歌曲名称
    @TableField(value = "`singers_name`")
    private String singers_name;    // 歌手姓名
    @TableField(value = "`song_classification`")
    private String song_classification; // 歌曲分类
    @TableField(value = "`album_title`")
    private String album_title; // 专辑名称
    @TableField(value = "`song_file`")
    private String song_file;   // 歌曲文件
    @TableField(value = "`release_date`")
    private Timestamp release_date; // 发布日期
    @TableField(value = "`cover_photo`")
    private String cover_photo; // 封面图片
    @TableField(value = "`song_lyrics`")
    private String song_lyrics; // 歌曲歌词
    @TableField(value = "`song_introduction`")
    private String song_introduction;   // 歌曲介绍
    @TableField(value = "hits")
    private Integer hits;   // 点击数
    @TableField(value = "praise_len")
    private Integer praise_len; // 点赞数
    @TableField(value = "update_time")
    private Timestamp update_time;  // 更新时间
    @TableField(value = "create_time")
    private Timestamp create_time;  // 创建时间
}
