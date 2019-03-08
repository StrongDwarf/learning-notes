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

所以,一般情况下,我们可以直接使用splice()函数对数组进行修改。
同时,也可以使用filter,concat,slice方法,返回的数组将是一个不同的实例，我们可以用新的数组替换原来的数组。

v-for同时还可以和Vue.js提供的内置过滤器或排序数据一起使用,如filterBy，orderBy

### v-text

v-text指令可以更新元素的textContent。在内部,{{Mustache}}差值也被编译为textNode的一个v-text指令,代码示例如下:

``` html
<span v-text="msg"></span><br/>
<!-- same as -->
<span>{{msg}}</span>
```

### v-html

v-html指令可以更新元素的innerHTML。内容按普通HTML插入--数据绑定被忽略。如果想复用模板片段,则应该使用partials。

### v-bind

v-bind指令用于响应更新HTML特性,将以个或多个attribute,或者一个组件prop动态绑定到表达式。v-bind可以简写为:

``` html
<img v-bind:src="imageSrc">
<!-- 缩写 -->
<img :src="imageSrc">
```

在绑定prop时,prop必须在子组件中声明。可以用修饰符指定不同的绑定类型。修饰符为:

* .sync:双向绑定，只能用于prop绑定
* .once:单次绑定，只能用于prop绑定
* .camel:将绑定的特性名字转换回驼峰命名。只能用于普通HTML特性的绑定。

### v-on

v-on指令用于绑定事件监听器。事件类型由参数指定；表达式可以是一个方法的名字或一个内联语句;如果没有修饰符,也可以省略。

使用在普通元素上时,只能监听元素DOM事件；使用在自定义元素组件上时,也可以监听子组件触发的自定义事件。

在监听元素DOM事件时,如果只定义一个参数，DOM event为事件的唯一参数;如果在内敛语句处理器中访问原生DOM事件,则可以用特殊变量$event把它传入方法。

Vue.js 1.0.11及之后的版本在监听自定义事件时,内敛语句可以访问一个$arguments属性,它是一个数组,包含了传给子组件的$emit回调的参数

``` html
<!-- 方法处理器 -->
<button v-on:click="doThis"></button>
<!-- 内联语句 -->
<button v-on:click="doThat('hello',$event)"></button>
<!-- 缩写 -->
<button @click="doThis"></button>
```

v-on后面不仅可以跟参数,还可以增加修饰符

* .stop:调用event.stopPropagation()
* .prevent:调用event.preventDefault()
* .capture:添加事件监听器时使用capture(捕获)模式
* .self:只当事件是从侦听器绑定的元素本身触发时才触发回调
* .{keyCode|keyAlias}:只在指定按键上触发回调。

``` html
<!-- 停止冒泡 -->
<button @click.stop="doThis"></button>
<!-- 阻止默认行为 -->
<button @click.prevent="doThis"></button>
<!-- 阻止默认行为,没有表达式 -->
<form @submit.prevent></form>
<!-- 串联修饰符 -->
<button @click.stop.prevent="doThis">stop</button>
<!-- 键修饰符,键别名 -->
<input @keyup.enter="onEnter">
```

### v-ref

在子组件上注册一个子组件的索引,便于直接访问。不需要表达式,必须提供参数id。可以通过父组件的$refs对象访问子组件。

当v-ref和v-for一起使用时，注册的值将是一个数组,包含所有的子组件,对应于绑定数组;如果v-for使用在一个对象上，注册的值将是一个对象，包含所有的子组件，对应于绑定对象。

值得注意的是:因为HTML不区分大小写,camelCase风格的名字比如v-ref:someRef将全部转换为小写。可以用v-ref:some-ref设置this.$refs.someRef

### v-el

为DOM元素注册一个索引,方便通过所属实例的$els访问这个元素,可以用v-el:some-el设置this.$els.someEl。

``` html
<span v-el:msg>hello</span>
<span v-el:other-msg>world</span>
```

通过this.$els获取相应的DOM元素

``` javascript
this.$els.msg.textContent  //-> "hello"
this.$els.otherMsg.textContent // -> "world"
```

### v-pre

编译时跳过当前元素和它的子元素.可以用来显示原始Mustache标签。跳过大量没有指令的节点会加快编译

### v-cloak

v-clock这个指令保持在元素上直到关联实例结束编译。用法如下:

``` html
<style>
    [v-cloak]{
        display:none;
    }
</style>
<div v-vloak>
    {{message}}
</div>
```

## 自定义指令

### 基础

Vue.js用Vue.directive(id,definition)方法注册一个全局自定义指令,它接受两个参数:指令ID与定义对象。也可以用组件的directive选项注册一个局部自定义指令。

#### 钩子函数

Vue.js提供了几个钩子函数(都是可选的,相互之间没有制约关系)

* bind:只调用一次,在指令第一次绑定到元素上时使用
* update:在bind之后立即以初始值为参数第一次调用，之后每当绑定值变化时调用，参数为新值与旧值
* unbind:只调用一次,在指令从元素上解绑时调用

``` javascript
Vue.directive('my-directive',{
    bind:function(){
        //准备工作
        //例如,添加事件处理器或只需要运行一次的高耗任务
    },
    update:function(newValue,oldValue){
        //值更新时的工作
        //也会一初始值为参数调用一次
    },
    unbind:function(){
        //清理工作
        //例如,删除bind()添加的事件监听器
    }
})
```

在注册之后,便可以在Vue.js模板中这样用(记得添加前缀v):

``` javascript
<div v-my-directive="someValue"></div>
```

#### 指令实例属性

所有的钩子函数都将被复制到实际的指令对象中,在钩子内this指向这个指令对象。这个对象暴露了一些有用的属性:

* el:指令绑定的元素
* vm:拥有该指令的上下文ViewModel
* expression:指令的表达式,不包括参数和过滤器
* arg:指令的参数
* name:指令的名字,不包含前缀
* modifiers:一个对象,包含指令的修饰符
* descriptor:一个对象,包含指令的解析结果

#### 对象字面量

如果指令需要多个值,则可以传入一个JavaScript对象字面量。记住,指令可以使用任意合法的JavaScript表达式。代码示例如下:

``` html
<body>
    <div id="demo" v-demo="{color:'white',text:'hello!'}"></div>
</body>
<script>
    Vue.directive('demo',function(value){
        console.log(value.color)        //"white"
        console.log(value.text)         //"hello"
    })
    var demo = new Vue({
        el:"#demo"
    })
</script>
```

#### 字面修饰符

当指令使用了字面修饰符时,它的值将按照普通字符串处理并传递给update方法。update方法将只调用一次，因为普通字符串不能响应数据变化。代码实例如下:

``` html
<body>
    <div id="demo" v-demo.literal="foo bar baz"></div>
</body>
<script>
    Vue.directive('demo',function(value){
        console.info(value)     //foo bar baz
    })
    var edmo = new Vue({
        el:'#demo'
    })
</script>
```

#### 元素指令

有时候我们想以自定义元素的形式使用指令,而不是以属性的形式。元素指令可以看做是一个轻量组件。可以像下面这样注册一个自定义元素指令:

``` html
<body id="demo">
    <my-directive class="hello" name="hi"></my-directive>
</body>
<script>
    Vue.