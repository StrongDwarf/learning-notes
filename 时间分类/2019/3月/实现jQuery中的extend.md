# 实现jQuery中的extend

## 前言

jQuery的extend是jQuery中应用非常多的一个函数,今天我们一边看jQuery的extend的特性,一边实现一个extend

## extend基本用法

先来看看extend的功能,应用jQuery官网

``` javascript
Merge the contents of two or more objects together into the first object
```

翻译过来就是,合并两个或者更多的对象的内容到第一个对象中

用法如下:

``` javascript
jQuery.extend(target[,object1][,object N])
```

第一个参数target,表示要拓展的目标,我们就称它为目标对象吧

后面的参数,都传入对象,内容都会复制到目标对象中,我们就称它们为待复制对象吧。

举个例子：

``` javascript
var obj1 = {
    a:1,
    b:{b1:1,b2:2}
}
var obj2 = {
    b:{b1:3,b3:4},
    c:3
}
var obj3 = {
    d:4
}

console.log($extend(obj1,obj2,obj3))

//{
//    a:1,
//    b:{b1:3,b3:4},
//    c:3,
//    d:4
//}
```

当两个对象出现相同字段的时候,后者会覆盖前者,而不会进行深层次的覆盖

## extend第一版

``` javascript
function extend1(){
    var name,options,copy,
        length = arguments.length,
        i = 1,
        target = arguments[0]

    for(;i<length;i++){
        options = arguments[i]
        for(name in options){
            copy = options[name]
            if(copy !== undefined){
                target[name] = copy
            }
        }
    }
    return target
}
```

## extend深拷贝

那如何进行深层次的复制呢?jQuery v1.1.4加入了一个新的用法

``` javascript
jQuery.extend([deep],target,object1[,objectN])
```

也就是说,函数的第一个参数就可以传入一个布尔值,如果为true,我们就会进行深拷贝,false依然当做浅拷贝,这个时候,target就往后移动到第二个参数

如下:

``` javascript
var obj1 = {
    a:1,
    b:{b1:1,b2:2}
}

bar obj2 = {
    b:{b1:3,b3:4},
    c:3
}
var obj3 = {
    d:4
}

console.log($.extend(true,obj1,obj2,obj3))

/*
{
    a:1,
    b:{b1:3,b2:2,b3:4},
    c:3,
    d:4
}
*/
```

因为采用了深拷贝,会遍历到更深的层次进行添加和覆盖

## extend第二版

实现深拷贝的功能,值得注意的是:

* 需要根据第一个参数的类型,确定target和要合并的对象的下标起始值
* 如果是深拷贝,根据copy的类型递归extend

``` javascript
function extend(){
    //默认不进行深拷贝
    var deep = false,
        name,options,src,copy,
        length = arguments.length,
        //记录要复制的对象的下标
        i = 1,
        //第一个参数不传布尔值的情况下,target默认是第一个参数
        target = arguments[0] || {}

    //如果第一个参数是布尔值
    if(typeof target === 'boolean'){
        deep = target
        target = arguemnts[i] || {}
        i++
    }

    //如果target不是对象,我们是无法复制的,所以设为{}
    if(typeof target !== "object" && !isFunction(target)){
        target = {}
    }

    //循环遍历复制
    for(;i<length;i++){
        //获取当前对象
        options = arguments[i]

        if(options){
            for(name in options){
                //目标属性值
                src = target[name]
                //要复制的对象的属性值
                copy = options[name]

                if(deep && copy && typeof copy == 'object'){
                    //递归调用
                    target[name] = extend(deep,src,copy)
                }else if(copy !== undefined){
                    target[name] = copy
                }
            }
        }
    }
    return target
}
```

## target是函数

在我们的实现中,typeof target必须等于object,我们才会在这个target基础上进行拓展,然而我们用typeof判断一个函数时.会返回function,也就是说,我们无法在一个函数上进行拓展!

然而,实际上,在underscore的实现中,underscore的各种方法便是挂在了函数上!

所以我们在这里还要判断是不是函数,这时候我们便可以使用isFunction函数

``` javascript
function isFunction(value){
    return typeof value === "function" || Object.prototype.toString.call(value) === "[Object Function]"
}
```

做如下修改:

``` javascript
if(typeof target !== "object" && !isFunction(target)){
    target = {}
}
```

## 类型不一致

其实我们实现的方法有个小bug,不信我们写个demo:

``` javascript
var obj1 = {
    a:1,
    b:{
        c:2
    }
}
var obj2 = {
    b:{
        c:[5]
    }
}
var d = extend(true,obj1,obj2)
```

我们预期会返回这样一个对象:

``` javascript
{
    a:1,
    b:{
        c:[5]
    }
}
```

然而返回了这样一个对象:

``` javascript
{
    a:1,
    b:{
        c:{
            0:5
        }
    }
}
```

为了解决这个问题,我们需要对目标属性值和待复制对象的属性值进行判断:

判断目标属性值跟要复制的对象的属性值类型是否一致:

* 如果要复制对象属性值类型为数组,目标属性值不为数组的话,目标属性值就设为[]
* 如果待复制对象属性值为对象,目标属性值不为对象的话,目标属性值就设置为{}

``` javascript
var clone,copyIsArray
//...
if(deep && copy && (isPlainObject(copy) || (copyIsArray = Array.isArray(copy)))){
    if(copyIsArray){
        copyIsArray = false
        clone = src && Array.isArray(src) ? src : []
    }else{
        clone = src && isPlainObject(src) ? src : {}
    }
    target[name] = extend(deep,clone,copy)
}else if(copy !== undefined){
    target[name] = copy
}
```

## 循环引用

实际上,我们还可能遇到一个循环引用的问题,举个例子:

``` javascript
var a = {name:b}
var b = {name:a}
var c = extend(a,b)
console.log(c)
```

上面的代码中,我们会得到一个无限引用的例子

为了避免这个问题,我们可以判断要复制的对象属性是否等图targte,如果等于,我们就跳过:

``` javascript
//...
src = target[name]
copy = options[name]

if(target === copy){
    continue
}
//...
```

## 最终代码

``` javascript
var class2type = {}
var toString = class2type.toString
var hasOwn = class2type.hasOwnProperty

function isFunction(obj){
    return typeof obj === 'function' || Object.prototype.toString.call(obj) === "[object Function]"
}

function isPlainObject(obj){
    var proto,Ctor
    if(!obj || toString.call(obj) !== "[object Object]"){
        return false
    }
    proto = Object.getPrototypeOf(obj)
    if(!proto){
        return true
    }
    Ctor = hasOwn.call(proto,"constructor") && proto.constructor
    return typeof Ctor === "function" && hasOwn.toString.call(Ctor) === hasOwn.toString.call(Object)
}

function extend(){
    //默认不进行深拷贝
    var deep = false,
        name,options,src,copy,clone,copyIsArray,
        length = arguments.length,
        i = 1,
        target = arguments[0] || {}
    if(typeof target === 'boolean'){
        deep = target
        target = arguments[i] || {}
        i++
    }

    if(typeof target !== "object" && isFunction(target)){
        target = {}
    }

    for(;i<length;i++){
        options = arguments[i]
        if(options != null){
            for(name in options){
                src = target[name]
                copy = options[name]
                if(target === copy){
                    continue
                }
                if(deep && copy && (isPlainObject(copy) || (copyIsArray = Array.isArray(copy)))){
                    if(copyIsArray){
                        copyIsArray = false
                        clone = src && Array.isArray(src) ? src : []
                    }else{
                        clone = src && isPlainObject(src) ? src : {}
                    }
                    target[name] = extend(deep,clone,copy)
                }else if(copy !== undefined){
                    target[name] = copy
                }
            }
        }
    }
    return target
}
```