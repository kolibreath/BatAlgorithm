<template>
  <v-card class="center">
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

      let gridXz = new THREE.GridHelper(
        length * 2,
        division,
        0xeed5b7,
        0xeed5b7
      );
      //   gridXz.position.x = length / 2;
      //   gridXz.position.y = 0;
      //   gridXz.position.z = length / 2;
      this.scene.add(gridXz);

      let gridXy = new THREE.GridHelper(
        length * 2,
        division,
        0xeed5b7,
        0xeed5b7
      );
      gridXy.rotation.x = Math.PI / 2;
      //   gridXy.position.x = length / 2;
      //   gridXy.position.y = length / 2;
      //   gridXy.position.z = 0;
      this.scene.add(gridXy);

      let gridYz = new THREE.GridHelper(
        length * 2,
        division,
        0xeed5b7,
        0xeed5b7
      );
      gridYz.rotation.z = Math.PI / 2;
      //   gridYz.position.x = 0;
      //   gridYz.position.y = length / 2;
      //   gridYz.position.z = length / 2;
      this.scene.add(gridYz);

      let axes = new THREE.AxesHelper(3);
      this.scene.add(axes);

      this.initAxisNumber(length, division);
      this.initLineOfRealm(length);
      //测试的用的随机生成粒子
      // this.initParticles(length);
    },

    initAxisNumber(length, division) {
      let loader = new THREE.FontLoader();
      loader.load("fonts/helvetiker_regular.typeface.json", function(font) {
        //渲染x y z轴
        for (let i = 0; i <= division; i++) {
          let x = (length / division) * i;
          let content = i + "";
          let geometry = new THREE.TextGeometry(content, {
            font: font,
            size: 3,
            height: 0.1
          });
          //创建法向量材质
          let meshMaterial = new THREE.MeshNormalMaterial({
            flatShading: THREE.FlatShading,
            transparent: true,
            opacity: 0.9
          });
          let meshX = new THREE.Mesh(geometry, meshMaterial);
          let meshY = new THREE.Mesh(geometry, meshMaterial);
          let meshZ = new THREE.Mesh(geometry, meshMaterial);
          meshX.position.set(x, 0, 0);
          meshY.position.set(0, x, 0);
          meshZ.position.set(0, 0, x);

          this.scene.add(meshX);
          this.scene.add(meshY);
          this.scene.add(meshZ);
        }
      });
    },
    //修改xyz轴
    initLineOfRealm(length) {
      let lineMaterial = new THREE.LineBasicMaterial({
        vertexColors: THREE.VertexColors,
        linewidth: 3
      });
      let color1 = new THREE.Color(0xee00dd),
        color2 = new THREE.Color(0xeec0b0);

      //Y轴
      let starts = [
        new THREE.Vector3(0, 0, 0),
        new THREE.Vector3(0, 0, 0),
        new THREE.Vector3(0, 0, 0)
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
        geom.colors.push(color1, color2);

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
      this.removePreviousParticles();

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

          this.scene.add(sphere);
          improvedParticles.push(sphere);

          sphere.on("click", function(m) {
            bus.$emit("particlePosition", {
              x: improved[i].x,
              y: improved[i].y,
              z: improved[i].z,
              type: "success"
            });
          });
        }

        for (let i = 0; i < original.length; i++) {
          let sphere = new THREE.Mesh(gemometry, material2);

          sphere.position.y = original[i].y;
          sphere.position.z = original[i].z;
          sphere.position.x = original[i].x;

          this.scene.add(sphere);
          originalParticles.push(sphere);

          sphere.on("click", function(m) {
            bus.$emit("particlePosition", {
              x: improved[i].x,
              y: improved[i].y,
              z: improved[i].z,
              type: "success"
            });
          });
        }

        this.improvedParticles = improvedParticles;
        this.originalParticles = originalParticles;
      } else {
        for (let i = 0; i < improved.length; i++) {
          this.improvedParticles[i].position.x = improved[i].x;
          this.improvedParticles[i].position.y = improved[i].y;
          this.improvedParticles[i].position.z = improved[i].z;
        }
        for (let i = 0; i < original.length; i++) {
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
        console.log("代数" + global.generation + "当前index" + code);
        let improved = res.data.data.improved;
        let original = res.data.data.original;
        //坐标系太大了 需要换算大小
        if (code != global.generation - 1) {
          console.log("收到数据");
          console.log(res.iteration);
          this.injectParticles(improved, original);
        } else {
          console.log("停止算法执行");
          bus.$emit("stop", "test");
        }
      });
    }
  },
  mounted() {
    this.init(10, 10);
    this.animate();

    this.listenParticleData();
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
