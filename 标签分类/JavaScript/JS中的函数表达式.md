# JS中的函数表达式

目录:
 * [优化递归](# 优化递归)
 * [闭包](# 闭包)
 * [模仿块级作用域](# 模仿块级作用域)
 * [私有变量](# 私有变量)
 * [总结](# 总结)

定义函数有两种方式:一个函数声明,二是函数表达式。其中函数声明存在函数声明提升，函数表达式类似普通的赋值表达式。如下

``` javascript
//函数声明
function sayHello(){}
//函数表达式
var sayHi
sayHi = function(){}
```

JavaScript除了OO特性之外，其也拥有函数式编程特性,即函数是一等公民，函数拥有其作用域，属性,值。可以将函数作为返回值和属性。如下

``` javascript
function sayHi(){
    return function(name){
        console.log('hello '+name)
    }
}
sayHi()('xiaobaicai')           //'hello xiaobaicai'
```

## 优化递归

递归函数是在一个函数通过名字调用自身的情况下构成的，如下:

``` javascript
function factorial(num){
    if(num<=1){
        return 1
    }else{
        return num * factorial(num - 1)
    }
}
```

但是这种使用函数声明创建的递归函数无法将函数赋值给其他变量，如下:

``` JavaScript
var temp = factorial
factorial = null
temp(10)    //Error
```

如上,我们将temp变量指向递归函数，并清除factorial，然后执行temp函数后会报错。其原因是原递归函数与变量名factorial存在高度耦合。当执行temp = factorial后，temp函数如下:

``` javascript
temp = function(num){
    if(num<=1){
        return 1
    }else{
        return num * factorial(num - 1)
    }
}
```

此时factorial已设为null，故调用temp函数会报错。
解决这种问题有两种方式，分别是使用arguments.callee()调用函数自身，和使用命名函数表达式。其中arguments.callee()因为在严格模式下是被禁用的，一般不建议使用.两种方式代码如下:

``` javascript
//使用arguments.callee()
function factorial(num){
    if(num<=1){
        return 1
    }else{
        return num * arguments.callee(num - 1)
    }
}
//‘使用命名函数表达式
factorial = (function f(num){
    if(num <= 1){
        return 1
    }else{
        return num * f(num - 1)
    }
})
```

## 闭包

闭包是指有权访问另一个函数作用域中的变量的函数。关于闭包,主要需要理解的有以下几点:

 * 闭包与变量
 * this对象
 * 闭包中的内存删除

### 2.1 闭包与变量

闭包中的变量取值同样遵守作用域链机制，而作用域链的配置机制却引出了一个值得注意的副作用，即闭包只能取得包含函数中任何变量的最后一个值。闭包所保存的是整个变量对象，而不是某个特殊的变量。下面的例子中清晰的说明了这个问题。

``` javascript
function createFunctions(){
    var result = new Array()

    for(var i =0 ;i<10 ;i++){
        result[i] = function(){
            return i
        }
    }

    return result
}
```

如上的函数,我们实际希望result数组中保存的函数返回的结果是0~10，但是实际上每个函数都返回10。因为在result[i] = function(){return i} 形成的函数闭包中，函数可以读取的作用域是createFunctions的作用域。而在createFunctions的作用域中，只存在一个i,切此时i = 10。
要解决这种问题，我们可以在result[i] = function(){return i} 外再包一层作用域，且在该作用域中，保存不同的i值。如下

``` javascript
function createFunctions(){
    var result = new Array()

    for(var i =0;i<10;i++){
        result[i] = (function(num){
            return function(){
                return num
            }
        })(i)
    }

    return result
}
``` 

此时,我们在函数function(){return num}的作用域 和函数createFunction的作用域中新增了一层作用域。并将每一个i都的值都保存在该作用域的num中，

### 2.2 this对象

在闭包中使用this对象也可能会导致一些问题。我们知道，this对象是在运行时基于函数的执行环境绑定的:在全局函数中，this等于window，而当函数被作为某个对象的方法调用时，this等于那个对象。不过，匿名函数的执行环境具有全局性，因此其this对象通常指向window。但有时候由于编写闭包的方式不同，这一点可能不会那么明显。看下面的例子

``` javascript
var name = "the window"

var object = {
    name:"my object",
    getNameFunc:function(){
        return function(){
            return this.name
        }
    }
}

console.log(object.getNameFunc()())     //"the window"  (在非严格模式下)
```

正常情况下,我们希望输出的是 "my object"，但实际上却输出了"the window",我们先来分析一下上诉程序的执行过程,首先执行object.getNameFunc(),此时返回funciton(){return this.name},故当执行object.getNameFunc()()时,function(){return this.name} 函数的作用域链顺序为: 1:函数自身拥有的作用域->getNameFunc函数中的作用域->全局作用域

要想使该程序正常输出,可以修改成如下:

``` javascript
var name = "the window"
var object = {
    name:"my object",
    getNameFunc:function(){
        var that = this
        return function(){
            return that.name
        }
    }
}

console.log(object.getNameFunc()())     //"my object"
```

此时,当执行object.getNameFunc()时,getNameFunc()中的that变量被初始化并被赋值指向object对象的作用域,故能正确返回

### 2.3 内存泄漏

由于IE9之前的版本对JScript对象和COM对象使用不同的垃圾收集例程,因此闭包在IE的这些版本中会导致一些特殊的问题，具体来说，如果闭包的作用域中保存着一个HTML元素，那么就意味着该元素将无法被销毁。如下:

``` javascript
function assignHandler(){
    var element = document.getElementById("someElement")
    element.onclick = function(){
        alert(element.id)
    }
}
```

以上代码创建了一个作为element元素处理程序的闭包,而这个闭包则又创建了一个循环引用。由于匿名函数保存了一个队assginHandler()的活动对象的引用，因此就会导致无法减少element的引用数。只要匿名函数保存了一个队assignHandler()的活动对象的引用，因此就会导致无法减少element的引用数。只要匿名函数存在，element的引用数至少也是1，因此它所占用的内存就永远也不会被回收。可以对上面的代码做如下修改

``` javascript
function assignHandler(){
    var element = document.getElementById("someElement")
    var id = element.id
    element.onclick = function(){
        alert(id)
    }
    element = null
}
```

在上面的代码中,先将element.id保存到变量id中,解除了匿名函数中对变量element的引用,然后再将element设置为null 解除了闭包中对element的引用。

## 3 模仿块级作用域

在ES6中新增了let和const语法支持块级作用域,而在ES5中我们要使用块级作用域则可以使用立即执行函数来模拟块级作用域。如下

``` javascript
//不使用块级作用域,变量保存在函数作用域中
function outputNumbers(count){
    for(var i = 0;i<count;i++){
        console.log(i)
    }

    console.log(i);     //count
}

//使用块级作用域
function outputNumbers(count){
    (function(){
        for(var i =0;i<count;i++){
            console.log(i)
        }
    })()

    console.log(i)      //Error
}
```

## 私有变量

严格来讲,JavaScript中没有私有成员的概念，所有对象属性都是公开的。不过，倒是有一个私有变量的概念。任何在函数中定义的变量，都可以认为是私有变量，因为不能在函数的外部访问这些变量。私有变量包括函数的参数，局部变量和函数内部定义的其他函数。
特权方法指有权访问私有变量和私有函数的共有方法。

有两种在对象上创建特权方法的方式。第一种是在构造函数中定义特权方法，基本模式如下:

``` javascript
function MyObject(){
    //私有变量和私有函数
    var privateVariable = 10

    function privateFunction(){
        return false
    }

    //特权方法
    this.publicMethod = function(){
        privateVariable ++
        return privateFunction()
    }
}
```

在构造函数中直接定义特权方法的缺点是会针对每个实例都会创建同样一组新方法，而使用静态私有变量来实现特权方法就可以避免这个问题

### 4.1 静态私有变量

通过在私有作用域中定义私有变量或函数，同样也可以创建特权方法，其基本模式如下:

``` javascript
(function(){
    //私有变量和私有函数
    var privateVariable = 10;
    function privateFunction(){
        return false
    }

    //构造函数
    MyObject = function(){}

    //共有/特权方法
    MyObject.prototype.publicMethod = function(){
        privateVariable ++
        return privateFunction();
    }
})()
```

这个模式创建了一个私有作用域,并在其中封装了一个构造函数及相应的方法。在私有作用域中,首先定义了私有变量和私有函数，然后又定义了构造函数及其公有方法。这里需要注意的是在创建构造函数时,没有声明MyObject,而是直接对其赋值，是因为:初始化未经声明的变量，总是会创建一个全局变量。因此,MyObject就成为了一个全局变量。但在严格模式下，这种方式是被禁止的，严格模式下在创建MyObject之前可以先在要创建MyObject变量的作用域中声明。

这个模式还有一个问题就是,私有变量和函数是所有实例共享的，如下:

``` javascript
var Person
(function(){
    var name = ""

    Person = function(value){
        name = value
    }
    Person.prototype.getName = function(){
        return name
    }
    Person.prototype.setName = function(newValue){
        name = newValue
    }
})()

var person1 = new Person('xiaobaicai')
var person2 = new Person('xiaohei')

person1.getName()       //'xiaohei'
```

### 4.2 模块模式

前面的模式都是用于为自定义类型创建私有变量和特权方法的，而道格拉斯所说的模块模式则是为单例创建私有变量和特权的方法。所谓单例，指的就是只有一个实例的对象。按照惯例，JavaScript是以对象字面量的方式来创建单例对象的。
如下:

``` javascript
var singleton = {
    name : value,
    method:function(){
        //这里是方法的代码
    }
}
```

模块模式通过为单例添加私有变量和特权方法能够使其得到增强，其语法形式如下:

``` javascript
var application = function(){

    //私有变量和私有函数
    var privateVariable = 10

    function privateFunction(){
        return false
    }

    //特权,共有方法和属性
    return {
        publicProperty:true,
        publicMethod:function(){
            //这里是公有方法的代码
        }
    }
}()
```

### 4.3 增强的模块模式

有人进一步改进了模块模式，即在返回对象之前加入对其增强的代码。这种增强的模块模式适合那些单利必须是某种类型的实例，同时还必须添加某些属性和(或)方法对其加以增强的情况。来看下面的例子.

``` javascript
var singleton = function(){
    //私有变量和私有函数
    var privateVariable = 10
    function privateFunction(){
        return false
    }

    //创建对象
    var object = new CustomType()

    //添加特权/公有属性和方法
    object.publicProperty = true

    object.publicMethod = function(){
        privateVariable++
        return privateFunction()
    }

    //返回这个对象
    return object
}()
```

如果前面演示模式的例子中的application对象必须是BaseComponent的实例，那么就可以使用以下代码

``` javascript
var application = function(){
    //私有变量和函数
    var components = new Array()

    //初始化
    components.push(new BaseComponent())

    //创建application的一个局部副本
    var app = new BaseComponent()

    //公共接口
    app.getComponentCount = function(){
        return components.length
    }

    app.registerComponent = function(component){
        if(typeof component == "object"){
            components.push(component)
        }
    }

    //返回这个副本
    return app
}()
```

## 总结

在javascript编程中，函数表达式是一种非常有用的技术。使用函数表达式可以无须对函数命名，从而实现动态编程，匿名函数,函数表达式有下面一些特点

* 函数表达式不同于函数声明。函数声明要求有名字，但函数表达式不需要。没有名字的函数表达式也叫匿名函数
* 在无法确定如何引用函数的情况下,递归函数就会变得比较复杂
* 递归函数应该始终使用arguments.callee来递归地调用自身，不再使用函数名

当在函数内部定义了其他函数时，就创建了闭包。闭包有权访问包含函数内部的所有变量，原理如下:

* 在后台执行环境中，闭包的作用域包含它自己的作用域,包含函数的作用域和全局作用域
* 通常,函数的作用域及其所有变量都会在函数执行结束后被销毁
* 但是，当函数返回了一个闭包，这个函数的作用域将会一直在内存中保存到闭包不存在为止。

使用闭包可以在JavaScript中模仿块级作用域,
闭包还可以用于在对象中创建私有变量。