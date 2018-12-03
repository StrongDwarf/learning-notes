# Android开发中重写ViewGroup
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一般来说,开发Android应用程序的UI界面都不会直接使用ViewGroup，而是使用它的派生类。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ViewGroup派生出的直接子类有CoordinatorLayout,DrawerLayout,RecyclerView,FramwLayout,linearLayout,RelativeLayout,SwipeRefreshLayout,ViewPager等<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ViewGroup派生出的间接子类有GridView,ListView,WebView等<br>
## 1 自定义ViewGroup
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;自定义ViewGroup跟自定义View有点类似。一般情况下自定义ViewGroup会重写以下几个方法:<br>
* onMeasure 测量子View的宽高，根据子View的宽高与自己的测量模式来决定自己的宽高。
* onLayout 决定子View的摆放位置
* generateLayoutParams根据子View的布局参数决定子View在当前容器的摆放位置，这个方法根据需求决定要不要重写。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如下,是几个实现常用布局的例子<br>
#### 实现LinearLayout布局的水平排列效果
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实现这种效果只需要重写onLayout和onMeasure方法，这里新建一个类MyViewGroup，继承自ViewGroup。<br>
```
public class MyViewGroup   extends ViewGroup{
   public MyViewGroup(Context context){
      super(context);
   }
   public MyViewGroup(Context context,AttributeASet attrs){
   super(context,attrs)
   }
<br>
   @override
   protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
      //获取ViewGroup宽高
      int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
      int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
      //测量所有子View的宽高
      measureChildren(widthMeasureSpec,heightMeasureSpec);
      setMeasuredDimension(sizeWidth,sizeHeight);
   }
<br>
   @override
   protectded void onLayout(boolean changed,int l,int t,int r,int b){
      int count = getChildCount();   //获取子View的数量
      int left = 0;
      for(int i =0;i&lt;count;i++){
         View child=getChildAt(i);
         int childWidth = child.getMeasuredWidth();   //获取子View宽度
         int childHeight = child.getMeasuredHeight();   //获取子View高度
         child.layout(left,0,left+childWidth,childHeight);   //设置摆放位置
         left += childWidth;
      }
   }
}
```
#### 测量ViewGroup宽高
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;和重写View时一样，如果不重新测量ViewGroup的宽高，其默认宽高也将使用match_parent。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;所以我们需要重写onMeasure方法,写法如下:<br>
```
@Override
protected void onMeasure(int WidthMeasureSpec,int heightMeasureSpec){
   //获得此ViewGroup计算模式
   int widthMode = MeasureSpec.getMode(widthMeasureSpec);
   int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    
   //获取Viewgroup宽高
   int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
   int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
   //测量所有子View的宽高
   measureChildren(widthMeasureSpec,heightMeasureSpec);    
   if(getChildCount()&lt;=0){
      //如果没有子View 当前ViewGroup的宽高直接设置为0
      setMeasuredDimension(0,0)
   }else if(heightMode == MeasureSpec.AT_MOST &amp;&amp; widthMode == MeasureSpec.AT_MOST){
      int measuredWidth = 0; //测量宽度
      int maxMeasuredHeight=0; //测量子View最大的高度
      for(int i=0;i&lt;getChildCount();i++){
         View child = getChildAt(i);
         measuredWidth += child.getMeasuredWidth();
         if(child.getMeasuredHeight() &gt; maxMeasuredHeight){
            //如果当前的View大于之后的View的高
            maxMeasuredHeight = child.getMeasuredHeight();
         }
        setMeasuredDimension(measuredWidth,maxMeasuredHeight);
   }else{
      setMeasuredDimension(sizeWidth,sizeHeight);
   }
}
```
