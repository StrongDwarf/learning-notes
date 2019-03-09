# 《javascript深入》读书笔记

原文:[https://github.com/mqyqingfeng/Blog/issues/14](https://github.com/mqyqingfeng/Blog/issues/14)

本文作为阅读笔记记录下来,较原文有缩略，也有个人理解。

[目录](#目录)

* [从原型到原型链](#从原型到原型链)
* [词法作用域和动态作用域](#词法作用域和动态作用域)
* [执行上下文栈](#执行上下文栈)
* [变量对象](#变量对象)
* [作用域链](#作用域链)
* [ECMAScript规范解读this](#ECMAScript规范解读this)
* [执行上下文](#执行上下文)
* [闭包](#闭包)
* [参数按值传递](#参数按值传递)

## 从原型到原型链

### prototype

每个函数都有一个prototype属性,这个prototype属性指向一个对象,这个对象是调用该构造函数而创建的实例的原型

### __proto__

这是每个JavaScript对象(除了null)都具有的属性,叫__proto__。这个属性会指向该对象的原型

``` javascript
var o = new Object()
console.log(o.__proto__ === Object.prototype)
```

### constructor

constructor:每个原型都有一个constructor属性指向关联的构造函数

``` javascript
console.log(Object.prototype.constructor === Object)
```

### 实例与原型

当读取实例的属性时,如果找不到,就会查找与对象关联的原型中的属性，如果还找不到,就去找原型的原型,一直找到顶层为止

## 词法作用域和动态作用域

### 作用域

作用域是指程序源代码中定义变量的区域

作用域规定了如何查找变量,也就是确定当前执行代码对变量的访问权限。

JavaScript采用词法作用域,也就是静态作用域

### 静态作用域与动态作用域

因为JavaScript采用的是词法作用域,函数的作用域在函数定义的时候就决定了

而与词法作用域相对的是动态作用域,函数的作用域是在函数调用的时候才决定的

``` javascript
var value = 1
function foo(){
    console.log(value)
}
function bar(){
    var value = 2
    foo()
}

bar()  //1
```

## 执行上下文栈

### 顺序执行？

JavaScript引擎并非一行一行地分析和执行程序,而是一段一段地分析执行,当执行一段代码的时候，会进行一个"准备工作",比如变量提升和函数提升

### 可执行代码

JavaScript中的可执行代码类型有全局代码，函数代码,eval代码

比如,当执行到一个函数的时候,就会进行准备工作,这里的准备工作,用更专业的话来说,就叫做"执行上下文"

### 执行上下文栈

JavaScript引擎创建了执行上下文栈(ECS)来管理执行上下文

值得注意的是,JavaScript中变量查找是在作用域链中查找的，而作用域链和执行上下文栈是两个东西

为了模拟执行上下文栈的行文,让我们定义执行上下文栈是一个数组:

``` javascript
ECStack = []
```

当JavaScript开始要解释执行代码的时候,最先遇到的就是全局代码,所以初始化的时候首先就会向执行上下文栈压入一个全局执行上下文，

``` javascript
ECStack = [
    globalContext
]
```

现在JavaScript遇到了下面的代码

``` javascript
function func3(){
    console.log('func3')
}
function func2(){
    func3()
}
function func1(){
    func2()
}
func1()
```

ECS的变化如下

``` java
//伪代码

//func1()
ECStack.push(<func1>functionContext)

//func2()
ECStack.push(<func2>functionContext)

//func3()
ECStack.push(<func3>functionContext)

//执行完了func3
ECStack.pop(<func3>functionContext)

ECStack.pop(<func2>functionContext)

ECStack.pop(<func1>functionContext)
```

## 变量对象

当JavaScript代码执行一段可执行代码时,会创建对应的执行上下文

对于每个执行上下文,都有三个重要属性

* 变量对象(Variable object,VO)
* 作用域链(Scope chain)
* this

变量对象就是与执行上下文相关的数据作用域,存储了在上下文中定义的变量和函数声明

### 全局上下文

W3School中对全局对象的介绍

* 全局对象是预定义的对象,作为JavaScript的全局函数和全局属性的占位符。通过使用全局对象，可以访问其他所有预定义的对象,函数和属性。
* 在顶层JavaScript代码中,可以用关键字this引用全局对象。因为全局对象是作用域链的头，这意味着所有非限定性的变量和函数名都会作为该对象的属性来查询

### 函数上下文

在函数上下文中,我们用活动对象(activation object,AO)来表示变量对象

活动对象和变量对象其实是一个东西,只是变量是规范上的或者说是引擎实现上的，不可在JavaScript环境中访问,只有当进入一个执行上下文中,这个执行上下文的变量对象才会被激活，所以才叫activation object，而只有被激活的变量对象,也就是活动对象上的各种属性才能被访问

活动对象是在进入函数上下文时刻被创建的,它通过函数的arguments属性初始化。arguments属性值是Arguments对象

### 执行过程

执行上下文的代码会分成两个阶段进行处理:分析和执行,我们也可以叫做

* 1,进入执行上下文
* 2,代码执行

#### 进入执行上下文

当进入执行上下文时,这时候还没有执行代码

变量对象会包括:

* 1,函数的所有形参(如果是函数的执行上下文)
  * 由名称和对应值组成的一个变量对象的属性被创建
  * 没有实参,属性值设为undefined
* 2,函数声明
  * 由名称和对应值(函数对象(function-object))组成一个变量对象的属性被创建
  * 如果变量对象已经存在相同名称的属性,则完全替换这个属性
* 3,变量声明
  * 由名称和对应值组成一个变量对象的属性被创建
  * 如果变量名称跟已经声明的形式参数或函数相同,则变量声明不会干扰已经存在的这类属性

如下:

``` javascript
function foo(a){
    var b = 2
    function c(){}
    var d = function(){}
    b=3
}
foo(1)
```

在进入执行上下文后,这时候的AD值是

``` javascript
AD = {
    arguments:{
        0:1,
        length:1
    },
    a:1,
    b:undefined,
    c:reference to function c(){},
    d:undefined
}
```

#### 代码执行

在代码执行阶段,会顺序执行代码,根据代码,修改变量的值

还是上面的例子,当代码执行完后,这时候的AO是:

``` javascript
AO = {
    arguments:{
        0:1,
        length:1
    },
    a:1,
    b:3,
    c:reference to function c(){},
    d:reference to FunctionExpression "d"
}
```

到了这里变量对象的创建过程就介绍完了,让我们简洁的总结我们上诉所说:

* 1,全局上下文的变量对象始终是全局对象
* 2,函数上下文的变量对象初始化只包括Arguments对象
* 3,在进入执行上下文时会给变量添加形参,函数声明,变量声明等初始的属性值
* 4,在代码执行阶段,会再次修改变量对象的属性值

## 作用域链

在查找变量的时候,会先从当前上下文的变量对象中查找,如果没有找到,就会从父级(词法层面上的父级)作用域的变量对象中查找,一直找到全局上下文的变量对象,也就是全局对象。这样由多个执行上下文的变量对象构成的链表就叫做作用域链。

下面,让我们以一个函数的创建和激活两个时期来讲解作用域链是如何创建和变化的。

### 函数创建

函数的作用域在函数定义的时候就决定了

这是因为函数有个内部属性[[scope]],当函数创建的时候,就会报错所有父变量对象到其中,你可以理解[[scope]]就是所有父变量对象的层级链,但是注意:[[scope]]并不代表完整的作用域链

举个例子:

``` javascript
function foo(){
    function bar(){
        //...
    }
}
```

函数创建时,各自的[[scope]]为:

``` javascript
foo.[[scope]] = [
    globalContext.VO
]
bar.[[scope]] = [
    fooContext.AO
    globalContext.VO,
]
```

### 函数激活

当函数激活时,进入函数上下文,创建VO/AO后,就会将活动对象添加到作用域的前端。

这时候执行上下文的作用域链,我们命名为Scope

``` javascript
Scope = [AO].concat([[Scope]])
```

### 例子

以下面的例子,结合着之前讲的变量对象和执行上下文栈,我们来总结一下函数执行上下文中作用域链和变量对象的创建过程

``` javascript
var scope = "global scope"
function checkscope(){
    var scope = 'local scope'
    return scope
}
checkscope()
```

执行过程如下:

1,checkscope函数被创建,保存作用域链到内部属性[[scope]]

``` javascript
checkscope.[[scope]] = {
    globalContext.VO
}
```

2,执行checkscope函数,创建checkscope函数执行上下文,checkscope函数执行上下文被压入执行上下文栈,

``` javascript
ECStack = [
    checkscopeContext,
    globalContext
]
```

3,checkscope函数并不立即执行,开始准备工作,第一步:复制函数[[scope]]属性创建作用域链

``` javascript
checkscopeContext = {
    Scope:checkscope.[[scope]]
}
```

4,第二步:用arguemnts创建活动对象,随后初始化活动对象,加入形参,函数声明,变量声明

``` javascript
checkscopeContext = {
    AO:{
        arguments:{
            length:0
        },
        scope:undefined
    },
    Scope:checkscope.[[scope]]
}
```

5,第三步:将活动对象压入checkscope作用域顶端

``` javascript
checkscopeContext = {
    AO:{
        arguments:{
            length:0
        },
        scope2:undefined
    },
    Scope:[AO,[[Scope]]]
}
```

6,准备工作做完,开始执行函数,随着函数的执行,修改AO的属性值

``` javascript
checkscopeContext = {
    AO:{
        arguments:{
            length:0
        },
        scope:'local scope'
    },
    Scope:[AO,[[Scope]]]
}
```

查找到scope的值,返回后函数执行完毕,函数上下文从执行上下文栈中弹出

``` javascript
ECStack = [
    globalContext
]
```

## ECMAScript规范解读this

### Types

* ECMAScript的类型分为语言类型和规范类型
* ECMAScript语言类型是开发者直接使用ECMAScript可以操作的。其实就是我们常说的undefined,null,boolean,string,number和object
* 而规范类型相当于meta-values，是用来用算法描述ECMAScript语言结构和ECMAScript语言类型的。规范类型包括:Reference,List,Completion,Property Descriptor,Property Identifier,Lexical Environment和Environment Record

### Reference

这里的Reference是一个Specification Type,也就是"只存在于规范里的抽象类型"。它们是为了更好地描述抽象语言的底层行为逻辑才存在的,但并不存在于实际的js代码中

Reference的构成由三个组成部分组成,分别是:

* base value
* referenced name
* strict reference

可是这些到底是什么呢?简单的时候base value就是属性所在的对象或者就是EnvironmentRecord,它的值只可能是undefined,an object，a Boolan，a String，a Number，or an environment record其中的一种

referenced name就是属性的名称

举例:

``` javascript
var foo = 1
//对应的Reference是:
var fooReference = {
    base:EnvironmentRecord,
    name:'foo',
    strict:false
}
```

再比如:

``` javascript
var foo = {
    bar:function(){
        return this
    }
}
foo.bar() //foo

//bar对应的Reference是:
var BarReference = {
    base:foo,
    propertyName:'bar',
    strict:false
}
```

### 如何确定this的值

关于Reference讲了那么多,为什么要将Reference呢?到底Reference跟本文的主体this有哪些关联呢?

看规范11.2.3 函数调用

* 1,令ref为解释执行MemberExpression的结果
* 2,令func为GetValue(ref)
* 3,令argList为解释执行Arguments的结果,产生参数值们的内部列表
* 4,如果Type(func)is not Object，抛出一个TypeError异常
* 5,如果IsCallable(func)is false,抛出一个TypeError异常
* 6,如果Type(ref)为Reference,那么如果isPropertyReference(ref)为true,那么令thisValue为GetBase(ref)，否则,ref的基值是一个环境记录,令thisValue为调用GetBase(ref)的ImplicitThisValue具体方法的结果
* 7,否则,假如Type(ref)不是Reference,令thisValue为undefined
* 8,返回调用func的[[Call]]内置方法的结果，传入thisValue作为this值和列表argList作为参数列表

什么是MemberExpression?规范11.2 Left-Hand-Side Expressions:MemberExpression

* PrimaryExpression //原始表达式
* FunctionExpression    //函数定义表达式
* MemberExpression[Expression]  //属性访问表达式
* MemberExpression.IdentifierName   //属性访问表达式
* new MemberExpression Arguments    //对象创建表达式

举例:

``` javascript
function foo(){
    console.log(this)
}
foo()   //MemberExpression是foo

function foo(){
    return function(){
        console.log(this)
    }
}

foo()() //MemberExpression 是 foo()

var foo = {
    bar:function(){
        return this
    }
}

foo.bar(); //MemberExpression 是 foo.bar
```

所以简单理解MemberExpression其实就是()左边的部分

2,判断ref是不是一个Reference类型

关键就在于看规范是如何处理各种MemberExpression,返回的结果是不是一个Reference类型。

如下:

``` JavaScript
var value = 1;

var foo = {
  value: 2,
  bar: function () {
    return this.value;
  }
}

//示例1
console.log(foo.bar());
//示例2
console.log((foo.bar)());
//示例3
console.log((foo.bar = foo.bar)());
//示例4
console.log((false || foo.bar)());
//示例5
console.log((foo.bar, foo.bar)());
```

#### foo.bar()

在示例1中,MemberExpression计算的结果是foo.bar，那么foo.bar是不是一个Reference呢?

查看规范11.2.1 Property Accessors,这里展示了一个计算的过程,什么都不管了,就看最后一步

* Return a value of type Reference whose base value is baseValue and whose referenced name is propertyNameString，and whose strict mode flag is strict

我们得知该表达式返回了一个Reference类型

根据之前的内容,我们知道该值为:

``` javascript
var Reference = {
    base:'foo',
    name:'bar',
    strict:false
}
```

接下来按照2.1的判断流程走:

* 2.1 如果ref是Reference,并且IsPropertyReference(ref)是true,那么this的值为GetBase(ref)

该值是Reference类型,那么IsPropertyReference(ref)的结果是什么呢?

前面我们已经铺垫了IsPropertyReference方法，如果base value是一个对象,结果返回true。

base value为foo,是一个对象,所以IsPropertyReference(ref)结果为true

这个时候我们就可以确定this的值了

``` javascript
this = GetBase(ref)
```

GetBase也已经铺垫了,获得base value值,这个例子中就是foo,所以this的值就是foo,示例一的结果是2

#### (foo.bar)()

foo.bar被()包住,查看规范11.1.6 The Grouping Operator

直接看结果部分:

* return the result of evaluating Expression，This may be of type Reference
* NOTE This algorithm does not apply GetValue to the result of evaluating Expression

实际上()并没有对MemberExpression进行计算,所以跟示例1的结果是一样的

#### (foo.bar = foo.bar)()

看示例3,有复制操作符,查看规范11.13.1 Simple Assignment( = )

计算的第三步:

``` javascript
Let rval be GetValue(rref)
```

因为使用了GetValue,所以返回的不是Reference类型,this为undefined

#### (foo.bar,foo.bar)()

看示例5,逗号操作符,查看规范11.14 Comma Operator(,)

计算第二步

``` javascript
Call GetValie(lref)
```

因为使用了GetValue,所以返回的不是Reference类型,this为undefined

``` javascript
var arr = new Array()

arr.push({a:'xiaobaicai'})
arr.push(function(){console.log(this)})

arr[1]()

var foo = function(){
    foo.bar = function(){
        console.log(this)
    }

    return foo.bar
}
foo()()
```

所以最后一个例子的结果是:

``` JavaScript
var value = 1;

var foo = {
  value: 2,
  bar: function () {
    return this.value;
  }
}

//示例1
console.log(foo.bar()); //2
//示例2
console.log((foo.bar)()); //2
//示例3
console.log((foo.bar = foo.bar)()); //1
//示例4
console.log((false || foo.bar)());  //1
//示例5
console.log((foo.bar, foo.bar)());  //1
```

## 执行上下文

分析下面一段代码:

``` javascript
var scope = "global scope"
function checkscope(){
    var scope = "local scope"
    function f(){
        return scope
    }
    return f()
}
checkscope()
```

执行过程如下:

1,执行全局代码,创建全局执行上下文,全局上下文被亚茹执行上下文栈

``` javascript
ECStack = [
    globalContext
]
```

2,全局上下文初始化

``` javascript
globalContext = {
    VO:[global],
    Scope:[globalContext.VO],
    this:globalContext.VO
}
```

2,初始化的同时,checkscope函数被创建,保存作用域链到函数的内部属性[[scope]]

``` javascript
checkscope.[[scope]] = [
    globalContext.VO
]
```

3,执行checkscope函数,创建checkscope函数执行上下文,checkscope函数执行上下文被压入执行上下文栈

``` javascript
ECStack = [
    checkscopeContext,
    globalContext
]
```

4,checkscope函数执行上下文初始化

* 1,复制函数[[scope]]属性创建作用域链
* 2,用arguments创建活动对象
* 3,初始化活动对象,即加入形参,函数声明,变量声明
* 4,将活动对象压如checkscope作用域链顶端

同时f函数被创建,保存作用域链到f函数内部属性[[scope]]

``` javascript
checkscopeContext = {
    AO:{
        arguments:{
            length:0
        },
        scope:undefined,
        f:reference to function(){}
    },
    Scope:[AO,globalContext.VO],
    this:undefined
}
```

5,执行f函数,创建f函数执行上下文,f函数执行上下文被压入执行上下文栈

``` javascript
ECStack = [
    fContext,
    checkscopeContext,
    globalContext
]
```

6,f函数执行上下文初始化,以下跟第4步相同

* 1,复制函数[[scope]]属性创建作用域链
* 2,用arguments创建活动对象
* 3,初始化活动对象,即加入形参,函数声明,变量声明
* 4,将活动对象压入f作用域链顶端

``` javascript
fContext = {
    AO:{
        arguments:{
            length:0
        }
    },
    Scope:[AO,checkscopeContext.AO,globalContext.AO],
    this:undefined
}
```

7,f函数执行,沿着作用域查找scope值,返回scope值
8,f函数执行完毕,f函数上下文从执行上下文栈中弹出

``` javascript
ECStack = [
    globalContext
]
```

## 闭包

MDN对闭包的定义为:

``` javascript
闭包是指那些能够访问自由变量的函数
```

那么什么是自由变量呢?

``` javascript
自由变量是指在函数中使用的,但即不是函数声明也不是函数的局部变量的变量
```

由此,我们可以看出闭包共由两部分组成:

``` javascript
闭包=函数+函数能够访问的自由变量
```

ECMAScript中,闭包指的是:

* 1,从理论角度:所有的函数。因为它们都在创建的时候就将上层上下文的数据保存起来了。哪怕是简单的全局变量也是如此,因为函数中访问全局变量就相当于是在访问自由变量,这个时候使用最外层的作用域
* 2,从实践角度:以下函数才算是闭包:
  * 即使创建它的上下文已经销毁,它仍然存在(比如，内部函数从父函数中返回)
  * 在代码中引用了自由变量

## 参数按值传递

在《JavaScript高级程序设计》第三版4.1.3,讲到传递参数:

``` javascript
ECMAScript中所有函数的参数都是按值传递的
```

什么是按值传递?

``` javascript
也就是说,把函数外部的值复制给函数内部的参数,就和把值从一个变量复制到另一个变量一样。
```

### 引用传递

拷贝虽然很好理解,但是当值是一个复杂的数据结构的时候,拷贝就会产生性能上的问题。

所以还有一种传递方式叫做按引用传递

所谓按引用传递,就是传递对象的引用,函数内部对参数的任何改变都会影响该对象的值,因为两者引用的是同一个对象。

如下:

``` javascript
var obj = {
    value:'a'
}
function foo(o){
    o.value = 'b'
    console.log(o.value)    //'b'
}
foo(obj)
console.log(obj.value)  //'a'
```

### 按共享传递

那么《javascript高级程序设计》中说的ES中所有函数的参数都是按值传递的是对还是错呢?

再看个例子如下。

``` javascript
var obg = {
    value:1
}
function foo(o){
    o = 2;
    console.log(o); //2
}
foo(obj)
console.log(obj.value)  //1
```

如果JavaScript采用的是引用传递,外层的值也会被修改,但是这怎么又没有被改呢?所以真的不是引用传递吗?

这就要讲到其实还有第三种传递方式,叫按共享传递

而共享传递是指,在传递对象的时候,传递对象的引用的副本

值得注意的是:按引用传递是传递对象的引用,而按共享传递是传递对象的引用的副本。

所以修改o.value 可以通过引用找到原值,但是直接修改o，并不会修改原值。所以上面两个例子都是按共享传递。

最后,可以这样理解:

参数如果是基本类型是按值传递,如果是引用类型按共享传递。

但是因为拷贝副本也是一种值的拷贝,所以在高程中也直接认为是按值传递了