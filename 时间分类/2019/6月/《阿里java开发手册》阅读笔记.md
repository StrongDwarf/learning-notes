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

3,if/for/while/switch/do等保留字与括号之间
