# Android开发中常见的布局
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Android中系统SDK有5种布局，所有的布局都继承自ViewGroup，分别是LinearLayout(线性布局)，FrameLayout(框架布局),AbsoluteLayout(绝对布局)，RelativeLayout(相对布局),TableLayout(表格布局)。但是一般AbsoluteLayout与TableLayout几乎不会用到，所以这篇文章只介绍其他三种布局<br>
## 1 LinearLayout(线性布局)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;LinearLayout是线性布局控件，是ViewGroup的子类，会按照android:orientation属性的值对子View进行排序，可以将子View设置为垂直或水平方向布局。LinearLayout的每个子视图都会按照它们各自在XML中的出现顺序显示在屏幕上。<br>
### 设置排列方式
```
android:orientation="vertical";   //垂直排列
android:orientation="horizontal";     //水平排列
```
### android:layout_gravity(对齐方式)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;layout_gravity是LinearLayout子元素的特有属性。对于layout_gravity，该属性用于设置控件相对于容器的对齐方式，可选项有top,bottom,left,right,center_verticel,center_horizontal,center,fill等<br>
### 权重
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;LinearLayout布局中layout_weight属性用来分配子View在LinearLayout中占用的空间(显示大小),只有LinearLayout包裹的View才有这个属性。<br>
## 2 RelativeLayout(相对布局)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Relativelayout是相对布局,控件的位置是按照相对位置来计算的，一个控件需要依赖另外一个控件或者依赖父控件。这是实际布局中最常用的布局方式之一。它灵活性大很多，当然属性也多，操作难度也大。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RelativeLayout常用的一些属性如下:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第一类:属性名为true或false<br>
* android:layout_centerHorizontal 相对于父元素水平居中
* android:layout_centerVertical 相对于父元素垂直居中
* android:layout_centerInparent 相对于父元素完全居中(垂直水平都居中)
* android:layout_alignParentBottom 贴紧父元素的下边缘
* android:layout_alignParentLeft 贴紧父元素的左边缘
* android:layout_alignParentRight 贴紧父元素的右边缘
* android:layout_alignParentTop 贴紧父元素的上边缘
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第二类:属性名为id的引用名"@+id/name"<br>
* android:layout_below 在某元素的下方
* android:layout_above 在某元素的上方
* android:layout_toLeftOf 在某元素的左边
* android:layout_toRightOf   在某元素的右边
* android:layout_toLeftOf   在某元素的左边
* android:layout_alignTop 本元素的上边缘和某元素的上边缘对齐
* android:layout_alignLeft 本元素的左边缘和某元素的左边缘对齐
* android:layout_alignRight 本元素的右边缘和某元素的右边缘对齐
* android:layout_alignBottom 本元素的下边缘和某元素的下边缘对齐
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这种属性的使用方法如下:<br>
```
&lt;RelativeLayout &gt;
   &lt;TextView
      android:id="@+id/tv_nickname"&gt;&lt;/TextView&gt;
   &lt;EditText
      android:layout_toRightOf="@+id/tv_nickname"&gt;&lt;/EditText&gt;
&lt;/RelativeLayout&gt;
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第三类: 属性值为具体的值<br>
* android:layout_marginBottom:离某元素底边缘的距离
* android:layout_marginTop:离某元素上边缘的距离
* android:layout_marginLeft:离某元素左边缘的距离
* android:layout_marginRight:离某元素右边缘的距离
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这种属性的使用方法如下:<br>
```
&lt;TextView
   android:layout_marginTop = "20dp"&gt;&lt;/TextView&gt;
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 3 FrameLayout框架布局
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;FrameLayout是Android中比较简单的布局之一，该布局直接在屏幕上开辟了一块空白区域。向七周年添加控件时，所有的组件都会置于这块控件的左上角。如果所有的组件都一样大的话，同一时刻只能看到最上面的那个组件。当然，可以为组件添加layout_gravity属性，从而指定对齐方式。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;下面是一个定义4个控件的FrameLayout布局demo<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如下的demo最终渲染出来的元素都在左上角<br>
```
&lt;FrameLayout
   android:layout_width="match_parent"
   android:layout_height="match_parent"&gt;
   &lt;TextView
      android:layout_width="220dp"
      android:layout_height="220dp"&gt;&lt;/TextView&gt;
   &lt;TextView
      android:layout_width="200dp"
      android:layout_height="200dp"&gt;&lt;/TextView&gt;
   &lt;TextView
      android:layout_width="180dp"
      android:layout_height="180dp"&gt;&lt;/TextView&gt;
&lt;/FrameLayout&gt;
```
