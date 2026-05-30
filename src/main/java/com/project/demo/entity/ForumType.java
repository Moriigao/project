package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("forum_type")
@Data
@EqualsAndHashCode(callSuper = false)
public class ForumType implements Serializable {
    @TableId(value = "type_id", type = IdType.AUTO)
    private Integer typeId; //分类ID：[0,10000]
    @TableField(value = "name")
    private String name;    //分类名称：[2,16]
    @TableField(value = "description")
    private String description; //描述：[0,255]描述该分类的作用
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
    @TableField(value = "update_time")
    private Timestamp updateTime;   //更新时间
}

