<template>
  <table class="table table-striped table-margin">
    <thead>
      <tr>
        <th v-for="(heading, index) in tableHeadings" :key="index">{{heading.name}}</th>
      </tr>
    </thead>

    <tbody v-if="rowData.length != 0">
      <table-row
        v-for="( row, index ) in rowData"
        v-bind:row="row"
        :key="index"
        :headings="tableHeadings"
      ></table-row>
    </tbody>
  </table>
</template>

<script>
import TableRow from "./ResultListRow";
import axios from "axios";

import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";

export default {
  components: { TableRow },
  methods: {
    initRowData() {
      axios.get("http://localhost:8081/api/dashboard").then(res => {
        let data = res.data.data;
        let length = data.length;
        for (let i = 0; i < data.length; i++) {
          //先完成improved list
          let obj = {
            iteration: data[i].iteration,
            improvedMax: data[i].improvedMax,
            improvedMin: data[i].improvedMin,
            improvedAve: data[i].improvedAve,
            improvedStd: data[i].improvedStd,

            originalMax: data[i].originalMax,
            originalMin: data[i].originalMin,
            originalAve: data[i].originalAve,
            originalStd: data[i].originalStd
          };

          this.rowData.push(obj);
        }
      });
    }
  },
  data() {
    return {
      tableHeadings: [
        { name: "迭代次数", value: "iteration" },
        { name: "改进最大值", value: "improvedMax" },
        { name: "改进最小值", value: "improvedMin" },
        { name: "改进平均值", value: "improvedAve" },
        { name: "改进标准差", value: "improvedStd" },

        { name: "原始最大值", value: "originalMax" },
        { name: "原始最小值", value: "originalMin" },
        { name: "原始平均值", value: "originalAve" },
        { name: "原始标准差", value: "originalStd" }
      ],
      rowData: []
    };
  },
  mounted() {
    //测试 是否是网络问题
    this.initRowData();
  }
};
</script>

<style scoped>
.table-margin {
  margin-top: 30px;
  margin-left: 55px;
}
</style>
