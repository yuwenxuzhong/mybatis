package com.echo.mybatis.template.mybatistemplate.controller;

import com.echo.mybatis.template.mybatistemplate.pojo.TemplateTablePo;
import com.echo.mybatis.template.mybatistemplate.result.Result;
import com.echo.mybatis.template.mybatistemplate.service.TemplateTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 控制层
 *
 * @author echo
 * @date 2019-09-05 03:09:49
 * @since jdk 1.8
 */
@RestController
@RequestMapping("/template/table")
public class TemplateTableController {

    @Autowired
    private TemplateTableService templateTableService;

    /**
     * 查询所有的记录
     *
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Result<List<TemplateTablePo>> getAll() {
        return templateTableService.getAll();
    }

    /**
     * 根据id查询某一条记录
     *
     * @return
     */
    @RequestMapping(value = "/getOne", method = RequestMethod.GET)
    public Result<TemplateTablePo> getOne() {
        return templateTableService.getOne("8EC8777899D87FA7E053A114A8C08F3C");
    }

    /**
     * 获取前一天的所有记录
     *
     * @return
     */
    @RequestMapping(value = "/getYesterday", method = RequestMethod.GET)
    public Result<List<TemplateTablePo>> getYesterday() {
        Date yesterday = new Date(System.currentTimeMillis() - 86400000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String format = sdf.format(yesterday);
        return templateTableService.getYesterday(format);
    }

    /**
     * 获取前一天的所有记录
     *
     * @return
     */
    @RequestMapping(value = "/getYesterdayDate", method = RequestMethod.GET)
    public Result<List<TemplateTablePo>> getYesterdayDate() {
        Date yesterday = new Date(System.currentTimeMillis() - 86400000L);
        return templateTableService.getYesterdayDate(yesterday);
    }

    /**
     * 插入一条数据
     */
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public void insert() {
        templateTableService.insert();
    }

    /**
     * 批量插入数据
     */
    @RequestMapping(value = "/batchInsert", method = RequestMethod.GET)
    public void batchInsert() {
        templateTableService.batchInsert();
    }

    /**
     * 根据主键有选择的更新数据库表
     */
    @RequestMapping(value = "/updateByPrimaryKeySelective", method = RequestMethod.GET)
    public void updateByPrimaryKeySelective() {
        templateTableService.updateByPrimaryKeySelective();
    }

    /**
     * 批量更新数据
     */
    @RequestMapping(value = "/batchUpdate", method = RequestMethod.GET)
    public void batchUpdate() {
        templateTableService.batchUpdate();
    }

    /**
     * 根据Map的多条件参数查询对应的数据
     */
    @RequestMapping(value = "/getByConditionMap", method = RequestMethod.GET)
    public void getByConditionMap() {
        templateTableService.getByConditionMap();
    }

    /**
     * 出现需要分组条件，根据多条件参数查询对应的数据
     */
    @RequestMapping(value = "/getByConditionListMap", method = RequestMethod.GET)
    public void getByConditionListMap() {
        templateTableService.getByConditionListMap();
    }

    /**
     * 根据ID删除一条数据
     */
    @RequestMapping(value = "/deleteById", method = RequestMethod.GET)
    public void deleteById() {
        templateTableService.deleteById();
    }

    /**
     * 根据多个id，批量删除数据
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.GET)
    public void deleteBatch() {
        templateTableService.deleteBatch();
    }

    /**
     * 根据分组条件，查询对应数据
     */
    @RequestMapping(value = "/getByConditionListMap1", method = RequestMethod.GET)
    public void getByConditionListMap1() {
        templateTableService.getByConditionListMap1();
    }

    /**
     * 存在即更新，否者插入
     * 注意：该方法是用于Oracle的
     */
    @RequestMapping(value = "/upsertSelective", method = RequestMethod.GET)
    public void upsertSelective() {
        templateTableService.upsertSelective();
    }
}
