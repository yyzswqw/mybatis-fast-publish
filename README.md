#  mybatis-fast-publish
mybatis-fast-publish是整合mybatis与spring boot的轻量级快速发布接口工具。  
大部分分析需求只是需要写好SQL即可，该工具可以在配置好 **动态SQL** (mybatis动态SQL)后，调用相关接口即可返回结果，不需要在程序中另外再写控制层、服务层以及数据访问层。

##  主要功能
- **快速发布**：预先存储SQL，支持mybatis **动态SQL** ,通过接口传递参数与要执行的SQL或者code,即可返回结果。  
##  如何构建
- mybatis-fast-publish使用 Maven 来构建,确保安装了 Maven ，将源码下载、执行命令：```mvn install```安装到本地，即可在其他项目中引入依赖。  
##  如何使用
1. 在spring boot配置文件中配置数据源连接信息。
2. mybatis-fast-publish提供```mysql```与```与oracle```存储动态SQL,表结构见目录```/src/resources/mysql.sql(oracle.sql)```，创建表```sql_fragment```。
3. 在spring环境中注入```DBSqlFragmentProducer```  
```java
   @Bean
   public SqlFragmentProducer sqlFragmentProducer(){
       return new DBSqlFragmentProducer();
   }
```
默认情况下，sql提供器为：```DefaultSqlFragmentProducer```,存储引擎为```mysql```,若为```oracle```时，需配置```fastpublish.datasource.driver-type```为```oracle```。  
4. 启动spring boot项目。访问以下链接：
>- ```/fragment/add```:添加动态sql到表```sql_fragment```;
>- ```/fragment/update-by-id```:通过主键id修改表```sql_fragment```;
>- ```/fragment/update-by-code```:通过code修改表```sql_fragment```;
>- ```/fragment/delete-by-id```:通过主键id删除动态sql;
>- ```/fragment/list```:获取所有表```sql_fragment```记录;
>- ```/fragment/list-page```:分页获取表```sql_fragment```记录;
>- ```/fragment/get-by-id```:通过主键id获取表```sql_fragment```记录;
>- ```/fragment/get-by-code```:通过code获取表```sql_fragment```记录。

    以上链接在BaseFastPublishController中定义。
    
>- ```/fast-publish/test```:传递参数与动态sql片段（特殊字符需要转义），测试相关sql（POST请求,参数为json格式）,适用于查询语句;
>- ```/fast-publish/test```:参数没有嵌套的使用这个GET请求，传递参数与动态sql片段（特殊字符需要转义），测试相关sql,适用于查询语句;
>- ```/fast-publish/execute```:传递参数与表```sql_fragment```中code字段，执行相关sql（POST请求,参数为json格式）,适用于查询语句;
>- ```/fast-publish/execute```:参数没有嵌套的使用这个GET请求，传递参数与表```sql_fragment```中code字段，执行相关sql,适用于查询语句;
>- ```/fast-publish/delete```:删除缓存相关数据;
>- ```/fast-publish/update```:删除缓存相关数据;
>- ```/fast-publish/execute/ret-num```: 传递参数与表```sql_fragment```中code字段，执行相关sql（POST请求,参数为json格式）,适用于增删改语句;
>- ```/fast-publish/execute/ret-num```: 参数没有嵌套的使用这个GET请求，传递参数与表```sql_fragment```中code字段，执行相关sql,适用于增删改语句。

    以上链接在FastPublishController中定义。
