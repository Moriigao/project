package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("user_group")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserGroup implements Serializable {
    @TableId(value = "group_id", type = IdType.AUTO)
    private Integer groupId;    //用户组ID：[0,8388607]
    @TableField(value = "name")
    private String name;    //名称：[0,16]
    @TableField(value = "source_table")
    private String sourceTable; //来源表
    @TableField(value = "source_field")
    private String sourceField; //来源字段
    @TableField(value = "register")
    private String register;    //注册位置
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
}

