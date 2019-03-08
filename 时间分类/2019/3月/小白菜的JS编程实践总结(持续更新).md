# 小白菜的JS编程实践总结(持续更新)

前端性能优化请点[这里](https://github.com/StrongDwarf/learning-notes/blob/master/时间分类/2019/3月/小白菜的前端性能优化总结.md),这篇博客只记录编码实践。

[目录](#目录)

* [代码约定](#代码约定)
  * [可读性](#可读性)
  * [变量命名](#变量命名)
  * [变量类型透明](#变量类型透明)
* [编程实践](#编程实践)
  * [解耦CSS/JavaScript](#解耦CSS/JavaScript)
  * [解耦应用逻辑/事件处理程序](#解耦应用逻辑/事件处理程序)
  * [尊重对象所有权](#尊重对象所有权)
  * [避免全局变量](#避免全局变量)
  * [避免与null进行比较](#避免与null进行比较)
  * [使用常量](#使用常量)
  * [使用内部变量引用外部变量](#使用内部变量引用外部变量)
  * [避免with语句](#避免with语句)
  * [避免不必要的属性查找](#避免不必要的属性查找)
  * [优化循环](#优化循环)
  * [避免双重解释](#避免双重解释)
  * [使用switch替代复杂的if~else语句](#使用switch替代复杂的if~else语句)
  * [最小化语句数](#最小化语句数)
  * [最小化现场更新](#最小化现场更新)
  * [使用innerHTML代替JS创建DOM](#使用innerHTML代替JS创建DOM)
  * [使用事件代理](#使用事件代理)
  * [使用arguments.callee解耦递归函数](#使用arguments.callee解耦递归函数)
  * [使用尾调用优化递归](#使用尾调用优化递归)
  * [使用函数懒加载避免重复判断](#使用函数懒加载避免重复判断)
  * [使用函数节流避免重复执行](#使用函数节流避免重复执行)
  * [使用模板字符串代替字符串拼接](#使用模板字符串代替字符串拼接)
  * [分离样式的读写避免重绘重排](#分离样式的读写避免重绘重排)
* [未来可能被支持的优秀编程实践]
  * [使用可选链式调用访问元素属性](#使用可选链式调用访问元素属性)

## 代码约定

### 可读性

* 1,函数和方法:每个函数都应该包含一个注释,描述其目的和用于完成任务所可能使用的算法。陈诉实现的假设也很重要,如参数代表什么,函数是否有返回值
* 2,大段代码:用于完成单个任务的多行代码应该在前面放一个描述任务的注释
* 3,复杂的算法:如果使用了一种独特的方式解决了某个问题,则要在注释中解释你是如何做的。这不仅仅可以帮助其他浏览你代码的人，也能在下次你自己查阅代码的时候帮助理解
* 4,Hack:因为存在浏览器差异,JavaScript代码一般会包含一些Hack。不要假设其他人在看代码的时候能够理解Hack所要应付的浏览器问题。因为某种浏览器无法使用普通的方法，所以你需要用一些不同的方法，那么请将这些信息放在注释中。这样可以减少出现这种情况的可能性:有人偶然看到了你的hack,然后"修正"了它,最后重新引入了你本来修正了的错误。

### 变量命名

* 1,变量名应为名词如car,person
* 2,函数名应该以动词开始,如getName().返回布尔类型值的函数一般以is开头,如isEnable()
* 3,变量和函数都应使用合乎逻辑的名字,不要担心长度

### 变量类型透明

1:通过初始化指定变量类型

``` JavaScript
var found = false;      //布尔型
var count = -1;         //数字
var name = "";          //字符串
var person = null;      //对象
```

2:使用匈牙利标记法

JavaScript中最传统的匈牙利表示法是使用单个字符表示基本类型:"o"代表对象,"s"代表字符串,"i"代表整数,"f"代表浮点数,"b"代表布尔型。如下

``` javascript
var bFound;     //布尔型
var iCount;     //整数
var sName;      //字符串
var oPerson;    //对象
```

## 编程实践

### 解耦CSS/JavaScript

通过设置element的class而不是直接设置style减少css对javascript的耦合

CSS对JavaScript的紧密耦合

``` javascript
element.style.color = "red"
element.style.backgroundColor = "blue"
```

CSS对JavaScript的松散耦合

``` javascript
element.className = "edit"
```

如果一定要通过设置style来修改元素样式,可以使用cssText修改。如下:

``` javascript
element.style.cssText = "color:red;background-color:blue"
```

### 解耦应用逻辑/事件处理程序

不直接在事件处理程序中编写应用逻辑,而是将事件处理程序和应用逻辑分离。以此减少耦合。

如下是事件处理程序和应用逻辑高度耦合的例子:

``` javascript
/**
 *@name:handleKeyPress(event) 处理键盘按键事件,并在输入回车键时显示错误信息
 *@param:event  事件
 *@return:undefined
 **/
function handleKeyPress(event){
    event = EventUtil.getEvent(event);
    if(event.keyCode === 13){
        var target = EventUtil.getTarget(event);
        var value = 5 * parseInt(target.value);
        if(value > 10){
            document.getElementById("error-msg").style.display = "block";
        }
    }
}
```

解耦后

``` javascript
/**
 *@name:validateValue(value) 验证值是否大于2,并在大于2时显示错误信息
 *@param:value Number类型
 *@return:undefined
 **/
function validateValue(value){
    value = 5 * parseInt(value);
    if(value > 10){
        document.getElementById("error-msg").style.display = "block";
    }
}

/**
 *@name:handleKeyPress(event) 处理键盘按键事件
 *@param:event 事件
 **/
function handleKeyPress(event){
    event = EventUtil.getEvent(event);
    if(event.keyCode === 13){
        var target = EventUtil.getTarget(event);
        validateValue(target.value)
    }
}
```

### 尊重对象所有权

如果你不负责创建或维护某个对象,它的对象或者它的方法，那么你就不能对它们进行修改。更具体的说:

* 不要为实例或原型添加属性
* 不要为实例或原型添加方法
* 不要重定义已存在的方法

若必须要使用不属于你的对象,可以创建该对象的副本并使用,复制对象可以使用如下函数:

``` javascript
/**
 *@name:deepCopy
 *@param:original:复制的目标
 *@return:object:返回一个对象,是复制源对象属性后生成的一个新对象
 **/
function deepCopy(original,target){
    var target = target||{};
    for(var propName in original){
        if(typeof original[propName] === 'object'){
            target[propName] = (original[propName].constructor === 'Array')?[]:{}
            deepCopy(original[propName],target[propName])
        }else{
            target[propName] = original[propName]
        }
    }
    return target
}
```

### 避免全局变量

与尊重对象所有权一样,在开发时要尽量避免全局变量。对于变量的作用域,要将其控制在变量的最小作用域中。具体如下:

* 变量不能声明在高于其作用区间的作用域中
* 尽量不使用全局变量，如果一个变量一定要在全局中使用,可以将其设置为自己负责的模块的变量,并在全局中通过访问模块的属性的形式访问该变量

如下:

``` javascript
//错误的写法
var author = 'xiaobaicai'
//正确的写法
var myModule = {
    author:'xiaobaicai'
}
```

### 避免与null进行比较

避免与null进行比较,可以尝试使用如下方法替换:

* 如果一个值为引用类型,使用instanceof操作符检查其构造函数
* 如果值应为一个基本类型，使用typeof检查其类型
* 如果是希望对包含某个特定的方法名，则使用typeof操作符确保指定名字的方法存在于对象上

使用null进行判断,

``` javascript
function sortArray(values){
    //如果values是number,String等类型也可以通过
    if(values != null){
        values.sort(comparator)
    }
}
```

替换成其他判断方式

``` javascript
function sortArray(values){
    if(values instanceof Array){
        values.sort(comparator)
    }
}
```

### 使用常量

使用常量能够将数据从应用逻辑分离出来。并且给数据赋予命名，简单直观。

未使用常量时，如下:

``` javascript
function validate(value){
    if(!value){
        alert("Invalid value!")
        location.href = "/errors/invalid.php"
    }
}
```

使用常量时，如下:

``` javascript
var Constants = {
    INVALID_VALUE_MSG:"Invalid value!",
    INVALID_VALUE_URL:"/errors/invalid.php"
}
function validate(value){
    if(!value){
        alert(Constants.INVALID_VALUE_MSG)
        location.href = Constants.INVALID_VALUE_URL
    }
}
```

在将数据和逻辑分离时候,要注意的值类型如下:

* 重复值:任何在多处用到的值都应抽取为一个常量。这就限制了当一个值变了而另一个没变的时候会造成的错误。这也包含了CSS类名
* 用户界面字符串:任何用于显示给用户的字符串，都应该抽取出来以方便国际化
* URLs:在web应用中,资源位置很容易变更,所以推荐用一个公共地方存放所有的URL
* 任何可能更改的值:每当使用字面量值时,都要思考这个值在未来是否会变化。如果答案是"是",那么这个值就应该被提取出来作为一个常量

### 使用内部变量引用外部变量

对经常要用到的外部变量,可使用一个内部变量指向外部变量。在下次内部作用域中使用该变量时,可直接使用该内部变量，缩小查找作用域。

未使用内部变量引用外部变量之前,如下:

``` javascript
function updateUI(){
    var imgs = document.getElementsByTagName("img")
    for(var i =0,len=imgs.length;i<len;i++){
        imgs[i].title = document.title + " image " + i
    }
    var msg = document.getElementById("msg")
    msg.innerHTML = "Update complete"
}
```

使用内部变量引用外部变量之后,如下:

``` javascript
function updateUI(){
    var doc = document
    var imgs = doc.getElementsByTagName("img")
    for(var i =0,len=imgs.length;i<len;i++){
        imgs[i].title = doc.title + " image " + i
    }
    var msg = doc.getElementById("msg")
    msg.innerHTML = "Update complete"
}
```

未使用内部变量引用外部变量之前,每次使用document都会在全局作用域中查找,耗时。而在使用doc引用document。只用,只在内部作用域中查找

### 避免with语句

with语句会创建自己的作用域，因此会增加其中执行的代码的作用域链的长度。由于额外的作用域链查找，在with语句中执行的代码肯定会比外面执行的代码要满。

在使用with语句的地方，可以使用局部变量完成同样的事情。

``` javascript
//使用with
function updateBody(){
    with(document.body){
        alert(tagName)
        innerHTML = "Hello World"
    }
}
//使用局部变量代替with
function updateBody(){
    var body = document.body
    alert(body.tagName)
    body.innerHTML = "hello world"
}
```

### 避免不必要的属性查找

1,使用数组下标查找,优于使用对象属性查找

``` javascript
//使用数组下标查找,时间复杂度 O(1)
var values = [5,10]
var sum = values[0] + values[1]
//使用对象属性查找,时间复杂度 O(n)
var values = {first:5,second:10}
var sum = values.first + values.second
```

2,一旦多次用到对象属性,应将其存储在局部变量中

``` javascript
//未使用局部变量,属性查找6次
var query = window.location.href.substring(window.location.href.indexOf("?"))
//使用局部变量,属性查找3次
var url = window.location.href
var query = url.substring(url.indexOf("?"))
```

### 优化循环

* 1,减值迭代,大多数循环使用一个从0开始,增加到某个特定值的迭代器。在很多情况下,从最大值开始,在循环中不断减值的迭代器更加高效
* 2,简化终止条件,由于每次循环过程都会计算终止条件，所以必须保证它尽可能块。也就是说避免属性查找或其他O(n)操作
* 3,简化循环体,循环体是执行最多的,所以要确保其被最大限度地优化。确保没有某些可以被很容易移除循环的密集计算
* 4,使用后测试循环,最常用for循环和while循环都是前测试循环。而如do-while这种后测试循环，可以避免最初终止条件的计算，因此运行更快
* 5,展开循环,当循环的次数是确定的,消除循环并使用多次函数调用往往更快

如下:

``` javascript
//未优化循环
for(var i=0;i<values.length;i++){
    process(values[i])
}
//使用减值迭代,将对value.length的O(n)次查找降为O(1)次查找
for(var i=values.length -1;i>=0;i--){
    process(values[i])
}
//使用后测试循环,因减少了可读性,不是很推荐使用
var i = values.length - 1;
if(i>-1){
    do{
        process(values[i])
    }while(--i >= 0)
}
```

展开循环,当循环次数是确定的,直接多次调用函数优于使用循环调用

``` JavaScript
//使用循环调用
for(var i =3;i>=0;i--){
    process(i)
}
//直接调用
process(1)
process(2)
process(3)
```

### 避免双重解释

当JavaScript代码想解析JavaScript的时候就会存在双重解释惩罚。当使用eval()函数或者是Function构造函数以及使用setTimeout()传一个字符串参数时都会发生这种情况。

### 使用switch替代复杂的if~else语句

当有一系列复杂的if-else语句时，可以转换成单个switch语句则可以得带更快的代码。同时,可以将case语句按照最可能的到最不可能的顺序进行组织，来进一步优化switch语句

### 最小化语句数

JavaScript代码中的语句数量也影响所执行的操作的速度。完成多个操作的单个语句要比完成单个操作的多个语句快。所以，就要找出可以组合在一起的语句，以减少脚本整体的执行时间。

1:多个变量声明

``` javascript
//优化前 4个语句,很浪费
var count = 5
var color = "blue"
var values = [1,2,3]
var now = new Date()
//优化后,一个语句
var count = 5,
    color = "blue",
    values = [1,2,3],
    now = new Date()
```

2:插入迭代值

``` javascript
//优化前
var name = values[i]
i++
//优化后
var name = values[i++]
```

3:使用数组和对象字面量

``` javascript
//优化前,
var values= new Array()
values[0] = 123
values[1] = 456
values[2] = 789
var person = new Object()
person.name = "xiaocaicai"
person.age = 22
person.sayName = function(){
    console.log(this.name)
}

//优化后
var values = [123,456,789]
var person = {
    name:"xiaobaicai",
    age:22,
    sayName:function(){
        console.log(this.name)
    }
}
```

### 最小化现场更新

将对DOM的连续多次更新缩减为一个。

``` javascript
//优化前
var list = document.getElementById("myList"),
    item,
    i;
for(i = 0;i<10;i++){
    item = document.createElement("li")
    list.appendChild(item)
    item.appendChild(document.createTextNode("Item " + i))
}
//优化后
var list = document.getElementById("myList"),
    fragment = document.createDocumentFragment(),
    item,
    i;
for(i = 0;i<10;i++){
    item = document.createElement("li")
    fragment.appendChild(item)
    item.appendChild(document.createTextNode("Item " + i))
}
list.appendChild(item)
```

### 使用innerHTML代替JS创建DOM

有两种在页面上创建DOM节点的方法:使用诸如createElement()和appendChild()之类的DOM方法,以及使用innerHTML。对于小的DOM更改而言，这两种方法效率都差不多。然而,对于大的DOM更改，使用innerHTML要比使用标准DOM方法创建同样的DOM结构快得多。
当把innerHTML设置为某个值时，后台会创建一个HTML解析器,然后使用内部的DOM调用来创建DOM结构，而非基于JavaScript的DOM调用。由于内部方法是编译好的而非解释执行的，所以执行快得多。

### 使用事件代理

略

### 使用arguments.callee解耦递归函数

``` JavaScript
//解耦前,函数和函数名耦合
var foo = function(num){
    if(num === 1){
        return 1
    }else if(num > 1){
        return num*foo(num-1)
    }else{
        console.error("num is invalid")
    }
}
//解耦后,函数和函数名无耦合
var foo = function(num){
    if(num === 1){
        return 1
    }else if(num > 1){
        return num*argument.callee.call(this,num-1)
    }else{
        console.error("num is invalid")
    }
}
```

### 使用尾调用优化递归

``` javascript
//未使用尾调用优化
var foo = function(num){
    if(num === 1){
        return 1
    }else if(num > 1){
        return num*foo(num-1)
    }
}
//使用尾调用优化
var foo = function(num,total){
    if(num === 1){
        return total
    }
    return factorial(num-1,num*total)
}
```

尾调用能优化递归的原理是:函数调用会在内存中形成以个"调用记录",又称"调用帧",仅保存调用位置和内部变量等信息。如果在函数A的内部调用函数B,那么在A的调用帧上方还会形成以个B的调用帧。等到B运行结束,将结果返回到A,B的调用帧才会消失。如果函数B内部还调用了函数C，那就还有一个C的调用帧，以此类推。所有的调用帧就形成一个"调用栈"。

### 使用函数懒加载避免重复判断

``` JavaScript
//优化前,每次执行foo函数前都要进行判断
function foo(){
    if(document.addEventListener){
        doA()
    }else if(document.attach){
        doB()
    }else{
        doC()
    }
}
//优化后，函数加载时执行一次判断
var foo = (function(){
    if(document.addEventListener){
        foo = doA()
    }else if(document.attach){
        foo = doB()
    }else{
        foo = doC()
    }
})()
//优化后，函数第一次执行时执行判断,之后都不执行判断
var foo = function(){
    if(document.addEventListener){
        foo = doA()
    }else if(document.attach){
        foo = doB()
    }else{
        foo = doC()
    }
    foo()
}
```

### 使用函数节流避免重复执行

``` javascript
function throttle(method,context){
    clearTimeout(method.tId)
    method.tId = setTimeout(function(){
        method.call(context)
    },200)
}
```

### 使用模板字符串代替字符串拼接

### 分离样式的读写避免重绘重排

``` javascript
//优化前
div.style.left = div.offsetLeft + 10 + "px"
div.style.top = div.offsetTop + 10 + "px"
//优化后
var left = div.pffsetLeft
var top = div.offsetTop
div.style.left = left + 10 + "px"
div.style.top = top + 10 + "px"
```

DOM变动和样式变动都会触发重新渲染,但是游览器会尽量将所有的变动集中在一起,排列成一个队列，然后一次性执行，尽量避免多次重新渲染。但是每次获取布局信息时,浏览器一定要获得当前最新的布局信息。所以,如果在这之前有DOM变动和样式变动,浏览器会立即重绘(和重排),然后然后最新的样式信息。如上,优化前,会触发两次重绘,第一次在div.style.left赋值后,第二次在div.style.top赋值后。优化后则只会触发一次重绘,在div.style.top之后。

会强制队列刷新的方法还有如下方法:

* offsetTop,offsetLeft,offsetWidth,offsetHeight
* scrollTop,scrollLeft,scrollWidth,scrollHeight
* clientTop,clientLeft,clientWidth,clientHeight
* getComputedStyle()
* getBoundingClientRect

## 未来可能被支持的优秀编程实践

### 使用可选链式调用访问元素属性




## 文章参考

[文章参考](#文章参考)

* [《javascript高级程序设计》]
* [《代码大全》]
* [《javascript之美》]
* [《编写可维护的JavaScript》]
* [小白菜的博客](https://github.com/StrongDwarf/learning-notes)
* [【高性能JS】重绘、重排与浏览器优化方法](https://juejin.im/post/5c7f80f4e51d4541c00218b0)


``` javascript

function a(){
    var scope = "a scope"
    return function(){
        console.log(scope)
    }
}
var scope = "global scope"
a()()