package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("code_token")
@Data
@EqualsAndHashCode(callSuper = false)
public class CodeToken implements Serializable {
    @TableId(value = "code_token_id", type = IdType.AUTO)
    private Integer code_token_id;  //邮箱验证码ID
    @TableField(value = "token")
    private String token;   //临时访问牌
    @TableField(value = "code")
    private String code;    //验证码
    @TableField(value = "expire_time")
    private Timestamp expire_time;  //失效时间
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
}
