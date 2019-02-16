# JS中的面向对象

## 目录

 * [1,对象属性](# 1,对象属性)
 * [2,创建对象](#2,创建对象)
 * [3,继承](# 3,继承)
 * [4,ES6中对对象的扩展](# 4,ES6中对对象的扩展)
 * [5,总结](# 5,总结)

## 1,对象属性

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

``` javascript
function object(o){
    function F(){}
    F.prototype = o
    return new F()
}
```

在object()函数内部,先创建了一个临时性的构造函数，然后将传入的对象作为这个构造函数的原型，最后返回了这个临时类型的一个新实例。从本质上讲，object()对传入其中的对象执行了一次浅复制。来看下面的例子

``` javascript
var person = {
    name:'xiaobaicai',
    friends:['xiaohei','xiaobai']
}

var anotherPerson = object(person)
anotherPerson.name = 'xiaohuihui'
anotherPerson.friends.push("xiaoheihei")

var yetAnotherPerson = object(person)
anotherPerson.name = 'xiaoheiehi'
yetAnotherPerson.friends.push("xiaohuihui")

console.log(person.friends)     //['xiaohei','xiaobai','xiaoheihei','xiaohuihui']
```

这种原型式继承的基本思想是:将基本类型值属性作为实例独有属性，将引用类型值属性作为对象共有的属性使用。

原型式继承在ES5中可以直接使用Object.create()方法实现，该方法接收2个参数,一个用做新对象原型的对象和(可选的)一个为新对象定义额外属性的对象。在传入一个参数的情况下，Object.create()与object()方法的行为相同

``` javascript
var person = {
    name:'xiaobaicai',
    friends:['1','2']
}

var anotherPerson = Object.create(person,{
    name:{
        value:'xiaohuihui'
    }
})
anotherPerson.friends.push("xiaoheihei")

var yetAnotherPerson = Object.create(person,{
    name:{
        value:'xiaoheihei'
    }
})
yetAnotherPerson.friends.push("xiaohuihui")

console.log(person.friends)     //['1','2','xiaoheihei','xiaohuihui']
```

### 3.5 寄生式继承

寄生式继承的思路与寄生构造函数和工厂模式类似，即创建一个仅用于封装继承过程的函数，该函数在内部以某种方式来增强对象，最后再像真的是它做了所有工作一样返回对象。如下:

``` javascript
function createAnother(original){
    var clone = object(original)    //通过调用函数创建一个新的对象
    clone.sayHi = function(){
        console.log("hi")
    }
    return clone
}
```

在这个例子中，createAnother()函数接收了一个参数，也就是将要作为新对象基础的对象。然后，把这个对象(original)传递给object()函数，将返回的结果赋值给clone。再为clone对象添加一个新方法sayHi(),最后返回clone对象。可以像下面这样来使用createAnother()函数:

``` javascript
var person = {
    name:'xiaobaicai',
    age:23
}
var anotherPerson = createAnother(person)
anotherPerson.sayHi()   
```

### 3.6 寄生组合式继承

前面说过，组合继承是JavaScript最常用的继承模式;不过它也有自己的不足。组合继承最大的问题就是无论什么情况下，都会调用两次超类型构造函数，一次是在创建子类型的时候，一次是在子类型构造函数内部。没错，子类型最终会包含超类型对象的全部实例属性，但我们不得不在调用子类型构造函数时重写这些属性。组合继承的例子如下:

``` javascript
function SuperType(name){
    this.name = name
}
SuperType.prototype.sayName = function(){
    console.log(this.name)
}

function SubType(name,age){
    SuperType.call(this,name)           //第二次调用SuperType
    this.age = age
}

SubType.prototype = new SuperType()   //第一次调用SuperType
SubType.prototype.constructor = SubType
SubType.prototype.sayAge = function(){
    console.log(this.age)
}
```

这种继承方式一般来说没什么大问题，但是子类型中的属性因为调用了两次构造函数所以分别在生成的对象和对象的原型中都存在，如下:

``` JavaScript
var subInstance = new SubType('xiaobaicai',22)
console.log(subInstance.name)   //'xiaobaicai'
console.log(subInstance.prototype.name)     //undefined
```

其中对象原型中的属性被对象的属性覆盖了。
寄生组合式继承就能够解决这种问题:所谓寄生组合式继承，即通过借用构造函数来继承属性，通过原型链的混合形式来继承方法。其背后的基本思路是:不必为了指定子类型的原型而调用超类型的构造函数，我们所需要的无非是超类型的一个副本而已。本质上，就是使用寄生式继承来继承超类型的原型，然后再将结果指定给子类型的原型。寄生组合式继承的基本模式如下:

``` javascript
function inheritPrototype(subType,superType){
    var prototype = object(superType.prototype)     //创建对象
    prototype.constructor = subType                 //增强对象
    subType.prototype = prototype                   //指定对象
}
```

实际使用如下:

``` javascript
function SuperType(name){
    this.name = name
}
SuperType.prototype.sayName = function(){
    console.log(this.name)
}

function SubType(name,age){
    SuperType.call(this,name)
    this.age = age
}
inheritPrototype(SubType,SuperType)
subType.prototype.sayAge = function(){
    console.log(this.age)
}
```

## 4,ES6中对对象的扩展

ES6中对对象的扩充主要分2方面的扩充,一是Object对象上方法和属性的扩展，二是新增class语法

### 4.1 Object对象上方法和属性的扩展

#### 4.1.1 属性的简洁表示法

在ES5中,我们表示一个对象的属性只能如下一样表示

``` javascript
var o = {
    属性名1:属性值1,
    属性名2:属性值2,
    ...
}
```

而在ES6中，如下的情况可以简洁表示如下

``` javascript
//ES5
var name = 'xiaobaicai'
var person = {
    name:name
}
//ES6
var name = 'xiaobaicai'
var person = {
    name
}
```

在ES6中对象中的函数属性也可以简洁表示如下

``` javascript
//ES5
var person = {
    sayName:function(name){
        console.log(name)
    }
}
//ES6
var person = {
    sayName(name){
        console.log(name)
    }
}
```

#### 4.1.2 属性名表达式

ES6新增使用表达式作为属性名,不过必须放在[]中

``` javascript
var a = 'name'
var person = {
    [a]:'xiaobaicai',
    ['a'+'ge']:23
}
```

#### 4.1.3 方法的name属性

函数的name属性返回函数名。对象方法也是函数，因此也有name属性。返回的name有以下几种情况

 * 1:如果对象的方法使用的取值函数(getter)和存值函数(setter),则name属性不是在该方法上面，而是在该方法属性的描述对象的get和set属性上面，返回值是方法名前加get和set
 * 2:bind方法创建的函数，name属性返回"bound"加上原函数的名字
 * 3:Function构造函数创造的函数，name属性返回"anonymous"
 * 4:如果对象是一个Symbol值，那么name属性返回的是这个Symbol值的描述
 * 5:除上面的几种情况之外，都返回函数名称

如下:

``` javascript
//有取值器的函数方法
var obj1 = {
    get foo(){},
    set foo(value){}
}
var descriptor = Object.getOwnPropertyDescriptor(obj1,'foo')
descriptor.get.name         //"get foo"

//bind方法创建的函数
var sayHello = function(){}
sayHello.bind().name        //"bound sayHello"

//Function构造函数创建的函数
var func1 = new Function("console.log('hello world')")
func1.name                  //"anonymous"

//对象是Symbol值
var key1 = Symbol('function1')
var key2 = Symbol()
var obj2 = {
    [key1](){},
    [key2](){}
}
obj2[key1].name         //"[function1]"
obj2[key2].name         //""

//一般情况
function sayHi(){}
sayHi.name              //"sayHi"
```

#### 4.1.4 Object.assign()

Object.assign(original，[obj1,obj2,obj3,...])用于实现对象的浅复制。会将obj1,obj2,obj3...这些对象中的可遍历属性复制到original中。并返回复制后的original,在复制过程中，如果有相同的属性名，则后面的会覆盖前面的。如下

``` javascript
var person1 = {
    age:23,
    friends:['1','2']
}
var person2 = {
    name : 'xiaobaicai'
}
var person3 = {
    name : 'xiaohei'
}
Object.assign(person2,person1)
Object.assign(person3,person1)
person2.friends.push('3')
console.log(person3.friends)    //['1','2','3']
```

当Object.assign()只有一个参数时,会将该参数转化为Object对象，并返回该对象。如果是无法转为Object对象的参数则报错

``` javascript
Object.assign(3)        //Number{3}
Object.assign(undefined)    //Error
Object.assign(null)         //Error
```

#### 4.1.5 __proto__属性,Object.setPrototypeOf(),Object.getPrototyprOf()

__proto__属性用来读取和设置对象的prototype对象，目前所有浏览器都部署了这个属性。
Object.setPrototypeOf()用来设置对象的prototype对象
Object.getPrototypeOf()用来读取对象的prototype对象

__proto__前后的双下划线说明了它是一个内部属性，不建议直接使用该属性，建议使用setPrototypeOf()和getPrototypeOf()进行写和读

``` javascript
//创建新对象
var person = {
    name:'xiaobaicai'
}
//创建对象的proto
var proto = {}
//设置__proto__
Object.setPrototypeOf(person,proto)
proto.age = 22
console.log(Object.getPrototypeOf(person))      //{age:22}
```

#### Object.keys(),Object.values(),Object.entries()

Object.keys()返回一个包含对象中所有可遍历对象的键的数组
Object.values()返回一个包含对象中所有可遍历对象的值的数组
Object.entries()返回一个包含对象中所有可遍历对象的键值的数组

``` javascript
var arr = ['a','b','c','d']
Object.keys(arr)    //[0,1,2,3]
Object.values(arr)  //['a','b','c','d']
Object.entries(arr) //[[0,'a'],[1,'b'],[2,'c'],[3,'d']]
```

#### Object.getOwnPropertyDescriptors()

ES5中有Object.getOwnPropertyDescriptor()用来返回某个对象属性的描述对象。
Object.getOwnPropertyDescriptors()则是返回对象的所有自身属性(非继承属性)的描述对象

``` javascript
var person = {
    name:'xiaobaicai',
    age:23
}
console.log(Object.getOwnPropertyDescriptors())
/*
{
    name:{
        value:'xiaobaicai',
        writable:true,
        enumerable:true,
        configurable:true
    },
    age:{
        value:'xiaobaicai',
        writable:true,
        enumerable:true,
        configurable:true
    }
}
*/
```

### 4.2 ES6中新增的class继承

OO语言中基本都有的东西..使用方法如下:

``` javascript
class Point{
    constructor(x,y){
        this.x = x
        this.y = y
    }
}
class ClassPoint extends Point{
    constructor(x,y,color){
        super(x,y)
        this.color
    }
    getColor(){
        return this.color
    }
    setColor(newColor){
        this.color = newColor
    }
}
```

## 5,总结

写这篇博客主要学习了JS中的面相对象的东西,其中主要的只是可以分为4个部分:
第一部分 属性:学习了对象的属性，对象的属性分为数据属性和访问器属性，其中两个不同的数据属性拥有的描述符也不一样
第二部分 创建对象:学习了创建对象的几种模式,如工厂模式,构造函数模式.原型模式,组合使用原型模式和构造函数模式,动态原型模式,寄生构造函数模式,
第三部分 继承:这部分首先了解了什么是原型链，然后学习了几种实现继承的模式，分别是:借用构造函数,组合继承,原型式继承,寄生式继承,寄生组合式继承,
其中,借用构造函数简单的调用一次SuperType的构造函数,组合继承则不单单调用SuperType的构造函数,还将SuperType.prototype赋值给SubType.prototype，原型式继承则在一个原型上进行赋值,其基本思想是浅复制原型对象，然后再复制后的对象上做修改，返回修改后的对象，寄生式继承与原型式继承类型，同样需要复制原型对象，但并不对复制后的对象进行修改，而是对复制后的对象进行增强。寄生组合式继承,寄生组合式继承则是在组合继承上做了改进，组合继承给SubType.prototype赋值的是 new SuperType()， 而寄生组合式继承给SubType.prototype赋值的
仅仅是SuperType.prototype





