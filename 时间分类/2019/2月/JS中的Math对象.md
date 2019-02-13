# JS中的Math对象

Math对象中封装了常用的数学方法,

 * [Math对象的属性](Math对象的属性)
 * [Math对象的方法](Math对象的方法)
 * [ES6中对Math对象的扩充](ES6中的对Math对象的扩充)

## Math对象的属性

Math对象中包含的属性大都是数学计算中可能会用到的一些特殊值，如下

``` javascript
Math.E  //自然对数的底数
Math.LN10  //10的自然对数
Math.LN2   //2的自然对数
Math.LOG2E   //以2为底e的对数
Math.LOG10E  //以10为底e的对数
Math.PI      //π的值
Math.SQRT1_2    //1/2的平方根
Math.SQRT2      //2的平方根
```

## Math对象的方法

### min()和max()方法

min()和max()方法分别用于确定一串数字中的最小值和最大值,使用方式如下

``` javascript
Math.min(2,3,4,6,5)   //2
```

若要确定一串数组中的最小值，可以如下使用

``` javascript
const arr = [2,3,4,6,5]
//ES6之前
Math.min.apply(Math,arr)
//ES6
Math.min(...arr)
```

### 舍入方法

Math对象中有3个舍入方法,如下

 * Math.floor():执行向下舍入,即它总是将数值向下舍入为最接近的整数
 * Math.ceil():执行向上舍入,即它总是将数值像上舍入为最接近的整数
 * Math.round():执行标准舍入,即四舍五入

``` javascript
Math.floor(2.9)  //2
Math.ceil(2.1)   //3
Math.round(2.49465657)  //2
```

### 其他方法

``` javascript
Math.random()       //返回介于0到1之间的随机数
Math.abs(num)       //返回num的绝对值
Math.exp(num)       //返回Math.E的num次幂
Math.log(num)       //返回num的自然对数
Math.pow(num,power) //返回num的power次幂
Math.sqrt(num)      //返回num的平方根
Math.acos(x)        //返回x的反余弦值
Math.asin(x)        //放回x的反正弦值
Math.atan(x)        //返回x的反正切值
Math.atan2(y,x)     //返回y/x的反正切值
Math.cos(x)         //返回x的余弦值
Math.sin(x)         //返回x的正弦值
Math.tan(x)         //返回x的正切值
```

## ES6中对Math对象的扩充

### 二进制和八进制表示法

ES6提供了二进制和八进制数值的新写法，分别用前缀0b和0o表示,

``` javascript
console.log(3 === 0b11)   //true
console.log(9 === 0o11)   //true
```

在ES5中,非严格模式下,八进制可以使用前缀0表示，而在非严格模式下则不能使用前缀0表示

``` javascript
function isEqual(){
    console.log(9 === 011)
}

function isEqualUseStrict(){
    'use strict'
    console.log(9 === 011)
}

isEqual()   //true
isEqual()   //Uncaught SyntaxError
            //Octal literals are not allowed in strict mode,
```

如果要讲二进制和八进制数字转为十进制使用,可以使用Number方法。

``` javascript
Number('0b111')  //7
```

### Number.isFinite(),Number.isNaN()

Number.isFinite()方法用于检测某个数字是否是有限的
Number.isNaN()用于检测某个值是否是NaN

### Number.parseInt(),Number.parseFloat()

ES6中讲全局方法parseInt(),parseFloat()移植到了对象Number上,行为保持不变。

### Number.isInterger()

Number.isInterger()用来判断一个数是否是整数,值得注意的是,JavaScript中整数和浮点数是同样的存储方法,所以3和3.0都是整数

``` javascript
Number.isInterger(3)    //true
Number.isInterger(3.0)  //true
Number.isInterger(3.1)  //false
``` 

### Number.EPSILON

ES6在Number对象上面新增了一个极小的常量:Number.EPSILON

``` javascript
Number.EPSILON      //2.220446049250313e-16
```

引入一个这么小的量，目的在于为浮点数计算设置一个误差范围。我们知道浮点数计算是不精确的，如下:

``` javascript
0.1 + 0.2           //0.30000000000000004
```

如果计算误差小于Number.EPSILON，我们则可以认为得到了正确的结果

``` javascript
0.1 + 0.2 - 0.3 < Number.EPSILON    //true
```

### 安全整数和Number.isSafeInteger()

JavaScript能够精确表示的整数范围在-2^53到2^53之间,超过这个范围就无法精确表示.
Number.isSafeInteger()用于判断一个数是否是安全数


### ES6中对Math对象的扩展

ES6中还新增了一些与数学相关的方法,不过用的比较少.这里不细说了

