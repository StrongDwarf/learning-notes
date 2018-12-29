# JavaScript中的Function类型
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;说起来ECMAScript中什么最有意思，那莫过于函数了-而最有意思的根源，则在于函数实际上是对象。每个函数都是Function类型的实例，而且都与其他引用类型一样具有属性和方法。由于函数是对象，因此函数名实际上也是一个指向函数对象的指针，不会与某个函数绑定。函数通常是使用函数声明语法定义的，如下面的例子所示。<br>
```
function sum(num1,num2){
   return num1+num2
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这与下面使用函数表达式声明函数的方式几乎相差无几。<br>
```
var sum = function(num1,num2){
   return num1+num2;
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;以上代码定义了变量sum并将其初始化为一个函数。在下面的定义函数的方式中，并没有使用函数名，这是因为在使用函数表达式定义函数时，没有必要使用函数名-通过变量sum即可以引用函数。另外，还要注意函数末尾有一个分号，就像声明其他变量时一样。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最后一种定义函数的方式是使用Function构造函数，如下:<br>
```
var sum = new Function('num1','num2','return num1+num2');
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;从技术角度上讲，这是一个函数表达式。但是，我们不推荐读者使用这种方法定义函数，因为这种语法会导致解析两次代码(第一次解析常规ECMAScript代码，第二次是解析传入构造函数中的字符串)，从而影响性能。不过,这种语法对于理解"函数是对象，函数名是指针"的概念倒是非常直观的。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;由于函数名仅仅是指向函数的指针，因此函数名与包含对象指针的其他变量没有什么不同。换句话说，一个函数可能会有多个名字，如下所示:<br>
```
function sum(num1,num2){
   return num1+num2;
}
var sum1 = sum;
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在上面的代码中<br>
