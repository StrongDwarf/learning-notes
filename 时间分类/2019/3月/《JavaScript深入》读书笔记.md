# 《javascript深入》读书笔记

原文:[https://github.com/mqyqingfeng/Blog/issues/14](https://github.com/mqyqingfeng/Blog/issues/14)

本文作为阅读笔记记录下来,较原文有缩略，也有个人理解。

[目录](#目录)

* [从原型到原型链](#从原型到原型链)

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





``` javascript
function bubble_sort(arr){
    var len = arr.length,
        temp;
    for(var i =0;i<len;i++){
        for(var j =0;j<len-1-i;j++){
            if(arr[j]<arr[j+1]){
                temp = arr[j]
                arr[j] = arr[j+1]
                arr[j+1] = temp
            }
        }
    }
    return arr
}
```

``` javascript
(function(){
    function b(){
        function c(){
            console.log(scope)
        }
        return c
    }
    function d(){
        var scope = "d scope"
        b()()
    }
    d()
})()


