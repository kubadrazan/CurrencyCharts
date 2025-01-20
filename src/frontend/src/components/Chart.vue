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
  Legend
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
  },
  watch: {
    currencies: {
      immediate: true,
      deep: true,
      handler(newVal) {
        console.log('Updated currencies:', newVal);
      },
    },
  },
  name: 'LineChart',
  components: { LineChartComponent: Line },
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
    async AddCurrencyToChart(currency:Currency, baseCurrency:Currency) {
      try {
        const currencyService = new CurrencyService();
        const rates = await currencyService.getExchangeRates(baseCurrency, currency);
        var dataset = {
          label: currency.code,
          backgroundColor: 'white',
          borderColor: '#FF5733',
          data: rates
        }
        this.data.datasets.push(dataset);
      } catch (error) {
        console.error("Failed to fetch currencies", error);
      }
    }
  }
}
</script>
