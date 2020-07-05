package com.echo.springmybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author echo
 * @date 2019-09-25 02:33:53
 * @since jdk1.8
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysUserPo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 邮箱
     */
    private String userEmail;
    /**
     * 简介
     */
    private String userInfo;
    /**
     * 头像
     */
    private String headImg;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户角色
     */
    private SysRolePo sysRolePo;

}
