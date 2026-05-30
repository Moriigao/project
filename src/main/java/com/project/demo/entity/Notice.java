package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("notice")
@Data
@EqualsAndHashCode(callSuper = false)
public class Notice implements Serializable {
    @TableId(value = "notice_id", type = IdType.AUTO)
    private Integer noticeId;   //公告id
    @TableField(value = "title")
    private String title;   //标题
    @TableField(value = "content")
    private String content; //正文
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
    @TableField(value = "update_time")
    private Timestamp updateTime;   //更新时间
}

