<template>
  <v-container>
    <!-- <v-row justify="space-around">
      <v-col cols="12">
        <v-select
          v-model="color"
          :items="colors"
          label="Color"
        ></v-select>
      </v-col>

      <v-switch
        v-model="drawer"
        class="ma-2"
        label="v-model"
      ></v-switch>

      <v-switch
        v-model="miniVariant"
        class="ma-2"
        label="Mini variant"
      ></v-switch>

      <v-switch
        v-model="expandOnHover"
        class="ma-2"
        label="Expand on hover"
      ></v-switch>

      <v-switch
        v-model="background"
        class="ma-2"
        label="Background"
      ></v-switch>

      <v-switch
        v-model="right"
        class="ma-2"
        label="Right"
      ></v-switch>
    </v-row>-->
    <v-navigation-drawer
      v-model="drawer"
      :color="color"
      :expand-on-hover="expandOnHover"
      :mini-variant="miniVariant"
      :src="bg"
      absolute
      dark
    >
      <v-list dense nav class="py-0">
        <v-list-item two-line :class="miniVariant && 'px-0'">
          <v-list-item-content>
            <v-list-item-title>算法演示系统</v-list-item-title>
            <v-list-item-subtitle>基于ThreeJS的算法演示系统</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>

        <v-list-item v-for="item in items" :key="item.title" link @click="toggle(item.id)">
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </v-container>
</template>

<script>
import bus from "../bus/bus.js";

export default {
  data() {
    return {
      drawer: true,
      items: [
        { title: "三维图像", icon: "mdi-airplay", id: 1 },
        { title: "仪表盘", icon: "mdi-view-dashboard", id: 2 },
        { title: "参数设置", icon: "mdi-image", id: 3 }
      ],
      color: "primary",
      right: false,
      miniVariant: false,
      expandOnHover: true,
      background: false
    };
  },
  computed: {
    bg() {
      return this.background
        ? "https://cdn.vuetifyjs.com/images/backgrounds/bg-2.jpg"
        : undefined;
    }
  },
  methods: {
    toggle(id) {
      switch (id) {
        case 1:
          this.$router.replace("canvas").catch(err => console.log(err));
          break;
        case 2:
          this.$router.replace("dashboard").catch(err => console.log(err));
          break;
        case 3:
          bus.$emit("paramsDialog", "test");
          break;
      }
    }
  }
};
</script>

<style>
</style>
