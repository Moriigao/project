package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("collect")
@Data
@EqualsAndHashCode(callSuper = false)
public class Collect implements Serializable {
    @TableId(value = "collect_id", type = IdType.AUTO)
    private Integer collectId;  //收藏ID
    @TableField(value = "user_id")
    private Integer userId; //收藏人ID
    @TableField(value = "source_table")
    private String sourceTable; //来源表
    @TableField(value = "source_field")
    private String sourceField; //来源字段
    @TableField(value = "source_id")
    private Integer sourceId;   //来源ID
    @TableField(value = "title")
    private String title;   //标题
    @TableField(value = "img")
    private String img; //封面
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
}

