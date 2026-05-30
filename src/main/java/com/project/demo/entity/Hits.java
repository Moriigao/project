package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.sql.Timestamp;

@TableName("hits")
@Data
@EqualsAndHashCode(callSuper = false)
public class Hits implements Serializable {
    @TableId(value = "hits_id", type = IdType.AUTO)
    private Integer hitsId; //访问ID
    @TableField(value = "user_id")
    private Integer userId; //访问人
    @TableField(value = "create_time")
    private Timestamp createTime;   //创建时间
    @TableField(value = "source_table")
    private String sourceTable; //来源表
    @TableField(value = "source_field")
    private String sourceField; //来源字段
    @TableField(value = "source_id")
    private Integer sourceId;   //来源ID
}

