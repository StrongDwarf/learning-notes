# BOM

ECMAScript是JavaScript的核心，但如果要在Web中使用JavaScript，那么BOM(浏览器对象模型)则无疑才是真正的核心。BOM提供了很多对象，用于访问浏览器的功能，这些功能与任何网页内容无关。

## 目录

[文章目录](文章目录)

* [window对象](# window对象)
* [location对象](# location对象)
* [navigator对象](# navigator对象)
* [screen对象](# screen对象)
* [history对象](# history对象)
* [参考](# 参考)

## window对象

BOM的核心是window，它表示浏览器的一个实例。在浏览器中,window对象有双重角色，它即是通过JavaScript访问浏览器窗口的一个接口，又是ECMAScript贵的的global对象。这意味着在网页中定义的任何一个对象，变量,和函数，都一window作为global对象，因此有权访问parseInt()等方法

### 1.1 全局作用域

window作为浏览器中的Global对象,所有在全局作用域中定义的函数和变量都可以作为window的属性被访问，如下

``` javascript
function sayHi(){console.log('Hi')}

window.sayHi   //f sayHi(){console.log('Hi')}
```

不过在全局作用域中声明的变量和直接作为window的属性被声明的变量还有有点不同的，如下:

``` javascript
var a = 'a'
window.b = 'b'
Object.getOwnPropertyDescriptor(window,'a')
//{value: 'a', writable: true, enumerable: true, configurable: false}
Object.getOwnPropertyDescriptor(window,'b')
//{value: 'a', writable: true, enumerable: true, configurable: true}
delete window.a     //false
delete window.b     //true
```

直接在全局作用域中声明的变量作为window的属性是不可配置的,即不可以使用delete删除,而作为window的属性被声明的变量是可配置的，即可被delete删除

### 1.2 窗口关系及框架

如果页面中包含框架,则每个框架都拥有自己的window对象，并且保存在frames集合中。在frames集合中，可以通过数值索引(从0开始，从左至右，从上到下)或者框架名称来访问相应的window对象。每个window对象都有一个name属性，其中包含框架的名称。下面是一个包含框架的页面:

``` html
<html>
    <head>
    <head>
    <frameset rows="160,*">
        <frame src="frame.htm" name="toFrame">
        <frameset col="50%,50%">
            <frame src="anotherframe.htm" name="leftFrame">
            <frame src="yetanotherframe.htm" name="rightFrame">
        </frameset>
    </frameset>
</html>
```

以上代码创建了一个框架集，其中一个框架居上，两个框架居下。对这个例子而言，可以通过window.frames[0]或者window.frames["topFrame"]来引用上方的框架。不过，恐怕你最好使用top而非window来引用这些框架。(例如，通过top.frames[0])
我们知道,top对象始终指向最高(最外)层的框架，也就是浏览器窗口。使用它可以确保在一个框架中正确地访问另一个框架。因为对于在一个框架中编写的任何代码来说，其中的window对象指向的都是那个框架的特定实例，而非最高层的框架。
与top相对的另一个window对象是parent。顾名思义，parent(父)对象始终指向当前框架的直接上层框架。
与框架有关的最后一个对象是self,它始终指向window,实际上,self和window对象可以互换使用。引入self对象的目的只是为了与top和parent对象对应起来，因此他不格外包含其他值。

值得注意的是,在使用框架的情况下,浏览器中会存在多个Global对象，在每个框架中定义的全局变量会自动成为框架中window对象的属性。由于每个window对象都包含原生类型的构造函数，因此每个框架都有一套自己的构造函数，这些构造函数一一对应，但并不相等。所以在使用框架的情况下谨慎使用instanceof运算符

### 1.3 窗口位置

关于窗口位置,不同的浏览器区别有点大,略恶心，可以使用下面的代码获取窗口左边和上边的位置

``` javascript
var leftPos = (typeof window.screeLeft == "number")?window.screenLeft : window.screenX
var topPos = (typeof window.screenTop == "number")?window.screenTop : window.screenY
```

### 1.4 窗口大小

关于窗口大小,同样比较恶心,可以使用下面的代码获取页面视图大小

``` javascript
var pageWidth = window.innerWidth
var pageHeight = window.innerHeight

if(typeof pageWidth != "number"){
    if(document.compatMode == "CSS1Compat"){
        pageWidth = document.documentElement.clientWidth
        pageHeight = document.documentElement.clientHeight
    }else{
        pageWidth = document.body.clientWidth
        pageHeight = document.body.clientHeight
    }
}
```

### 1.5 导航和打开窗口

使用window.open()方法既可以导航到一个特定的URL，也可以打开一个新的浏览器窗口。这个方法接收4个参数:要加载的URL,窗口目标,一个特性字符串以及以个表示新页面是否取代浏览器历史记录中当前加载页面的布尔值。通常指传递第一个参数

使用方法如下:

``` javascript
window.open("http://www.baidu.com")
```

如果为window.open()传递了第二个参数,而且该参数是已有窗口或框架的名称，那么就会在具有该名称的窗口或框架中加载第一个参数指定的URL。
第二个参数也可以是下列任何一个特殊的窗口名称:——self,_parent,_top,_blank

#### 1,弹出窗口

如果给window.open()传递的第二个参数并不是一个已经存在的窗口或框架，那么该方法就会根据在第三个参数位置上传入的字符串创建一个新窗口或新标签页。如果没有传入第三个参数,那么就会打开一个带有全部默认设置(工具栏,地址栏和状态栏等)的新浏览器窗口(或者打开一个新标签页--根据浏览器设置)。在不打开新窗口的情况下，会忽略第三个参数。

第三个参数是一个逗号分隔的设置字符串，表示在新窗口中都显示哪些特性。可以设置的参数如下:

* fullscreen:yes或no,表示浏览器窗口是否最大化。仅限IE
* height:数值,表示新窗口的高度,不能小于100
* width:数值,表示新窗口的宽度,不能是负值
* left:数值,表示新窗口离屏幕左侧的距离
* location:yes或no,表示是否在浏览器窗口中显示地址栏。不同浏览器的默认值不同
* menubar:yes或no,表示是否在浏览器窗口中显示菜单栏。默认值为no
* resizable:yes或no,表示是否可以通过拖动浏览器窗口的边框改变其大小。默认值为no
* scrollbars:yes或no,表示如果内容在视图中显示不下，是否允许滚动。默认值为no
* status:yes或no,表示是否在浏览器窗口中显示状态栏。默认值为no
* toolbar:yes或no,表示是否在浏览器窗口中显示工具栏。默认值为no
* top:数值,表示新窗口离屏幕顶部的距离

一般情况下,浏览器可能会禁止我们调整窗口大小和移动窗口,但是对于使用window.open()打开的窗口却没有这种限制。通过这个返回的对象，可以像操作其他窗口一样操作新打开的窗口。如下:

``` javascript
var w = window.open("http://www.baidu.com",'xiaobaicai','width=400,height=400,left=10,top=10')
w.resizeTo(500,500)
w.moveTo(20,20)
```

#### 2,弹出窗口屏蔽程序

大多数浏览器都会默认禁止弹出窗口,而且浏览器扩展或其他程序也能阻止弹出窗口。所以当需要弹出窗口时我们一般需要做一次判断，看是否成功弹出了窗口。
判断代码如下

``` javascript

var blocked = false
try{
    var w = window.open("http://www.baidu.com")
    if(w == null){
        blocked = true
    }
}catch(ex){
    blocked = true
}

if(blocked){
    alert("the popup was blocked!")
}
```

### 1.6 间歇调用和超时调用

间歇调用:setInterval(()=>{},time)
超时调用:setTimeout(()=>{},time)

清除间歇调用:clearInterval(intervalId)
清除超时调用:clearTimeout(timeoutId)

一般来说,在使用超时调用时，没有必要跟踪超时调用ID,因为每次执行代码之后，如果不再设置另一次超时调用，调用就会自行停止。一般认为，使用超时调用来模拟间歇调用是一种最佳模式。在开发环境下，很少使用真正的间歇调用，原因是后一个间歇调用可能会在前一个间歇调用结束之前启动。使用超时调用模拟间歇调用如下:

``` javascript
var num = 0
var max = 10
function incrementNumber(){
    num++
    if(num<max){
        setTimeout(incrementNumber,500)
    }else{
        alert("Done")
    }
}

setTimeout(incrementNumber,500)
```

### 1.7 系统对话框

浏览器可以通过alert(),confirm()和prompt()方法调用系统对话框像用户显示消息。系统对话框的外观由操作系统及浏览器设置决定，而不是有CSS决定。

值得注意的是，通过这几个方法打开的对话框都是同步和模态的。也就是说，显示这些对话框的时候代码会停止执行，而关掉这些对话框后代码又会恢复执行

## location对象

location对象提供了与当前窗口中加载的文档有关的信息，还提供了一些导航功能。值得注意的是,location对象在浏览器中同时被window.location和window.document.location引用。location对象的用处不只表现在它保存着当前文档的信息，还表现在它将URL解析为独立的片段，让开发人员可以通过不同的属性访问这些片段。

当文档的地址如下:

``` javascript
'https://www.google.com:8080/book.html?name=xiaobaicai&age=18#aiohowhoahf'
```

location对象的属性如下:

* href:https://www.google.com:8080/book.html?name=xiaobaicai&age=18#aiohowhoahf  表示文档的原始引用
* protocol:https        文档引用的协议
* hostname:www.google.com   不带端口的服务器名称
* host:www.google.com:8080  带端口的服务器名称
* pathname:/book.html       返回URL中的目录和文件名
* port:"8080"               端口号
* search:"？name=xiaobaicai&age=18"  查询字符串
* hash:"#aiohowhoahf"       返回URL中的hash(#号后跟0或多个字符)，如果URL中不包含散列，则返回空字符串

### 2.1 查询字符串参数

使用location.search可以获取查询的字符串。可以使用下面的函数将查询字符串转化为查询对象

``` javascript
function getQueryObject(){
    let queryObj = {}
    if(location.search){
        //获取查询字符串数组,类似['name=xiaobaicai','age=13']
        let queryArr = location.search.slice(1).split('&')
        queryArr.forEach((value,index,origin) => {
            let name = decodeURIComponent(value.split('=')[0])
            let val = decodeURIComponent(value.split('=')[1])
            queryObj[name] = val
        })
    }
    return queryObj
}
```

### 2.2 位置操作

要改变浏览器的位置，有以下几种情况

* 使用location.assign()方法并为其传递一个URL
* 使用location.replace()方法并为其传递一个URL
* 修改location属性的值

以上三种情况中，使用location.assign()方法和修改location属性的值都会立即打开新URL并在浏览器的历史记录中生成一条记录，而使用location.replace()方法时，也会立即打开新URL，但不会生成一条新记录，而是会将原页面历史记录中的URL修改为当前URL，此时是无法使用浏览器的后退按钮返回到上一个URL的

## navigator对象

navigator包含有关浏览器的信息,通常使用navigator对象的属性检测网页的浏览器类型

### 检测插件

检测浏览器中是否安装了特定的插件是一种最常见的检测例程。对于非IE浏览器，可以使用plugins数组来达到这个目的。该数组中的每一项都包含下列属性。

* name:插件的名称
* description:插件的描述
* filename:插件的文件名
* length:插件可处理的MIME类型数量

``` javascript
console.log(navigator.plugins)
/*
[{0: MimeType, application/x-google-chrome-pdf: MimeType, name: "Chrome PDF Plugin", filename: "internal-pdf-viewer", description: "Portable Document Format", length: 1},
{0: MimeType, application/pdf: MimeType, name: "Chrome PDF Viewer", filename: "mhjfbmdgcfjbbpaeojofohoefgiehjai", description: "", length: 1}
]
*/
```

所以检测插件可以使用下面的函数检测

``` javascript
function hasPlugin(name){
    name= name.toLowerCase()
    for(let i =0;i<navigator.plugins.length;i++){
        if(navigator.plugins[i].name.toLowerCase().indexOf(name) > 1){
            return true
        }
    }
    return false
}
```

对于IE浏览器，因为IE不支持Netscape式的插件。在IE中检测插件的唯一方式就是使用专有的ActivityXObject类型，并尝试创建一个特定类型的实例。如下:

``` javascript
function hasIEPlugin(name){
    try{
        new ActiveXObject(name)
        return true
    }catch(ex){
        return false
    }
}
```

如下是检测所有浏览器中的Flash插件的方法

``` javascript
//检测所有浏览器中的插件
function hasFlash(){
    var result = hasPlugin("Flash")
    if(!result){
        result = hasIEPlugin("ShockwaveFlash.ShockwaveFlash")
    }
    return result
}
```

### 注册处理程序

registerContentHandler(),registerProtocolHandler()

## screen对象

screen对象在变成中用处不大,基本上值用来表明客户端的能力，其中包括浏览器窗口外部的显示器的信息，如像素宽度和高度等。

screen对象在不同的浏览器中支持不同，在所有浏览器中都支持的属性有如下属性:

* availHeight:屏幕的像素高度减系统部件高度之后的值(只读)
* availWidth:屏幕的像素宽度减系统部件宽度之后的值(只读)
* colorDepth:用于表示颜色的位数(大多数系统是32，只读)
* height:屏幕的像素高度
* width:屏幕的像素宽度

## history对象

history对象保存着用户上网的历史记录，从窗口被打开的那一刻算起。因为history是window对象的属性，因此每个浏览器窗口，每个标签页乃至每个框架都有自己的history对象与特定的window对象关联。出于安全方面的开了，开发人员无法得知用户浏览过的URL。不过皆有用户访问过的页面列表，同样可以在不知道实际URL的情况下实现后退和前进。

history对象拥有一个history属性,保存着历史记录的数量。这个数量包括所有历史记录，即所有向后和向前的记录。可以判断history.length的长度判断当前窗口是否是用户打开的第一个窗口

``` javascript
if(history.length == 0){
    //这是用户打开的第一个窗口
}
```

history对象拥有几个方法可以实现页面的向前或向后跳转

``` JavaScript
history.go(-1)      //后退一页
history.go(1)       //前进一页
history.back()      //后退一页
history.froward()   //前进一页
```

## 小结

浏览器对象模型(BOM)以window对象为依托，表示浏览器窗口以及页面可见区域。同时，window对象还是ECMAScript中的Global对象，因此所有全局变量和函数都是它的属性，且所有原生的构造函数及其他函数也都存在于它的命名空间下。

* 在使用框架时,每个框架都有自己的window对象以及所有原生构造函数及其他函数的副本。每个框架都保存在frames集合中，可以通过位置或通过名称来访问
* 有一些窗口指针,可以用来引用其他框架，包括父框架
* top对象始终指向最外围的框架，也就是整个浏览器窗口
* parent对象包含当前框架的框架，而self对象则回指window
* 使用location对象可以通过编程方式来访问浏览器的导航系统。设置相应的属性，可以逐段或整体性的修改浏览器的URL
* 调用replace()方法可以导航到一个新的URL,同时该URL会替换浏览器历史记录中当前显示的页面
* navigator对象提供了与浏览器有关的信息
* BOM还有两个对象,screen和history，但它们的功能有限。screen对象中保存着与客户端显示器有关的信息，这些信息一般只由于站点分析。history对象为访问浏览器的历史记录提供了几个小方法。

## 文章参考

* 《JavaScript高级程序设计》第二版 第8章:BOM