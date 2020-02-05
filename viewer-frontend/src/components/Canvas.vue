<template>
    <v-card  class="center" >
        <div id="container"></div>
    </v-card>
</template>

<script>

import $ from 'jquery';
import * as THREE from 'three';
const OrbitControls = require('three-orbit-controls')(THREE);

export default {
    data(){
        return {
            camera:'',
            scene:'',
            renderer:'',
            controls:''
        }
    },
    methods:{
        init(length, division){
            this.scene = new THREE.Scene();
            this.camera = new THREE.PerspectiveCamera(60, window.innerWidth / window.innerHeight, 1, 10000);
            // camera.position.set(0, 0, 1.5).setLength(100);
            this.camera.position.y = length;
            this.camera.position.x = length;
            this.camera.position.z = length;

            this.renderer = new THREE.WebGLRenderer();
            this.renderer.setSize(window.innerWidth, window.innerHeight);
            //renderer.setClearColor(0xcccccc);
            // document.body.appendChild(renderer.domElement);
            document.getElementById("container").appendChild(this.renderer.domElement);

            this.controls = new OrbitControls(this.camera, this.renderer.domElement);
            console.log(this.controls);

            let gridXz = new THREE.GridHelper(length,division,0xEED5B7, 0xEED5B7);
            gridXz.position.x = length / 2;
            gridXz.position.y = 0;
            gridXz.position.z = length / 2;
            this.scene.add(gridXz);

            let gridXy = new THREE.GridHelper(length,division,0xEED5B7, 0xEED5B7);
            gridXy.rotation.x = Math.PI /2 ;
            gridXy.position.x = length / 2;
            gridXy.position.y = length / 2;
            gridXy.position.z = 0;
            this.scene.add(gridXy);

            let gridYz = new THREE.GridHelper(length,division,0xEED5B7, 0xEED5B7);
            gridYz.rotation.z = Math.PI /2;
            gridYz.position.x = 0;
            gridYz.position.y = length / 2;
            gridYz.position.z = length / 2;
            this.scene.add(gridYz);

            let axes = new THREE.AxesHelper(3);
            this.scene.add(axes);

            this.initAxisNumber(length, division);
            this.initLineOfRealm(length);
            this.initParticles(length);
        },

        initAxisNumber(length, division){
            let loader = new THREE.FontLoader();
            loader.load("fonts/helvetiker_regular.typeface.json", function(font) {
                //渲染x y z轴
                for (let i = 0; i <= division ; i++) {
                    let x = length / division * (i);
                    let content = i + "";
                    let geometry = new THREE.TextGeometry(content, {
                        font: font,
                        size: 3,
                        height: 0.1,
                    }) ;
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

                }
            );
        },
        initLineOfRealm(length){
            let lineMaterial = new THREE.LineBasicMaterial({
                vertexColors:THREE.VertexColors,
                linewidth:3
            });
            let color1 = new THREE.Color(0xEE00DD), color2 = new THREE.Color(0xEEC0B0);


            //Y轴
            let starts = [new THREE.Vector3(0,0,0),new THREE.Vector3(0,0,0),new THREE.Vector3(0,0,0)];
            let ends = [ new THREE.Vector3(0,0,length), new THREE.Vector3(0,length,0), new THREE.Vector3(length,0,0)];


            for(let i =0;i<3;i++){
                let geom = new THREE.Geometry();
                geom.vertices.push(starts[i]);
                geom.vertices.push(ends[i]);
                geom.colors.push(color1,color2);

                let line = new THREE.Line(geom, lineMaterial);
                console.log(line);
                this.scene.add(line);
            }
        },
        initParticles(length){
            let particles = this.receiveParticles();
            for (let i = 0; i < particles.length ; i++) {
                let gemometry = new THREE.SphereGeometry(1,16,16);
                let material = new THREE.MeshBasicMaterial({color:0xffff00});
                let sphere = new THREE.Mesh(gemometry, material);

                sphere.position.x = particles[i].x * length;
                sphere.position.y = particles[i].y * length;
                sphere.position.z = particles[i].z * length;

                this.scene.add(sphere);
            }
        },
        receiveParticles() {
            //test random
            let particles = [];
            for(let i = 0; i< 200;i++){
                let x = Math.random();
                let y = Math.random();
                let z = Math.random();

                let object = {
                    x:x,
                    y:y,
                    z:z
                };
                particles[i] = object;
            }
            return particles;
        },
        animate() {
            requestAnimationFrame(this.animate);
            this.render();
        },
        render() {
            this.renderer.render(this.scene, this.camera);
        }
    },
    mounted(){
        this.init(80,10);
        this.animate();
    }
}
</script>

<style>
.center{
    width:1300px;
    height: 600px;
    margin: 0 auto
}
/* 对应生成的canvas标签 */
canvas {
    width: 100%;
    height: 100%;
}

</style>
