<template>
  <q-page class="row items-center justify-evenly">
    <CurrenciesSelector v-model="currencies" v-model:base-model-value="baseCurrency"/>
    <Chart :currencies="currencies" :base-model-value="baseCurrency"/>
  </q-page>
</template>

<script setup lang="ts">
import {ref} from 'vue';
import Chart from 'components/Chart.vue'
import CurrenciesSelector from 'components/CurrenciesSelector.vue'
import type Currency from "src/interfaces/Currency";

const currencies = ref<Currency[]>([]);
const baseCurrency = ref<Currency>({ code: "", currency: "" });
</script>
