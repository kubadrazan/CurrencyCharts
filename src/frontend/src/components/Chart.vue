<template>
  <div id="main">
    <apexchart type="line" height="350" :options="chartOptions" :series="series"></apexchart>
  </div>
</template>

<script lang="ts">
import CurrencyService from "../services/CurrencyService";
import type Currency from "src/interfaces/Currency";
import VueApexCharts from "vue3-apexcharts";
import type CurrencyRates from "src/interfaces/CurrencyRates";

export default {
  props: {
    currencies: {
      type: Array as () => Currency[],
      required: true,
    },
    dateRange: {
      type: Object as () => { from: string, to: string },
      required: true,
    },

  },
  watch: {
    currencies: {
      immediate: true,
      deep: true,
      handler(newVal) {
        this.addCurrencyToChart(newVal)
        console.log('Updated currencies:', newVal);
      },
    },
    dateRange: {
      immediate: true,
      deep: true,
      handler(newVal) {
        this.addCurrencyToChart(this.currencies)
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
          width: 2,
          dashArray: [] as number[],
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
          type: [] as string[],
          gradient: {
            shadeIntensity: 1,
            inverseColors: false,
            opacityFrom: 0.5,
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
    async addCurrencyToChart(currencies: Currency[]) {
      try {
        this.series = [];
        this.chartOptions.stroke.dashArray = [];
        this.chartOptions.fill.type = []

        let result: CurrencyRates | null = null;
        for (const currency of currencies) {
          result = await this.addExchangeRate(currency);
          await this.addIndicators(currency);
        }
        this.chartOptions = result !== null ? {
          ...this.chartOptions,
          labels: result.rates.map(rate => rate.effectiveDate),
        } : this.chartOptions;

      } catch (error) {
        console.error("Failed to fetch currencies", error);
      }
    },
    async addExchangeRate(currency: Currency): Promise<CurrencyRates> {
      let result = await this.currencyService.getCurrencyRates(currency.code, this.dateRange.from, this.dateRange.to);
      const serie = {
        name: currency.code,
        type: "area",
        data: result.rates.map(rate => Number(rate.mid.toFixed(2)))
      }
      this.chartOptions.fill.type = [...this.chartOptions.fill.type, "gradient"]
      this.chartOptions.stroke.dashArray = [...this.chartOptions.stroke.dashArray, 0]
      this.series = [...this.series, serie];
      return result;
    },
    async addIndicators(currency: Currency) {
      for (const opt of currency.group) {
        const result = await this.currencyService.executeMethod(opt, currency.code, this.dateRange.from, this.dateRange.to);
        const serie = {
          name: this.currencyService.options.find(value => value.value == opt)?.label + ": " + currency.code,
          type: "line",
          data: result.rates.map(rate => Number(rate.mid.toFixed(2)))
        }
        this.chartOptions.fill.type = [...this.chartOptions.fill.type, "solid"]
        this.chartOptions.stroke.dashArray = [...this.chartOptions.stroke.dashArray, result.rates.length]
        this.series = [...this.series, serie];
      }
    }
  }
}
</script>
<style>
#main {
  flex:auto;
  max-width: 100%;
  height: auto;
}
</style>
