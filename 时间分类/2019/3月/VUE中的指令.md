# VUE中的指令

## 内部指令

### v-if

v-if指令可以根据表达式的值在DOM中生成或移除一个元素。如果v-if表达式赋值为false,那么对应的元素就会从DOM中移除;否则,对应元素的一个克隆将被重新插入DOM中

``` html
<body class="native">
    <div id="example">
        <p v-if="greeting">Hello</p>
    </div>
</body>
<script>
var exampleVM2 = new Vue({
    el:"#example",
    data:{
        greeting:false
    }
})
</script>
```

渲染后结果

``` html
<body class="native">
    <div id="example">
    </div>
</body>
```

### v-show

v-show指令是根据表达式的值来显示或者隐藏HTML元素.当v-show赋值为false时,元素将被隐藏(stylr="display:none")。

``` html
<body class="native">
    <div id="example">
        <p v-show="greeting">Hello</p>
    </div>
</body>
<script>
var exampleVM2 = new Vue({
    el:"#example",
    data:{
        greeting:false
    }
})
</script>
```

渲染后结果

``` html
<body class="native">
    <div id="example">
        <p style="display:none">Hello</p>
    </div>
</body>
```

v-show和v-if的区别在于,当表达式的值为false时,v-if指令对应的元素不会被创建,而v-show指定对应的元素仍然会被创建,只是不可见。

### v-else

v-else指令必须紧跟这v-if或v-show，充当着else功能。

``` html
<body class="native">
    <div id="example">
        <p v-show="greeting">Hello</p>
        <p v-else>haha</p>
    </div>
</body>
<script>
var exampleVM2 = new Vue({
    el:"#example",
    data:{
        greeting:false
    }
})
</script>
```

渲染后结果

``` html
<body class="native">
    <div id="example">
        <p style="display:none">Hello</p>
        <p>haha</p>
    </div>
</body>
```

### v-model

v-model指令用来在input,select,text,checkbox,radio等表单控件元素上创建双向数据绑定。根据控件的类型v-model自动选择正确的方法更新元素。

使用实例:

``` html
<body>
    <div id="app">
        <input type="text" v-model="data.name" placeholder="">
    </div>
</body>
<script>
var app = new Vue({
    el:'#app',
    data:{
        data:{
            name:""
        }
    }
})
</script>
```

v-model指令还可以添加多个参数

* number:如果想将用户的输入自动装换为Number类型(如果原值为NaN,则返回原值),则可以添加number特性
* lazy:在默认情况下,v-model在input事件中同步输入框的值与数据,我们可以添加一个lazy特性,从而将数据改到在change事件中发生。
* debounce:设置一个最小的延时,在每次敲击之后延时同步输入框的值与数据。如果每次更新都要进行高耗操作(例如:在input中输入内容时要随时发送AJAX请求)，那么它较为有用

### v-for

可以使用v-for指令基于源数据重复渲染元素。并且可以使用$index来呈现相对应的数组索引，代码示例如下:

``` html
<body>
    <ul id="demo">
        <li v-for="item in items" class="item-{{$index}}">
            {{$index}}-{{parentMessage}} {{item.msg}}
        </li>
    </ul>
</body>
<script>
var demo = new Vue({
    el:"#demo",
    data:{
        parentMessage:'xiaobaicai',
        items:[
            {msg:'1'},
            {msg:'2'}
        ]
    }
})
```

当要对v-for绑定的数据做出变动时,Vue.js包装了被观察数组的变异方法,它们能触发视图更新。被包装的方法有:

* push()
* pop()
* shift()
* unshift()
* splice()
* sort()
* reverse()

值得注意的是,除了这些方法之外,直接修改数组中的数据是不生效的。如下代码，当点击按钮时,Data中的数据会刷新,但视图并不会被更新

``` html
<div id='app'>
    <ul>
        <li v-for="item in items">
            item.msg: {{item.msg}} + parentMsg
        </li>
    </ul>
    <button v-on:click="updateItem">更新元素</button>
</div>
<script src="https://cdn.bootcss.com/vue/2.6.6/vue.common.dev.js"></script>
<script>
   window.onload = function(){
        var app = new Vue({
            el:"#app",
            data:{
                parentMsg:'这是parentMsg',
                items:[{
                    msg:'1'
                },{
                    msg:'2'
                }]
            },
            methods:{
                updateItem(){
                    this.items[0] = {msg:'3'}
                }
            }
        })
   }
</script>
```

要更新数组中的元素，可以使用Vue.$set()方法,该方法接收三个参数,分别是数组,数组下标,值。如上代码可以修改成下面这样的。当数据变化时,视图会同步更新。

``` html
<script>
    window.onload = function(){
        var app = new Vue({
            el:"#app",
            data:{
                //...
            },
            methods:{
                updateItem(){
                    this.$set(this.items,0,{msg:'3'})
                }
            }
        })
   }
</script>
```

Vue.$set()方法是splice的语法糖,如果看Vue的源码的话，会发现源码中$set()方法的定义如下:

``` javascript
def(
    arrayProto,
    'set',
    function $set(index,val){
        if(index >= this.length){
            this.length = Number(index) + 1
        }
        return this.splice(index,1,val)[0]
    }
)
```