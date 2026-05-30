package com.project.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("`ordinary_users`")
@Data
@EqualsAndHashCode(callSuper = false)
public class OrdinaryUsers implements Serializable {
    @TableId(value = "ordinary_users_id", type = IdType.AUTO)
    private Integer ordinary_users_id;  // OrdinaryUsers编号
    @TableField(value = "`user_name`")
    private String user_name;   // 用户姓名
    @TableField(value = "`user_gender`")
    private String user_gender; // 用户性别
    @TableField(value = "`contact_number`")
    private String contact_number;  // 联系电话
    @TableField(value = "user_id")
    private Integer userId; // 用户编号
    @TableField(value = "update_time")
    private Timestamp update_time;  // 更新时间
    @TableField(value = "create_time")
    private Timestamp create_time;  // 创建时间

}
