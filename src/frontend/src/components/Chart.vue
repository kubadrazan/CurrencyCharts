<template>
  <div id="chart">
    <apexchart type="line" height="350" :options="chartOptions" :series="series"></apexchart>
  </div>
</template>

<script lang="ts">
import CurrencyService from "../services/CurrencyService";
import type Currency from "src/interfaces/Currency";
import VueApexCharts from "vue3-apexcharts";
import CurrencyRates from "src/interfaces/CurrencyRates";

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
        this.addIndicatorToChart(newVal, this.baseModelValue)
        console.log('Updated currencies:', newVal);
      },
    },
  },
  components: {apexchart: VueApexCharts,},
  data() {
    return {
      currencyService: new CurrencyService(),
      series: [{
        name: "",
        type: "",
        data: [] as Number[],
      }],
      chartOptions: {
        chart: {
          height: 600,
          width: 800,
          type: 'area',
          zoom: {
            autoScaleYaxis: true
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'smooth',
          width: 2
        },
        title: {
          text: 'Exchange Rates',
          align: 'left'
        },
        grid: {
          row: {
            colors: ['#f3f3f3', 'transparent'],
            opacity: 0.5
          },
        },
        xaxis: {
          hideOverlappingLabels: true,
          type: 'datetime',
        },

        fill: {
          type: 'gradient',
          gradient: {
            shadeIntensity: 1,
            inverseColors: false,
            opacityFrom: 0.7,
            opacityTo: 0,
            stops: [0, 90, 100],
            type: 'vertical'
          },
        },
        labels: [] as Date[],
      }
    }
  },
  methods: {
    async addCurrencyToChart(currencies:Currency[], baseCurrency:Currency) {
      try {
        this.series = [];
        let result: CurrencyRates | null = null;
        for (const currency of currencies) {
          result = await this.currencyService.getCurrencyRates(currency.code, this.dateRange.from, this.dateRange.to);
          const serie = {
            name: currency.code,
            type: "area",
            data: result.rates.map(rate => rate.mid)
          }
          this.series = [...this.series, serie];
        }
        this.chartOptions = result !== null ? {
          ...this.chartOptions,
          labels: result.rates.map(rate => rate.effectiveDate),
        } :  this.chartOptions;

    } catch (error) {
        console.error("Failed to fetch currencies", error);
      }
    },
    async addIndicatorToChart(currencies:Currency[], baseCurrency:Currency) {
      try {
        this.series = [];
        for (const currency of currencies) {
          {
            if (currency.group.includes("ma")) {
              const result = await this.currencyService.getCurrencyMovingAverage(currency.code, this.dateRange.from, this.dateRange.to, 10);
              const serie = {
                name: "Moving Average: " + currency.code,
                type: "line",
                data: result.rates.map(rate => rate.mid)
              }
              this.series = [...this.series, serie];
            }
          }
        }
    } catch (error) {
        console.error("Failed to fetch currencies", error);
      }
    }
  }
}
</script>
