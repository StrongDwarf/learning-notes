# 《JAVA基础知识》课程笔记

###### author:朱泽聪

###### class:后端一班

###### createTime:2019/7/6

## 目录

* [Java基础知识](#Java基础知识)
* [Java面向对象](#Java面向对象)
* [Java常用API](#Java常用API)

## Java基础知识

### 1.1 发展里程碑和主要特性

主要特性:

* java语言是简单,面向对象的。
* java语言支持Internet应用的开发。
* java语言是健壮和安全的。
* java语言是跨平台,可移植的。
* java语言是多线程的。

### 1.2 开发环境配置

下载JDK -> 配置环境变量JAVA_HOME,PATH -> CMD中测试

### 1.3 JVM组成

JVM组成:

* 运行时数据区:
  * 线程共享区:
    * 方法区:存储运行时常量池,已被虚拟机加载的类信息,常量,静态变量,即时编译器编译后的代码等数据。
    * Java堆:存储对象实例。
  * 线程独占区:
    * 虚拟机栈:存放方法运行时所需的数据,存为栈帧。
    * 本地方法栈:JVM调用本地方法。
    * 程序计数器(PC寄存器):记录当前线程所执行到的字节码的行号。
* 执行引擎。
* 本地库接口。
* 本地方法区。

### 1.4 基础语法

略

### 1.5 数据类型

* 基本数据类型:byte(1),short(2),int(4),long(8),float(4),double(8),char(2),boolean(1)。
* 引用数据类型:数组,类和接口。

### 1.6 变量类型

java中的变量类型:局部变量,实例变量和类变量。
*
### 1.7 修饰符

java中的修饰符分为:访问控制修饰符和非访问控制修饰符。

访问控制修饰符:

* public:所有类可见。
* protected:同一包内的类及子孙类可见。
* default:同一包内的类及同一包中的子孙类可见。
* private:仅当前类可见。

访问控制修饰符的继承规则:

* 父类中的public方法在子类中也必须为public
* 父类中protected方法在子类中,要么protected,要么public,不能private。
* private方法不能被继承

非访问控制修饰符:

* static
* final
* abstract
* synchronized:主要用于多线程编程,该关键字声明的方法同一时间只能被一个线程访问。
* volatile:volatile修饰成员变量在每次被线程访问时,都强制从共享内存中重新读取该成员变量的值。而且,当成员变量发生变化时,会强制线程将变化值回写到共享内存。
* transient:transient修饰的成员变量在序列化的对象时候,JVM会跳过该特定的变量。

### 1.8 运算符

### 1.9 流程控制语句

### 1.10 异常处理

略

## Java面向对象

### 2.1 对象和类

### 2.2 继承

### 2.3 重写和重载

略

### 2.4 多态和封装

###### 多态

多态是面向对象编程的又一个重要特性,它是指在父类中定义的属性和方法被子类继承之后,可以具有不同的数据类型或表现出不同的行为,这使得同一个属性和方法在父类及其各个子类中具有不同的含义。

对面向对象来说,多态分为编译时多态和运行时多态。其中编译时多态是静态的,主要是指方法的重载,它是根据参数列表的不同来区分不同的方法。通过编译之后会变成两个不同的方法,在运行时多态是动态的,它是通过动态绑定来实现的,也就是大家通常所说的多态性。

Java实现多态有3个必要条件:继承,重写和向上转型。

* 继承:在多态中必须存在有继承关系的子类和父类。
* 重写:子类对父类中某些方法重写定义。
* 向上转型:在多态中需要将子类的引用赋给父类对象,只有这样该引用才既能调用父类的方法,又能调用子类的方法。

###### 封装

封装就是讲对象的属性和方法相结合,通过方法将对象的属性和实现细节保护起来,实现对象的属性隐藏。

### 2.5 抽象类和抽象方法

抽象类和抽象方法的使用原则:

* 1):抽象类不能被实例化。
* 2):抽象类中不一定包含抽象方法,但是有抽象方法的类必定是抽象类。
* 3):抽象类中的抽象方法只是声明,不包含方法体,就是不给出方法的具体实现。
* 4):构造方法,类方法不能声明为抽象方法。
* 5):抽象类的子类必须给出抽象类中的抽象方法的具体实现,除非该类也是抽象类。

### 2.6 内部类和匿名类

包

### 2.7 包

java使用包这种机制是为了防止命名冲突,访问控制,提供搜索和定位类,接口,枚举和注释等,包名为小写字母。

## Java常用API

### 3.1 String API

Java中相关的String API有String,StringBuffer和StringBuilder。

###### 区别

* String:字符串常量
* StringBuffer:字符串变量,线程安全
* StringBuilder:字符串变量,非线程安全

###### 基本原则

* 操作少量数据使用String
* 单线程操作大量数据使用StringBuilder
* 多线程操作大量数据使用StringBuffer

### 3.2 流(Stream),文件(File)和IO

java.io中的包比较多,而且在此次课程中也没讲到什么,故等下次有时间再仔细查看。

### 3.2 集合框架

集合框架是一个用来代表和操作集合的统一架构。所有的集合框架都包含如下内容:

* 接口:是代表集合的抽象数据类型。例如Collection,List,Set,Map等。之所以定义多个接口,是为了以不同的方式操作集合对象。
* 实现(类):是集合接口的具体实现。从本质上讲,它们是可重复使用的数据结构,例如:ArrayList,LinkedList,HashSet,HashMap。
* 算法:是实现集合接口的对象里的方法执行的一些有用的计算,例如:搜索和排序。这些算法称为多态,那是因为相同的方法可以在相似的接口上有着不同的实现。

###### 相关接口,类,算法。

接口:

* List -> Collection接口:采用线性列表的存储方式,长度可动态改变。
* Map:采用键值对的存储方式,长度可动态改变。

###### ArrayList和LinkedList的区别。

* ArrayList:底层是基于动态数组,根据下标随机访问数组元素的效率高，向数组尾部添加元素的效率高,但是,删除数组中的数据以及向数组中间添加数据效率低，因为需要移动数组。
* LinkedList:底层是基于链表的,添加删除元素的效率都高,但是访问元素的效率低。

###### HashMap和CurrentHashMap的区别。

HashMap和CrrentHashMap都是基于数组和链表的,区别在于CurrentHashMap在多线程环境下会加锁

###### 集合快速失败和安全失败机制:

* 1:在单线程的遍历过程中,如果要进行remove操作,可以调用迭代器的remove方法而不是集合类的remove方法。
* 2:使用java并发包(java.util.concurrent)中的类来代替ArrayList和HashMap。

### 3.3 多线程编程

###### 线程的生命周期

出生,就绪,运行,等待,休眠,阻塞,死亡。

###### 实现多线程编程

实现多线程编程的方式主要有两种:

* 实现Runnable接口
* 继承Thread类

``` java
// 实现Runnable接口
public class MyRunnable implements Runnable {
    public void run () {
        System.out.println("MyRunnable运行中!");
    }
}
public class Test {
    public static void main(String[] args){
        Runnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        System.out.println("主线程运行结束！");
    }
}
```

``` java
// 继承Thread类
public class MyThread extends Thread {
    public void run() {
        System.out.println("这是线程类MyThread");
    }

    public static void main(String[] args) {
        MyThread mythread = new MyThread();
        // 开启线程
        mythread.start();
        System.out.pringln("运行结束！");
    }
}
```

``` java
// 使用Callable+Future
public class Task implements Cakkable<Integer> {
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        Future<Integer> result = executor.submit(task);
        executor.shutdown();
        Systme.out.println("task运行结果" + result.get());
    }
}
```

###### 同步与死锁

如果一个对象的状态是可变的,同时它又是共享的(即至少可被多于一个线程同时访问),则它存在线程安全问题。

解决方法:

``` java
// 同步方法
public synchronized void method() {
    // 可能会产生线程安全问题的代码
}

// 静态同步方法
public static synchronized void mathod() {
    // 可能会产生线程安全问题的代码
}

// 同步代码块
synchronized(Object o) {
    // 可能会产生线程安全问题的代码
}
```

同步锁使用的弊端:当线程任务中出现了多个同步(多个锁)时,如果同步中嵌套了其他的同步,容易引发死锁。