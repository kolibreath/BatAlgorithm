<template>
  <v-card class="center">
    <span style="position:absolute; z-index:10;margin-top:20px; margin-left:1000px">
      <span style="color:white">x轴:</span>
      <span style="color:yellow">红色</span>
      <br>
      <span style="color:white">y轴:</span>
      <span style="color:red">红色</span>
      <br>
      <span style="color:white">z轴:</span>
      <span style="color:blue">蓝色</span>
      <br>
      <br>
      <span style="color:white">改进蝙蝠算法粒子颜色:</span>
      <span style="color:rgb(194, 24, 91)">平红色</span>
      <br>
      <span style="color:white">原始蝙蝠算法粒子颜色:</span>
      <span style="color:rgb(48, 63, 159)">深蓝色</span>
      <br>
    </span>
    <div id="container"></div>
  </v-card>
</template>

<script>
import bus from "../bus/bus.js";
import $ from "jquery";
import * as THREE from "three";

import global from "../store/Common";
const OrbitControls = require("three-orbit-controls")(THREE);

import { Interaction } from "three.interaction";
import { clearInterval } from "timers";

import helvetikerRegular from "../assets/helvetiker_regular.typeface.json";

export default {
  data() {
    return {
      camera: "",
      scene: "",
      renderer: "",
      controls: "",
      interaction: "",
      timer: null,
      //初始化两个新的数组 如果数组为空 说明是第一次进行循环 如果不为空直接改变粒子的位置即可
      originalParticles: [],
      improvedParticles: []
    };
  },
  methods: {
    init(length, division) {
      this.scene = new THREE.Scene();
      this.camera = new THREE.PerspectiveCamera(
        60,
        window.innerWidth / window.innerHeight,
        1,
        10000
      );
      // camera.position.set(0, 0, 1.5).setLength(100);
      this.camera.position.y = length;
      this.camera.position.x = length;
      this.camera.position.z = length;

      this.renderer = new THREE.WebGLRenderer();

      this.interaction = new Interaction(
        this.renderer,
        this.scene,
        this.camera
      );

      // console.log(this.interaction);

      this.renderer.setSize(1300, 600);
      document
        .getElementById("container")
        .appendChild(this.renderer.domElement);
      this.controls = new OrbitControls(this.camera, this.renderer.domElement);

      let axes = new THREE.AxesHelper(3);
      this.scene.add(axes);

      this.initAxisNumber(length, division);
      this.initLineOfRealm(length);
    },

    initAxisNumber(length, division) {
      let loader = new THREE.FontLoader();
      let font = loader.parse(helvetikerRegular);
      //渲染x y z正半轴
      //使用相同的material材料可以节省内存开销
      let meshMaterial = new THREE.MeshBasicMaterial({
        flatShading: THREE.FlatShading,
        transparent: true,
        opacity: 0.9
      });

      let g = new THREE.TextGeometry("0", {
        font: font,
        size: 0.3,
        height: 0.1
      });

      let m = new THREE.Mesh(g, meshMaterial);
      m.position.set(0, 0, 0);
      this.scene.add(m);
      //xyz 轴
      for (let i = 1; i <= division; i++) {
        let x = (length / division) * i;
        let content = i + "";

        let geometry = new THREE.TextGeometry(content, {
          font: font,
          size: 0.3,
          height: 0.1
        });

        //正半轴上面的数字
        let meshX = new THREE.Mesh(geometry, meshMaterial);
        let meshY = new THREE.Mesh(geometry, meshMaterial);
        let meshZ = new THREE.Mesh(geometry, meshMaterial);

        meshX.position.set(x, 0, 0);
        meshY.position.set(0, x, 0);
        meshZ.position.set(0, 0, x);

        let _content = "-" + i;
        let _geometry = new THREE.TextGeometry(_content, {
          font: font,
          size: 0.3,
          height: 0.1
        });

        //负半轴
        let _meshX = new THREE.Mesh(_geometry, meshMaterial);
        let _meshY = new THREE.Mesh(_geometry, meshMaterial);
        let _meshZ = new THREE.Mesh(_geometry, meshMaterial);

        _meshX.position.set(-x, 0, 0);
        _meshY.position.set(0, -x, 0);
        _meshZ.position.set(0, 0, -x);

        this.scene.add(meshX);
        this.scene.add(meshY);
        this.scene.add(meshZ);

        this.scene.add(_meshX);
        this.scene.add(_meshY);
        this.scene.add(_meshZ);
      }
    },
    //修改xyz轴
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
    },
    //移除scene中的
    removePreviousParticles() {
      let length = 0;
      for (let i = 0; i < length; i++) {
        this.improvedParticles[i].geometry.dispose();
        this.improvedParticles[i].material.dispose();

        this.originalParticles[i].geometry.dispose();
        this.originalParticles[i].material.dispose();
      }
    },
    //从后端获取的数据放入场景中

    injectParticles(improved, original) {
      let improvedParticles = [];
      let originalParticles = [];

      let gemometry = new THREE.SphereGeometry(0.1, 16, 16);
      //red
      let material1 = new THREE.MeshBasicMaterial({ color: 0xc2185b });
      //blue
      let material2 = new THREE.MeshBasicMaterial({ color: 0x303f9f });

      //如果是第一次遍历 需要初始化例子
      if (
        this.originalParticles.length === 0 ||
        this.improvedParticles.length === 0
      ) {
        for (let i = 0; i < improved.length; i++) {
          let sphere = new THREE.Mesh(gemometry, material1);

          sphere.position.y = improved[i].y;
          sphere.position.z = improved[i].z;
          sphere.position.x = improved[i].x;

          sphere.once("click", function(m) {
            let position = m.data.target.position;
            bus.$emit("particlePosition", {
              x: position.x,
              y: position.y,
              z: position.z,
              type: "success"
            });
          });

          this.scene.add(sphere);
          improvedParticles.push(sphere);
        }

        for (let i = 0; i < original.length; i++) {
          let sphere = new THREE.Mesh(gemometry, material2);

          sphere.position.y = original[i].y;
          sphere.position.z = original[i].z;
          sphere.position.x = original[i].x;

          sphere.once("click", function(m) {
            let position = m.data.target.position;
            bus.$emit("particlePosition", {
              x: position.x,
              y: position.y,
              z: position.z,
              type: "success"
            });
          });
          this.scene.add(sphere);
          originalParticles.push(sphere);
        }

        this.improvedParticles = improvedParticles;
        this.originalParticles = originalParticles;
        //后面遍历的时候直接修改粒子的位置
      } else {
        for (let i = 0; i < improved.length; i++) {
          this.improvedParticles[i].position.x = improved[i].x;
          this.improvedParticles[i].position.y = improved[i].y;
          this.improvedParticles[i].position.z = improved[i].z;
        }
        for (let i = 0; i < original.length; i++) {
          let sphere = this.originalParticles[i];
          this.originalParticles[i].position.x = original[i].x;
          this.originalParticles[i].position.y = original[i].y;
          this.originalParticles[i].position.z = original[i].z;
        }
      }
    },
    animate() {
      requestAnimationFrame(this.animate);
      this.render();
    },
    render() {
      this.renderer.render(this.scene, this.camera);
    },
    listenParticleData() {
      //监听是否有数据
      bus.$on("particleData", res => {
        let code = res.data.data.iteration;

        let improved = res.data.data.improved;
        let original = res.data.data.original;
        if (code != global.generation - 1) {
          this.injectParticles(improved, original);
        } else {
          bus.$emit("alertContent", {
            message: "函数迭代完成！",
            type: "success"
          });
          bus.$emit("stop", "test");
        }
      });
    },
    //监听是否有重置数据
    listenReset() {
      bus.$on("reset", res => {
        global.firstTime = true;
        let length = this.improvedParticles.length;
        for (let i = 0; i < length; i++) {
          this.scene.remove(this.improvedParticles[i]);
          this.scene.remove(this.originalParticles[i]);
        }
        this.removePreviousParticles();

        this.improvedParticles = [];
        this.originalParticles = [];
      });
    }
  },
  mounted() {
    this.init(10, 10);
    this.animate();

    this.listenParticleData();
    this.listenReset();
  }
};
</script>

<style>
.center {
  width: 1300px;
  height: 600px;
  margin: 0 auto;
}
/* 对应生成的canvas标签 */
canvas {
  width: 100%;
  height: 100%;
}
</style>
