[TOC]

# GoNettyComponent

### 1.基本功能

1. 以标准JSON数据格式进行 `byte[]` <--> `String` <--> `Object` 的编码与解码
2. 以Java基本类型格式进行 `byte[]` <--> `Object` 的解码与编码
3. 支持自定义协议（ByteBuf、ProtoBuf）
4. Client 与 Server 建立起连接后到断开连接之间，缓存会话，会话具备功能参考Servlet 中的 Session 域
5. Client 与 Server 的消息分发参考Spring MVC 的 RequestMapping
6. Client 与 Server 的消息参数绑定参考 Spring MVC 的RequestParam
7. 支持默认心跳检测与自定义心跳检测
8. 消息发送与接收方式支持 TCP或 UDP协议
9. 具备一定得容错能力与并发能力


### 2.技术分解

1. 使用 spring 核心功能 IOC 和 AOP 作为基础代码，减少代码工作量
2. 基于通讯框架 netty 的二次封装完成TCP/UDP通讯功能
3. 参考 Spring MVC 的消息分发以及参数绑定方式实现该组件的消息分发与参数绑定

### 3.主体结构

GoNettyComponent 的结构主要分为4大组件，分别是：

+ NettyProcessor
+ RequestAccepter
+ RequestDispatcher
+ BusinessHandler。

4大组件由GoNettyComponent 组织串联起来，完成消息的解编码、传递、分发等功能。

![主体结构][1]

### 4.概念解释
|         类型         |                                                          解释                                                         |
|----------------------|-----------------------------------------------------------------------------------------------------------------------|
| Byte[]               | 字节数组，客户端与服务端通讯的数据格式                                                                                |
| Encoder              | 编码器，完成byte[] 到对应model 的转换                                                                                 |
| Decoder              | 解码器，完成 model 到对应的 byte[] 的转换                                                                             |
| Request              | 请求域对象，封装了请求消息，会话（session）消息，以及 Netty 中的Channel 等系列关键对象                                |
| Response             | 响应域对象，封装了请求消息，会话（session）消息，以及 Netty 中的Channel 等系列关键对象                                |
| Session              | 会话域对象，封装了会话信息（一次长连接代表一个会话），以及 Netty 中的Channel 等系列关键对象                           |
| ClientNettyProcessor | 客户端处理器，主要任务是完成与服务端的基础通讯。                                                                      |
| ServerNettyProcessor | 服务端处理器，主要任务是监听端口，完成与客户端的基础通讯。                                                            |
| RequestAccepter      | 请求接收器，消息（byte[]）经过解码器解码转为 Request ，会调用 RequestAccepter ， 在这里完成请求路由规则的数据初始化。 |
| RequestDispatcher    | 请求分发器，根据进行 RequestAccepter 初始化的路由规则进行请求的分发。                                                 |
| BusinessHandler      | 业务处理器，相当于Controller,请求会根据路由规则分发到不同的BusinessHandler 进行业务处理。                             |

[1]: ./images/structure.png "structure.png"