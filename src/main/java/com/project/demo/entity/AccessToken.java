package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;


@Data
@EqualsAndHashCode(callSuper = false)
public class AccessToken implements Serializable {
   // private static final long serialVersionUID = 913269304437207500L;
    @TableId(value = "token_id", type = IdType.AUTO)
    private Integer tokenId;    //临时访问牌ID
    @TableField(value = "token")
    private String token;   //临时访问牌
    @TableField(value = "maxage")
    private Integer maxage; //最大寿命：默认2小时
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
    @TableField(value = "user_id")
    private Integer user_id;    //用户信息
}
