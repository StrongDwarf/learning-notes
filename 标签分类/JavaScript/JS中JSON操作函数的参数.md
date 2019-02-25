# JS中JSON操作函数的参数

在我们日常编写JavaScript代码时,JSON类型的处理是经常会遇到的。一般来说,JSON.stringify(obj)和JSON.parse(str)已经足够我们使用了。但是在某些特殊情况下,向JSON.stringify()和JSON.parse()函数中传入其他参数可以实现一些定制化操作。

* JSON.strinify() :可以接收3个个参数,第一个参数可以是一个基本数据类型,也可以是引用数据类型。第二个参数是一个自定义函数,自定义函数可以接收2个参数key，value。函数返回值将作为序列化后对应key的Value值。第三个参数可以是数字也可以是字符串。当是数字时，表示每个级别的缩进数。当是字符串时，表示每个级别前的填充数。
* JSON.parse() ：可以接收2个参数,第一个参数是要解析的字符串,第二个参数是一个自定义函数，自定义函数可以接收2个参数。函数返回值将作为解析后对于key的value值

## JSON.stringify()

``` javascript
//接收数字,将自动转化为字符串
typeof JSON.stringify(1)        //"string"
JSON.stringify("xiao").length          //6

//使用第二个参数接收函数
var person = {
    name:'xiaobaicai',
    age:23,
    likes:['dog','cat','fish']
}
JSON.stringify(person,(key,value) => {
    if(Array.isArray(value)){
        return value.join(' ')
    }else{
        return value
    }
})
//"{"name":"xiaobaicai","age":23,"likes":"dog cat fish"}"

//使用第三个参数,
//传入数字
JSON.stringify(person,(key,value) => {
    if(Array.isArray(value)){
        return value.join(' ')
    }else{
        return value
    }
},4)
/**
 * "{
 *    "name": "xiaobaicai",
 *    "age": 23,
 *    "likes": "dog cat fish"
 *  }"
 **/
//传入字符串
JSON.stringify(person,(key,value) => {
    if(Array.isArray(value)){
        return value.join(' ')
    }else{
        return value
    }
},"--")
/**
 * "{
 * --"name": "xiaobaicai",
 * --"age": 23,
 * --"likes": "dog cat fish"
 * }"
 **/
```

有时候,JSON.stringify()还是不能满足对某些对象进行自定义序列化的需求。在这种情况下，可以给对象定义toJSON()方法。如下

``` javascript
var person = {
    name:'xiaobaicai',
    age:23,
    likes:['dog','cat','fish'],
    toJSON:function(){
        return "the Person is " + this.name
    }
}
JSON.stringify(person)
//""the Person is xiaobaicai""
```

toJSON()可以作为函数过滤器的补充,因此,理解序列化的内部顺序十分重要。假设要把一个对象传入JSON.stringify(),序列化该对象的顺序如下:

* 先检查是否存在toJSON()方法,如果存在并且能通过它取得有效的值，则调用该方法。否则返回对象本身
* 查看该函数第二个参数是否存在，如果存在，调用该方法
* 对第二步提供的值进行序列化
* 如果提供了第三个参数，则执行相应的格式化

## JSON.parse()

JSON.parse()同样可以接收一个自定义的解析函数。

对于该解析函数的返回值，如果返回undefined，则表示将在解析后的对象中删除对应的键。

``` javascript
var person = {
    name : "xiaobaicai",
    age : 23,
    likes : ['cat','dog','fish']
}

var jsonText = JSON.stringify(person)

var personCopy = JSON.parse(jsonText,(key,value) => {
    if(key == 'likes'){
        return value.join(' ')
    }else{
        return value
    }
})
```
