<template>
  <div>
    <h1 class="currency_display">PLN</h1>
    <div class="currencies_selection">
      <!--      <h1 v-for="currency in currencies" :key="currency.id" class="currency_display"> {{currency.text}}</h1>-->
      <q-chip square v-for="(currency, index) in currencies" :key="index" :size="chipSize"
              removable @remove="removeFromCurrecyList(currency)"  color="primary" text-color="white">
        {{ currency.currency}}
      </q-chip>
      <div v-if="count<5" class="dropdown">
        <button class="dropdown-button" @click="toggleDropdown">+</button>
        <ul v-if="isOpen" class="dropdown-menu">
          <li v-for="(item, index) in options" :key="index" @click="selectItem(item)">{{ item.currency }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import CurrencyService from "../services/CurrencyService.js"
import {options} from "axios";
export default {
  data() {
    return {
      count: 0,
      isOpen: false,
      options: [],/*['Eur', 'Usd', 'Gbp', 'Jpy', 'Chf', 'Cad', 'Aud', 'Nzd'],*/
      currencies: []
    }
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
    selectItem(item) {
      this.isOpen = false;
      this.currencies.push(item)
      this.count++
      this.options = this.options.filter(option => option !== item)
    },
    removeFromCurrecyList(deletedItem){
      this.currencies = this.currencies.filter(item => item !== deletedItem)
      this.options.push(deletedItem)
      this.count--
    },
    async getOptions() {
      try {
        const response = await CurrencyService.getCurrencies();
        this.options = response.data; // Assuming response data contains the options
      } catch (error) {
        this.options = [
          { code: 'EUR', currency: 'Euro' },
          { code: 'USD', currency: 'US Dollar' },
          { code: 'GBP', currency: 'British Pound' },
        ];
        console.error("Failed to fetch currencies", error);
      }
    }
  }
}

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
