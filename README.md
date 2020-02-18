# Yet Another Implemetation of BatAlgorithm
![演示图片](https://s2.ax1x.com/2020/02/18/3ixjl6.png)

![演示图片2](https://s2.ax1x.com/2020/02/18/3ix2Yn.png)

## introduction
- viewer-backend
1. 使用Springboot 作为架构的一个简单的后端服务器程序，用于处理前端代码发出的请求
2. 在后端主要实现了一个改进的蝙蝠算法和一个原始的蝙蝠算法、
- viewer-frontend
前端使用的是VueJS作为框架,vue-cli 简单的搭建了一个SPA项目，使用[vuetify](https://vuetifyjs.com/) 作为主要组件库，使用了部分[element-ui](https://element.eleme.cn/) 组件,用[ThreeJS](https://threejs.org/)作为3D工具显示在不同的测试函数下粒子的收敛情况,
使用[three-interation](https://github.com/jasonChen1982/three.interaction.js/tree/master)作为粒子点击事件的响应工具

## how to use
- clone project
```
git clone https://github.com/kolibreath/BatAlgorithm.git
```
- go to the directory of viewer-frontend
```
cd viewer-frontend
```
- install dependencies
```
npm install
```
- start the project 
```
npm run serve
```
And start the backend application with IntelliJ

### some details
1. [notes must be taken about frontend](https://github.com/kolibreath/BatAlgorithm/blob/master/viewer-frontend/README.md)
2. [words gotta to say  about backend and algoritm](https://github.com/kolibreath/BatAlgorithm/blob/master/viewer-backend/README.md)
