# ES6中Set和Map

Set和Map数据结构其实在其他语言中早已是常用的数据结构了,不过JavaScript知道ES6也引入了这两种数据结构。
Set又称集合,其和数组类似,不过只能存储互异的值。
Map又称字典,其和对象类似,不过"键"的范围不限于字符串，各种类型的值(包括对象)都可以当做键

## Set

### 1.1 基本用法

创建一个Set

``` javascript
const set = new Set([1,2,3,4,5,NaN,NaN,6,5])
[...set]    //[1,2,3,4,5,NaN,6]
```

向Set中添加一个元素

``` javascript
set.add(5)
```

Set构造函数可以接受数组作为参数，也可以接受类似数组的对象作为参数。
Set判断两个值是否互异时使用的算法类似于"==="，主要区别是Set认为NaN等于NaN,而"==="认为NaN不等于NaN。

### 1.2 Set实例的属性和方法

Set结构的实例有以下属性

* Set.prototype.constructor:构造函数,默认就是Set函数
* Set.prototype.size:返回Set实例的成员总数

Set实例的方法分为两大类:操作方法(用于操作数据)和遍历方法(用于遍历成员)，操作方法如下:

* add(value):添加某个值，返回Set结构本身
* delete(value):删除某个值,返回一个值，表示删除是否成功
* has(value):返回一个布尔值,表示参数是否为Set的成员
* clear():清楚所有成员,没有返回值

### 1.3 遍历操作

Set结构的实例有4个遍历方法，可用于遍历成员

* keys():返回键名的遍历器
* values():返回键值的遍历器
* entries():返回键值对的遍历器
* forEach():使用回调函数遍历每个成员

由于Set结构中没有键名，只有键值，故4个遍历方法与在Array对象上时候时的情况有点不同。如下:

``` javascript
const set = new Set([2,3,4,4])

for(let i of set.keys()){
    console.log(i)
}
//2
//3
//4

for(let i of set.values()){
    console.log(i)
}
//2
//3
//4

for(let [i,j] of set.entries()){
    console.log(i,j)
}
//2 2
//3 3
//4 4

set.forEach((value,original) => {
    console.log(value)
})
```

### 操作Set

对于Set上的操作,由于Set对象上的方法较少，操作Set结构时一般将其转化为Array进行操作，操作结束后再转为Set,如下

``` javascript
//过滤小于4的数字
let set = new Set([1,2,3,4,5,6])
set = new Set(Array.from(set).filter((value,index,arr) => {
    if(value<4){
        return false
    }
    return true
}))
```

## 2 WeakSet

WeakSet结构与Set类似，也是不重复的值的集合。但是，它与Set有三个区别。

第一:WeakSet的成员只能是对象，而不能是其他类型的值。

第二:WeakSet中的对象都是弱引用,即在垃圾回收机制中不考虑WeakSet对对象的引用

第三:WeakSet没有遍历方法,只有has(),add(),delete()方法

关于WeakSet,google了半天,没发现有什么用，所以不细说。发现有用的时候再来补这篇文章，

## 3 Map

Map和对象类型,属于值键对的集合，和对象的区别在于，对象的键只能是字符串，而Map的键不限于字符串,各种类型的值都可以作为键。Map是一个键互异的数据结构。

``` javascript
//创建Map
let map1 = new Map()
let map2 = new Map([
    ['a','b'],
    [1,'c']
])      //Map{'a'=>'b',1=>'c'}
```

Map上的方法和Set的方法类似.
操作方法有:

* set(key,value):添加一个键值对，如果键名已存在，则该键对应的原来的值改为现在的值
* get(key):获得键对应的值
* delete(key):删除键对应的值键对
* clear():情况Map
* has(key):返回一个布尔值,表示某个键是否在Map数据结构中

遍历方法有:

* forEach():
* values()
* keys()
* entries()

## 4 WeakMap

和WeakSet类似，也没发现有什么用。等以后发现有用的时候再来补充