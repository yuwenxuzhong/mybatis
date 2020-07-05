package com.echo.springmybatis.mapper;


import com.echo.springmybatis.model.Country;

/**
 * @author XLecho
 * Date 2019/9/26 0026
 * Time 14:06
 */
public interface CountryMapper {

    /**
     * 查询所有国家
     *
     * @return
     */
    Country selectAll();

}
