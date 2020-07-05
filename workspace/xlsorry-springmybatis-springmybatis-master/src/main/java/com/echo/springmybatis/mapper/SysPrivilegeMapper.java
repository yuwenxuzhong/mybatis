package com.echo.springmybatis.mapper;

import com.echo.springmybatis.model.SysPrivilegePo;
import com.echo.springmybatis.provider.PrivilegeProvider;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author XLecho
 * Date 2019/9/26 0026
 * Time 14:06
 */
public interface SysPrivilegeMapper {

    @SelectProvider(type = PrivilegeProvider.class, method = "selectById2")
    SysPrivilegePo selectById(Long id);

}
