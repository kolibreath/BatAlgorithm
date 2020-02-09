<template>
    <div></div>
</template>

<script>
let firstTime = true
let base = 'http://localhost:8081/'
//frequencyRate 是频率的最高值
let population = 20, generation = 1000, pulseRate = 0.5, frequencyRate = '', loudness = 0.45;
//当前正在执行的函数的下标
let functionIndex = 0, functionQueue;
let defaultTitle = "改进蝙蝠算法 Vs 原始蝙蝠算法";
let functionNames = ['sphere', 'schwefel', 'schwefel2','schwefel3',
'Rosen Brock','step', 'quartic with Noise', 'schwefel4','Rastrign' ,'Ackley' ,'griewank', 'penalized' ,'penalized2'];
export default {
    population, generation, pulseRate, frequencyRate , functionQueue,
     functionNames, defaultTitle, functionIndex,loudness, firstTime
}
</script>

<style>

</style>
