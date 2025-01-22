<template>
  <q-page class="row items-center justify-evenly">
    <CurrenciesSelector v-model="currencies" v-model:base-model-value="baseCurrency"/>
<!--    https://stackoverflow.com/questions/73575535/how-to-implement-qdate-range-selection-with-qinput-->
    <q-input filled :model-value="`${dateRange.from} - ${dateRange.to}`">
      <template v-slot:append>
        <q-icon name="event" class="cursor-pointer">
          <q-popup-proxy cover transition-show="scale" transition-hide="scale">
            <q-date v-model="dateRange" range mask="YYYY-MM-DD">
              <div class="row items-center justify-end">
                <q-btn v-close-popup label="Close" color="primary" flat />
              </div>
            </q-date>
          </q-popup-proxy>
        </q-icon>
      </template>
    </q-input>    <Chart :currencies="currencies" :base-model-value="baseCurrency" :dateRange="dateRange"/>
  </q-page>
</template>

<script setup lang="ts">
import {ref} from 'vue';
import Chart from 'components/Chart.vue'
import CurrenciesSelector from 'components/CurrenciesSelector.vue'
import Currency, {createCurrency} from "src/interfaces/Currency";

const currencies = ref<Currency[]>([]);
const baseCurrency = ref<Currency>(createCurrency());
let dateRange =  ref({ from: '2020-07-08', to: '2020-07-17' });
</script>
