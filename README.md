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


阿里druid 数据库连接池与tomcat-jdbc连接池比较：http://www.cnblogs.com/barrywxx/p/6343303.html。
> 结论是：druid在连接次数多了之后，比如上万，平均时间相比于tomcat-jdbc更稳定一点，用时少一点。如果连接次数没有很多的话，tomcat-jdbc用时更少。

druid语法可以参考 [Druid连接池-阿里巴巴开源JDBC组件](http://blog.csdn.net/chenleixing/article/details/45003753)

[关于Spring-boot的debug调试](https://www.2cto.com/kf/201708/668239.html)

im-长连接
