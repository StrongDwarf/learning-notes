# ES6数组扩展

## 数组的扩展

数组操作一直是编程中使用的最多的操作，所以ES6中对于数组的扩展是最值得我们注意的

### 1.1 扩展运算符

扩展运算符是三个点(...),它如同rest参数的逆运算，将一个数组转为用逗号分隔的参数序列

``` javascript
console.log(...[1,2,3])     // 1 2 3
console.log(1,...[2,3,4],5) // 1 2 3 4 5
```

### 1.2 替代数组的apply方法

由于扩展运算符可以展开数组,所以不再需要使用apply方法将数组转化为函数的参数。如下

``` javascript
//ES5的写法
function f(x,y,z){
    //...
}

var args = [0,1,2]
f.apply(null,args)

//ES6的写法
function f(x,y,z){
    //...
}
f(...args)
```

### 1.3 扩展运算符的运用

#### 合并数组

``` javascript
//ES5
[1,2].concat(more)
//ES6
[1,2,...more]
```

#### 与结构赋值结合

``` javascript
//ES5
a = list[0]
b = list.slice(1)
//ES6
[a,...b] = list
```

#### 字符串

将字符串转为真正的数组

``` JavaScript
[...'hello']    //['h','e','l','l','o']
```

#### 实现了Iterator接口的对象

任何实现了Iterator接口的对象都可以用扩展运算符转为真正的数组

``` javascript
var nodeList = document.querySelectorAll('div')
var array = [...nodeList]
```

#### Map和Set结构,Generator函数

扩展运算符内部调用的是数据结构的Iterator接口,因此只要具有Iterator接口的对象，都可以使用扩展运算符，如Map结构.

``` javascript
let map = new Map([
    [1,'one'],
    [2,'two'],
    [3,'three']
])
let arr = [...map.keys()];      //[1,2,3]
```

Generator函数运行后会返回一个遍历器对象，因此也可以使用扩展运算符

``` javascript
var go = function*(){
    yield 1;
    yield 2;
    yield 3;
}
[...go()] //[1,2,3]
```

### 1.2 Array.from()

Array.from方法用于将两类对象转化为真正的数组:类似数组的对象和可遍历的对象

``` javascript
let arrayLike = {
    '0':'a',
    '1':'b',
    '2':'c',
    'length':'3'
}
//ES5的写法
[].slice.call(arrayLike)
//ES6的写法
Array.from(arrayLike)
```

实际运用中,常见的类似数组的对象是DOM操作返回的NodeLike集合,以及函数内部的arguments对象。Array.from用于将它们转为真正的数组。

类似数组的对象和真正的数组的对象的区别在于:类似数组的对象不是数组对象，故不能使用数组类型的方法。而所谓类似数组的对象,本质特征只有一点,即具有length属性。将类似数组的对象转为数组时候,将先读取length属性,获取数组的长度,然后根据长度声明数组相应下标，最后查找对象的相应下标的属性，并赋值。使用函数表示类似如下:

``` javascript
Array.prototype.from = function(arrayLike){
    let arr = new Array()
    if(arrayLike['length'] && arrayLike['length'] > 0){
        for(let i =0;i<arrayLike['length'];i++){
            if(arrayLike[i]){
                arr[i] = arrayLike[i]
            }else{
                arr[i] = undefined
            }
        }
    }
    return arr
}
```

### 1.3 Array.of()

Array.of方法用于将一组值转换为数组

``` javascript
Array.of(1,3,5)     //[1,3,5]
Array.of(3)         //[3]
```

这个方法主要用于弥补数组构造函数Array()的不足。因为参数个数的不同会导致Array()的行为有差异

``` javascript
Array()  //[]
Array(3)    //[,,,]
Array(1,3,5)    //[1,3,5]
```

### 1.4 数组实例的copyWithin()

数组实例的copyWithin方法会在当前数组内部将指定位置的成员复制到其他位置(会覆盖原有成员)，然后返回当前数组。也就是说，使用这个方法会修改当前数组。

``` javascript
Array.prototype.copyWithin(target,start = 0,end = this.length)
```

它接受3个参数

* target(必选):从当前位置开始替换数据
* start(可选):从该位置开始读取数据，默认为0,如果为负值，表示倒数
* end(可选):到该位置停止读取数据，默认等于数组长度。如果为负值，表示倒数

这3个参数都应该是数值,如果不是,会自动转为数值

``` javascript
[1,2,3,4,5].copyWithin(0,3)     //[4,5,3,4,5]
//将3号位复制到0号位
[1,2,3,4,5].copyWithin(0,3,4)   //[4,2,3,4,5]
```

### 1.5 数组实例的find()和findIndex()

数组实例的find方法用于查找第一个符合条件的值，该方法接受3个参数,分别是value,index,original
数组实例的findIndex方法用于查找第一个符合条件的值的下标，该方法同样接受3个参数,分别是value,index,origial

``` JavaScript
var arr = [1,2,3,4,5]
arr.find((value,index,arr) => {
    return value === 5
})
arr.findIndex((value,index,arr) => {
    return value === 5
})
```

### 1.6 数组实例的fill()

fill方法使用给定值填充一个数组,同时,接受第二个和第三个参数,用于指定填充的起始位置和结束位置

``` javascript
new Array(3).fill(7)      //[7,7,7]
new Array(5).fill(7,2,3)    //[,,7,,,]
```

### 1.7 数组实例的 keys(),values(),entries()

数组实例的keys(),values(),entries()方法和Object()对象上的keys(),values(),entries()类似

只不过区别在于一个返回的是Iterator对象,一个返回的是Array对象

如下:

``` javascript
let arr = ['a','b','c']
arr.entries     //Array Iterator{}
Object.entries(arr)     //[['0','a'],['1','b'],['2','c']]
```

两者使用情况也一样,区别只在于,使用arr.entries()后获得的index是number类型,而使用Object.entries()获得的index是String类型

``` javascript
for(let [i.j] of arr.entries()){
    console.log(i,j)
}
// 0 'a'
// 1 'b'
// 2 'c'
for(let [i,j] of Object.entries()){
    console.log(i,j)
}
// '0' 'a'
// '1' 'b'
// '2' 'c'
```

### 1.8 数组实例的includes()

Array.prototype.includes方法返回一个布尔值,用来表示某个数组是否包含给定的值,与字符串的includes方法类似。

以前,我们数组中是否包含给定的值时一般使用indexOf()方法,现在ES6中引入了includes方法可以简化一点,如下:

``` javascript
var arr = [1,2,3,4]
//判断数组中是否有2
//ES5
arr.indexOf(2) > -1
//ES6
arr.includes(2)
```

### 1.9 数组的空位

数组的空位是指数组的某一个位置没有任何值。比如,Array构造函数返回的数组都是空位。

``` javascript
Array(3) //[,,,]
```

值得注意的是:空位不是undefined，一个位置的值等于undefined依然是有值的。空位是指没有任何值的，in运算符可以说明这一点。

``` javascript
0 in new Array(3).fill(undefined)
0 in [,,,]
```

其Object形式的区别如下:

``` javascript
new Array(3).fill(undefined)
/**
 * {
 *  length:3,
 *  0:undefined,
 *  1:undefined,
 *  2:undefined
 * }
 */
[,,,]
/**
 * {
 *  length:3
 * }
 */
```

ES5对空位的处理很不一致，大多数情况下回忽略空位。

* forEach(),filter(),every(),some()都会跳过空位
* map()会跳过空位，但会保留这个值
* join()和toString()会将空位视为undefined,而undefined和null会被处理成空字符串

ES6则是明确将空位转为undefined,Array.from()方法就是把空位转化为undefined,copyWithin()会将空位一起复制,fill()会将空位视为正常位置，for...of循环也会遍历空位.

``` javascript
for(let i of [,,,]){
    console.log(i)
}

//undefined * 3
```
