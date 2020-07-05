package com.echo.springmybatis.test;

import com.echo.springmybatis.mapper.SysUserMapper;
import com.echo.springmybatis.model.SysUserPo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * @author XLecho
 * Date 2019/10/8 0008
 * Time 19:23
 */
public class CacheTest extends BaseMapperTest{

    @Test
    public void testL1Cache(){
        // 获取SqlSession
        SqlSession sqlSession = getSqlSession();
        SysUserPo sysUserPo = null;
        // 获取sysUserMapper接口
        SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
        try{
            // 调用selectById方法，查询id=1的用户
            sysUserPo = sysUserMapper.selectById(1L);
            System.out.println("这是sysUserPo没有修改值之前的输出: " + sysUserPo);
            // 对当前获取的对象重新赋值
            sysUserPo.setUserName("New Echo");
            // 再次查询获取id相同的用户
            SysUserPo sysUserPo1 = sysUserMapper.selectById(1L);
            // 虽然没有更新数据库，但是这个用户和sysUserPo重新复制的名字相同
            System.out.println("这是sysUserPo修改值之后的输出: " + sysUserPo);
            System.out.println("这次输出的是sysUserPo1: " + sysUserPo1);
        }finally {
            sqlSession.close();
        }

        // ************************ 对比打印结果我们发现两个结果一模一样，并没有更新。开启新会话 ********************
        sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            // 调用selectById方法，查询id=1的用户
            SysUserPo sysUserPo2 = sysUserMapper.selectById(1L);
            System.out.println("查询之后立马输出sysUserPo2: " + sysUserPo2);
            sysUserMapper.deleteById(2L);
            System.out.println("做完删除动作之后再次输出sysUserPo2: " + sysUserPo2);
            // 获取id为1的用户
            SysUserPo sysUserPo3 = sysUserMapper.selectById(1L);
            System.out.println("再次查询输出sysUserPo2: " + sysUserPo2);
            System.out.println("这次输出的是sysUserPo3: " + sysUserPo3);
        }finally {
            sqlSession.close();
        }
    }

    @Test
    public void testL2Cache(){
        // 获取SqlSession
        SqlSession sqlSession = getSqlSession();
        SysUserPo sysUserPo = null;
        try{
            // 获取sysUserMapper接口
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            // 查询用户id为1的用户
            sysUserPo = sysUserMapper.selectById(1L);
            System.out.println("第一查询之后，马上输出sysUserPo: " + sysUserPo);
            sysUserPo.setUserName("echo");
            // 修改对象之后，再次查询
            SysUserPo sysUserPo1 = sysUserMapper.selectById(1L);
            System.out.println("第二查询之后，马上输出sysUserPo1: " + sysUserPo1);
        }finally {
            sqlSession.close();
        }

        System.out.println("-----------------------------------------");
        sqlSession = getSqlSession();
        try {
            // 获取sysUserMapper接口
            SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
            // 查询用户id为1的用户
            SysUserPo sysUserPo2 = sysUserMapper.selectById(1L);
            System.out.println("第二个会话查询之后，马上输出sysUserPo2: " + sysUserPo2);
            sysUserPo2.setUserName("echo");
            // 修改对象之后，再次查询
            SysUserPo sysUserPo3 = sysUserMapper.selectById(1L);
            System.out.println("第二个会话再次查询之后，马上输出sysUserPo3: " + sysUserPo3);
        } finally {
            sqlSession.close();
        }
    }

}
