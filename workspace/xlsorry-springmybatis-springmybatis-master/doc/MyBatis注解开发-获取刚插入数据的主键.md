xlecho编辑整理，欢迎转载，转载请声明文章来源。欢迎添加echo微信(微信号：t2421499075)交流学习。 百战不败，依不自称常胜，百败不颓，依能奋力前行。——这才是真正的堪称强大！！！

---

### @Insert是插入语句最为关键的注解
插入接口和注解的示例：
```java
/**
 * 插入一条数据，注解方式不需要返回主键
 * @param sysRolePo
 * @return
 */
@Insert({"insert into sys_role(id, role_name, enable, create_by, create_time) ",
        "values(#{id}, #{roleName}, #{enable}, #{createBy}, #{createTime})"})
int insert1(SysRolePo sysRolePo);
```

### 需要返回主键和xml类似需要useGeneratedKeys和keyProperty
- 返回自增主键需要使用@Options注解
- 返回非自增主键需要使用@SelectKey注解

### 返回主键接口示例
```java
/**
 * 插入一条数据，返回自增主键
 *
 * @param sysRolePo
 * @return
 */
@Insert({"insert into sys_role(role_name, enable, create_by, create_time)",
        "values(#{roleName}, #{enable}, #{createBy}, #{createTime})"})
@Options(useGeneratedKeys = true, keyProperty = "id")
int insert2(SysRolePo sysRolePo);

/**
 * 插入一条数据，返回非自增主键
 *
 * @param sysRolePo
 * @return
 */
@Insert({"insert into sys_role(role_name, enable, create_by, create_time)",
        "values(#{roleName}, #{enable}, #{createBy}, #{createTime})"})
@SelectKey(statement = "SELECT LAST_INSERT_ID()",
        keyProperty = "id",
        resultType = Long.class,
        before = false)
int insert3(SysRolePo sysRolePo);
```

### 测试代码
```java
@Test
public void testInsert() {
    // 获取sqlSession
    SqlSession sqlSession = getSqlSession();
    try {
        // 获取sysUserMapper接口
        SysRoleMapper sysRoleMapper = sqlSession.getMapper(SysRoleMapper.class);
        SysRolePo sysRolePo = new SysRolePo();
        sysRolePo.setRoleName("test");
        sysRolePo.setEnable(2);
        sysRolePo.setCreateBy(1L);
        sysRolePo.setCreateTime(new Date());
        int i = sysRoleMapper.insert1(sysRolePo);
        if(i > 0){
            System.out.println("插入一条数据，注解方式不需要返回主键。操作成功");
        }
        int i1 = sysRoleMapper.insert2(sysRolePo);
        if (i1 > 0){
            System.out.println("插入一条数据，返回自增主键。操作成功，主键为：" + sysRolePo.getId());
        }
    }catch (Exception e) {
        // 如果报错，这里选择回滚
        sqlSession.rollback();
        e.printStackTrace();
    } finally {
        sqlSession.commit();
        sqlSession.close();
    }
}
```

---
做一个有底线的博客主：项目coding地址：https://coding.net/u/xlsorry/p/springmybatis/git