# JS中的模块化

以功能块为单位进行程序设计，实现其求解算法的方法称为模块化，原则是"高内聚,低耦合"。"低耦合"是模块与模块之间要相互独立。模块化的目的是为了降低程序复杂度，使程序设计，调试和维护等操作简单化。

[目录](#目录)
* [为什么需要模块化](#为什么需要模块化)
* [AMD和CMD规范](#AMD和CMD规范)
* [ES6标准的模块支持](#ES6标准的模块支持)

## 为什么需要模块化

在JavaScript发展初期，AJAX并未普及，JavaScript还是一种"玩具语言"，用来在网页上进行表单验证，实现简单的动画效果等。回想一下，那个网页上到处小广告飘来飘去的时代是多么领人痛恶。Web1.0相当长一段时间的页面脚本都在以实例中的形式存在着。代码如下:

``` JavaScript
if(xx){
    //...
}else if(xx){
    //...
}else{

}
```

代码简单的堆积在一起,只要能顺利地从上往下依次执行即可。但随着网站越来越复杂，实现网站功能的JavaScript代码也越来越大，网页越来越像桌面程序，许多问题开始被暴露出来，比如全局变量冲突，函数命名冲突，依赖关系处理等。实例中b.js依赖a.js，标签的书写顺序必须按照先后排列，代码如下:

``` JavaScript
<script type="text/javascript" src="a.js"></script>
<script type="text/javascript" src="b.js"></script>
```

庞大复杂的应用需要一个团队分工协作，进度管理，单元测试等，开发者不得不使用软件工程的方法管理网页的业务逻辑。

JavaScript模块化编程，已经成为一个迫切的需求。但是JavaScript并不是一种模块化编程语言，不支持"类"和模块(ES6支持这两个,但在浏览器环境使用还要借助Babel进行编译，在后面的章节会进行介绍)。经过JavaScript开发者和社区的不懈努力，现在已经能够模拟出"模块的效果"。

### 原始写法

既然模块是一个功能,那么可以把实现功能的函数放在同一文件夹中，实例代码如下:

``` javascript
function a(){
    //...
}
function b(){
    //...
}
```

函数a1和b2组成一个模块,其他文件先假装该模块,再对函数进行调用。这种做法的缺点很明显，一旦代码量庞大后很难保证不发生变量冲突，容易污染全局变量，而且该模块成员之间没有太多必然联系

### 添加命名空间

为了解决上面这种写法的缺点,可以加入命名空间来管理模块，也就是使用全局变量的模式。实例代码如下:

``` javascript
var module_special = {
    _index:0,
    a1:function(){
        //...
    },
    b2:function(){
        //...
    }
}
```

### 立即执行的函数

在上面的代码中_index变量在前面加_声明为私有变量是一般约定俗成的写法。但是其仍然是一个外部可访问的变量,可以使用匿名函数将其转化为私有变量，代码如下:

``` javascript
var module_special = (function{
    var _index = 0
    var a1 = function(){
        //...
    }
    var b2 = function(){
        //...
    }
    return {
        a1:a1,
        b2:b2
    }
})()
```

## AMD和CMD规范

现在流行的模块化规范主要有commonJS,AMD和CMD规范。其中commonJS实现代表是Node.js，AMD规范的实现代表是RequireJS,CMD规范的实现代表是Sea.js

### CommonJS规范

Node.js是服务器端JavaScript解释器,允许开发者能够用JavaScript的语法去编写服务器程序。Node.js应用由模块组成。采用CommonJS规范，通过全局方法require来加载模块，实例代码如下:

``` javascript
var http = require('http')
var server = http.createServer((req,res) => {
    res.statusCode = 200
    res.setHeader('Content-Type','text/plain')
    res.end('hello world')
})
server.listen(3000,'127.0.0.1',() => {
    console.log('Server runing at http://127.0.0.1:3000')
})
```

Node.js内部提供了一个Module构建函数，所有模块都是Module的实例，每个模块内部，都有一个Module对象，代表当前模块。包含如下属性：

* id:模块的标识符，通常是带有绝对路径的模块文件名
* filename:模块的文件名
* parent:返回一个对象,表示调用该模块的模块
* loaded:返回一个布尔值,表示模块是否已经完成加载
* children:返回一个数组，表示该模块要用到的其他模块
* exports:表示模块对外输出的值

CommonJS模块的特点如下:

* 所有模块都有单独作用域，不会污染全局作用域
* 重复加载模块只会加载第一次，后面都从缓存读取
* 模块加载的顺序按照代码中出现的顺序
* 模块加载是同步的

### AMD规范

CommonJS模块采用同步加载，适合服务器却不适合浏览器。AMD规范支持异步加载模块，规范中定义了一个全局变量define函数，描述如下:

``` javascript
define(id?,dependencies?,factory)
```

第一个参数id,为字符串类型，表示模块标识，为可选参数。若不存在则模块标识默认定义为在加载器中被请求脚本的标识。如果存在，那么模块标识必须为顶层的或者一个绝对的标识
第二个参数dependencies，定义当前所依赖模块的数组。依赖模块必须根据模块的工厂方法优先级执行，并且执行的结果按照依赖数组中的位置顺序以参数的形式传入(定义中模块的)工厂方法中
第三个参数factory,为模块初始化要执行的函数或对象。如果为函数，只执行一次。如果是对象，次对象应该为模块的输出值。如果工厂方法返回一个值(对象，函数或任意强制类型转化为true的值)，应该设置该模块的输出值

#### 创建一个标准的AMD模块

创建标识为"alpha"的模块,依赖于内置的"require"和"exports"模块和外部标识为"beta"的模块。require函数取得模块的引用，从而使模块没有作为参数定义，也能够被使用。

``` javascript
define("alpha",["require","exports","beta"],function(require,exports,beta){
    export.verd = function(){
        return beta.verb();
        //或者:
        return require("beta").verb()
    }
})
```

#### 创建一个匿名模块

define方法允许省略第一个参数，当省略第一个参数定义模块时，模块文件的文件名即模块标识，该模块为匿名模块。定义一个依赖于alpha模块的匿名函数，代码如下:

``` javascript
define(["alpha"],function(alpha){
    return{
        verb:function(){
            return alpha.verb() + 1;
        }
    }
})
```

#### 仅有一个参数的define

define的前两个参数都是可以省略的。第三个参数有两种情况，一种是JavaScript对象，另一种是函数。

如果参数是一个对象，那么可能是一个包含方法的对象，也可能仅提供数据，或都存在，代码如下:

``` javascript
define({
    name:'add',
    add:function(x,y){return x+y}
})
```

如果参数是一个函数，其用途是快速发现，适用于较小的应用，代码如下:

``` javascript
define(function(){
    //使用math-util这个模块
    var mathUtil = require('math-util');
    return mathUtil.add(1,2)
})
```

#### 局部require与全局require

局部require可以被解析成一个符合AMD工厂函数规范的require函数，实例代码如下:

``` javascript
define(['require'],function(require){
    //...
})
//或者
define(function(require,exports,module){
    //....
})
```

#### RequireJS

RequireJS库能够把AMD规范应用到实际浏览器Web端的开发中。其主要解决了两个问题:实现JavaScript文件的异步加载，避免网页失去响应；管理模块之间的依赖性，便于代码的编写和维护。

要使用requireJS如下:
先从官网下require.js文件,然后使用如下:

``` javascript
<script src="js/require.js" data-main="js/main"></script>
```

data-main属性定义Web程序的主模块,在这里"js/main"即主模块,省略了后缀的".js"，Requires.js在加载脚本引用时会为其默认添加。主要模块也称为入口文件，类似于C语言的main函数，所有代码都从这儿开始运行。main.js的实例代码如下:

``` javascript
require(['jquery','underscore',"backbone"],function($,_,backbone){
    //业务代码
})
```

RequireJS会依次加载类库jQuery,underscore，backbone。然后再运行函数.使用require.config()方法，开发者可以对模块的加载路径进行自定义，假设这些库文件都在和main.js同级的lib文件夹下，实例代码如下:

``` javascript
require.config({
    paths:{
        "jquery":"lib/jquery.min",
        "underscore":"lib/underscore.min",
        "backbone":"lib/backbone.min"
    }
})
```

或者使用属性basrUrl定义基础路径,代码如下:

``` javascript
require.config({
    basrUrl:"js/lib",
    paths:{
        "jquery":"jquery.min",
        "underscore":"underscore.min",
        "backbone":"backbone.min"
    }
})
```

RequireJS支持加载非AMD规范的模块，支持使用require.config方法来定义一些特性，比如Underscore和Backbone.js(非AMD规范),实例代码如下:

``` javascript
require.config({
    shim:{
        'underscore':{
            exports:'_'
        },
        'backbone':{
            deps:['underscore','jquery'],
            exports:'backbone'
        }
    }
})
```
代码中的shim属性,专门用来配置不兼容的模块，具体来说，每个模块要定义:exports值(输出的变量名),表明这个模块外部调用时的名称:deps数组,表明该模块的依赖

### CMD规范

CMD规范全称为Common Module Definition,下面介绍该规范实现的关键函数。

#### define函数

在CMD规范中，一个模块就是一个文件，书写格式如下:

``` javascript
define(factory)
```

define是一个全局函数,用来定义模块，接收factory参数，factory可以是一个函数，也可以是一个对象或字符串。当factory参数为对象，字符串时，表示模块的接口就是该对象，字符串。比如，定义一个JSON数据作为factory参数，代码如下:

``` javascript
define({'foo':'bar'})
```

也可以通过字符串定义模块模板，代码如下:

``` javascript
define('hello ,I am {{name}}')
```

factory为函数时,表示是模块的构造方法。执行该构造方法，可以得到模块向外提供的接口。factory方法在执行时，默认会传入三个参数:require,exports和module，代码如下:

``` javascript
define(function(require,exports,module){
    //模块代码
})
```

define也可以接收两个以上的参数，语法如下所示:

``` javascript
define(id?,deps,factory)
```

字符串id表示模块标识，数组deps是模块依赖，实例代码如下:

``` javascript
define('define',['jquery'],function(require,exports,module){
    //模块代码
})
```

define.cmd方法可用来判定当前页面是否有CMD模块加载器，实例代码如下:

``` javascript
if(typeof define === "function" && define.cmd){
    //有sea.js等CMD模块加载器存在
}
```

#### require函数

require是一个方法，接收模块标识作为唯一参数，用来获取其他模块提供的接口。实例代码如下:

``` javascript
define(function(require,exports){
    var a = require('./a')      //获取模块a的接口
    a.doSomeThing();            //调用模块a的方法
})
```

#### exports对象

exports是一个对象，用来向外提供模块接口，实例代码如下:

``` javascript
define(function(require,exports){
    exports.foo = 'bar'
    exports.doSomething = function(){}
})
```

除了给exports对象增加成员外，还可以使用return直接向外提供接口，实例代码如下:

``` javascript
define(function(require){
    return{
        foo:'bar',
        doSomeThing:function(){}
    }
})
```

Sea.js作为CMD规范的经典实现,追求简单，自然的代码书写和组织方式，具有以下核心特性。

* 简单友好的模块定义规范，Seajs遵循CMD规范，可以像Node.js一样书写模块代码。
* 自然直观的代码组织方式，依赖自动加载，配置简洁清晰，可以让开发者更多享受编码的乐趣

Seajs还提供常用插件，帮助开发调试和性能优化，并具有丰富的可扩展接口。
使用Seajs如下:

``` javascript
seajs.config({
    base:"../sea-modules",
    alias:{"jquery":"jquery/jquery/1.10.1/jquery.js"}
})
//加载入口模块
seajs.use("../src/main")
```

main.js是程序的入口文件,实例代码如下:

``` javascript
//所有模块都通过define来定义
define(function(require,exports,module){
    //通过require引入依赖
    var $ = require('jquery')
    //通过exports对外提供接口
    exports.doSomething = ...
    //或者通过module.exports提供整个接口
    module.exports = ...
})
```

## ES6标准的模块支持

ES5及之前的版本不支持原生模块化，需要引入AMD规范的RequeryJS或CMD规范的Seajs等第三方库实现。直到ES6才支持原生模块化，其不但具有CommonJS规范和AMD规范的优点，而且实现得更加友好，语法较之CommonJS更简洁，支持编译时加载或者叫静态加载，循环依赖处理更加优秀

ES6模块功能主要由两个命令构成:export和import。export命令用于规定模块的对外接口，import命令用于输入其他模块提供的功能。

### export

在ES6中,一个模块也是一个独立的文件，具有独立的作用域，通过export命令输出内部变量，实例代码如下:

``` javascript
var name = 'bus'
var color = 'green'
var weight = '20吨'
export {name,color,weight}
```

export命令除了输出变量，还可以输出函数或类,实例代码如下:

``` javascript
export function run(){console.log('Bus is running')};
```

可以使用as关键字对输出的变量，函数,类,重新命名

``` javascript
var name = 'bus'
var color = 'green'
var weight = '20吨'
function run(){console.log('Bus is running')}
export{
    name as busName,
    color as busColor,
    weight as busWeight,
    run as busRun
}
```

注意:export命令规定的是对外的接口，必须与模块内部的变量建立一一对应关系

以下导出方式会报错，代码如下:

``` JavaScript
//报错
export 20
//报错
var name = 'bus'
export name
//正确的导出方式
export {name}
```

### import

import命令用于导入模块,实例代码如下:

``` javascript
import {name,color,weight,run} from './car'
```

导入模块的时候也可以使用as关键字对模块进行重命名，实例代码如下:

``` javascript
import {name as busName} from './car'
```

如果一个模块没有输出，也可以用于加载其他模块,实例代码如下:

``` javascript
import lodash
```

上诉代码仅仅是加载了lodash模块,没有任何输入值。重复加载模块也只会执行一次。

可以通过星号"*"整体加载某个文件,实例代码如下:

``` javascript
import * as car from './car'
console.log(car.name)
console.log(car.color)
```

### export default命令

从前面的例子可以看出,使用import命令加载模块时需要知道变量名或者函数名，或者整个文件，否则无法加载。为了方便，可以使用export default 命名为模块指定默认输出。实例代码如下:

``` javascript
export default function(){console.log('default')}
```

加载该模块时,可使用import命名为其指定任意名字，实例代码如下:

``` javascript
import someName from './export-default'
someName();
```



