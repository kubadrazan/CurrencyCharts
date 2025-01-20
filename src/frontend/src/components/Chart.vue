<template>
  <Line id="my-chart-id" :options="options" :data="data" />
</template>

<script>
import { Line } from 'vue-chartjs'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
} from 'chart.js'
ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
)
export default {
  name: 'LineChart',
  components: { Line },
  data() {
    return {
      baseCurrency: 'PLN',
      data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [
          {
            label: 'Data One',
            backgroundColor: 'white',
            borderColor: '#FF5733',
            data: [40, 39, 10, 40, 39, 80, 40]
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: true,
      }
    }
  },
  methods: {
    //AddCurrencyToChart(currency, baseCurrency) {
      // ApiDataService.getRates(currency, baseCurrency).then(data => {
      //   this.chartData.labels.push(currency)
      //   this.chartData.datasets[0].data.push(data)
      // })
    //}
  }
}
</script>
