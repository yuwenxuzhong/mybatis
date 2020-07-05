xlecho编辑整理，欢迎转载，转载请声明文章来源。欢迎添加echo微信(微信号：t2421499075)交流学习。 百战不败，依不自称常胜，百败不颓，依能奋力前行。——这才是真正的堪称强大！！

---

### 在项目中的mybatis-config.mxl中配置了如下配置
```xml
<mappers>
    <package name="com.echo.springmybatis.mapper"/>
</mappers>
```

### 项目结构如下
![项目结构](https://img-blog.csdnimg.cn/20190926154625253.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hsZWNobw==,size_16,color_FFFFFF,t_70)

### 启动测试类CountryMapperTest报如下错误
```
org.apache.ibatis.exceptions.PersistenceException: 
### Error querying database.  Cause: java.lang.IllegalArgumentException: Mapped Statements collection does not contain value for com.echo.springmybatis.sql.mapper.CountryMapper.selectAll
### Cause: java.lang.IllegalArgumentException: Mapped Statements collection does not contain value for com.echo.springmybatis.sql.mapper.CountryMapper.selectAll

	at org.apache.ibatis.exceptions.ExceptionFactory.wrapException(ExceptionFactory.java:30)
	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:150)
	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:141)
	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:136)
	at com.echo.springmybatis.test.CountryMapperTest.testSelectAll(CountryMapperTest.java:20)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
Caused by: java.lang.IllegalArgumentException: Mapped Statements collection does not contain value for com.echo.springmybatis.sql.mapper.CountryMapper.selectAll
	at org.apache.ibatis.session.Configuration$StrictMap.get(Configuration.java:888)
	at org.apache.ibatis.session.Configuration.getMappedStatement(Configuration.java:721)
	at org.apache.ibatis.session.Configuration.getMappedStatement(Configuration.java:714)
	at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:147)
	... 26 more
```

### 引起原因
这个错误的起因是配置mappers的时候出现的错误，没有注意到mappers中配置package他的特性“必须保证接口名（例如CountryMapper）和xml名（CountryMapper.xml）相同，还必须在同一个包中。”。我的接口和xml明显不在同一个地方。

### 解决办法
- 将对应的xml文件放到对应的接口文件下面（还会报错，因为IDEA maven项目默认不会把src下除java文件外的文件打包到classes文件夹下，所以我们还要第二步配置）
- pom文件中加入打包其他文件的配置

pom文件配置如下
```xml
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.xml</include>
            </includes>
            <!--默认是true-->
            <!--<filtering>true</filtering>-->
        </resource>
    </resources>
</build>
```

> 再次启动测试文件，发现已经能够成功运行了
