<template>
  <q-page class="row items-center justify-evenly">
    <ChartButtons v-model="currencies" />
    <Chart :currencies="currencies" />
  </q-page>
</template>

<script setup lang="ts">
import {ref} from 'vue';
import Chart from 'components/Chart.vue'
import ChartButtons from 'components/ChartButtons.vue'
import type Currency from "src/interfaces/Currency";

const currencies = ref<Currency[]>([]);
</script>
