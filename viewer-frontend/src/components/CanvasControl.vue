<template>
  <div class="text-center">
    <v-bottom-sheet inset>
      <!-- 模拟触发事件 -->
      <template v-slot:activator="{ on }">
        <!-- open player 点击的时候设置这个on是一个事件 点击的时候会传递-->
        <v-btn
          id="mock_click"
          color="red"
          dark
          v-on="on"
          class="mock"
        > 
         </v-btn>
      </template>

      <!-- main content -->
      <v-card tile>
        <v-progress-linear
          :value="50"
          class="my-0"
          height="3"
        ></v-progress-linear>

        <v-list>
          <v-list-item>
            <v-list-item-content>
              <v-list-item-title>The Walker</v-list-item-title>
              <v-list-item-subtitle>Fitz & The Trantrums</v-list-item-subtitle>
            </v-list-item-content>

            <v-spacer></v-spacer>

            <v-list-item-icon>
              <v-btn icon>
                <v-icon>mdi-rewind</v-icon>
              </v-btn>
            </v-list-item-icon>

            <v-list-item-icon :class="{ 'mx-5': $vuetify.breakpoint.mdAndUp }">
              <v-btn icon>
                <v-icon>mdi-pause</v-icon>
              </v-btn>
            </v-list-item-icon>

            <v-list-item-icon
              class="ml-0"
              :class="{ 'mr-3': $vuetify.breakpoint.mdAndUp }"
            >
              <v-btn icon>
                <v-icon>mdi-fast-forward</v-icon>
              </v-btn>
            </v-list-item-icon>
          </v-list-item>
        </v-list>
      </v-card>
    </v-bottom-sheet>
  </div>
</template>

<script>
import bus from "../bus/bus.js"
import $ from 'jquery'
export default{
    data(){
        return {

        }
    },
    mounted(){
        this.activateCanvasControl()
       
    },
    methods:{
        activateCanvasControl(){
            bus.$on('activateCanvasControl', res =>{
              $("#mock_click").trigger("click")
            })
        }
    },
    beforeDestroy(){
        bus.$off('activateCanvasControl');
    }
}
</script>

<style>
.mock{
  visibility: hidden;
}
</style>
