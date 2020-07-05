xlecho编辑整理，欢迎转载，转载请声明文章来源。欢迎添加echo微信(微信号：t2421499075)交流学习。 百战不败，依不自称常胜，百败不颓，依能奋力前行。——这才是真正的堪称强大！！！

---

> MyBatis中like有两种写法，但是优劣并不明显，都可以选用

## MyBatis中两种实现方式：
- concat
- bind

在开发中比较常见的写法就是使用concat示例如下
### concat
```java
List<SysUserPo> selectByUser2(SysUserPo sysUserPo);

<select id="selectByUser2" resultType="com.echo.springmybatis.model.SysUserPo">
    select
    id, user_name userName, user_password userPassword,
    user_email userEmail, user_info userInfo, head_img headImg,
    create_time createTime
    from sys_user
    <where>
        <if test="userName != null and userName != ''">
            and user_name like concat('%', #{userName}, '%')
        </if>
        <if test="userEmail !='' and userEmail != null">
            and user_email = #{userEmail}
        </if>
    </where>
</select>
```

> 可以从实例中看到concat拼接可以用多个参数，直接使用concat('%', #{userName}, '%')

使用bind实现like关键字的拼接
### bind
```
List<SysUserPo> selectByUser3(SysUserPo sysUserPo);

<select id="selectByUser3" resultType="com.echo.springmybatis.model.SysUserPo">
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
> bind拼接类似于直接使用string凭借，使用的是“”进行的拼接。

---
做一个有底线的博客主：项目coding地址：https://coding.net/u/xlsorry/p/springmybatis/git