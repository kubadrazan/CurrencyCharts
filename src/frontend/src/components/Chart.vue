<template>
  <LineChartComponent id="my-chart-id" :options="options" :data="data" />
</template>

<script lang="ts">
import CurrencyService from "../services/CurrencyService";
import { Line } from 'vue-chartjs'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend, ChartData
} from 'chart.js'
import type Currency from "src/interfaces/Currency";
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
  props: {
    currencies: {
      type: Array as () => Currency[],
      required: true,
    },
    baseModelValue: {
      type: Object as () => Currency,
      required: true,
    },
    beginDate: {
      type: Object as () => Date,
      required: true,
    },
    endDate: {
      type: Object as () => Date,
      required: true,
    },
  },
  watch: {
    currencies: {
      immediate: true,
      deep: true,
      handler(newVal) {
        this.addCurrencyToChart(newVal, this.baseModelValue)
        console.log('Updated currencies:', newVal);
      },
    },
  },
  name: 'LineChart',
  components: { LineChartComponent: Line },
  data() {
    return {
      //baseCurrency: 'PLN',
      data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [] as ChartData<'line'>['datasets']
      },
      options: {
        responsive: true,
        maintainAspectRatio: true,
      }
    }
  },
  methods: {
    async addCurrencyToChart(currencies:Currency[], baseCurrency:Currency) {
      try {
        const currencyService = new CurrencyService();
        for (const currency of currencies) {
          const rates = await currencyService.getExchangeRates(baseCurrency, currency, this.beginDate, this.endDate);
          const dataset = {
            label: currency.code,
            backgroundColor: 'white',
            borderColor: '#FF5733',
            data: rates
          }
          this.data.datasets.push(dataset);
        }
      } catch (error) {
        console.error("Failed to fetch currencies", error);
      }
    }
  }
}
</script>
