package com.echo.mybatis.template.mybatistemplate.service.impl;

import com.echo.mybatis.template.mybatistemplate.dao.TemplateTableDao;
import com.echo.mybatis.template.mybatistemplate.pojo.TemplateTablePo;
import com.echo.mybatis.template.mybatistemplate.result.Result;
import com.echo.mybatis.template.mybatistemplate.service.TemplateTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.IntStream;


/**
 * service层
 *
 * @author echo
 * @date 2019-09-05 03:09:49
 * @since jdk 1.8
 */
@Service("templateTableService")
public class TemplateTableServiceImpl implements TemplateTableService {

    @Autowired
    private TemplateTableDao templateTableDao;

    @Override
    public Result<List<TemplateTablePo>> getAll() {
        Result<List<TemplateTablePo>> result = new Result<>();
        List<TemplateTablePo> list = templateTableDao.getAll();
        result.setStatus(200);
        result.setMessage("success");
        result.setData(list);
        return result;
    }

    @Override
    public Result<TemplateTablePo> getOne(String id) {
        Result<TemplateTablePo> result = new Result<>();
        TemplateTablePo templateTablePo = templateTableDao.getOne(id);
        result.setStatus(200);
        result.setMessage("success");
        result.setData(templateTablePo);
        return result;
    }

    @Override
    public Result<List<TemplateTablePo>> getYesterday(String format) {
        Result<List<TemplateTablePo>> result = new Result<>();
        List<TemplateTablePo> list = templateTableDao.getYesterday(format);
        result.setStatus(200);
        result.setMessage("success");
        result.setData(list);
        return result;
    }

    @Override
    public Result<List<TemplateTablePo>> getYesterdayDate(Date yesterday) {
        Result<List<TemplateTablePo>> result = new Result<>();
        List<TemplateTablePo> list = templateTableDao.getYesterdayDate(yesterday);
        result.setStatus(200);
        result.setMessage("success");
        result.setData(list);
        return result;
    }

    @Override
    public void insert() {
        TemplateTablePo templateTablePo = new TemplateTablePo();
        templateTablePo.setId(UUID.randomUUID().toString());
        templateTablePo.setCode(UUID.randomUUID().toString());
        templateTablePo.setDepartmentName("echo工作室研发部");
        templateTablePo.setDepartmentCode("W168");
        templateTablePo.setParentDepartmentName("echo工作室IT部");
        templateTablePo.setParentDepartmentCode("W888");
        templateTablePo.setCompanyName("echo工作室");
        templateTablePo.setDepartmentAccount("13612345678");
        templateTablePo.setBalance(new BigDecimal(88888));
        templateTablePo.setUnuseAmount(new BigDecimal(88888));
        templateTablePo.setAlreadyAmount(new BigDecimal(0));
        templateTablePo.setAccountType("对公账户");
        templateTablePo.setDatasource("0");
        templateTablePo.setStatus("1");
        templateTablePo.setRemark("success");
        templateTablePo.setCreateUser("echo");
        templateTablePo.setCreateTime(new Date());
        templateTableDao.insert(templateTablePo);
    }

    @Override
    public void batchInsert() {
        List<TemplateTablePo> list = new ArrayList<>();
        IntStream.range(0, 100).forEach(it -> {
            TemplateTablePo templateTablePo = new TemplateTablePo();
            templateTablePo.setId(UUID.randomUUID().toString());
            templateTablePo.setCode(UUID.randomUUID().toString());
            templateTablePo.setDepartmentName("echo工作室研发部");
            templateTablePo.setDepartmentCode("W168");
            templateTablePo.setParentDepartmentName("echo工作室IT部");
            templateTablePo.setParentDepartmentCode("W888");
            templateTablePo.setCompanyName("echo工作室");
            templateTablePo.setDepartmentAccount("13612345678");
            templateTablePo.setBalance(new BigDecimal(88888));
            templateTablePo.setUnuseAmount(new BigDecimal(88888));
            templateTablePo.setAlreadyAmount(new BigDecimal(0));
            templateTablePo.setAccountType("对公账户");
            templateTablePo.setDatasource("0");
            templateTablePo.setStatus("1");
            templateTablePo.setRemark("success");
            templateTablePo.setCreateUser("echo");
            templateTablePo.setCreateTime(new Date());
            list.add(templateTablePo);
        });
        templateTableDao.batchInsert(list);
    }

    @Override
    public void updateByPrimaryKeySelective() {
        List<TemplateTablePo> list = templateTableDao.getAll();
        list.forEach(it -> {
            it.setUpdateUser("echo");
            it.setUpdateTime(new Date());
        });
        templateTableDao.updateByPrimaryKeySelective(list.get(0));
    }


    @Override
    public void batchUpdate() {
        List<TemplateTablePo> list = templateTableDao.getAll();
        List<TemplateTablePo> templateTablePos = list.subList(0, 100);
        templateTablePos.forEach(it -> {
            it.setUpdateTime(new Date());
            it.setUpdateUser("echo");
        });
        templateTableDao.batchUpdate(templateTablePos);
    }

    @Override
    public void getByConditionMap() {
        Map<String, String> map = new HashMap<>(16);
        map.put("updateUser", "echo");
        map.put("datasource", "0");
        List<TemplateTablePo> list = templateTableDao.getByConditionMap(map);
        System.out.println(list);
    }

    @Override
    public synchronized void getByConditionListMap() {
        // 查询条件
        List<Map<String, String>> list = new ArrayList<>();
        method(list);
        List<TemplateTablePo> templateTablePos = new ArrayList<>();
        list.forEach(it -> templateTablePos.addAll(templateTableDao.getByConditionListMap(it)));
        templateTablePos.forEach(System.out::println);
    }

    @Override
    public void deleteById() {
        List<TemplateTablePo> list = templateTableDao.getAll();
        int i = templateTableDao.deleteById(list.get(0).getId());
        System.out.println(i);
    }

    @Override
    public void deleteBatch() {
        List<TemplateTablePo> list = templateTableDao.getAll();
        List<String> ids = new ArrayList<>();
        list.forEach(it -> {
            if (ids.size() < 10) {
                ids.add(it.getId());
            }
        });
        templateTableDao.deleteBatch(ids);
    }

    @Override
    public List<TemplateTablePo> getByConditionListMap1() {
        List<Map<String, String>> list = new ArrayList<>();
        method(list);
        return templateTableDao.getByConditionListMap1(list);
    }

    private void method(List<Map<String, String>> list) {
        IntStream.range(1, 4).forEach(it -> {
            Map<String, String> map = new HashMap<>(16);
            map.put("updateUser", "echo" + it);
            map.put("datasource", "" + it);
            list.add(map);
        });
    }

    @Override
    public void upsertSelective() {
        TemplateTablePo templateTablePo = new TemplateTablePo();
        templateTableDao.upsertSelective(templateTablePo);
    }

}
