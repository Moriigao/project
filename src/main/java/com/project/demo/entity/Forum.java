package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("forum")
@Data
@EqualsAndHashCode(callSuper = false)
public class Forum implements Serializable {
    @TableId(value = "forum_id", type = IdType.AUTO)
    private Integer forumId;    //论坛id
    @TableField(value = "user_id")
    private Integer userId; //用户ID
    @TableField(value = "nickname")
    private String nickname;    //昵称：[0,16]
    @TableField(value = "praise_len")
    private Integer praise_len; //点赞数
    @TableField(value = "hits")
    private Integer hits;   //访问数
    @TableField(value = "title")
    private String title;   //标题
    @TableField(value = "tag")
    private String tag; //标签
    @TableField(value = "img")
    private String img; //封面图
    @TableField(value = "content")
    private String content; //正文
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
    @TableField(value = "update_time")
    private Timestamp updateTime;   //更新时间
    @TableField(value = "avatar")
    private String avatar;  //发帖人头像
    @TableField(value = "type")
    private String type;    //论坛分类：[0,1000]用来搜索指定类型的论坛帖
	@TableField(value = "istop")
	private Integer istop;  //是否置顶
}

