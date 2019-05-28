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

* char charAt(int index):返回给定位置的代码单元。除非对底层的代码单元感兴趣，否则不需要调用这个方法
* int codePointAt(int index):返回从给定位置开始的码点
* int offsetByCodePoints(int startIndex,int cpCount):返回从startIndex代码点开始,位移cpCount后的码点索引
* int compareTo(String other):按照字典顺序,如果字符串位于other之前,返回一个负数;如果字符串位于other之后，返回一个正数；如果两个字符串相等，返回0。
* intStream codePoints():将这个字符串的码点作为一个流返回。调用toArray()将它们放在一个数组中
* new String(int[] codePoints,int offset,int count):用数组中offset开始的count个码点构造一个字符串。
* boolean equals(Objeect other):如果字符串与other相等，返回true。
* boolean reualsIgnoreCase(String other):如果字符串与other相等(忽略大小写)，返回true
* boolean startsWith(String prefix):如果字符串以suffix开头,则返回true
* boolean endsWith(String prefix):如果字符串以suffix结尾，则返回true
* int indexOf(String str[,int fromIndex]):返回与字符串str匹配的第一个子串的开始位置。这个位置从索引0或fromIndex开始计算。如果在原始串中不存在str，返回-1.
* int indexOf(int cp[,int fromIndex]):返回与代码点cp匹配的第一个子串的开始位置。这个位置从索引0或fromIndex开始计算。
* int lastIndexOf(String str[,int fromIndex]):返回与字符串str匹配的最后一个子串的开始位置。这个位置从字符串尾端或fromIndex开始计算。
* int lastIndexOf(int cp[,int fromIndex]):返回与代码点cp匹配的最后一个子串的开始位置。这个位置从字符串尾端或fromIndex开始计算
* int length():返回字符串的长度
* int codePointCount(int startIndex,int endIndex):返回startIndex和endIndex-1之间的代码点数量。没有配成对的代用字符将计入代码点
* String replace(charSequence oldString,CharSequence newString):返回一个新字符串。这个字符串用newString代替原始字符串中所有的oldString。可以用String或StringBuilder对象作为CharSequence参数
* String subString(int beginIndex[,int endIndex]):返回一个新字符串。这个字符串包含原始字符串从beginIndex到串尾或endIndex-1的所有代码单元
* String toLowerCase():返回一个新字符串,这个字符串将原始字符串中的大写字母转为小写字母
* String toUpperCase():返回一个新字符串,这个字符串将原始字符串中的小写字符转为大写字母
* String trim():返回一个新字符串,这个字符串将删除了原始字符串头部和尾部的空格
* String join(ChaeSequence delimiter,CharSequence... elements):返回一个新字符串,用给定的定界符连接所有元素

### 6.8,阅读联机文档

联机文档地址: https://docs.oracle.com/javase/8/docs/api/

### 6.9,构建字符串

有些时候,需要由较短的字符串构建字符串，例如,按键或来自文件中的单次。采用字符串连接的方式达到此目的效率比较低。每次连接字符串，都会构建一个新的String对象，既耗时，又浪费空间。使用StringBuilder类就可以避免这个问题的发生。使用StringBuilder如下:

``` java
StringBuilder builder = new StringBuilder();        //创建StringBulider对象
builder.append('h');            //添加单个字符
builder.append('ello');         //添加字符串
String completedString = builder.toString();        //获取构建器中的字符序列
```

下面是一些StringBulider类中的重要方法:

* StringBuilder():构造一个空的字符串构建器
* int length():返回构建器或缓冲器中的代码单元数量
* StringBuilder append(String str):追加一个字符串并返回this
* StringBuilder append(char c):追加一个代码单元并返回this
* StringBuilder appendCodePoint(int cp):追加一个代码点,并将其转换为一个或两个代码单元并返回this
* void setCharAt(int i,char c):将第i个代码单元设置为c
* StringBuilder insert(int offset,String str):在offset位置插入一个字符串病返回this
* StringBuilder insert(int offset,Char c):在offset位置插入一个代码单元并返回this
* StringBuilder delete(int startIndex,int endIndex):删除偏移量从startIndex到-endIndex-1的代码单元并返回this。
* String toString():返回一个与构建器或缓冲器内容相同的字符串

## 7，输入输出

### 7.1，读取输入

使用标准输入流(即从控制台窗口输入)

``` java
Scanner in = new Scanner(System.in);
System.out.pringln("what is your name?");
String name = in.nextLine();
```

Scanner相关方法：

* Scanner(InputStream in):用给定的输入流创建一个Scanner对象
* String nextLine():读取输入的下一行内容
* String next():读取输入的下一个单次(以空格作为分隔符)
* int nextInt()  double nextDouble():读取并转换下一个表示整数或浮点数的字符序列
* boolean hasNext():检测输入中是否还有其他单词
* boolean hasNextInt():
* boolean hasNextDouble():检测是否还有表示整数或浮点数的下一个字符序列

### 7.2,格式化输出

可以使用System.out.print(x)将数值x输出到控制台上。这条命令将以x对应的数据类型所允许的最大非0数字位数打印输出x。例如:

``` java
double x = 10000.0/3.0;
System.out.print(x);        //3333.333333333335
```

用于printf的转换符:

* d:十进制整数,
* x:十六进制整数
* o:八进制整数
* f:定点浮点数
* e:指数浮点数
* g:通用浮点数
* a:十六进制浮点数
* s:字符串
* c:字符
* b:布尔
* h:散列码
* tx或Tx:日期时间

### 7.3:文件输入和输出

要想对文件进行读取,就需要一个用File对象构造一个Scanner对象,如下所示:

``` java
Scanner in = new Scanner(Paths.get("myfile.txt"),"UTF-8")
```

如果文件名中包含反斜杠符号,就要记住在每个反斜杠之前再加以个额外的反斜杠: "c:\\mydirectory\\myfile.txt"

如果要写入文件,就需要构造一个PrintWriter对象。在构造器中，只需要提供文件名:

``` java
PrintWriter out = new PrintWriter("myFile.txt","UTF-8");
```

如果文件不存在,创建该文件。可以像输出到System.out一样使用print,println以及printf命令

值得注意的是,可以构造一个带有字符串参数的Scanner,但这个Scanner将字符串解释为数据，而不是文件名。例如，如果调用:

``` java
Scanner in = new Scanner("myfile.txt"); //Error?
```

当指定一个相对文件名时,例如,"myfile.txt","mydirectory/myfile.txt" 或 "../myfile.txt",文件位于java虚拟机启动路径的相对位置。如果在命令杭霞用如下命令启动程序:

``` shell
java MyProp
```

启动路径就是命令解释器的当前路径。然而,如果使用集成开发环境，那么启动路径将由IDE控制。可以使用下面的调用方式找到路径的位置:

``` java
String dir = System.getProperty("user.dir")
```

## 8,控制流程

与任何程序设计语言一样,java使用条件语句和循环结构确定控制流程。

### 8.1，块作用域

在java中,不能在嵌套的块作用域中声明两个同名的变量,但能在两个不嵌套的块作用域中声明两个同名的变量。如下:

``` java
//嵌套,编译不会通过
public static void main(String[] args){
    int n;
    {
        int k ;
        int n;
    }
}
//不嵌套,编译会通过
public static void main(String[] args){
    {
        int n;
    }
    {
        int n;
    }
}
```

### 8.2，条件语句

即: if...else语句

### 8.3,循环

while(condition) statement

do{statement} while(condition)

### 8.4,确定循环

for循环

### 8.5,多重选择:switch语句

### 8.6，中断控制流程语句

break，continue

### 9，大数值

如果基本的整数和浮点数精度不能满足需求,那么可以使用java.math包中的量个很有用的类:BigInteger和BigDecimal。

使用静态的valueOf方法可以将普通的数值转换为大数值

``` java
BigInteger a = BigInteger.valueOf(100);
```

遗憾的是,不能使用熟悉的算术运算符处理大数值,可以使用下面的方法处理大数值

* BigInteger add(BigInteger other):加
* BigInteger subtract(BigInteger other):减
* BigInteger multiply(BigInteger other):乘
* BigInteger divide(BigInteger other):除
* int compareTo(BigInteger other):相等:0,小于另一个:负数, 大于另一个:正数
* static BigInteger vlaurOf(long x):返回值等于x的大整数
* static BigDecimal valueOf(long x,int scale):返回值为x或x/10^scale的大整数

## 10，数组

声明数组:

``` java
int[] a;
```

创建数组

``` java
int[] a = new int[100];
//或者
int a[] = new int[100];
```

从语法上来书,第一种方式更好,因为他将类型[]和变量名分开了

值得注意的是,一旦创建了数组,就不能再改变他的大小(尽管可以改变每一个数组元素),如果经需要在运行过程中扩展数组的大小，就应该使用另一中数据结构--数组列表(array,list)

### 10.1,for each循环

在java中可以使用for each循环快速遍历每个元素,如下:

``` java
int[] a = new int[100];
for(int i =0,len=a.length;i<a;i++){
    a[i] = i;
}
for(int i:a){
    System.out.println(i)
}
```

### 10.2，数组初始化以及匿名数组

创建数组并赋予数组初始值

``` java
int[] smallPrimes = {2,3,5,7,11,13}
```

初始化一个匿名数组

``` java
new int[] {17,19,23,29,31,37}
```

### 10.3,数组拷贝

``` java
//直接引用,浅拷贝
int[] luckyNumbers = smallPrimes;
luckyNumbers[5] = 12;
//使用Array.copyOf()方法,深拷贝
int[] copiedLuckyNumbers = Arrays.copyOf(luckyNumbers,luckyNumbers.length);
//指定数组长度小于luckyNumebrs的长度时,当luckyNumbers是数值型时,多余的元素将被赋值为0,当数组元素是布尔型时,多余元素将被赋值为false。
```

### 10.4，命令行参数

每个java应用程序都有一个带String arg[]参数的main方法。这个参数表明main方法将接收一个字符串数组，也就是命令行参数。

看如下程序：

``` java
public class Message{
    public static void main(String[] args){
        if(args.length == 0 || args[0].equal("-h")){
            System.out.print("Hello,");
        }else if(args[0].equals("-g")){
            System.out.print()
        }
    }
}