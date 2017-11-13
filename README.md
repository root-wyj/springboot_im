# springboot_im
使用springboot开发的一个极其简易的可以聊天或者留言的web。主要联系springboot相关知识和长连接。

ps: git常用命令
- git init
- git add .|file
- git commit -m 'commit log'
- git push -u origin master
- git pull [origin master --allow-unrelated-histories]
- git remote add origin https://github.com/root-wyj/springboot_im.git

另外，git如果是第一次使用的话，需要认证身份，通过下面的步骤：
1. git config --global user.name "yourname"
2. git config --global user.email "youemail"
3. 生成秘钥： ssh-keygen -t rsa -C "youemail"
4. 之后就是让你输入连接杆github时的密码，密码确认密码，可直接3个回车略过，表示密码为空
5. 到github上添加秘钥 中的公钥

### springboot 学习教程
基础教程参考的是：[翟永超的springboot教程专栏](http://blog.didispace.com/categories/Spring-Boot/)，里面还附有git地址。<br>
基础提高教程可以参考：[zheting的springboot干货系列](http://www.cnblogs.com/zheting/category/966890.html)。<br>
包括：
- [（一）优雅的入门篇](http://www.cnblogs.com/zheting/p/6707032.html)(nice)
- [（二）配置文件解析](http://www.cnblogs.com/zheting/p/6707036.html)（nice）
- [（三）启动原理解析](http://www.cnblogs.com/zheting/p/6707035.html)
- [（四）Thymeleaf篇](http://www.cnblogs.com/zheting/p/6707037.html)
- [（六）静态资源和拦截器处理](http://www.cnblogs.com/zheting/p/6707040.html)
- [（七）默认日志框架配置](http://www.cnblogs.com/zheting/p/6707041.html)
- [（八）数据存储篇-SQL关系型数据库之JdbcTemplate的使用](http://www.cnblogs.com/zheting/p/6707042.html)

<br>
[Spring Boot加载配置文件](https://www.cnblogs.com/moonandstar08/p/7368292.html)
[springboot打包发布教程](https://www.cnblogs.com/honger/p/6886017.html) <br>
[Spring Boot干货系列：常用属性汇总](http://tengj.top/2017/02/28/springbootconfig/)


阿里druid 数据库连接池与tomcat-jdbc连接池比较：http://www.cnblogs.com/barrywxx/p/6343303.html。
> 结论是：druid在连接次数多了之后，比如上万，平均时间相比于tomcat-jdbc更稳定一点，用时少一点。如果连接次数没有很多的话，tomcat-jdbc用时更少。

druid语法可以参考 [Druid连接池-阿里巴巴开源JDBC组件](http://blog.csdn.net/chenleixing/article/details/45003753)

[关于Spring-boot的debug调试](https://www.2cto.com/kf/201708/668239.html)

### springboot 中踩过的坑

#### @Value


### netty-socketio 学习资源
初级教程，也是[源码git项目](https://github.com/mrniko/netty-socketio)推荐的[demo](https://github.com/mrniko/netty-socketio-demo)<br>
因为没有guide，所以只能多看项目，一下是在网上参考的一些项目，给了我很大的帮助：
- [Spring Boot实战之netty-socketio实现简单聊天室(给指定用户推送消息)](http://blog.csdn.net/sun_t89/article/details/52060946)
- [git 上的一个用了分布式和分布式锁的 NettySocketServer](https://github.com/wangnamu/NettySocketioServer) [该类MySocketIOServer](https://github.com/wangnamu/NettySocketioServer/blob/master/NettySocketioServer/src/main/java/com/ufo/NettySocketioServer/MySocketIOServer.java)是比较重要的一个类
- [Spring Boot实战之netty-socketio实现简单聊天室](http://blog.csdn.net/sun_t89/article/details/52060946)

#### 基本使用
这里总结一下NettySocketIO的基本使用，只是说一下大概流程，具体细节实现看代码：

1. 配置`com.corundumstudio.socketio.Configuration` 并实例化 `SocketIOServer server = new SocketIOServer(config)`。
2. 通过`com.corundumstudio.socketio.annotation.OnConnect` 等注解 来实现监听socket连接、断开、接收消息等。然后通过依赖注入 `new SpringAnnotationScanner(SocketIOServer socketIOServer)` ，通过该类来扫描`@OnConnect`, `OnEvent`等注解，注册正真的监听器。
3. 当然了，也可以通过 `Configuration.setXxxListener` 和 `SocketIOServer.addEventListener` 来注册监听器，算是2步骤的扩展。
4. 最后客户端就可以通过 `io.connect('http://localhost:9090?token='+userName);` 来连接到socket，然后就可以通信了。因为NettySocketIO是通过集成`socket.io`这个库来实现socket的，所以客户端语言不限，但是必须也要使用socketio的库，[socketio官网](https://socket.io/)

#### 理解NettySocketIO server框架
NettySocketIO是一个集成了Netty服务器和socketio的，很方便实现长连接的框架。<br><br>

Netty服务器是一个https://www.zhihu.com/question/24322387
http://blog.csdn.net/z69183787/article/details/52505249
http://blog.csdn.net/z69183787/article/details/52505163
NettySocketIO是一个开源框架，非要说什么官网的话 怕就是上面说道的他的git地址了，



### 炸金花游戏逻辑
[扎金花大小比较算法(Java版)](http://blog.csdn.net/dobuy/article/details/31521609)
