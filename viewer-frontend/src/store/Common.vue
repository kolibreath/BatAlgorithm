<template>
    <div></div>
</template>

<script>
let population = '', generation = '', pulseRate = '', frequencyRate = '';
//当前正在执行的函数的下标
let functionIndex = 0, functionQueue;
let defaultTitle = "改进蝙蝠算法 Vs 原始蝙蝠算法";
let functionNames = ['sphere', 'schwefel', 'schwefel2','schwefel3',
'Rosen Brock','step', 'quartic with Noise', 'schwefel4','Rastrign' ,'Ackley' ,'griewank', 'penalized' ,'penalized2'];
export default {
    population, generation, pulseRate, frequencyRate , functionQueue, functionNames, defaultTitle, functionIndex
}
</script>

<style>

</style>
