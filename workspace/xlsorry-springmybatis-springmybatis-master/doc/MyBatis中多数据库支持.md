xlecho编辑整理，欢迎转载，转载请声明文章来源。欢迎添加echo微信(微信号：t2421499075)交流学习。 百战不败，依不自称常胜，百败不颓，依能奋力前行。——这才是真正的堪称强大！！！

---

#### 注意：该文章只是指明MyBatis怎么匹配多种数据库，而不是多数据源的实现

> 在实际项目开发中可能实现多数据开发，用MyBatis中的databaseIdProvider数据库标签可以直接配置多数据源，使用的时候可以直接在if标签上面根据databaseId来加载不同数据库的数据。

使用多数据源只要有按照如下步骤即可：

### 第一步
在mybatis-config.xml中加入databaseIdProvider配置即可
```xml
<databaseIdProvider type="DB_VENDOR">
    <property name="SQL Server" value="sqlserver"/>
    <property name="DB2" value="db2"/>
    <property name="Oracle" value="oracle"/>
    <property name="MySQL" value="mysql"/>
    <property name="PostgreSQL" value="ppostgresql"/>
    <property name="Derby" value="derby"/>
    <property name="HSQL" value="hsqldb"/>
    <property name="H2" value="h2"/>
</databaseIdProvider>
```

> 由于每个数据厂商对应数据库产品名称不同，这里的DB_VENDOR会通过DatabaseMetaData#getDatabaseProductName()返回的字符串进行设置，通常情况下都会比较长，所以通常会设置别名。如上代码设置了常见厂商的别名

### 第二步
配置了配置文件之后，我们需要对mapper映射文件中的需要加载其他数据库数据的标签进行设置。这里只演示like在MySQL和Oracle中的实现
- MySQL
```xml
<select id="selectByUserToMySQL" resultType="com.echo.springmybatis.model.SysUserPo" databaseId="mysql">
    select
    id, user_name userName, user_password userPassword,
    user_email userEmail, user_info userInfo, head_img headImg,
    create_time createTime
    from sys_user
    <where>
        <if test="userName != null and userName != ''">
            <bind name="userName" value="'%' + userName + '%'"/>
            and user_name like concat('%', #{userName}, '%')
        </if>
        <if test="userEmail !='' and userEmail != null">
            and user_email = #{userEmail}
        </if>
    </where>
</select>
```

- Oracle
```xml
<select id="selectByUserToOracle" resultType="com.echo.springmybatis.model.SysUserPo" databaseId="oracle">
    select
    id, user_name userName, user_password userPassword,
    user_email userEmail, user_info userInfo, head_img headImg,
    create_time createTime
    from sys_user
    <where>
        <if test="userName != null and userName != ''">
            <bind name="userName" value="'%' + userName + '%'"/>
            and user_name like '%'||#{userName}||'%')
        </if>
        <if test="userEmail !='' and userEmail != null">
            and user_email = #{userEmail}
        </if>
    </where>
</select>
```

* 注意以上两种用法要配置对应的数据源，如果要项目中有数据源的切换，这里只需要按照如下写法即可，不需要更改xml代码
```xml
<select id="selectByUserToDatabase" resultType="com.echo.springmybatis.model.SysUserPo">
    select
    id, user_name userName, user_password userPassword,
    user_email userEmail, user_info userInfo, head_img headImg,
    create_time createTime
    from sys_user
    <where>
        <if test="userName != null and userName != ''">
            <if test="_databaseId == 'oracle'">
              and user_name like '%'||#{userName}||'%')
            </if>
            <if test="_databaseId == 'mysql'">
              and user_name like conat('%', #{userName}, '%')
            </if>
        </if>
        <if test="userEmail !='' and userEmail != null">
            and user_email = #{userEmail}
        </if>
    </where>
</select>
```

> 以上代码直接在关键部分使用if添加_databaseId来判断需要查询的是什么数据库，在有数据库切换的时候，能够有效的改善更改xml代码的操作。但是如果数据库切换很少，不建议这么使用，会增加很多冗余代码。

