> 数据初始化
- DataInit 

        spring实例化bean时，执行init方法，初始化基础数据
        从application.properties读取角色，初始化角色表
        从application.properties读取ADMIN用户数据，初始化用户表
<br>    
               
> 日志重定向到mongodb
- MongoAppender 
- WebLogAspect

        自定义logback的appender，将日志输出流导入mongodb
        mongo数据库连接参数定义在logback-spring.xml
        mongoAppender实现具体的日志输出格式和内容
        WebLogAspect负责对Controller层的方法做切面，将请求参数、返回结果和执行时间输出到mongodb
<br> 
        
> 多数据源
- DataSourceConfig

        从application.properties读取多个数据库连接参数，初始化DataSource
        @Primary指定主数据源
<br>  
        
> Redis
- RedisConfig

        application.properties定义Redis服务器连接参数
        初始化两个RedisTemplate
<br>  
        
> RestApiDoc
- Swagger2Config
  
<br>
        
> 请求拦截器
- LogInterceptor

        通过实现HandlerInterceptor的方式，自定义一个请求拦截器，打印请求路径
<br>  
        
> WebSecurity
- WebSecurityConfig

        通过扩展UsernamePasswordAuthenticationFilter的方式，自定义一个过滤器
            『
                如果为登录请求（请求路径匹配WebSecurityConfig配置的登录页login路径且为POST请求），验证并生成token返回给客户端
                如果非登录请求且cookie里携带token，根据token获取userName，查询用户权限更新到SecurityContext
                                                                                                        』
        对特定路径的请求，需要验证用户登录信息，未登录会跳转配置的登录页，登录成功跳转相应的配置页，登录失败跳转页todo
        用户登录后，用户权限会保存到SecurityContext
        对指定路径的请求，通过前置权限校验的方式增加用户权限控制
<br>  
        
> ResponseBody序列化
- ResponseToJackson   
- ResponseBodyAspect
 
        对Controller层ResponseBody注解修饰的方法，SPRING提供了可扩展的切面，可以自定义返回结果
        实现ResponseBodyAdvice，返回结果使用Jackson序列化，解决序列化时双向依赖无限递归的问题
        support方法定义切面对哪些ResponseBody修饰的方法生效，基于此可以做定制化处理，比如只针对加了某个注解的方法生效
<br>  
        
> ORM
- User Role AccountRecord
    
        ORM框架使用JPA，JPA提供基础API用于CRUD和简单的条件查询操作，同时维护对象间的关联关系，简化查询
        用户表和角色表是多对多关系，用户表和账单流水表是一对多关系
        一对多关系，通过外键维护。多对多关系，通过中间表维护。
        JsonIgnoreProperties注解+jackson序列化，解决双向引用造成序列化无限递归
<br>  
        
> 异常拦截
- MyExceptionHandler

        对Controller层的方法里抛出的特定类型异常，自定义处理逻辑，本质还是基于切面处理

        
        