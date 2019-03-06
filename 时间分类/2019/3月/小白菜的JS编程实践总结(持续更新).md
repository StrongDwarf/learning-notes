# 小白菜的JS编程实践总结(持续更新)

## 代码约定

### 可读性

* 1,函数和方法:每个函数都应该包含一个注释,描述其目的和用于完成任务所可能使用的算法。陈诉实现的假设也很重要,如参数代表什么,函数是否有返回值
* 2,大段代码:用于完成单个任务的多行代码应该在前面放一个描述任务的注释
* 3,复杂的算法:如果使用了一种独特的方式解决了某个问题,则要在注释中解释你是如何做的。这不仅仅可以帮助其他浏览你代码的人，也能在下次你自己查阅代码的时候帮助理解
* 4,Hack:因为存在浏览器差异,JavaScript代码一般会包含一些Hack。不要假设其他人在看代码的时候能够理解Hack所要应付的浏览器问题。因为某种浏览器无法使用普通的方法，所以你需要用一些不同的方法，那么请将这些信息放在注释中。这样可以减少出现这种情况的可能性:有人偶然看到了你的hack,然后"修正"了它,最后重新引入了你本来修正了的错误。

### 变量命名

* 1,变量名应为名词如car,person
* 2,函数名应该以动词开始,如getName().返回布尔类型值的函数一般以is开头,如isEnable()
* 3,变量和函数都应使用合乎逻辑的名字,不要担心长度

### 变量类型透明

1:通过初始化指定变量类型

``` JavaScript
var found = false;      //布尔型
var count = -1;         //数字
var name = "";          //字符串
var person = null;      //对象
```

2:使用匈牙利标记法

JavaScript中最传统的匈牙利表示法是使用单个字符表示基本类型:"o"代表对象,"s"代表字符串,"i"代表整数,"f"代表浮点数,"b"代表布尔型。如下

``` javascript
var bFound;     //布尔型
var iCount;     //整数
var sName;      //字符串
var oPerson;    //对象
```

## 编程实践

### 解耦CSS/JavaScript

通过设置element的class而不是直接设置style减少css对javascript的耦合

CSS对JavaScript的紧密耦合

``` javascript
element.style.color = "red"
element.style.backgroundColor = "blue"
```

CSS对JavaScript的松散耦合

``` javascript
element.className = "edit"
```

### 解耦应用逻辑/事件处理程序

不直接在事件处理程序中编写应用逻辑,而是将事件处理程序和应用逻辑分离。以此减少耦合。

如下是事件处理程序和应用逻辑高度耦合的例子:

``` javascript
/**
 *@name:handleKeyPress(event) 处理键盘按键事件,并在输入回车键时显示错误信息
 *@param:event  事件
 *@return:undefined
 **/
function handleKeyPress(event){
    event = EventUtil.getEvent(event);
    if(event.keyCode === 13){
        var target = EventUtil.getTarget(event);
        var value = 5 * parseInt(target.value);
        if(value > 10){
            document.getElementById("error-msg").style.display = "block";
        }
    }
}
```

解耦后

``` javascript
/**
 *@name:validateValue(value) 验证值是否大于2,并在大于2时显示错误信息
 *@param:value Number类型
 *@return:undefined
 **/
function validateValue(value){
    value = 5 * parseInt(value);
    if(value > 10){
        document.getElementById("error-msg").style.display = "block";
    }
}

/**
 *@name:handleKeyPress(event) 处理键盘按键事件
 *@param:event 事件
 **/
function handleKeyPress(event){
    event = EventUtil.getEvent(event);
    if(event.keyCode === 13){
        var target = EventUtil.getTarget(event);
        validateValue(target.value)
    }
}
```

### 尊重对象所有权

如果你不负责创建或维护某个对象,它的对象或者它的方法，那么你就不能对它们进行修改。更具体的说:

* 不要为实例或原型添加属性
* 不要为实例或原型添加方法
* 不要重定义已存在的方法

若必须要使用不属于你的对象,可以创建该对象的副本并使用,复制对象可以使用如下函数:

``` javascript
/**
 *@name:deepCopy
 *@param:original:复制的目标
 *@return:object:返回一个对象,是复制源对象属性后生成的一个新对象
 **/
function deepCopy(original,target){
    var target = target||{};
    for(var propName in original){
        if(typeof original[propName] === 'object'){
            target[propName] = (original[propName].constructor === 'Array')?[]:{}
            deepCopy(original[propName],target[propName])
        }else{
            target[propName] = original[propName]
        }
    }
    return target
}
```

### 避免全局变量

与尊重对象所有权一样,在开发时要尽量避免全局变量。对于变量的作用域,要将其控制在变量的最小作用域中。具体如下:

* 变量不能声明在高于其作用区间的作用域中
* 尽量不使用全局变量，如果一个变量一定要在全局中使用,可以将其设置为自己负责的模块的变量,并在全局中通过访问模块的属性的形式访问该变量

如下:

``` javascript
//错误的写法
var author = 'xiaobaicai'
//正确的写法
var myModule = {
    author:'xiaobaicai'
}
```

