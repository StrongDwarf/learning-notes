# 接口,lambda表达式与内部类

接口(interface)技术,主要用来描述类具有什么功能,而并不给出每个功能的具体实现。一个类可以实现(implement)一个或多个接口,并在需要接口的地方,随时使用实现了相应接口的对象。

lambda表达式是一种表示可以在将来某个时间点执行的代码块的简洁方法。使用lambda表达式,可以用一种精巧而简洁的方式表示使用回调或变量行为的代码。

内部类(inner class)。理论上讲,内部类有些复杂，内部类定义在另外一个类的内部,其中的方法可以访问包含它们的外部类的域。内部类技术主要用于设计具有相互协作关系的类集合。

代理(proxy)。是一种实现任意接口的对象。代理是一种非常专业的构造工具,它可以用来构建系统级的工具。

## 1 接口

### 1.1 接口概念

在JAVA程序设计语言中,接口不是类,而是对类的一组需求描述,这些类要遵从接口描述的统一格式进行定义。

### 1.2 接口特性

接口不是类,不能使用new运算符实例化一个接口

``` java
x = new Comparable(...);    // ERROR
```

然而,尽管不能构造接口的对象,却能声明接口的变量

``` java
Comparable x;   // OK
```

接口变量必须引用实现了接口的类对象

并且可以像使用instanceof检查一个对象是否属于某个特定类一样，也可以使用instance检查一个对象是否实现了某个特定的接口:

``` java
if (anObject instanceOf Comparable) {
    // ...
}
```

接口可继承

``` java
public interface Powered extends Moveable{
    // ...
}
```

一个类可继承多个接口

``` java
class Employee implements Cloneable,Comparable {
    // ...
}
```

### 1.3 接口与抽象类

一个类可继承多个接口,但却只能继承一个抽象类。

接口用于定义类应该具有的属性,而抽象类用于表示类的类别

### 1.4 静态方法

在JAVA SE8 中,允许在接口中添加静态方法。理论上讲,没有任何理由认为这是不合法的。只是这有违于将接口作为抽象规范的初衷。

### 1.5 默认方法

可以为接口方法提供一个默认实现。必须用default修饰符标记这样一个方法。

``` java
public interface Comparable<T> {
    default int compareTo(T other) {
        return 0;
    }
}
```

### 1.6 解决默认方法冲突

如果先在一个接口中将一个方法定义为默认方法,然后又在超类或另一个接口中定义了同样的方法,会发生什么情况?

* 1)超类优先,如果超类提供了一个具体方法,同名而且有相同参数类型的默认方法会被忽略
* 2)接口冲突,如果一个超接口提供了一个默认方法,另一个接口提供了一个同名而且参数类型(不论是否属是默认参数)相同的方法,必须覆盖这个方法来解决冲突。

``` java
// 当继承的两个接口中有同名同参的方法时,可以选择两个冲突方法中的一个
class Student implements Person, Named {
    public String getName() {
        return Person.super.getName();
    }
}
```

## 2 lambda表达式

### 2.1 为什么引入lambda表达式

lambda表达式是一个可传递的代码块,可以在以后执行一次或多次。

### 2.2 lambda表达式的语法

代码示例:

``` java
(String first, String second)
    -> first.length() - second.length();
```

### 2.3 函数式接口

对于只有一个抽象方法的接口,需要这种接口的对象时,就可以提供一个lambda表达式。这种接口称为函数式接口。

代码示例:

``` java
Arrays.sort(words, (first, second) -> first.length() - second.length());
```

### 2.4 方法引用

有时,可能已经有现成的方法可以完成你想要传递到传递其他代码的某个动作。

代码示例:

``` java
Timer t = new Timer(1000, event -> System.out.println(event));
```

### 2.5 构造器引用

构造器引用与方法引用很类似,只不过方法名为new。例如,Person::new是Person构造器的一个引用。

### 2.6 变量作用域

lambda表达式可以捕获外围作用域中变量的值。lambda表达式中捕获的变量必须实际上是最终变量。实际上的最终变量是指,这个变量初始化之后就不会再为它赋新值。

## 3 内部类

### 3.1 使用内部类访问对象状态

内部类既可以访问自身的数据域,也可以访问创建它的外围类对象的数据域。

``` java
public class TalkingClock {
    private int interval;
    private boolean beep;

    public TalkingClock(int interval, boolean beep) {
        // ...
    }
    public void start() {
        // ...
    }

    /**
     * an inner class
     **/
    class TimePrinter implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.out.println("At the tone,the time is " + new Date());
            // beep是外围类对象的引用
            if(beep) {
                Tookit.getDefaultToolkit().beep();
            }
        }
    }
}
```

### 3.2 内部类的特殊语法规则

## 代理

略

