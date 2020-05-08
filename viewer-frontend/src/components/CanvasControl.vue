<template>
  <div class="text-center">
    <v-bottom-sheet inset v-model="isPlaying">
      <!-- 模拟触发事件 -->
      <template v-slot:activator="{ on }">
        <!-- open player 点击的时候设置这个on是一个事件 点击的时候会传递-->
        <v-btn id="mock_click" color="red" dark v-on="on" class="mock"></v-btn>
      </template>

      <!-- main content -->
      <v-card class="mx-auto" max-width="600" tile>
        <v-toolbar flat dense>
          <!-- 迭代速度控制 -->
          <v-toolbar-title>
            <span class="subheading">迭代速度</span>
          </v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>

        <v-card-text>
          <v-row class="mb-4" justify="space-between">
            <v-col class="text-left">
              <span class="display-3 font-weight-light" v-text="ticksLabels[bpm]"></span>
              <span class="subheading font-weight-light mr-1">秒/轮</span>
              <v-fade-transition>
                <v-avatar
                  v-if="isPlaying"
                  :color="color"
                  :style="{
                  animationDuration: animationDuration
                }"
                  class="mb-1 v-avatar--metronome"
                  size="12"
                ></v-avatar>
              </v-fade-transition>
            </v-col>
            <v-col class="text-right">
              <v-btn :color="color" dark depressed fab @click="toggle">
                <v-icon large>{{ isPlaying ? 'mdi-pause' : 'mdi-play' }}</v-icon>
              </v-btn>
            </v-col>
          </v-row>

          <!-- 进度条调整的下标 根据下标的值将速度赋值给speed 使用watch属性-->
          <v-slider
            v-model="bpm"
            :tick-labels="ticksLabels"
            :max="3"
            step="1"
            ticks="always"
            tick-size="4"
          >
            <template v-slot:prepend>
              <v-icon :color="color" @click="decrement">mdi-minus</v-icon>
            </template>

            <template v-slot:append>
              <v-icon :color="color" @click="increment">mdi-plus</v-icon>
            </template>
          </v-slider>
        </v-card-text>
      </v-card>
    </v-bottom-sheet>
  </div>
</template>

<script>
import bus from "../bus/bus.js";
import $ from "jquery";
import axios from "axios";
import global from "../store/Common";
import { setInterval, clearInterval } from "timers";

export default {
  // bpm index
  data() {
    return {
      bpm: 0,
      interval: null,
      isPlaying: false,
      // 修改计算 修改数据 修改显示秒/轮文字
      ticksLabels: ["0.005", "0.010", "0.015", "0.02"],
      speed: "0.010",
      //定时器的引用类型
      timer: null
    };
  },
  mounted() {
    this.activateCanvasControl();
    this.stopInterval();
    this.listenPause();

    //监听requireData
    this.requireData(function() {
      axios
        .post("http://localhost:8081/api/start/")
        .then(function(response) {
          bus.$emit("particleData", response);
        })
        .catch(function(error) {
          // bus.$emit("alertContent", {
          //   message: "函数执行失败",
          //   type: "error"
          // });
        });
    });
  },
  methods: {
    //监听事件
    activateCanvasControl() {
      bus.$on("activateCanvasControl", res => {
        $("#mock_click").trigger("click");
        this.isPlaying = false;
      });
    },
    decrement() {
      this.bpm--;
    },
    increment() {
      this.bpm++;
    },
    //开始按照固定的周期获取当前粒子的收敛状态
    requireData(request) {
      bus.$on("requireData", res => {
        console.log("收到require timer");
        global.timer = setInterval(request, parseFloat(this.speed) * 1000);
      });
    },
    stopInterval() {
      bus.$on("stop", res => {
        console.log("收到停止算法执行" + global.timer);
        clearInterval(global.timer);
      });
    },
    //给后端发送开始的消息
    async toggle() {
      this.isPlaying = !this.isPlaying;
      //如果是第一次请求，就开始执行函数并且获取第一次粒子的数据！
      // axios 默认使用表单请求！
      //https://github.com/Wscats/vue-tutorial/issues/16
      let message;
      //是否修改了速度 确定发送速度数据
      if (global.firstTime) {
        global.firstTime = false;
        message = "开始执行测试函数";

        //如果是firstTime 需要初始化
        const init = await axios
          .post("http://localhost:8081/api/init/" + global.functionIndex)
          .then(function(response) {
            console.log("完成初始化");
          })
          .catch(function(error) {
            console.log(error);
          });

        //总是拉取当前的设置 从Common.vue 中拿取设置
        const start = await axios
          .post("http://localhost:8081/api/start/")
          .then(function(response) {
            bus.$emit("alertContent", { message: message, type: "success" });
            bus.$emit("particleData", response);
            bus.$emit("requireData", "test");
            console.log("函数执行完成");
          })
          .catch(function(error) {
            // bus.$emit("alertContent", {
            //   message: "函数执行失败",
            //   type: "error"
            // });
          });
      } else {
        message = "测试函数重新开始执行";

        const request1 = await axios
          .post("http://localhost:8081/api/start/")
          .then(function(response) {
            bus.$emit("alertContent", { message: message, type: "success" });
            bus.$emit("particleData", response);
            bus.$emit("requireData", "test");
          })
          .catch(function(error) {
            // bus.$emit("alertContent", {
            //   message: "函数执行失败",
            //   type: "error"
            // });
          });
      }

      //总是把当前的速度提交
      let _speed = this.speed;

      const speedRequest = await axios({
        url: "http://localhost:8081/api/alterSpeed",
        method: "post",
        headers: {
          "Content-Type": "application/json"
        },
        data: JSON.stringify({
          speed: this.speed
        })
      })
        .then(function(response) {
          console.log("速度修改成功");
          bus.$emit("alertContent", {
            message: "速度修改成功",
            type: "success"
          });
          global.speed = _speed;
        })
        .catch(function(error) {
          console.log(error);
          bus.$emit("alertContent", { message: "速度修改失败", type: "error" });
        });

      global.firstTime = false;
    },
    //监听是否有暂停的消息 直接取消
    listenPause() {
      bus.$on("pause", res => {
        bus.$emit("alertContent", { message: "函数执行被暂停", type: "warn" });
        global.firstTime = false;
        clearInterval(global.timer);
      });
      this.firstTime = false;
    }
  },
  beforeDestroy() {
    bus.$off("activateCanvasControl");
  },
  computed: {
    color() {
      if (this.bpm == 0) return "indigo";
      if (this.bpm == 1) return "teal";
      if (this.bpm == 2) return "green";
      if (this.bpm == 3) return "orange";
      return "red";
    },
    //表示迭代时间
    animationDuration() {
      return `${0.5 / this.bpm}s`;
    }
  },
  watch: {
    bpm: function(val) {
      this.speed = this.ticksLabels[val];
      global.speed = this.speed;
    }
  }
};
</script>

<style>
.mock {
  visibility: hidden;
}

@keyframes metronome-example {
  from {
    transform: scale(0.5);
  }

  to {
    transform: scale(1);
  }
}

.v-avatar--metronome {
  animation-name: metronome-example;
  animation-iteration-count: infinite;
  animation-direction: alternate;
}
</style>
