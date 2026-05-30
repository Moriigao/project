package com.project.demo.dao.base;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BaseMapper<E> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<E> {
    List<Map<String,Object>> selectBaseList(@Param("select") String select);    //将动态 SQL 查询作为 select 参数传递给数据库
    Integer selectBaseCount(@Param("count") String count);
}
