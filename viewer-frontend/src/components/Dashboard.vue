<template>
  <div>
    <div id="chartContainer" class="container"></div>
    <result-list></result-list>
  </div>
</template>
<script>
import * as CanvasJS from "../canvas/canvasjs.min.js";
import axios from "axios";

import ResultList from "./ResultList.vue";

export default {
  components: {
    ResultList
  },
  data() {
    //放入图像中的data的数据结构
    //dataPoints: 数据的坐标
    //dataSeries 数据的形状
    //整合到一起的data结果
    return {
      data: []
    };
  },
  methods: {
    initChart() {
      let chart = new CanvasJS.Chart("chartContainer", {
        animationEnabled: true,
        zoomEnabled: true,
        title: {
          text: "运算结果"
        },
        axisY: {
          includeZero: false
        },
        data: this.data
      });

      chart.render();
    },
    initData() {
      axios.get("http://localhost:8081/api/dashboard").then(res => {
        let result = res.data.data;
        let length = result.length;

        console.log(result);

        let improvedDataSeries = { type: "line" };
        let originalDataSeries = { type: "line" };

        let improvedPoints = [];
        let originalPoints = [];

        for (let i = 0; i < length; i++) {
          improvedPoints.push({
            x: result[i].iteration,
            y: result[i].improvedMin
          });
          originalPoints.push({
            x: result[i].iteration,
            y: result[i].originMin
          });
        }

        improvedDataSeries.dataPoints = improvedPoints;
        originalDataSeries.dataPoints = originalPoints;

        this.data.push(improvedDataSeries);
        this.data.push(originalDataSeries);

        this.initChart();
      });
    }
  },
  mounted() {
    //获取数据 并且初始化chart
    this.initData();
  }
};
</script>
<style scoped>
.container {
  height: 300px;
  width: 100%;
  margin-left: 200px;
}
</style>