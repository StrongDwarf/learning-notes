global.scope = "global"
var obj = {
    a:{
        sayName:() => {
            console.log(this)
        },
        name:'朱泽聪'
    },
    b:function(){
        obj.a.sayName()
    }
}
let name = 'xiaobaicai'
obj.a.sayName()
obj.b()