# JavaScript中的数组对象和方法
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Array类型恐怕是ECMAScript中最常用的类型了。而且，ECMAScript中的数组与其他多数语言中的数组有着很大的区别。虽然ECMAScript数组与其他语言中的数组都是数据的有序列表，但与其他语言不同的是，ECMAScript数组的每一项可以保存任何类型的数据。<br>
## 1 创建数组
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建数组的基本方式有两种，一种是使用Array构造函数，如下:<br>
```
var colors = new Array()
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;也可以像Array构造函数传递参数。如下:<br>
```
var arr1 = new Array(20);   //创建一个大小为20的空数组
var arr2 = new Array("red","blue");   //['red','blue']
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;此外，在使用Array构造函数时也可以省略new操作符。如下:<br>
```
var arr = Array('xiao');   //['xiao']
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建数组的第二种方法是使用数组字面量表示法，这也是最常用的一种方法,如下：<br>
```
var arr1 = ['red','yellow','green']
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 2 数组方法
### 2.1 检测数组
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自从ECMAScript3做出规定之后，就出现了确定某个对象是不是数组的经典问题。对于一个网页，或者以个全局作用域而言，使用instanceof操作符就能得到满意的结果:<br>
```
if(value instanceof Array){
  ...
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;instanceof操作符存在的问题在于，它假定只有一个全局执行环境。如果网页中包含多个框架，那么实际上就存在两个以上不同的全局执行环境，从而存在两个以上不同版本的Array构造函数。如果你从一个框架向另一个框架传入一个数组，那么传入的数组与在第二个框架中原生创建的数组分别具有各自不同的构造函数。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为了解决这个问题，ECMAScript5新增了Array.isArray()方法。这个方法的目的是最终确定某个值到底是不是数组，而不管它是在哪个全局执行环境中创建的。这个方法的用法如下。<br>
```
if(Array.isArray(value)){
   ...
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 2.2 转换方法
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Array的转换方法有toLocaleString(),toString()和valueOf()三种。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其中toLocaleString()和toString()方法会将数组转换成字符串:valueOf()则返回数组的原始值。如下:<br>
```
var a = ['1','2','3']
a.toString();   //'1','2','3'
a.toLocaleString();     //'1','2','3'
a.valueOf(); //Array{'1','2','3'}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其中，toLocaleString()和toString()在以下两种情况下会有区别。<br>
* 1 当要转换的值是大于4位的数字时
* 2 当要转换的值是标准时间时
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;具体区别如下<br>
```
var a = 12345;
a.toString();   //'12345'
a.toLocaleString();   //'12,345'
var b = new Date();
b.toString();     //Thu Nov 29 2018 19:15:39 GMT+0800 (中国标准时间)
b.toLocaleString();   //"2018/11/29 下午7:15:39"
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 2.3 栈方法
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ECMAScript数组也提供了一种让数组的行为类似于其他数据结构的方法。具体来说，数组可以表现的像栈一样，栈是一种后进先出的数据结构。可以使用push()和pop()方法实现:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如下:<br>
```
var stack = ['1','2','3','4']
stack.push('5');     //入栈 stack:['1','2','3','4','5']
var item = stack.pop();   //出栈 stack:['1','2','3','4'] item:‘5’
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 2.4 队列方法
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;队列是一种先进先出的数据结构，在ECMAScript中可以使用push()和shift()方法模拟,如下:<br>
```
var line = ['1','2','3','4']
line.push('5');     //入队 line:['1','2','3','4','5']
var a = line.shift();   //出队 line:['2','3','4','5']
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;与push(),shift()类似的组合有unshift(),pop()，不同的是unshift()在队首添加元素，pop()弹出队尾元素。而push()在队尾添加元素，shift()弹出队首元素<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 2.5 重排序方法
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数组中有两个重排序方法:sort()和reverse()<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其中sort()用于排序，而reverse()则用于反转数组。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在默认情况下,sort()方法按升序排列数组项-即最小的值位于最前面，最大的值排在最后面。为了实现排序，sort()方法会调用每个数组项的toString()转型方法，然后比较得到的字符串，以确定如何排序。即使数组中的每一项都是数值,sort()方法比较的也字符串，如下所示。<br>
```
var arr = [0,1,5,10,15]
arr.sort();     //arr:[0,1,10,15,5]
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可见,即使例子中值的顺序没有问题，但sort()方法也会根据测试字符串的结果改变原来的顺序。因为数值5小于10，但在进行字符串比较时，"10"则位于"5"的前面，于是数组的顺序就被修改了。不用说，这种排序方式在很多情况下都不是最佳方案。因此sort()方法可以接收一个比较函数作为参数，以便我们指定哪个值位于哪个值的前面。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;比较函数接收2个参数，如果第一个参数应位于第二个参数之前则返回一个负数，如果两个参数相等则返回0，如果第一个参数应该位于第二个之后则返回一个正数。以下就是一个简单的比较函数:<br>
```
function compare(value1,value2){
   if(value1&lt;value2){
      return -1;
   }else if(value1&gt;value2){
      return 1;
   }else{
      return 0;
   }
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这个比较函数可以适用于大多数数据类型，只要将其作为参数传递给sort()方法即可，如下面这个例子所示。<br>
```
var arr = [0,1,5,10,15]
arr.sort(compare);     //0,1,5,10,15
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 2.6 操作方法
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ECMAScript为操作已经包含在数组中的项提供了很多方法。其中,concat()方法可以基于当前数组中的所有项创建一个新数组。具体来说，这个方法会先创建当前数组一个副本，然后将接收到的参数添加到这个副本的末尾，最后返回新构建的数组。在没有给concat()方法传递参数的情况下，它只是复制当前数组并返回副本。如果传递给concat()方法的是一或多个数组，则该方法会将这些数组中的每一项都添加到结果数组中。如果传递的值不少数组，这些值就会被简单地添加到结果数组的末尾。如下所示:<br>
```
var a = ['1','2','3']
a.concat('4',['5,'6']);     //['1','2','3','4','5','6']
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
