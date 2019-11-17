# APP用户体验升级实践

###### @author zhuzecong
###### @createTime 2019/11/16
###### @notes 这篇文章可能会比较长,哈哈,因为给一个APP的用户体验升级涉及到的东西太多了
###### @notes 相比成为顶级程序员,成为顶级产品经理更吸引我

###### @notes 因担心被人说泄漏源代码,全文代码全为伪代码。

自从来到项目组后,经常就听到产品经理说"又被业务叼了","业务说我们的APP太丑","业务说我们的APP不好用"。每一次从产品经理口中听到这样的话,我心里都很不是滋味,一方面,作为项目组中的一员,叼产品经理不就是叼我们项目组么,不就是叼我么。另一方面,作为一个程序员,产品就像儿子,儿子好看优秀才是每个父亲最希望看到的事情,有人说儿子烂,当父亲的感觉可想而知。

最近一月,在项目组开发任务终于缓了一些后,终于有时间去进行优化了。

以下是一些优化过程中的实践,有兴趣的可以了解下。

## 目录

* [1,让APP好看起来-样式优化](#1让APP好看起来-样式优化)
  * [1.1,选择一个主题色](#1.1,选择一个主题色)
  * [1.2,简约,即是美](#1.2,简约,即是美)
  * [1.3,将注意力还给用户](#1.3,将注意力还给用户)
  * [1.4,背景色,元素色,线条,阴影之间的故事](#1.4,背景色,元素色,线条,阴影之间的故事)
* [2,让APP好用起来-交互优化](#2,让APP好用起来-交互优化)
  * [2.1,交互只能做减法](#2.1,交互只能做减法)
  * [2.2,面向产品经理的错误提示设计]
    * [2.2.1,页面级错误提示设计](#2.2.1,页面级错误提示设计)
    * [2.2.2,接口级错误提示设计](#2.2.2,接口级错误提示设计)
  * [2.3,后端编程让异常无处可跑的小技巧](#2.3,后端编程让异常无处可跑的小技巧)
    * [2.3.1,try...catch...统筹全局](#2.3.1,try...catch...统筹全局)
    * [2.3.2,终点之外只有异常](#2.3.2,终点之外只有异常)
* [3,让APP快起来-性能优化](#3,让APP快起来-性能优化)
  * [3.1,"看起来很快"比"实际很快"更重要](#3.1,"看起来很快"比"实际很快"更重要)
  * [3.2,延迟渲染](#3.2,延迟渲染)
  * [3.3,请求缓存](#3.3,请求缓存)
  * [3.4,sql优化](#3.4,sql优化)
    * [3.4.1,使用索引](#3.4.1,使用索引)
    * [3.4.2,避免在where字句中使用or来连接条件](#3.4.2,避免在where字句中使用or来连接条件)
    * [3.4.3,能用between就不要用in](#3.4.3,能用between就不要用in)
    * [3.4.4,避免在where子句中对字段进行表达式操作](#3.4.4,避免在where子句中对字段进行表达式操作)
    * [3.4.5,少用like%xxx%](#3.4.5,少用like%xxx%)
  * [3.5,微服务合并](#3.5,微服务合并)
* [4,让APP自我迭代](#4,让APP自我迭代)
  * [4.1,监控url请求数量](#4.1,监控url请求数量)
  * [4.2,监控控件和页面使用率](#4.2,监控控件和页面使用率)
  * [4.3,向用户接收反馈](#4.3,向用户接收反馈)
* [5,总结](#5,总结)

## 1,让APP好看起来-样式优化

关于样式,在优化APP之前,和需求聊了一下,需求表示业务对我们APP样式的评价是:"就是觉得丑,具体哪里丑我也说不上来"。

"美"与"丑","好看"与"不好看"。往往就是这样,我就是觉得你丑,但是你要问我觉得你哪里丑,那我还真说不上来。

怎样的是美?怎样的是丑?我认为借用python之禅里的几句话可以很好的解释。

* 优美胜于丑陋（美的APP以"优美"为目标）
* 明了胜于晦涩（优美的APP应是功能明了的）
* 简洁胜于复杂（优美的APP应是简洁的）
* 复杂胜于凌乱（如果复杂不可避免，那功能间也不能有难懂的关系，要保持功能分类合理）
* 扁平胜于嵌套（优美的APP应当是扁平的）
* 间隔胜于紧凑（优美的APP元素与元素之间应该有适当的间隔）
* 可读性很重要（优美的APP是可读的）

### 1.1,选择一个主题色

移动互联网发展到今天,APP之间的竞争也早已从功能层次的厮杀升级到了"特色"的厮杀,APP的主题色即是APP样式层面的"特色",APP的主题色应与APP的业务场景形成清晰的对应关系,如下是一些常用APP的主题色:

淘宝主题色:给人的感觉如阳光微撒,活力无限,像是初夏走在阳光下的购物街上(淘宝业务:购物)。

<img alt="主题色-淘宝图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/主题色-淘宝.jpg?raw=true" width="400"/>

支付宝主题色:沉着冷静，成熟稳重。(支付宝业务:支付,储蓄,理财)

<img alt="主题色-支付宝图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/主题色-支付宝.jpg?raw=true" width="400"/>

QQ主题色:白色作为底调，淡蓝色作为主题色，给人的印象是还是学生时候的纯白年代，以及那些清淡，青涩的感情。(QQ主要客群:学生)

<img alt="主题色-QQ图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/主题色-QQ.png?raw=true" width="400"/>

我的APP的主题色:继(chao)承(xi)自支付宝的沉着冷静,成熟稳重。但更加鲜活,代表着我的APP更有活力(我喜欢有活力的感觉)。(我的APP业务:支付,商户收款)

<img alt="主题色-我的APP图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/主题色-我的APP.jpg?raw=true" width="400"/>

### 1.2,简约,即是美

什么是简约?在《互联网思维》中有个很好的解释:外在部分,外观要足够简洁;内在部分,操作流程要足够简化。

样式层的简约其实可以简单的理解为页面中的元素尽可能的"少",这种少是视觉上的少(可能实际中却增加了)。

要理解"简约",其实还可以参考代码层面的"优美的代码","优美的代码:功能一目了然,逻辑一目了然"

以前实习时候,实习导师对我的代码要求是:"如果你写的代码我5秒内没看懂,那就需要重构"。我认为这句话放在一款产品的样式层面也是适用的,如果一款产品,一个页面,5秒钟我没对页面中的功能形成一个强烈印象的话,那么这个页面的样式就需要"重构"。

那么怎样的页面是用户5秒类无法形成强烈印象的页面?这个问题其实也可以先讨论代码层面的问题"什么样的代码是5秒内不能让人看懂的代码？"。

对于这个问题我想大家很快脑海中就会想起那些职业生涯中难以忘记的"烂"代码和优雅的好代码。

对于那些5秒内让人看不懂的代码,我总结了以下,大抵是符合如下1或多条的代码:

* 1:代码行数多
* 2:命名烂而且代码没注释
* 3:代码注释太多太详细
* 4:代码组织不合理

而那些让人5秒内就能看懂的代码无非是:代码行数少,命名好,注释充分但不累赘,组织合理。

将上面的情况翻译到样式层面就是:

用户5秒类无法形成强烈印象的页面拥有的特征是:

* 1:页面中功能太多
* 2:功能命名烂而且没注释
* 3:页面功能注释太多,导致用户注意力不在功能本身,而在功能注释上
* 4:页面功能组织不合理

这里的"注释"在页面层次中指的是ICON(图标),IMG(图片),specialStyle(特殊样式),如下图片中用红圈标注的就是功能的"注释":

<img alt="发现精彩-首页-图标-注释图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/发现精彩-首页-图标-注释.png?raw=true" width="400"/>

<img alt="发现精彩-首页-今日美食-注释图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/发现精彩-首页-今日美食-注释.png?raw=true" width="400"/>

###### 事实上,对"功能来说",所有的文字,图片,样式都是功能的注释。

#### 1.2.1,页面中功能太多

"页面中功能太多":在用户感知中,页面中有多少功能?请先看一段伪代码示例。

现在有如下两段伪代码,分别表示页面A,和页面B。其中,对页面A而言,页面A中的100个功能是直接写在页面A中的,而页面B中的200个功能,分了4个代码段分别写在页面中。

在这个伪代码例子中回答上面的问题,可以很肯定的回答道:在用户感知中,页面A中有100个功能,而页面B中只有4个功能(页面B中的做法不正是我们写代码常用的函数抽取法么ლ(′◉❥◉｀ლ))。

``` java
// 代码段1: 页面A
{
    // 100 functions
}

// 代码段2: 页面B
{
    // segment 1
    {
        // 50 functions
    }

    // segment 2
    {
        // 50 functions
    }

    // segment 3
    {
        // 50 functions
    }

    // segment 4
    {
        // 50 functions
    }
}
```

###### 实例

上面的例子可以再看一个实例:

实例1:支付宝首页,功能繁多,但因为布局合理,在用户感知中其实只有6块功能

<img alt="支付宝-首页-功能块图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/支付宝-首页-功能块.png?raw=true" width="400"/>

实例2:我的优化前的APP,功能没几个,但因为样式太烂,在用户感知中却拥有11个功能。

<img alt="首页-优化前-功能块图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/首页-优化前-功能块.png?raw=true" width="400"/>

###### 关于在样式层怎样给功能分块,会在后文 [1.4,背景色,元素色,线条,阴影之间的故事]中介绍

#### 1.2.2,功能命名烂而且没注释

功能命名烂而且没注释:命名烂的功能指的是不能望文生义的命名,功能命名也和编程中的变量命名一样,命名应该符合变量(功能)的用途。

##### 实例

在优化APP的过程中,我们有个事件申报功能,事件申报??? 咋一看,我想没人知道这个功能是干啥的(事件?啥是事件啊.),而且点击事件申报进入功能详情后,也没有任何对功能的解释。后来向产品经理了解了后才知道事件申报功能的用途是:"当商家在使用APP时候遇到问题后使用事件申报申报事件"。这不就是用户反馈问题的地方么？ 于是将其名称改为了 **用户反馈**。

<img alt="设置-事件申报图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/设置-事件申报.png?raw=true" width="400"/>

#### 1.2.3,页面功能注释太多

页面功能注释太多:页面功能注释太多在样式层的表现更多是喧宾夺主,次要元素(次要元素包括“注释”元素)比主要元素更加吸引用户注意力,如下实例:

实例1:优化前我的APP店铺信息页面

在下图中,红圈中的元素在页面中的功能是告诉用户点击该按钮可以查看商户详情,其与商户名称,商户简称这两个元素的重要性级别应该是: 商户名称>商户简称>该元素。 但是在样式表现上却是: 该元素>商户名称>商户简称。 这就是喧宾夺主。

<img alt="我的-店铺信息图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/我的-店铺信息.png?raw=true" width="400"/>

优化后:

<img alt="我的-店铺信息-优化后图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/我的-店铺信息-优化后.png?raw=true" width="400"/>

实例2:优化前首页功能栏

优化前:图标>文字, 容易使用户记得图标却记不住功能,
优化后:图标只起辅助记忆作用

<img alt="首页-功能栏-优化前图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/首页-功能栏-优化前.png?raw=true" width="400"/>

<img alt="首页-功能栏-优化后图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/首页-功能栏-优化后.png?raw=true" width="400"/>

#### 1.2.4,页面功能组织不合理

页面功能组织不合理:样式层的页面功能组织不合理指的主要是页面功能未分类,或页面功能分类错误。

页面功能未分类造成的后果是上文提到的"页面中功能太多"

页面功能分类错误在样式上而言指的是使用了错误的样式,导致用户感知中的页面元素层级混乱。这一部分可以继续阅读下文:[1.4,背景色,元素色,线条,阴影之间的故事](#1.4,背景色,元素色,线条,阴影之间的故事)

### 1.3,背景色,线条,阴影,元素间距之间的故事

在编程中,代码组织方式有很多种,如设计模式,面向###编程,函数式编程,服务化...

而在APP的样式层中,组织功能的方式却只有那么几种:

* 1:使用背景色
* 2:使用线条
* 3:使用阴影
* 4:使用元素间隔

这4种都可以用来组织功能,但是它们却有比较明显的区别。

如下:

#### 1,背景色

通过给子元素和父元素设置不同的背景色表示子元素和父元素之间的层级关系。使用背景色表示元素与父元素之间的层级关系是最自然的。

例子:如下,设置父元素背景色为灰色,子元素背景色为白色将功能分为3块:

<img alt="组织功能-背景色图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-背景色.png?raw=true" width="400"/>

#### 2,线条

线条一般用来分隔同级别元素,使页面看起来清爽整齐。此外,还可以和背景色搭配使用用来强化元素与父元素之间的层级关系。

例子:线条分隔了同级别元素

<img alt="组织功能-线条-1图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-线条-1.png?raw=true" width="400"/>

例子:线条与背景色搭配使用,强化层级关系

<img alt="组织功能-线条-2图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-线条-2.png?raw=true" width="400"/>

#### 3,阴影

阴影可通过实现透视效果来实现元素之间的层级关系,但阴影相比使用背景色来实现层级关系有其明显的缺点:阴影相比背景色更容易被用户感知。(用户注意力永远需要留给页面中需要用户注意力的元素)(在material design规范中,google也不建议使用阴影)

例子:使用阴影表示元素之间的层级关系

<img alt="组织功能-阴影图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-阴影.png?raw=true" width="400"/>

例子:使用阴影表示层级关系和使用背景色表示层级关系的对比

能够清晰的看到使用阴影比使用背景色更易被用户感知。

使用阴影

<img alt="组织功能-阴影-对比图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-阴影-对比.png?raw=true" width="400"/>

使用背景色

<img alt="组织功能-阴影-对比图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-阴影-对比-1.png?raw=true" width="400"/>

<img alt="组织功能-阴影-对比图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-阴影-对比-2.png?raw=true" width="400"/>

## 2,让APP好用起来-交互优化

### 2.1,拼命做减法

亚里士多德曾说:"自然界选择最短的道路"。在互联网时代,用户的想法更简单:你能让我少一步,我就更愿意用这个产品。

### 2.2,面向产品经理的错误提示设计

在交互中,错误提示是块用户体验极重的地方,但在软件开发过程中,错误提示却常常是由开发人员编写。在这种情况下,软件的错误提示则常常是与开发人员的职业素养强相关,而与产品经理弱相关的。

面向产品经理的错误提示设计旨在将程序执行过程中的错误提示,全部暴露给产品经理,并可由产品经理修改错误提示信息,状态,显示时间等。

#### 2.2.1,页面级错误提示设计

页面级错误提示设计,在前端,创建一个UI方法,基础提示类,页面错误提示类,接口错误提示类。其各部分伪代码如下:

##### 2.2.1.1,公共UI方法

``` js
// config基本配置
let UiTypeEnums = {
    NOTICE:     Symbol('notice ui'),
    TOAST:      Symbol('toast'),
    PROMPT:     Symbol('prompt'),
    ALERT:      Symbol('alert'),
    HEADERTIPS: Symbol('headerTips')
}
let uiConfig = {
        // 错误提示的UI类型
        uiType:{
            type:       enum in UiTypeEnums,
            enums:      UiTypeEnums,
            default:    UiTypeEnums.TOAST
        },
        // 错误提示持续时间
        duration:{
            type:Number,
            default(){
                switch(this.uiType){
                    case UiTypeEnums.NOTICE:
                    case UiTypeEnums.TOAST:
                        return 5000
                    case UiTypeEnums.PROMPT:
                    case UiTypeEnums.ALERT:
                    case UiTypeEnums.HEADERTIPS:
                        return -1
                    default:
                        return 5000
                }
            }
        },
        // 错误提示标题
        title:{
            type:String,
            default:''
        },
        // 错误提示文字
        message:{
            type:String,
            default:''
        },
        // 其他配置忽略
        // ...
    }

/**
 * 提示:UI方法
 *
 */
exports default {
    $Tips(uiConfig) => {
        // 根据config在屏幕中渲染错误提示
    },
    UiTypeEnums
}
```

不同的UI类型样式如下:

notice:

<img alt="错误提示-notice图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/错误提示-notice.png?raw=true" width="400"/>

toast:

<img alt="错误提示-toast图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/错误提示-toast.png?raw=true" width="400"/>

alert:

<img alt="错误提示-toast图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/错误提示-alert.png?raw=true" width="400"/>

headerTips:

<img alt="错误提示-headerTips图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/错误提示-headerTips.png?raw=true" width="400"/>

##### 2.2.1.2,基础提示类

基础提示类用于保存与业务逻辑无关的错误提示:客户端错误和服务端技术层错误码转换。

``` js
// 服务端错误码和对应错误提示的map
let serverErrorCodeTipsMap = {
    '404': {
        uiType:     UiTypeEnums.ALERT,
        message:    '未发现指定接口或服务,请稍后再试'
    },
    // ...
}

// 客户端错误和对应错误提示的map
let clientErrorTypeTipsMap = {
    'NETWORK_DISABLED': {
        uiType:     UiTypeEnums.HEADERTIPS,
        message:    '网络连接不可用'
    },
    // ...
}

exports default {

    /**
     * 输入客户端错误类型,输出对应的错误提示设置
     * @param clientErrorType 客户端错误类型
     * @return
     */
    clientErrorTypeTips(clientErrorType) {
        return clientErrorTypeTipsMap[clientErrorType]
    }

    /**
     * 输入技术层错误码,输出对应的错误提示设置
     * @param techLevelLayerErrorCode 技术层错误码
     * @return
     */
    serverErrorCodeTips(techLevelLayerErrorCode) {
        return serverErrorCodeTipsMap[techLevelLayerErrorCode]
    }
}
```

##### 2.2.1.3,页面错误提示类

页面错误提示类中以路由为单位保存页面中的错误提示。

``` js
let pageErrorTipsMap = [
    {
        path:'login',
        children:[{
            path:'login',
            errorTipsMap:{
                // 用户名或手机号为空
                'EMPTY_USERNAME':{
                    message: '请先输入用户名或手机号',
                },
                // 密码为空
                'EMPTY_PASSWORD':{
                    message: '请先输入密码'
                },
                // 密码格式错误
                'ERROR_FORMAT_PASSWORD':{
                    title:'输入的密码格式不正确',
                    message: '密码必须包含数字,字母,特殊符号中的两种,且长度为8~16位',
                    uiType: UiTypeEnums.ALERT,
                }
                // ...
            }
        }]
    }
]

exports default {
    /**
     * 根据当前页面的执行上下文,获取当前页面的错误提示map
     * @param context 执行上下文
     * @return
     */
    getErrorTipsMap(context){
        // 取context中的路由
        // code ellipsis

        // 根据路由返回指定的错误提示map
        return errorTipsMap
    }
}
```

##### 2.2.1.4,接口错误提示类

接口错误提示类,接口错误提示类描述每只接口的功能,并且为其指定错误提示等级和错误提示的title。

``` js
let urlErrorTipsMap = {
    'login/login': {
        uiType: UiTypeEnums.ALERT,
        title:'登录失败',
    },
    // ... 没有这里描述的全部使用 toast类型,且无title
}

exports default {
    getUrlErrorTips(url){
        return urlErrorTipsMap[url]
    }
}
```

##### 2.2.1.5,重构HTTP方法

因为项目组中只用了post方法,这里用重写post方法作为示例

**重构前的post方法**:

``` js
/**
 * 重构前的post方法
 * @param url       post请求地址
 * @param sendObj   post请求发送给服务器的数据
 * @param callback  回调函数
 */
function post(url,sendObj,callback){
    // ...
}

// 调用post方法的地方
this.post('login/login',{
    userName:'######',
    passWord:'######'
},(data)=>{
    if(data.resCode != 200){
        this.Toast(data.resMsg)
    }

    // 登录成功逻辑
})
```

**重构后的post方法**

``` js
/**
 * 重构后的post方法
 * @param url       post请求地址
 * @param sendObj   post请求发送给服务器的数据
 * @param callback  回调函数
 */
function post(url,sendObj,callback){
    // ...
}

// 调用post方法的地方
this.post('login/login',{
    userName:'######',
    passWord:'######'
},(data)=>{
    if(data.resCode != 200){
        this.Toast(data.resMsg)
    }

    // 登录成功逻辑
})