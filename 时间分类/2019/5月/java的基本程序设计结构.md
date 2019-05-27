# java的基本程序设计结构

## 1，一个简单的java应用程序

``` java
public class FirstSaple{
    public static void main(String[] args)
    {
        System.out.println("hello world")
    }
}
```

## 3，注释

与大多数程序设计语言一样,java中的注释也不会出现在可执行程序中。因此,可以在源程序中根据需要添加任意多的注释，而不必担心可执行代码会膨胀。

单行注释使用“//”

多行注释使用"/* */"

## 3，数据类型

java是一种强类型语言。这就意味着必须为每一个变量声明一种类型。在java中，一共有8中基本类型，其中有4种整形，2种浮点类型，1种用于表示Unicode编码的字符单元的字符类型char和一种用于表示真值的boolean类型。

### 3.1，整形

整形用于表示没有小数部分的数值，它允许是负数。java提供了4种整形，具体内容如下:

int:4字节, short:2字节  long:8字节 byte:1字节

``` js
C++注释:在C和C++中,int和long等类型的大小与目标平台相关。在8086这样的16位处理器上整形数值占2字节;不过,在32为处理器上,整形数值则为4字节。类似的,在32位处理器上long值为4字节，在64位处理器上则为8字节。由于存在这些差别，这对编写跨平台程序带来了很大难度。在java中，所有的数值类型所占据的字节数量与平台无关
```

同样,java中没有任务无符号形式的int,long,short或byte类型

### 3.2，浮点类型

浮点类型用于表示有小数部分的数值。在java中有两种浮点类型，分别是float和double。其中float 4字节,  double 8字节。

float类型的数值有一个后缀F或f.没有后缀f的浮点数值默认为double类型。当然，也可以在浮点数值后面添加后缀D或d

``` js
注意:浮点数值不适用于无法接受舍入误差的金融计算中。例如,命令System.out.println(2.0-1.1)将打印出0.89999...999，而不是人们想象中的0.9,这种舍入误差的主要原因是浮点数值采用二进制系统表示，而在二进制系统中无法精确地表示分数1/10.如果在数值计算中不允许有任何舍入误差，就应该使用BigDecimal类。
```

### 3.3,char类型

char类型原本用来表示单个字符。不过，现在情况已经有所改变。如今，有些Unicode字符可以用一个char值描述，另外一些Unicode字符则需要两个char值描述。

char类型的字面量值要用单引号括起来。例如'A'是编码值为65所对应的字符常量

``` js
注意:unicode转义序列会在解析代码之前得到处理。例如,编码为\u0022的unicode字符表示双引号，在源代码中的"\u0022+\u0022"并不回被解析成\u0022+\20022字符串。而是会被解析成""+""，最后得到一个空字符串
```

### 3.4,Unicode和char类型

### 3.5,boolean类型

boolean(布尔)类型有两个值:false和true，用来判定逻辑条件。整形值和布尔值之间不能进行相互转换。

``` js
注意:在C++中,数值甚至指针可以代替boolean值。值0相当于布尔值false，非0值相当于布尔值true。在java中则不是这样。如当程序为
    if(x = 0){ }
    这在C++中能继续执行，并且结果总是false，而在java中,则不能通过编译,因为整数表达式x=0不能转换为布尔值
```

## 4,变量

在java中,每个变量都有一个类型(type)。在声明变量时，变量的类型位于变量名之前。这里列举一些申明变量的示例:

``` java
double salary;
int count;
```

### 4.1,变量初始化

声明一个变量之后,必须用赋值语句对变量进行显示初始化，千万不要使用未初始化的变量。

在java中，变量的声明尽可能地靠近变量第一次使用的地方，这是一种良好的程序编写风格

``` js
注意:C和C++区分变量的声明与定义。例如:
    int i = 10; 是一个定义,而 extern int i 是一个声明。
    在java中,不区分变量的声明与定义。
```

C++中声明和定义的区别如下:

* 变量定义:用于为变量分配存储空间，还可为变量指定初始值。
* 变量声明:用于向程序表明变量的类型和名字
* 定义也是声明:当定义变量时我们声明了它的类型和名字
* extern关键字:通过使用extern关键字声明变量名而不定义它,extern告诉编译器变量在其他地方定义了

### 4.2,常量

在java中,利用关键字final指示常量。例如:

``` java
public class Constants
{
    public static vid main(String[] args)
    {
        final String AUTHOR_NAME = "xiaobaicai";
    }
}
```

定义类常量如下:

``` java
public class Constant2{
    public static final String AUTHOR_NAME = "xiaobaicai";

    public static void main(String[] args){

    }
}
```

## 5,运算符

在java中,使用算术运算符+,-,*,/表示加，减，乘，除运算。当参与/运算的两个操作数都是整数时,表示整数除法；否则,表示浮点除法。整数的求余操作用%表示。例如,15/2等于7。15.0/2等于7.5

### 5.1,数学函数与常量

### 5.2,数值类型之间的转换

当使用两个数值进行二元操作时,先要将两个操作数转换为同一种类型，然后再进行计算。

* 如果两个操作数中有一个是double类型,另一个操作数就会转换为double类型
* 否则,如果其中一个操作数是float类型，另一个操作数将会转换为float类型。
* 否则,如果其中一个操作数是long类型,另一个操作数将会转换为long类型
* 否则,两个操作数都将被转换为int类型

### 5.3,强制类型转换

强制类型的语法如下:

``` java
double x = 9.997;
int nx = (int) x;   //nx = 9
```

强制类型转换通过截断小数部分将浮点值转换为整形

``` js
警告:如果试图将一个数值从一种类型强制转换为另一种类型，而又超出了目标类型的表示范围，结果就会截断成一个完全不同的值。例如,(byte) 300的实际值为44
```

### 5.4,结合赋值和运算符

可以在赋值中使用二元运算符,这是一种很方便的简写形式。例如:

``` java
x += 4;  //等同于 x = x+4
```

``` js
注意:如果运算符得到一个值,其类型与左侧操作数的类型不同,就会发生强制类型转换。例如,如果x是一个int,则以下语句
x += 3.5; 是合法的,将把x设置为 (int)(x + 3.5)
```

### 5.5,自增与自减运算符

略

### 5.6,关系与boolean运算符

java沿用了c++的做法,使用&&表示逻辑 "与" 运算符,使用||表示逻辑 "或" 运算符。且两种运算符都是按照"短路"方式来求值的

### 5.7,位运算符

位运算符包括 &("and") |("or") ^("xor") ~("not")

### 5.8，括号与运算符优先级

略

### 5.9，枚举类型

有时候,变量的取值只在一个有限的集合内。例如,销售的服装只有小,中,大和超大这四种尺寸。当然,可以将这些尺分别编码为1,2,3,4或S,M,L,X。但这样存在着一定的隐患。在变量中很可能保存的是一个错误的值(如0或m)

针对这种情况,可以自定义枚举类型。枚举类型包括有限个命名的值。例如:

``` java
enum Size{SMALL,MEDIUM,LARGE,EXTRA_LARGE}
```

现在可以声明这种类型的变量

``` java
Size s = Size.MEDIUM;
```

Size类型的变量只能存储这个类型声明中给定的某个枚举值，或者null值，null表示这个变量没有设置任何值。

## 6,字符串

从概念上讲,java字符串就是Unicode字符序列。例如,串"java\u2122" 由5个Unicode字符j,a,v,a和TM。java没有内置的字符串类型，而是在标准java类库中提供了一个预定义类，很自然的叫做String。声明String如下:

``` java
String e = "";  //空字符串
String greeting = "hello";
```

### 6.1，子串

可以使用subString方法从一个较大的字符串中提取出一个子串。例如:

``` java
String greeting = "Hello";
String s = greeting.subString(0,3);
```

### 6.2，拼接

与绝大多数的程序设计语言一样，java语言允许使用+进行字符串拼接。

``` java
String str = "hello "+"world"
```

### 6.3,不可变字符串

String类没有提供用于直接修改字符串的方法。如果希望修改字符串,可以先提取后拼接。如下所示:

``` java
String str = "hello"
String str1 = str.subString(0,3) + 'p!'
```

C++注释:在C程序员第一次接触java字符串的时候,常常会感到迷惑，因为它们中讲字符串认为是字符型数组:

``` java
char greeting[] = "hello"
```

这种认识是错误的,java字符串大致类似于 char* 指针

``` java
char* greeting = "Hello"
```

当采用另一个字符串替换greeting的时候，java代码大致进行下列操作

``` java
char* temp = malloc(6);
strncpy(temp,greeting,3);
strcpy(temp+3,"p!",3);
greeting = temp;
```

### 6.4,检测字符串是否相等

可以使用equals方法检测两个字符串是否相等。对于表达式:

``` java
s.equals(t)
```

如果要不区分大小写,可以使用equalsIgnoreCase方法。

``` java
'Hello'.equalsIgnoreCase("hello")
```

值得注意的是一定不要使用 == 运算符检测两个字符串是否相等!这个运算符只能确定两个字符串是否放置在同一个位置上。当然,如果字符串放置在同一个位置上，它们必然相等。但是，完全有可能将内容相同的多个字符串的拷贝放置在不同的位置上。

如果虚拟机始终将相同的字符串共享，就可以使用 == 运算符检测是否相等。但实际上只有字符串常量是共享的，而+或subString等操作产生的结果并不是共享的。因此，千万不要使用 == 运算符测试字符串的相等性。

### 6.5，空串与Null串

空串""是长度为0的字符串。可以调用以下代码检查一个字符串是否为空。

``` java
if(str.length() == 0)
//或
if(str.equals(""))
```

要检查一个字符串是否为null,可以使用以下方法

``` java
if(str == null)
```

### 6.6,码点与代码单元

获取字符长度

``` java
String str = "hello";
int n = str.length();   //5
```

获取码点长度

``` java
int cpCount = str.codePointCount(0,str.length)
```

获取第i个码点

``` java
int index = greeting.offsetByCodePoints(0,i);
int cp = greeting.codePointAt(index)
```

遍历字符串,依次查看每一个码点

``` java
int cp = sentence.codePointAt(i);
if(Character.isSupplementaryCodePoint(cp)) i+=2;
else i++;
```

### 6.7,String API

