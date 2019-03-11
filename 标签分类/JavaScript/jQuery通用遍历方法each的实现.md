# jQuery通用遍历方法each的实现

## each实现

jQuery的each方法,作为一个通用遍历方法,可用于遍历对象和数组

语法为:

``` javascript
jQuery.each(object,[callback])
```

回调函数有两个参数：第一个为对象的成员或数组的索引,第二个为对应变量或内容

## 退出循环

尽管ES5提供了forEach方法,但是forEach没有办法中止或者跳出forEach循环,除了抛出一个异常。但是对于jQuery的each函数,如果需要退出each循环可使用回调函数返回false,其他返回值将被忽略

``` javascript
#.each([0,1,2,3,4,5],function(i,n){
    if(i > 2) return false
    console.log("i:"+i+" n:"+n)
})
```

## 第一版

``` javascript
function each(arr,callback){
    var length,
        i = 0;
    if(isArrayLike(obj)){
        length = obj.length
        for(;i<length;i++){
            callback(i,obj[i])
        }
    }else{
        for(i in obj){
            callback(i,obj[i])
        }
    }
    return obj
}
```

## 中止循环

``` javascript
function each(arr,callback){
    var length,
        i = 0;
    if(isArrayLike(obj)){
        length = obj.length
        for(;i<length;i++){
            if(callback(i,obj[i])){
                break
            }
        }
    }else{
        for(i in obj){
            if(callback(i,obj[i])){
                break
            }
        }
    }
    return obj
}
```

## this

``` javascript
function each(arr,callback){
    var length,
        i = 0;
    if(isArrayLike(obj)){
        length = obj.length
        for(;i<length;i++){
            if(callback.call(obj,i,obj[i]) === false){
                break
            }
        }
    }else{
        for(i in obj){
            if(callback.call(obj,i,obj[i]) === false){
                break
            }
        }
    }
    return obj
}
```

