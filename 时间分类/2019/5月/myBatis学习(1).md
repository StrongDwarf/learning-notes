# myBatis学习笔记

## 原文章地址

[MyBatis学习总结(一)——MyBatis快速入门](https://www.cnblogs.com/xdp-gacl/p/4261895.html)

## 介绍

MyBatis是一个支持普通SQL查询,存储过程和高级映射的优秀持久层框架。MyBatis消除了几乎所有的JDBC代码和参数的手工设置以及对结果集的检索封装。MyBatis可以使用简单的XML或注解用于配置和原始映射。将接口和JAVA的POJO(普通的java对象)映射成数据库中的记录

## 快速入门

### 创建数据库和表,针对MySql数据库

SQL脚本如下:

``` sql
create database mybatis
use mybatis
CREATE TABLE users(id INT PRIMARY KEY AUTO_INCREMENT,NAME VARCHAR(20),age INT)
INSERT INTO users(NAME,age) VALUES('小白菜',21)
INSERT INTO users(NAME,age) VALUES('小黑',22)
```

在数据库中执行,并创建表

### 使用MyBatis查询表中的数据

#### 1，添加MyBatis的配置文件conf.xml

在src目录下创建了conf.xml文件,文件内容如下:

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
     <environments default="development">
         <environment id="development">
             <transactionManager type="JDBC" />
             <!-- 配置数据库连接信息 -->
             <dataSource type="POOLED">
                 <property name="driver" value="com.mysql.jdbc.Driver" />
                 <property name="url" value="jdbc:mysql://localhost:3306/mybatis" />
                 <property name="username" value="root" />
                 <property name="password" value="XDP" />
             </dataSource>
         </environment>
     </environments>
</configuration>
```

#### 2，定义表所对应的实体类

User类的代码如下:

``` java
package me.gacl.domain;

/**
 * @author gacl
 * users表对应的实体类
 */
public class User{
    //实体类的属性和表的字段名称对应
    private int id;
    private String name;
    private int age;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name
    }

    public void setName(String name){
        this.name = name
    }

    public int getAge(){
        return age
    }

    public void setAge(int age){
        this.age = age
    }

    @Override
    public String toString(){
        return "User [id="+id+",name="+name+",age="+age+"]";
    }
}
```

#### 3，定义操作users表的sql映射文件userMapper.xml

userMapper.xml文件的内容如下:

``` xml
 <?xml version="1.0" encoding="UTF-8" ?>
 <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
 <!-- 为这个mapper指定一个唯一的namespace，namespace的值习惯上设置成包名+sql映射文件名，这样就能够保证namespace的值是唯一的
 例如namespace="me.gacl.mapping.userMapper"就是me.gacl.mapping(包名)+userMapper(userMapper.xml文件去除后缀)
  -->
<mapper namespace="me.gacl.mapping.userMapper">
    <!-- 在select标签中编写查询的SQL语句， 设置select标签的id属性为getUser，id属性值必须是唯一的，不能够重复
   使用parameterType属性指明查询时使用的参数类型，resultType属性指明查询返回的结果集类型
    resultType="me.gacl.domain.User"就表示将查询结果封装成一个User类的对象返回
    User类就是users表所对应的实体类
    -->
    <!-- 
         根据id查询得到一个user对象
    -->
    <select id="getUser" parameterType="int"
        resultType="me.gacl.domain.User">
        select * from users where id=#{id}
    </select>
</mapper>
```

#### 4，在conf.xml文件中注册userMapper.xml文件

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC" />
            <!-- 配置数据库连接信息 -->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver" />
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis" />
                <property name="username" value="root" />
                <property name="password" value="XDP" />
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <!-- 注册userMapper.xml文件， 
        userMapper.xml位于me.gacl.mapping这个包下，所以resource写成me/gacl/mapping/userMapper.xml-->
        <mapper resource="me/gacl/mapping/userMapper.xml"/>
    </mappers>

</configuration>
```

#### 5，编写测试代码:执行定义的select语句

``` java
package me.gacl.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import me.gacl.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBulider;

public class Test1{
    public static void main(String[] args) throws IOException{
        //mybatis的配置文件
        String resource = "conf.xml";
        //使用类加载器加载mybatis的配置文件(它也加载关联的映射文件)
        InputStream is = Test1.class.getClassLoader().getResourceAsStream(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        //使用MyBatis提供的Resource类加载mybatis的配置文件(它也加载关联的映射文件)
        //Reader reader = Resources.getResourceAsReader(resource); 
        //构建sqlSession的工厂
        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
        /**
         * 映射sql的标识字符串，
         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
        String statement = "me.gacl.mapping.userMapper.getUser";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        User user = session.selectOne(statement, 1);
        System.out.println(user);
    }
}
```

### 使用MyBatis对表执行CRUD操作

#### 基于XML的实现

##### 1，定义sql映射xml

userMapper.xml文件的内容如下:

``` xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!-- 为这个mapper指定一个唯一的namespace，namespace的值习惯上设置成包名+sql映射文件名，这样就能够保证namespace的值是唯一的
例如namespace="me.gacl.mapping.userMapper"就是me.gacl.mapping(包名)+userMapper(userMapper.xml文件去除后缀)
 -->
<mapper namespace="me.gacl.mapping.userMapper">
    <!-- 在select标签中编写查询的SQL语句， 设置select标签的id属性为getUser，id属性值必须是唯一的，不能够重复
    使用parameterType属性指明查询时使用的参数类型，resultType属性指明查询返回的结果集类型
    resultType="me.gacl.domain.User"就表示将查询结果封装成一个User类的对象返回
    User类就是users表所对应的实体类
    -->
    <!-- 
        根据id查询得到一个user对象
     -->
    <select id="getUser" parameterType="int" 
        resultType="me.gacl.domain.User">
        select * from users where id=#{id}
    </select>

    <!-- 创建用户(Create) -->
    <insert id="addUser" parameterType="me.gacl.domain.User">
        insert into users(name,age) values(#{name},#{age})
    </insert>

    <!-- 删除用户(Remove) -->
    <delete id="deleteUser" parameterType="int">
        delete from users where id=#{id}
    </delete>

    <!-- 修改用户(Update) -->
    <update id="updateUser" parameterType="me.gacl.domain.User">
        update users set name=#{name},age=#{age} where id=#{id}
    </update>

    <!-- 查询全部用户-->
    <select id="getAllUsers" resultType="me.gacl.domain.User">
        select * from users
    </select>

</mapper>
```

单元测试类代码如下:

``` java
package me.gacl.test;

import java.util.List;
import me.gacl.domain.User;
import me.gacl.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class TestCRUDByXmlMapper{
    @Test
    public void testAdd(){
        //SqlSession sqlSession = MyBatisUtil.getSqlSession(false)
    }
}

//分组->  test/java/../app/main.java   app里