<template>
  <div>
    <span style=" margin-left:1000px">
      <span>
        改进蝙蝠算法:
        <span style="color:rgb(58, 85, 117);">浅蓝色</span>
      </span>
      <span>
        原始蝙蝠算法:
        <span style="color:rgb(119, 58, 57)">深红色</span>
      </span>
    </span>
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
          text: "函数标准差随迭代次数变化"
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

        let improvedDataSeries = { type: "line" };
        let originalDataSeries = { type: "line" };

        let improvedPoints = [];
        let originalPoints = [];

        for (let i = 0; i < length; i++) {
          improvedPoints.push({
            x: result[i].iteration,
            y: parseFloat(result[i].improvedStd)
          });
          originalPoints.push({
            x: result[i].iteration,
            y: parseFloat(result[i].originalStd)
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