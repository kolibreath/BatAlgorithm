<template>
  <table class="table table-striped">
    <thead>
      <tr>
        <th v-for="(heading, index) in tableHeadings" :key="index">{{heading}}</th>
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
        {
          iteration:1,
          max:1,
          min:1,
          ave:1,
          std:1,
        },
        {
          iteration:1,
          max:1,
          min:1,
          ave:1,
          std:1,
        },{
          iteration:1,
          max:1,
          min:1,
          ave:1,
          std:1,
        },{
          iteration:1,
          max:1,
          min:1,
          ave:1,
          std:1,
        }
      ]
    };
  },
  mounted() {
    //测试 是否是网络问题
    // this.initRowData();
  }
};
</script>

<style>
</style>
