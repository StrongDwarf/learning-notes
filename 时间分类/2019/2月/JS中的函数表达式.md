# JS中的函数表达式

目录:
 * [函数表达式的特征](# 函数表达式的特征)
 * [使用函数实现递归](# 使用函数实现递归)
 * [使用闭包定义私有变量](# 使用闭包定义私有变量)

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
