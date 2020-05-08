<template>
  <v-container>
    <!-- 原始蝙蝠算法 -->
    <v-stepper v-model="e1">
      <v-stepper-header>
        <v-stepper-step :complete="e1 > 1" step="1">初始化解空间</v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step :complete="e1 > 2" step="2">变异</v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step step="3">选择新解</v-stepper-step>
      </v-stepper-header>

      <v-stepper-items>
        <v-stepper-content step="1">
          <v-card class="mb-12" color="grey lighten-1" height="200px">将蝙蝠种群随机初始化到整个解空间中</v-card>

          <v-btn color="primary" @click="e1 = 2">Continue</v-btn>

          <v-btn text>Cancel</v-btn>
        </v-stepper-content>

        <v-stepper-content step="2">
          <v-card class="mb-12" color="grey lighten-1" height="200px">按照公式对整个蝙蝠种群在进行变异，并且计算相关的适应值</v-card>

          <v-btn color="primary" @click="e1 = 3">Continue</v-btn>

          <v-btn text>Cancel</v-btn>
        </v-stepper-content>

        <v-stepper-content step="3">
          <v-card class="mb-12" color="grey lighten-1" height="200px">选择适应值较高的个体进行保留</v-card>

          <v-btn color="primary" @click="e1 = 2">Continue</v-btn>

          <v-btn text>Cancel</v-btn>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>

    <!-- 基于精英候选池的蝙蝠算法 -->
    <v-stepper v-model="e2" class="top">
      <v-stepper-header>
        <v-stepper-step :complete="e2 > 1" step="1">初始化解空间</v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step :complete="e2 > 2" step="2">选择精英</v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step :complete="e2 > 3" step="3">变异</v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step :complete="e2 > 4" step="4">选择新解</v-stepper-step>

        <v-divider></v-divider>

        <v-stepper-step step="5">扰动操作</v-stepper-step>
      </v-stepper-header>

      <v-stepper-items>
        <v-stepper-content step="1">
          <v-card class="mb-12" color="grey lighten-1" height="200px">将蝙蝠种群随机初始化到整个解空间中</v-card>

          <v-btn color="primary" @click="e2 = 2">Continue</v-btn>

          <v-btn text>Cancel</v-btn>
        </v-stepper-content>

        <v-stepper-content step="2">
          <v-card class="mb-12" color="grey lighten-1" height="200px">按照公式替换精英池中的个体，修改精英池中的精英个体的概率</v-card>

          <v-btn color="primary" @click="e2 = 3">Continue</v-btn>

          <v-btn text>Cancel</v-btn>
        </v-stepper-content>

        <v-stepper-content step="3">
          <v-card class="mb-12" color="grey lighten-1" height="200px">按照公式对整个蝙蝠种群在进行变异，并且计算相关的适应值</v-card>

          <v-btn color="primary" @click="e2 = 4">Continue</v-btn>

          <v-btn text>Cancel</v-btn>
        </v-stepper-content>

        <v-stepper-content step="4">
          <v-card
            class="mb-12"
            color="grey lighten-1"
            height="200px"
          >选择适应值较高的个体进行保留，并且修改精英池中的个体和相关的选择概率</v-card>

          <v-btn color="primary" @click="e2 = 5">Continue</v-btn>

          <v-btn text>Cancel</v-btn>
        </v-stepper-content>

        <v-stepper-content step="5">
          <v-card class="mb-12" color="grey lighten-1" height="200px"></v-card>

          <v-btn color="primary" @click="e2 = 2">Continue</v-btn>

          <v-btn text>Cancel</v-btn>
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      firstOirginal: true,
      firstImproved: true,
      e1: 1,
      e2: 1
    };
  },
  methods: {}
};
</script>
<style scoped>
.top {
  margin-top: 30px;
  margin-bottom: 30px;
}
</style>
