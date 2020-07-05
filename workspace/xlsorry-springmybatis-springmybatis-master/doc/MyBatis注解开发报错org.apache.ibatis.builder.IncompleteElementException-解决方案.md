xlecho编辑整理，欢迎转载，转载请声明文章来源。欢迎添加echo微信(微信号：t2421499075)交流学习。 百战不败，依不自称常胜，百败不颓，依能奋力前行。——这才是真正的堪称强大！！

---

出现错误的关键代码如下：
```java
// 接口
@Results(id = "roleResultMap", value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "roleName", column = "role_name"),
        @Result(property = "enable", column = "enable"),
        @Result(property = "createBy", column = "create_by"),
        @Result(property = "createTime", column = "create_time")
})
@ResultMap("roleResultMap")
@Select("select id, role_name, enable, create_by, create_time " +
        "from sys_role where id = #{id}" )
SysRolePo selectById2(Long id);
```

测试改接口，出现如下错误。
```
org.apache.ibatis.builder.IncompleteElementException: Could not find result map com.echo.springmybatis.mapper.SysRoleMapper.roleResultMap

	at org.apache.ibatis.builder.MapperBuilderAssistant.getStatementResultMaps(MapperBuilderAssistant.java:346)
	at org.apache.ibatis.builder.MapperBuilderAssistant.addMappedStatement(MapperBuilderAssistant.java:290)
	at org.apache.ibatis.builder.annotation.MapperAnnotationBuilder.parseStatement(MapperAnnotationBuilder.java:351)
	at org.apache.ibatis.builder.annotation.MethodResolver.resolve(MethodResolver.java:33)
	……
```

错误提示的意思很明显就是找不到roleResultMap，但是明显@ResultMap("roleResultMap")最上面就是对应的值。


### 解决方案
```java
@Results(id = "roleResultMap", value = {
        @Result(property = "id", column = "id", id = true),
        @Result(property = "roleName", column = "role_name"),
        @Result(property = "enable", column = "enable"),
        @Result(property = "createBy", column = "create_by"),
        @Result(property = "createTime", column = "create_time")
})
@Select("select id, role_name, enable, create_by, create_time " +
        "from sys_role where id = #{id}" )
SysRolePo selectById2(Long id);

@ResultMap("roleResultMap")
@Select("select * from sys_role")
List<SysRolePo> selectAll();
```

> 这两个注解，接口上面只能有一个存在，ResultMap又依赖于Results，用ResultMap的时候只是公用其它的Results

项目coding地址：https://coding.net/u/xlsorry/p/springmybatis/git