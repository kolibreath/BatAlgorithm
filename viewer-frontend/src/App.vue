<template>
  <v-app>
    <!-- app bar -->
    <div>
      <v-app-bar app color="primary" dark>
        <div class="d-flex align-center">
          <v-img
            alt="Vuetify Logo"
            class="shrink mr-2"
            contain
            src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
            transition="scale-transition"
            width="40"
          />

          <v-img
            alt="Vuetify Name"
            class="shrink mt-1 hidden-sm-and-down"
            contain
            min-width="100"
            src="https://cdn.vuetifyjs.com/images/logos/vuetify-name-dark.png"
            width="100"
          />
        </div>

        <v-spacer></v-spacer>

        <v-btn href="https://github.com/vuetifyjs/vuetify/releases/latest" target="_blank" text>
          <span class="mr-2">Latest Release</span>
          <v-icon>mdi-open-in-new</v-icon>
        </v-btn>
      </v-app-bar>
    </div>

    <!-- content -->
    <v-content class="wrapper">
      <LeftDrawer></LeftDrawer>
      <Main></Main>
      <CanvasControls></CanvasControls>
      <!-- 渲染的组件显示在这里 -->
      <router-view></router-view>
      <ParamsDialog></ParamsDialog>

      <BottomTweaks class="footer"></BottomTweaks>
    </v-content>
  </v-app>
</template>

<script>
// import HelloWorld from './components/HelloWorld';
import LeftDrawer from "./components/LeftDrawer";
import Main from "./components/Main";
import BottomTweaks from "./components/BottomTweaks";
import CanvasControls from "./components/CanvasControl";
import Canvas from "./components/Canvas";
import ParamsDialog from "./components/ParamsDialog";

import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

import Vue from "vue";
import bus from "./bus/bus.js";
Vue.use(ElementUI);

import global from "./store/Common.vue";

export default {
  name: "App",
  components: {
    LeftDrawer,
    Main,
    BottomTweaks,
    CanvasControls,
    ParamsDialog
  },
  data: () => ({
    //
  }),
  //todo 如果进行路由切换的时候vue的状态？
  //当加载完成主页的时候就弹出canvasControl.vue
  mounted() {
    bus.$emit("activateCanvasControl", "test");
    //监听alert消息
    bus.$on("alertContent", res => {
      this.createAlert(res);
    });
    bus.$on("particlePosition", res => {
      this.showParticlePosition(res);
    });

    this.$router.push("canvas");
  },
  methods: {
    createAlert(res) {
      this.$message({
        showClose: true,
        message: res.message,
        type: res.type
      });
    },
    showParticlePosition(res) {
      console.log(res);
      this.$message({
        showClose: true,
        message: `粒子坐标 (
          ${parseFloat(res.x).toFixed(2)},
          ${parseFloat(res.y).toFixed(2)}, 
          ${parseFloat(res.z).toFixed(2)})`,
        type: res.type
      });
    }
  }
};
</script>
<style>
.wrapper {
  position: relative;
  min-height: 100%;
  padding-bottom: 50px;
  box-sizing: border-box;
}
.footer {
  position: absolute;
  bottom: 0;
  height: 100px;
}
</style>
