# JavaScript客户端检测

由于不同的浏览器的实现不同，在使用具体功能前,一般要先确定当前浏览器是否支持该功能。为此,需要进行客户端检测

客户端检测常用的方法有三种：

* 能力检测:在使用某能力之前(一般是某函数),检测当前浏览器是否支持该函数，如果不支持该函数，则使用其他兼容方式
* 怪癖检测:怪癖检测用于识别浏览器的特殊行为。
* 用户代理检测:用户代理检测指通过检测用户代理字符串来确定用户实际使用的浏览器

## 目录

[文章目录](文章目录)

* [能力检测](#能力检测)
* [怪癖检测](#怪癖检测)
* [客户端检测](#客户端检测)
* [参考](#参考)

## 能力检测

能力检测的目标不是识别特定的浏览器,而是识别浏览器的能力。能力检测的基本模式如下:

``` JavaScript
if(object.propertyInQuestion){
    //使用object.propertyInQuestion
}
```

举例来说,IE5.0版本中不只吃document.getElementById()这的DOM方法,在IE中可以像下面一样使用这个方法

``` javascript
var getElement = function(id){
    if(document.getElementById){
        return function(id){
            return document.getElementById(id)
        }
    }else if(document.all){
        return function(id){
            return document.all[id]
        }
    }else{
        throw new Error("No way to retrieve element!")
    }
}
```

### 更可靠的能力检测

能力检测对于想知道某个特性是否会按照适当方式行事非常有用，但是如上面那样检测，只能检测除该特性是否存在，而不能检测除该特性是不是我们想要的。如确定一个对象是否支持排序

``` javascript
function isSortable(object){
    return !!object.sort
}
```

这个函数通过检测对象是否存在object.sort属性来确定对象是否支持排序,但是其中存在一个问题是，如果用户为某个对象自定义了sort属性,那么该方法也会返回true。而此时,我们却只希望当对象具有sort方法的时候返回true。这样可以明显的看出上述方法还是不符合要求的。一个更完善的写法如下:

``` javascript
function isHostMethod(object,property){
    var t = typeof object[property]
    return t == 'function' ||
            //兼容IE8
            (!!(t == 'object' && object[property])) ||
            t == 'unknown'
}
```

## 怪癖检测

与能力检测类似,怪癖检测的目标是识别浏览器的特殊行为。但与能力检测确认浏览器支持什么能力不同，怪癖检测是想要知道浏览器存在什么缺陷。

例如:早期IE8及更早版本中存在一个bug,即如果某个实例属性与[[Enumerable]]标记为false的某个原型属性同名，那么该属性将不会出现在for...in循环中。可以使用下面的代码来检测这种怪癖

``` javascript
var hasDontEnumQuick = function(){
    var o = {toString:function(){}}
    for(var prop in o){
        if(prop == 'toString'){
            return false
        }
    }
    return true
}
```

## 用户代理检测

用户代理检测通过检测用户代理字符串来确定实际使用的浏览器。在每一次HTTP请求过程中，用户代理字符串是作为响应首部发送的。而且该字符串可以通过JavaScript的navigator.userAgent属性访问。在服务器端,通过检测用户代理字符串来确定用户使用的浏览器是一种常用而且广为接受的做法。而在客户端，用户代理检测一般被当做一种万不得以才用的做法，其优先级在能力检测和怪癖检测之后。

### 用户代理字符串的历史

#### 1 早期的浏览器

1993年，美国NCSA发布了世界上第一款web浏览器Mosaic.这款浏览器的用户代理字符非常简单，类似如下

``` javascript
Mosaic/0.9      //产品名称/版本号
```

其格式为产品名称/版本号。不久后,Netscape Communications公司介入浏览器开发领域，发布了自己的浏览器,并将用户代理字符串定义如下:

``` javascript
Mozilla/版本号 [语言] (平台:加密类型)
//例子
Mozilla/2.02 [fr] (WinNT; I)
```

#### 2 Netscape Navigator 3 和 Internet Explorer 3

不久后 Netscape Navigator 3 发布,并且成为了当时世界上最流行的浏览器。Netscape Navigator 3的用户代理字符串格式如下:

``` javascript
Mozilla/版本号 (平台:加密类型 [;操作系统或cpu说明])
//例子
Mozilla/3.0 (Win95;U)
```

而在Netscape Navigator 3发布不久后,微软公司也发布了Internet Explorer 3，由于当时Netscape浏览器在市场上占有绝对份额，许多服务器在提供网页之前都要专门检测该浏览器。如果用户通过IE打不开相关网页，那么这个新生的浏览器很快就会夭折。于是，微软将IE的用户代理字符串修改为兼容Netscape的形式，结果如下:

``` javascript
Mozilla/2.0 (compatible;MSIE 版本号;操作系统)
//例子
Mozilla/2.0 (compatible;MSIE 3.02;Window 95)
```

直到IE8,IE材质用户代理字符串添加了呈现引擎(Trident)的版本号

``` javascript
Mozilla/4.0 (compatible;MSIE 版本号;操作吸引；Trident/Trident 版本号)
```

#### 3 Gecko

Gecko是Firefox的呈现引擎。当初的Gecko是作为通用Mozilla浏览器的一部分开发的，Firefox使用该引擎时，其用户代理字符串如下:

``` javascript
Mozilla/5.0 (Windows NT 6.1;rv;2.0.1) Gecko/20100101 Firefox 4.0.1
```

#### 4 Webkit

2003年,apply公司发布了自己的Web浏览器，名字定为safari。safari的呈现引擎叫做webkit，是Linux平台Konqueror浏览器的呈现引擎KHTML的一个分支。几年后，webkit独立出来成为了一个开源项目，专注于呈现引擎的开发。
而在当时,这款新浏览器和呈现引擎的开发人员演遇到了与IE3类似的问题:如何确保浏览器不被流行的站点拒之门外?答案就是想用户代理字符串中放入足够多的信息，以便站点能够信任它与其他流行的浏览器是兼容的。webkit的用户代理字符串个数如下:

``` javascript
Mozilla/5.0 (平台:加密类型;操作系统或CPU；语言) AppleWebkit/AppleWebkit 版本号(KHTML,like Gecko) Safari/safari版本号
```

#### 5 Konqueror

与KDE Linux集成的Konqueror,是一款基于KHTML开源呈现引擎的浏览器,Konqueror中的用户代理字符串格式如下:

``` javascript
Mozilla/5.0 (compatible;konqueror/版本号;操作系统或CPU) KHTML/KHTML版本号(like Gecko)
```

#### 6 Chrome

谷歌公司的Chrome浏览器以Webkit作为呈现引擎，但使用了不同的JavaScript引擎。Chrome中的用户代理字符串完全取自webkit，但添加了一段表示chrome版本号的信息。如下:

``` javascript
Mozilla/5.0 (平台:加密类型;操作系统或CPU；语言) AppleWebkit/AppleWebkit 版本号(KHTML,like Gecko) Chrome/Chrome 版本号 Safari/safari版本号
```

#### 7 Opera

Opera的呈现引擎是Presto
在Opera 8.0之前,其用户代理字符串如下:

``` JavaScript
Opera/版本号 (操作系统或CPU;加密类型) [语言]
```

在Opera 9以后,Opera选择修改自身的用户代理字符串来迷惑嗅探代码

其修改后的用户代理字符串如下:

``` JavaScript
Mozilla/5.0 (Window NT 5.1;U;en;rv;1.8.1) Gecko/20061208 Firefox/2.0.0 Opera 9.50
Mozilla/4.0 (compatible;MSIE 6.0;Window NT 5.1;en) Opera 9.50
```

到Opera 10 又对字符串进行了修改。现在的格式是:

``` javascript
Opera/9.80 (操作系统或CPU;加密类型;语言) Presto/Presto版本号 Version/版本号
```

值得注意的是,初始的版本号Opera/9.80是不变的，如下是Windows7中Opera 10.63的用户代理字符串

``` javascript
Opera/9.80 (Window Nt 6.1;U;en) Presto/2.6.30 Version/10.63
```

#### 8 IOS和Andrid

移动操作系统ios和Android默认的浏览器都基于Webkit,而且都像它们的桌面版一样，共享相同的基本用户代理字符串格式。ios设备的基本格式如下:

``` javascript
Mozilla/5.0 (平台;加密类型;操作系统或CPU like Mac OS X;语言)AppleWebKit 版本号(KHTML，like Gecko) Version/浏览器版本号 Mobile/移动版本号 Safari/Safari版本号
```

注意辅助确定MAC操作系统的 like Mac OS X和额外的Mobile记号,一般来说，Mobile记号的版本号没什么用,主要用来确定Webkit是移动端而非桌面端。而平台则可能是"iphone","iPod","iPad".例如:

``` javascript
Mozilla/5.0 (iPhone;U;Cpu iphone OS 3_0 like Mac OS X;en-us) AppleWebkit/528.18 (KHTML,like Gecko) Version/4.0 Mobile/7A341 Safari/528.16
```

Android浏览器中的默认格式与ios的格式相似，没有移动版本号(但有Mobile记号)。例如:

``` 
Mozilla/5.0 (Linux;U;Android 2.2;en-us) AppleWebkit/528.18 (KHTML,like Gecko) Version/4.0 Mobile Safari/528.16
```

### 编写用户代理检测程序

一段用户代理检测程序包括检测呈现引擎，平台，Windows操作系统，移动设备和游戏系统。代码如下:

``` javascript
var client = function(){
     //呈现引擎
     var engine = {
         ie : 0,
         gecko : 0,
         webkit : 0,
         khtml : 0,
         opera : 0,

         //具体的版本号
        ver : null
     };

     var browser = {
         //浏览器
         ie : 0,
         firefox : 0,
         konq : 0,
         opera : 0,
         chrome : 0,
         safari : 0,

         //具体的版本
         ver : null
     };

     //平台、设备和操作系统
     var system = {
         win : false,
         mac : false,
         xll : false,

         //移动设备
         iphone : false,
         ipod : false,
         nokiaN : false,
         winMobile : false,
         macMobile : false,

         //游戏系统
         wii : false,
         ps : false
     };

     //检测呈现引擎及浏览器
     var ua = navigator.userAgent;

     if ( window.opera ){
         engine.ver = window.opera.version();
         engine.opera = parseFloat( engine.ver );
     } else if ( /AppleWebKit\/(\S+)/.test(ua)){
         engine.ver = RegExp["$1"];
         engine.webkit = parseFloat(engine.ver);

         //确定是Chrome 还是 Safari
         if ( /Chrome\/(\S+)/.test(ua)){
             browser.ver = RegExp["$1"];
             browser.chrome = parseFloat(browser.ver);
         } else if ( /Version\/(S+)/test(ua)){
             browser.ver = RegExp["$1"];
             borwser.safari = parseFloat(browser.ver);
         } else {
             //近似的确定版本号
             var safariVersion = 1;

             if (engine.webkit < 100 ){
                 safariVersion = 1;
             } else if (engine.webkit < 312){
                 safariVersoin = 1.2;
             } else if (engine.webkit < 412){
                 safariVersion = 1.3;
             } else {
                 safariVersion = 2;
             }

             browser.safari = browser.ver = safariVersion;
         }

     } else if ( /KHTML\/(\S+)/.test(ua) || /Konqueror\/([^;]+)/.test(ua)){
         engine.ver = RegExp["$1"];
         engine.khtml = parseFloat(engine.ver);
     } else if ( /rv:([^\)]+)\) Gecko\/\d{8}/.test(ua)){
         engine.ver = RegExp["$1"];
         engine.gecko = parseFloat(engine.ver);

         //确定不是Firefox
         if( /Firefox\/(\S+)/.test(ua)){
             browser.ver = RegExp["$1"];
             browser.firefox = parseFloat(browser.ver);
         }

     } else if (/MSIE ([^;]+)/.test(ua)){
         engine.ver = browser.ver = RegExp["$1"];
         engine.ie = browser.ie = parseFloat(engine.ver);
     }

     //检测浏览器
     browser.ie = engine.ie;
     browser.opera = engine.opera;

     //检测平台
     var p = navigator.platform;
     system.win = p.indexOf("Win") == 0;
     systemp.mac = p.indexOf("Mac") == 0;
     system.xll = (p.indexOf("Xll")) == 1 || (p.indexOf("Linux") == 0);

     //检测 Windows 操作系统
     if( system.win){
         if( /Win(?:dows)?([^do]{2})\s?(\d+\.\d+)?/.test(ua)){
             if(RegExp["$1"] == "NT"){
                 switch(RegExp["$2"]){
                     case "5.0" :
                         system.win = "2000";
                         break;
                     case "5.1" :
                         system.win = "XP";
                         break;
                     case "6.0" :
                         system.win = "Vista";
                         break;
                     default : 
                         system.win = "NT";
                         break;
                 }
             } else if (RegExp["$1"] == "9x"){
                 system.win = "ME";
             } else {
                 system.win = RegExp["$1"];
             }
         }
     }

     //移动设备
     system.iphone = ua.indexOf("iphone") > -1;
     system.ipod = ua.indexOf("iPod") > -1;
     system.nokiaN = ua.indexOf("NokiaN") > -1;
     system.winMobile = (system.win == "CE");
     system.macMobile = (system.iphone || system.ipod);

     //游戏系统
     system.wii = ua.indexOf("Wii") > -1;
     system.ps = /playstation/i.test(ua);

     //再次检测呈现引擎、平台和设备

     return {
         engine : engine,
         browser : browser,
         system : system
     };
 }();
```

### 使用方法

用户代理检测一般适用于下列情形

* 不能直接准确地使用能力检测或怪癖检测。例如,某些浏览器实现了为将来功能预留的存根函数。在这种情况下,仅测试相应的函数是否存在还得不到足够的信息
* 同一款浏览器在不同的平台下具备不同的能力。这时候，可能就欧必要确定浏览器位于哪个平台下
* 位了跟踪分析等目的需要知道确切的浏览器

## 文章参考

[文章参考](文章参考)

* 《JavaScript高级程序设计》 第9章:客户端检测
