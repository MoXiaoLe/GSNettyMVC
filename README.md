[TOC]

# MoNetty

### 1.基本功能

TODO

### 2.基本使用

TODO

### 3.主体结构

MoNetty 的结构主要分为4大组件，分别是：

+ NettyProcessor
+ RequestAccepter
+ RequestDispatcher
+ BusinessController

4大组件由GSNettyMVC组织串联起来，完成消息的解编码、传递、分发等功能。

![主体结构][1]
                    
[1]: ./images/structure.png "structure.png"
