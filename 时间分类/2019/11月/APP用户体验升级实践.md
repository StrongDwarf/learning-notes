# APP用户体验升级实践

###### @author zhuzecong
###### @createTime 2019/11/16
###### @notes 这篇文章可能会比较长,哈哈,因为给一个APP的用户体验升级涉及到的东西太多了
###### @notes 因时间和版本限制,文中大部分优化是已经做了,但还有少部分待做。

自从来到项目组后,经常就听到产品经理说"**又被业务叼了**","**业务说我们的APP太丑**","**业务说我们的APP不好用**"。每一次从产品经理口中听到这样的话,我心里都很不是滋味。一方面,作为项目组中的一员,叼产品经理不就是叼我们项目组么,不就是叼我么。另一方面,作为一个程序员,产品就像儿子,儿子好看优秀才是每个父亲最希望看到的事情,有人说儿子烂,当父亲的感觉可想而知。

最近一月,在项目组开发任务终于缓了一些后,趁机去优化了一些。

以下是一些优化过程中获得的实践感受,有兴趣的可以了解下。

## 目录

* [1,让APP好看起来-样式优化](#1让APP好看起来-样式优化)
  * [1.1,美的APP是有自己风格的](#11美的APP是有自己风格的)
  * [1.2,美的APP就像美的代码一样](#12美的APP就像美的代码一样)
    * [1.2.1,功能清晰](#121功能清晰)
    * [1.2.2,命名好](#122命名好)
    * [1.2.3,功能注释太多](#123功能注释太多)
    * [1.2.4,注释充分但不过度](#124注释充分但不过度)
  * [1.3,美的APP是干净整洁的](#13美的APP是干净整洁的)
* [2,让APP好用起来-交互优化](#2让APP好用起来-交互优化)
  * [2.1,做减法](#21做减法)
  * [2.2,错误提示程序设计](#22错误提示程序设计)
    * [2.2.1,前端错误提示设计](#221前端错误提示设计)
      * [2.2.1.1,公共UI方法](#2211公共UI方法)
      * [2.2.1.2,基础提示类](#2212基础提示类)
      * [2.2.1.3,页面错误提示类](#2213页面错误提示类)
      * [2.2.1.4,接口错误提示类](#2214接口错误提示类)
      * [2.2.1.5,重构HTTP方法](#2215重构HTTP方法)
      * [2.2.1.6,新旧设计对比](#2216新旧设计对比)
    * [2.2.2,后端错误提示设计](#222后端错误提示设计)
      * [2.2.2.1,使用@Validated注解](#2221使用@Validated注解)
      * [2.2.2.2,状态码管理](#2222状态码管理)
      * [2.2.2.3,try...catch统筹全局](#2223trycatch统筹全局)
* [3,让APP快起来-性能优化](#3让APP快起来-性能优化)
  * [3.1,"看起来很快"比"实际很快"更重要](#31"看起来很快"比"实际很快"更重要)
    * [3.1.1,延迟渲染](#311延迟渲染)
    * [3.1.2,虚幻填充](#312虚幻填充)
  * [3.2,请求缓存](#32请求缓存)
  * [3.3,sql优化](#33sql优化)
    * [3.3.1,使用索引](#331使用索引)
    * [3.3.2,避免在where字句中使用or来连接条件](#332避免在where字句中使用or来连接条件)
    * [3.3.3,能用between就不要用in](#333能用between就不要用in)
    * [3.3.4,禁止使用like%xxx%](#334禁止使用like%xxx%)
    * [3.3.5,禁止在where子句中对字段进行表达式操作](#335禁止在where子句中对字段进行表达式操作)
    * [3.3.6,禁止在where字句中对字段进行函数操作](#336禁止在where字句中对字段进行函数操作)
* [4,让APP自我迭代](#4让APP自我迭代)
  * [4.1,监控接口使用率](#41监控接口使用率)
  * [4.2,监控页面使用率](#42监控页面使用率)
  * [4.3,向用户获取反馈](#43向用户获取反馈)
* [5,总结](#5总结)

## 1,让APP好看起来-样式优化

在优化APP之前,和需求聊了一下,需求表示业务对我们APP样式的评价是:"**就是觉得丑,具体哪里丑我也说不上来**"。

那么怎样的APP是美的APP?

### 1.1,美的APP是有自己风格的

美的APP是有自己风格的,是独一无二的,是与众不同的。

与人类似,每个人的一言一行都是这个人的风格表现,APP的每个元素的样式,每个场景的交互方式,每个动画的效果也都是这款APP的风格。

但是对APP而言,其风格却很大一部分能都体现在它的主题色上,**主题色是在APP中除了白和黑之外能够被用户感知到的最多的颜色**。

选择了一个正确的主题色,在塑造合适风格上相当于完成了40%。

在选择APP主题色时,其主题色应与APP的业务场景形成清晰的对应关系,如下是一些常用APP的主题色:

**淘宝主题色**:给人的感觉如阳光微撒,活力无限,像是初夏走在阳光下的购物街上(淘宝业务:购物)。

<img alt="主题色-淘宝图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/主题色-淘宝.jpg?raw=true" width="400"/>

**支付宝主题色**:沉着冷静，成熟稳重。(支付宝业务:支付,储蓄,理财)

<img alt="主题色-支付宝图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/主题色-支付宝.jpg?raw=true" width="400"/>

**QQ主题色**:白色作为底调，淡蓝色作为主题色，给人的印象是还是学生时候的纯白年代，以及那些清淡，青涩的感情。(QQ主要客群:学生)

<img alt="主题色-QQ图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/主题色-QQ.png?raw=true" width="400"/>

**我的APP的主题色**:类似支付宝的沉着冷静,成熟稳重。但更加鲜活,代表着我的APP更有活力(我喜欢有活力的感觉)。(我的APP业务:支付,商户收款)

<img alt="主题色-我的APP图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/主题色-我的APP.jpg?raw=true" width="400"/>

###### 除了主题色外,如何在元素样式,交互方式,动画效果中展现APP风格,发现更多的是美学的范畴,暂时思路还没整理清晰,以后可能会写一篇文章来介绍一下。 暂时就先留个悬念在这了。

### 1.2,美的APP就像美的代码一样

**美的代码**是逻辑清晰的,命名好的,注释充分但不累赘的。

**美的APP**是功能清晰的,命名好的,注释充分但不累赘的。

#### 1.2.1,功能清晰

怎样的APP是功能清晰的APP?

讨论这个问题前可以先看怎样的代码是逻辑清晰的代码。

现在有如下两段伪代码:其中代码段1,直接将200行代码写在了一起,而代码段2将200行代码分为了4个代码段。

``` java
// 代码段1: 下单
{
    // 200 lines
}

// 代码段2: 下单
{
    // segment 1: 权限校验
    {
        // 50 lines
    }

    // segment 2: 订单创建
    {
        // 50 lines
    }

    // segment 3: 通道下单
    {
        // 50 lines
    }

    // segment 4: 通道返回参数检查
    {
        // 50 lines
    }
}
```

上面的哪段代码的逻辑是更清晰的?显而易见,是代码段2的代码。

逻辑清晰的代码是**根据代码逻辑将代码分成多个代码块后的代码**。

功能清晰的APP(在样式层)是**根据其使用场景将功能分成多个功能块后的功能**。

###### 实例

实例1:支付宝首页,功能繁多,但在用户感知中其实只有6块功能

<img alt="支付宝-首页-功能块图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/支付宝-首页-功能块.png?raw=true" width="400"/>

实例2:我的优化前的APP,功能没几个,但在用户感知中却拥有11个功能。

<img alt="首页-优化前-功能块图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/首页-优化前-功能块.png?raw=true" width="400"/>

功能在样式层如何分块?可见[1.3,背景色,线条,阴影,元素间距](#1.3背景色,线条,阴影,元素间距)

#### 1.2.2,命名好

怎样的APP是命名好的APP?

在APP中,功能命名要能够符合其意义,且功能命名要统一。

**实例:功能命名不符合意义**

在我的APP中,其中有个功能叫"事件申报",但是和产品经理沟通后,发现其作用是,当用户在产品使用过程中遇到困难或者问题了,可以通过使用该功能提出问题。

<img alt="设置-事件申报图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/设置-事件申报.png?raw=true" width="400"/>

优化后,修改名称为用户反馈

<img alt="用户反馈-4图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/用户反馈-4.png?raw=true" width="400"/>

**实例:功能命名不统一**

在某银行掌上生活APP中,底部栏中名字叫金融,点进去却是"卡.汽车"

<img alt="掌上生活-金融图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/掌上生活-金融.jpg?raw=true" width="400"/>

#### 1.2.3,注释充分但不过度

对APP而言 注释指的是ICON(图标),IMG(图片),specialStyle(特殊样式),等。如下图片中用红圈标注的就是功能的"注释":

<img alt="发现精彩-首页-图标-注释图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/发现精彩-首页-图标-注释.png?raw=true" width="400"/>

<img alt="发现精彩-首页-今日美食-注释图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/发现精彩-首页-今日美食-注释.png?raw=true" width="400"/>

注释能够帮助用户理解功能,或者和功能相互映衬,美化页面。

但是在使用注释时,必须特别注意的是,如果注释是功能元素外元素时,注释必须比功能本身更难被用户感知。

如下,是一个使用注释的反例:

在下面图片中红圈圈住的元素,其本身的作用本应该是"注释商户信息可点击,并且点击后会进入商户详情",但是,在视觉上,其却比商户名称更容易被用户感知。这就是过度注释。

<img alt="我的-店铺信息图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/我的-店铺信息.png?raw=true" width="400"/>

优化后如下:

<img alt="我的-店铺信息-优化后图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/我的-店铺信息-优化后.png?raw=true" width="400"/>

### 1.3,美的APP是干净整洁的

美的APP是干净整洁的,在美的APP中,屏幕中的元素并不是散乱的,而是有其相互联系的。

元素与元素之间的联系有3种:

* 1:父子关系(即包含关系)
* 2:兄弟关系(即并列关系)
* 3:无关系

如下图所示，红圈与红圈中的元素是兄弟关系,而红圈与蓝圈中的元素是父子关系。

<img alt="我的-店铺信息-优化后图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/支付宝-首页-元素关系.png?raw=true" width="400"/>

在APP中,表现元素之间的关系的方式有如下几种:

* 1:使用背景色
* 2:使用线条
* 3:使用阴影
* 4:使用元素间隔

这4种都可以用来组织功能,但是它们却有比较明显的区别。

#### 1,背景色

通过给子元素和父元素设置不同的背景色表示元素之间的父子关系。使用背景色表示元素之间的父子关系是最自然的。

例子:如下,通过设置父元素背景色为灰色,子元素背景色为白色将功能分为3块:

<img alt="组织功能-背景色图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-背景色.png?raw=true" width="400"/>

#### 2,线条

线条一般用来分隔兄弟关系元素。此外,还可以和背景色搭配使用用来强化元素的父子关系。

例子:线条分隔了同级别元素

<img alt="组织功能-线条-1图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-线条-1.png?raw=true" width="400"/>

例子:线条与背景色搭配使用,强化元素的父子关系

<img alt="组织功能-线条-2图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-线条-2.png?raw=true" width="400"/>

#### 3,阴影

阴影可通过实现透视效果来实现元素之间的父子关系,但阴影相比使用背景色来实现层级关系有其明显的缺点:**阴影相比背景色更容易被用户感知**,故不建议使用。(用户注意力永远需要留给页面中需要用户注意力的元素)(在material design规范中,google也不建议使用阴影)

例子:使用阴影表示元素之间的父子关系

<img alt="组织功能-阴影图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-阴影.png?raw=true" width="400"/>

例子:使用阴影表示父子关系和使用背景色表示父子关系的对比

能够清晰的看到使用阴影比使用背景色更易被用户感知。

使用阴影

<img alt="组织功能-阴影-对比图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-阴影-对比.png?raw=true" width="400"/>

使用背景色

<img alt="组织功能-阴影-对比图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-阴影-对比-1.png?raw=true" width="400"/>

<img alt="组织功能-阴影-对比图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/组织功能-阴影-对比-2.jpg?raw=true" width="400"/>

## 2,让APP好用起来-交互优化

### 2.1,做减法

亚里士多德曾说:"自然界选择最短的道路"。在互联网时代,用户的想法更简单:你能让我少一步,我就更愿意用这个产品。

###### 此部分还没想好怎样写,暂略

### 2.2,错误提示程序设计

错误提示程序设计以更好的管理错误提示为目标进行程序设计。

#### 2.2.1,前端错误提示设计

前端错误提示设计的核心思想是:

* 1:抽取公共错误提示UI方法,用于管理错误提示UI样式
* 2:抽取客户端错误和服务端技术层错误提示,用于单独维护
* 3:抽取页面错误提示,可视化所有页面中的错误提示
* 3:抽取接口错误提示,维护每只接口的错误提示UI配置
* 4:重构HTTP方法,使错误提示对前端透明化

其各部分伪代码如下:

##### 2.2.1.1,公共UI方法

``` js
// UI类型枚举
let UiTypeEnums = {
    NOTICE:     Symbol('notice ui'),
    TOAST:      Symbol('toast'),
    PROMPT:     Symbol('prompt'),
    ALERT:      Symbol('alert'),
    HEADERTIPS: Symbol('headerTips')
}
// 错误提示UI 基本配置
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
            default:'错误提示标题'
        },
        // 错误提示文字
        message:{
            type:String,
            default:'错误提示内容'
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
        // 根据uiConfig在屏幕中渲染错误提示
    },
    UiTypeEnums
}
```

不同的UI类型样式如下:

notice:

<img alt="错误提示-notice图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/错误提示-notice.png?raw=true" width="400"/>

toast:

<img alt="错误提示-toast图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/错误提示-toast.jpg?raw=true" width="400"/>

alert:

<img alt="错误提示-toast图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/错误提示-alert.jpg?raw=true" width="400"/>

headerTips:

<img alt="错误提示-headerTips图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/错误提示-headerTips.jpg?raw=true" width="400"/>

##### 2.2.1.2,基础提示类

基础提示类用于保存与业务逻辑无关的错误提示配置:客户端错误和服务端技术层错误码转换。

``` js
/**
 * BaseErrorTips.js
 * @notes 用于保存与业务逻辑无关的错误提示:客户端错误和服务端技术层错误码转换。
 */

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
    getClientErrorTypeTips(clientErrorType) {
        return clientErrorTypeTipsMap[clientErrorType]
    }

    /**
     * 输入技术层错误码,输出对应的错误提示设置
     * @param techLevelLayerErrorCode 技术层错误码
     * @return
     */
    getServerErrorCodeTips(techLevelLayerErrorCode) {
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

接口错误提示类,接口错误提示类描述每只接口的功能,并且为其指定错误提示配置。

``` js
// 文件名: ApiUrlErrorTips.js
let urlErrorTipsMap = {
    'login/login': {
        uiType: UiTypeEnums.ALERT,
        title:'登录失败',
    },

    // ...
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
this.oldPost = function(url,sendObj,callback){
    // ...
}

// 调用post方法的地方
this.post('login/login',{
    userName:'######',
    passWord:'######'
},(data)=>{
    if(data.resCode != 200){
        this.Toast(data.resMsg)
        // 登录失败逻辑
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
 * @param succFunc  请求成功后的回调函数
 * @param errorFunc 请求失败后的回调函数
 */
this.newPost = function(url,sendObj,succFunc,errorFunc) {
    // 调用旧post方法
    this.oldPost(url,sendObj,(data) => {

        // 请求执行失败
        if(!(data && data.resCode && data.resCode === SUCCESS_CODE)) {
            // 渲染错误提示
            this.printErrorTips(url,data)

            // 执行回调
            errorFunc()
        }

        // 请求执行成功
        succFunc(data.data)
    })
}

/**
 * 渲染错误提示
 * @param url post请求地址
 * @param data 后端post返回
 * @return 在页面中渲染一个错误提示组件
 */
this.printErrorTips = function(url,data){
    // false: 表示非技术层错误，  true: 表示是技术层错误
    let isTechLayerError = false

    // 判断是否是技术层错误
    // code ellipsis

    // 从错误提示类中获取对应的错误提示config
    let tipsConfig
    if(isTechLayerError){
        tipsConfig = BaseErrorTips.getServerErrorCodeTips(data.resCode)
    } else {
        tipsConfig =  ApiUrlErrorTips.getUrlErrorTips(url)
    }

    // 调用公共UI方法渲染错误提示组件
    this.$Tips(tipsConfig)
}
```

``` js
// 调用新post方法用例
this.post('login/login',{
    userName:'######',
    passWord:'######'
},(data)=>{
    // 登录成功逻辑
},()=>{
    // 登录失败逻辑
})
```

##### 2.2.1.6,新旧方法对比

新设计与旧设计的区别:

* 1:新设计将所有的所有错误提示都抽取出来了,便于管理和维护
* 2:新设计将错误提示相关代码对开发者透明化,减少了前端开发的工作量

#### 2.2.2,后端错误提示设计

因为目前后端使用状态码的错误提示已经很好了,我还没发现可以优化的地方,所以这里更多的是解释后端错误提示相关的技术。

##### 2.2.2.1,使用@Validated注解

对于参数校验,可以使用@Validated注解将参数的类型限制,错误提示等和实体类绑定。

@Validated注解可以在实体类中设置实体需要满足的规则,如非空,长度限制等。其使用方法如下:

* 1:引入依赖
* 2:给需要校验的实体类属性添加注解
* 3:在@Controller中校验数据

**1:引入依赖**:

只需要引入spring-boot-starter-web依赖即可

``` xml

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

**2:给需要校验的实体类属性添加注释**:

``` java
public class TestVo implements Serializable {
    private static final long serialVersionUID = -1L;

    @NotBlank(message="姓名不能为空")
    private String name;

    // ...
}
```

**3:在@Controller中校验数据**:

``` java
@Controller
public class TestController {

    @RequestMapping("/test")
    public String test(@Validated TestVo req,BindingResult bindingResult){
        // 如果参数有错误,返回第一个错误的提示信息
        if(bindingResult.hasErrors()){
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                return fieldError.getDefaultMessage();
            }
        }
        return "success";
    }
}
```

目前该框架已提供了如下这些校验规则,当然,该框架也支持自定义校验规则,不过这个请自行百度

``` js
@Null   被注释的元素必须为 null    
@NotNull    被注释的元素必须不为 null    
@AssertTrue     被注释的元素必须为 true    
@AssertFalse    被注释的元素必须为 false    
@Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值    
@Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值    
@DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值    
@DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值    
@Size(max=, min=)   被注释的元素的大小必须在指定的范围内    
@Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内    
@Past   被注释的元素必须是一个过去的日期    
@Future     被注释的元素必须是一个将来的日期    
@Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式    

// Hibernate Validator提供的校验注解:
@NotBlank(message =)   验证字符串非null，且长度必须大于0    
@Email  被注释的元素必须是电子邮箱地址    
@Length(min=,max=)  被注释的字符串的大小必须在指定的范围内    
@NotEmpty   被注释的字符串的必须非空    
@Range(min=,max=,message=)  被注释的元素必须在合适的范围内
```

###### 使用@Validated注解的优点在于:将接口的参数校验规则和参数校验对应的错误提示可视化

##### 2.2.2.2,状态码管理

对于程序执行过程中发生的所有错误,都需要明确该错误的状态码。

而对于错误码,可以使用如下2级错误码进行管理。

* 1:基础错误码类:管理程序运行过程中的非逻辑错误对应的错误码
* 2:指定服务错误码类: 管理指定服务的错误码

``` java
/**
 * 基础状态码
 * 保存技术层状态码和提示文字
 * 对于与HTTP协议中状态码对应的状态码定义与HTTP错误码规范相同
 * 对于自己系统自定义的基础错误码类,使用4位长度的int类型进行定义,
 */
public enum BaseStatus{
    SUCCESS     (200,"请求成功"),
    NOT_FOUND   (404,"未发现指定接口或服务,开发人员正在修复中,可稍后再使用该功能"),
    SERVER_ERROR(503,"服务器内部异常,开发人员正在修复中,请稍后再试"),

    UNKNOWN_EXCEPTION(1001,"程序发生未知异常,开发人员正在赶来修复,请稍后再试"),
    // ...
}
```

``` java
/**
 * 指定服务的状态码
 * 指定服务的状态码,使用6位长度的int类型进行定义,其中前两位表示服务类别
 * 例子:商户服务状态码
 */
public enum MerchantStatus {
    EMPTY_MERCHANT(010001,"商户信息为空,请检查商户号是否正确"),
    DISABLED_MERCHANT(010002,"商户被禁用"),
    DISABLED_MERCHANT_BY_RISK_STRAGE(010003,"商户因风险控制策略被禁用,了解风险控制策略请点击<a url='http:www.#####'>风险控制策略</a>"),

    // ...
}
```

###### 看了一些代码,很多开发者将错误提示直接以魔法数的形式写在代码中,这样在后期是很难管理的,使用状态码进行管理一是可复用,二是方便后期对错误提示进行用户体验方面的优化

##### 2.2.2.3,try...catch统筹全局

上面先是讲了参数校验,然后讲了状态码管理,但是在实际程序运行过程中,对用户体验影响最差的却不是错误提示写的不好,而是程序没有反应。

近期在查看系统日志时候,发现许多未被捕获的异常,当前端向服务器发起一支请求时,如果程序在运行过程中发生了未被捕获的异常,那么一般情况下这支请求会被一直被挂起,在前端的表现就是一直没反应。

要避免这种情况可以使用一个try...catch包住请求的全部逻辑,这样不管是怎样的异常,程序都能在最外层捕获。如下:

``` java
public ResVo func(funcVo req){
    ResVo res = new ResVo()
    try {

        // 逻辑代码
    } catch(Exception e) {
        logger.error(e)
        res.setResult(BaseStatus.UNKNOW_EXCEPTION.getCode(),BaseStatus.UNKNOW_EXCEPTION.getMessage())
    }

    return res;
}
```

## 3,让APP快起来-性能优化

在做完了APP的样式优化和交互优化后,APP已经能够称得上是一个合格的APP了,但是要想成为一个优秀的APP,除了样式要好看,交互要舒服之外,还得性能要快。

### 3.1,"看起来很快"比"实际很快"更重要

在开发者看来,要想让程序跑的快,就只有性能优化这一条路,但是很多时候,可以使用一些"欺骗"用户感知的方法去让程序看起来跑的很快。

**对用户而言,你实际跑的快还是慢他并不关心, 他只关心它能感受到的快和慢。**

在程序性能不变的情况下,让用户"看起来很快"的方法有很多,这里只介绍几种容易实现的。

#### 3.1.1,延迟渲染

延迟渲染:在页面切换时,**先执行页面数据初始化方法(多是向后端发请求),延迟一定时间再渲染页面**。(延迟渲染搭配元素交互动画后效果更佳)

**优化实例**:

在APP优化过程中,我们APP首页有个订单流水的功能,原来点击后会直接跳转到指定页面,而此时,因为向后端发送请求到后端返回数据会有一定时间间隔,这这段时间内屏幕会白屏。

优化后的程序执行流程如下:

点击订单流水功能 -> 异步发起获取订单流水HTTP请求 -> 执行点击动画(200ms)同时设置定时器,延迟200ms后跳转到订单流水页面。

执行动画

<img alt="订单流水-阴影-1图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/订单流水-阴影-1.png?raw=true" width="100"/>

<img alt="订单流水-阴影-2图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/订单流水-阴影-2.png?raw=true" width="100"/>

<img alt="订单流水-阴影-2图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/订单流水-阴影-3.png?raw=true" width="100"/>

动画结束后跳转到订单流水页面

<img alt="订单流水-阴影-1图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/订单流水-1.jpg?raw=true" width="400"/>

###### 使用延迟渲染后,一是能够有时间向用户展示点击效果(有点击效果的按钮比无点击效果的按钮哪种用户体验更好不用解释了吧),二是相比没有延迟渲染的页面,展示给用户的页面无数据状态时间更短

###### 在IOS设计规范中对动画时长的建议是不益超过300ms,所以延迟渲染的时长也不益超过300ms

**vue-router快捷实现延迟渲染的方法**:

vue-router提供了一些路由钩子,可以用于延迟渲染页面。

``` js
/**
 * Vue Router文档
 */
const Foo = {
  template: `...`,
  beforeRouteEnter (to, from, next) {
    // 在渲染该组件的对应路由被 confirm 前调用
    // 不！能！获取组件实例 `this`
    // 因为当守卫执行前，组件实例还没被创建
  },
  beforeRouteUpdate (to, from, next) {
    // 在当前路由改变，但是该组件被复用时调用
    // 举例来说，对于一个带有动态参数的路径 /foo/:id，在 /foo/1 和 /foo/2 之间跳转的时候，
    // 由于会渲染同样的 Foo 组件，因此组件实例会被复用。而这个钩子就会在这个情况下被调用。
    // 可以访问组件实例 `this`
  },
  beforeRouteLeave (to, from, next) {
    // 导航离开该组件的对应路由时调用
    // 可以访问组件实例 `this`
  }
}

/**
 * 实例:在路由变化时,先执行post方法,延迟300ms再加载
 */
beforeRouteEnter (to, from, next) {
    doPost()

    setTimeout(next,300)
}
```

#### 3.1.2,虚幻填充

虚幻填充:指当元素没有数据时候,给其设置无数据时的默认样式使其仿佛有数据。

**优化实例**

之前在优化统一收银台的时候,其页面逻辑如下:

当用户刚进入页面时,页面最开始是没有数据的,显示样式如下:

<img alt="统一收银台-3图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/统一收银台-3.png?raw=true" width="400"/>

然后随着请求的数据返回,页面会在用户肉眼可见中,由上述页面变化成下面的页面。商户名称等文字都是突然出现的,极为突兀,用户体验不好。

<img alt="统一收银台-2图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/统一收银台-2.png?raw=true" width="400"/>

最近考虑了一下,该页面样式逻辑可以优化如下:

当用户刚进入页面时,页面显示如下:

<img alt="统一收银台-4图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/统一收银台-4.png?raw=true" width="400"/>

当数据返回后,页面变为:

<img alt="统一收银台-2图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/统一收银台-2.png?raw=true" width="400"/>

###### 虽然大部分情况下当页面中的数据还在加载时,可以只是简单的给页面一个loading效果。但当loading时间较长时,使用虚幻填充用户体验更好。

### 3.2,请求缓存

请求缓存:即客户端将请求缓存起来,当再次调用相同请求时直接从缓存中读取。

实现如下:

``` js
// 这里因为APP较小,将所有post请求返回的数据缓存在内存中也是不会有啥影响
// 如果APP较大的话,请使用其他方式缓存数据
let cache = new Map()
// 用一个内部变量指向Date
let date = Date

/**
 * 重构POST方法
 * @param config.nonuseCache:   true:不使用缓存
 * @param config.refreshAllCache:  true:刷新全部缓存
 * @param config.cacheTime:     请求缓存时间:默认5分钟
 */
this.newPost = (url,sendObj,callback,config) => {
    if(config.refreshAllCache){
        cache.clear()
    }

    // 不使用缓存
    if(config.nonuseCache){
        this.oldPost(url,sendObj,(data)=>{
            // 异步缓存数据
            setTimeout((url,sendObj,data,config)=>{
                let key = url + "&" + JSON.stringify(sendObj)
                let value = {
                    data:getDeepCloneObj(data)
                }

                if(config.cacheTime && typeof config.cacheTime === 'number'){
                    value.expiryTime = date.now() + config.cacheTime
                }else{
                    value.expiryTime = date.now() + 300000
                }

                cache.set(key,value)
            },0);

            callback(data)
        })
        return
    }

    // 使用缓存
    // 先检查有没有缓存,没有就发送请求并缓存
    // code ellipsis
}
```

### 3.3,sql优化

在联机程序中,sql的执行效率对程序执行效率的影响是最大的。

最近在做APP性能优化的时候,检查了后端接口中的sql语句,发现了一些能够明显提高sql执行效率的sql优化方式,如下。

#### 3.3.1,使用索引

使用索引:索引的好处自不必多说。但是**大部分情况,开发人员并不会给数据库表添加索引**。

不添加索引,从开发人员的角度来看,这是情有可原的,毕竟,对开发来说,加不加索引,并不影响其功能实现,而且有些时候,开发人员对业务不熟悉,是无法创建合适索引的。但是从项目组的角度来看,加不加索引对性能影响是很严重的。

要避免这种情况,**可以由项目组的技术负责人或后端开发负责人对新增表结构进行整理,并由其创建索引**。

#### 3.3.2,避免在where字句中使用or来连接条件

错误示例:导致全表查询

``` sql
select id from t where num=10 or num=20
```

正确示例

``` sql
select id from t where num=10
union all
select id from t where num=20
```

#### 3.3.3,能用between就不要用in

错误示例:导致全表查询

``` sql

select id from t where num in (1,2,3)
```

正确示例

``` sql
select idfrom t where num between 1 and 3
```

#### 3.3.4,禁止使用like%xxx%

当遇到程序中有like %XXX% 代码的时候,很多情况下,单纯的改动sql语句是不行的。这次在APP优化的时候,我们就遇到了一个情况: 同业竞品中有个根据订单号后4位查询订单的功能,但是因为我们系统中订单号生成规则是: 时间(精确到毫秒)+当前毫秒中的下单数。  由此规则生成的订单号后4位几乎都是0001。所以当时我们的开发实现的是根据订单号模糊查询订单。其sql语句如下:

```
select id from t where num like '%12345%'
```

这条语句在测试环境中测试的时候是没有问题的(因为测试环境的数据量不大),但是一放到生产环境中,因为全表扫描,就贼慢。

考虑了一下后,我们优化的方案是: 修改订单号生成规则, 创建数据库订单号的反向索引, 修改sql语句。

**修改订单号生成规则**

将订单号生成规则修改为:时间(精确到毫秒) + 下单编号。  其中下单编号是一个从0001开始的自增数,当其大于9000时自增时变为1。

**创建数据库订单号的反向索引**

``` sql
create index PK_T_NUM on t(num) REVERSE;
```

**修改sql语句**

``` sql
select id from t where  num like '12345%'
```

#### 3.3.5,禁止在where子句中对字段进行表达式操作

错误示例:

``` sql
select id from t where num/2=100
```

正确示例

``` sql
select id from t where num=100*2
```

#### 3.3.6,禁止在where字句中对字段进行函数操作

错误示例:

``` sql
select id from t where substring(name,1,3)='abc'
```

正确示例:

``` sql
select id from t where name like 'abc%'
```

## 4,让APP自我迭代

在优化了样式,交互,性能后,APP已经变得很棒了。但是这种棒只是目前看来的棒。事实上,**任何一款APP都是迭代的,不断改善的**。

而我们项目组以前APP的新增功能和优化几乎都是由业务人员提出的。这种情况是十分不合理的,产品的任何功能新增和优化的需求应该都是由产品经理提出或由技术人员提出。业务,让他们放心的用产品去做业务就好了。(这里也许有人会说:用户才是最知道产品哪里是需要改进的。但是,产品经理本身就是用户,如果一个产品经理对产品用户体验的感知还不如一个普通用户,那么是否应该称其为##经理,而不是产品经理?)

就技术层而言,可以通过监控请求和页面使用率 来获取产品中各功能的使用情况,为APP的迭代提供燃料-数据。

### 4.1,监控接口使用率

监控url请求数,可以获取每只接口的使用率,根据使用率获知功能使用情况。

其实现如下:

**后端**:后端新增一只接口,获取前端传来的参数,并将信息记入数据库。

接收参数为:**请求url,请求方法,商户号,操作员ID,收银员ID,当前时间,设备经纬度,设备序列号等。**

**前端**:重构HTTP请求,每次发送HTTP请求时都异步调用上述接口

### 4.2,监控页面使用率

**后端**:新增接口,用于接收页面使用信息,并将信息记入数据库。

接收参数为:**即将离开的页面url,即将进入的页面url,当前时间,商户号,操作员ID,收银员ID,设备经纬度,设备序列号。**

**前端**:使用vue router提供的路由钩子,在每次路由进入和离开时都异步发送请求

``` js
const router = new VueRouter({ ... })

router.beforeEach((to, from, next) => {
   // 异步发送请求给后端
})
```

### 4.3,向用户获取反馈

一人拾柴火不旺,众人拾柴火焰高。一方面,可能因为测试不充分导致程序出现bug,还有一方面,可能因为遗漏的优化的地方导致用户体验不好。 针对这种情况,可以在系统中增加用户反馈功能,如下:

我的页面用户反馈菜单。

<img alt="用户反馈图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/用户反馈-2.png?raw=true" width="400"/>

用户反馈列表页

<img alt="用户反馈图片" src="https://github.com/StrongDwarf/learning-notes/blob/master/public/img/APP用户体验升级/用户反馈-3.png?raw=true" width="400"/>

## 5,总结

上诉工作都做完后,一款让**用户用得不吐槽**,**能感知用户行为**的产品才算是诞生了,但是**优化与重构的思想,是值得贯彻在产品开发全生命周期中的**,重构与优化也将永不止步。

最后,最近得知,过段时间可能会担任团队中前端开发小组组长的职责,所以**下一篇文章大概率可能是开发效能和质量提升的实践之类的文章**了,不过如果有时间,还是会做各种优化的,有好的实践也会继续分享的,哈哈,敬请期待。

最后的最后, 日常吹牛:"我出手的产品, 一定会是最棒的产品","我带的团队,一定会是最棒的团队"。