# DOM2级中定义的样式

在HTML中定义样式的方式有3种:通过<link/>元素包含外部样式表文件,使用<style/>元素定义嵌入式样式，以及使用style特性针对特定元素的样式。"DOM2级样式"模块围绕这3种应用样式的机制提供了一套API。要确定浏览器是否支持DOM2级定义的CSS能力，可以使用下列代码:

``` JavaScript
var supportsDOM2CSS = document.implementation.hasFeature("CSS",2.0)
var supportsDOM2CSS2 = document.implementation.hasFeature("CSS2",2.0)
```

### 1 访问元素的样式

任何支持style特性的HTML元素在JavaScript中都有一个对应的style属性。这个style对象是CSSStyleDeclaration的实例，包含着通过HTML的style特性指定的所有样式信息,但不包含与外部样式表或嵌入样式表经过层叠而来的样式。在style特性中指定的任何CSS属性都将表现为这个style对象的相应属性。对于使用短划线(分隔不同的词汇,例如background-image)的CSS属性名，必须将其转换成驼峰大小写形式，才能通过JavaScript来访问。如下:

``` javascript
background-image    :style.backgroundImage
```

多数情况下,都可以通过简单的转换属性名的格式来实现转换。其中一个不能转换的CSS属性就是float,对于IE,需要将其转换为styleFloat,对于IE之外的浏览器,应该将其转换为cssFloat

#### 1.1,DOM样式属性和方法

"DOM2级样式"规范还为style对象定义了一些属性和方法。这些属性和方法在提供元素的style特殊值的同时，也可以修改样式。下面列出了这些属性和方法

* cssText:通过它能访问到style特性中的CSS代码
* length:应用给元素的CSS属性的数量
* parentRule:表示CSS信息的CSSRule对象。本章后面将讨论CSSRule类型
