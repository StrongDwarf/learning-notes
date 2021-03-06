# android属性动画总结
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;android中的动画类型有两种:一种是视图动画,一种是属性动画。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本篇文章中要介绍的就是属性动画<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 1 属性动画出现的原因
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实现动画效果在android开发中是非常常见的需求，因此android一开始就提供了视图(view)动画:逐帧动画和补间动画。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;然而视图动画却存在以下缺点:<br>
* 1:作用域局限于view,即智能对继承自view的组件进行动画操作,但无法对非View的对象进行动画操作
* 2:没有改变view的属性,只是改变视觉效果.如通过补间动画将按钮从左上角移动到屏幕的右下角。实际上按钮还是在左上角,点击左下角的按钮是不生效的
* 3:动画效果单一:补间动画只能实现平移,旋转,缩放,透明度这些简单的动画需求,一旦遇到相对复杂的动画效果,补间动画就无法生效
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 2 简介
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;属性动画的作用对象是任意java对象,不再局限于视图View对象。同时可以实现各种自定义动画效果。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;属性动画的工作原理是通过在一定时间间隔内，通过不断对值进行改变,并不断将该值赋给对象的属性，从而实现该对象在该属性上的动画效果。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在实现属性动画的过程中，我们一般利用ValueAnimator类和ObjectAnimator类。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 3 使用ValueAnimator类实现动画
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ValueAnimator类通过不断控制值的变化，再不断手动赋给对象的属性，从而实现动画效果。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ValueAnimator类有3个重要方法:<br>
* ValueAnimator.ofInt(int values)
* ValueAnimator.ofFloat(float values)
* ValueAnimator.ofObject(int values)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 3.1 ValueAnimator.ofInt(int values)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作用:将初始值以整数值的形式过渡到结束值<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可以通过java代码在activity中设置, 也可以通过在xml文件中设置，然后java代码调用<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如下是一个使用ValueAnimator.ofInt(int values)实现动画的例子.<br>
#### java代码设置
```
//获取元素
Button button = (Button)findViewById(R.id.button);
//设置属性数值的初始值和结束值
ValueAnimator valuenimator = ValueAnimator.ofInt(mButton.getLayoutParams().width,500);
//设置动画播放的各种属性
valueAnimator.setDuration(2000);
//将属性数值手动赋值给对象的属性:此处是将值赋给按钮的宽度
valueAnimator.addUpdateListener(new AnimatorUpdateListener(){
   @Override
   public void onAnimationUpdate(ValueAnimator animator){
      int currentValue = (Integer)animator.getAnimatedValue();
      System.out.println(currentValue);
      mButton.getLayoutParams().width=currentValue;
      //重新绘制view
      mButton.requestLayout();
   }
});
//启动动画
valueAnimator.start();
}
```
#### xml文件中设置,java调用
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;set_animation.xml文件<br>
```
<animator xmlns:android="http://schemas.android.com/apk/res/android"
   android:valueFrom="0"
   android:valueTo="100"
   android:valueType="intType" />
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;java中调用代码<br>
```
//载入动画
Animator animator = AnimatorInflater.loadAnimator(this,R.animator.set_animation);
//设置动画对象
animator.setTarget(view);
//启动动画
animator.start();
```
##### 效果图
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 3.2 ValueAnimator.ofFloat(float values)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作用:将初始值以浮点型数值的形式过渡到结束值<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ValueAnimator.ofFloat(float values)函数的功能和ValueAnimator.ofInt(int values)函数的使用几乎一样，仅仅是估值器上的区别:<br>
* ValueAnimator.ofFloat()采用默认的浮点型估值器,（FloatEvaluator）
* ValueAnimator.ofInt()采用默认的整数股指期,(IntEvaluator)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 3.3 ValueAnimator.ofObject()
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作用:将初始值以对象的形式过渡到结束值,即通过操作对象实现动画效果<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其使用如下:<br>
```
//创建初始对象和结束对象
myObject object1 = new myObject();
myObject object2 = new myObject();
<br>
//创建动画对象
//参数1：自定义的估值器对象
//参数2：初始动画的对象
//参数3：结束动画的对象
ValueAmimator anim = ValueAnimator.ofObject(new myObjectEvaluator(),object1,object2);
anim.setDuration(5000);
anim.start();
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
#### 估值器介绍
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作用:设置动画如何从初始值 过渡到结束值的逻辑<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;对一个动画而言:存在插值器和估值器：其中插值器决定值的变化模式,估值器决定值的具体变化数值。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;就上一节中,我们使用ValueAnimator.ofFloat(int value)函数实现了一个动画。其中的估值器是系统默认实现的:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如下<br>
```
public class FloatEvaluator implements TypeEvaluator{
   //重写evaluate()
   public Object evaluate(float fraction,Object startValue,Object endValue){
      //参数说明
      //fraction:表示动画完成度
      //startValue,endValue:动画的初始值和结束值
      float startFloat = (Number)startValue).floatValue();
      return startFloat+fraction*((Number)endValue).floatValue() - startFloat);
   }
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ValueAnimator.ofInt() 和 ValueAnimator.ofFloat()都具备系统内置的估值器，即FloatEvaluator，IntEvaluator<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;但对于ValueAnimator.ofObject()而言,并没有内置的估值器，因为对对象的操作复杂多样,系统无法知道如何欧尼初始对象过渡到结束对象。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如下是一个实现一个圆从一个点移动到另一个点的例子<br>
##### 步骤1：定义对象类
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因为ValueAnimator.ofObject()是面向对象操作的，所以需要自定义对象类，本例需要操作的对象是圆的点坐标.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Point.java<br>
```
public class Point{
   private float x;
   private float y;
<br>
   public Point(float x, float y){
      this.x = x;
      this.y = y;
   }
   public float getX(){
      return x;
   }
   public float getY(){
      return y;
   }
}
```
##### 步骤2：根据需求实现TypeEvaluator接口
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本例中实现的需求是从一个点坐标移动到另一个点坐标<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;PointEvaluator.java<br>
```
//实现TypeEvaluator接口
public class PointEvaluator implement TypeEvaluator{
   //在evaluate()里写入对象动画过渡逻辑
   @Override
   public Object evaluate(float fraction,Object startValue,Object endValue){
      Point startPoint = (Point)startValue;
      Point endPoint = (Point)endValue;
<br>
      float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
      float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
<br>
      //将计算后的坐标封装到一个新的Point对象中并返回
      Point point = new Point(x,y);
      return point;
   }
}
```
##### 步骤3：将属性动画作用到自定义View当中
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MyView.java<br>
```
public class MyView extends View{
   //设置需要用到的变量
   public static final float RADIUS = 70f; //圆的半径
   private Point currentPoint;   //当前点坐标
   private Paint mPaint;   //绘图画笔
    
   //构造方法(初始化画笔)
   public MyView(Context context,AttributeSet attrs){
      super(context,attrs);
      //初始化画笔
      mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
      mPaint.setColor(Color.BLUE);
   }
<br>
   //重写onDraw()从而实现绘制逻辑
   //绘制逻辑：先在初始点画圆，通过监听当前坐标值(currentPoint)的变化，每次变化都调用那个onDraw()重新绘制圆，从而实现圆的平移动画效果
   @Override
   protected void onDraw(Canvas canvas){
      //如果当前坐标为空(即第一次)
      if(currentpoint == null){
         currentPoint = new Point(RADIUS,RADIUS);
         //创建一个点对象(坐标是(70,70))
         //在该点画一个圆:圆心=(70,70),半径=70
         canvas,drawCircle(x,y,RADIUS,mPaint);
<br>
         //将属性动画作用到View中
         Point startPoint = new Point(RADIUS,RADIUS);  
         Point endPoint = new Point(700,1000);
         ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(),startPoint,endPoint);
         anim.setDuration(5000);
         anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
               currentPoint = (Point)animation.getAnimatedValue();
               invalidate();
            }
         });
      }else{
         float x = currentPoint.getX();
         float y = currentPoint.getY();
         canvas.drawCircle(x,y,RADIUS,mPaint);
      }
   }
}
```
##### 步骤4：在布局文件中加入自定义View空间
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;activity_main.xml<br>
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   tools:context="com.xiaobaicai.demo.MainActivity">
   <com.xiaobaicai.demo.MyView
      android:layout_width="match_parent"
      android:layout_height="match_parent
      />
</RelativeLayout>
```
##### 步骤5：在主代码文件中显示视图
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MainActivity.java<br>
```
public class MainActivity extends AppCompatActivity{
   @Override
   protected void onCreate(Bundle savedInstanceState){
      super.onCreate(savedInstanceStaet);
      setContentView(R.layout。activity_main);
   }
}
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
##### 效果图
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 4 ObjectAnimator类
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ObjectAnimator类实现动画的原理是通过直接对对象的属性值进行改变操作，从而实现动画效果<br>
* 如直接改变View的alpha属性，从而实现透明度的动画效果
* 继承自ValueAnimator类,即底层的动画实现机制是基于ValueAnimator类
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 4.1 具体使用,
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ObjectAnimator类的使用也分xml设置和java设置<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如对于实现以个透明度变化效果. 有如下两种方式实现<br>
##### java设置
```
Button button = (Button)findViewById(R.id.button);
ObjectAnimator animator = ObjectAnimator.ofFloat(mButton,"alpha",1f,0f,1f);
animator.setDuration(5000);
animator.start();
```
##### XML设置
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;set_animation.xml<br>
```
<objectAnimator xmlns:android="http://scheams.android.com/apk/res/android"
   android:valueFrom="1"
   android:valueTo="0"
   android:valueType="floatType"
   android:propertyName="alpha" />
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在java代码中启动动画<br>
```
Animator animator = AnimatorInflater.loadAnimator(context,R.animator.set_animation.xml);
animator.setTarget(view);
animator.start();
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 4.2 内置属性动画
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在ObjectAnimator.ofFloat()的第二个参数String property可以传入任意属性值，但是系统内置的属性值只有如下几种:<br>
* Alpha 控制View的透明度    
* TranslationX   控制X方向的位移
* TranslationY   控制Y方向的位移
* ScaleX   控制X方向的缩放倍数
* ScaleY   控制Y防线的缩放倍数
* Rotation   控制以屏幕方向性为轴的旋转度数
* RotationX   控制以X轴为轴的旋转度数
* RotationY   控制以Y轴为轴的旋转度数
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 4.3 通过自定义对象属性实现动画效果
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首先分析ObjectAnimator内置属性动画的实现原理<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ObjectAnimator类对对象属性值进行改变从而实现动画效果的本质是:通过不断控制值的变化,再不断自动赋给对象的属性，从而实现动画效果<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;而自动赋给对象的属性的本质是调用该对象属性的set()和get()方法进行赋值<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所以,ObjectAnimator.ofFloat(Object object,String property,float ...values)第二个参数传入值的作用是:让ObjectAnimator类根据传入的属性名去寻找该对象对应属性名的set()和get()方法，从而进行对象属性值的赋值,如上面的例子:<br>
```
ObjectAnimator animator = ObjectAnimator.ofFloat(button,"alpha",0f,1f,0f);
//其实button对象中并没有alpha这个属性值
//ObjectAnimator并不是直接对我们传入的属性名进行操作
//而是根据传入的属性值"rotation"去寻找对象对应属性名对应的get和set方法，从而通过set()和get()对属性进行赋值
//因为button对象中有rotation属性所对应的get&amp;set方法
//所以传入的rotation属性是有效的
//所以才能对rotation这个属性进行操作赋值
public void setRotation(float value);
public float getRotation();
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在自定义属性动画时,本质上,就是:<br>
* 为对象设置需要操作属性的set()&amp;get()方法
* 通过实现TypeEvaluator类从而定义属性变化的逻辑
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;下面是一个通过自定义属性实现圆的颜色渐变的动画效果<br>
##### 步骤1:设置对象类属性的set()&amp;get()方法
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;设置对象类属性的set()&amp;get()有两种方法:<br>
* 1,通过继承原始类,直接给类加上该属性的get()&amp;set()，从而实现给对象加上该属性的get()&amp;set()
* 2,通过包装原始动画对象,间接给对象加上该属性的get()&amp;set()。即用一个类来包装原始对象
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;此处使用第一种方式进行展示.<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MyView2.java<br>
```
public class MyView2 extends view{
   //设置需要用到的变量
   private Paint mPaint;   //绘图画笔
   private String color;   //设置背景颜色属性
   public String getColor(){
      return color;
   }
   public void setColor(String color){
      this.color = color;
      mPaint.setColor(color.parseColor(color));
      //将画笔的颜色设置成方法参数传入的颜色
      //调用了invalidate()方法,即画笔颜色每次改变都会刷新视图,然后调用onDraw()方法重新绘制圆
      invalidate();
   }
<br>
   //构造方法
   public MyView2(Context context,AttributeSet attrs){
      super(context,attrs);
      mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
      mPaint.setColor(color.BLUE);
   }
<br>
   //@Override
   protected void onDraw(Canvas canvas){
      canvas.drawCircle(500,500,RADIUS,mPaint);    
   }
}
```
##### 步骤2：在布局文件中加入自定义View控件
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;activity_main.xml<br>
```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
   android:layout_width="match_parent"
   android:layout_height="match_height">
   <com.xiaobaicai.demo.MyView2
      android:id="@+id/MyView2"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
   />
</RelativeLayout>
```
##### 步骤3：根据需求实现TypeEvaluator接口
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
## 5 额外的使用方法
### 5.1 组合动画(AnimatorSet类)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AnimatorSet类的方法:<br>
* AnimatorSet.play(Animator anim):播放当前动画
* AnimatorSet.after(long delay):将现有动画延迟x毫秒后执行
* AnimatorSet.with(Animator anim):将现有动画和传入的动画同时执行
* AnimatorSet.after(Animator anim):将现有动画插入到传入的动画之后执行
* AnimatorSet.before(Animator anim):将现有动画插入到传入的动画之前执行
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AnimatorSet类同样有java代码设置和XML设置两种方式<br>
##### Java代码设置
```
//步骤1:设置需要组合的动画效果
ObjectAnimator translation = ObjectAnimator.ofFloat(button,"translationX",curTranslationX,300,curTranslationX);
ObjectAnimator rotate = ObjectAnimator.ofFloat(button,"rotation",0f,360f);
ObjectAnimator alpha = ObjectAnimator.ofFloat(button,"alpha",1f,0f,1f);
//透明度动画
//步骤2:创建组合动画的对象
AnimatorSet animSet = new AnimatorSet();
//步骤3:根据需求组合动画
animSet.play(translation).with(rotate).before(alpha);
animSet.setDuration(5000);
//步骤4:启动动画
animSet.start();
```
##### xml设置 java调用
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;set_animation.xml<br>
```
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:ordering="sequentially" >
    // 表示Set集合内的动画按顺序进行
    // ordering的属性值:sequentially & together
    // sequentially:表示set中的动画，按照先后顺序逐步进行（a 完成之后进行 b ）
    // together:表示set中的动画，在同一时间同时进行,为默认值

    <set android:ordering="together" >
        // 下面的动画同时进行
        <objectAnimator
            android:duration="2000"
            android:propertyName="translationX"
            android:valueFrom="0"
            android:valueTo="300"
            android:valueType="floatType" >
        </objectAnimator>
        
        <objectAnimator
            android:duration="3000"
            android:propertyName="rotation"
            android:valueFrom="0"
            android:valueTo="360"
            android:valueType="floatType" >
        </objectAnimator>
    </set>

        <set android:ordering="sequentially" >
            // 下面的动画按序进行
            <objectAnimator
                android:duration="1500"
                android:propertyName="alpha"
                android:valueFrom="1"
                android:valueTo="0"
                android:valueType="floatType" >
            </objectAnimator>
            <objectAnimator
                android:duration="1500"
                android:propertyName="alpha"
                android:valueFrom="0"
                android:valueTo="1"
                android:valueType="floatType" >
            </objectAnimator>
        </set>

</set>
```
java代码调用<br>
```
mButton = (Button) findViewById(R.id.Button);
        // 创建动画作用对象：此处以Button为例

        AnimatorSet animator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.set_animation);
// 创建组合动画对象  &  加载XML动画
        animator.setTarget(mButton);
        // 设置动画作用对象
        animator.start();
        // 启动动画
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 5.2 ViewPropertyAnimator用法
```
// 使用解析
        View.animate().xxx().xxx();
        // ViewPropertyAnimator的功能建立在animate()上
        // 调用animate()方法返回值是一个ViewPropertyAnimator对象,之后的调用的所有方法都是通过该实例完成
        // 调用该实例的各种方法来实现动画效果
        // ViewPropertyAnimator所有接口方法都使用连缀语法来设计，每个方法的返回值都是它自身的实例
        // 因此调用完一个方法后可直接连缀调用另一方法,即可通过一行代码就完成所有动画效果
        
// 以下是例子
        mButton = (Button) findViewById(R.id.Button);
        // 创建动画作用对象：此处以Button为例

        mButton.animate().alpha(0f);
        // 单个动画设置:将按钮变成透明状态 
        mButton.animate().alpha(0f).setDuration(5000).setInterpolator(new BounceInterpolator());
        // 单个动画效果设置 & 参数设置 
        mButton.animate().alpha(0f).x(500).y(500);
        // 组合动画:将按钮变成透明状态再移动到(500,500)处
        
        // 特别注意:
        // 动画自动启动,无需调用start()方法.因为新的接口中使用了隐式启动动画的功能，只要我们将动画定义完成后，动画就会自动启动
        // 该机制对于组合动画也同样有效，只要不断地连缀新的方法，那么动画就不会立刻执行，等到所有在ViewPropertyAnimator上设置的方法都执行完毕后，动画就会自动启动
        // 如果不想使用这一默认机制，也可以显式地调用start()方法来启动动画
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 5.3 监听动画
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Animation类通过监听动画开始/结束/重复/取消时刻来进行一系列操作,如跳转页面等。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;设置方法如下:<br>
```
Animation.addListener(new AnimatorListener() {
          @Override
          public void onAnimationStart(Animation animation) {
              //动画开始时执行
          }
      
           @Override
          public void onAnimationRepeat(Animation animation) {
              //动画重复时执行
          }

         @Override
          public void onAnimationCancel()(Animation animation) {
              //动画取消时执行
          }
    
          @Override
          public void onAnimationEnd(Animation animation) {
              //动画结束时执行
          }
      });

// 特别注意：每次监听必须4个方法都重写。
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
##### 动画适配器
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;使用动画适配器只需要实现自需要的接口就行,<br>
```
anim.addListener(new AnimatorListenerAdapter() {  
// 向addListener()方法中传入适配器对象AnimatorListenerAdapter()
// 由于AnimatorListenerAdapter中已经实现好每个接口
// 所以这里不实现全部方法也不会报错
    @Override  
    public void onAnimationStart(Animator animation) {  
    // 如想只想监听动画开始时刻，就只需要单独重写该方法就可以
    }  
});
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>

##### 文章参考
Android属性动画:Android 属性动画：这是一篇很详细的 属性动画 总结&攻略 https://www.jianshu.com/p/2412d00a0ce4
Android属性动画完全解析(下)，Interpolator和ViewPropertyAnimator的用法
https://blog.csdn.net/guolin_blog/article/details/44171115

