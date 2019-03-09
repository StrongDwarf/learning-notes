# 程序运行时Function类型和其他引用类型的区别

关于Function类型基础的部分,这里不再细诉,有兴趣的可看这篇文章:[【JS基础】（八）JavaScript引用类型之Function类型](https://www.jianshu.com/p/4b97ce098a61)

这里要讲的是关于Function类型在JavaScript执行时,执行上下文的变化和其他引用类型的异同(Object,Array)

我们知道,当JavaScript代码执行一段可执行代码时,会创建对应的执行上下文。
这里不知道的可以看这篇文章：[《JavaScript深入》读书笔记](https://github.com/StrongDwarf/learning-notes/blob/master/时间分类/2019/3月/《JavaScript深入》读书笔记.md)

对于每个执行上下文,都有三个重要属性

* 变量对象(Variable object,VO)
* 作用域链(Scope chain)
* this

