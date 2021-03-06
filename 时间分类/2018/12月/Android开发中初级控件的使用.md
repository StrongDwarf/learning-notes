# Android开发中初级控件的使用
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Android开发中经常需要用到很多初级控件，熟练的使用初级控件是一个Android开发工程师必备的能力。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本文总结了Android中的初级控件。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 1 TextView(文本视图)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TextView显示一行或者多行文本，也能显示html。在Android开发中，TextView是最常用的组件之一，基本上每天都会使用。<br>
#### 1 设置背景颜色
```
<TextView
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   android:background="#ff00ff"
   android:layout_marginTop="10dp"
   android:text="设置背景颜色"
/>
```
#### 2 在程序中动态赋值
```
TextView tv0=(TextView)findViewById(R.id.tv0);
tv0.setText("在程序中动态赋的值");
```
#### 3 实现多字符串的动态处理
##### (1) 在strings.xml文件中写入字符串
```
<string name="testing">这是一个数:%1$d,这是二位数:%2$d,这是三位数:%3$s</string>
```
##### (2) 在java代码中设置值
```
tv1.setText(getString(R.string.testing,new Integer[]{11,21,31}));
```
#### 4 TextView显示html，字体颜色为红色
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;需要注意的是，不支持html标签的style属性<br>
```
String html="<font color='red'>TextView显示html字体颜色为红色</font><br/>"
tv3.setText(Html.fromHtml(html));
```
#### 5 给TextView设置点击事件
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这个事件是父类view的，所以所有的Android控件都有这个事件，这里为了方面就采用内部类的方式<br>
```
tv4.setOnClickListener(new OnClickListener(){
   @Override
   public void onClick(View v){
      Toast.makeText(MainActivity.this,"点击了TextView",Toast.LENGTH_LONG).show();
   }
}
```
#### 6 给TextView文字加粗并显示阴影效果
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;字体阴影需要4个相关参数:<br>
* android:shadowColor:阴影的颜色
* android:shadowDx:水平方向上的偏移量
* android:shadowDy:垂直方向上的偏移量
* android:shadowRadius:阴影的半径
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;文字加粗可以使用android:textStyke参数<br>
#### 7 TextVuew显示文字加图片
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;设置图片相关的属性主要有以下几个<br>
* drawableBottom:在文本框内文本的底部绘制指定图像
* drawableLeft:在文本框的左部绘制指定图像
* drawableRight:在文本框的右部绘制指定图像
* drawableTop:在文本框的顶部绘制指定图像
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;以下代码在文字左边显示一张图片，并且设置文章跟图片之间的间距为10dp。<br>
```
<TextView
   android:drawableLeft="@drawable/ic_launcher"  
   android:drawablePadding="10dp"/>
```
#### 8 TextView的样式类Span的使用
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首先新建一个SpannableString对象，构造方法中传入要显示的内容，调用SpannableString的setSpan方法实现字符串各种风格的显示。setSpan方法有四个参数。参数1表示格式，可以是前景色，背景色等，我们这里用的是背景色。参数2设置格式的开始index，参数3结束index。参数4是一个常量，有以下4个值:<br>
* Spannable.SPAN_INCLUSIVE_EXCLUSIVE:前面包括，后面不包括，即在文本前插入新的文本会应用该样式，而在文本后插入新文本不会应用该样式
* Spannable.SPAN_INCLUSIVE_INCLUSIVE:前面包括，后面包括，
* Spannable.SPAN_EXCLUSIVE_INCLUSIVE:前面不包括，后面包括
* Spannable.SPAN_INCLUSIVE_EXCLUSIVE:前面包括，后面不包括
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;以下是一段使用SpannableString对象的例子<br>
```
SpannableString spannableString = new SpannableString("TextView的样式类Span的使用详解");
BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(Color.RED);
//0到10的字符设置红色背景
spannableString.setSpan(backgroundColorSpan,0,10,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
tv7.setText(spannableString);
```
#### TextView 设置点击事件Spannable
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;除了给TextView设置背景颜色之外，还可以给TextView中某一段文字设置点击效果，调用SpannableString.setSpan方法时第一次参数传入ClickableSpan格式。使用ClickableSpan时，在点击链接时凡是有要执行的动作，必须要给TextView设置MovementMethod对象<br>
```
SpannableString spannableClickString = new SpannableString("TextView设置点击事件");
ClickableSpan clickableSpan = new ClickableSpan(){
   @override
   public void onClick(View widget){
      Toast.makeText(MainActivity.this,"TextView设置点击事件Span",Toast.LENGTH_LONG).show();
   }
};
spannableClcikString.setSpan(clickableSpan,11,15,Spannable.SPAN_EXVLUSIVE_INCLUSIVE);
tv1.setMovementMethod(LinkMovementMethod.getInstance());
tv1.setText(spannableClickString);
```
#### 10 TextView设置点击背景
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;新建一个selector_textview.xml文件，放到drawable目录下。<br>
```
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://...">
   <item android:drawable="@color/textview_click_background"
      android:state_focused="true"/>
   <item android:drawable="@color/textview_click_background"
      android:state_pressed="true" />
</selector>
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在TextView的xml布局中设置背景<br>
```
android:background="@drawable/selector_textview"
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;设置点击事件<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;code satr<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//必须要给TextView加上点击事件，点击之后才能改变背景颜色<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;findViewById(R.id.tv9).setOnClickListener(new OnClickListener(){<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   @Override<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   public void onClick(View v){<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      Toast.makeText(MainActivity.this,"点击了TextView9",Toast.LENGTH_LONG).show();<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   }<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;code end<br>
#### 11 跑马灯效果
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当一行文章的内容太多时，导致无法全部显示，也不想分行演示时，可以让文本从左到右滚动显示，类似于跑马灯。<br>
```
<TextView
   android:id="@+_id/tv12"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   android:layout_margin="10dp"
   android:ellipsize="marquee"
   android:marqueeRepeatLimit="marquee forever"
   android:scrollHorizontally = "true"
   android:focusable="true"
   android:focusableInTouchMode = "true"
   android:singleLine="true"
   android:text="跑马灯效果，跑马灯效果，跑马灯效果,跑马灯效果,跑马灯效果"
/>
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 2 Button(按钮)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Button支持的XML属性及相关方法如下所示<br>
* android:clickable   setClickable(boolean clickable)   设置是否允许点击，true:允许点击 false:禁止点击
* android:background   setBackgroundResource(int resid) 通过资源文件设置背景色resid:资源xml文件ID按钮默认背景为android.R.drawable.btn_default
* android:text   setText(CharSequence text)   设置文字
* android:textColor   setTextColor(int color) 设置文字颜色
* android:onClick   setOnClickListener(OnClickListener l) 设置点击事件
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在使用Button时候需要注意以下两点:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1:Button的setOnClickListener优先级比xml中的android:onClick高，如果同时设置点击事件，只有setOnclickListener才有效<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2:能用TextView就尽量不要用Button,TextView灵活性更高<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 3 EditText(文本编辑框)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;EditText在开发中是经常用到的控件，也是一个比较重要的组件，可以说它是用户与Android应用进行数据传输的窗口，比如实现一个用户登录界面,需要用户输入账号和密码，然后获取用户输入的内容，交给服务器判断。EditText支持的xml属性及相关方法如下:<br>
* android:text   setText(CharSequence text)
* android:textColor setTextColor(int color)
* android:hint   setHint(int resid)
* android:textColorHint   void setHintTextColor(int color)
* android:inputType setInputType(int type)
* android:maxLnegth
* android:minLines   setMinLines(int minLines)
* android:gravity   setGravity(int gravity)
* android:drawableLeft      setCompoundDrawables(Drawable left,Drawable top,Drawable right,Drawable bottom)   在text的左边输出一个drawable,如图片
* android:drawablePadding   设置text与drawable的间隔
* android:digits   设置允许输入哪些字符，如:'1234567890'
* android:ellipsize 设置当文字过长时该控件该如何显示 start:省略号显示在开头，end:省略号显示在结尾， middle 省略号显示在文本中间 marquee:以跑马灯的方式显示(动画横向移动)
* android:lines setLines(int lines) 设置文本的行数，设置两行就显示两行，即使第二行没有数据。
* android:lineSpacingExtra 设置行间距
* android:sigleLine setSingleLine() true:单行显示   false:多行显示
* android:textStyle: 设置字形，可以设置为一个或多个，用"\
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可以使用addTextChangedListener()为EditText添加监听器,如下:<br>
```
EditText et1 = (EditText)findViewById(R.id.et1);
et1.addTextChangedListener(new TextWatcher(){
   @Override
   public void beforeTextChanged(charSequence s,int start,int count,int after){
   }
   @Override
   public void onTextChanged(charSequence s,int start,int before,int count){
   }
   @Override
   public void afterTextChanged(Editable s){
   }
})
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 4 ImageView(图像视图)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ImageView用于显示图片的View，是开发中频繁使用的一个控件。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用:<br>
```
<ImageView
   android:src="@mipmap/ic_launcher" />
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在java中修改图片地址<br>
```
imageView.setImageResource(R.mipmap.coffee);
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 5 RadioButton(单选按钮)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实现单选按钮需要将RadioButton和RadioGroup配合使用.<br>
```
<RadioGroup
   android:id="@+id/rediogroup"
   android:orientation="horizontal">
   <RadioButton
      android:id="@+id/rb_male"
      android:text="男"/>
   <RadioButton
      android:id="@+id/rb_girl"
      android:text="女"/>
</RadioGroup>
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;获取按钮选中的值如下:<br>
```
RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
radioGroup.setOnCheckedChangeListener(new onCheckedChangeListener(){
   @Override
   public void onCheckedChanged(RadioGroup group,int checkedId){
      if(checkedId == R.id.rb_male){
         Toast.makeText(MainActivity.this,"你的性别是男",Toast.LENGTH_SHORT).show();
      }else if(checkedId == R.id.rb_girl){
           Toast.makeText(MainActivity.this,"你的性别是女",Toast.LENGTH_SHORT).show();
      }
   }
});
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 6 Checkbox(复选框)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用:<br>
```
<CheckBox
   android:id="@+id/cb_php"
   android:text="php" />
<CheckBox
   android:id="@+id/cb_java"
   android:text="java" />
<CheckBox
   android:id="@+id/cb_c"
   android:text="c" />
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在activity中使用<br>
```
...
//获取元素
CheckBox cbJava = (CheckBox) findViewById(R.id.cb_java);
CheckBox cbPhp = (CheckBox) findViewById(R.id.cb_php);
CheckBox cbC = (CheckBox) findViewById(R.id.cb_c);
//为元素添加事件绑定
cbJava.setOnCheckedChangeListener(onCheckedChangeListener);
cbPhp.setOnCheckedChangeListener(onCheckedChangeListener);
cbC.setOnCheckedChangeListener(onCheckedChangeListener);
...
//定义onCheckedChangeListener
private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener(){
   @Override
   public void onCheckedChanged(CompoundButton buttonView,boolean isChecked){
      //通过id区分是哪个复选框的值发生了变化
      if(buttonView.getId() == R.id.cb_java){
      }else if(buttonView.getId() == R.id.cb_php){
      }else if(buttonView.getId() == R.id.cb_c)[
      }
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 7 ProgressBar(进度条)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;进度条分为水平进度条和原型进度条，其中水平进度条是确定进度的，原型进度条是不确定进度的。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;进度条的使用方式如下:<br>
```
<!--水平进度条 -->
<ProgressBar
   android:id="@+id/pb_horizontal"
   android:max="100"
   android:progress="0"
   style="?android:attr/progressBarStyleHorizontal" />
<!-- 圆形进度条 -->
<ProgressBar
   android:id="@+id/pb_large"
   style="?android:attr/progressBarStyleLarge" />
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;修改水平进度条的值如下:<br>
```
//获取进度条
pbHorizontal = (ProgressBar)findViewById(R.id.pb_horizontal);
pbHorizontal.setProgress(pbHorizontal.getProgress() + 10);
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 8 ProgressDialog(进度对话框)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ProgressDialog经常用于一些费时的操作，需要用户进行等待。例如，加载网页内，这时需要一个提示来告诉用户程序正在运行，并没有假死或者真死，而ProgressDialog就是专门干这项工作的。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一般使用它的步骤为:在执行耗时间的操作之前弹出ProgressDialog提示用户，然后开一个新线程。在新线程中执行耗时的操作，运行完毕之后通知主程序将ProgressDialog结束。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实现方式如下:<br>
```
//显示
staticDialog = ProgressDialog.show(MainActivity.this,"这是标题","这是内容");
//关闭
staticDialog.dismiss();
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 9 AlertDialog(简单对话框)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在Android开发中，经常需要在Android界面上弹出一些对话框,例如询问用户或者让用户选择(如删除提示对话框，警告对话框等),这些功能用AlertDialog对话框来实现。<br>
```
//显示对话框
protected void showDialog(){
   AlertDialog.Builder builder = new AlrtDialog.Builder(this);
   builder.setTitle("提示");   //设置标题
   builder.setMessage("确认退出吗?");     //设置消息
   builder.setIcon(R.mipmap.ic_launcher);   //设置icon
   builder.setPositiveButton("确认",new DialogInterface.OnClickListener(){
      @Override
      public void onClick(DialogInterface dialog,int which){
         dialog.dismiss();
         MainActivity.this.finish();   //结束当前Activity
         }
     });
   builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
      @Override
      public void onClick(DialogInterace dialog,int which){
         dialog.dismiss();
      }
   });
   builder.create().show();
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 10 PopupWindow(弹出式窗口)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PopupWindow弹出一个浮动的窗口，可以显示在屏幕任意的位置，比Dialog对话框更加灵活(默认只能在屏幕的中间)。我们还可以通过setAnimationStyle方法设置PopupWindow的显示或隐藏动画。<br>
#### 在元素的下方显示一个PopupWindow
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;xml文件<br>
```
<Button
   android:id="@+id/btn_show_popupwindow"
   android:text="在当前位置下面弹出PopupWindow" />
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PopupWindow xml文件<br>
```
<TextView
   android:text="我是点击上面的按钮弹出的哦"/>
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;activity<br>
```
private void showAsDropDown(){
   View popView = LayoutInflater.from(this).inflate(R.layout.popup_drop_down,null);
   //设置PopupWindow View，宽度，高度
   PopupWindow popupWindow = new PopupWindow(popView,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.lAYOUTparams.WRAP_CONTENT);
   //设置允许在外点击消失，必须要给popupWindow设置背景才会生效
   popupWindow.setOutsideTouchable(true);
   popupWindow.setBackgroundDrawable(new BitmapDrawable());
   //显示在btnShowPopupwindow按钮下面,x位置偏移100px  
   popupWindow.showAsDropDown(btnShowPopupwindow,100,0);
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
#### 将popupWindow显示在指定位置,从下往上弹出
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;activity<br>
```
private void showBottomPopupwindow(){
   View popView = LayoutInflater.from(this).inflate(R.layout.popup_bottom,null);
   final PopupWindow popupWindow = new PopupWindow(popView,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
   popupWindow.setOutsideTouchable(true);   //设置允许在外点击消失
   popupWindow.setBackfroundDrawable(new ColorDrawable(0x30000000));   //设置背景颜色
   popupWindow.setAnimationStyle(R.style.Animation_Bottom_Dialog); //设置动画
   //在View上绑定事件
   View.OnClickListener onClickListener = new View.OnClickListener(){
      @Override
      public void onClick(View v){
         if(v.getId() == R.id.btn_creame_album){
         //...
         }else if(v.getId() == R.id.btn_creame_cancel){
         //...
         }
      }
   }
   popView.findViewById(R.id.btn_camera_album).setOnClickListener(onClickListener);
popView.findViewById(R.id.btn_camera_cancel).setOnClickListener(onClickListener);
//设置显示在视图底部
popupWindow.shoeAtLocation(getWindow().getDecorView(),Gravity.BOTTOM,0,0);
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;底部PopupWindow显示的布局文件popup_bottom.xml文件如下:<br>
```
<LinearLayout
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   android:orientation="vertical">
   <Button
      android:id="@+id/btn_camera_album"
      android:text="拍照"/>
   <Button
      android:id="@+id/btn_camera_cancel"
      android:text="取消"/>
</LinearLayout>
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在上面的方法中，还通过setAnimation方法设置了动画，这是因为在style.xml增加了一个stylke，android:windowEnterAnimation是PopupWindow显示动画,android:windowExitAnimation是PopupWindow消失动画<br>
```
<style name="Animation_Bottom_Dialog">
   <item name="android:windowEnterAnimation">@anim/bottom_dialog_enter</item>
   <item name="android:windowExitAnimation">@anim/bottom_dialog_exit</item>
</style>
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在上面的style中引用了res/anim下的两个动画文件<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[bottom_dialog_enter.xml]<br>
```
<?xml version="1.0" encoding="utf-8"?>
<translate xmlns:android="..."
   android:duration="225"
   android:fromYDelta="100%"
   android:interpolator="@android:anim/delerate_interpolator"
   android:toYDelta="0%" />
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上面的xml文件各个属性的作用如下:<br>
* android:duration:动画的运行时间，毫秒为单位
* android:fromYDelta:动画起始时，Y坐标上的位置
* android:toYDelta:动画结束时，Y坐标上的位置
* android:interpolator:用来修饰动画效果，定义动画的变化率，可以使动画效果accelerated(加速)，decelerated(减速)，repeated(重复),bounced(弹跳)等。
* <br>
* #11 DialogFragment
* DialogFragment是在Android3.0版本中本引入的，是一种基于Fragment的Dialog，可以用来创建基本对话框，警告对话框，以替代Dialog。
* DialogFragment的实现以后会写一篇文章介绍一下:这篇文章就先不介绍了。因为写太久了,累了。
