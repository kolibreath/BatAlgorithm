<template>
  <v-bottom-navigation :value="activeBtn" grow color="teal">
    <v-btn @click="canvasControl">
      <span>演示控制</span>
      <v-icon>mdi-history</v-icon>
    </v-btn>

    <v-btn @click="reset">
      <span>重置</span>
      <v-icon>mdi-autorenew</v-icon>
    </v-btn>
    <v-btn @click="pause">
      <span>暂停</span>
      <v-icon>mdi-pause</v-icon>
    </v-btn>
  </v-bottom-navigation>
</template>

<script>
import bus from "../bus/bus.js";
import axios from "axios";
export default {
  data() {
    return {
      activeBtn: 1
    };
  },
  methods: {
    //使用总线发送事件
    canvasControl() {
      bus.$emit("activateCanvasControl", "test");
    },
    reset() {
      bus.$emit("stop", "test");
      axios
        .post("http://localhost:8081/api/reset/")
        .then(function(response) {
          //重置后端的数据
          bus.$emit("alertContent", { message: "重置算法", type: "success" });
          //重置前端的数据
          bus.$emit("reset", "test");
        })
        .catch(function(error) {
          // bus.$emit("alertContent", {
          //   message: "函数执行失败",
          //   type: "error"
          // });
        });
    },
    //暂停算法进行
    pause() {
      bus.$emit("pause", "test");
    }
  },
  mounted() {}
};
</script>

<style scoped>
</style>
