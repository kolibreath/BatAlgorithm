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

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

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
            max: data[i].improvedMax,
            min: data[i].improvedMin,
            ave: data[i].improvedAve,
            std: data[i].improvedStd
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
        { name: "最大值", value: "max" },
        { name: "最小值", value: "min" },
        { name: "平均值", value: "ave" },
        { name: "标准差", value: "std" }
      ],
      rowData: [
        
      ]
    };
  },
  mounted() {
    //测试 是否是网络问题
    this.initRowData();
  }
};
</script>

<style scoped>
.table-margin{
  margin-top:30px;
  margin-left:80px
}
</style>
