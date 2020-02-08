# 前后端交互api

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
        "functionNames" : name of the funtions
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
}
```
## 调整算法速度 不修改其他的配置
|  methods   | api  | description | parameter |
|  ---- | ----  | ---- | ---- |
|POST| /api/alterSpeed|修改蝙蝠算法的运行速度|json|

```

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
|POST| /api/start|启动蝙蝠算法的参数配置|no-params|
```
{
    "code":int,
    "message":string,
    data:object
}
```

## 返回每一次原始蝙蝠算法迭代粒子位置以及相关信息
|  methods   | api  | description | parameter |
|  ---- | ----  | ---- | ---- |
|GET| /api/original?index=1修改蝙蝠算法的参数配置|no-params|
```
{
    "code":int,
    "message":string,
    data:object
}
```