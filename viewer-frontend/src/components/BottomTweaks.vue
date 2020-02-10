<template>
  <v-bottom-navigation :value="activeBtn" grow color="teal">
    <v-btn @click="canvasControl">
      <span>演示控制</span>
      <v-icon>mdi-history</v-icon>
    </v-btn>

    <v-btn>
      <span>过滤器</span>
      <v-icon>mdi-alien</v-icon>
    </v-btn>

    <v-btn @click="reset">
      <span>重置</span>
      <v-icon>mdi-autorenew</v-icon>
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
          bus.$emit("alertContent", { message: "重置算法", type: "success" });
        })
        .catch(function(error) {
          bus.$emit("alertContent", {
            message: "函数执行失败",
            type: "error"
          });
        });
    }
  }
};
</script>

<style scoped>
</style>
