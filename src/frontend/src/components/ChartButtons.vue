<template>
  <div>
    <h1 class="currency_display">PLN</h1>
    <div class="currencies_selection">
      <q-chip square v-for="(currency, index) in currencies" :key="index" :size="chipSize"
              removable @remove="removeFromCurrecyList(currency)"  color="primary" text-color="white">
        {{ currency.code }}
      </q-chip>
      <div v-if="count < 5" class="dropdown">
        <button class="dropdown-button" @click="toggleDropdown">+</button>
        <ul v-if="isOpen" class="dropdown-menu">
          <li v-for="(item, index) in options" :key="index" @click="selectItem(item)">{{ item.currency }}</li>
        </ul>
      </div>
    </div>
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
  },
  data() {
    return {
      count: 0,
      isOpen: false,
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
    toggleDropdown() {
      this.isOpen = !this.isOpen;
    },
    selectItem(item: Currency) {
      this.isOpen = false;
      this.currencies.push(item);
      this.$emit('update:modelValue', this.currencies);
      this.count++;
      this.options = this.options.filter(option => option !== item);
    },
    removeFromCurrecyList(deletedItem: Currency) {
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
        this.options = [
          { code: 'EUR', currency: 'Euro' },
          { code: 'USD', currency: 'US Dollar' },
          { code: 'GBP', currency: 'British Pound' },
          { code: 'JPY', currency: 'Japanese Yen' },
          { code: 'AUD', currency: 'Australian Dollar' },
          { code: 'CAD', currency: 'Canadian Dollar' },
          { code: 'CHF', currency: 'Swiss Franc' },
          { code: 'CNY', currency: 'Chinese Yuan' },
          { code: 'INR', currency: 'Indian Rupee' },
          { code: 'MXN', currency: 'Mexican Peso' }

        ];
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

.dropdown {
  display: flex;
  flex-direction: column;
}

.dropdown-button {
  position: relative;
  background-color: magenta;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 32px;
  cursor: pointer;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.dropdown-button:hover {
  background-color: purple;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 100%;
  border: 1px solid #ccc;
  list-style: none;
  padding: 0;
  margin: 0;
  width: 150px;
  z-index: 1000;
  background-color: #ffffff;
}

.dropdown-menu li {
  padding: 10px;
  cursor: pointer;
}

.dropdown-menu li:hover {
  background-color: grey;
}

.currency_display {
  display: inline-block;
  margin-left: 5px;
  padding: 5px;
  border-style: solid;
  border-radius: 10px;
  max-width: fit-content;
}
</style>
