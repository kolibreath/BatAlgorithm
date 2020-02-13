<template>
  <tr>
    <td v-for="(heading, index) in headings" :key="index">{{row[heading.value]}}</td>
  </tr>
</template>

<script>
import $ from 'jquery'
import 'bootstrap'

export default {
  name: "tableRow",
  //从父组件传递过来的数据
  props: ["row", "headings"],
  mounted() {
    console.log(this.row);
  }
};
</script>

<style>
</style>
