# 《阿里巴巴 Java开发手册》阅读笔记

## 第一章:编程规约

### 1.1 命名风格

1,代码中的命名均不能以下划线或美元符号开始，也不能以下划线或美元符号结束

2,代码中的命名严禁使用拼音与英文混合的方式，更不允许直接使用中文的方式

3,类名使用UpperCamelCase风格，但DO/BO/DTO/VO/AO/PO等情形例外

4,方法名,参数名,成员变量,局部变量都统一使用lowerCamelCase风格，必须遵从驼峰形式

5,常量命名全部大写，单词间用下划线隔开，力求语义表达清楚，不要嫌名字长

6,抽象类命名使用Abstract或Base开头;异常类命名使用Exception结尾;测试类命名以它要测试的类名开始，以Test结尾

7,类型与中括号之间无空格相连定义数组

    正例:定义整形数组int[] arrayDemo;
    反例:在main参数中,使用String args[]定义

8,POJO类中布尔类型的变量都不要加上is前缀,否则部分框架解析会引起序列化错误。

9,包名统一使用小写,点分隔符之间有且仅有一个自然语义的英语单词。包名统一使用单数形式,单数类名如果有复数含义，则类名可以使用复数形式。

    正例:应用工具类包名为com.alibaba.ai.util,类名为MessageUtils

10,避免在子父类的成员变量之间或者不同代码块的局部变量之间采用完全相同的命名方式，使可读性降低。

    说明:子类,父类成员变量名相同，即使属性是public也是能通过编译，虽然局部变量在同一方法内的不同代码块中同名也是合法的，但应避免使用。对于非setter/getter的参数名称也应该避免与成员变量名称相同。

``` java
//反例
public class ConfusingName{
    public int alibaba;
    //非setter/getter的参数名称
    //不允许与本类成员变量同名
    public void get(String alibaba){
        if(true){
            final int taobao = 15;
            //....
        }

        for(int i =0;i<10;i++){
            //在同一方法内，不允许与其他代码块中的taobao命名相同
            final int taobao = 15;
            //...
        }
    }
}
class Son extends ConfusingName{
    //不允许与父类的成员变量名称相同
    public int alibaba;
}
```

11,杜绝完全不规范的缩写,避免词不达意

    反例:AbstractClass“缩写”命名成absClass,condition"缩写"命名成condi，此类随意缩写严重降低了代码的可读性。

12,为了达到代码自解释的目标,任何自定义编程元素在命名时,使用尽量完整的单词组合来表达其意

    正例:在JDK中,对某个对象引用的volatile字段进行原子更新的类名为:AtomicReferenceFieldUpdater
    反例:变量 int a;的随意命名方式

13,在常量与变量的命名时,表示类型的名词放在词尾，以提升辨识度。

    正例:startTime/workQueue/nameList/TERMINATED_THREAD_COUNT
    反例:startedAt/QueueOfWork/listName/COUNT_TERMINATED_THREAD

14,如果模块,接口,类,方法使用了设计模式,应在命名时体现出具体模式。

    正例:public class OrderFactory;
        public class LoginProxy;

15,接口类中的方法和属性不要加任何修饰符号(public也不要加),保持代码的简洁性,并加上有效的Javadoc注释。尽量不要在接口中定义变量,如果一定要定义变量,必须是与接口方法相关的，并且是整个应用的基础常量。

    正例:   接口方法签名:void commit();
            接口基础常量:String COMPANY = "alibaba";
    反例:   接口方法定义:public abstract void commit()
    说明:   如果JDK8中接口允许有默认实现,那么这个default方法,是对所有实现类都有价值的默认实现。

16,接口和实现类的命名有两套规则：

    1)对于Service和DAO类,基于SOA的理念,暴露出来的服务一定是接口,内部的实现类用Impl后缀与接口区别。
        正例:CacheServiceImpl实现CacheService接口
    2)如果是形容能力的接口名称。取对应的形容词为接口名(通常是-able的形式)
        正例:AbstractTranslator实现Translatable

17,枚举类名建议带上Enum后缀,枚举成员名称需要全大写,单词间用下划线隔开。

18,各层命名规约:

    1)Service/DAO层方法命名规约如下:
        获取单个对象的方法用get作为前缀
        获取多个对象的方法用list作为前缀,复数结尾,如listObjects。
        获取统计值的方法用count作为前缀
        插入的方法用save/insert作为前缀
        删除的方法用remove/delete作为前缀
        修改的方法用update作为前缀
    2)领域模型命名规约如下
        数据对象:xxxDo,xxx为数据表名
        数据传输对象:xxxDTO,xxx为业务领域相关的名称
        展示对象:xxxVO,xx一般为网页名称
        POJO是DO/DTO/BO/VO的统称,禁止命名成xxxPOJO

### 1.2 常量定义

1,不允许任何魔法值(即未经预先定义的常量)直接出现在代码中

``` java
//反例
String key = "Id#taobao_" + tradeId;
cache.push(key,value)
```

2,在long或者long初始赋值时,数值后使用大写的L,不能是小写的l。小写l容易跟数字1混淆，造成误解。

``` java
Long a = 21;    //写的是数字的21还是Long型的2?
```

3,不要使用一个常量类维护所有常量,要按常量功能进行归类,分开维护。

如:缓存相关常量放在类CacheConsts下;系统配置相关常量放在类ConfigConsts下

4,常量的复用层次有5层:跨应用共享常量,应用内共享常量，子工程内共享常量，包内共享常量，类内共享常量。

 1):跨应用共享常量:放置在二方库中,通常是在client,jar中的constant目录下
 2):应用内共享常量:放置在一方库中,通常是在子模块中的constant目录下
 3):子工程内部共享常量:即在当前子工程的constant目录下
 4):包内共享常量:即在当前包下单独的constant目录下。
 5):类内共享常量:直接在类内部以private static final的方式定义

5,如果变量值仅在一个范围内变化,则用enum类型来定义。

``` java
/**
 * 说明:如果存在名称之外的延伸属性,应使用enum类型，下面正例的数字就是延伸信息,表示一年之内的第几个季节。
 **/
public enum SeasonEnum{
    SPRING(1),SUMMER(2),AUTUMN(3),WINTER(4);
    private int seq;
    SeasonEnum(int seq){
        this.seq = seq;
    }
    public int getseq(){
        return seq;
    }
}
```

### 1.3 代码格式

1,大括号的使用约定。如果大括号内为空,则简介地写成{}即可,大括号中间无须换行和空格;

2,左小括号和字符之间不出现空格；同样,右小括号和字符之间也不出现空格;而左大括号前需要加空格。

3,if/for/while/switch/do等保留字与括号之间都必须加空格

4,任何二目，三目运算符的左右两边都需要加一个空格

5,采用4个空格缩进,禁止使用tab字符,如果使用tab缩进,必须设置1个tab为4个空格。

6,注释的双斜线与注释内容之间有且仅有一个空格

7,单行字符字符数限制不超过120个,超出需要换行，换行时遵循如下原则:

    1):第二行相对第一行缩进4个空格,从第三行开始,不在继续缩进,
    2):运算符与下文一起换行
    3):方法调用的点符号与下文一起换行
    4):方法调用中的多个参数需要换行时,在逗号后进行
    5):在括号前不要换行,

8,方法参数在定义和传入时,多个参数逗号后边必须加空格。

``` java
//正例
method(arg1, arg2, arg3);
```

9,IDE的text file encoding设置为UTF-8,IDE中文件的换行符使用Unix格式,不要使用Windows格式

10,单个方法的总函数不超过80行

代码分清红花和绿叶,个性和共性,绿叶逻辑单独出来成为额外方法，使主干代码更加清晰;共性逻辑抽取成为共性方法，便于复用和维护

11,没有必要增加若干空格来使某一行的字符与上一行对应位置的字符对齐。

``` java
//正例
int one = 1;
long two = 2L;
float three = 3F;
StringBuffer sb = new StringBuffer();
```

说明:增加sb这个变量,如果需要对齐,则给a,b,c都要增加几个空格,在变量比较多的情况下,是非常累赘的

12,不同逻辑,不同语义,不同业务的代码之间插入一个空行分隔开来以提升可读性。

说明:任何情形,没有必要插入多个空行进行隔开

### 1.4 OOP规约

1,避免通过一个类的对象引用此类的静态变量或静态方法,造成无谓增加编译器解析成本,直接用类名来访问即可。

2,所有的覆写方法,必须加@Override注解

3,相同参数类型,相同业务含义,才可以使用Java的可变参数,避免使用Object

    说明:可变参数必需放置在参数列表的最后面(建议工程师尽量不用可变参数编程)

``` java
//正例
public list<User> listUsers(String type, Long... ids) {...}
```

4,对外部正在调用或者二方库依赖的接口,不允许修改方法签名，以避免对接口调用方产生影响。若接口过时，必须加@Deprecated注解，并清晰地说明采用的新接口或者新服务是什么。

5,不能使用过时的类或方法

    说明:java.net.URLDecoder中的方法deode(String encodeStr)已经过时,应该使用双参数decode(String source,String encode).接口提供方既然明确是过时接口，那么有义务同时提供新的接口;作为调用方来说,有义务去考证过时方法的新实现是什么。

6,Object的rquals方法容易抛出空指针异常，应使用常量或确定有值的对象来调用equals。

``` java
//正例
"test".equals(object);
//反例
object.equals("test");
```

7,所有整形包装类对象之间值的比较，全部使用equals方法

    说明:对于Integer var = ? 在-128 ~ 127范围内的赋值,Integer对象是在IntegerCache.cache中产生的，会复用已有对象,这个区间内的Integer值可以直接使用 == 进行判断,但是这个区间之外的所有数据，都会在堆上产生,并不会复用已有对象。这是一个大坑。推荐使用equals方法进行判断。

8,浮点数之间的等值判断,基本数据类型不能用 == 进行比较,包装数据类型不能用equals方法进行判断。

    说明:浮点数采用"尾数+阶码"的编码方式,类似科学计数法的"有效数字+指数"的表达方式。二进制数无法精确表示大部分的十进制小数，

``` java
// 反例
float a = 1.0f - 0.9f;
float b = 0.9f - 0.8f;

if(a == b) {
    // 预期进入此代码块,执行其他业务逻辑
    // 但是 a == b 的结果为false
}

Float x = Float.valueOf(a);
Float y = Float.valueOf(b);
if(x.equals(y)) {
    // 预期进入此代码块,执行其他业务逻辑
    // 但是 x.equals(y) 的结果为false
}
```

``` java
// 正例:
//    1):指定一个误差范围,两个浮点数的差值在此范围之内,则认为是相等的。
float a = 1.0f - 0.9f;
float b = 0.9f - 0.8f;
// 10的-6字方
float diff = 1e-6f;

if(Math.abs(a - b) < diff) {
    System.out.println("true");
}

//    2):使用BigDecimal来定义值,再进行浮点数的运算操作。
BigDecimal a = new BigDecimal("1.0");
BigDecimal b = new BigDecimal("0.9");
BigDecimal c = new BigDecimal("0.8");

BigDecimal x = a.subtract(b);
BigDecimal y = b.subtract(c);

if(x.equals(y)) {
    System.out.println("true");
}
```

9,定义数据对象DO类时,属性类型要与数据库字段类型相匹配。

    正例:数据库字段的bigint类型必须与类属性的Long类型相对应

10,禁止使用构造方法BigDecimal(double)的方式把double值转化为BigDecimal对象

    说明:BigDecimal(double)存在精度损失风险,在精确计算或值比较的场景中可能会导致业务逻辑异常。

11,关于基本数据类型与包装数据类型的使用标准如下:

    1)所有的POJO类属性必需使用包装数据类型
    2)RPC方法的返回值和参数必须使用包装数据类型
    3)所有的局部变量使用基本数据类型

12,在定义 DO/DTO/VO 等POJO类时,不要设定任何属性默认值。

    反例:POJO类的createTime默认值为new Date();,但是这个属性在数据提取时并没有置入具体值,在更新其他字段时又附带更新了此字段,导致创建时间呗修改成当前时间。

13,当序列化类新增属性时,请不要修改serialVersionUID字段,以避免反序列失败；如果完全不兼容升级,避免反序列混乱,那么请修改serialVersionUID值

    说明:serialVersionUID不一致会抛出序列化运行时异常

14,构造方法里面禁止加入任何业务逻辑,如果有初始化逻辑,请放在init方法中。

15,POJO类必须写toString方法,在使用IDE工具source>generate toString时,如果继承了另一个POJO类,注意在前面加一下super.toString.

    说明:在方法抛出异常时,可以直接调用POJO的toString()方法打印其属性值,便于排查问题

16,禁止在POJO类中,同时存在对应属性xxx的isXxx()和getXxx()方法

    说明:框架在调用属性xxx的提取方法时,并不能确定哪种方法一定是被优先调用到

17,当使用索引访问String的split方法得到的数组时,需在最后一个分隔符后做有无内容的检查,否则会有抛IndexOutOfBoundsException的风险。

``` java
// 说明
String str = "a,b,c,,";
String[] arg = str.split(",");
// 预期大于3,结果是3
System.out.println(arg.length);
```

18,当一个类有多个构造方法时，或者多个同名方法时,这些方法应该按顺序放置在一起,便于阅读,此条规则优先于本书第19条规则。

19,类内方法定义的顺序是:公有方法或保护方法 > 私有方法 > getter/setter方法

    说明:公有方法是类的调用者和维护这最关心的方法,首屏展示最好;保护方法虽然只有子类关心,但也可能是"模板设计模式"下的核心方法;而私有方法外部一般不需要特别关心,是一个黑盒实现；因为承载的信息价值较低,所有Service 和 DAO 的getter/setter 方法放在类体后面。

20,在setter方法中,参数名称与类成员变量名称一致,this.成员名 = 参数名。在getter/setter方法中，不在增加业务逻辑,否则会增加排查问题的难度。

``` java
// 反例
public Integer getData() {
    if(condition) {
        return this.data + 100;
    } else {
        return this.data - 100;
    }
}
```

21,在循环体内,字符串的连接方式使用StringBuilder的append方法扩展

    说明:如下面例子中反编译出的字节码文件显示每次循环都会new出一个StringBuilder对象,然后进行append操作,最后通过toString方法返回String对象,造成内存资源浪费。

``` java
// 反例
String str = "start";
for (int i = 0; i < 100; i++) {
    str = str + "hello";
}
```

22,final可以声明类,成员变量,方法及本地变量,下列情况使用final关键字:

    1)不允许被继承的类,如:String类
    2)不允许修改引用的域对象,如:POJO类的域变量
    3)不允许被重写的方法,如:POJO类的setter方法
    4)不允许运行过程中重新赋值的局部变量
    5)避免上下文重复使用一个变量,使用final描述可以强制重新定义一个变量，方便更好地进行重构。

23,慎用Object的clone方法来拷贝对象。

    说明:对象的clone方法默认是浅拷贝,若想实现深拷贝,需要重写clone方法来实现域对象的深度遍历式拷贝。

24,类成员与方法访问控制从严:

    1)如果不允许外部直接通过new来创建对象,那么构造方法必须限制为private
    2)工具类不允许有public或default构造方法
    3)类非static成员变量并且与子类共享,必须限制为protected
    4)类非static成员变量并且仅在本类使用,必须限制为private
    5)类statc成员变量如果仅在本类使用,必须限制为private
    6)若是static成员变量,必须考虑是否为final
    7)类成员方法只供类内部调用,必须限制为private
    8)类成员方法只对继承类公开,限制为protected

### 1.5 集合处理

1,
