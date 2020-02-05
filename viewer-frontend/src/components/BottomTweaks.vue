<template>
  <v-bottom-navigation
    :value="activeBtn"
    grow
    color="teal"
  >
    <v-btn @click="canvasControl">
      <span>演示控制</span>
      <v-icon>mdi-history</v-icon>
    </v-btn>

    <v-btn>
      <span>过滤器</span>
      <v-icon>mdi-alien</v-icon>
    </v-btn>

    <v-btn>
      <span>Nearby</span>
      <v-icon>mdi-map-marker</v-icon>
    </v-btn>
  </v-bottom-navigation>
</template>

<script>
import bus from "../bus/bus.js"
  export default {
    data () {
      return {
        activeBtn: 1,
      }
    },
    methods:{
        //使用总线发送事件
        canvasControl(){
            bus.$emit('activateCanvasControl',"test")
        }
    }
  }
</script>

<style scoped>

</style>
