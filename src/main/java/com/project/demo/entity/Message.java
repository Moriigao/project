package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("message")
@Data
@EqualsAndHashCode(callSuper = false)
public class Message implements Serializable {
    @TableId(value = "message_id", type = IdType.AUTO)
    private Integer messageId;  //留言板ID
    @TableField(value = "user_id")
    private Integer userId; //用户ID
    @TableField(value = "title")
    private String title;   //标题
    @TableField(value = "content")
    private String content; //内容
    @TableField(value = "nickname")
    private String nickname;    //昵称
    @TableField(value = "avatar")
    private String avatar;  //头像
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
    @TableField(value = "update_time")
    private Timestamp updateTime;   //更新时间
    @TableField(value = "reply")
    private String reply;   //回复
    @TableField(value = "reply_state")
    private Integer replyState; //回复状态
}

