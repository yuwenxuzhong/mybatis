package com.echo.springmybatis.test;

import com.echo.springmybatis.mapper.SysRoleMapper;
import com.echo.springmybatis.model.SysRolePo;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @author XLecho
 * Date 2019/9/28 0028
 * Time 9:02
 */
public class SysRoleMapperTest extends BaseMapperTest{

    @Test
    public void testSelectById() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            // 调用selectById方法，查询id = 1的用户
            SysRolePo sysRolePo1 = sysRoleMapper.selectById1(1L);
            System.out.println(sysRolePo1);
            SysRolePo sysRolePo2 = sysRoleMapper.selectById2(1L);
            System.out.println(sysRolePo2);
            List<SysRolePo> sysRolePos = sysRoleMapper.selectAll();
            System.out.println(sysRolePos);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            SysRolePo sysRolePo = new SysRolePo();
            sysRolePo.setRoleName("test");
            sysRolePo.setEnable(2);
            sysRolePo.setCreateBy(1L);
            sysRolePo.setCreateTime(new Date());
            int i = sysRoleMapper.insert1(sysRolePo);
            if(i > 0){
                System.out.println("插入一条数据，注解方式不需要返回主键。操作成功");
            }
            int i1 = sysRoleMapper.insert2(sysRolePo);
            if (i1 > 0){
                System.out.println("插入一条数据，返回自增主键。操作成功，主键为：" + sysRolePo.getId());
            }
        }catch (Exception e) {
            // 如果报错，这里选择回滚
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void testUpdate() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            SysRolePo sysRolePo = sysRoleMapper.selectById1(6L);
            sysRolePo.setRoleName("test");
            sysRolePo.setEnable(3);
            sysRolePo.setCreateBy(1L);
            sysRolePo.setCreateTime(new Date());
            int i = sysRoleMapper.updateById(sysRolePo);
            if(i > 0){
                System.out.println("根据id修改一条数据。操作成功");
            }
        }catch (Exception e) {
            // 如果报错，这里选择回滚
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void testDelete() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
            int i = sysRoleMapper.deleteById(6L);
            if(i > 0){
                System.out.println("根据id删除一条数据。操作成功");
            }
        }catch (Exception e) {
            // 如果报错，这里选择回滚
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }

}
