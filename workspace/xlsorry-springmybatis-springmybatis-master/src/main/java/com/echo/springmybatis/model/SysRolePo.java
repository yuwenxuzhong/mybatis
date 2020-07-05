package com.echo.springmybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 *
 * @author echo
 * @date 2019-09-25 02:33:53
 * @since jdk1.8
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysRolePo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private Long id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 有效标志
     */
    private Integer enable;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 用户信息
     */
    private SysUserPo sysUserPo;

}
