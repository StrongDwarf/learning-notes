# nodeJS面试题目总结

## ES6新特性

### ES6有哪些新特性？

class的支持,模块化,箭头操作符,let/const块作用域,for..of遍历,generator函数,Map/Set，Promise，字符串模板,参数默认值/不定参数。解构

### 你对ES6的个人看法

ES6强化了JS对面相对象的支持,并且提供了基于语言标准的模块化支持(在这之前都是使用AMD或CMD标准的第三方类库),这些加强了JS的工程化特性,我们知道,在以前,JS只是一门脚本语言(虽然现在也还是脚本语言,哈哈)。但是从ES6引入的新特性来看,我们在使用过程中可以很明显的感觉到,JS更适合工程化了,即越来越向能开发大型应用的语言靠拢了。

## JS高级话题(面向对象,作用域,闭包,设计模式)

### 常用js类定义的方法有哪些

主要有构造函数原型和对象创建两种方法。原型法是通用老方法,对象创建是ES5推荐使用的方法,不过目前来看,原型法更普遍

#### 构造函数定义类

``` javascript
function Person(){
    this.name="xiaobaicai"
}
Person.prototype.sayName = function(){
    console.log(this.name)
}
```

#### 对象创建方法

``` javascript
function Person(){
    name:'xiaobaicai',
    sayName:function(){
        console.log(this.name)
    }
}
var person = Object.create(Person)
person.sayName()
```

### js类继承的方法有哪些

原型链法,属性复制法和构造器应用法,另外,由于每个对象可以是一个类,这些方法也可以用于对象类的继承

#### 原型链法

``` javascript
function Animal(){
    this.name="xiaobaicai"
}
Animal.prototype.sayName=function(){
    console.log(this.name)
}
function Person(){}
Person.prototype = Animal.prototype;
Person.prototype.constructor = 'Person'
```

#### 属性复制法

``` javascript
function Animal(){
    this.name = 'animal'
}
Animal.prototype.sayName = function(){
    console.log(this.name)
}
function Person(){}

for(prop in Animal.prototype){
    Person.prototype[prop] = Animal.prototype[prop]
}
Person.prototype.constructor = 'Person'
```

#### 构造器应用法

``` javascript
function Animal(){
    this.name = 'xiaobaicia'
}
Animal.prototype.sayName = function(){
    console.log(this.name)
}
function Person(){
    Animal.call(this);
}
```

### js类多重继承的实现方法是怎样的

就是类继承里边的属性复制法来实现,因为所有父类的prototype属性被复制后,子类自然拥有类似行为和属性

### js里的作用域是怎样的

* 在ES6之前,js只存在全局作用域和函数作用域,在ES6之后,JS还存在块级作用域
* js中的作用域是词法作用域,即静态作用域。有别于如JAVA中的动态作用域
* 静态作用域的意思是,作用域中的属性的值在该作用域创建时就已确定。

### js里边的this指的是什么

可以简单的理解为this指的是对象

### apply，call和bind有什么区别

三者都可以把一个函数应用都其他对象上,注意不是自身对象,apply,call是直接指向函数调用,bind是绑定,执行需要再次调用,apply和call的区别是apply接受数组作为参数,而call接受逗号分隔的参数列表

### caller,callee和arguments分别是什么

caller指向函数调用者,callee指向当前函数(被调用函数自身),arguments指向函数参数列表,是一个类数组的变量

### 什么是闭包，闭包有哪些用处

闭包简单的理解就是闭合的包,包区分包内和包外,即包内作用域和包外作用域。闭包的用处:闭包唯一的用处是实现私有变量,而利用这个才引申出闭包的一系列用法,比如用它实现模块化.

### definePropperty,hasOwnProperty，propertyIsEnumerable都是做什么用的?

Object.defineProperty(obj,prop,descriptor)用来给对象定义属性,它和直接设置属性的区别是可以声明访问器属性。
Object.hasOwnProperty()用于检查某一属性是不是存在于对象本身,继承来的父亲的属性不算
propertyIsEnumerable用来检测某一属性是否可遍历,也就是能不能用for...in循环来取到

### js常用设计模式的实现思路,单例,工厂,代理,装饰,观察者模式

``` javascript
/* 单例 */
//任何对象都是单例
var obj = {
    'name':'xiaobaicai',
}
//要固定某个类的话
var danli = function(){
    function Author(){
        this.name = 'xiaobaicai'
    }
    Author.prototype.sayName = function(){
        console.log(this.name)
    }
    Author.prototype.constructor = "Author"
    return new Author()
}()

/* 工厂 */
var ballFactory = function(){
    function FootBall(){
        this.type = "footBall"
    }
    function BasketBall(){
        this.type = "basketBall"
    }

    return function(type){
        switch(type){
            case 'footBall':
                return new FootBall()
                break;
            case 'basketBall':
                return new BasketBall()
                break;
        }
    }
}()

/* 代理 */
function Person(){}
Person.prototype.sayName = function(){console.log('[Function] sayName')}
Person.prototype.sayHello = function(){console.log('[Function] sayHello')}

var Proxy = function(){
    this.person = new Person()
    var that = this;
    return function(funcName){
        return that.person[funcName]()
    }
}

/* 观察者 */
var eventListener = {}
function addListener(listener,func){
    if(!(listener in eventListner)){
        eventListener[listener] = []
    }
    eventListener[listener].push(func)
}
function removeListener(listener,func){
    if(listener in eventListener){
        if(func){
            //...
        }else{
            delete eventListener[listener]
        }
    }
}

function emit(listener){
    if(listener in eventListener){
        for(func of eventListener[listener]){
            func()
        }
    }
}
```

### 列举数组的常用方法

栈操作:shift/unshift pop/push
位置操作: indexOf/lastIndexOf
排序操作:reverse/sort
偏函数式操作：map/filter/every/forEach
操作:slice/concat/splice
切割/合并:split join
归并操作:reduce

ES6: Array.from() Array.of()，Array.isArray(),  .values() .keys() .entries()

### 列举字符串的常用操作

indexOf/lastIndexOf/charAt,split/match/test,slice/subStr/subString,toLowerCase/toUpperCase

## node 核心内置类库(事件,流,文件,网络)

### node概览

#### 为什么要用node

node特点：简单强大,轻量可扩展。简单体现在node使用的是javascript,json来进行编码，前端程序员很容易上手,强大体现在node在底层基于非阻塞IO,可以适应分块传输数据,尤其擅长高并发架构；轻量体现在node本身既是代码,又是服务器,前后端使用同一语言,可扩展体现在可以轻松应付多实例,符服务器架构,同时又海量的第三方应用组件

#### node的架构是什么样子的

主要分3层,应用app>>V8及node内置架构>>操作系统,v8是node运行的环境,可以理解为node虚拟机,node内置架构又可分为三层:核心模块(JavaScript实现)>>c++绑定>>libuv+CAes+http

#### node有哪些核心模块

EventEmitter,Stream,FS,Net和全局对象

### node全局对象

#### node有哪些全局对象

process,console,Buffer

#### process有哪些方法

process.stdin,process.stdout,process.stderr,process.on,percess.env,percess.argv,process.arch,process.platform,process.exit

#### console.log有哪些方法

console.log,console.info,console.error,console.warning,console.time,console.timeEnd,console.trace,console.table

#### node有哪些定时功能

setTimeout/clearTimeout,setInterval/clearInterval,setImmediate/clearImmediate,process.nexttick

#### node中的事件循环是什么样子的

process.nextTick:当前事件执行过程之后执行
setImmidate: