xlecho编辑整理，欢迎转载，转载请声明文章来源。欢迎添加echo微信(微信号：t2421499075)交流学习。 百战不败，依不自称常胜，百败不颓，依能奋力前行。——这才是真正的堪称强大！！

---

#### 参考书籍：MyBatis从入门到精通

> 该文章以springmybatis项目为基准，可以参照该项目的代码。代码地址：https://coding.net/u/xlsorry/p/springmybatis/git  配置mybatis获取新增数据id的方法有几种，一种是JDBC的方式，一种是使用标签<selectKey>

- 先看一段没有主键返回的代码，用于和后面有主键返回的代码进行对比。
```
int insert1(SysUserPo sysUserPo);

<insert id="insert1">
    insert into
      sys_user(id, user_name, user_password, user_email, user_info, head_img, create_time)
    values
      (#{id}, #{userName}, #{userPassword}, #{userEmail}, #{userInfo}, #{headImg}, #{createTime})
</insert>
```
以上代码我们可以看到返回的结果就是int值，但是并不是我们要的数据的id

### 使用JDBC方式返回主键自增的值
这种方式比较简单，只需要在insert标签上面配置如下两个属性，同时去掉insert语句中间id和#{id}即可。
```
useGeneratedKeys="true"
keyProperty="id"
```

完整代码如下：
```
int insert2(SysUserPo sysUserPo);

<insert id="insert2" useGeneratedKeys="true" keyProperty="id">
    insert into
      sys_user(user_name, user_password, user_email, user_info, head_img, create_time)
    values
      (#{userName}, #{userPassword}, #{userEmail}, #{userInfo}, #{headImg}, #{createTime})
</insert>
```

- 该方式的缺点：
这种方法只能支持主键自增的数据库，如：mysql

useGeneratedKeys设置成为true后，MyBatis会使用JDBC的getGeneratedKeys方法取出由数据库内部生成的主键。获得主键值后将其赋值给keyProperty配置的id属性。

- 注意：
测试的时候我们可以看到，返回的值是一个int值，该值并不是返回的id，id如上所说是赋值到了keyProperty配置的id属性当中，也就是我们传入的对象里面。

### 使用selectKey返回主键的值
使用selecKey相对来说会复杂一点，需要针对不同的数据库来进行设置。可以先看看项目里面代码（mysql数据库）
```
int insert3(SysUserPo sysUserPo);

<insert id="insert3">
    insert into
      sys_user(user_name, user_password, user_email, user_info, head_img, create_time)
    values
      (#{userName}, #{userPassword}, #{userEmail}, #{userInfo}, #{headImg}, #{createTime})
    <selectKey keyColumn="id" resultType="long" keyProperty="id" order="AFTER">
        SELECT LAST_INSERT_ID()
    </selectKey>
</insert>
```

和最开始的代码进行对比我们可以看到新增了selectKey标签。
```
<selectKey keyColumn="id" resultType="long" keyProperty="id" order="AFTER">
        SELECT LAST_INSERT_ID()
    </selectKey>
```

selectKey标签中几个属性的解释
- keyColumn:库中对应的id列
- keyProperty:赋值对应的对象属性
- resultType:用于设置返回值类型
- order比较特殊，它的设置是根据数据库相关的。在MySQL数据库中，order="AFTER"。在Oracle数据库中，order="BEFORE"。

> order的配置after和before是根据数据库的主键生成策略来决定的，mysql的自增策略会让insert执行成功之后才能得到id，oracle使用的是序列，插入数据到oracle需要先从序列获取值，然后才会将数据库插入数据库中。

使用selectKey获取插入Oracle数据库的数据id，示例如下:
```
int insert4(SysUserPo sysUserPo);

<insert id="insert4">
    <selectKey keyColumn="id" resultType="long" keyProperty="id" order="BEFORE">
        SELECT SEQ_ID.nextval from dual
    </selectKey>
    insert into
    sys_user(user_name, user_password, user_email, user_info, head_img, create_time)
    values
    (#{userName}, #{userPassword}, #{userEmail}, #{userInfo}, #{headImg}, #{createTime})
</insert>
```

### 其他一些支持主键自增的数据库配置selectKey中回写主键的SQL
- DB2使用 VALUES IDENTITY_VAL_LOCAL()
- MYSQL使用 SELECT LAST_INSERT_ID()
- SQLSERVER使用 SELECT SCOPE_IDENTITY()
- CLOUDSCAPE使用 VALUES IDENTITY_VAL_LOCAL()
- DERBY使用 VALUES IDENTITY_VAL_LOCAL()
- HSQLDB使用 CALL IDENTITY()
- SYBASE使用 SELECT @@IDENTITY
- DB2_MF使用 SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1
- INFORMIX使用 select dbinfo('sqlca.sqlerrd1') from systables where tabid=1

---

做一个有底线的博客主