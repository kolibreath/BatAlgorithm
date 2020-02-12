<template>
  <div class="small">
    <canvas id="myChart2" width="400px" height="400px"></canvas>
  </div>
</template>
<script>
import Chart from "chart.js";

export default {
  components: {},
  data() {
    return {};
  },
  mounted() {
    var ctx2 = document.getElementById("myChart2");

    var myChart2 = new Chart(ctx2, {
      type: "line",
      data: {
        labels: [
          "January",
          "February",
          "March",
          "April",
          "May",
          "June",
          "July",
          "August",
          "September",
          "October",
          "November",
          "December"
        ],
        datasets: [
          {
            label: "test1",
            backgroundColor: "rgba(225,10,10,0.3)",
            borderColor: "rgba(225,103,110,1)",
            borderWidth: 1,
            pointStrokeColor: "#fff",
            pointStyle: "crossRot",
            data: [65, 59, 0, 81, 56, 10, 40, 22, 32, 54, 10, 30],
            cubicInterpolationMode: "monotone",
            spanGaps: "false",
            fill: "false"
          }
        ]
      },
      options: {}
    });
  },
  methods: {}
};
</script>
<style>
.small {
  width: 500px;
  height: 500px;
}
</style>