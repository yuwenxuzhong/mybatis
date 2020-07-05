/*
 * 注意Provider的方式必须要有一个空的构造方法
 */
package com.echo.springmybatis.provider;

import org.apache.ibatis.jdbc.SQL;

/**
 * @author XLecho
 * Date 2019/9/29 0029
 * Time 17:01
 */
public class PrivilegeProvider {

    /**
     * 根据id查询权限
     *  这一种写法和下面selectById2是一样的，推荐使用new SQL()的方式
     *
     * @param id
     * @return
     */
    public String selectById1(final Long id) {
        return new SQL() {
            {
                SELECT("id, privilege_name, privilege_url");
                FROM("sys_privilege");
                WHERE("id = #{id}");
            }
        }.toString();
    }

    public String selectById2(final Long id) {
        return "select id, privilege_name, privilege_url from sys_privilege where id = #{id}";
    }


}
