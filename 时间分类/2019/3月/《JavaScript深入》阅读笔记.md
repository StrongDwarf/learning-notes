# 《javascript深入》阅读笔记

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


