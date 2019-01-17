# Android动态添加和删除控件

在Android开发中,我们经常需要根据后端返回的数据动态渲染不同的控件。这个时候就需要用到以下方法。

## 动态添加控件

android 中提供了以下方法在viewGroup控件中添加view子控件。

``` java
viewGroup.addView(View child,LayoutParams params) :在viewGroup中添加view,并设置器LayoutParams
viewGroup.addView(View child) :添加view
viewGroup.addView(View child,int index) :指定index位置添加
viewGroup.addView(View child,int index,LayoutParams params) :在指定位置添加view,并设置其LayoutParams
```

其中LayoutParams用于设置View的宽高属性,需要使用LinerLayout.LayoutParams(width,height)创建,如下:

``` java
linearLayout.addView(imageView,new LinearLayout.LayoutParams(100,100));
```

new LinearLayout.LayoutParams()中可以传入两个参数,第一个参数是子控件的宽度,第二个参数是子控件的高度。两者可以取值如下:

 * ViewGroup.LayoutParams.WRAP_CONTENT : 等同于在xml文件中为对应布局高度或宽度设置 "wrap_content"
 * ViewGroup.LayoutParams.MATCH_PARENT : 等同于在xml文件中为对应布局高度或宽度设置 "match_parent"
 * ViewGroup.LayoutParams.FILL_PARENT : 等同于在xml文件中为对应布局高度或宽度设置 "fill_parent"
 * int 类型数字,如100，  当传入int类型的值时,该子控件的高度是固定的,为100px <strong>注意单位是px</strong>

设置new LinearLayout.LayoutParams()时,传入其他类型的时候都没有什么问题,但是如果要在使用int参数的时候,因为我们编写xml布局时一般使用的是dp单位,为了统一单位,建议先写一个转换器将dp单位转为px。如下:

``` java
//输入dp,输出px
private int getPixelsFromDp(int size){
    DisplayMetrics metrics =new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(metrics);
    return(size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
}
```

## 动态删除控件

android提供了如下一些方法动态删除控件

``` java
viewGroup.removeAllViews()  :移除所有的子控件
viewGroup.removeAllViewsInLayout() :移除自身布局中计算了的子view
viewGroup.removeView(View view) : 移除指定子控件
viewGroup.removeViewAt(int index) : 移除指定index的控件
viewGroup.removeViews(int start,int end) : 移除指定下标之间的控件
viewGroup.removeViewsInLayout(int start,int end) :移除自身布局中已计算了的子view
```

这些方法的使用都很简单,直接用就行,值得注意的是需要注意区分removeAllViews()和removeAllViewsInLayout()的区别。这里我们可以先看看两者的源代码

### removeAllViews()和removeAllViewsInLayout()源码分析

下面是removeAllViewsInLayout()的源码

``` java
/**
     * Called by a ViewGroup subclass to remove child views from itself,
     * when it must first know its size on screen before it can calculate how many
     * child views it will render. An example is a Gallery or a ListView, which
     * may "have" 50 children, but actually only render the number of children
     * that can currently fit inside the object on screen. Do not call
     * this method unless you are extending ViewGroup and understand the
     * view measuring and layout pipeline.
     */
    public void removeAllViewsInLayout() {
        final int count = mChildrenCount;
        if (count <= 0) {
            return;
        }
 
        final View[] children = mChildren;
        mChildrenCount = 0;
 
        final View focused = mFocused;
        final boolean detach = mAttachInfo != null;
        View clearChildFocus = null;
 
        needGlobalAttributesUpdate(false);
 
        for (int i = count - 1; i >= 0; i--) {
            final View view = children[i];
 
            if (mTransition != null) {
                mTransition.removeChild(this, view);
            }
 
            if (view == focused) {
                view.clearFocusForRemoval();
                clearChildFocus = view;
            }
 
            if (view.getAnimation() != null ||
                    (mTransitioningViews != null && mTransitioningViews.contains(view))) {
                addDisappearingView(view);
            } else if (detach) {
               view.dispatchDetachedFromWindow();
            }
 
            onViewRemoved(view);
 
            view.mParent = null;
            children[i] = null;
        }
 
        if (clearChildFocus != null) {
            clearChildFocus(clearChildFocus);
        }
    }
```

方法removeAllViews()的源码

``` java
/**
    * Call this method to remove all child views from the
    * ViewGroup.
    */
    public void removeAllViews() {
        removeAllViewsInLayout();
        requestLayout();
        invalidate(true);
    }
```

对removeAllViewInLayout():  
由ViewGroup子类调用，以从自身中移除子视图，此时必须首先知道其在屏幕上的大小，然后才能计算将呈现多少子视图。例如，a Gallery or a ListView，它可能“有”50个子项，但实际上只呈现当前可在屏幕上的对象内容纳的子项的数目。不要调用此方法，除非您正在扩展视图组并了解视图测量和布局管道。  
removeAllViewInLayout()删除的只会删除其在onMeasure阶段参与宽高计算的子控件。
    
对removeAllView():  
removeAllViews() 也调用了removeAllViewsInLayout(), 但是后面还调用了requestLayout(),这个方法是当View的布局发生改变会调用它来更新当前视图, 移除子View会更加彻底. 所以除非必要, 还是推荐使用removeAllViews()这个方法

关于这两个方法的建议是,一般使用removeAllViews()方法就足够应付业务场景了。没什么必要不需要使用removeAllViewsInLayout()方法。

## DEMO

下面是一个动态添加和删除的简单DEMO,供参考,DEMO代码地址:["DEMO"](https://github.com/StrongDwarf/Demo/tree/46af116530c9930c2a6fd8e7f7fd0ae6e17dfaf3)

##### 动态添加前

!["动态添加前"](https://github.com/StrongDwarf/learning-notes/blob/master/public/img/111CAD69-81FD-431D-A28B-E280BB1CA68E.png?raw=true "动态添加前的图片")

##### 动态添加后

!["动态添加后"](https://github.com/StrongDwarf/learning-notes/blob/master/public/img/A216EC387CBFB76ECD66F8C214295D84.jpg?raw=true "动态添加后的图片")


activity_main.xml

``` xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="动态添加组件示例"
        android:id="@+id/textview"/>
    <HorizontalScrollView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="20dp"
        android:scrollbars="none"
        android:paddingBottom="30dp"
        >
        <LinearLayout
            android:id="@+id/lasted_activity"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <LinearLayout
                android:layout_width="190dp"
                android:layout_height="wrap_content"
                android:orientation="vertical"
                android:layout_marginRight="20dp">
                <ImageView
                    android:layout_width="190dp"
                    android:layout_height="110dp"
                    android:background="#f2f2f2"/>
                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:paddingTop="4dp"
                    android:text="自驾|穿越湖泊和中士"/>
            </LinearLayout>
        </LinearLayout>
    </HorizontalScrollView>
</LinearLayout>
``` 

MainActivity.java

``` java
package com.xiaobaicai.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int TV1 = 1;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.lasted_activity);
        linearLayout.removeAllViews();

        LinearLayout linearLayout1 = new LinearLayout(this);
        ImageView imageView = new ImageView(this);
        TextView textView = new TextView(this);


        linearLayout1.setOrientation(LinearLayout.VERTICAL);
        imageView.setBackgroundColor(getResources().getColor(R.color.image_background));
        linearLayout1.addView(imageView,new LinearLayout.LayoutParams(getPixelsFromDp(190),
                getPixelsFromDp(110)));


        textView.setTextColor(getResources().getColor(R.color.text_light));
        textView.setTextSize(14);
        textView.setPadding(getPixelsFromDp(0),getPixelsFromDp(4),getPixelsFromDp(0),getPixelsFromDp(0));
        textView.setText("动态添加的元素");
        linearLayout1.addView(textView,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(linearLayout1,new LinearLayout.LayoutParams(getPixelsFromDp(190),ViewGroup.LayoutParams.WRAP_CONTENT));


    }

    //输入dp,输出px
    private int getPixelsFromDp(int size){

        DisplayMetrics metrics =new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return(size * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;

    }

}
```

##### 文章参考

 * [ViewGroup中removeAllViews()和removeAllViewsInLayout()之间的区别](https://blog.csdn.net/stzy00/article/details/43966149)
 * [LayoutParams — 设置参数dp值（获取了当前手机的手机密度信息）](http://blog.sina.com.cn/s/blog_90309ae10102wgyz.html)
 * [android中LayoutParams设置参数的理解](https://www.cnblogs.com/hubing/p/5104110.html)
 * [Android动态设置控件大小以及设定margin以及padding值](https://www.aliyun.com/jiaocheng/42518.html)