package com.echo.springmybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 角色权限关联表
 *
 * @author echo
 * @date 2019-09-25 02:33:53
 * @since jdk1.8
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysRolePrivilegePo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 权限ID
     */
    private Long privilegeId;
}
