<template>
  <main>
    <hello-world/>
    <ChartButtons/>
    <Chart/>
  </main>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'
import Chart from './components/Chart.vue'
import ChartButtons from './components/ChartButtons.vue'

export default {
  name: 'App',
  components: {
    HelloWorld,
    Chart,
    ChartButtons
  }
}
</script>

<style>
main {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

</style>