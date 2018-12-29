# post与get的区别
### 两者的区别
* 两种方式能传递的数据大小不同,get方法传递的数据有大小限制,一般是2K,不过不同的浏览器上的大小限制不同,post的可以传递的数据理论上则是无限大,不过实际情况中都会在服务器上进行大小限制。
* get请求获得的静态资源和html页面一般会被浏览器缓存,而post请求获得的数据则不会被缓存。
* get不安全,post则安全很多
* 请求方式不同,get通过将请求参数拼接到url中发送请求,post则将请求参数放在[request-body]中发送
* 请求速度不同,get请求的速度比post请求的速度快点
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
### 两者的区别深究
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
#### 为什么get和post请求传递的数据大小不同?
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;关于get和post请求传递的数据大小, HTTP协议中说的是两者能传递的数据都是没有限制的(即可以传无限大的数据)，而get传递的数据大小限制是2K的原因是浏览器对url的长度的限制是2K+53bits,所以一般认为get传递的数据大小限制是2K,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这个应该很好理解,因为get请求获取数据的方式是通过将字符串拼接在url上来实现的,比如向www.baidu.com 传递{name:xiaobaicai}数据的url如下:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;http://www.baidu.com?name=xiaobaicai<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;试想一下,如果浏览器不限制url的长度,那么将会产生一条格外常的url，那会是一种怎样的场景,哈哈哈,<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
#### 为什么get请求获得的静态资源会被缓存,而post请求获得的数据则不会被缓存？
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这主要是和get和post两种方法在设计时的目的不同,在对资源操作中,我们知道最常用的是增删查改, 而HTTP协议的设计目的是为了更好的使用网络资源，所以HTTP协议的设计者在设计该协议时，对于这几种情况,分别设计了put(增),delete(删)，get(查),post(改)四种方法。所以,一般情况下的理解是 get(查)获取的数据是不变的,而post(改)每次获取的数据(获取的是修改后的数据)都是变化的。 所以在对两种情况进行缓存处理时,想想看,对于默认是不变的数据你会选择缓存还是不缓存?<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
#### 为什么说get不安全,而post则安全很多?
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这种说法主要是从用户信息保密的角度上说的，如上面讲到的一样,get是通过在url上拼接参数向服务器发起请求的,而这种方式发起的请求,都会保存在浏览器的历史记录中,可以被其他网站访问。而post发起的请求则不会保存在浏览器的历史记录中,相对来说安全很多。这也提示我们在设计API接口时需要注意：一些涉及私密信息的请求不要使用get,而要使用post。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
#### 为什么说get和post的请求方式不同?get和post请求的根本区别是什么?
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;说起get通过将数据拼接到url中发出请求,而post将数据保存在body中发出请求。相信大家明白,但是get请求能不能将数据放在[request-body]中发给服务器呢? post请求能不能将数据拼接到url上发送给服务器端呢?<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;答案是当然可以。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;这里我们需要看一下协议请求报文的格式来协助分析:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如下是一段HTTP协议报文的格式，可以看到一段HTTP协议发起的请求分为3个主体:<br>
* 请求行:包含了method URL Version信息, 其中method如get,post。URL如www.baidu.com Version如http/1.1
* 请求头:请求头中可以包含多个参数,如 cache-control:no-cache；这里具体的可以放什么参数可以自己去百度
* 请求体:请求体是数据存放的地方(如使用post请求向服务器发送数据时数据存放在请求体中)

["请求报文图片"](https://img-blog.csdn.net/20170114161157321?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMjY5MjcyODU=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast "请求报文图片")
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;再来看一段get请求报文和一段post请求报文来分析两者的区别:<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;首先看get请求<br>
```
GET http://download.microtool.de:80/somedata.exe?username=admin&password=123123
Host:download.microtool.de
Accept:*/*
Pragma:no-cache
Cache-Control:no-cache
Referer:http://download.microtool.de/
User-Agent:Mozilla/4.04[en](Win95;I;Nav)
Range:bytes=554554-
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;再看post请求<br>
```
POST /02_WEB_HTTP/index.html HTTP/1.1
Accept: application/x-ms-application, image/jpeg, application/xaml+xml, image/gif, image/pjpeg, application/x-ms-xbap, */*
Referer: http://localhost:8080/02_WEB_HTTP/form.html
Accept-Language: zh-CN,en-US;q=0.7,ko-KR;q=0.3
User-Agent: Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; InfoPath.3)
Content-Type: application/x-www-form-urlencoded
Accept-Encoding: gzip, deflate
Host: localhost:8080
Content-Length: 30
Connection: Keep-Alive
Cache-Control: no-cache 
 
username=admin&password=123123
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;两者的区别只在于请求行中的method的不同(以及由于method的不同而导致的请求头中一些key的默认配置不同)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;同样,在get请求中的body中存放数据和拼接url使用post传递数据都没有问题。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br><br>
#### 为什么get请求的速度比post请求的速度快?
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;get请求的速度比post请求的速度更快,有两个原因:<br>
* 1:post请求包含更多的请求头，因为post需要在请求的body部分包含数据,所以会多了几个数据描述部分的首部字段(如content-type)，当然,这个部分的影响是微乎其微的,
* 2:post请求在真正接收数据之前会先将请求头发送给服务器进行确认，然后才真正发送数据。而get不需要,这是最主要的原因，下面会详细讲解
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一段post请求的过程如下:<br>
```
(1)浏览器请求tcp连接(第一次握手)
(2)服务器答应进行tcp连接(第二次握手)
(3)浏览器确认，并发送post请求头(第三次握手,这个报文比较小,所以http会在此时进行第一次数据发送)
(4)服务器返回100 continue响应
(5)浏览器发送数据
(6)服务器返回200 ok响应
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;一段get请求的过程如下:<br>
```
(1)浏览器请求tcp连接(第一次握手)
(2)服务器答应进行tcp连接(第二次握手)
(3)浏览器确认,并发送get请求头和数据(第三次握手,这个报文比较小,所以http会在此时进行第一次数据发送)
(4)服务器返回200 OK响应
```
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;可以看到 post请求的过程比get请求的时间要多了一次中间请求头确认过程。<br>
