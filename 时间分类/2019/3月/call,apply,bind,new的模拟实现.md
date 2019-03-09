# call,apply,bind,new的模拟实现

## call

call的功能:call()方法在使用一个指定的this值和若干个指定的参数值的前提下调用某个函数或方法

使用示例:

``` javascript
var foo = {
    value:1
}
function bar(){
    console.log(this.value)
}
bar.call(foo) //1
```

### 绑定作用域

要实现call函数的第一步,我们需要将bar函数绑定作用域,如下:

```  javascript
Function.prototype.call1 = function(context){
    context.fn = this
    var result = context.fn()
    delete context.fn
    return result
}
```

### 绑定参数

``` javascript
Function.prototype.call = function(context,args){
    context.fn = this
    var args = [];
    for(var i = 1, len = arguments.length; i < len; i++) {
        args.push('arguments[' + i + ']');
    }
    var result = eval('context.fn('+ args +')')
    delete context.fn
    return result
}
```

### 设置context

在实际使用时,当传入的第一个参数为空,或为null时,context默认指向window

``` javascript
Function.prototype.call = function(context,args){
    var context = context || window
    context.fn = this
    var args = [];
    for(var i = 1, len = arguments.length; i < len; i++) {
        args.push('arguments[' + i + ']');
    }
    var result = eval('context.fn('+ args +')')
    delete context.fn
    return result
}
```

## apply

apply的功能:apply()方法在使用一个指定的this值和一个参数值数组的前提下调用某个函数或方法

使用示例:

``` javascript
var obj = {
    value:1
}
function foo(name,age){
    console.log(name+','+age+','+this.value)
}
foo.apply(obj,['xiaobaicai','xiaohei'])
```

### 模拟实现

apply和call方法的实现相差不大,这里直接给代码

``` javascript
Function.prototype.apply1 = function(context,arr){
    var context = context || window
    context.fn = this
    var result
    if(!arr){
        result = context.fn()
    }else{
        var args = []
        for(var i =0,len=arr.length;i<len;i++){
            args.push(arr[i])
        }
        result = eval('context.fn('+args+')')
    }
    delete context.fn
    return result
}
```

## bind

bind的功能:

* bind()会创建一个新函数。当这个新函数被调用时,bind()的第一个参数将会作为它运行时的this,之后的一序列参数将会在传递的实参前传入作为它的参数。
* 一个绑定函数也能使用new操作符创建对象:这种行为就像把原函数当成构造器。提供的this值被忽略,同时调用时的参数被提供给模拟函数

### 直接绑定

``` javascript
Function.prototype.bind1 = function(context){
    var self = this;
    return function(){
        return self.apply(context)
    }
}
```

### 传递参数

``` javascript
Function.prototype.bind2 = function(context){
    var self = this
    var args = Array.prototype.slice.call(arguments,1)
    return function(){
        var bindArgs = Array.from(arguments)
        return self.apply(context,args.concat(bindArgs))
    }
}
```

### 可以返回构造函数

``` javascript
Function.prototype.bind3 = function(context){
    var self = this
    var args = Array.prototype.slice.call(arguments,1)

    var fBound = function(){
        var bindArgs = Array.from(arguments)
        return self.apply(this instanceof fBound ? this : context,args.concat(bindArgs))
    }

    fBound.prototype = this.prototype
    return fBound
}
```

### 优化构造函数效果

``` javascript
Function.prototype.bind2 = function (context) {

    var self = this;
    var args = Array.prototype.slice.call(arguments, 1);

    var fNOP = function () {};

    var fBound = function () {
        var bindArgs = Array.prototype.slice.call(arguments);
        return self.apply(this instanceof fNOP ? this : context, args.concat(bindArgs));
    }

    fNOP.prototype = this.prototype;
    fBound.prototype = new fNOP();
    return fBound;
}
```

## new

``` javascript
function objectFactory(){
    var obj = new Object()
    Constructor = [].shift.call(arguments)

    obj.__proto__ = Constructor.prototype

    var ret = Constructor.apply(obj,arguments)
    return typeof ret === 'object' ? ret:obj
}
```
