# git常用命令

最近在团队项目开发中,需要使用git,百度了一番相关的命令。特地记录下来

### 设置用户名和密码

设置用户名:

``` bash
git config --global user.name "xiaobaicai"
```

设置邮箱

``` bash
git config --global user.email "1908654110"
```

### 提交操作

创建分支

``` bash
git brance -b mbranch
```

检查分支

``` bash
git checkout mbranch
```

提交到本地分支

```
git commit -m '这次改动了###'
git add .
git commit -m '这次改动了###'

//提交到远程分支
git push
```

其他更多命令请 git -help查看.
