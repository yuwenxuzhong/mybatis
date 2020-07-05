echo编辑整理，欢迎转载，转载请声明文章来源。欢迎添加echo微信(微信号：t2421499075)交流学习。 百战不败，依不自称常胜，百败不颓，依能奋力前行。——这才是真正的堪称强大！！！

---

> 问题背景:只用MyBatis中foreach进行批量插入数据，一次性插入超过一千条的时候MyBatis开始报错。项目使用技术：SpringBoot、MyBatis

### 批量插入碰到的问题：
```java
java.lang.StackOverflowError: null
```

该问题是由于一次性插入数据1w条引起的，具体插入代码如下：
```java
userDao.batchInsert(list);

<insert id="batchInsert" parameterType="java.util.List">
    INSERT INTO USER
    <trim prefix="(" suffix=")" suffixOverrides=",">
        ID, AGE, NAME, EMAIL
    </trim>
    SELECT A.*
    FROM
    (<foreach collection="list" index="index" item="item" separator="UNION ALL">
    SELECT
    sys_guid(), #{user.age}, #{user.name}, #{user.email}
    FROM dual
</foreach>) A
</insert>
```

> 以上的插入代码其实也是一种批量插入的方式，但是他的灵界点并不高，插入数据过多的时候，可能需要我们使用代码在一次分批。当然如果插入数据不超过5000的时候可以直接这么使用

插入1w条数据，发现出现错误，原因是数据量过大，栈内存溢出了。mybatis中直接使用foreach插入数据，就相当于将所有的sql预先拼接到一起，然后一起提交。这本身就是一种批量插入的处理方案，但是达不到我们要求。主要是插入有上限。如果需要更多的数据导入，我们需要更换一种方式来解决这个问题，mybatis中ExecutorType的使用。

### mybatis中ExecutorType的使用
Mybatis内置的ExecutorType有3种，SIMPLE、REUSE、BATCH; 默认的是simple，该模式下它为每个语句的执行创建一个新的预处理语句，单条提交sql；而batch模式重复使用已经预处理的语句，并且批量执行所有更新语句，显然batch性能将更优；但batch模式也有自己的问题，比如在Insert操作时，在事务没有提交之前，是没有办法获取到自增的id，这在某型情形下是不符合业务要求的；

### 插入大量数据的解决方案，使用ExecutorType
为了能够高效，并且解决上述问题，我们使用ExecutorType，并分批插入。代码如下：
```java
//我们使用的是springboot，sqlSessionTemplate是可以自己注入的
@Autowired
private SqlSessionTemplate sqlSessionTemplate;

public void insertExcelData(List<User> list) {
    //如果自动提交设置为true,将无法控制提交的条数，改为最后统一提交，可能导致内存溢出
    SqlSession session = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
    //不自动提交
    try {
        UserDao userDao = session.getMapper(UserDao.class);
        for (int i = 0; i < list.size(); i++) {
            userDao.insert(list.get(i));
            if (i % 400 == 0 || i == list.size() - 1) {
                //手动每400条提交一次，提交后无法回滚
                session.commit();
                //清理缓存，防止溢出
                session.clearCache();
            }
        }
    } catch (Exception e) {
        //没有提交的数据可以回滚
        session.rollback();
    } finally {
        session.close();
    }
}

userDao.insert(User user);

<insert id="insert" parameterType="com.echo.UserPo">
    insert into USER
    (id
    <if test="age != null">
        ,age
    </if>
    <if test="name != null">
        ,name
    </if>
    <if test="email != null">
        ,email
    </if>
    )
    values (
    sys_guid()
    <if test="age != null">
        ,#{age}
    </if>
    <if test="name != null">
        ,#{name}
    </if>
    <if test="email != null">
        ,#{email}
    </if>)
</insert>
```

> 这里采用的是单条插入，直接使用for循环，但是使用ExecutorType.BACTH就相当于手动提交。这也是我们需要的效果，所以我们在循环里面判断了，是否到了第400笔，如果到了第400笔就直接提交，然后清空缓存，防止溢出。这样就有效的实现了批量插入，同时保证溢出问题的不出现

---
做一个有底线的博客主
