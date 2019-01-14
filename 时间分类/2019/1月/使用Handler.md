# Android:使用Handler

Handler可以发送和处理消息对象或Runnable对象,这些消息对象和Runnable对象与一个线程相关联。每个Handler的实例都关联了一个线程和线程的消息队列。当创建了一个Handler对象时,一个线程或消息队列同时也被创建，该Handler对象将发送和处理这些消息或Runnable对象。
Handler类有两种主要用途:

 * 执行Runnable对象,还可以设置延迟
 * 两个线程之前发送消息,主要用来给主线程发送消息更新UI

## 1,为什么要用Handler

为了解决多线程并发问题,假设如果在一个Activity中有多个线程去更新UI,并且都没有加锁机制,那么界面肯定会不正常。因此,Android官方就封装了一套更新UI的机制,也可以使用Handler来实现多个线程之间的消息发送。

## 2,使用Handler

Handler常用的方法有以下几种:

 * post(Runnable)
 * postAtTime(Runnable,long)
 * postDelayed(Runnable,long)
 * sendEmptyMessage(int)
 * sendMessage(message)
 * sendMessageAtTime(Message,long)
 * sendMessageDelayed(message,long)

这些方法主要分为两类:一类是传入一个Runnable对象,另一类是传入一个Message对象.

### 2.1 post一个Runnable对象

``` java
//创建Handler对象
private Handler Handler = new Handler();
//实现Runable接口,使用匿名实现方式,重写run方法,打印一个字符串
private Runnable runnable = new Runnable(){
    @Override
    public void run(){
        Log.i("xiaobaicai","Runnable run");
    }
}
//调用Handler中的post方法
Handler.post(runnable);  //执行
Handler.postDelayed(runnable,2000);     //延迟2秒后执行
```

如上,当使用Handler.post方法时,post一个Runnable对象,底层用的是回调,不会开启一个新的线程。所有的Runnable的run方法还是在主线程中,是可以更新UI的。

### 2.2 使用sendMessage方法发送消息

sendMessage方法可以理解为用来发送消息,这种消息在Android中使用频率比较高。因为在Android多线程中是不能更新UI的,所以必须通过Handler把消息发送给UI线程,才能更新UI，当然,也可以用Handler实现两个子线程发送消息。

如下是一段使用Hadnler每隔200ms更新UI的代码

``` java
public class MainActivity extends AppCompatActivity{
    private TextView textview;
    public static final int UPDATE_UI = 1;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what == UPDATE_UI){
                textview.setText("当前值是:"+msg.obj);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView) findViewById(R.id.textview);
        new Thread(new Runnable(){
            @Override
            public void run(){
                for(int i=1;i<=100;i++){
                    Log.i("xiaobaicai","当前值是:"+i);
                    Message message = handler.obtainMessage();
                    message.what = UPDATE_UI;
                    message.obj = i;
                    handler.sendMessage(message);
                    try{
                        Thread.sleep(200);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
```

使用Handler首先使用内部类的方式重写handleMessage方法,然后在handleMessage方法中处理接收到不同message.what时的处理逻辑。在handler中编写UI操作逻辑,而在其他线程中处理数据相关逻辑,并在数据处理后调用handler.sendMessage()执行UI界面更新逻辑。

这种方式和其他多线程中更新UI操作的方法相比是减少了数据和UI操作之间的耦合性。
