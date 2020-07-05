xlecho编辑整理，欢迎转载，转载请声明文章来源。欢迎添加echo微信(微信号：t2421499075)交流学习。 百战不败，依不自称常胜，百败不颓，依能奋力前行。——这才是真正的堪称强大！！

---

> SpringBoot整合MyBatis最大的特点就是省事，相比于Spring整合MyBatis来讲，省了很多的步骤，并且操作简单，容易弄懂。

项目地址：https://coding.net/u/xlsorry/p/mybatis/git  源码开放下载，里面有很多的操作，可以直接当做模板使用。

### 步骤一:使用IDEA来创建并整合MyBatis
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190915144334108.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hsZWNobw==,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190915144422334.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hsZWNobw==,size_16,color_FFFFFF,t_70)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190915144601257.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hsZWNobw==,size_16,color_FFFFFF,t_70)

### 步骤二：将配置文件改为yml（博主习惯使用yml文件），并设置如下配置
```java
server:
  port: 10080

spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://192.168.222.132:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC&allowMultiQueries=true
    driver-class-name: com.mysql.jdbc.Driver


mybatis:
  mapper-locations: classpath:mapping/*Mapper.xml
  type-aliases-package: com.echo.mybatis.template.mybatistemplate.pojo

logging:
  level:
    com.echo.mybatis.template.mybatistemplate.dao: DEBUG
```

### 步骤三：编写一个Mapper文件并放入配置文件指定的目录
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190915145205755.png)
### 步骤四：使用逆向工具完成一个接口的编写（这里使用的mybatis plus插件），完成之后结构如下
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190915145321374.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hsZWNobw==,size_16,color_FFFFFF,t_70)

### 步骤五：启动测试
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190915145931485.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3hsZWNobw==,size_16,color_FFFFFF,t_70)
该测试的数据和表都写成了SQL，并放入了项目当中，下载项目之后可以看到相关的SQL文件。
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019091515005967.png)
到这里就已经完成了SpringBoot整合Mybatis