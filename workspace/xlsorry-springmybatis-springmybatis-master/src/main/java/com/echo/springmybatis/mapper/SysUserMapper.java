package com.echo.springmybatis.mapper;

import com.echo.springmybatis.model.SysRolePo;
import com.echo.springmybatis.model.SysUserPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author XLecho
 * Date 2019/9/26 0026
 * Time 14:06
 */
public interface SysUserMapper {

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    SysUserPo selectById(Long id);

    /**
     * 查询全部用户
     *
     * @return
     */
    List<SysUserPo> selectAll();

    /**
     * 根据用户id获取角色信息
     *
     * @param userId
     * @return
     */
    List<SysRolePo> selectRolesByUserId(Long userId);

    /**
     * 根据用户id获取角色信息
     * 注意：这里是一个基于上面接口的特殊情况，查询的信息不仅要包含角色信息，
     * 还要包含部分当前用户的信息
     *
     * @param userId
     * @return
     */
    List<SysRolePo> selectRolesByUserIdExpand(Long userId);

    /**
     * 新增用户
     *
     * @param sysUserPo
     * @return
     */
    int insert1(SysUserPo sysUserPo);

    /**
     * 新增用户，同时返回新增记录id
     * userCeneratedKeys设置成为true
     * keyProperty设置成为id
     * 注意：id不是设置到返回值，而是回写到传入的对象中,同时该方法只能适用于支持主键自增的数据库
     *
     * @param sysUserPo
     * @return
     */
    int insert2(SysUserPo sysUserPo);

    /**
     * 新增用户，同时返回新增记录id <selectKey>
     * 注意：这个方法是使用的mysql数据库，不能用于oracle
     *
     * @param sysUserPo
     * @return
     */
    int insert3(SysUserPo sysUserPo);

    /**
     * 新增用户，同时返回新增记录id <selectKey>
     * 注意：这个方法是使用的oracle数据库，不能用于mysql
     *
     * @param sysUserPo
     * @return
     */
    int insert4(SysUserPo sysUserPo);

    /**
     * 根据主键更新
     *
     * @param sysUserPo
     * @return
     */
    int updateById(SysUserPo sysUserPo);

    /**
     * 通过主键删除
     *
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 根据用户id和角色的enable状态获取用户的角色
     *
     * @param userId
     * @param enable
     * @return
     */
    List<SysRolePo> selectRolesByUserIdAndRoleEnable(@Param("userId") Long userId,
                                                     @Param("enable") Integer enable);

    /**
     * 多条件查询：用户名模糊，邮箱精准
     *
     * @param sysUserPo
     * @return
     */
    List<SysUserPo> selectByUser(SysUserPo sysUserPo);

    /**
     * 根据主键更新
     *
     * @param sysUserPo
     * @return
     */
    int updateByIdSelective(SysUserPo sysUserPo);

    /**
     * 插入一条数据(条件语句形式)
     *
     * @param sysUserPo
     * @return
     */
    int insert5(SysUserPo sysUserPo);

    /**
     * 根据用户id或用户名查询
     *
     * @param sysUserPo
     * @return
     */
    SysUserPo selectByIdOrUserName(SysUserPo sysUserPo);

    /**
     * 根据用户名和邮箱查询用户
     *
     * @param sysUserPo
     * @return
     */
    List<SysUserPo> selectByUser2(SysUserPo sysUserPo);

    /**
     * 根据用户名和邮箱查询用户
     *
     * @param sysUserPo
     * @return
     */
    List<SysUserPo> selectByUser3(SysUserPo sysUserPo);

    /**
     * 根据用户名和邮箱查询用户
     * 重点：使用mysql数据库（多数据源）
     * @param sysUserPo
     * @return
     */
    List<SysUserPo> selectByUserToMySQL(SysUserPo sysUserPo);

    /**
     * 根据用户名和邮箱查询用户
     * 重点：使用oracle数据库（多数据源）
     * @param sysUserPo
     * @return
     */
    List<SysUserPo> selectByUserToOracle(SysUserPo sysUserPo);

    /**
     * 根据id有选择性的更新记录
     * 重点：bind->转换like使用concat拼接问题
     *
     * @param sysUserPo
     * @return
     */
    int updateByIdSelective2(SysUserPo sysUserPo);

    /**
     * 根据用户id集合查询
     *
     * @param idList
     * @return
     */
    List<SysUserPo> selectByIdList(List<Long> idList);

    /**
     * 批量插入用户信息
     *
     * @param userPos
     * @return
     */
    int insertList(List<SysUserPo> userPos);

    /**
     * 通过map更新列
     *
     * @param map
     * @return
     */
    int updateByMap(Map<String, Object> map);

    /**
     * 根据用户id获取用户信息和用户的角色信息
     * 使用自动映射模式
     * @param id
     * @return
     */
    SysUserPo selectUserAndRoleById(Long id);

    /**
     * 根据用户id获取用户信息和用户的角色信息
     * 使用resultMap的方式
     * @param id
     * @return
     */
    SysUserPo selectUserAndRoleById2(Long id);

    /**
     * 根据用户id获取用户信息和用户的角色信息
     * 使用association的方式(resultMap的标签)
     * @param id
     * @return
     */
    SysUserPo selectUserAndRoleById3(Long id);

}
