package com.echo.springmybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 权限表
 *
 * @author echo
 * @date 2019-09-25 02:33:53
 * @since jdk1.8
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysPrivilegePo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    private Long id;
    /**
     * 权限名称
     */
    private String privilegeName;
    /**
     * 权限URL
     */
    private String privilegeUrl;

}
