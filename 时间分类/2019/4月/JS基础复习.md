# JS基础复习

只针对一些容易忘记的地方进行复习,

## 引用类型的方法

### Object类型

### Array类型

* 栈方法:push(),pop()
* 队列方法:unshift()，shift()
* 重排序:reverse(),sort()
* 操作方法:splice(),
* 位置方法:indexOf(),lastIndexOf()
* 迭代方法:every(),filter(),forEach(),map(),some()
* 归并方法:reduce(),reduceRight()

### Date类型

### RegExp类型

匹配模式

* g:全局模式
* i:不区分大小写
* m:表示多行

实例属性

* gloabl:
* ignoreCase:
* lastIndex:
* multiline:
* source:

实例方法

RegExp对象的主要方法是exec(),该方法是专门为捕获组二设计的。exec()接受一给参数,都要应用模式的字符串,然后返回包含第一给匹配项信息的数组，或者在没有匹配项的情况下返回null,返回的数组虽然是Array的实例,但包含两给额外的属性:index和input。

## 面向对象

### 创建对象

* 工厂模式
* 构造函数模式
* 原型模式
* 组合使用构造函数模式和原型模式
* 动态原型模式
* 寄生构造函数模式
* 稳妥构造函数模式

### 继承

* 借用构造函数
* 组合继承
* 原型式继承
* 寄生式继承
* 寄生组合式继承

## ES6

### 字符串扩展

* codePointAt()
* String.fromCodePoint()
* 字符串的遍历器接口
* at()
* normalize()
* includes()，startsWith(),endsWith()
* repeat()
* padStart(),padEnd()

### 正则的扩展

* RegExp构造函数
* 字符串的正则方法
* u修饰符
* y修饰符
* sticky属性
* flags属性
* s修饰符:dotAll模式

### 数值的扩展

* 二进制和八进制表示法
* Number.isFinite(),Number.isNaN()
* Number.parseInt(),Number.parseFloat()
* Number.isInteger()
* Number.EPSILON()

### 函数的扩展

* 函数参数的默认值
  * 基本用法
  * 与解构赋值默认值组合使用
  * 参数默认值的位置
  * 函数的length属性
  * 作用域
  * 应用
* rest参数
* 严格模式
* name属性
* 箭头函数
* 绑定this
* 尾调用优化

### 数组的扩展

* 扩展运算符
* Array.from()
* Array.of()
* 数组实例的copyWithin()
* find(),findIndex()
* fill()
* entries()，keys(),values()
* includes()

### 对象的扩展

* 属性的简洁表示法
* 属性名表达式
* 方法的name属性
* Object.is()
* Object.assign()
* Object.keys(),Object.values(),Object.entries()

### Symbol

实例:消除魔术字符串

### Set和Map数据结构

### Proxy

代理

### Reflect

### Promise

* Promise.prototype.then()
* Promise.prototype.catch()
* Promise.all()
* Promise.race()
* Promise.resolve()
* Promise.reject()
* done()
* finally()

### iterator和for..of循环

### Generator函数的语法

应用:

* 异步操作的同步化表达
* 控制流管理
* 部署iterator接口
* 作为数据结构

## VUE

### MVVM

### 数据绑定

### 生命周期

* 创建 -> 挂载 -> 更新 -> 销毁

### 计算属性

对原数据进行复杂的逻辑运算处理,然后返回

注意与methods的区别,methods只要重新渲染,它就会被调用,因此函数也会被执行，使用计算属性还是methods取决于是否需要缓存,当遍历大数组和做大量计算时,应当使用计算属性.

### 内置指令

* 基本指令:v-clock,v-once,v-if,v-show,v-for,
* 数组更新:pop(),push(),unshift(),shift(),splice(),sort(),reverse()
* 不会更新数组的操作:1,通过索引直接设置项,2,修改数组长度

### 方法与事件

* 绑定方法:v-on:click="method"
* 修饰符:.stop,.prevent,.capture,.self,.once

### 表单与v-model

* 绑定表单数据  input v-model="inputValue" 

### 组件详解

* 数据传递:父->子 静态数据使用props  动态数据使用v-bind  子->父：v-model,  非父子组件通信: $dispatch(),$broadcast() （vue2已废弃）

## 