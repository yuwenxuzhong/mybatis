package com.echo.springmybatis.test;

import com.echo.springmybatis.model.Country;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @author XLecho
 * Date 2019/9/25 0025
 * Time 9:36
 */
public class CountryMapperTest extends BaseMapperTest {

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = getSqlSession();
        try {
            List<Country> countryList = sqlSession.selectList("com.echo.springmybatis.mapper.CountryMapper.selectAll");
            printCountryList(countryList);
        } finally {
            sqlSession.close();
        }
    }

    private void printCountryList(List<Country> countryList) {
        countryList.forEach(it -> System.out.printf("%-4d%4s%4s\n", it.getId(), it.getCountryname(), it.getCountrycode()));
    }

}
