package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("praise")
@Data
@EqualsAndHashCode(callSuper = false)
public class Praise implements Serializable {
    @TableId(value = "praise_id", type = IdType.AUTO)
    private Integer praiseId;   //点赞ID
    @TableField(value = "user_id")
    private Integer userId; //点赞人
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
    @TableField(value = "source_table")
    private String sourceTable; //来源表
    @TableField(value = "source_field")
    private String sourceField; //来源字段
    @TableField(value = "source_id")
    private Integer sourceId;   //来源ID
    @TableField(value = "status")
    private Integer status; //点赞状态:1为点赞，0已取消
}

