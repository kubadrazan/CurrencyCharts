<template>
  <q-page class="main">
      <Chart :currencies="currencies" :dateRange="dateRange" />
      <div class="grid">
        <DataSelector v-model="dateRange" />
        <CurrenciesSelector v-model="currencies" />
      </div>
  </q-page>
</template>

<script setup lang="ts">
import {ref} from 'vue';
import Chart from 'components/Chart.vue'
import CurrenciesSelector from 'components/CurrenciesSelector.vue'
import type Currency from "src/interfaces/Currency";
import DataSelector from "components/DataSelector.vue";

const currencies = ref<Currency[]>([]);
const dateRange =  ref({ from: '2020-07-08', to: '2020-07-17' });
</script>

<style>
.main {
  display: flex;
  gap: 1rem;
  margin-top: 10px;
  position: relative;
  flex-wrap: nowrap;
  align-items: stretch;
}


</style>
