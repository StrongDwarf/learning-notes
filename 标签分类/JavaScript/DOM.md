# DOM

文章内容:

* 理解包含不同层次节点的DOM
* 使用不同的节点类型
* 客服浏览器兼容性问题及各种陷阱

DOM(文档对象模型)是针对HTML和XML文档的一个API(应用程序编程接口)。DOM秒回了一个层次化的节点树，允许开发人员添加,移除和修改页面的某一部分。

## 节点类型

DOM可以将任何HTML或XML文档秒回成由多层节点构成的结构。

### 1.1 Node类型

DOM1级定义了一个Node接口,该接口将由DOM中的所有节点类型实现。这个Node接口在JavaScript中是作为Node类型实现的，除了IE之外，在其他所有浏览器中都可以访问到这个类型

``` JavaScript
Node.ELEMENT_NODE   //1
```

每个节点都有一个nodeType属性,用于表明节点的类型。节点类型由在Node类型中定义的下列12个数值常量表示，任何节点类型必居其一。

* Node.ELEMENT_NODE 1
* Node.ATRIBUTE_NODE 2
* Node.TEXT_NODE 3
* Node.CDATA_SECTION_NODE 4
* Node.ENTITY_REFERENCE_NODE 5
* Node.ENTITY_NODE 6
* Node.PROCESSING_INSTRUCTION_NODE 7
* Node.COMMENT_NODE 8
* Node.DOCUMENT_NODE 9
* Node.DOCUMENT_TYPE_NODE 10
* Node.DOCUMENT_FRAGMENT_NODE 11
* Node.NOTATION_NODE 12

通过比较上面这些常量,可以很容易地确定节点的类型，例如:

``` javascript
if(someNode.nodeType === Node.ELEMENT_NODE){
    console.log('node is an element')
}
```

不过在IE中,并没有公开Node类型的构造函数,所以不能访问Node对象,在IE中要判断节点的类型,只能讲nodeType属性与数字进行比较,如:

``` javascript
if(someNode.nodeType === 1){
    console.log('node is an element')
}
```

#### nodeName和nodeValue属性

要了解节点更多的信息,可以使用nodeName和nodeValue这两个属性。这两个属性的值完全取决于节点的类型。在使用这两个值之前，最好是想下面这样先检查节点的类型

``` javascript
if(someNode.nodeType === 1){
    console.log(someNode.nodeName)
}
```

对于元素节点来说,nodeName始终保存元素的标签名,而nodeValue则是null。

#### 节点关系

文档中的所有节点都存在关系。对于存在于文档树中的节点,可以使用下面的几个属性访问其节点

* parentNode:指向文档树中的父节点
* childNodes:获取子节点列表
* nextSibling:获取归属相同父节点的下一个子节点
* previousSibling:获取归属相同父节点的前一个子节点
* firstChild:获取子节点列表的第一个子节点
* lastChild:获取子节点列表的最后一个子节点

当使用childNodes获取子节点列表时,childNodes返回的是一个nodeList,nodeList是一个类数组对象,有时候我们可能仅仅需要获取该对象在当前时间的快照,可以将其转化为数组。可以使用下面的函数将其转化为数组

``` javascript
function convertToArray(nodes){
    var array = null
    try{
        array = Array.prototype.slice.call(nodes,0)
    }catch(ex){
        arr = new Array()
        for(var i =0,len = nodes.length;i<len;i++){
            array.push(nodes[i])
        }
    }
    return array
}
```

#### 操作节点

因为关系指针都是只读的,所以DOM提供了一些操作节点的方法。如下:

* appendChild():向childNodes列表的末尾添加一个节点
* insertBefore():该方法接受2个参数,要插入的节点和作为参照的节点。插入节点后,被插入的节点会变成参照节点的前一个通报节点(【previousSibling),同时被方法返回
* replaceChild():该方法接收2个参数,要插入的节点和要替换的节点。要替换的节点将由这个方法返回并从文档树中移除，同时要插入的节点占据其位置
* removeChild():该方法接受一个参数，即要移除的节点。被移除的节点将成为方法的返回值.

#### 其他方法

有两个方法是所有方法都有的,第一个是cloneNode(),用于克隆节点,该方法接收一个布尔值参数，表示是否执行深复制,该参数默认为false。当参数值为true时执行深复制,否则执行浅复制。浅复制时，只复制当前节点，而不复制该节点的子节点。深复制时，不但复制当前节点，而且复制当前节点的子节点。

还有一个方法是normalize(),这个方法唯一的作用就是处理文档树中的文本节点(TEXT_NODE)。由于解析器的实现或DOM操作等原因，可能会出现文本节点不包含文本，或者接连出现两个文本节点的情况。当在某个节点上调用这个方法时，就会在该节点的厚点节点中查找上诉两种情况。

### 1.2 Document类型

JavaScript通过Document类型表示文档。在浏览器中，document对象是HTMLDocument(继承自Document类型)的一个实例，表示整个HTML页面。而且,document对象是window对象的一个属性，因此可以将其作为全局对象来访问。Document具有以下特征

* nodeType的值为9
* nodeName的值为"#document"
* nodeValue的值为null
* parentNode的值为null
* ownerDocument的值为null
* 其子节点可能是一个DocumentType(最多一个),Element(最多一个)，ProcessingInstruction或Comment

Document类型可以表示HTML页面或者其他基于XML的文档，不过最常见的应用还是作为HTMLDocument实例的document对象。通过这个对象可以获取文档的有关信息，而且还能操作文档的底层结构。

document对象具有如下属性

* childNodes:指向子节点列表
* documentElement:指向页面中的<html>元素
* body:指向页面中的<body>元素
* doctype:指向<!DOCTYPE>
* title:指向<title>
* URL:当前页面的URL
* referrer:当前页面的来源URL
* domain:当前页面的域名,如 "google.com"

在document对象中,有些值得注意的兼容性问题如下:

#### (1) 对document.doctype的支持

* IE8及之前版本,如果存在文档类型声明，会将其错误地解释为一个注释并把它当做Comment节点,而document.doctype的值始终未null
* IE9+和firefox,如果存在文档声明,会将其作为文档的第一个子节点,此时可以使用document.childNodes[0]和document.firstChild访问
* chrome,Safari和Opera:如果存在文档类型声明,会将其解析，但不会作为文档的子节点，无法通过documnt.childNodes访问他

document类型还提供了一些用于查找元素的方法,如下:

* getElementsByTagName:通过标签名查找
* getElementsByClassName:通过类名查找
* getElementById:通过id名查找
* querySelector:通过选择器查找，只返回第一个符合条件的
* querySelectorAll:通过选择器查找,查找所有的

document对象还有一些特殊的集合。这些集合都是HTMLCollection对象,为访问文档常用的部分提供了快捷方式，包括

* document.anchors 包含文档中所有带有name特性的<a>元素
* document.applets 包含文档中所有的<applet>元素,因为不在推荐使用<applet>元素，所以这个集合已经不建议使用了
* document.forms 包含文档中所有的<form>元素,与document.getElementsByTagName("form")得到的结果一致
* document.images 包含文档中所有的img元素,
* document.links 包含文档中所有带href特性的<a>元素

document对象还提供将输出流写入到网页中的能力,不过值得注意的是,该方法只有在页面被加载的过程中，才可以向页面动态地加入内容。

write(),writeln(),open(),close()

``` javascript
<html>
    <body>
        <script type="text/javascript">
            document.write("hello world")
        </script>
    <body>
</html>
```

### 1.3 Element类型

除了Document类型之外,Element类型就要算是Web编程中最常用的类型了。Element类型用于表现XML或HTML元素，提供了对元素标签名，子节点及特性的访问。Element节点具有以下特征

* nodeType的值为1
* nodeName的值为元素的标签名
* nodeValue的值为null

要访问元素的标签名,可以使用nodeName和tagName属性，两者返回一样的值

``` javascript
var ulNode = document.getElementsByTagName('ul')[0]
ulNode.tagName === ulNode.tagName       //true
```

关于标签名,在HTML中,标签名始终大写，在XML中,标签名和源代码中的保持一致,所以要准确判断节点的标签名最好先将其转换为小写后再判断。

``` javascript
ulNode.tagNmae.toLowerCase() == "ul"       //true
```

#### HTML元素

所有HTML元素都有HTMLElement类型表示,不是直接通过这个类型，也通过它的子类型来表示。HTMLElement类型直接继承自Element并添加了一些属性。添加的这些属性分别对应每个HTML元素中都存在的下列特性。

* id:元素在文档中的唯一标识符
* title:有关元素的附加说明信息,一般通过工具提示条显示出来
* lang:元素内容的语言代码,一般很少使用
* dir:语言的方向,值为"ltr"(从左至右) 或"rtl"(从右至左),也很少使用
* className:与元素的class特性对应，即为元素指定的CSS类。

#### 取得特性

Element元素有3个操作特性的方法,分别为:getAttribute(),setAttribute(),removeAttribute()

#### attributes属性

Element类型是使用attributes属性的唯一一个DOM节点类型。attribute属性中包含一个NamedNodeMap,与NodeList类似，也是一个"动态的集合"。元素的每一个特性都由一个Attr节点表示，每个节点都保存在NamedNodeMap对象中。NamedNodeMap对象拥有下列方法

* getNamedItem(name):返回nodeName属性等于name的节点
* removeNamedItem(name):从列表中移除nodeName属性等于name的节点
* setNamedItem(node):向列表中添加节点，以节点的nodeName属性为索引
* item(pos):返回位于数字pos处的节点

``` javascript
var ulNode = document.createElement('ul')
ulNode.setAttribute('id','a')
ulNode.attributes.getNamedItem('id')   // id=a
```

在世界使用时,可以通过遍历attrbutes来访问Element的所有属性,不过值得注意的是,针对attributes对象中的特性,不同浏览器返回的顺序不同。而且在IE7及更早的版本中会返回Element中所有可能的特性，包括没有指定的特性

可以使用下面的函数访问元素的所有属性

``` javascript
function outputAttributes(element){
    var pairs = new Array(),
        attrName,
        attrValue,
        i,
        len;
    for(i = 0,len = element.attributes.length;i<len;i++){
        attrName = element.attributes[i].nodeName
        attrValue = element.attrbutes[i].nodeValue
        if(element.attributes[i].specified){
            pairs.push(attrName + "=\"" + attrValue + "\"")
        }
    }
    return pairs.join(" ");
}
```

## DOM操作技术

### 2.1 动态脚本

使用<script>元素可以向页面中插入JavaScript代码，一种方式是通过其src特性包含外部文件，另一种方式是用这个元素本身来包含代码。

通过操作DOM动态加载外部JavaScript文件方法如下:

``` JavaScript
function loadScript(url){
    var script = document.createElement("script")
    script.type = "text/javascript"
    script.src = url
    document.body.appendChild(script)
}
```

操作DOM加载行内JavaScript文件

``` javascript
function loadScriptString(code){
    var script = document.createElement("script")
    script.type = "text/javascript"
    try{
        script.appendChild(document.createTextNode)
    }catch(ex){
        script.text = code
    }
    document.body.appendChild(script)
}
```

### 2.2 动态样式

能够把CSS样式包含到HTML页面中的元素有两个.其中<link>元素用于包含来自外部的文件，而<style>元素用于指定嵌入的样式。

嵌入外部CSS样式

``` javascript
function loadStyles(url){
    var link = document.createElement("link")
    link.rel = "stylesheet"
    link.type = "text/css"
    link.href = url
    var head = document.getElementsByTagName("head")[0]
    head.appendChild(link)
}
```

嵌入CSS样式

``` javascript
function loadStyleString(css){
    var style = document.createElement("style")
    style.type = "text/css"
    try{
        stye.appendChild(document.createTextNode(css))
    }catch(ex){
        style.styleSheet.cssText = css
    }
    var head = document.getElementsByTagName("head")[0]
    head.appendChild(style)
}
```
