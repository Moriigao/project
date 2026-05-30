package com.project.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("`song_classification`")
@Data
@EqualsAndHashCode(callSuper = false)
public class SongClassification implements Serializable {
    @TableId(value = "song_classification_id", type = IdType.AUTO)
    private Integer song_classification_id; // SongClassification编号
    @TableField(value = "`song_classification`")
    private String song_classification; // 歌曲分类
    @TableField(value = "update_time")
    private Timestamp update_time;  // 更新时间
    @TableField(value = "create_time")
    private Timestamp create_time;  // 创建时间
}
