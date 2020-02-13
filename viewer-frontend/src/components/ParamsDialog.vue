<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="900px">
      <template v-slot:activator="{ on }">
        <!-- 模拟发送事件 -->
        <v-btn color="primary" dark v-on="on" id="mockBtn" style="visibility:hidden">Open Dialog</v-btn>
      </template>

      <v-card>
        <v-card-title>
          <span class="headline">{{defaultTitle}}</span>
        </v-card-title>
        <v-card-subtitle>
          <span>当前的测试函数{{functionNames[functionIndex]}}</span>
        </v-card-subtitle>

        <v-card-text>
          <v-container>
            <!-- progress bar 种群数量 -->
            <v-row>
              <v-col cols="12" sm="2" class="my-auto">
                <span>种群数量</span>
              </v-col>

              <v-col cols="12" sm="10">
                <v-slider
                  v-model="population"
                  :tick-labels="popLabels"
                  :max="popLabels.length - 1"
                  step="1"
                  ticks="always"
                  tick-size="4"
                ></v-slider>
              </v-col>
            </v-row>

            <!-- 迭代次数 -->
            <v-row>
              <v-col cols="12" sm="2" class="my-auto">
                <span>迭代次数</span>
              </v-col>

              <v-col cols="12" sm="10">
                <v-slider
                  v-model="generation"
                  :tick-labels="genLabels"
                  :max="genLabels.length  - 1"
                  step="1"
                  ticks="always"
                  tick-size="4"
                ></v-slider>
              </v-col>
            </v-row>

            <!-- 脉冲频率 -->
            <v-row>
              <v-col cols="12" sm="2" class="my-auto">
                <span>脉冲频率</span>
              </v-col>

              <v-col cols="12" sm="10">
                <v-slider
                  v-model="pulseRate"
                  :tick-labels="pulseLabels"
                  :max="pulseLabels.length  - 1"
                  step="1"
                  ticks="always"
                  tick-size="4"
                ></v-slider>
              </v-col>
            </v-row>

            <!-- 频率最高值 -->
            <v-row>
              <v-col cols="12" sm="2" class="my-auto">
                <span>最大频率</span>
              </v-col>

              <v-col cols="12" sm="10">
                <v-slider
                  v-model="frequency"
                  :tick-labels="freLabels"
                  :max="freLabels.length  - 1"
                  step="1"
                  ticks="always"
                  tick-size="4"
                ></v-slider>
              </v-col>
            </v-row>

            <!-- 响度调整 -->
            <v-row>
              <v-col cols="12" sm="2" class="my-auto">
                <span>响度调整</span>
              </v-col>

              <v-col cols="12" sm="10">
                <v-slider
                  v-model="loudness"
                  :tick-labels="loudnessLabels"
                  :max="loudnessLabels.length  - 1"
                  step="1"
                  ticks="always"
                  tick-size="4"
                ></v-slider>
              </v-col>
            </v-row>

            <el-transfer v-model="value" :data="data" class="transfer-margin"></el-transfer>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="cancelConfig">Close</v-btn>
          <v-btn color="blue darken-1" text @click="confirmConfig">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>
<script>
import bus from "../bus/bus.js";
import $ from "jquery";
import global from "../store/Common";
export default {
  data() {
    const generateData = _ => {
      const data = [];
      for (let i = 0; i <= global.functionNames.length; i++) {
        data.push({
          key: i,
          label: global.functionNames[i],
          disabled: false
        });
      }
      return data;
    };
    // 当前进行的函数不能够在放入
    return {
      dialog: false,
      population: global.population,
      generation: global.generation,
      pulseRate: global.pulseRate,
      frequency: global.frequency,
      defaultTitle: global.defaultTitle,
      functionQueue: global.funtionQueue,
      functionIndex: global.functionIndex,
      functionNames: global.functionNames,
      loudness: global.loudness,
      popLabels: ["5", "10", "15", "20"],
      genLabels: ["100", "300", "500", "700", "900", "1000"],
      pulseLabels: ["0.25", "0.50", "0.75", "1.0"],
      freLabels: ["0.75", "1.25", "1.75", "2.0", "2.5"],
      loudnessLabels: ["0.35", "0.40", "0.45", "0.50"],
      value: [],
      data: generateData()
    };
  },
  mounted() {
    this.paramsDialog();
  },
  beforeDestroy: {},
  methods: {
    paramsDialog() {
      bus.$on("paramsDialog", res => {
        $("#mockBtn").trigger("click");
      });
    },
    confirmConfig() {
      this.dialog = false;
    },
    cancelConfig() {
      this.dialog = false;
    }
  }
};
</script>

<style scoped>
.transfer-margin {
  margin-left: 120px;
}
</style>

