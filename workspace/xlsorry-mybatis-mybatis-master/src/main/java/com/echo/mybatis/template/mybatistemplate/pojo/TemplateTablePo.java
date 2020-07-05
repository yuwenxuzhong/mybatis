package com.echo.mybatis.template.mybatistemplate.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 即时到账
 *
 * @author echo
 * @date 2019-09-05 03:09:49
 * @since jdk 1.8
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TemplateTablePo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String id;
    /**
     * 编码
     */
    private String code;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 部门编码
     */
    private String departmentCode;
    /**
     * 父级部门名称
     */
    private String parentDepartmentName;
    /**
     * 父级部门编码
     */
    private String parentDepartmentCode;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 部门交互账号
     */
    private String departmentAccount;
    /**
     * 收款金额
     */
    private BigDecimal balance;
    /**
     * 未使用金额
     */
    private BigDecimal unuseAmount;
    /**
     * 已使用金额
     */
    private BigDecimal alreadyAmount;
    /**
     * 账号类型
     */
    private String accountType;
    /**
     * 数据来源：0：手动导入 1：自动导入
     */
    private String datasource;
    /**
     * 状态，1 成功；2失败
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人
     */
    private String updateUser;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 测试编码
     */
    private String testCode;
}
