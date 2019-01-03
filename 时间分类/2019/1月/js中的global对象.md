# js中的global对象

和其他所有语言一样，javascript中当然也有全局对象-global。所有在全局作用域中定义的属性和函数,都是global对象的属性.
而JS中的global值得值得注意的地方主要有三点

### 1 URL编码方法.
JS中的global对象提供了encodeURI()和encodeURIComponent()方法对URI(通用资源标志符)进行编码。
两者的区别在于:
 * encodeURI()不会对本身属于URI的特殊字符进行编码,例如冒号,正斜杠,问好和井号;
 * 而encodeURIComponent()则会对它发现的任何非标准字符进行编码。

两者使用时候的区别可以看如下代码
```
let uri = "http://xiaobaicai.com/hello world";
encodeURI(uri);     //http://xiaobacai.com/hello%20world
encodeURIComponent(uri);    //http%3A%2F%2F:/xiaobaicai.com/hello%20world
```

就使用而言,encodeURIComponent()使用的更多,因为在实践中常见的是对查询字符串参数而不是对基础URI进行编码.
对编码后的URI字符串进行解码可以使用decodeURI()和decodeURIComponent()方法解码
###eval()方法

eval()方法可以称为是JavaScript中最强大的一个方法了,其作用相当于一个JavaScript编译器,接收一个参数,并将其作为一串JavaScript代码执行。
eval()方法中执行的代码,代码执行所在的作用域仍然是和eval()函数所在的作用域是一样的。
```
let a = 1;
eval("console.log(a)");     //1
```
值得注意的是eval()函数容易引发安全性问题,所以在严格模式下,该方法已经被禁用了

###Global对象的属性
Global对象还包含一些属性,其包括3个特殊值和一些构造函数如:
 * undefined    特殊值
 * NaN      特殊值
 * Infinity     特殊值
 * Object       
 * Array
 * Function
 * Boolean
 * String
 * Number
 * Date
 * Regexp
 * Error
 * EvalError
 * RangeError
 * ReferenceError
 * SyntaxError
 * TypeError
 * URIError

###Global对象的实现
Global对象在web客户端的实现是window，在服务器端(node环境中)的实现还是global.
所以,在客户端为全局对象添加属性,可以设置window.propertyName，而在服务器端设置全局属性可以设置global.propertyName。如下:
``` JavaScript
/*客户端*/
window.editor = "小白菜";       //设置后全局可访问

/*服务器端*/
global.editor = "小白菜";       //设置后全局可访问
```
