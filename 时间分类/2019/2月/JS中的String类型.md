# JS中的String类型

## 创建String类型对象

String类型是字符串的对象包装类型,可以像下面这样使用String构造函数来创建。

``` javascript
var str = new String('hello,world')
```
或者直接赋值

``` javascript
var str = 'hello,world'
```

## String类型中的方法

String类型提供了很多方法,用于辅助完成对ECMAScript中字符串的解析和操作

### 1，字符方法

两个用于访问字符串中制定字符的方法分别是charAt()和charCodeAt()。这两个方法接收一个参数,即基于0的字符位置。其中,chrAt()放回字符,charCodeAt()返回字符串编码。

``` javascript
var str = 'hello world!'
str.charAt(2)       //'l'
str.charCodeAt(2)   //108
```

### 2,字符串操作方法

字符串相关的操作方法主要有拼接方法和切割方法

#### 2.1 字符串拼接方法

字符串可以使用concat()方法来拼接，不过实际使用中,一般直接使用"+"来实现字符串拼接，或者在字符串较多的时候使用模板。concat()方法使用较多的场景是在Array对象中使用。

``` javascript
var str = 'hello '
str.concat('world')  //'hello world'
var arr1 = [1,2,3,4]
var arr2 = [5,6]
var arr3 = arr1.concat(arr2)  //arr3:[1,2,3,4,5,6]
```

#### 2.2 字符串切割方法

字符串切割方法有slice(start,[end]),substr(start,[length]),substring(start,[end])3个方法
三个函数的基本用法如下,

``` javascript
var str = 'hello world!'
//当对slice()只赋值一个参数时，返回该位置到字符串结尾的字符串
str.slice(1)    //'ello world'
str.slice(1,2)  //'e'
str.substr(1,2)  //'el'
str.substring(1,2) //'e'
```

3者的区别在于：

 * substr()函数第二个参数是返回字符串的长度,而其余两个函数第二个参数都是子字符串结尾字符串的下标(不包括该下标字符)
 * slice()方法是基于迭代的,不仅仅可用于String对象,还可用于其他迭代器类型的值，如Array
 * 传入负数时的处理不同,对于slice()，第二个参数可以是负数,当传入负数num时,相当于传入,str.length+num。而对于substring()，当传入负数时候,默认负数为0,
 * 传入两个参数时，大小判断方法不同,对于slice()函数,严格遵守第一个数是start字符的下标，而第二个数是end字符的下标。当start大于end时,函数将返回空字符串。而对于substring()函数,传入介于两个值之间的字符串。具体如下

``` javascript
var str = 'hello world!'
str.slice(4,2)    //''
str.substring(4,2)  //'ll'
```

### 2.3 字符串位置方法

要获取字符串中指定字符的位置,可以使用indexOf()和lastIndexOf()方法,两个方法都用于返回指定字符的下标，区别在于indexOf()从前往后搜索,而lastIndexOf()从后往前搜索。

``` javascript
var str = 'hello world!'
str.indexOf('o')   //4
str.lastIndexOf('o')  //7
```

### 2.4 trim方法

trim()方法用于删除字符串前置和后缀的所有空格。
要单独删除字符串前置或后缀的空格,可以使用:trimLeft(),trimRight(),trimStart(),trimEnd()函数

``` javascript
var str = ' hello '
str.trim()  //'hello'
str.trimLeft() //'hello '
str.trimRight()  //' hello'
str.trimStart()  //'hello '
str.trimEnd()  //' hello'
```

### 2.5 字符串的大小写转换方法

JS中字符串的大小写转换方法有toUpperCase(),toLowerCase(),toLocaleUpperCase(),toLocaleLowerCase()方法。
其中,toLocaleUpperCase()和toLacaleLowerCase()方法是针对特定地区的实现。一般用不到。

``` javascript
var str = 'Hello World'
str.toUpperCase()   //'HELLO WORLD'
str.toLowerCase()   //'hello world'
```

### 2.6 字符串的模式匹配方法

字符串的模式匹配方法有match(),search(),replace(),split()

#### match()

match()返回一个匹配指定pattern的数组

``` JavaScript
var str = 'hello world'
str.match('hello')  //['hello']
str.match(/o/g)  //['o','o']
```

#### search()

search()和indexOf方法类型,返回匹配指定pattern的下标

``` javascript
var str = 'hello world'
str.search('ello')  //1
```

#### replace()

replace(pattern,newStr)用于将匹配指定pattern的字符串用newStr替换

``` javascript
var str = 'hello world'
str.replace('he','eh')   //'ehllo world'
```

同时,replace()函数,第二个参数还可以是一个函数,当匹配成功时会向该函数传递3个参数:match,pos,originalText。

``` javascript
function htmlEscapeI(text){
    return text.replaceo(/[<>"&]/g,function(match,pos,originalText){
        switch(match){
            case "<":
                return "&lt;";
            case ">":
                return "&gt;";
            case "&":
                return "&amp;";
            case "\"":
                return "&quot;";
        }
    })
}
```

#### split()

split()方法可以基于指定的分隔符将字符串分割成多个子字符串，并将结果放在一个数组中。分割符可以是字符串，也可以是一个RegExp对象。split()可以接受可选的第二个参数，用于指定数组的大小，以确保返回的数组不会超过即定大小

``` javascript
var colorText = "red,blue,green,yellow"
colors1 = colorText.split(",")  //["red","blue","green","yellow"]
colors2 = colorText.split(",",2)  //["red","blue"]
```

### 2.7 localeCompare()方法

与操作符有关的最后一个方法是localeCompare()，这个方法比较字符串的大小，并返回下列值中的一个:

 * 如果字符串在字母表中应该排在字符串参数之前，则返回一个负数(大多数情况下是-1，具体的值要视实现而定)
 * 如果字符串等于字符串参数，则返回0
 * 如果字符串在字母表中应该排在字符串参数之后，则返回一个正数(大多数情况下是1，具体的值视实现而定)

``` javascript
var val = "yellow"
val.localCompare("red")  //1
val.localCompare("yellow")  //0
```

### 2.8 fromCharCode()方法

fromCharCode()方法和charAtCode()作用相反。
fromCharCode()方法接收一或多个字符编码，然后将它们转换成一个字符串。

``` javascript
String.fromCharCode(104,101,108,108,111);  //"hello"
```

## 3 ES6中对String类型的扩展

### 3.1 codePointAt()

javascript内部,字符串以UTF-16的格式存储,每个字节固定为2个字节。对于那些需要4个字节存储的字符，JavaScript会认为它们是2个字符。
如使用charAt()和charCodeAt()方法只能返回前2个字节和后2个字节的值
而使用codePointAt()则正正确读取出四个字节存储的字符

### 3.2 String.fromCodePoint()

和codePointAt()对应的字符生成函数是String.fromCodePoint()，可以输入1或多个编码大于0XFFFF的码点，并返回对应字符或字符串

### 3.3 字符串的遍历器接口

ES6中新提供了字符串的便利器接口，可以使用访问遍历器的方式访问字符串。

``` javascript
var str = "hello world"
for(let char of str){
    console.log(char)    //依次输出 "h","e","l","l","o"," ","w","o","r","l","d"
}
```

### 3.3 at()

ES6中新增了at()方法，该方法和charAt()方法类似,不过charAt()方法不发识别码点大于0XFFFF的字符。而charAt()则可以识别

### 3.4 includes(),startsWith(),endsWith()

ES6又提供了3种新方法用于判断一个字符串是否包含在另一个字符串中。
 * includes():返回布尔值,表示是否找到了参数字符串
 * startsWith():返回布尔值，表示参数字符串是否在源字符串的头部
 * endsWith():返回布尔值，表示参数字符串是否在源字符串的尾部

``` javascript
var str = "hello world"
var str1 = "hel"
str.includes(str1)   //true
```

### 3.5 repeat()

repeat()返回一个新字符串，表示将原字符串重复n次

``` javascript
var str = "a"
str.repeat(3)  //"aaa"
//如果接收小数，会被Math.floor()取整
str.repeat(2.9)  //"aa"
```

### 3.6 padStart(),padEnd()

ES6中引入了字符串补全长度的功能。如果某个字符串不够指定长度，会在头部或尾部补全。

``` javascript
'x'.padStart(4,'a')     //'aaax'
'x'.padEnd(5,'a')       //'xaaaa'
```

### 3.7 模板字符串



