# JavaScript中的RegExp类型
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ECMAScript通过RegExp类型来支持正则表达式。通过下面语法就可以创建一个正则表达式:<br>
```
var expression = /pattern/flags;
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其中的模式部分可以是任何简单或复杂的正则表达式，可以包含字符类，限定符，分组，向前查找以及方向引用。每个正则表达式都带有一或多个标志(flags)，用以标明正则表达式的行为。正则表达式的匹配模式支持下列3个标志。<br>
* g:表示全局(global)模式，即模式将被应用于所有字符串，而非在发现第一个匹配项时立即停止;
* i:表示不区分大小写模式，即在匹配时忽略模式与字符串的大小写;
* m:表示多行模式，即在到达一行文本末尾时还会继续查找下一行中是否存在与模式匹配的项。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因此，一个正则表达式就是一个模式与上述3个标志的组合体。不同组合产生不同结果，如下面的例子所示.<br>
```
var pattern1 = /at/g;   //匹配字符中所有"at"的实例
var pattern2 = /[bc]at/i;   //匹配第一个"bat"或"cat"， 不区分大小写
var pattern3 = /.at/gi;      //匹配所有以at结尾的3个字符的组合，不区分大小写
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;与其他语言中的正则表达式类似，模式中使用的所有元字符都必须转义。正则表达式中的元字符包括:<br>
```
( [ { \ ^ $ | ) ? * + . ] }
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这些元字符在正则表达式中都有一或多种特殊用途，因此如果想要匹配字符串中包含的这些字符，就必须对它们进行转义。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;前面举的这些例子都是以字面量形式来定义的正则表达式。另一种创建正则表达式的方式是使用RegExp构造函数，它接收两个参数:一个是要匹配的字符串模式，另一个是可选的标志字符串。可以使用字面量定义的任何表达式，都可以使用构造函数来定义，如下面的例子所示。<br>
```
var pattern1 = /[bc]at/i;     //匹配第一个bat或者cat,不区分大小写
var pattern2 = new RegExp('[bc]at','i');     //与pattern1相同
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在此，pattern1和pattern2是两个完全等价的正则表达式。要注意的是，传递给RegExp构造函数的两个参数都是字符串。由于RegExp构造函数的模式参数是字符串，所以在某些情况下要对字符进行双重转义。所有原字符都必须双重转义，那些已经转义过的字符也是如此，例如\n（字符\在字符串中通常被转义为\\，而在正则表达式字符串中就会变成\\\）。下表给出了一些模式，左边是这些模式的字面量形式，右边是使用RegExp构造函数定义相同模式时使用的字符串。<br>
* 字面量模式: /\[bc\]at/   等价的字符串: "\\[bc\\]at"
* 字面量模式: /\w\\hello\\123/   等价的字符串:"\\w\\\\hello\\\\123"
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用正则表达式字面量和使用RegExp构造函数创建的正则表达式不一样。在ECMAScript 3中，正则表达式字面量始终会共享同一个RegExp实例，而使用构造函数创建的每一个新RexExp实例都是一个新实例。如下例子:<br>
```
var re = null,
     i;
for(i=0;i&lt;10;i++){
   re = /cat/g;
   re.test("catastrophe");
}
for(i = 0;i&lt;10;i++){
   re = new RegExp("cat","g");
   re.test("catastrophe");
}
```
## 1 RegExp实例属性
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RegExp 的每个实例都具有下列属性，通过这些属性可以取得有关模式的各种信息。<br>
* global:布尔值,表示是否设置了g标志
* ignoreCase:布尔值，表示是否设置了i标志
* lastIndex:整数，表示开始搜索下一个匹配项的字符位置，从0算起
* multiline:布尔值，表示是否设置了m标志
* source:正则表达式的字符串表示,按照字面量形式而非传入构造函数中的字符串模式返回
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;通过这些属性可以获知一个正则表达式的各方面信息，但是却没什么用，因为这些信息全部包含在模式声明里。<br>
## 2 RegExp实例方法
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RegExp对象的主要方法是exec()，该方法是专门为捕获组而设计的。exec()接受一个参数，即要应用模式的字符串，然后返回包含第一个匹配信息的数组；或者在没有匹配项的情况下返回null。返回的数组虽然是Array的实例，但包含两个额外的属性:index和input。其中，index表示匹配项在字符串中的位置,而input表示应用正则表达式的字符串。在数组中，第一项是与整个模式匹配的字符串，其他项是与模式中的捕获组匹配的字符串(如果模式中没有捕获组，则该数组只包含一项)。如下:<br>
```
var text = "mom and dad and baby";
var pattern = /mom( and dad( and baby)?)?/gi;
var matchs = pattern.exec(text);
alert(matchs.index);   //0
alert(matchs.input);     //mom and dad and baby
alert(matches[0]);     //“mom and dad and baby”
alert(matches[1]);        //'and dad and baby'
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这个例子中的模式包含两个捕获组。最内部的捕获组匹配"and baby",而包含它的捕获组匹配"and dad"或者"and dad and baby"。当把字符串传入exec()方法中之后，发现了一个匹配项。因此,整个字符串本身与模式匹配，所以返回的数组matches的index属性值为0.数组中的第一项是匹配的整个字符串，第二项包含与第一个捕获组匹配的内容，第三项包含与第二个捕获组匹配的内容。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对于exec()方法而言，即使在模式中设置了全局标志(g),它每次也只会返回一个匹配项。在不设置全局标志的情况下，在同一个字符串上多次调用exec()将始终返回第一个匹配项的信息。而在设置全局标志的情况下，每次调用exec()则都会在字符串中继续查找新匹配的项。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正则表达式的第二个方法是test()，它接受一个字符串参数。在模式与该参数匹配的情况下返回true；否则，返回false。在只想知道目标字符串与某个模式是否匹配，但不需要知道其文本内容的情况下,使用这个方法非常方便。因此，test()方法经常被用在if语句中，如下面的例子所示。<br>
```
var text = '000-00-0000';
var pattern = /\d{3}-\d{2}-\d{4}/;
if(pattern.test(text)){
   alert("the pattern was matched");
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RegExp实例继承的toLocaleString()和toString()方法都会返回正则表达式的字面量，与创建正则表达式的方式无关。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 后面的内容有点乱,以后再整理
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
