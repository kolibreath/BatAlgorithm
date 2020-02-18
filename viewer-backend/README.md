# 前后端交互api

## 算法简述
1. 原始的蝙蝠算法简述： 蝙蝠算法通过模拟蝙蝠飞行的行为，将每一只蝙蝠设置为解空间的一个点 并且追随最优蝙蝠进行函数寻优。
2. 改进的蝙蝠算法通过客服原始的蝙蝠算法容易早熟的问题，通过设置精英池和在收敛后期进行随机改变的过程跳出函数局部解。
## 使用步骤
1. 使用参数设置功能(``/api/alterConfig``)设置相关的参数 ``前端 -> 后端``
2. 拉取刚刚的参数设置,从Common.vue中获取，可能是由Common.vue中设置的默认设置，也可能是刚才设置的配置
3. 可以使用``progressbar`` 设置算法的速度（``/api/alterSpeed``），点击开始按钮 ``前端 -> 后端`` 发送设置的速度信息，调用``/api/start``
开始算法调用,如果是第一次点击，而不是暂停后的重新开始，将会调用``/api/init``重新设置相关参数
4. 算法的暂停
5. 算法的重置，算法通过在CanvasControl.vue中不断地调用``/api/start``，并且通过在后端tWrapper数组引用计数，保证调用次数不会超过迭代上限
；使用``/api/reset``进行重置，会重置算法成为算法的默认设置，重置tWrapper；重置的时候前端将``global.firstTime``设置为true，用于触发算法的初始化
## 初始化算法的值
|  methods   | api  | description | parameter |
|  ---- | ----  | ---- | ---- |
|POST| /api/init/id|初始化的内容|json|
<br>
初始化的内容
1. 设置函数的index
2. original improved 算法实例
3. pulseRate

> 注意：项目所有的配置都保存在内存中，没有使用数据库进行保存


## 获取当前参数设置
|  methods   | api  | description | parameter |
|  ---- | ----  | ---- | ---- |
|GET| /api/curConfig|获取当前的的蝙蝠算算法的参数配置|json|

```
{
    "code":int,
    "message":string,
    data:{
        "population"  : int,
        "generation" :  int,
        "pulseRate" :   double,
        "frequency" :   double,
        "defaultTitle" : string,
        "functionQueue" : array of int,
        "functionIndex" : int,
        "functionNames" : name of the funtions,
        "speed"         : double
 }   
}

```
## 修改参数配置
|  methods   | api  | description | parameter |
|  ---- | ----  | ---- | ---- |
|POST| /api/alterConfig|修改蝙蝠算法的参数配置|json|

```
{
    "population"  : int,
    "generation" :  int,
    "pulseRate" :   double,
    "frequency" :   double,
    "defaultTitle" : string,
    "functionQueue" : array of int,
    "functionIndex" : int,
    "functionNames" : name of the funtions,
    "speed":double
}
```
## 调整算法速度 不修改其他的配置
|  methods   | api  | description | parameter |
|  ---- | ----  | ---- | ---- |
|POST| /api/alterSpeed|修改蝙蝠算法的运行速度|json|

```
{
    "speed":double
}

```

## 重置算法运行
|  methods   | api  | description | parameter |
|  ---- | ----  | ---- | ---- |
|POST| /api/reset|重置并且停止算法运行|no-params|
```
```

## 暂停算法
|  methods   | api  | description | parameter |
|  ---- | ----  | ---- | ---- |
|POST| /api/stop|停止蝙蝠算法的运行|no-params|
```
{
    "code":int,
    "message":string,
    data:object
}
```

## 启动算法
|  methods   | api  | description | parameter |
|  ---- | ----  | ---- | ---- |
|POST| /api/start/|启动算法|no-params|
```
{
    "code":int,
    "message":string,
    data:object
}
```