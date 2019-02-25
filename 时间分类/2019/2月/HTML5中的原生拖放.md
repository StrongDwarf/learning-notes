# HTML5中的原生拖放

## 拖放事件

通过拖放事件，可以控制拖放事件相关的各个方面。其中最关键的地方在于确定哪里发生了拖放事件，有些事件是在被拖动的元素上被触发的，而有些事件是在放置目标上触发的。拖动某元素时，将依次触发下列事件:

* dragstart
* drag
* dragend

当某个元素被拖动到一个有效的放置目标上时，下列事件会依次发生:

* dragenter
* dragover
* dragleave或drop

其中,当元素放到了放置目标上时，会触发drop事件而不是dragleave事件

## dataTransfer对象

在处理拖动相关的事件时,可以通过dataTransfer对象在拖动事件和放置事件之间传递数据。如下:

IE对dataTransfer对象定义了"text"和"url"两种有效的数据类型，而HTML5则对此加以扩展，允许指定各种MIME类型。考虑到向后兼容，HTML5也支持"text"和"url"，但这两种类型会被映射为"text/plain"和"text/url-list"。

dataTransfer对象传递的数据在dragstart事件中定义,而在drop事件中获取。如下:

``` JavaScript
function dragstartHandler(e){
    e.dataTransfer.setData('text',"some text")
}
function dropHandler(e){
    e.dataTransfer.getData('text')
}
```

## dropEffect和effectAllowed

利用dataTransfer对象,不光能够传输数据，还能通过它来确定被拖动的元素以及作为放置目标的元素能够接收什么操作。为此，需要访问dataTransfer对象的两个属性:dropEffect和effectAllowed。

其中,通过dropEffect属性可以知道被拖动的元素能够执行哪种放置行为。这个属性有下列4个可能的值。

* none:不能把拖动的元素放在这里。这是除文本框之外所有元素的默认值
* move:应该把拖动的元素移动到放置目标
* copy:把拖动的元素复制到放置目标
* link:表示放置目标会打开拖动的元素,但拖动的元素必须是一个链接，有URL

在把元素拖动到放置目标上时，以上每一个值都会导致光标显示为不同的符号。然而,要怎样实现光标所指示的动作完全取决于你。换句话说，如果你不处理，没什么会移动，复制。要使用dropEffect属性，必须在ondragenter事件处理程序中针对放置目标来设置它。

dropEffect属性只有搭配effectAllowed属性才有用。effectAllowed属性表示允许拖动元素的哪种dropEffect，effectAllowed属性可能的值如下:

* uninitialized:没有给被拖动的元素设置任何放置行为
* none:被拖动的元素不能有任何行为
* copy:只允许值为copy的dropEffect
* link:只允许值为link的dropEffect
* move:只允许值为move的dropEffect
* copyLink:允许值为copy和link的dropEffect
* copyMove:允许值为copy和move的dropEffect
* linkMove:允许值为link和move的dropEffect
* all:允许任意dropEffect

## 可拖动

默认情况下,图像,链接和文本是可以拖动的，也就是说，不用额外编写代码，用户就可以拖动它们。文本只有在被选中的情况下才能拖动，而图像和链接在任何时候都可以拖动。
要将元素设置为不可拖动可以设置draggable属性。如下:

``` html
<!-- 让这个元素不可拖动 -->
<img src="smile.gif" draggable="false" alt="Smiley face">
```

支持draggable属性的浏览器有IE 10+，Firefox 4+,Safari 5+和Chrome。Opera 11.5 及之前的版本都不支持HTML5的拖放功能

## 其他成员

HTML5规范制定了dataTransfer对象还应该包含下列方法和属性

* addElement(element):为拖动操作添加一个元素。添加这个元素只影响数据(即增加作为拖动源而响应回调的对象)，不会影响拖动操作时页面元素的外观。实现这个方法的浏览器较少
* clearData(format):清除以特定格式保存的数据。实现这个方法的浏览器有IE,Firefox 3.5+,Chrome和Safari 4+.
* setDragImage(element,x,y):指定一副图像，当拖动发生时，显示在光标下方。这个方法接收的3个参数分别是要显示的HTML元素和光标在图像中的X,y坐标。其中HTML元素可以是一副图像，也可以是其他元素。实现这个方法的浏览器有Firefox 3.5+，Safari 4+和Chrome
* types:当前保存的数据类型。这个一个类似数组的集合，以"text"这样的字符串形式保存着数据类型。实现这个属性的浏览器有IE10+,Firefox 3.5+和Chrome

## 例子

如下是一个实现拖动的例子,

``` html
<!DOCTYPE html>
<html>
<style>
    * {
        margin: 0;
        padding: 0;
    }

    body {
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
    }

    #wrap {
        height: 100px;
        text-align: center;
    }

    img {
        width: 100px;
        height: 100px;
        cursor: -webkit-grab;
        cursor: -moz-grab;
        cursor: grab;
    }

    #cart {
        width: 500px;
        height: 100px;
        border-radius: 20px;
        margin: 50px auto 0;
        background-color: orange;
    }

    #cart.hover {
        background-color: red;
    }

    #cart img {
        width: 70px;
        height: 70px;
        margin: 15px;
    }
</style>

<div id='container'>
    <div id='wrap'>
        <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551028697158&di=e3e56fb450fa7afc24ed786831883eec&imgtype=0&src=http%3A%2F%2Foss.huangye88.net%2Flive%2Fuser%2F1154998%2F1502504091079049700-5.jpg" title='包子' />
        <img src="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3162845939,1390758181&fm=26&gp=0.jpg" title='鞋子' />
        <img src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551623527&di=e858aed039a0cff320c612aa748ca28e&imgtype=jpg&er=1&src=http%3A%2F%2Fimg1.fdc.com.cn%2Fupload%2Fforum%2F201405%2F27%2F210911jo0b6bjotato01bb.jpg" title='薯片' />
    </div>
    <div id='cart'></div>
</div>
</html>
```

为元素添加事件绑定

``` javascript
//兼容性代码
var EventUtil = {
    addHandler:function(element,type,handler){
        if(element.addEventListener){
            element.addEventListener(type,handler,false)
        }else if(element.attachEvent){
            element.attachEvent("on"+type,handler)
        }else{
            element["on"+type] = handler
        }
    },
    getTarget:function(event){
        return event.target || event.srcElement
    },
    getEvent:function(event){
        return event?event:window.event
    },
    preventDefault:function(event){
        if(event.preventDefault){
            event.preventDefault()
        }else{
            event.returnValue = false
        }
    }
}

//设置透明度兼容性处理
function setOpacity(element, value) {
    if (typeof element.style.opacity != 'undefined') {
        element.style.opacity = value;
    } else {
        element.style.filter = "alpha(opacity=" + value * 100 + ")";
    }
}

//相应事件处理函数

//拖动开始
function dragstart(e){
    //获得事件目标
    e = EventUtil.getEvent(e)
    var target = EventUtil.getTarget(e)
    //设置拖动元素时要传递的数据
    e.dataTransfer.setData('text',target.title)
    //添加拖动元素时鼠标下方的元素
    if(e.dataTransfer.setDragImage){
        e.dataTransfer.setDragImage(target,50,50)
    }
    //设置拖动元素允许的元素操作
    e.dataTransfer.effectAllowed = "move"
}

//拖动中
function drag(e){
    e = EventUtil.getEvent(e)
    var target = EventUtil.getTarget(e)
    setOpacity(target,0)
}

//拖动结束
function dragend(e){
    e = EventUtil.getEvent(e)
    var target = EventUtil.getTarget(e)
    setOpacity(target,1)
}

//拖动元素进入容器
function dragenter(e){
    e = EventUtil.getEvent(e)
    var target = EventUtil.getTarget(e)

    e.dataTransfer.dropEffect = "move"
    target.className = "hover"
}

//拖动元素在容器中移动
function dragOver(e){
    e = EventUtil.getEvent(e)
    EventUtil.preventDefault(e)
}

//拖动元素离开了容器
function dragleave(e){
    e = EventUtil.getEvent(e)
    var target = EventUtil.getTarget(e)

    target.className = ""
}

//拖动元素drop在了容器中
function drop(e){
    e = EventUtil.getEvent(e)
    var target = EventUtil.getTarget(e)

    target.className = ""
    var title = e.dataTransfer.getData('text')
    console.log("将"+title+"加入了cart中")
    //删除原来位置的拖动元素
    dragElement.parentNode.removeChild(dragElement)
    //在新位置加入该元素
    var img = dragElement.cloneNode();
    target.appendChild(img)
    //设置元素的透明度
    setOpacity(img,1)
    //禁止默认行为
    EventUtil.preventDefault(e)
}

//获取事件元素
var imgs = document.getElementsByTagName('img'),
    cart = document.getElementById('cart'),
    dragElement = null;
for (var i = 0; i < imgs.length; i++) {
        EventUtil.addHandler(imgs[i], 'dragstart', dragstart);
        EventUtil.addHandler(imgs[i], 'drag', drag);
        EventUtil.addHandler(imgs[i], 'dragend', dragend);
}
EventUtil.addHandler(cart, 'dragenter', dragenter);
EventUtil.addHandler(cart, 'dragover', dragover);
EventUtil.addHandler(cart, 'dragleave', dragleave);
EventUtil.addHandler(cart, 'drop', drop);

```