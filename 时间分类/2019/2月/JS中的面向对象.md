# JS中的面向对象

 * [1,对象属性](#1,对象属性)
 * [2,创建对象](#2,创建对象)
 * [3,继承](#3,继承)
 * [4,ES6中对对象的扩充](#4,ES6中对对象的扩充)

##1,对象属性

### 1.1 属性类型

ECMAScript中有两种属性:数据属性和访问器属性

#### 1 数据属性

数据属性包含一个数据值的位置。在这个位置可以读取和写入值。数据属性有4个描述其行为的特性

 * [[Configurable]]:表示能否通过delete删除属性从而重新定义属性,能否修改属性的特性，或者能否把属性修改为访问器属性。像直接在对象上定义的属性，它们的这个值默认为true。
 * [[Enumerable]]:表示能否通过for-in循环访问属性。像直接在对象上定义的属性，它们的这个特性默认值为true
 * [[Writable]]:表示能否修改属性的值,像直接在对象上定义的属性，它们的这个特性默认值为true
 * [[Value]]:包含这个属性的数据值。读取属性值的时候，从这个位置读:写入属性值的时候，把新值保存在这个位置。这个特性的默认值为undefined

这些特性是默认不可见的,但是可以通过使用Object.defineProperty()函数来修改

``` javascript
var person = {}
Object.defineProperty(person,'name',{
    writable:false,
    value:'xiaohei'
})
person.name = "xiaobaicai"
console.log([person])   //{name:'xiaobaicai'}
```

#### 2 访问器属性

访问器属性不包含数据值；它们包含一对儿getter和setter函数(不过，这两个函数都不是必需的)。在读取访问器属性时，会调用getter函数，这个函数负责返回有效的值；在写入访问器属性时，会调用setter函数并传入新值，这个函数负责决定如何处理数据。访问器属性有如下4个特性。

 * [[Configurable]]:表示能否通过delete删除属性从而重新定义属性，能否修改属性的特性，或者能否把属性修改为数据属性。对于直接在对象上定义的属性，这个特性的默认值为true。
 * [[Enumerable]]:表示能否通过for-in访问该属性
 * [[Get]]:在读取属性时调用的函数。默认值为undefined
 * [[Set]]:在设置属性时调用的函数。默认值为undefined

访问器属性不能直接来定义,必须使用Object.defineProperty()来定义，如下:

``` javascript
var book = {
    _year:2004,
    edition:1
}
Object.defineProperty(book,"year",{
    get:function(){
        return this._year
    },
    set:function(newValue){
        if(newValue>2004){
            this._year = newValue;
            this.edition += newValue - 2004;
        }
    }
});

book.year = 2005;
alert(book.edition);        //2
```

### 1.2 定义多个属性

要定义多个属性可以使用Object.defineProperties()方法

``` javascript
var person = {}
Object.defineProperties(person,{
    _year:{
        writable:true,
        value:2014
    },
    edition:{
        writable:true,
        value:1
    },
    year:{
        get(){
            return this._year
        },
        set(newValue){
            if(newValue>2014){
                this._year = newValue
                this.edition += newValue - 2014
            }
        }
    }
})
```

### 1.3 读取属性的特性
使用ECMAScript 5中的Object.getOwnPropertyDescriptor()方法，可以取得给定属性的描述符。这个方法接收2个参数:属性所在的对象和要读取其描述符的属性名称。返回值是一个对象，如果是访问器属性，这个对象的属性有configurable,enumerable,get和set.如果是值属性，这个对象的属性有configurable,enumerable,writable和value。
例如:

``` javascript
Object.getOwnPropertyDescriptor(peroson,'edition')
Object.getOwnPropertyDescriptors(person)
```

## 2,创建对象

### 2.1 工厂模式

工厂模式用于创建具有相同或者相似属性的对象，如下:

``` javascript
function createPerson(name,age,job){
    var o = new Object()
    o.name = name
    o.age = age
    o.job = job
    o.sayName = function(){
        console.log(name)
    }
    return o
}
var person1 = createPerson('xiaobaicai',22,'programmer')
var person2 = createPerson('xiaohei',22,'teacher')
```

### 2.2 构造函数模式

使用构造函数模式创建对象如下:

``` JavaScript
function Person(name,age,job){
    this.name = name
    this.age = age
    this.job = job
    this.sayName = function(){
        console.log(this.name)
    }
}

var person1 = new Person('xiaobaicai',22,'programmer')
var person2 = new Person('xiaohei',22,'teacher')
```

在这个例子中，Person()函数取代了createPerson()函数，我们注意到，Person()中的代码除了与createPerson()中相同的部分外，还存在以下不同之处:

 * 没有显式地创建对象
 * 没有直接将属性和方法赋给this对象
 * 没有return语句

要创建Person函数实例,必须使用new操作符。以这种方式调用构造函数实际上会经历以下4个步骤:

 * 创建一个新对象
 * 将构造函数的作用域赋给新对象(即将this指向该对象)
 * 执行构造函数中的代码(为这个新对象添加属性)
 * 返回新对象

使用构造函数创建的对象都会有一个constructor(构造函数)属性,该属性指向Person,如下:

``` JavaScript
person1.constructor == Person       //true
```

对象的constructor属性最初是用来标识对象类型的。但是，提到检测对象类型，还是instanceof操作符更可靠些，因为constructor属性只能显示对象的直接类型，而无法识别对象的继承类型，如下:

``` javascript
person1.constructor === Person  //true
person1.constructor === Object  //false
person1 instanceof Person       //true
person1 instanceof Object       //true
```

构造函数的问题:构造函数虽然极好的实现了OO思想，但是其和普通函数其实并没有区别，当直接调用的时候，其就相当于普通函数，当加上new调用的时候，其才是构造函数，如下:

``` javascript
function Person(name,age){
    this.name = name
    this.age = age
    console.log('hello')
}

person1 = new Person('xiaobaicai',22)
Person('xiaobaicai',22)
```

当直接调用Person()时，this将指向当前作用域,如上例则是指向global对象。

``` javascript
window.name     //'xiaobaicai'
```

### 2.3 原型模式

我们创建的每个函数都有一个prototype(原型)属性,这个属性是一个指针,指向一个对象，而这个对象的用途是包含可以有特定类型的所有实例共享的属性和方法。如下,是基于原型继承的常见用法。创建原型对象{'name':'xiaobaicai','age':22}而后基于该对象实现继承。

``` javascript
function Teacher(){
}
Teacher.prototype = {
    name:'xiaobaicai',
    age:22
}


var teacher1 = new Teacher()
var teacher2 = new Teacher()
teacher1.name = 'xiaohei'
console.log(teacher1.name)      //'xiaohei'     来自对象
console.log(teacher2.name)      //'xiaobaicai'  来自原型
```

在上面的情况中,可以使用hasOwnProperty()函数来查看属性是原型上的属性还是对象上的属性

``` JavaScript
teacher1.hasOwnProperty('name')     //true
teacher2.hasOwnProperty('name')     //false
```

在读取原型对象的使用时,还有一个值得注意的地方是:

 * 使用 in 时，读取的是对象上的属性和对象原型上的属性
 * 使用Object.keys()读取属性时候读取对象上的属性

如下:

``` javascript
teacher1.hasOwnProperty('age')      //false
'age' in teacher1                   //true
Object.keys(teacher1)               //['name']
```

### 2.4 组合使用构造函数模式和原型模式

创建自定义类型的最常见方式，就是组合使用构造函数模式与原型模式。构造函数模式用于定义实例属性，而原型模式用于定义方法和共享的属性。结果，每个实例都会有自己的一份实例属性的副本，但同时又共享着对方法的引用，最大限度地节省了内存。另外，这种混合模式还支持向构造函数传递参数，可谓是集两种模式之长。如下是一个组合继承的例子

``` javascript
function Person(name,age,job){
    this.name = name
    this.age = age
    this.job = job
    this.friends = ["Shelby","Court"]
}
Person.prototype = {
    constructor : Person,
    sayName : function(){
        console.log(this.name)
    }
}

var person1 = new Person("xiaobaicai",23,"Engineer")
var person2 = new Person("xiaohei",27,"Teacher")
```

其实这种继承方式和类继承是一样的。如上的写法在ES6中可以写成下面这样

``` javascript
class Person{
    constructor(name,age,job){
        this.name = name
        this.age = age 
        this.job = job
    }
    sayName(){
        console.log(this.name)
    }
}
```

比较两段代码，我们很容易理解，在组合继承中，原型对象中的属性相当于OOP中的类属性。

### 2.5 动态原型模式

有其他OO语言经验的开发人员在看到独立的构造函数和原型时，很可能会感到非常困惑。动态原型模式正是致力于解决这个问题的一个方案，它把所有信息都封装在了构造函数中，而通过在构造函数中初始化原型，保持了同时使用构造函数和原型的优点。换句话说，可以通过检查某个应该存在的方法是否有效，来决定是否需要初始化原型。如下:

``` javascript
function Person(name,age,job){
    this.name = name
    this.age = age
    this.job = job

    //检查某个原型中的方法是否有效
    if(typeof this.sayName != "function"){
        //如果无效，说明没有初始化原型，则初始化原型
        Person.prototype.sayName = function(){
            console.log(this.name)
        }
    }
}
```

### 2.6 寄生构造函数模式

通常，在前述的几种模式都不适用的情况下，可以使用寄生构造函数模式。这种模式的基本思想是创建一个函数，该函数的作用仅仅是封装创建对象的代码，然后再返回新创建的对象，但是，从表面上看，这个函数又很像是典型的构造函数。如下:

``` javascript
function Person(name,age,job){
    var o = new Object()
    o.name = name
    o.age = age
    o.job = job
    o.sayName = function(){
        console.log(this.name)
    }
    return o
}

var friend = new Person('xiaobaicai',23,'engineer')
friend.sayName();       //'xiaobaicai'
```

这种模式一般用于不方便直接修改原构造函数的情况，如下:假设我们徐亚为Array类型添加一个函数，但是由于不能直接修改Array构造函数，因此可以如下:

``` javascript
function SpecialArray(){
    //创建数组
    var values = new Array()
    values.push.apply(values,arguments)
    values.toPipedString = function(){
        return this.join("|")
    }
    return values
}
var colors = new SpecialArray("red","yellow","blue")        //这里也可以写成 var colors = SpecialArray
colors.toPipedString()      //"red|blue|green"
```

关于寄生函数，如上，虽然colors对象是我们通过SpecialArray()函数创建的，但是colors的构造函数仍然是Array,

``` javascript
colors instanceof SpecialArray  //false
colors instanceof Array         //true
```

### 2.7 稳妥构造函数模式

使用稳妥构造函数模式创建的对象被称为稳妥对象。所谓稳妥对象，指的是没有公共属性，而且其方法也不引用this的对象。稳妥对象最适合在一些安全的环境中(这些环境中会禁止使用this和new),或者在防止数据被其他应用程序改动时使用，稳妥构造函数遵循与寄生构造函数类似的模式，但有两点不同:一是新创建对象的实例方法不引用this,而是不适用new操作符调用构造函数。按照构造稳妥构造函数的要求，可以将前面的Person构造函数重写如下:

``` javascript
function Person(name,age,job){
    //创建要返回的对象
    var o = new Object()
    //可以在这里定义私有变量和函数

    //添加方法
    o.sayName = function(){
        console.log(name)
    }

    //返回对象
    return o;
}
```

注意,在以这种模式创建的对象中，除了使用sayName()方法之外，没有其他的方法访问name的值。可以像下面使用稳妥的Person构造函数

``` javascript
var friend = Person('xiaobaicai',23,'engineer')
friend.sayName()
```

## 3,继承

继承是OO语言中一个最为人津津乐道的概念。许多OO需要都支持两种继承方式:接口继承和实现继承。接口继承只继承方法签名，而实现继承则继承实际的方法。

### 3.1 原型链

ECMAScript中描述了原型链的概念，并将原型链作为实现继承的主要方法。其基本思想是利用原型让一个引用类型继承另一个引用类型的属性和方法。
实现原型链有一种基本模式，其代码大致如下:

``` javascript
function SuperType(){
    this.property = true
}
SuperType.prototype.getSuperValue = function(){
    return this.property
}
function SubType(){
    this.subproperty = false
}
//继承了SuperType
SubType.prototype = new SuperType()
SubType.prototype.getSubValue = function(){
    return this.subproperty
}

var instance = new SubType()
console.log(instance.getSuperValue())      //true
```

在如上的例子中，通过将SubType的prototype指向一个SuperType()实例实现继承。

在使用这种方式实现原型的过程中，需要注意的有以下几点:
 * 1,所有函数的默认原型都是Object的实例
 * 2,要确定原型和实例的关系可以使用 instanceof 和 isPrototypeOf() 方法
 * 3,子类型中覆盖超类型中的某个方法时，需要将给原型添加方法的代码放在替换原型的语句之后。

原型链继承除了上述一些注意事项之外，还有2个值得注意的问题。

##### 1 原型中的引用类型是共享的

可以看如下代码

``` javascript
function SuperType(){
    this.colors = ['red','yellow','blue']
}

function SubType(){}
SubType.prototype = new SuperType()

var instance1 = new SubType()
var instance2 = new SubType()

instance1.colors.push("black")
instance2.colors        //['red','yellow','blue','black']
```

可以看到在上诉代码中,instance1和instance2中的colors属性会相互影响。

##### 2 不能传参

原型链的第二个问题是:在创建子类型的实例时，不能向超类型的构造函数中传递参数。实际上，应该说是没有办法在不影响所有对象实例的情况下，给超类型的构造函数传递参数。有鉴于此，实践中很少会单独使用原型链

### 3.2 借用构造函数

在解决原型中包含引用类型值所带来的问题时，可以使用一种叫做借用构造函数的技术。这种技术的基本思想相当简单，即在子类型构造函数的内部调用超类型构造函数。如下:

``` javascript
function SuperType(){
    this.colors = ["red"]
}
function SubType(){
    SuperType.call(this)
}

var instance1 = new SubType()
var instance2 = new SubType()
instance1.colors.push('yellow')
instance1.colors            //['red','yellow']
instance2.colors            //['red']
```

相对于单纯的原型链继承，借用构造函数的优势在于可以向超类传递参数,如下:

``` javascript
function SuperType(name){
    this.name = name
}
function SubType(name){
    SuperType.call(this,name)
}
```

但是借用构造函数仍然存在问题:方法都在构造函数中定义，因此函数复用就无从谈起了。而且，在超类型的原型继承中定义的方法，对子类型而言也是不可见的，结果所有类型都只能使用构造函数模式。考虑到这些问题，借用构造函数的技术也是很少使用的

### 3.3 组合继承

组合继承，有时候也叫伪经典继承，指的是将原型链和借用构造函数的技术组合到一块，从而发挥两者之长的一种继承模式。其背后的实现思路是使用原型链实现对原型属性和方法的继承。简单的说就是使用原型链继承方法，使用借用构造函数继承属性。如下:

``` javascript
function SuperType(name){
    this.name = name
    this.colors = ['red','blue']
}
SuperType.prototype.sayName = function(){
    console.log(this.name)
}

function SubType(name,age){
    //继承属性
    SupeType.call(this,name)
    this.age = age
}

//继承方法
SubType.prototype = new SuperType()
SubType.prototype.constructor = SubType;
SubType.prototype.sayAge = function(){
    console.log(this.age)
}
```

组合继承避免了原型链继承和借用构造函数继承的缺陷，融合了它们的缺点，称为JavaScript中最常用的继承模式。而且，instanceof和isPrototypeOf()也能够用于识别基于组合继承创建的对象。

### 3.4 原型式继承

原型式继承是指在基于一个现有的对象，在该对象的基础上对该对象进行装饰从而实现继承。
其基本思想如下:

``` 

