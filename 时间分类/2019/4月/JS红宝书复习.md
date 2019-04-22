# JS红宝书复习

## 基本概念

### JS中有哪些基本数据类型

string number undefined null object boolean symbol

### 简单介绍一下JS中的垃圾收集机制

垃圾收集机制分两种,一种是引用计数,一种是标记清除,除了早期的IE使用引用计数之外,其他的浏览器版本都使用标记清楚

### 知道包装类型吗，简单介绍一下

JS在读取String，Number，Boolean类型值的时候,并不是直接读取使用内存中的数据,而是读取数据后,使用数据对应的类创建对应对象。

### 说一下你知道的数组的方法

ES5:

* 栈操作:pop,push,unshift,shift
* 增删查改:concat,slice,split,splice,indexOf,lastIndexOf,join
* 函数式:map,filter,every,forEach,
* 归并排序:reduce,reverse,sort

ES6:

* 属性遍历:keys,values,entries,
* Array.from(),Array.of(),Array.isArray()

### 说说你知道的JS中创建的对象的方法

创建对象的方法:

* 使用类构造函数:new Person()
* 使用ES5推荐的:Object.create()

创建对象的设计模式:

* 工厂模式
* 构造函数模式
* 原型模式
* 组合使用构造函数模式和原型模式

### 说说你知道的JS中的继承

* 原型链继承
* 结构构造函数继承
* 组合继承
* 原型式继承
* 寄生式继承
* 寄生组合式继承
* 使用ES6中class语法

### 知道递归和迭代吗? 说说你的理解

递归在函数执行过程中再次调用该函数

迭代使用旧值求新值,即每次调用都传值

具体区别如下

``` javascript
/* 递归  */
function qiufeibonaqie(n){
    if(n == 1){
        return 1
    }else{
        return n*arguments.callee(n-1)
    }
}

/* 迭代 */
function qiufeibonaqie(n,num){
    if(n == 1){
        return num
    }
    return arguments.callee(n-1,num*n)
}
```

迭代在某些时候可以用于优化递归,比如尾调用优化

### 谈谈你对BOM的理解

BOM指浏览器对象模型,其中包含一些常用的对象,如window,history,screen,location,navigator。

window不用说了,作为客户端全局对象,其他对象都挂载在它的上面,history中有几个常用的方法可以用来前进和后退,screen中的属性可以用来读取浏览器在屏幕中的位置,location可以用来处理域名相关的操作,navigator用来进行客户端能力检测

### 谈谈你对DOM的理解

DOM指文档对象模型,DOM中封装了一些文档对象的API接口。其中

### 谈谈你对客户端能力检测的理解

客户端能力检测主要用于实现浏览器兼容,有能力检测,怪癖检测和客户端检测三种。

其中能力检测一般是在使用某API之前检测该API是否存在。比如最常见的为元素添加事件监听。在使用DOM3级和DOM2级事件之前我们都会先检测一下这些API是否存在,如果都不存在就使用DOM0级事件

怪癖检测一般是用于检测一些浏览器遗留下来的傻逼问题:比如在火狐三以前的版本中使用 for...in 会枚举被隐藏的属性

客户端检测则是没有办法中的办法,使用用户代理进行检测。这一块比较啰嗦. 可以自己百度

### 知道事件冒泡和事件捕获吗

事件冒泡由目标元素向该元素的父元素进行发散
事件捕获由目标元素的最上级父元素向目标元素进行集中

所有现代浏览器都支持事件冒泡,事件捕获在一些早期版本的浏览器中不受支持,所以一般都使用事件冒泡

### 说说你知道的事件类型有哪些

* 鼠标事件:点击,划过,
* 键盘事件
* 滚轮事件
* 移动端的触摸与手势事件
* 资源加载中的readystate事件: 加载中,加载完成,交互,完成

### 说说你知道的在使用事件处理程序时的性能优化方案

* 使用事件委托
* 在删除元素前先移除元素上的事件处理程序

### 说说H5中新增的东西

* 新的元素,header,footer等
* 视音频
* 离线存储
* 原生拖放
* websocket
* 文件API

### 你看过那些JS书籍

你不知道的JS,JS忍者秘籍，JS之美,Web前端高效开发实战,H5 APP开发,JS高级程序设计,JS设计模式,JS函数式编程。