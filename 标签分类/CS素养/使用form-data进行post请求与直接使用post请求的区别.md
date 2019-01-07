# 使用form-data进行post请求与直接使用post请求的区别

最近在维护前人留下的代码的时候,发现前人使用post请求之前都使用form-data类型作为post请求的传递数据。如下:

``` javascript
let _formData = new FormData()
_formData.append('departmentIds', datas.departmentIds)
return Axios({
    url: API.saveDepartmentTree,
    data: _formData,
    method: 'POST',
    headers: {
      'Content-Type': 'multipart/form-data'
    }
})
```

而如果是我的话,我只会直接传递. 如下:

``` javascript
//...
return Axios({
    //...
    data:{departmentIds:datas.departmentIds}
    //...
})
//...
```

顿生疑惑,为什么前人不和我直接传递数据呢? 而还要私用form-data封装一下呢?直接使用post传递和使用form-data封装一下再使用post传递有什么区别呢?

### post起源  
post的诞生最早是用于表单信息的提交,如下:

``` html
<form method="post" action="http://www.xiaobaicai.com/upload">
    <input type="text" name="txt1">
    <input type="text" name="txt2">
</form>
```


该段代码会向服务器端发出这样的数据,如下(已省略大部分不相关的数据):

``` javascript
POST /upload /HTTP/1.1
//...
Content-Type:application/x-www-form-urlencoded
//...
Content-Length:21

txt1=hello&txt2=world
```

而这种方式,在上传文件的时候却存在不足,如:使用这种方式如果我们要传递一个文件的话,可以传递一个参数: filedata=fileBitaryData, 将文件二进制数据作为一个参数的值传递给服务器。但是这样就无法传递文件名了,所以在rfc1867中引入了form-data数据类型作为post传递数据的一种新的方法,多用于传递二进制数据。

### post中使用form-data

在post中使用form-data,先看在表单中使用form-data,如下:
与不使用form-data时多了 enctype="multipart/form-data"的设置,这句话的意思是将数据传递方式设置为multipart/form-data。具体区别作用到数据报文上则是多了个Content-Type:multipart/form-data;

``` JavaScript
<form method="post" action="http://www.xiaobaicai.com/upload" enctype="multipart/form-data">
    <input type="text" name="desc">
    <input type="file" name="pic">
</form>
```

该段代码会向服务器发送以下数据(省略不相关数据)
``` javascript
POST /upload /HTTP/1.1
Content-Type:multipart/form-data;boundary=xiaobaicaiData
Content-Length:60408

--xiaobaicai
Content-Disposition:form-data;name="desc"
Content-Type:text/plain;charset=UTF-8
Content-Transfer-Encoding:8bit

...//name="desc"对应的数据
--xiaobaicai
Content-Disposition:form-data:name="pic";filename="photo.jpg"
Content-Type:application/octet-stream
Content-Transfer-Encoding:binary

...//图片二进制数据

```

### 区别

细心的同学看了上面的两个数据报应该已经观察到一些区别了,两者的区别大概有以下几种:
 * 1,multipart/form-data的基础方式是post,也就是说通过post组合方式来实现的.(数据报的method部分仍为post)
 * 2,multipart/form-data于post方法的不同之处在于请求头和请求体,请求头中多了Content-Type:multipart/form-data;boundary=xiaobaicaiData 用来描述数据类型和数据分割符. 其中,boundary用于设置数据分割符,可以随意设置,不过为了避免和文本重复,尽量要使用复杂一点的内容.
 * 3,multipart/form-data的请求体也是一个字符串,不过和post的请求体不同的是它的构造方式,post是简单的name1=value1&name2=value2键值对连接,而multipart/form-data是添加了分隔符等内容的构造体.
