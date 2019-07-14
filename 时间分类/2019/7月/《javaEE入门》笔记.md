# 《JavaEE》课程笔记

###### author:朱泽聪

###### class:后端一班

###### createTime:2019/7/6

###### 课程目标:能熟悉了解常用的J2EE组件,了解J2EE项目的基本结构与相关配置。在培训课程结束时通过提问能清晰说出课程的要点,以及通过案例常见分析找出案例错误之处。

## 目录

* [J2EE基本概念介绍](#J2EE基本概念介绍)
* [J2EE-WEB应用](#J2EE-WEB应用)
* [J2EE常用组件](#J2EE常用组件)
* [MVC](#MVC)

## J2EE基本概念介绍

### 1.1 J2EE是什么?

J2EE是使用JAVA技术开发企业级应用的工业标准,它是java技术不断适应和促进企业级应用过程中的产物。

它是一个标准,不是一个产品!

### 1.2 J2EE常用技术

JSP,SERVLET,JDBC,JMS,EJB...

### 1.3 常见的J2EE服务器

Tomcat,Jetty,Weblogic,Jboss,WebSphere。

## J2EE-WEB项目

### 2.1 WEB项目结构

![J2EE项目结构图](https://github.com/StrongDwarf/learning-notes/blob/master/public/img/J2EE%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84%E5%9B%BE.png?raw=true "J2EE项目结构图")

### 2.2 web.xml

用于配置web项目的相关Filter,Listener,Servlet及其参数。

## J2EE-常用组件

### 3.1 Servlet

#### Servlet是什么？

Server Applet是java servlet简称,用java编写的服务端程序,主要功能在于交互式浏览和根据数据生成动态的Web内容。

#### Servlet-常见方法

所有的servlet都是继承于HttpServlet这个类,该类提供了一系列的方法供重写,常见方法init,destory,doGet,doPost,service。

各方法作用如下:

* init:Servlet初始化时执行。
* destroy:Servlet销毁时执行。
* doGet:处理表单method为get时候的请求。
* doPost:处理表单method为post时的请求。
* service:用于处理业务逻辑，业务逻辑代码都写在这，当用户访问servlet时，都会调用该函数。


###### 一个ServletDEMO

一个演示DEMO:用于在浏览器中输出hello world。

``` java
// hello.java文件
/**
 * Servlet Demo
 * @author: 朱泽聪
 * @reason: Servlet函数方法演示DEMO
 * @createTime: 2019/7/7
 */
package com.xiaobaicai.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class hello implements Servlet {

    /**
     * @name: destroy
     * @notes: servlet销毁时调用
     * @return: void
     */
    @Override
    public void destroy() {
        System.out.println("destroy");
    }

    /**
     * @name: init
     * @notes: servlet初始化时调用
     * @param: ServletConfig args0 servlet配置文件中的相关属性
     * @return: void
     */
    @Override
    public void init(ServletConfig arg0) throws ServletException {
        System.out.println("init it");
    }

    /**
     * @name: service
     * @notes: service用于处理业务逻辑,业务逻辑代码都写在这
     * @param: ServletResponse res 用于向客户端(浏览器)返回信息
     * @param: ServletRequest req 用于获取客户端(浏览器)的请求信息
     * @return: void
     */
    @Override
    public void service(ServletRequest req, ServletResponse res) {
        System.out.println("service");

        // 打印在浏览器上的信息
        PrintWriter pw = req.getWriter();
        pw.println("hello,world");
    }
}
```

``` xml
<!-- web.xml文件 -->
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" id="WebApp_ID" version="3.0">
    <!-- 实现servlet -->
    <display-name>hello</display-name>
    <!-- servlet部署 -->
    <servlet>
        <!--给servlet取名，任意 -->
        <servlet-name>name</servlet-name>
        <!-- 指明servlet路径，包名.类名 -->
        <servlet-class>com.xiaobaicai.test.hello</servlet-class>
    </servlet>
    <servlet-mapping>
        <!--servlet映射，必须与servlet-name同名 -->
        <servlet-name>name</servlet-name>
        <!--这是浏览器中输入的访问该servlet的url-->
        <url-pattern>/sp</url-pattern>
    </servlet-mapping>
</web-app>
```

#### Servlet-HttpServletRequest

HttpServletRequest用于接收客户端请求的数据,常用API如下:

* getParameter(String name):获取请求中的参数,该参数是由name指定的
* getParameterValues(String name):获取指定名称参数的所有值数组。它适用于一给参数名对应多给值的情况下,如也买你表单下的复选框,多选列表提交的值。
* getParameterMap():返回一给保存了请求消息中的所有参数名和值的Map对象。Map对象的key是字符串类型的参数名,value是这个参数所对应的Object类型的值数组。
* getParameterNames():返回当前请求的所有属性的名字集合赋值。
* getSession():返回和客户端相关的session,如果没有给客户端分配session,则返回null。
* getAttribute(String name):返回name指定的属性值。
* setAttribute():设定指定的值
* getRequestDispatcher().forward():方法的请求转发过程结束后,浏览器地址栏保持初始的URL地址不变。方法在服务器端内部将请求转发给另外一个资源,浏览器只知道发出了请求并得到了响应记过,并不知道服务器程序内部发生了转发行为。

#### Servlet-HttpServletResponse

HttpServletResponse用于向客户端发送数据。常用api有:

* getWriter():服务器对客户端(浏览器)的输出流。
* getWriter().write():向输出流中写数据。
* getWriter().fulsh():把输出流立即发送到应答中。
* getWriter().close():关闭输出流。
* sendRediredt():向浏览器发送一个重定向URL,浏览器会跳转到新的URL中。

### 3.2 JSP

#### JSP是什么

JSP是什么:java service page,J2EE规范,各服务器将JSP编译成一个对应的servlet,此类servlet是不需要配置即可访问的,访问时还是直接访问.jsp, JSP=(Html+java->servlet)

#### JSP-常用内置对象

九大内置对象:

* page:page对象代表当前JSP页面,是当前JSP编译后的Servlet类的对象。相当于this。
* config:标识Servlet配置,类型:ServletConfig,api跟Servlet中的ServletConfig对象是一样的,能获取该servlet的一些配置信息,能够激活区ServletContext。
* application:标志web应用上下文,类型:ServletContext,
* request:请求对象,类型:httpServletRequest
* response:响应对象,类型:httpServletResponse
* session:表示一次会话,在服务器端记录用户状态信息的技术。
* out:输出响应体,类型JspWriter
* exception:表示发送异常对象,类型Throwable,
* pageContext:表示jsp页面上下文(jsp管理者)

四大作用域:

* pageContext:表示当前页面的作用域
* request:请求作用域
* session:会话作用域
* application:应用作用域(多个会话)

###### 四大作用域中的常用方法

* void setAttribute(String name,Object value)
* Object getAttribute(String name,Object value)
* void removeAttribute(String name,Object value)

使用pageContext代理其他三个域对象的功能

* pageContext.setAttribute("x","xx",PageContext.REQUEST_SCOPE)
* pageContext.setAttribute("x","xxx",PageContext.APPLICATION_SCOPE)
* pageContext.setAttribute("x","xxxx",PageContext.SESSION_SCOPE)

### 3.3 Filter

#### 什么是Filter

Filter:过滤器,能对客户的请求进行预先处理,然后再将请求转发给其他web组件。

应用场景:

* 黑名单
* 爬虫重复URL检测
* 代理缓存技术
* 设置通用功能(代理)
* 不文明话语过滤
* 自动登录(登录保存技术)

###### Filter DEMO

DEMO:为所有URL添加一个字符过滤器

``` java
// CharacterFilter.java文件
/**
 * CharacterFilter
 * @notes: 字符格式过滤,继承Filter并覆写一些方法
 * @author: 朱泽聪
 * @createTime: 2019/7/7
 */
package com.xiaobaicai.test;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

public class CharacterFilter implements Filter {
    private String charset;

    @Override
    public void destroy() {

    }

    /**
     * @name: doFilter
     * @notes: 过滤器逻辑
     * @param: ServletRequest req
     * @param: ServletResponse res
     * @param: FilterChain chain 过滤链
     * @return: void
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding(charset);
        res.setCharacterEncoding(charset);
        HttpServletResponse resp = (HttpServletResponse) res;
        chain.doFilter(request, resp);
    }

    /**
     * @name: init
     * @notes: 过滤器初始化时读取配置文件中charset属性值
     * @param: FilterConfig fConfig 过滤器配置文件,从web.xml文件中读取
     * @return: void
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        charset = fConfig.getInitParameter("charset");
    }
}
```

``` xml
<!-- web.xml配置文件 -->
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" version="3.0">
    <display-name></display-name>

    <!-- 注册一个过滤器 -->
    <filter>
        <!-- 过滤器名称 -->
        <filter-name>CharacterFilter</filter-name>
        <!-- 过滤器对应的类 -->
        <filter-class>com.xiaobaicai.test.CharacterFilter</filter-class>
        <!-- 过滤器对应的属性 -->
        <init-param>
            <param-name>charset</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>

    <!-- 将过滤器和指定URL绑定 -->
    <filter-mapping>
        <filter-name>CharacterFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
</web-app>
```

### 3.4 listener

#### 什么是listener

listener:监听器,一般对于某一类请求进行监听,常见在session创建时,或者session数据发生变化的时候,我么进行监听,并记录相应日志,包括但不限定以上用途。

#### 将其编写步骤

* 1,编写一个监听器类去实现监听接口
* 2,覆盖监听器的方法
* 3,在web.xml中进行配置

#### 监听域对象创建于销毁

###### ServletContextListener DEMO

``` java
public class MyServletContextListener implements ServletContextListener {

    @Override
    public void contextDestoryed(ServletContextEvent sce) {
        System.out.println("ServletContextListener销毁了");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        sce.getSource();
        System.out.println("servletContextListenner创建了");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date firstTime = null;
        try {
            firstTime = format.parse("2018-12-10 20:37:46");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            System.out.println("银行计息了。。。。");
            }
        }, firstTime, 24*60*60*1000);
    }
}
```

``` xml
<!-- web.xml文件 -->
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" id="WebApp_ID" version="3.0">
    <listener>
        <listener-class>com.xiaobaicai.test.MyServletContextListener</listener-class>
    </listener>
</web-app>
```

#### 监听域对象属性变化

###### ServletContextAttributeListener DEMO

``` java
/**
 * MyServletContextAttributeListener
 * @notes: 对象属性监听器
 * @author: 朱泽聪
 * @createTime: 2019/7/8
 */
public class MyServletContextAttibuteListener implements ServletContextAttributeListener{

    public void attributeAdded(ServletContextAttributeEvent scab) {
        System.out.println("添加："+scab.getName()+":"+scab.getValue());

    }

    public void attributeRemoved(ServletContextAttributeEvent scab) {
        System.out.println("删除："+scab.getName()+":"+scab.getValue());

    }

    public void attributeReplaced(ServletContextAttributeEvent scab) {
        System.out.println("修改："+scab.getName()+":"+scab.getValue());

    }

}
```

``` xml
<!-- web.xml文件 -->
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd" id="WebApp_ID" version="3.0">
    <listener>
        <listener-class>com.xiaobaicai.test.MyServletContextAttibuteListener</listener-class>
    </listener>
</web-app>
```

``` java
// 测试类
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = this.getServletContext();
        context.setAttribute("name", "lx");
        context.setAttribute("name", "lx2");
        context.removeAttribute("name");
}
```

#### 监听与session中的绑定的对象相关的监听器(对象感知监听器)

###### 与session相关对象状态

* 绑定状态:当对象被放到session域时触发
* 解除状态:对象从session域中移除时触发
* 钝化状态:将session内存中的对象持久化(序列化)到磁盘
* 活化状态:将磁盘上的对象再次恢复到session内存中

###### 对象感知监听器 DEMO

``` java
/**
 * Person
 * @notes: 对象感知器DEMO,对象在session绑定,解绑,活化,钝化
 * @author: 朱泽聪
 * @createTime: 2019/7/8
 */
public class Person implements HttpSessionBindingListener,HttpSessionActivationListener,Serializable {

    private String id;
    private String name;

    public Person(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent hsb) {
        System.out.println("Person被绑定了"+hsb.getName()+hsb.getValue());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent hsb) {
        System.out.println("Person被解绑了");
    }

    @Override
    public void sessionDidActivate(HttpSessionEvent hse) {
        System.out.println("Person被活化了");
    }

    @Override
    public void sessionWillPassivate(HttpSessionEvent hse) {
        System.out.println("Person被钝化了");
    }

}
```

``` java
// 测试类1
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Person person = new Person("name1","lx");
        session.setAttribute("person", person);
        session.removeAttribute("person");

        Person person = new Person("name2","lx");
        session.setAttribute("person2", person);
}
```

``` java
// 测试类2
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Person person = (Person) request.getSession().getAttribute("person2");
        System.out.println(person.getName());
}
```

配置文件:context.xml（放到WebContent下的META-INF目录下）

``` xml
<Context>
    <!-- maxIdleSwap:session中的对象多长时间不使用就钝化(分钟) -->
    <!-- directory:钝化后的对象的文件写到磁盘的哪个目录下  
        配置钝化的对象文件在work/catalina/localhost/钝化文件 -->
    <Manager className="org.apache.catalina.session.PersistentManager" 
            maxIdleSwap="1">
    <Store className="org.apache.catalina.session.FileStore" directory="lxTestActivation" />
    </Manager>
</Context>
```

## MVC

M-V-C:model-view-controller

用一种业务逻辑,模型(用来为视图层和数据持久层准备数据和处理从视图层和数据持久层接收到的数据),界面显示分离的方式组织代码。

MVC强调视图(显示),控制器(逻辑),模型(数据)的分离。

### 最简单的MVC

###### 以下为了便于理解,全代码全部采用JavaScript编写

最简单的MVC如下：

``` js
// mvc架构
const APP = {
    // 模型层,放置模型相关操作
    models : {

    },
    // 视图层,放置视图相关的操作
    views : {

    },
    // 控制器层,放置逻辑相关操作
    controllers : {

    }
}
```

对上面的架构进行扩展,如下:

* 模型层:模型层中主要放置数据相关操作,
* 控制器:控制器主要放置业务逻辑相关操作
* 视图层:视图层中放置视图相关操作

下面以对一张订单表的数据进行增删查改的操作为例,分别在不同层次实现不同方法。

```js
// 太简单,不想写..
```
