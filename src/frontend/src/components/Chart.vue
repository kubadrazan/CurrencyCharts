<template>
  <LineChartComponent  ref="chart" id="Chart" :options="options" :data="data" >Chart couldn't be loaded xD</LineChartComponent>
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
  Legend, ChartData, ChartDataset
} from 'chart.js'
import type Currency from "src/interfaces/Currency";
import {ComponentPublicInstance} from "vue";
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
    dateRange: {
      type: Object as () =>{ from: string, to: string },
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
        labels: [] as string[] ,//['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: []  as ChartDataset<'line'>[],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
      }
    }
  },
  methods: {
    async addCurrencyToChart(currencies:Currency[], baseCurrency:Currency) {
      try {
        const currencyService = new CurrencyService();
        this.data.datasets = [];
        for (const currency of currencies) {
          const result = await currencyService.getCurrencyRates(currency.code, this.dateRange.from, this.dateRange.to);
          const dataset = {
            label: currency.code,
            backgroundColor: 'white',
            borderColor: '#FF5733',
            data: result.rates.map(rate => rate.mid)
          }
          this.data.datasets = [...this.data.datasets, dataset];
          this.data.labels = result.rates.map(rate => rate.effectiveDate.toString()).filter((date): date is string => date !== undefined);
        }
        this.data.datasets = [];
        const dataset = {
          label: 'AAAAAAAAAAA',
          backgroundColor: 'white',
          borderColor: '#FF5733',
          data: [40, 39, 10, 40, 39, 80, 40]
        }
        this.data.datasets = [...this.data.datasets, dataset];
        this.data.labels = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];
    } catch (error) {
        console.error("Failed to fetch currencies", error);
      }
    }
  }
}
</script>
