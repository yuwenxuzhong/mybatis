package com.echo.mybatis.template.mybatistemplate.dao;

import com.echo.mybatis.template.mybatistemplate.pojo.TemplateTablePo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * dao数据层
 *
 * @author echo
 * @date 2019-09-05 03:09:49
 * @since jdk 1.8
 */
@Mapper
public interface TemplateTableDao {

    /**
     * 如果数据过多，会出现读取过于缓慢或者直接卡死报错。在语句上加上fetchSize="1000"即可，代表一次加载1000条
     *
     * @return
     */
    List<TemplateTablePo> getAll();

    TemplateTablePo getOne(String id);

    /**
     * 传入String类型的时间，整合的是mysql的话不需要使用to_char
     * 如果整合oracle where 后面应该这样写：to_char(create_time, 'yyyy-MM-dd') = #{yesterday, jdbcType=TIMESTAMP}
     *
     * @param yesterday
     * @return
     */
    List<TemplateTablePo> getYesterday(String yesterday);

    /**
     * 传入DATE类型的时间
     *
     * @param yesterday
     * @return
     */
    List<TemplateTablePo> getYesterdayDate(Date yesterday);

    /**
     * 插入一条数据
     *
     * @param templateTablePos
     * @return
     */
    void insert(TemplateTablePo templateTablePos);

    /**
     * 批量插入数据
     * batch批量提交大量数据, 如果数据量不超过1w，系统可以接受就不需要做处理
     * 如果数据量大于10w，这个时候需要做处理
     * * 方案一：自己手写jdbc，session = sqlSessionFactory.openSession(ExecutorType.BATCH, false); 并且设置每次插入条数
     * * 方案二： xml中循环的时候，foreach批量，分页插入
     * <p>
     * 注意：如果是oracle foreach里面的separator需要设置成为UNION ALL <foreach collection="list" index="index" item="item" separator="UNION ALL">
     *
     * @param templateTablePos
     */
    void batchInsert(List<TemplateTablePo> templateTablePos);

    /**
     * 根据主键有选择的更新数据库表
     *
     * @param templateTablePos
     */
    void updateByPrimaryKeySelective(TemplateTablePo templateTablePos);

    /**
     * 根据主键批量更新数据库表中的记录
     * 异常：Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'set id = '0049f5c9-f1cb-49b6-9227-2cea5a157493', ' at line 2
     * ### The error may involve com.echo.mybatis.template.mybatistemplate.dao.TemplateTableDao.batchUpdate-Inline
     * 解决方法：直接在jdbc的连接后面添加：&allowMultiQueries=true  -> url: jdbc:mysql://192.168.222.132:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC&allowMultiQueries=true
     * 如果是oracle数据库报错这样的错误，不需要在数据库连接上添加东西，只需要在foreach上添加：open="begin" close=";end;"即可 -> <foreach collection="list" index="index" item="item" open="begin" close=";end;" separator=";">
     *
     * @param list
     */
    void batchUpdate(List<TemplateTablePo> list);

    /**
     * 根据Map的多条件参数查询对应的数据
     *
     * @param map
     * @return
     */
    List<TemplateTablePo> getByConditionMap(Map<String, String> map);

    /**
     * 出现需要分组条件，根据多条件参数查询对应的数据
     *
     * @param map
     * @return
     */
    List<TemplateTablePo> getByConditionListMap(Map<String, String> map);

    /**
     * 根据ID删除一条数据
     *
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 根据多个id，批量删除数据
     *
     * @param ids
     */
    void deleteBatch(List<String> ids);

    /**
     * 出现需要分组条件，根据多条件参数查询对应的数据
     * 这里如果需要加上：separator="union"  -> <foreach collection="list" item="item" index="index" separator="union"> 相当于返回一个并集
     * 如果没有上面的内容，就会出现只有一个返回集
     *
     * @param list
     * @return
     */
    List<TemplateTablePo> getByConditionListMap1(List<Map<String, String>> list);

    /**
     * 存在即更新，否者插入
     * 注意：该方法是用于Oracle的
     *
     * @param templateTablePo
     */
    void upsertSelective(TemplateTablePo templateTablePo);
}
