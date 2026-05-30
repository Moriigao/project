package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("comment")
@Data
@EqualsAndHashCode(callSuper = false)
public class Comment implements Serializable {
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer commentId;  //评论ID
    @TableField(value = "user_id")
    private Integer userId; //评论人ID
    @TableField(value = "reply_to_id")
    private Integer replyToId;  //回复评论ID：空为0
    @TableField(value = "content")
    private String content; //内容
    @TableField(value = "nickname")
    private String nickname;    //昵称
    @TableField(value = "avatar")
    private String avatar;  //头像地址：[0,255]
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
    @TableField(value = "source_table")
    private String sourceTable; //来源表
    @TableField(value = "source_field")
    private String sourceField; //来源字段
    @TableField(value = "source_id")
    private Integer sourceId;   //来源ID
}

