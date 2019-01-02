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
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;下一个方法是slice(),它能够基于当前数组中的一或多个项创建一个新数组。slice()方法可以接受一或两个参数，即要返回项的起始和结束位置。在只有一个参数的情况下，slice()方法返回从参数指定位置开始到当前数组末尾的所有项。如果有两个参数，该方法返回起始和结束位置之间的项-但不包括结束位置的项。需要注意的是,slice()方法不会影响原始数组。如下:<br>
```
var arr = [1,2,3,4,5,6];
var arr2 = arr.slice(1,4);
console.log(arr);     //[1,2,3,4,5,6]
console.log(arr2);   //[2,3,4]
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;和slice类似的方法是splice()方法，这个方法恐怕要算是最强大的数组方法了，它有很多种用法。splice()的主要用途是向数组的中部插入项，但使用这种方法的方式则有如下3种。<br>
* 删除：可以删除任意数量的项，只需指定2个参数:要删除的第一项的位置和要删除的项数。例如,splice(0,2)会删除数组中的前两项。
* 插入：可以向指定位置插入任意数量的项，只需提供3个参数:起始位置，0(要删除的项数)和要插入的项。如果要插入多个项，可以再传入第四，第五，以至任意多个项。例如,splice(2,0,'red','green')会从当前数组的位置2开始插入字符串"red"和"green"。
* 替换：可以向指定位置插入任意数量的项，且同时删除任意数量的项，只需指定3个参数：起始位置，要删除的项数和要插入的任意数量的项。插入的项数不必与删除的项数相等。例如:splice(2,1,'red','green')会删除当前数组位置2的项，然后在从位置2开始插入字符串"red"和"green"。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;splice()方法始终都会返回一个数组，该数组中包含从原始数组中删除的项(如果没有删除任何则返回一个空数组)。如下:<br>
```
var arr = [1,2,3,4]
var a = arr.splice(2,0);      //a:[]
var b = arr.splice(2,1);      //b:[3] arr:[1,2,4]
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值得注意的是splice()的操作是在原数组上进行的，而slice()是在数组的副本上进行的。<br>
### 2.7 位置方法
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ECMAScript 5为数组实例添加了两个位置方法:indexOf()和lastIndexOf()。这两个方法都接收两个参数:要查找的项和(可选的)表示查找起点位置的索引。其中,indexOf()方法从数组的开头开始向后查找，lastIndexOf()方法则从数组的末尾开始向前查找。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这两个方法都返回要查找的项在数组中的位置，或者在没找到的情况下返回-1。在比较第一个参数与数组中每一项时，会使用全等操作符;也就是说，要求查找的项必须严格相等(就像使用===一样):如下:<br>
```
var arr = [1,2,3,4,5,4,3,2,1]
numbers.indexOf(4);     //3
numbers.lastIndexOf(4);     //5
```
### 5.8 迭代方法
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ECMAScript5为数组定义了5个迭代方法。每个方法都接收两个参数:要在每一项上运行的函数和(可选的)运行该函数的作用域对象-影响this的值传入这些方法中的函数会接收三个参数:数组项的值，该项在数组中的位置和数组对象本身。根据使用的方法不同，这个函数执行后的返回值也可能不会影响方法的返回值。以下是这5个迭代方法的作用。<br>
* every()：对数组中的每一项运行给定函数，如果该函数对每一项都返回true，则返回true。
* filter()：对数组中的每一项运行给定函数，返回该函数会返回true的项组成的成员
* forEach()：对数组中的每一项运行给定函数。这个方法没有返回值。
* map()：对数组中的每一项运行给定函数，返回每次函数调用的结果组成的数组。
* some()：对数组中的每一项运行给定函数，如果该函数对任一项返回true，则返回true。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;值得注意的是上面的所有函数都是在原数组的副本上执行的操作。<br>
### 2.9 归并方法
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ECMAScript 5还新增了两个归并数组的方法:reduce()和reduceRight()。这两个方法都会迭代数组的所有项，然后构建一个最终返回的值。其中,reduce()方法从数组的第一项开始，逐个遍历到最后。而reduceRight()则从数组的最后一项开始，向前遍历到第一项。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这两个方法都接收两个参数:一个在每一项上调用的函数和(可选的)最为归并基础的初始值。传给reduce()和reduceRight()的函数接收4个参数:前一个值，当前值，项的索引和数组对象。这个函数返回的任何值都会作为第一个参数自动传给下一项。第一次迭代发生在数组的第二项上，因此第一个参数是数组的第一项，第二个参数就是数组的第二项。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用reduce()方法可以执行求数组中所有值之和的操作，比如:<br>
```
var values = [1,2,3,4,5]
var sum = values.reduce(function(prev,cur,index,array){
   return prev + cur;
});
console.log(sum);   //15
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>

