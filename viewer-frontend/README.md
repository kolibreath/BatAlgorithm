# viewer-frontend

## 主要模块
```
└── threeconfig.js .
├── App.vue
├── assets
│   ├── helvetiker_regular.typeface.json
│   ├── logo.png
│   └── logo.svg
├── bus
│   └── bus.js
├── canvas
│   └── canvasjs.min.js
├── components
│   ├── BottomTweaks.vue
│   ├── Canvas.vue
│   ├── CanvasControl.vue
│   ├── Dashboard.vue
│   ├── LeftDrawer.vue
│   ├── Main.vue
│   ├── ParamsDialog.vue
│   ├── ResultList.vue
│   └── ResultListRow.vue
├── main.js
├── plugins
│   └── vuetify.js
├── router
│   └── index.js
├── store
│   ├── Common.vue
│   └── index.js
└── threejs
    ├── ObjectLoader.js
    ├── OrbitControls.js
    ├── three.min.js
    └── threeconfig.js
```
1. assets 存放了logo和需要加载到坐标轴上面的字体文件
2. bus store router 这几个文件夹中的文件的都是和ui无关的逻辑文件
3. components 是所有的 ui的vue文件的集合， 父子组件之间通过props 通信，兄弟组件之间通过bus传递事件通信


## 实现中的细节说明
- ThreeJS基本使用
使用好ThreeJS主要需要掌握好几个概念
1. 渲染器renderer
2. 相机 camera
3. 场景 scene
4. 物体 mesh 
其中一个物体是由
material和geometry两个部分组成，material规定这个物体的外表颜色，反光情况，geometry规定这个物体的外形。
> 关于点击事件
一般来说点击事件是根据观察者的视野发射出一条射线，触碰到物体之后确定物体，进行回调。我个人觉得这样的逻辑比较重，于是找到了``three-iteration``这个库
<br>
关于如何引入VueJS，可以按照官网上面的配置引入。使用OrbitControls这样的控制器的时候可能需要按照要求下载依赖。
- 如何绘制坐标轴和坐标轴上面的刻度
1. 绘制坐标轴
ThreeJS是一个右手系，在绘制坐标轴的时候这点需要知道
```
 initLineOfRealm(length) {
      let lineMaterial = new THREE.LineBasicMaterial({
        vertexColors: THREE.VertexColors,
        linewidth: 3
      });
      //x y z轴颜色
      let color1 = new THREE.Color(0xff4081);
      let color2 = new THREE.Color(0x536dfe);
      let color3 = new THREE.Color(0xffeb3b);

      let starts = [
        new THREE.Vector3(0, 0, -length),
        new THREE.Vector3(0, -length, 0),
        new THREE.Vector3(-length, 0, 0)
      ];
      let ends = [
        new THREE.Vector3(0, 0, length),
        new THREE.Vector3(0, length, 0),
        new THREE.Vector3(length, 0, 0)
      ];

      for (let i = 0; i < 3; i++) {
        let geom = new THREE.Geometry();
        geom.vertices.push(starts[i]);
        geom.vertices.push(ends[i]);
        switch (i) {
          case 0:
            geom.colors.push(color1, color1);
            break;
          case 1:
            geom.colors.push(color2, color2);
            break;
          case 2:
            geom.colors.push(color3, color3);
            break;
        }

        let line = new THREE.Line(geom, lineMaterial);
        this.scene.add(line);
      }
```
我在绘制的时候使用的是一个三维向量Vector3，并且设置不同的起始位置到结束位置，就可以确定这条直线在空间中的位置。
2. 绘制刻度
直接在ThreeJS中使用html作为刻度很难和完成旋转效果，或者是根本不能实现（我没有这样尝试过）。所以我直接使用了``TextGeometry``+``FontLoader``的方式通过加载一个字体文件，将字体放置到对应的三维空间中。
3. 粒子位置的修改
这个项目主要是呈现通过随机算法生成在一个空间中的粒子，在不同的测试函数的影响下的收敛情况，在每一次迭代就修改一次粒子的位置。关于粒子位置的获取和暂停，重置等等功能的执行逻辑，很多是依赖于事件总线机制``bus.$emit()``&``bus.$on()``，下面使用一个流程图来演示。
![监听逻辑演示](https://upload-images.jianshu.io/upload_images/4714178-08abd4a5d60747a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 关于重绘粒子
因为ThreeJS还是很占用资源的，如果每一次迭代都重新生成新的粒子会非常占用资源，所以只需要第一次进行初始化，通过
```
 //如果是第一次遍历 需要初始化例子
      if (
        this.originalParticles.length === 0 ||
        this.improvedParticles.length === 0
      ) {
      }
```
这样的方式判断，之后将粒子的数据保存在数组中，之后的迭代中只需要修改粒子的位置即可，只有当重置的时候才需要清除容器中的粒子数据，其余时候直接复用。详情可以看看``Canvas.vue`` 