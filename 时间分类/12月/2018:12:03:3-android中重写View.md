# android中重写View
## 1 view介绍
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在Android开发中，Android的UI界面都是由View及其派生类组合而成的。View类几乎包含了所有的屏幕类型，每个View都有一个用于绘图的画布。画布可以进行任意扩展，只需要重写onDraw方法，就能绘制界面显示。界面即可以是复杂的3D效果，也可只是简单的文本显示。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;下面是View常用的一些XML属性及相关方法:<br>
* android:id   setId(int)   给当前View设置一个在当前xxx.xml中的唯一编号，可以通过调用View.findViewById()或Activity.findViewById()根据编号查找到对应的View.不同的layout.xml之间定义相同的id不会冲突
* android:clickable   setClickable(boolean clickable)     是否响应点击事件 true:允许 false:不允许
* android:longClickable   setLongClickable(boolean clickable) 是否响应长点击事件 true:允许   false:禁止
* android:background   setBackgroundColoe 设置背景颜色
* android:visibility     setVisibility 是否可见 Visible:默认值，可见   Invisible:不可见 Gone:不可见，并且在屏幕中不占位置
* android:scrollbars     设置滚动条显示, None:隐藏，不可见   Horizontal:水平 Vertical:垂直
* android:onClick 在xml中给onclick设置什么值，在对应的Activity中写对应的方法。例如:android:onClick="test",在Activity中写对应的方法public void test(View view){}
* android:padding setPadding(int,int,int,int)   设置上下左右内边距
* android:paddingTop
* android:paddingBottom
* android:paddingLeft
* android:paddingRight
* android:layout_margin   设置外边距
* android:layout_marginTop
* android:layout_marginBottom
* android:layout_marginLeft
* android:layout_marginRight
* android:minHeight   setMinimumHeight(int)   设置视图最小高度
* android:minWidth   setMinimumWidth(int)   设置视图最小宽度
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;View 是所有UI组件的基类。一般来说，开发Android应用程序的UI界面都不会直接使用View，而是使用派生类。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;View派生出的直接子类有ImageView,TextView,ViewGroup,ProgressBar等。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;View派生出的间接子类有AbsListView,Button,Edittext,CheckBox等。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用系统给我们提供的View派生类能实现开发中的大部分需求，但是有时候需要针对业务需求去定制View，例如画饼状图,折线图,贝塞尔曲线等。<br>
### 1.1 自定义View
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自定义View一般会重写3个方法<br>
* protected void onDraw(){}      //在View绘制时使用
* protected void onDetachedFromWindow(){}      //在销毁View时使用
* protected void onMeasure(){}   //在View测量时候使用
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在上面的3个方法中,主要逻辑放在onDraw中，onDetachedFromWindow方法中存放垃圾处理函数，比如销毁事件监听等，而onMeasure则定义View的布局逻辑。<br>
#### onMeasure函数的使用
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其中onMeasure会涉及到View的一个静态内部类MeasureSpec,MeasureSpec类封装了父View传递给子View的布局要求，没个MeasureSpec实例代表宽度或者高度(只能是其一)要求。MeasureSpec的字面意思是测量规格或测量属性，在measure方法中有两个参数widthMeasureSpec和heightMeasureSpec.如果使用widthMeasureSpec,我们就可以通过MeasureSpec计算出宽度模式Mode和宽度的实际值。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;测量的模式分为以下三种:<br>
* EXACTLY:精确值模式.当View的layout_width或者layout_height属性设置为具体的数值(例如，android:layout_width="100dp")或者指定为"match_parent"时（系统会自动分配为父布局的大小）使用的模式
* AT_MOST:最大值模式.当View的layout_width胡总layout_height属性设置为"wrap_content"时使用的模式
* UNSPECIFIFD:可以将视图按照自己的意愿设置成任意大小，没有任何限制。这种情况比较少见，很少用到。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当自定义view没有override onMeasure函数时，view渲染的长和宽一般会很混乱。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;下面是一个重写onMeasure方法的例子<br>
```
@Override
protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
   //获取父容器为它设置的模式和大小
   int widthMode = MeasureSpec.getMode(widthMeasureSpec);
   int widthSize = MeasureSpec.getSize(widthMeasureSpec);
<br>
   int heightMode = MeasureSpec.getMode(heightMeasureSpec);
   int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    
   int width;
   int height;
   if(widthMode == MeasureSpec.EXACTLY){
      //指定宽度或者match_parent
      width = widthSize;
   }else{
      width = (int)(getPaddingLeft()+getPaddingRight() + rectF.width()*2);
   }
   if(heightMode == MeasureSpec.EXACTLY){
      //指定高度或这match_parent
      height = heightSize;
   }else{
      height = (int)(getPaddingTop()+getPaddingBottom() + rectF.height()*2);
   }
   setMeasuredDimension(width,height);
}
```
### 1.2 自定义属性
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首先在res/values下新建一个attrs.xml文件。内容如下:<br>
```
&lt;?xml version="1.0" encoding="utf-8" ?&gt;
&lt;resources&gt;
   &lt;declare-styleable name="customStyleView"&gt;
      &lt;attr name="sweepAngleAdd" format="integer" /&gt;
   &lt;/declare-styleable&gt;
&lt;/resources&gt;
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其中，name为属性集的名字，主要用途是标识属性集;attr标签可以有多个；format属性对应的类型有很多，例如string,integer,dimension,reference,color,enum,这里使用integer<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在布局xml文件中引用我们刚刚自定义的属性。activity_main.xml在修改之后的内容如下:<br>
```
&lt;?xml version="1.0" encoding="utf-8" ?&gt;
&lt;RelativeLayout  
      xmlns:custom="http://schemas.android.com/apk/res-auto"&gt;
      &lt;view
         custom:sweepAngleAdd = "10" /&gt;
&lt;/RelativeLayout&gt;
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这样，最外层的RelativeLayout增加了自定义View的命名空间。之后在自定义View中就可以使用custom:sweepAngleAdd属性了。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;接下来在MyView类的init方法中获取sweepAngleAdd的值<br>
```
//获取自定义的属性的值
TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.customStyleView);
sweepAngleAdd = typedArray.getInt(R.styleable.customStyleView_sweepAngleAdd,0);
typedArray.recycle();
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这样做的好处就是如果我们想改变圆形每次增加的弧度大小，只需要修改xml文件即可，不需要修改自定义View的java代码，达到封装的效果。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
