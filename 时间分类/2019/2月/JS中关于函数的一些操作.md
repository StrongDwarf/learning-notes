# JS中关于函数的一些操作

函数是JavaScript中最有趣的部分之一。它们本质上是十分简单和过程化的，但也可以是非常复杂和动态的。一些额外的功能可以通过使用闭包来实现。

目录:

* [作用域安全的构造函数](#作用域安全的构造函数)
* [惰性载入函数](#惰性载入函数)
* [函数绑定](#函数绑定)
* [函数柯里化](#函数柯里化)

## 作用域安全的构造函数

在我们使用构造函数时,构造函数其实就是一个使用new操作符调用的函数。当使用new调用时，new操作符会申请一个作用域作为即将要创建的对象的作用域。但是构造函数也可作为函数调用，此时作用域是global作用域。而这样是非常危险的，可以如下一样在对this赋值之前，判断this对象是否是当前构造函数的实例

``` javascript
function Person(name,age){
    if(this instanceof Person){
        this.name = name
        this.age = age
    }else{
        return new Person(name,age)
    }
}
```

## 惰性载入函数

因为浏览器之间行为的差异,多数JavaScript代码包含了大量的if语句,将之心引导到正确的代码中，如下:

``` javascript
function addHandler(element,type,handler){
    if(element.addEventListener){
        element.addEventListener(type,handler,false)
    }else if(element.attachEvent){
        element.addachEvent("on" + type,handler)
    }else{
        element["on"+type] = handler
    }
}
```

如上代码,将在每次执行addHandler()函数时执行一次if...else...判断,而使用惰性载入函数则可以使的在执行多次addHandler()函数时只执行一次if...else...判断

惰性载入函数有两种:

* 一种将在加载函数代码时执行判断
* 一种将在第一次执行函数代码时执行判断

第一种函数代码如下,这种函数会在当前代码被加载时候立即执行函数,并对函数重新赋值.

``` javascript
var addHandler = function(element,type,handler){
    if(document.addEventListener){
        addHandler = function(element,type,handler){
            element.addEventListener(type,handler,false)
        }
    }else if(document.attachEvent){
        addHandler = function(element,type,handler){
            element.addachEvent("on" + type,handler)
        }
    }else{
        addHandler = function(element,type,handler){
            element["on"+type] = handler
        }
    }
}()
```

第二种代码如下,该函数会在当前函数第一次被调用时候重新对函数赋值

``` javascript
var addHandler = function(element,type,handler){
    if(element.addEventListener){
        addHandler = function(element,type,handler){
            element.addEventListener(type,handler,false)
        }
    }else if(element.attachEvent){
        addHandler = function(element,type,handler){
            element.addachEvent("on" + type,handler)
        }
    }else{
        addHandler = function(element,type,handler){
            element["on"+type] = handler
        }
    }
    addHandler(element,type,handler)
}
```

## 函数绑定

另一个日益流行的高级技巧是函数绑定。函数绑定要创建一个函数，可以在特定的this环境中以指定参数调用另一个函数。该技巧常常和回调函数与事件处理程序一起使用，以便在将函数作为变量传递的同时保留代码执行环境。如下:

``` javascript
var handler = {
    message:"Event handled",
    handleClick:function(event){
        alert(this.message)
    }
}
var btn = document.getElementById('my-btn')
addHandler(btn,'click',handler.handleClick)
```

在上面的代码中,我们希望每次点击btn,都会弹出handler.message "Event handled"，然而事实上点击btn后,弹出的却是undefined。这个因为,当点击btn时,handler.handleClick函数的执行环境由handler变成了btn的作用域。此时btn的作用域中名为message的变量为undefined

要使上面的代码实现正确的结果,可以使用函数创建一个闭包访问handler作用域如下:

``` javascript
var handler = {
    message:"Event handled",
    handleClick:function(event){
        alert(this.message)
    }
}
var btn = document.getElementById('my-btn')
addHandler(btn,'click',function(event){
    handler.handleClick(event)
})
```

这个解决方案在onclick事件处理程序内使用了一个闭包直接调用handler.handleClick()。当然,这是特定于这段代码的解决方案。创建多个闭包可能令代码变得难以理解和调试。因此，很多JavaScript库实现了一个可以将函数绑定到指定环境的函数。这个函数一般都叫做bind()

一个简单的bind()函数接受一个函数和一个环境,并返回一个在给定环境中调用给定函数的函数。如下:

``` javascript
function bind(func,context){
    return function(){
        return func.apply(context,arguments)
    }
}
```

ES5为所有函数定义了一个原生的bind()方法,进一步简单了操作。使用ES5中的bind()方法如下:

``` javascript
addHandler(btn,"click",handler.handlerClick.bind(handler))
```

## 函数柯里化

与函数绑定紧密相关的主体是函数柯里化,它用于创建已经设置好了一个或多个参数的函数。函数柯里话的基本方法和函数绑定是一样的:使用一个闭包返回一个函数。两者的区别在于，当函数被调用时，返回的函数还需要设置一些传入的参数。

函数柯里化通常由以下步骤动态创建:调用另一个函数并为它传入要柯里化的函数和必要参数。

如下是创建函数柯里化的通用方式

``` javascript
function curry(fn){
    var args = Array.prototype.slice.call(arguments,1)
    return funciton(){
        var innerArgs = Array.prototype.slice.call(arguments)
        var finalArgs = args.concat(innerArgs)
        return fn.apply(null,finalArgs)
    }
}
```

curry()函数可以按如下方式应用

``` javascript
function add(num1,num2){
    return num1+num2
}
var curriedAdd = curry(add,5)
curriedAdd(6)   //11
```


