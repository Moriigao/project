package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("article_type")
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleType implements Serializable {
    @TableId(value = "type_id", type = IdType.AUTO)
    private Integer typeId; //分类ID
    @TableField(value = "name")
    private String name;    //分类名称
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
    @TableField(value = "update_time")
    private Timestamp updateTime;   //更新时间
}

