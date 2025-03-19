git 使用方法：（如果出现fatal: Could not read from remote repository.  Please make sure you have the correct access rights a错误）
先删除远程仓库：git remote remove <name> -> git remote remove backup
再添加	git remote add <name> <url>		->git remote add backup https://backup-github.com/username/repository.git



**将 Log 保存入数据库中。**

         Logback 官方不再直接支持将日志直接写入数据库的功能。继承重写了`DBAppenderBase`的，用于处理日志事件。它绑定日志事件和调用者数据到预编译的SQL语句中，并执行更新操作。

- 绑定日志事件（如时间戳、IP地址、消息、日志级别等）到SQL语句。
- `bindCallerDataWithPreparedStatement()`方法绑定调用者信息（如文件名、类名、方法名和行号）到SQL语句。

       logback提供了一个`Appender`的异步实现类：`AsyncAppender`。使用方法也非常简单，同样也是通过在配置文件中添加`<appender>`标签声明`AsyncAppender`

与@Slf4j配合使用

**采用阻塞队列对于投票状态的倒计时处理。**

在创建投票时会，将投票信息、描述、选项与失效时间放入消息体，`loginToken`放入消息头中，

在通过验证后将用户id传到votes实体类中后将信息添加到数据库中，再传入消息队列与`redis`中，通过DelayQueue的take方法拿取数据，通过将数据传到阻塞队列后使用`CompletableFuture`的异步方法`*runAsync*`修改数据库中的代码。没有持久化保存数据，不行

存入数据库时，先存投票信息，再通过投票内容与创建人id查到投票id，将id保存到选项表中，forEach方法存到数据库，这里没有锁，因为有事务，id也不会查找出问题。

**用 Lock 锁解决多线程环境下的资源易出错的问题。**

在投票操作时进行加锁，效率低。我加锁加的是投票id，确保在同一时间内只有一个线程能够处理特定的投票或选项。这样可以避免多线程同时操作同一资源导致的数据不一致问题。项目中我都是在函数中new的实体类，并在service中加锁。除了这种方式，还可以将bean改为多列。

**页面实现了单点登录，只有登录用户才能进行投票请求**；

使用JWT的方式

1.用户向服务器发送用户名和密码。

2.验证服务器后，相关数据（如用户角色，登录时间等）将保存在当前会话中。

3.服务器向用户返回自定义实体类。

4.用户的每个后续请求都将通过请求头中取出Token传给服务器。确认用户的身份

没有设置过期，只有下次登录才会刷新


**netty制作聊天：**：使用Channel与UserChannel匹配用户，使得他们可以进行点对点传输。

**编程式事务：**

在创建投票的时候使用的是编程式事务

投票时要检查投票项目状态与登录状态，