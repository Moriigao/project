package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("auth")
@Data
@EqualsAndHashCode(callSuper = false)
public class Auth implements Serializable {
    @TableId(value = "auth_id", type = IdType.AUTO)
    private Integer authId; //授权ID
    @TableField(value = "user_group")
    private String userGroup;   //用户组
    @TableField(value = "mod_name")
    private String modName; //模块名
    @TableField(value = "table_name")
    private String tableName;   //表名
    @TableField(value = "page_title")
    private String pageTitle;   //页面标题
    @TableField(value = "path")
    private String path;    //路由路径
    @TableField(value = "position")
	private String position;    //位置
    @TableField(value = "`add`")
    private Integer add;    //是否可增加
    @TableField(value = "del")
    private Integer del;    //是否可删除
    @TableField(value = "`set`")
    private Integer set;    //是否可修改
    @TableField(value = "`get`")
    private Integer get;    //是否可查看
    @TableField(value = "field_add")
    private String fieldAdd;    //添加字段
    @TableField(value = "field_set")
    private String fieldSet;    //修改字段
    @TableField(value = "field_get")
    private String fieldGet;    //查询字段
    @TableField(value = "`option`")
    private String option;  //配置
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
}

