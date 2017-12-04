# springboot_im
使用springboot开发的一个极其简易的可以聊天或者留言的web。主要联系springboot相关知识和长连接。

接下来的任务<br>
1. 简单的断线重连。
2. 完成1之后在完善客户端的返回信息，然后看看是否需要把发消息的单独提出来或者整合到RoomContext里面
3. 增加上redis缓存，room的和user的
4. 看是否需要room连接池和room消息队列<可以延后再延后>



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

<br><br>
`git添加里程碑--tag`<br>
- `git commit --allow-empty -m "blank commit for annotated tag test."` 创建一个空白提交
- `git tag -m '创建里程碑，初步走通游戏流程，并且可以运行！！！' tag_wyj_complete_game_1.0` 添加里程碑
- `git tag -l -n1`, `git describe` 查看里程碑信息
- `cat .git/refs/tags/tagname` 查看里程碑
- `git tag -d tagname` 删除里程碑
- `git tag tagname ec3edf7` 如果发现删除错误，通过这句命令补救。表示将此tag指向某个commit
- `git push origin :mytag2` 远程删除tag
<br>更加详细的里程碑内容，请参考：[Git学习7：Git中的里程碑](http://blog.csdn.net/u011116672/article/details/51277341)

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
[Spring Boot加载配置文件](https://www.cnblogs.com/moonandstar08/p/7368292.html)<br>
[springboot打包发布教程](https://www.cnblogs.com/honger/p/6886017.html) <br>
java 后台运行 `nohup java -jar test.jar &` <br>
[Spring Boot干货系列：常用属性汇总](http://tengj.top/2017/02/28/springbootconfig/)

<br><br>
Spring-jpa学习
- [Spring Boot系列(五)：spring data jpa的使用](https://zhuanlan.zhihu.com/p/25000309)

<br><br>
阿里druid 数据库连接池与tomcat-jdbc连接池比较：http://www.cnblogs.com/barrywxx/p/6343303.html。
> 结论是：druid在连接次数多了之后，比如上万，平均时间相比于tomcat-jdbc更稳定一点，用时少一点。如果连接次数没有很多的话，tomcat-jdbc用时更少。

druid语法可以参考 [Druid连接池-阿里巴巴开源JDBC组件](http://blog.csdn.net/chenleixing/article/details/45003753)

[关于Spring-boot的debug调试](https://www.2cto.com/kf/201708/668239.html)

### springboot 中踩过的坑

#### @Value
之前采用这种方式注入，然后该类里面的成员总是为null，我现在只想说， 这TMD 不是废话么！！！能TM有值么！！ 对象你都实例化完成了，注入到系统中了，你还让系统怎么给这个对象赋值，使用@Component完成注入(好像这种认识并不正确)<br>

```
@Bean
public NettySocketServer socketServer() {
	return new NettySocketServer();
}

```
<br>

后来发现了问题的真正所在 -> 我tm在构造方法中使用成员变量  还tm问 为什么一直为空，当然tmd为空了，对象还没初始化完呢，怎么能初始化里面的成员变量，真tmd愚啊，后来使用了`@PostConstruct`注解 解决了该问题 另外一个是`@PreDestroy`



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

Netty服务器是一个使用了java `nio`-非阻塞流来处理http请求的，相比于tomcat，tomcat是一个线程处理一个请求，当这个请求比较慢的时候（如网络状态不好）这个线程也会阻塞，直到等到客户端数据，而Netty使用的同步非阻塞IO开启一个线程就可以处理多个请求，所以Netty能够支持高并发，可达百万级。而且Netty减少了拷贝，支持多种协议包括websocket。<br>

> Netty采用的是 `NIO+Reactor` 的线程模型。

更多了解Netty、NIO、Reactor线程模型的，可以参考下面的文章：<br>
- [Netty的作用与理解](https://www.zhihu.com/question/24322387)
- [NIO与IO详细比较（有代码和实例数据）](http://blog.csdn.net/sean417/article/details/69817690)
- [Netty实现原理解析 -- Netty实现原理浅析](http://www.importnew.com/15656.html)
- [Netty源码解读（四）Netty与Reactor模式](http://ifeve.com/netty-reactor-4/)
- [netty与websocket通信demo](http://blog.csdn.net/z69183787/article/details/52505163)
- [Netty-WebSocket长连接推送服务](http://blog.csdn.net/z69183787/article/details/52505249)
- [NIO与IO详细比较（有代码和实例数据）](http://blog.csdn.net/sean417/article/details/69817690)
- [例子说明 -- 同步，异步，阻塞，非阻塞，BIO，AIO，NIO 理解]()
- [例子说明阻塞非阻塞 同步异步](http://www.jianshu.com/p/e9c6690c0737)
- [详细介绍 -- 阻塞和非阻塞，同步和异步](https://www.cnblogs.com/George1994/p/6702084.html)
- [IO - 同步，异步，阻塞，非阻塞 ](http://blog.csdn.net/historyasamirror/article/details/5778378)
- [高性能IO设计 & Java NIO & 同步/异步 阻塞/非阻塞 Reactor/Proactor](https://www.cnblogs.com/charlesblc/p/6072827.html)
- [JAVA NIO 简单介绍](http://www.cnblogs.com/leehongee/p/3323840.html)
- [我认为的可能是在墙外的好东西](https://code.google.com/p/socketio-netty/downloads/detail?name=chat_server.zip&can=2&q=#makechanges)
- [Netty 官网guide文档](http://netty.io/wiki/user-guide-for-4.x.html)
<br><br>
NettySocketIO是一个开源框架，非要说什么官网的话 怕就是上面说到的他的git地址了。而且自己去找NettySocketIO的使用DEMO也是少的可怜，文档更是没有，所以只能自己去研究了。博主通过研究NettySocketIO源码，总结以下：
- **`Namespace`** : `Namespace implements SocketIONamespace`。而且也是`SocketIONamespace`的唯一实现，SocketIOServer中声明了它的实例 `mainNamespace`，里面保存了 所有的监听器、所有的客户端、客户端与Room的对应关系等数据，可以说他保存着SocketServer的用户所有信息。Namespace都会有一个名字，新创建一个Server的时候，都会默认创建一个名字为`""`的Namespace。
- **`NamespaceHub`** : `SocketIOServer`中有它的一个实例，叫`namespaceHub`。保存了该server的所有`Namespace`（虽然现在还不知道第二个create第二个Namespace用来干什么）和 该Server的配置
- **room** : 房间。`NettySocketIO`已经提供了房间的功能。可以通过String 类型的房间名来给所有在该房间里的所有用户发送消息。可以通过 `socketIOClient.joinRoom("room1");`来加入房间，也可以通过`SocketIOServer.getNamespace(xx).joinRoom()`方法加入房间。房间信息，主要就保存在`Namespace`的`roomClients`和`clientRooms`中。默认的所有用户都会进入 Namespace.DEFAULT_NAME("")，可以理解为是大厅房间。
- **`BroadcastOperations`** : BroadcastOperations 就是一个广播器，里面保存了SocketIoClient的列表和StoreFactory（暂时认为是一个发布消息的类），所以如果想给某一类用户发消息，就可以把这类用户放入到这个类的实例中，然后一起发送。给某个房间的所有用户发信息`server.getRoomOperations("roomName").sendEvent()`


### 炸金花游戏逻辑
[参考网上的状态模式](http://blog.csdn.net/hguisu/article/details/7557252)<br>
[扎金花大小比较算法(Java版)](http://blog.csdn.net/dobuy/article/details/31521609)<br>

初步完成整个游戏逻辑流程，没有页面，只有一个粗糙的按钮界面(baseUrl/static/index2.html)，下面是后台服务器的日志：
![游戏流程后台日志](https://github.com/root-wyj/springboot_im/blob/master/img/display_img_1.png)

### 断线重连逻辑
经过慎重仔细的思考，根据现在的游戏逻辑与信息存储模型，最大的问题其实就是当这个用户再连接回来的时候，不能根据已有的信息回溯到原来的所有信息，并建立连接（其实一般都会遇到这种问题）。 网上有的建议说当用户第一次建立房间的时候维护一个在服务器和本地都不会变的client_id，这样，当用户再次连接的时候，就可以根据此id来检查服务器有没有已经进入的房间信息，然后达到重连的效果。<br>

其实，这可以说是一种解决办法，因为它能解决现在遇到的问题，但是总感觉这种实现怪怪的，怪就可能有他的不合理之处，或者说和现实中的处理流程不一样（就像我在开发过程对总押注的计算，和对最后结果的处理，虽然一开始都得到了正确的结果，但是因为没有按照现实中的流程来计算，按照本来的模型来存储，导致后来简单的修改就导致了结果计算不正确），虽然结果一样，但是在未来的不断修改增加的需求过程中，总会出现问题的。

最后，我决定使用一个掉线用户池，就是说如果服务器发现某个用户掉线了，则把相关必要信息存储在一个掉线用户池中，当一个用户连接上来的时候，先根据id检查该池中是否有该用户，如果有则能找到原来所有用户的信息，当然数据的恢复也就是断线重连也就很简单了。
