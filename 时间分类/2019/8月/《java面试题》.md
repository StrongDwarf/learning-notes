# java面试题

### 1,一个".java"源文件中是否可以包括多个类(不是内部类)?有什么限制?

可以有多个类,但只能有一个public的类,并且public的类名必须与文件名相一致

### 2,java有没有goto?

java中的保留字,现在没有在java中使用

### 3,说说&和&&的区别

&和&&都可以用作逻辑与的运算符,表示逻辑与(and),当运算符两边的表达式的结果都为true时,整个运算结果才为true,否则,只要有一方为false,则结果为false。

&&还具有短路的功能,即如果第一个表达式为false,则不再计算第二个表达式,例如,对于if(str!=null&&!str.equals(s))表达式,当str为null时,后面的表达式不会执行,所以不会出现NullPointerException如果将&&改为&,则会抛出NullPointException异常。 if(x==33 & ++y>0) y会增长,if(x==33 && ++y > 0)不会增长

&还可以用作