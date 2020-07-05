package com.echo.mybatis.template.mybatistemplate.service;

import com.echo.mybatis.template.mybatistemplate.pojo.TemplateTablePo;
import com.echo.mybatis.template.mybatistemplate.result.Result;

import java.util.Date;
import java.util.List;

/**
 * 接口类
 *
 * @author echo
 * @date 2019-09-05 03:09:49
 * @since jdk 1.8
 */
public interface TemplateTableService {

    /**
     * 获取所有的记录
     *
     * @return
     */
    Result<List<TemplateTablePo>> getAll();

    /**
     * 根据id查询某一条记录
     *
     * @param id
     * @return
     */
    Result<TemplateTablePo> getOne(String id);

    /**
     * 获取前一天的所有记录
     *
     * @param format
     * @return
     */
    Result<List<TemplateTablePo>> getYesterday(String format);

    /**
     * 获取前一天的所有记录
     *
     * @param yesterday
     * @return
     */
    Result<List<TemplateTablePo>> getYesterdayDate(Date yesterday);

    /**
     * 插入一条数据
     */
    void insert();

    /**
     * 批量插入数据
     */
    void batchInsert();

    /**
     * 根据主键有选择的更新数据库表
     */
    void updateByPrimaryKeySelective();

    /**
     * 批量更新
     */
    void batchUpdate();

    /**
     * 根据Map的多条件参数查询对应的数据
     */
    void getByConditionMap();

    /**
     * 出现需要分组条件，根据多条件参数查询对应的数据
     */
    void getByConditionListMap();

    /**
     * 根据ID删除一条数据
     */
    void deleteById();

    /**
     * 根据多个id，批量删除数据
     */
    void deleteBatch();

    /**
     * 根据分组条件，查询对应数据
     *
     * @return
     */
    List<TemplateTablePo> getByConditionListMap1();

    /**
     * 存在即更新，否者插入
     */
    void upsertSelective();

}
