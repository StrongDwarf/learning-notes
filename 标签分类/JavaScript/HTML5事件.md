# HTML5事件

DOM规范没有涵盖所有浏览器支持的所有事件。很多浏览器出于不同的目的还实现了一些自定义事件。HTML5规范详尽列出了浏览器应该支持的所有事件。下面是这些事件中的一部分

## contextmenu事件

在页面中单击鼠标右键可以调出上下文菜单

contextmenu事件定义了何时应该显示上下文菜单，以便开发人员取消默认的上下文菜单而提供自定义的菜单。

contextmenu事件是冒泡的,可以为所document指定一个处理程序，用于取消页面中发生的所有此类事件。
使用contextmenu的例子如下:

``` html
<!DOCTYPE html>
<html>
    <head>
    </head>
    <body>
        <div id="myDiv">Right click or ctrl+click me to get a custom  context menu</div>
        <ul id="mymenu" style="position:absolute;visibility:hidden;background-color:silver">
            <li>1</li>
            <li>2</li>
            <li>3</li>
        </ul>
        <script>
            window.onload = function(event){
                var div = document.getElementById('myDiv')
                div.addEventListener('contextmenu',function(e){
                    e.preventDefault();

                    var menu = document.getElementById('myMenu')
                    menu.style.left = e.clientX+'px'
                    menu.style.top = e.clientY + 'px'
                    menu.style.visibili = 'visible'
                },flase)

                document.addEventListener('click',function(e){
                    document.getElementById("myMenu").style.visibility = "hidden"
                })
            }
        </script>
    </body>
</html>
```

支持cotextmenu事件的浏览器有IE,Firefox,Safari,Chrome和Opera 11+

## beforeunload事件

之所以有发生在window对象上的beforeunload事件，是为了让开发人员有可能在页面卸载前阻止这一操作。这个事件会在浏览器卸载页面之前触发，可以通过它来取消卸载并继续使用原有网页。但是，不能彻底取消这格式件，因为那就相当于让用户无法离开当前页面了。为此，这个事件的意图是将控制权交给用户。显示的信息会告知用户页面将被卸载，询问用户是否真的要关闭页面，还是希望继续留下来。

为了显示这个弹出对话框，必须将event.returnValue的值设置为要显示给用户的字符串(对IE和Firefox而言),同时作为函数的值返回(对Safari和Chrome而言),使用如下:

``` JavaScript
window.addEventListener('beforeunload',function(e){
    var message = "确定要关闭页面吗"
    e.returnValue = message
    return message
},false)
```

## DOMContentLoaded事件

window的load事件会在页面中的一切都加载完毕时触发，但这个过程可能会因为要加载的外部资源过多而波费周折。而DOMContentLoaded事件则在形成完整的DOM树之后就会触发，不理会图像，JavaScript文件，CSS文件或其他资源是否已经下载完毕。与load事件不同，DOMContentLoaded支持在页面下载的早期添加事件处理程序，这也就意味着用户能够尽早地与页面进行交互
要处理DOMContentLoaded事件，可以为document或window添加相应的事件处理程序(尽管这个事件或冒泡到window，但它的目标实际上是document)。如下:

``` javascript
document.addEventListener('DOMContentLoaded',function(event){
    alert("Content loaded")
})
```

IE9+,Firefox,Chrome,Safari 3.1+和Opera 9+都支持DOMContentLoaded事件,通常这个事件即可以添加事件处理程序，也可以执行其他DOM操作。这个事件始终早于load事件之前触发。

对于不支持DOMContentLoaded的浏览器，我们建议在页面加载期间设置一个事件为0毫秒的超时调用，如下面的例子所示。

``` javascript
setTimeout(function(){
    //在此添加事件处理程序
},0)
```

## readystatechange事件

IE为DOM文档中的某些部分提供了readystatechange事件。这个事件的目的是提供与文档或元素加载状态有关的信息，但这个事件的行为有时候也很难预料。支持readystatechange事件的每个对象都有一个readyState属性，可能包含下列5个值中的一个

* uninitialized:未初始化 对象存在但尚未初始化
* loading:正在加载:对象正在加载数据
* loaded:加载完毕 对象加载数据完成
* interactive: 交互 可以操作对象了，但还没有完全加载
* complete: 完成 对象已经加载完毕

这些状态看起来很直观，但并非所有对象都会经历readyState的这几个阶段。甚至，交互阶段可能会早于也可能会晚于完成阶段出现，无法确保顺序。要使用readystatechange事件判断页面是否加载完毕，可以如下使用:

``` javascript
EventUtil.addHandler(document,"readystatechange",funciton(e){
    if(document.readyState === "interactive" || document.readyState == "complete"){
        EventUtil.removeHandler(document,"readystatechange",arguments.callee)
        alert("Content loaded")
    }
})
```

支持readystatechange事件的浏览器有IE，Firefox 4+ 和Opera

另外,script和link元素也会触发readystatechange事件，可以用来确定外部的JavaScript和CSS文件是否已经加载完成。与其他浏览器中一样，除非把动态创建的元素添加到页面中，否则浏览器不会开始下载外部资源。基于元素触发的readystatechange事件也存在同样的问题，即readySatet的属性无论等于"loaded"还是"complete"都可以表示资源已经可用

## pageshow和pagehide事件

Firefox和Opera有一个特性,名叫"往返缓存",可以在用户使用浏览器的"后退"和前进按钮时加快页面的转换速度。这个缓存中不仅保存在页面数据，还保存了DOM和JavaScript的状态；实际上是将正个页面保存在了内存里。如果页面位于bfcache中，那么再次打开页面时就不会触发load事件。

pageshow事件在浏览器从bfcache中读取页面时触发，pagehide事件在浏览器向bfcache中中加入页面时触发。
pageshow事件和pagehide事件还有一个名为persisted的布尔属性。如果页面被保存在了bfcache中，则这个属性的值为true

使用pageshow和pagehide事件的方式如下:

``` javascript
EventUtil.addHandler(window,"pagehide",function(e){
    alert(event.persisted)
})
```

支持pageshow和pagehide事件的浏览器有Firefox,safari 5+ chrome和Opera.IE9及之前版本不支持这两个事件

## hashchange事件

HTML5新增了haschange事件，以便在URL的参数列表发生变化时通知开发人员。该事件包含额外两个属性,oldURL和newURL.这两个属性分别包含参数列表变化前后的完整URL。

支持haschange事件的浏览器有IE8+，Firefox 3.6+，Safari 5+,Chrome和Opera 10.6+。在这些浏览器中，只有Firefox 6+,Chrome和Opera支持oldUrl和newUrl实现，为此,最好是使用location对象来获取信息