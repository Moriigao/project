package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("article")
@Data
@EqualsAndHashCode(callSuper = false)
public class Article implements Serializable {
    @TableId(value = "article_id", type = IdType.AUTO)
    private Integer articleId;
    @TableId(value = "title")
    private String title;
    @TableField(value = "type")
    private String type;    //资讯分类
    @TableField(value = "hits")
    private Integer hits;   //点击数
    @TableField(value = "praise_len")
    private Integer praise_len; //点赞数
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
    @TableField(value = "update_time")
    private Timestamp updateTime;   //更新时间
    @TableField(value = "tag")
    private String tag; //标签：[0,255]用于标注资讯所属相关内容，多个标签用空格隔开
    @TableField(value = "content")
    private String content; //正文
    @TableField(value = "img")
    private String img; //封面图
    @TableField(value = "description")
    private String description; //资讯描述
}

