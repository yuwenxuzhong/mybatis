echo编辑整理，欢迎转载，转载请声明文章来源。欢迎添加echo微信(微信号：t2421499075)交流学习。 百战不败，依不自称常胜，百败不颓，依能奋力前行。——这才是真正的堪称强大！！！

---

> 在我们应用的MyBatis中提供两种缓存，一种是一级缓存，一种是二级缓存。一级缓存是默认会启用的，并且不能控制，因此很少会提到。二级缓存相对用的会多一些，但是这种缓存也有缺点，比如不能共享等。本章主要用于理解和应用MyBatis的缓存机制。

## MyBatis缓存

### 一级缓存
先来看一个实例，了解一下MyBatis的一级缓存的体现
```java
package com.echo.springmybatis.test;

import com.echo.springmybatis.mapper.SysUserMapper;
import com.echo.springmybatis.model.SysUserPo;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * @author XLecho
 * Date 2019/10/8 0008
 * Time 19:23
 */
public class CacheTest extends BaseMapperTest{
    @Test
    public void testL1Cache(){
        // 获取SqlSession
        SqlSession sqlSession = getSqlSession();
        SysUserPo sysUserPo = null;
        // 获取sysUserMapper接口
        SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
        try{
            // 调用selectById方法，查询id=1的用户
            sysUserPo = sysUserMapper.selectById(1L);
            System.out.println("这是sysUserPo没有修改值之前的输出: " + sysUserPo);
            // 对当前获取的对象重新赋值
            sysUserPo.setUserName("New Echo");
            // 再次查询获取id相同的用户
            SysUserPo sysUserPo1 = sysUserMapper.selectById(1L);
            // 虽然没有更新数据库，但是这个用户和sysUserPo重新复制的名字相同
            System.out.println("这是sysUserPo修改值之后的输出: " + sysUserPo);
            System.out.println("这次输出的是sysUserPo1: " + sysUserPo1);
        }finally {
            sqlSession.close();
        }
    }
}

```

输出结果如下：
```
DEBUG [main] - Opening JDBC Connection
DEBUG [main] - Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@31206beb]
DEBUG [main] - ==>  Preparing: select id, user_name, user_password, user_email, user_info, head_img, create_time from sys_user where id = ? 
DEBUG [main] - ==> Parameters: 1(Long)
DEBUG [main] - <==      Total: 1
这是sysUserPo没有修改值之前的输出: SysUserPo(id=1, userName=admin, userPassword=123456, userEmail=admin@mybatis.tk, userInfo=管理员, headImg=/img/test.jpg, createTime=Wed Sep 25 10:20:20 CST 2019, sysRolePo=null)
这是sysUserPo修改值之后的输出: SysUserPo(id=1, userName=New Echo, userPassword=123456, userEmail=admin@mybatis.tk, userInfo=管理员, headImg=/img/test.jpg, createTime=Wed Sep 25 10:20:20 CST 2019, sysRolePo=null)
这次输出的是sysUserPo1: SysUserPo(id=1, userName=New Echo, userPassword=123456, userEmail=admin@mybatis.tk, userInfo=管理员, headImg=/img/test.jpg, createTime=Wed Sep 25 10:20:20 CST 2019, sysRolePo=null)
DEBUG [main] - Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@31206beb]
DEBUG [main] - Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@31206beb]
```

- 总共执行了一次查询，第一次打印我们可以看到userName=admin，后面的两次打印都是New Echo。最关键的是上面的输出信息里面查询只进行了一次，后面的打印对象信息并没不是通过sysUserMapper.selectById(1L);查询得来的，而是通过缓存得到的。之所以这样就是因为MyBatis的一级缓存。

* 注意上面这种操作也可能在我们开发中遇到，很有可能由于一级缓存的问题，我们将值进行修改了继续使用对象，造成数据不一致的问题。

#### 一级缓存的范围是多大？
我们在上面的测试代码里面加上如下代码：
```java
// ************************ 对比打印结果我们发现两个结果一模一样，并没有更新。开启新会话 ********************
sqlSession = getSqlSession();
try {
    // 获取sysUserMapper接口
    sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
    // 调用selectById方法，查询id=1的用户
    SysUserPo sysUserPo2 = sysUserMapper.selectById(1L);
    System.out.println("查询之后立马输出sysUserPo2: " + sysUserPo2);
    sysUserMapper.deleteById(2L);
    System.out.println("做完删除动作之后再次输出sysUserPo2: " + sysUserPo2);
    // 获取id为1的用户
    SysUserPo sysUserPo3 = sysUserMapper.selectById(1L);
    System.out.println("再次查询输出sysUserPo2: " + sysUserPo2);
    System.out.println("这次输出的是sysUserPo3: " + sysUserPo3);
}finally {
    sqlSession.close();
}
```

执行结果如下：
```
DEBUG [main] - Opening JDBC Connection
DEBUG [main] - Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@27912e3]
DEBUG [main] - ==>  Preparing: select id, user_name, user_password, user_email, user_info, head_img, create_time from sys_user where id = ? 
DEBUG [main] - ==> Parameters: 1(Long)
DEBUG [main] - <==      Total: 1
Disconnected from the target VM, address: '127.0.0.1:64082', transport: 'socket'
这次输出的是sysUserPo: SysUserPo(id=1, userName=New Echo, userPassword=123456, userEmail=admin@mybatis.tk, userInfo=管理员, headImg=/img/test.jpg, createTime=Wed Sep 25 10:20:20 CST 2019, sysRolePo=null)
这次输出的是sysUserPo1: SysUserPo(id=1, userName=New Echo, userPassword=123456, userEmail=admin@mybatis.tk, userInfo=管理员, headImg=/img/test.jpg, createTime=Wed Sep 25 10:20:20 CST 2019, sysRolePo=null)
DEBUG [main] - Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@27912e3]
DEBUG [main] - Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@27912e3]
SysUserPo(id=1, userName=New Echo, userPassword=123456, userEmail=admin@mybatis.tk, userInfo=管理员, headImg=/img/test.jpg, createTime=Wed Sep 25 10:20:20 CST 2019, sysRolePo=null)

```

- 从执行结果过中我们可以看到，但我们第一个SqlSession结束之后，我们使用sysUserMapper.selectById(1L);查询立马就出现了sql查询语句，并不是继续走我们的一级缓存。这里可以很好的证明一个问题，那就是我们的一级缓存的作用域是一个会话。当我们的sysUserMapper.deleteById(2L);语句进行了之后我们再次执行查询语句发现，查询语句再一次出现了。在一个会话当中，如果出现增删改的操作就会将我们的一级缓存进行清空，需要重新查询。

### 不使用一级缓存
以上面的测试为例，我们可以在xml中使用它的一个属性flushCache，这个属性就是用来开关一级缓存的。如果不想使用一级缓存，那么我们可以做如下设置：
```xml
<select id="selectById" flushCache="true" resultMap="userMap">
    select
    <include refid="Base_Column_List"/>
    from sys_user where id = #{id}
</select>
```

> 设置了该属性之后，每一次查询都会走数据库

### 二级缓存
二级缓存的配置也相对比较简单，如果我们要想我们项目中的SysUserMapper能够使用缓存只需要在xml中添加<cache/>这一句简单的配置即可，因为MyBatis是默认开启二级缓存的，使用就只需要这配置。配置位置如下：
```xml
<mapper namespace="com.echo.springmybatis.mapper.SysUserMapper">
    <cache/>
    ……
</mapper>
```
> cache也有很多属性，如：eviction、flushInterval、size、readOnly。而且二级缓存使用注解也是相当简单，只需要在dao层接口上添加@CacheNamespace即可。

二级缓存也是可以手动设置的，它有一个属性cacheEnabled，这个参数是二级缓存的全局开发，默认值是true，如果配置成为false，后面配置其他的都不会在生效。该配置是需要配置在mybatis-config.xml中的，详情如下：
```xml
<settings>
    <setting name="cacheEnabled" value="true"/>
    ……
</settings>
```

- 对我们的SysUserPo实体类进行一下修改，体验二级缓存
> 注意如果使用二级缓存，我们需要让对象实现序列化，不然拿到的值mybatis会默认进行序列化，造成误差

```java
// 在rolePo上加上Serializable
public class SysUserPo implements Serializable {

// 编写一个测试示例
@Test
public void testL2Cache(){
    // 获取SqlSession
    SqlSession sqlSession = getSqlSession();
    SysUserPo sysUserPo = null;
    try{
        // 获取sysUserMapper接口
        SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
        // 查询用户id为1的用户
        sysUserPo = sysUserMapper.selectById(1L);
        System.out.println("第一查询之后，马上输出sysUserPo: " + sysUserPo);
        sysUserPo.setUserName("echo");
        // 修改对象之后，再次查询
        SysUserPo sysUserPo1 = sysUserMapper.selectById(1L);
        System.out.println("第二查询之后，马上输出sysUserPo1: " + sysUserPo1);
    }finally {
        sqlSession.close();
    }

    System.out.println("-----------------------------------------");
    sqlSession = getSqlSession();
    try {
        // 获取sysUserMapper接口
        SysUserMapper sysUserMapper = sqlSession.getMapper(SysUserMapper.class);
        // 查询用户id为1的用户
        SysUserPo sysUserPo2 = sysUserMapper.selectById(1L);
        System.out.println("第二个会话查询之后，马上输出sysUserPo2: " + sysUserPo2);
        sysUserPo2.setUserName("echo");
        // 修改对象之后，再次查询
        SysUserPo sysUserPo3 = sysUserMapper.selectById(1L);
        System.out.println("第二个会话再次查询之后，马上输出sysUserPo3: " + sysUserPo3);
    } finally {
        sqlSession.close();
    }
}
```

> 建议在开启一级缓存的情况下分析一次，搞定之后，在关闭一级缓存试试。

运行输出结果如下：
```
DEBUG [main] - Opening JDBC Connection
DEBUG [main] - Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@10b48321]
DEBUG [main] - ==>  Preparing: select id, user_name, user_password, user_email, user_info, head_img, create_time from sys_user where id = ? 
DEBUG [main] - ==> Parameters: 1(Long)
DEBUG [main] - <==      Total: 1
第一查询之后，马上输出sysUserPo: SysUserPo(id=1, userName=admin, userPassword=123456, userEmail=admin@mybatis.tk, userInfo=管理员, headImg=/img/test.jpg, createTime=Wed Sep 25 10:20:20 CST 2019, sysRolePo=null)
DEBUG [main] - Cache Hit Ratio [com.echo.springmybatis.mapper.SysUserMapper]: 0.0
第二查询之后，马上输出sysUserPo1: SysUserPo(id=1, userName=echo, userPassword=123456, userEmail=admin@mybatis.tk, userInfo=管理员, headImg=/img/test.jpg, createTime=Wed Sep 25 10:20:20 CST 2019, sysRolePo=null)
DEBUG [main] - Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@10b48321]
DEBUG [main] - Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@10b48321]
-----------------------------------------
DEBUG [main] - Cache Hit Ratio [com.echo.springmybatis.mapper.SysUserMapper]: 0.3333333333333333
第二个会话查询之后，马上输出sysUserPo2: SysUserPo(id=1, userName=echo, userPassword=123456, userEmail=admin@mybatis.tk, userInfo=管理员, headImg=/img/test.jpg, createTime=Wed Sep 25 10:20:20 CST 2019, sysRolePo=null)
DEBUG [main] - Cache Hit Ratio [com.echo.springmybatis.mapper.SysUserMapper]: 0.5
第二个会话再次查询之后，马上输出sysUserPo3: SysUserPo(id=1, userName=echo, userPassword=123456, userEmail=admin@mybatis.tk, userInfo=管理员, headImg=/img/test.jpg, createTime=Wed Sep 25 10:20:20 CST 2019, sysRolePo=null)
```

观察二级缓存和一级缓存稍微有一点不同，这里最关键的是要缓存的命中率，命中了缓存就代表使用到了缓存，比如上面的输出里面Cache Hit Ratio [com.echo.springmybatis.mapper.SysUserMapper]: 0.5，它代表的意思是总共进行了两次查询，使用了一次缓存，所以命中缓存的命中缓存率为0.5

- 仔细观看我们发现开启二级缓存之后，我们的测试输出语句其实只是输出了一次sql语句。第一个查询打印sysUserPo时我们发现紧接着就打印了Cache Hit Ratio为0.0。代表这一次并没有使用到缓存，这里其实还在对二级缓存进行设置值。当我们修改了查询到的对象之后，我们再次查询也并没有出现sql语句，这里使用的是一级缓存，所以缓存的命中率还是0。但是总共进行了两次查询。
- 当我们关闭了第一个会话，开始第二个会话，进行查询之后，我们看到了Cache Hit Ratio变成了0.33333，原因就是这是第三次查询，并且命中了缓存，3次中了一次所以0.333，但是当我们再一次修改对象并查询之后，发现这一次再次命中了缓存，所以命中率变成了0.5。

> 从上面一级缓存和二级缓存的应用中我们可以看出来，缓存这种机制对于加快查询速度是有一定的效果的，前提就是对于数据的实时性要求不高，而且数据不需要共享的情况下可以使用。同时对于缓存的操作也要注意，比如不是特别了解整体的缓存流程，或者缓存生效环节，如果使用缓存也有可能造成数据混乱。

---
做一个有底线的博客主：项目coding地址：https://coding.net/u/xlsorry/p/springmybatis/git