# 《VUE》笔记

###### author:朱泽聪
###### createTime:2019/7/8

## 目录

* [基本概念](#基本概念)
* [常用指令](#常用指令)
* [封装组件](#封装组件)
* [风格指南](#风格指南)

## 基本概念

### vue是什么?

* VUE是Vue.js的简称
* Vue的核心库只关注视图层,不仅易于上手,还便于与第三方库或集邮项目整合(渐进式)
* 数据绑定机制,数据改变,视图自动更新(响应式)
* Vue框架是MVVM模型的一种实现

### MVC模型

* MVC全程model-view-controller
* 一切架构设计的基础
* Controller用来控制业务逻辑
* M,V,C三者互相可见,互相引用
* 维护性和扩展性极差

### MVP

* model-view-Presenter
* 实现model和view解耦,Presenter通信,
* 所有的交互都发生在Presenter内部,承担Controller的工作
* 数据单向传递

### MVVM

* model-view-viewmodel
* 通过数据模型通信,实现视图和数据模型的双向绑定
* 由MVP发展而来

### 深入响应式原理

响应式

* 数据模型是JavaScript对象
* 数据模型改变,视图自动更新
* Object.defineProperty把data属性全部转化为getter/setter
* 初始化watcher,记录依赖
* setter触发时,会通知watcher,从而使它关联的组件从新渲染

### 检测变化的注意事项

* 属性必须在data对象上存在才能让Vue将它转化为响应式的
* 对于已经创建的实例,可以使用Vue.set(object,propertyName,value)方法向前台对象添加响应式属性。
* this.$set()

``` js
var vm = new Vue({
    data : {
        a : 1
    }
})
// 'vm.a' 是响应式的

vm.b = 2
// 'vm.b' 是非响应式的
```

### 异步更新队列

* Vue在更新DOM时是异步执行的
* 事件循环'tick'
* 缓冲数据变更并去重
* Vue更新完DOM,触发Vue.nextTick(callback)

``` html
<div>
    <div id = "example">{{message}}</div>
</div>
<script>
var vm = new Vue({
    el : '#example',
    data : {
        message : '123'
    }
})
vm.message = 'new message'  // 更改数据
vm.$el.textContent === 'new message'     // false
Vue.nextTick(function(){
    v,.$el.textContent === 'new message'    // true
})
```

### 模板语法

* 基于HTML,允许开发者声明式地将DOM绑定到Vue实例的数据
* 模板编译成虚拟DOM渲染函数
* 智能计算重新渲染的组件数,减少DOM操作次数
* 渲染(render)函数

``` html
<body>
    <p>Using mustaches:{{rawHtml}}</p>
    <p>Using v-html directive:<span v-html="rawHtml"></span></p>
</body>
<script>

    render:function(createElement) {
        return createElement(
            'h' + this.level,   // 标签名称
            this.$slots.default //子节点数组
        )
    },
</script>
```

### JavaScript表达式

* 由运算元和运算符(可选)构成,并产生运算结果的语法结构
* 所有的数据绑定,Vue都提供了完全的JavaScript表达式支持
* 每个数据绑定只能对应单个表达式

``` js
{{number + 1}}
{{ok ? 'YES' : 'NO'}}
{{message.split('').reverse().join('')}}

<div v-bind:id="'list-' + id"></div>
```

### 指令

* 指令带有前缀 v-，是Vue提供的特殊特性,处理特定的响应式行为。
* 预期值是单个表达式
* 常用内置指令: v-if,v-for,v-show,v-bind,v-on,v-model,v-ref
* 自定义指令:Vue.directive()

### 计算属性

* 优雅的处理复制的表达式
* 基于它们的响应式依赖进行缓存的
* 观察和响应Vue实例上的数据变动:侦听属性
* 写法:键是表达式名称,值是回调方法
* 回调方法不能使用箭头函数

``` js
computed : {
    // 计算属性的getter
    reverseMessage : function(){
        // 'this'指向vm实例
        return this.message.split('').reverse().join('')
    }
}
```

### 侦听器

* 监听数据变化的通用方式
* 写法:一个对象,键是表达式
* 值:
  * 方法:数据变化时会触发
  * 方法名:methods中的方法
  * 对象:handler,deep,immidiate
* 回调方法不能使用箭头函数

``` js
watch : {
    a : function(val,oldVal) {
        console.log('new: %s ,old: %s',val,oldVal)
    },
    // 方法名
    b : 'someMethod',
    c : {
        handler : function(val,oldVal) {
            /*
                ...
            */
        },
        deep : true
    },
    d : {
        handler : 'someMethod',
        immediate : true
    }
}
```

### class与style绑定

* 字符串拼接方式麻烦而易错
* Vue做了专门增强
* 操作元素的class列表和内联样式
* 表达式类型:字符串,对象，数组

``` html
<div v-bind:class="classObject"></div>

<div
    class = "static"
    v-bind:class = "{ active:isActive,'text-danger':hasError}"
></div>

<div v-bind:class="[isActive ? activeClass : '',errorClass]"></div>
```

### 组件

* 组件是可复用的VUE实例
* 组件可扩展HTML元素,封装可重用的代码
* 几乎任意类型的应用的界面都可以抽象为一个组件树

### 生命周期

* Vue实例从创建到销毁的过程
* 生命周期钩子
* 不要在钩子上使用箭头函数
* 生命周期列表:beforecreated,created,beforemount,mount,active,beforedestory,destory,

``` js
new Vue({
    data : {
        a : 1
    },
    create : function(){
        console.log('a is: ' + this.a)
    }
})
```

## 常用指令

### v-bind

* v-bind的基本用途是动态更新HTML元素或组件的属性
* 如下图的例子,当url或imageUrl改变的时候图片都会更新,而不再需要通过id或者name找到元素,然后修改元素属性进行更新

``` html
<img v-bind:url="imageUrl">
<a v-bind:href="url">链接</a>
```

### v-on

* 监听DOM事件,并在触发时运行一些JavaScript方法,对应methods属性
* 有时也需要访问原始的DOM事件。可以用特殊变量$event把他传入方法
* 事件修饰符
  * .stop:停止冒泡
  * .prevent:提交事件不再重载页面
  * .capture:事件捕获模式
  * .self:只当event.target是当前元素自身时触发
  * .once:事件将只会触发一次
  * .passive:滚动事件

### 条件渲染(v-if,v-else,v-else-if)

* 切换v-if模块时,有区域性编译/解除安装过程
* 惰性,如果条件为假,则不做任何操作

### v-show

* v-if有较高的切换消耗,v-show有较高的初始渲染消耗
* 如果需要频繁的切换,则使用v-show较好,如果在执行时条件不大可能改变,则使用v-if较好

### v-for

* 用于重复渲染元素
* 表达式形式:item in/of items
* items是源数据数组,而item则是被迭代的数组元素的别名
* 每项提供一个唯一key属性,从而重用和重新排序现有元素
* 变异方法:push,pop(),shift(),unshift(),splice(),sort(),reverse()
* 通过下标改变元素不会触发更新

``` html
<ul>
    <li v-for="lesson in lessons" :key="lessn.name">{{lesson.name}}</li>
</ul>
<script>
    lessons: [
        {name:'1'},
        {name:'2'},
        {name:'3'}
    ]
</script>
```

### v-model

* 在input,select,text,checkbox,radio等表单元素上建立双向绑定
* v-model修饰符
  * .lazy:转变为change事件同步,在失去焦点或按回车时才更新
  * .number:将输入转换成Number类型
  * .trim:自动过滤输入的首位空格
  * debounde:设定一个最小的延时,在每次敲击之后延迟同步输入框中的值

### 语法糖

* 语法糖是指在不影响功能的情况下,添加某种方法实现相同的效果,从而方便开发
* v-bind语法糖是":"
* v-on的语法糖是"@"
* v-model="test" -> :value = "test" @input="test=$event.target.value"
* input针对的是text或者textarea

## 封装组件

### 全局注册与局部注册

全局注册

* 简便,注册之后可以用在任何的Vue实例中
* 导致打包体积增大

``` js
Vue.component('my-component-name',{
    // ...选项...
})
```

局部注册

* components选项
* 按需引入

``` js
new Vue({
    el:'#app',
    components : {
        'component-a':ComponentA
    }
})
```

### 组件引用方法

* 组件通过ref进行注册,然后通过$refs来引用,这一点和传统上id和name类似,在vue中推荐使用$refs
* 避免在模板和计算属性中访问$refs
* ref不是响应式的,避免做数据绑定

``` html
<rtp-tree-table ref="table" ></rtp-tree-table>

query(){
    this.refs.table.reload()
}
```

### 组件通信

* 三种方式:传参,事件,插槽
* 传参,方向:父组件->子组件
* 事件,方向:子组件->父组件
* 插槽:
  * 组件内部设置挂载点
  * 内容由父组件决定
  * 可用于填充或替换组件内置节点

### 组件自定义

需求:

* 自定义一个数字输入框
* 可以动态绑定数据
* 对输入的内容进行检测
* 提供自增和自减按钮
* 可以设置数字的范围
* 当输入数字超过这个范围的时候,禁止点击按钮

###### 组件代码

``` html
<template>
    <div>
        <input type="text" :value="currentValue" @input="handleChange" class="input-class"/>
        <rtp-button @click="handleDown" class="btn" :disabled="currentValue <= min">-</rtp-button>
        <rtp-button @click="handleUp" class="btn" :disabled="currentValue >= max">+<rtp-button>
</template>
<script>
export default{
    name : 'myInputNumber',
    props : {
        value :{
            type : Number,
            default : 0
        },
        max : {
            type : Number,
            default : Infinity
        },
        min : {
            type : Number,
            default : -Infinity
        }
    },
    data : {
        return {
            currentValue : this.value
        }
    },
    methods : {
        handleChange(event) {
            let val = event.target.value.trim()
            let max = this.max
            let min = this.min
            if (this.isNumber(val)) {
                val = Number(val)
                this.currentValue = val
                if (val > max) {
                    this.currentValue = max
                } else if(val < max) {
                    this.currentValue = min
                } else {
                    event.target.value = this.currentValue
                }
            } else {
                event.target.value = ""
            }
        },
        updateValue(val) {
            if (val > this.max) {
                val = this.max
            }
            if (val < this.min) {
                val = this.min
            }
            this.currentValue = val
        }
    },
    watch : {
        currentValue : function(val,oldVla) {
            this.$emit('input',val)
            this.$emit('my-change',val)
        },
        value : function(val,oldVal) {
            this.updateValue(val)
        }
    },
    mounted(){
        this.updateValue(this.value)
    }
}
```

###### 调用

``` html
<my-input-number v-model="test" :max="10" :min="-10"></my-input-number>
```

## 风格指南

### 组件名避免单个单词

### Prop定义应该尽量详细

* 需要指定类型
* 详细的信息不包括类型,默认值,是否必填等

### 为v-for设置键值

### 永远不要把v-if和v-for同时用在同一个元素上

``` html
<!-- 反例 -->
<li
    v-for = "user in users"
    v-if = "user.isActive"
    :key = "user.id"
>
    {{user.name}}
</li>

<!-- 好例子 -->
<li
    v-for = "user in activeUsers"
    :key = "user.id"
>
    {{user.name}}
</li>
```

### 为组件样式设置作用域

* 对于引用来说,顶级APP组件和布局组件中的样式可以是全局的,但是其他所有组件都应该有作用域
* scope方式,class策略,倾向后者

``` html
<!-- 反例 -->
<template>
    <button class = "btn btn-close">X</button>
</template>
<style>
    .btn-close {
        background-color:red;
    }
</style>
```

``` html
<!-- 好例子 -->
<template>
    <button class = "c-Button c-Button--close">X</button>
</template>
<style>
    .c-button{
        border:none;
        border-radius:2px;
    }
</style>
```

### 组件文件名的大小写,采用PascalCase

``` js
// MyComponent.vue
```

### DOM模板中的组件名大小写

``` html
<!-- 反例 -->
<MyComponet></MyComponent>

<!-- 好例子 -->
<my-component></my-component>
```

### 组件名中的单词顺序

* 组件名应以高级别的(通常是一般化描述)单词开头,以描述性的修饰词结尾

``` js
// 好例子
components/
|- SearchButtonClear.vue
|- SearchButtonRun.vue
```

### Prop变量名大小写

* 声明prop的时候,其命名应该始终使用camelCase,而在模板和JSX中引港始终用kebab-case

``` js
// 反例
props : {
    'greeting-text' : String
}

// 好例子
props : {
    greetingText : String
}
```

### 模板中的表达式

* 组件模板应该只包含简单的表达式,复杂的表达式则应该重构为计算属性或方法
* 模板的职责在于描述,复杂的实现交给方法或者计算属性,方法或者计算属性方便重用
* 应该把复杂计算属性分割为尽可能多的更简单的属性
* 表达式用双引号,完整地包起来,表达式内部如果需要表达字符串,可以使用单引号
