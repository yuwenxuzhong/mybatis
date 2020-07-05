package com.echo.springmybatis.mapper;

import com.echo.springmybatis.model.SysRolePo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author XLecho
 * Date 2019/9/26 0026
 * Time 14:06
 */
public interface SysRoleMapper {

    @Select({"select id, role_name roleName, enable," +
            "create_by createBy, create_time createTime " +
            "from sys_role where id = #{id}"})
    SysRolePo selectById1(Long id);

    /**
     * 根据id查询角色信息
     * 采坑文章：MyBatis注解开发报错org.apache.ibatis.builder.IncompleteElementException-解决方案.md
     * Results和ResultMap共用报错：org.apache.ibatis.builder.IncompleteElementException:
     * Could not find result map com.echo.springmybatis.mapper.SysRoleMapper.roleResultMap
     *
     * @param id
     * @return
     */
    @Results(id = "roleResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "enable", column = "enable"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("select id, role_name, enable, create_by, create_time " +
            "from sys_role where id = #{id}")
    SysRolePo selectById2(Long id);

    @ResultMap("roleResultMap")
    @Select("select * from sys_role")
    List<SysRolePo> selectAll();

    /**
     * 插入一条数据，注解方式不需要返回主键
     *
     * @param sysRolePo
     * @return
     */
    @Insert({"insert into sys_role(id, role_name, enable, create_by, create_time) ",
            "values(#{id}, #{roleName}, #{enable}, #{createBy}, #{createTime})"})
    int insert1(SysRolePo sysRolePo);

    /**
     * 插入一条数据，返回自增主键
     *
     * @param sysRolePo
     * @return
     */
    @Insert({"insert into sys_role(role_name, enable, create_by, create_time)",
            "values(#{roleName}, #{enable}, #{createBy}, #{createTime})"})
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert2(SysRolePo sysRolePo);

    /**
     * 插入一条数据，返回非自增主键
     *
     * @param sysRolePo
     * @return
     */
    @Insert({"insert into sys_role(role_name, enable, create_by, create_time)",
            "values(#{roleName}, #{enable}, #{createBy}, #{createTime})"})
    @SelectKey(statement = "SELECT LAST_INSERT_ID()",
            keyProperty = "id",
            resultType = Long.class,
            before = false)
    int insert3(SysRolePo sysRolePo);

    @Update({"update sys_role set role_name = #{roleName}, enable = #{enable},",
            "create_by = #{createBy}, create_time = #{createTime} where id = #{id}"})
    int updateById(SysRolePo sysRolePo);

    @Delete("delete from sys_role where id = #{id}")
    int deleteById(Long id);

}
