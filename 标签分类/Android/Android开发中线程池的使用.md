# Android开发中线程池的使用
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;线程池是一种多线程处理形式，处理过程将任务添加到队列，然后在创建线程后自动启动这些任务。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;什么情况会用到线程池呢?假如你现在做一个音乐类APP，用户需要下载歌曲。下载歌曲很耗时，需要启动一个新线程进行下载。我们之前可能会采用下面的代码:<br>
* new Thread(new Runnable(){
*    @Override
*    public void run(){
*       //下载歌曲
*    }
* }).start();
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果要下载1000首歌曲,是否要同时开启1000个线程?这样会产生什么问题呢?<br>
* 每下载一首歌曲，就要新建一个线程，导致频繁地创建线程与销毁线程，使程序卡住或者程序崩溃。
* 这样创建的线程没法统一管理。
* 不方便统计，例如已下载完成歌曲的数量。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果使用线程池就能完美地解决以上问题。线程池的优点如下:<br>
* 重用已创建线程，不会频繁创建线程与销毁线程。
* 对线程统一管理，分配，调优和监控。
* 控制线程数量，合理使用系统资源，这样不会造成程序卡住或者程序崩溃。
### 创建线程池
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Android中常用的线程操作都是通过ThreadPoolExecutor类来实现的，此类最长的构造方法有7个参数:<br>
```
public ThreadPoolExecutor(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable>workQueue,ThreadFactory threadFactory,RejectedExecutionHandler handler)
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;各参数的含义如下:<br>
* corePoolSize:核心线程数
* maximumPoolSize:线程池最大线程数
* keepAliveTime:线程空闲时保持时间。如果线程池中线程数量超过核心线程数量，并且多出的线程空闲时间超过keepAliveTime，就会结束多出的线程，从而及时销毁多出来的空闲线程，减少资源消耗。如果线程池任务增多，就重新创建新线程。这个参数也可以通过setKeepAliveTime(long,TimeUnit)方法动态设置。默认情况下，这个keepAliveTime参数针对的是非核心线程。如果调用allowCoreThreadTimeOut(boolean)方法，传入true，keepAliveTime超时策略就会运用在核心线程上。
* unit:第三个参数的单位。这个一个枚举，常用的有TimeUnit.SECONDS(秒),具体还有以下值: TimeUnit.DAYS(天), TimeUnit.HOURS(小时),TimeUnit.MICROSECONDS(微秒),TimeUnit.MINUTES(分钟)，TimeUnit.NANOSECONDS(纳秒),TimeUnit.SECONDS(秒).
* workQueue:线程池中的任务队列。这个队列保存线程池提交的任务，它的使用与线程池中的线程数量有关，具体规则如下:
* 如果当前的线程池运行的线程数少于corePoolSize,则execute方法执行任务时会开启一个核心线程进行处理.
* 如果线程池中的线程数量达到核心线程数，并且workQueue未满，当execute方法执行任务时不会开启新线程，而是将任务加如workQueue队列中等待处理
* 当execute方法执行任务时，线程池中的线程数已达到核心线程数，并且workQueue队列已满。这时就会判断线程池中的线程数是否大于maximumPoolSize，如果没有大于maximumPoolSize，就会开启非核心线程数处理任务，如果大于maximumPoolSize，就拒绝执行该任务。
* BlockingQueue：阻塞队列，有一下三个常用的BlockingQueue实现类。
* SynchronousQueue:一种无缓冲的等待队列，它的特别之处在于内部没有容器，当它生产(put)产品时，如果没有人想要消费产品(当前没有线程执行take)，此生产线程必然阻塞，等待一个消费线程调用take操作，take操作将会唤醒该生产线程，同时消费线程会获取生产线程的产品(数据传递),这样一个过程称为一次配对过程(当然，也可以先take后put，原理是一样的)。
* LinkedBlockingQueue:无界队列。调用execute方法执行任务，线程池中的核心线程都在运行时，使用无界队列(例如创建时没有指定大小)会将新的任务加如inkedBlockingQueue中等待执行。当然，这个队列创建时，构造方法中也可以指定大小。
* ArrayBlockingQueue:必须要指定大小的队列，构造方法要传入一个int类型参数，设置队列的大小。存储在ArrayBlockingQueue中的元素按照FIFO(先进先出)的方式来进行存取。
* threadFactory:创建新线程的工厂类，可以通过Executors.defaultThreadFactory()获取系统给封装的线程工厂。我们也可以自己动手实现一个，继承ThreadFactory接口，实现newThread方法。
* handler:拒绝任务。调用execute(Runnable)方法提交新任务时，如果线程池关闭或者线程池数量等于maximumPoolSize并且队列满了，execute内部就会调用RejectedExecutionHandler接口实现类的rejectedExecution(Runnable,ThreadPoolExecutor)方法。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;针对拒绝任务，SQK提供了以下几种策略:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ThreadPoolExecutor.AbortPolicy:添加任务被拒绝，这是默认策略，如果不传递handler参数，默认值就是这个数<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ThreadPoolExecutor.CallerRunsPolicy:提供一个反馈机制，告诉调用者可以减慢提交新任务的速度。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ThreadPoolExecutor.DiscardPolicy:任务无法执行被丢弃<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ThreadPoolExecutor.DiscardOldestPolicy:如果线程池没有被关闭，丢弃队列最前面的任务，然后重新尝试执行任务(可能会再次失败，导致重复执行)。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
