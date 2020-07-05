package com.echo.springmybatis.test;

import com.echo.springmybatis.mapper.SysPrivilegeMapper;
import com.echo.springmybatis.model.SysPrivilegePo;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * @author XLecho
 * Date 2019/9/30 0030
 * Time 8:34
 */
public class SysPrivilegeTest extends BaseMapperTest{

    @Test
    public void testSelectById(){
        SqlSession sqlSession = getSqlSession();
        try{
            SysPrivilegeMapper sysPrivilegeMapper = sqlSession.getMapper(SysPrivilegeMapper.class);
            SysPrivilegePo sysPrivilegePo = sysPrivilegeMapper.selectById(1L);
            System.out.println(sysPrivilegePo);
        } finally {
            sqlSession.close();
        }
    }

}
