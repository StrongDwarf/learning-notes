# Android权限管理

今天在写文件操作工具类时,发现跑文件读写demo始终跑不起来,后查阅资料后发现,文件读写权限不但需要在AndroidManifest.xml文件中声明,而且还需要向用户申请。整理查阅的资料的内容如下。

 * [Android中应用运行环境](#Android中应用运行环境)
 * [Android中权限分类及区别](#Android中权限分类及区别)
 * [如何申请权限](#如何申请权限)
 * [申请权限demo](#申请权限demo)
  * [申请一个权限](#申请一个权限)
  * [同时申请多个权限](#同时申请多个权限)

## Android中应用运行环境

Android安全架构的中心设计点是:在默认情况下任何应用都没有权限执行对其他应用,操作系统或用户有不利影响的任何操作。这包括读取或写入用户的私有数据(例如联系人或电子邮件),读取或写入其他应用程序的文件,执行网络访问,使设备保持唤醒状态等
由于每个Android应用都是在进程沙盒中运行,因此应用必须显示共享资源和数据。它们的方法是需要哪些权限来获取基本沙盒未提供的额外功能。应用以静态方式声明它们需要的权限，然后Android系统提示用户同意
应用沙河不依赖用于开发应用的技术。特别是，Dalvok VM不是安全边界，任何应用都可运行原生代码。

## Android中权限分类及区别

Android中的权限基本上可以分为两类,

 * 正常权限:涵盖应用需要访问其沙河外部数据或资源，但对用户隐私或其他应用操作风险很小的区域,此类权限如:网络访问,WIFI设置,音量设置等
 * 危险权限:涵盖应用需要涉及用于隐私信息的数据或资源，或者核能对用户存储的数据或其他应用的操作产生影响的区域,此类权限如:读取通讯录,读写存储器数据,获取用户位置等。

## 如何申请权限

对于正常的权限,可以在AndroidManifest.xml中通过uses-permission声明,声明后即可使用。此类权限会在应用程序安装时告知用户。
对于危险权限,不但需要在AndroidManifest.xml中声明,而且还需要用户明确授予,如果设备运行的是Android 6.0(API级别23)或更高版本,并且应用的targetSdkVersion是23或更高版本,则应用在运行时向用户请求权限。用户可随时调用权限，因此用户在每次运行时均需检查自身是否具备所需的权限 如果设备运行的是Andorid 5.1(API级别22)或更低版本,并且应用的targetSdkVersion是22或更低版本,则系统会在用户安装应用时要求用户授予权限。如果将新权限添加到更新的应用版本,系统会在用户更新应用时要求授予该权限。用户一旦安装应用，它们撤销权限的唯一方式就是卸载应用。

## 申请权限demo

demo地址:[https://github.com/StrongDwarf/Demo/tree/9c27dd5599f20d2f868d9399b4af0ba3d044dc36](https://github.com/StrongDwarf/Demo/tree/9c27dd5599f20d2f868d9399b4af0ba3d044dc36)

### 申请一个权限

下面是一个申请获得打电话权限的demo
先在AndroidManifest.xml添加权限

``` xml
<manifest>
    <uses-permission android:name="android.permission.CALL_PHONE"/>
</manifest>
```

在activity_main.xml中添加一个按钮

``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    >
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/btn1"
        android:text="打电话给10086"/>
</LinearLayout>
```

MainActivity.java代码

``` java

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView(){
        findViewById(R.id.btn1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1: //单个授权
                //检查版本是否大于M
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    }else {
                        showToast("权限已申请,开始打电话给10086");
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + "10086");
                        intent.setData(data);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("权限已申请,开始打电话给10096");
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + "10086");
                intent.setData(data);
                startActivity(intent);
            } else {
                showToast("权限已拒绝");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void showToast(String string){
        Toast.makeText(MainActivity.this,string,Toast.LENGTH_LONG).show();
    }

}
```

### 同时申请多个权限

同样,先在AndroidManifest.xml中设置权限

``` xml
<uses-permission android:name="android.permission.CALL_PHONE"/>
<uses-permission android:name="android.permission.CAMERA"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

在activity_main.xml中添加一个按钮

``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    >
    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/btn2"
        android:text="申请多个权限"/>
</LinearLayout>
```

修改MainActivity.java文件如下

``` java
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 2;

    //声明需要用户授予的权限
    String[] permissions = new String[]{
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CALL_PHONE
    };
    //声明一个集合,在后面的代码中用来存储用户拒绝授权的权限
    List<String> mPermissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        findViewById(R.id.btn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn2:
                mPermission.clear();
                //遍历需要申请权限列表中的权限,如果未申请就将其添加到mPermission中
                for(int i = 0;i<permisssions.length;i++){
                    if(ContextCompat.checkSelfPermission(MainActivity.this,permissions[i]) != PackageManager.PERMISSION_GRANTED){
                        mPermissionList.add(permission[i]);
                    }
                }
                if(mPermisssionList.isEmpty()){
                    //未授予的权限为空，表示都授予了
                    Toast.makeText(MainActivity.this,"已授权",Toast.LENGTH_LONG).show();
                }else{
                    //请求权限方法
                    String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]); //将List转为数组
                    ActivityCompat.requestPermisssions(MainActivity.this,permissions,MY_PERMISSIONS_REQUEST_CALL_CAMERA);
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_CAMERA){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[i]);
                    if (showRequestPermission) {
                        showToast("权限未申请");
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showToast(String string){
        Toast.makeText(MainActivity.this,string,Toast.LENGTH_LONG).show();
    }
}
```

更具体的可见demo代码:[https://github.com/StrongDwarf/Demo/tree/9c27dd5599f20d2f868d9399b4af0ba3d044dc36](https://github.com/StrongDwarf/Demo/tree/9c27dd5599f20d2f868d9399b4af0ba3d044dc36)

文章参考:
 * [Android系统权限](https://www.2cto.com/kf/201702/601863.html)
 * [Android总结篇系列:Android权限](https://www.cnblogs.com/lwbqqyumidi/p/3793440.html)
 * [Android6.0--权限管理](http://www.cnblogs.com/zhangqie/p/7562736.html)
 * [Android系统权限和root权限](https://blog.csdn.net/zhgeliang/article/details/78805214)