<template>
  <div>
      <q-select filled color="purple-12" v-model="baseCurrency" @update:modelValue="selectBaseCurrency" :options="options" label="Select base currency" option-label="currency" option-value="code" ></q-select>
  </div>
  <div class="currencies_selection">
      <q-chip square v-for="(currency, index) in currencies" :key="index" :size="chipSize"
              removable @remove="removeFromCurrencyList(currency)" >{{currency.code}}
      </q-chip>
        <q-select v-if="count < 5"
      filled color="purple-12" v-model="selectedItem" @update:modelValue="selectItem()" :options="options" label="Select a currency" option-label="currency" option-value="code" display-value="Select Currency"></q-select>
      </div>
</template>

<script lang="ts">
import CurrencyService from "../services/CurrencyService";
import type Currency from "src/interfaces/Currency";

export default {
  props: {
    modelValue: {
      type: Array,
      required: true,
    },
    baseModelValue: {
      type: Object as () => Currency,
      required: true,
    },
  },
  data() {
    return {
      selectedItem: {code:"", currency:""} as Currency,
      baseCurrency: {code:"", currency:""} as Currency,
      count: 0,
      options: [] as Currency[], // Use Currency[] for an array of Currency objects
      currencies: [] as Currency[], // Use Currency[] for an array of Currency objects
    };
  },
  mounted() {
    this.getOptions();
  },
  computed: {
    chipSize() {
      if (this.$q.screen.xs) {
        return 'sm'; // Small size for extra-small screens
      } else if (this.$q.screen.sm) {
        return 'md'; // Medium size for small screens
      } else {
        return 'lg'; // Larger size for medium and larger screens
      }
    },
  },
  methods: {

    selectBaseCurrency() {
      if(this.baseCurrency.code != "") {
        if(this.baseModelValue.code != ""){
          this.options.push({...this.baseModelValue});
        }
        this.$emit('update:baseModelValue', this.baseCurrency);
        this.options = this.options.filter(option => option !== this.baseCurrency);
      }
      },
    selectItem() {
      if(this.selectedItem.code != "") {
        this.currencies.push(this.selectedItem);
        this.$emit('update:modelValue', this.currencies);
        this.count++;
        this.options = this.options.filter(option => option !== this.selectedItem);
        this.selectedItem = {code: "", currency: ""};
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
  display: flex;
  gap: 1rem;
  margin-top: 10px;
  position: relative; /* Add this line */
}

</style>
