xlecho编辑整理，欢迎转载，转载请声明文章来源。欢迎添加echo微信(微信号：t2421499075)交流学习。 百战不败，依不自称常胜，百败不颓，依能奋力前行。——这才是真正的堪称强大！！！

---

> 这里的查询一SysUser类为例，一个用户对应一个用户角色这里使用association实现

### 先使用自动映射来实现关联查询
```
// 在SysUserPo中加入如下属性
private SysRolePo sysRolePo;

// 接口
SysUserPo selectUserAndRoleById(Long id);

<select id="selectUserAndRoleById" resultType="com.echo.springmybatis.model.SysUserPo">
    select
      u.id,
      u.user_name userName,
      u.user_password userPassword,
      u.user_email userEmail,
      u.user_info userInfo,
      u.head_img headImg,
      u.create_time createTime,
      r.id "sysRolePo.id",
      r.role_name "sysRolePo.roleName",
      r.enable "sysRolePo.enable",
      r.create_by "sysRolePo.createBy",
      r.create_time "sysRolePo.createTime"
    from sys_user u
    inner join sys_user_role ur on u.id = ur.user_id
    inner join sys_role r on ur.role_id = r.id
    where u.id = #{id}
</select>
```

测试代码：
```java
@Test
public void testSelectUserAndRoleById() {
    SqlSession sqlSession = getSqlSession();
    try {
        SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUserPo sysUserPo = sysUserMapper.selectUserAndRoleById(1001L);
        System.out.println(sysUserPo);
    } finally {
        sqlSession.rollback();
        sqlSession.close();
    }
}
```

运行之后我们会发现，它能够直接将我们想要的字段通过sql映射到我们的SysUserPo中。
> 注意:测试这个代码的时候，记得注释掉SysRolePo中的SysUserPo，这是我们之前测试查询角色对应用户用到了的。如果不注释特定情况下可能会造成递归查询。

### 使用resultMap配置一对一映射
这种方式比自动映射要相对复杂一点，但是如果使用的多能够有效减少代码。
```
SysUserPo selectUserAndRoleById2(Long id);

<select id="selectUserAndRoleById2" resultMap="userMap">
    select
      u.id,
      u.user_name,
      u.user_password,
      u.user_email,
      u.user_info,
      u.head_img,
      u.create_time,
      r.id role_id,
      r.role_name,
      r.enable enable,
      r.create_by create_by,
      r.create_time role_create_time
    from sys_user u
    inner join sys_user_role ur on u.id = ur.user_id
    inner join sys_role r on ur.role_id = r.id
    where u.id = #{id}
</select>
```

测试代码：
```java
@Test
public void testSelectUserAndRoleById2() {
    SqlSession sqlSession = getSqlSession();
    try {
        SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUserPo sysUserPo = sysUserMapper.selectUserAndRoleById2(1001L);
        System.out.println(sysUserPo);
    } finally {
        sqlSession.rollback();
        sqlSession.close();
    }
}
```

### 使用association一对一映射(resultMap的标签)
```
SysUserPo selectUserAndRoleById3(Long id);

<select id="selectUserAndRoleById3" resultMap="userRoleMap">
    select
      u.id,
      u.user_name,
      u.user_password,
      u.user_email,
      u.user_info,
      u.head_img,
      u.create_time,
      r.id role_id,
      r.role_name,
      r.enable enable,
      r.create_by create_by,
      r.create_time role_create_time
    from sys_user u
    inner join sys_user_role ur on u.id = ur.user_id
    inner join sys_role r on ur.role_id = r.id
    where u.id = #{id}
</select>
```

测试代码：
```java
@Test
public void testSelectUserAndRoleById3() {
    SqlSession sqlSession = getSqlSession();
    try {
        SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
        SysUserPo sysUserPo = sysUserMapper.selectUserAndRoleById3(1001L);
        System.out.println(sysUserPo);
    } finally {
        sqlSession.rollback();
        sqlSession.close();
    }
}
```