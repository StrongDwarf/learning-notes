# android studio组织layout中的代码
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本篇文章采用界面模块分组,<br>
##### 第一步:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在res/layout文件夹下创建自己想要的目录（有几个模块就建几个目录）<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注意:这里需要切换到project文件形势下创建文件夹,或者直接在文件夹中创建文件夹，在android目录结构下创建文件夹后是看不到该文件夹的。<br>
##### 第二步:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在创建好的模块目录下,创建layout文件夹<br>
##### 第三步:
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;将布局文件放入相应的layout文件夹<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;我这里创建完后的目录结构如下：<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;layout-home-layout-activity_main.xml<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;           |-activity_a.xml<br>
##### 第四步:打开app/build.gradle，在android{}中加入以下代码:
```
sourceSets{
   main{
      res.srcDirs =  
         [
            'src/main/res/layout/home',
            'src/main/res/layout',
            'src/main/res'
         ]
   }
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最后编译一下,成功完成<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>

![ ](https://github.com/StrongDwarf/learning-notes/blob/master/public/img/1545703387236.png?raw=true " ")
