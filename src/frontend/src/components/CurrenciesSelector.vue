<template>
  <div class="currencies_selection">
    <q-expansion-item expand-separator square group="currency-chips" v-for="(currency, index) in currencies" :key="index" :size="chipSize">
      <template v-slot:header>
        <div class="q-gutter-xs flex justify-between items-center">
          <span>{{ currency.code }}</span>
          <q-btn flat round dense icon="close" @click.stop="removeFromCurrencyList(currency)"/>
        </div>
      </template>
        <q-option-group :options="currencyService.options" type="toggle" v-model="currency.group"/>
    </q-expansion-item>

    <q-select v-if="count < 5"
              filled color="purple-12" v-model="selectedItem" @update:modelValue="selectItem()" :options="options"
              label="Select a currency" option-label="currency" option-value="code"
              display-value="Select Currency"></q-select>
  </div>
</template>

<script lang="ts">
import CurrencyService from "../services/CurrencyService";
import type Currency from "src/interfaces/Currency";
import {createCurrency} from "src/interfaces/Currency";

export default {
  props: {
    modelValue: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      selectedItem: createCurrency(),
      count: 0,
      options: [] as Currency[],
      currencies: [] as Currency[],
    };
  },
  mounted() {
    this.getOptions();
  },
  computed: {
    currencyService() {
      return new CurrencyService();
    },
    chipSize() {
      if (this.$q.screen.xs) {
        return 's'; // Small size for extra-small screens
      } else if (this.$q.screen.sm) {
        return 'sm'; // Medium size for small screens
      } else {
        return 'md'; // Larger size for medium and larger screens
      }
    },
  },
  methods: {
    selectItem() {
      if (this.selectedItem.code != "") {
        this.currencies.push(this.selectedItem);
        this.$emit('update:modelValue', this.currencies);
        this.count++;
        this.options = this.options.filter(option => option !== this.selectedItem);
        this.selectedItem = createCurrency();
      }
    },
    removeFromCurrencyList(deletedItem: Currency) {
      this.currencies = this.currencies.filter(item => item !== deletedItem);
      this.$emit('update:modelValue', this.currencies);
      this.options.push(deletedItem);
      this.count--;
    },
    async getOptions() {
      try {
        const currencyService = new CurrencyService();
        this.options = await currencyService.getCurrencies();
      } catch (error) {
        console.error("Failed to fetch currencies", error);
      }
    },
  },
};
</script>


<style>

.currencies_selection {
  display: grid;
  gap: 1rem;
  margin-top: 10px;
  position: relative;
}

</style>
