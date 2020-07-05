# mybatis

> 该项目主要用于存储mybatis整合springboot与mysql交互的xml

- 使用技术
SpringBoot + MyBatis + JDK1.8

- 开发工具
IDEA

### MyBatis
是一款面向对象和返回集关系的数据层封装，有效的减少jdbc的代码。
- 缺点：
- * 不方便移植，数据库更换需要字段比较麻烦，需要改写sql
- * 对开发人员所写的SQL依赖很强。
- 优点：
- * 手写sql，能够有效的精准把控查询的速度，便于优化查询速度
- * mybatis不会对应用程序或者数据库的现有设计强加任何影响
- * 通过提供DAO层，将业务逻辑和数据访问逻辑分离，使系统的设计更清晰，更易维护，更易单元测试。
- * 保证名称相同，配置好映射关系即可自动映射或者，不配置映射关系，通过配置列名=字段名也可完成自动映射。

### release1.0
编写部门接口，完成mybatis中xml模板的sql方法。方法模板注解如下：
- 不传参数 getAll
- 传入String类型参数，查询单条数据 getOne
- 传入String类型参数，查询多条数据 getYesterday
- 传入Date类型参数，查询多条数据 getYesterdayDate
- 传入TemplateTablePo实体类，插入一条数据 insert
- 传入List<TemplateTablePo>集合，批量插入数据 batchInsert
- 传入TemplateTablePo实体类，批量更新数据 batchUpdate
- 传入List<TemplateTablePo>集合，根据主键有选择的更新数据库表 updateByPrimaryKeySelective
- 传入Map类型参数，根据Map的多条件参数查询对应的数据 getByConditionMap
- 传入Map<String, String>类型参数，根据多条件参数查询对应的数据 getByConditionListMap
- 传入String类型参数，根据ID删除一条数据 deleteById
- 传入List<String>类型参数，根据多个id，批量删除数据 deleteBatch
- 传入List<Map<String, String>>类型参数，根据分组条件，查询对应数据 getByConditionListMap1
- 传入TemplateTablePo实体类，Oracle存在---存在即更新，否则插入方法upsertSelective。

### release2.0
restful接口定义风格

### release3.0
MyBatis自定义关系映射及实现分页查询

