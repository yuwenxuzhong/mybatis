package com.echo.springmybatis.test;

import com.echo.springmybatis.mapper.SysUserMapper;
import com.echo.springmybatis.model.SysRolePo;
import com.echo.springmybatis.model.SysUserPo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author XLecho
 * Date 2019/9/25 0025
 * Time 9:36
 */
public class SysUserMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            // 调用selectById方法，查询id = 1的用户
            SysUserPo sysUserPo = sysUserMapper.selectById(1L);
            // sysUserPo不为空
            if (sysUserPo != null && !"".equals(sysUserPo)) {
                System.out.println(sysUserPo);
            } else {
                System.out.println("sysUserPo为空");
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectAll() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            // 调用selectAll方法，查询所有用户
            List<SysUserPo> sysUserPoList = sysUserMapper.selectAll();
            // sysUserPoList不为空
            if (CollectionUtils.isNotEmpty(sysUserPoList)) {
                System.out.println(sysUserPoList);
            } else {
                System.out.println("sysUserPoList为空");
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRolesByUserId() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            // 调用selectAll方法，查询所有用户
            List<SysRolePo> sysRolePos = sysUserMapper.selectRolesByUserId(1L);
            // sysUserPoList不为空
            if (CollectionUtils.isNotEmpty(sysRolePos)) {
                System.out.println(sysRolePos);
            } else {
                System.out.println("sysUserPoList为空");
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void selectRolesByUserIdExpand() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            // 调用selectAll方法，查询所有用户
            List<SysRolePo> sysRolePos = sysUserMapper.selectRolesByUserIdExpand(1L);
            // sysUserPoList不为空
            if (CollectionUtils.isNotEmpty(sysRolePos)) {
                System.out.println(sysRolePos);
            } else {
                System.out.println("sysUserPoList为空");
            }
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert1() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = new SysUserPo();
            sysUserPo.setUserName("test1");
            sysUserPo.setUserPassword("123456");
            sysUserPo.setUserEmail("test@mybatis.tk");
            sysUserPo.setUserInfo("test info");
            sysUserPo.setHeadImg("/img/1.jpg");
            sysUserPo.setCreateTime(new Date());
            int result = sysUserMapper.insert1(sysUserPo);
            if (result > 0) {
                System.out.println("插入一条数据成功, 返回结果等于: " + result);
            }
        } catch (Exception e) {
            // 如果报错，这里选择回滚
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            // 如果不设置commit，在会话结束之后存在session中的数据就会消失
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void testInsert2() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = new SysUserPo();
            sysUserPo.setUserName("test2");
            sysUserPo.setUserPassword("123456");
            sysUserPo.setUserEmail("test@mybatis.tk");
            sysUserPo.setUserInfo("test info");
            sysUserPo.setHeadImg("/img/2.jpg");
            sysUserPo.setCreateTime(new Date());
            int result = sysUserMapper.insert2(sysUserPo);
            if (result > 0) {
                System.out.println("方法的返回值为：" + result);
                System.out.println("插入一条数据成功, id回写到了传入的对象中： " + sysUserPo);
            }
        } catch (Exception e) {
            // 如果报错，这里选择回滚
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            // 如果不设置commit，在会话结束之后存在session中的数据就会消失
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void testInsert3() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = new SysUserPo();
            sysUserPo.setUserName("test5");
            sysUserPo.setUserPassword("123456");
            sysUserPo.setUserEmail("test@mybatis.tk");
            sysUserPo.setUserInfo("test info");
            sysUserPo.setHeadImg("/img/5.jpg");
            sysUserPo.setCreateTime(new Date());
            int result = sysUserMapper.insert3(sysUserPo);
            if (result > 0) {
                System.out.println("方法的返回值为：" + result);
                System.out.println("插入一条数据成功, id回写到了传入的对象中： " + sysUserPo);
            }
        } catch (Exception e) {
            // 如果报错，这里选择回滚
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            // 如果不设置commit，在会话结束之后存在session中的数据就会消失
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateById() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = sysUserMapper.selectById(1011L);
            sysUserPo.setUserName("abcde");
            int result = sysUserMapper.updateById(sysUserPo);
            if (result > 0) {
                System.out.println("方法的返回值为：" + result);
                System.out.println("插入一条数据成功, id回写到了传入的对象中： " + sysUserPo);
            }
        } catch (Exception e) {
            // 如果报错，这里选择回滚
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            // 由于默认的sqlSessionFactory.openSession()是不会自动提交的
            // 如果不设置commit，在会话结束之后存在session中的数据就会消失
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteById() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = sysUserMapper.selectById(1011L);
            System.out.println(sysUserPo);
            int result = sysUserMapper.deleteById(1011L);
            System.out.println("方法的返回值为：" + result);
            SysUserPo sysUserPo1 = sysUserMapper.selectById(1011L);
            System.out.println(sysUserPo1);
        } catch (Exception e) {
            // 如果报错，这里选择回滚
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            // 由于默认的sqlSessionFactory.openSession()是不会自动提交的
            // 如果不设置commit，在会话结束之后存在session中的数据就会消失
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectRolesByUserIdAndRoleEnable() {
        // 获取sqlSession
        SqlSession sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            List<SysRolePo> sysRolePos = sysUserMapper.selectRolesByUserIdAndRoleEnable(1L, 1);
            System.out.println(sysRolePos);
        } catch (Exception e) {
            // 如果报错，这里选择回滚
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            // 由于默认的sqlSessionFactory.openSession()是不会自动提交的
            // 如果不设置commit，在会话结束之后存在session中的数据就会消失
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByUser() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = new SysUserPo();
            sysUserPo.setUserName("ad");
            List<SysUserPo> sysUserPoList = sysUserMapper.selectByUser(sysUserPo);
            System.out.println(sysUserPoList);
            sysUserPo.setUserEmail("test@mybatis.tk");
            List<SysUserPo> sysUserPoList1 = sysUserMapper.selectByUser(sysUserPo);
            System.out.println(sysUserPoList1);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByIdSelective() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = new SysUserPo();
            sysUserPo.setId(1L);
            sysUserPo.setUserEmail("test@mybatis.tk");
            int i = sysUserMapper.updateByIdSelective(sysUserPo);
            System.out.println(i);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsert5Selective() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = new SysUserPo();
            sysUserPo.setId(1L);
            sysUserPo.setUserName("admin");
            sysUserPo.setUserPassword("test info");
            sysUserPo.setUserEmail("test@mybatis.tk");
            sysUserPo.setCreateTime(new Date());
            int i = sysUserMapper.insert5(sysUserPo);
            System.out.println(i);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByIdOrUserName() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = new SysUserPo();
            sysUserPo.setId(1L);
            sysUserPo.setUserName("admin");
            SysUserPo sysUserPo1 = sysUserMapper.selectByIdOrUserName(sysUserPo);
            System.out.println("sysUserPo1: " + sysUserPo1 + "---------------------------------");
            sysUserPo.setId(null);
            SysUserPo sysUserPo2 = sysUserMapper.selectByIdOrUserName(sysUserPo);
            System.out.println("sysUserPo2: " + sysUserPo2 + "---------------------------------");
            sysUserPo.setUserName(null);
            SysUserPo sysUserPo3 = sysUserMapper.selectByIdOrUserName(sysUserPo);
            System.out.println("sysUserPo3: " + sysUserPo3 + "---------------------------------");
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByUser2() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = new SysUserPo();
            sysUserPo.setUserName("admin");
            sysUserPo.setUserEmail("admin@mybatis.tk");
            List<SysUserPo> sysUserPoList = sysUserMapper.selectByUser2(sysUserPo);
            System.out.println(sysUserPoList);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByUser3() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = new SysUserPo();
            sysUserPo.setUserName("ad");
            sysUserPo.setUserEmail("admin@mybatis.tk");
            List<SysUserPo> sysUserPoList = sysUserMapper.selectByUser3(sysUserPo);
            System.out.println(sysUserPoList);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSelectByIdList() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            List<Long> idList = Arrays.asList(1L, 1006L, 1007L, 1008L, 1009L);
            List<SysUserPo> sysUserPoList = sysUserMapper.selectByIdList(idList);
            System.out.println(sysUserPoList);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testInsertList() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            List<SysUserPo> userPoList = new ArrayList<>();
            IntStream.range(0, 5).forEach(it -> {
                SysUserPo sysUserPo = new SysUserPo();
                sysUserPo.setUserName("test" + it);
                sysUserPo.setUserPassword("123456" + it);
                sysUserPo.setUserEmail("test@mybatis.tk" + it);
                userPoList.add(sysUserPo);
            });
            int result = sysUserMapper.insertList(userPoList);
            System.out.println(result + "---------------------------------");
            userPoList.forEach(System.out::println);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByMap() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            Map<String, Object> map = new HashMap<>();
            map.put("id", 1L);
            map.put("user_email", "test@mybatis.tk");
            map.put("user_password", "123456");
            sysUserMapper.updateByMap(map);
            SysUserPo sysUserPo = sysUserMapper.selectById(1L);
            System.out.println(sysUserPo);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserAndRoleById() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = sysUserMapper.selectUserAndRoleById(1001L);
            System.out.println(sysUserPo);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserAndRoleById2() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = sysUserMapper.selectUserAndRoleById2(1001L);
            System.out.println(sysUserPo);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testSelectUserAndRoleById3() {
        SqlSession sqlSession = getSqlSession();
        try {
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            SysUserPo sysUserPo = sysUserMapper.selectUserAndRoleById3(1001L);
            System.out.println(sysUserPo);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }
}
