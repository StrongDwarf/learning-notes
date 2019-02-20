# 《JavaScript设计模式》复习

今天,再次翻阅了一遍《JavaScript设计模式》这本书,发现其中还是有一些东西掌握的不是很好,现在此记录下来。

## 惰性模式

惰性模式:减少每次代码执行时的重复性分支判断，通过对对象重定义来屏蔽原对象中的分支判断。

惰性模式主要是将原来每次代码执行都要做的判断变成只进行一次判断，并且在判断后重定义原对象。
惰性模式有两种重定义原对象的方式，一种是在函数加载时重定义，一种是在函数第一次执行时重定义。用法如下:

``` javascript
var A = {}

//未使用惰性模式时
A.on = function(dom,type,fn){
    if(dom.addEventListener){
        dom.addEventListener(type,fn,false)
    }else if(dom.attachEvent){
        dom.attachEvent('on'+type,fn)
    }else{
        dom['on'+type] = fn
    }
}

//加载时重定义
A.on = function(dom,type,fn){
    if(dom.addEventListener){
        return function(dom,type,fn){
            dom.addEventListener(type,fn,false)
        }
    }else if(dom.attachEvent){
        return function(dom,type,fn){
            dom.attachEvent('on'+type,fn)
        }
    }else{
        return function(dom,type,fn){
            dom['on'+type] = fn
        }
    }
}()

//第一次执行时重定义
A.on = function(dom,type,fn){
    if(dom.addEventListener){
        A.on = function(dom,type,fn){
            dom.addEventListener(type,fn,false)
        }
    }else if(dom.attachEvent){
        A.on = function(dom,type,fn){
            dom.attachEvent('on'+type,fn)
        }
    }else{
        A.on = function(dom,type,fn){
            dom['on'+type] = fn
        }
    }

    A.on()
}
```

这种模式在减少多次判断的时候特别有用

## 函数柯里化

函数柯里话的思想是对函数的参数分割，这有点像其他OO语言中的类的多态，就是根据传递参数的不同，可以让一个函数存在多种状态，只不过函数柯里化处理的是函数，因此要实现函数的柯里化是要以函数为基础的，借助柯里化伪造其他函数，让这些伪造的函数在执行时调用这个基函数完成不同的功能。

如下是一个函数柯里化器:

``` javascript
function curry(fn){
    let args = []
    [,...args] = arguments
    //闭包返回新函数
    return function(){
        let addArgs = [...arguments]
        let allArgs = [...args,...addArgs]
        //返回新函数
        return fn.apply(null,allArgs)
    }
}

//加法器
function add(num1,num2){
    return num1 + num2
}
//加5加法器
function add5(num){
    return add(5,num)
}

//函数柯里化创建加5加法器
var add5 = curry(add,5);
```

使用柯里化实现bind方法

``` javascript
//兼容各个浏览器
if(Function.prototype.bind === undefined){
    Function.prototype.bind = function(context){
        //数组slice方法
        var slice = Array.prototype.slice
        var args = slice.call(arguments,1)
        var that = this
        return function(){
            var addArgs = slice.call(arguments)
            var allArgs = args.concat(addArgs)
            return that.apply(context,allArgs)
        }
    }
}
```

## 等待者模式

等待者模式:通过对多个异步进程监听，来触发未来发生的动作

``` javascript
//等待对象
var waiter = function(){
        //注册了的等待对象容器
    var dfd = [],
        //成功回调方法容器
        doneArr = [],
        //失败回调方法容器
        failArr = [],
        //缓存Array方法Slice
        slice = Array.prototype.slice,
        //保存当前等待者对象
        that = this
    //监控对象类
    var Promise = function(){
        this.resolved = false
        this.rejected = false
    }

    //监控对象类原型方法
    Promise.prototye = {
        //解决成功
        resolve:function(){
            //设置当前监控对象解决成功
            this.resolved = true
            if(!dfd.length){
                return
            }
            for(var i = dfd.length - 1;i>=0;i--){
                //如果有任意一个监控对象没有被解决或解决失败则返回
                if(dfd[i] && !dfd[i].resolved || dfd[i].rejected){
                    return
                }
                //清楚监控对象
                dfd.splice(i,1)
            }
            //执行解决成功回调方法
            _exec(doneArr)
        },
        reject:function(){
            //设置当前监控对象解决失败
            this.rejected = true
            if(!dfd.length){
                return
            }
            dfd.splice(0)
            _exec(failArr)
        }
    }

    //创建监控对象
    that.Deferred = function(){
        return new Promise()
    }

    //回调执行方法
    function _exec(arr){
        var i = 0,
            len = arr.length;
        //遍历回调数组执行回调
        for(;i<len;i++){
            try{
                arr[i] && arr[i]()
            }catch(e){}
        }
    }

    //监控异步方法
    that.when = function(){
        //设置监控对象
        dfd = slice.call(arguments);
        //获取监控对象数组长度
        var i = dfd.length;
        //向前遍历监控对象，最后一个监控对象的索引值为length-1
        for(--i;i>=0;i--){
            //如果不存在监控对象,或者监控对象已经解决，或者不是监控对象
            if(!dfd[i] || dfd[i].resolved || dfd[i].rejected || !dfd[i] instanceof Promise){
                //清理内存，清除当前监控对象
                dfd.splice(i,1)
            }
        }
        return that
    }

    //解决成功回调函数添加方法
    that.done = function(){
        doneArr = doneArr.concat(slice.call(arguments))
        return that
    }

    //解决失败回调函数添加方法
    that.fail = function(){
        failArr = failArr.concat(slice.call(arguments))
        return that
    }
}
```

