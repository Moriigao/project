package com.project.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

@TableName("`sensitive_vocabulary`")
@Data
@EqualsAndHashCode(callSuper = false)
public class SensitiveVocabulary implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;    //SensitiveVocabulary编号
    @TableField(value = "`sensitive_vocabulary`")
    private String sensitive_vocabulary;    //敏感词汇
    @TableField(value = "update_time")
    private Timestamp update_time;  //更新时间
    @TableField(value = "create_time")
    private Timestamp create_time;  //创建时间

}
