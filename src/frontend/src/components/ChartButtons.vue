
<template>
<div>
    <h1 class="currency_display">PLN</h1> 
    <div class="currencies_selection">
        <h1 v-for="currency in currencies" :key="currency.id" class="currency_display"> {{currency.text}}</h1>

        <div v-if="count<5" class="dropdown">
                <button class="dropdown-button" @click="toggleDropdown">+</button>
                <ul v-if="isOpen" class="dropdown-menu">
                    <li v-for="(item, index) in options" :key="index" @click="selectItem(item)">{{ item }}</li>
                </ul>
        </div>    
    </div>
</div>
</template>

<script>
export default {
    data() {
        return {
            count: 0,
            isOpen: false,
            options: ['Eur', 'Usd', 'Gbp', 'Jpy', 'Chf', 'Cad', 'Aud', 'Nzd'],
            currencies: []
        }
    },
    methods: {
        toggleDropdown() {
          this.isOpen = !this.isOpen;
        },
        selectItem(item) {
          this.isOpen = false; 
          this.currencies.push({
                id: this.count++,
                text: item
            })
          this.options = this.options.filter(option => option !== item)
        },
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
.currency_display{
  display: inline-block; 
  margin-left: 5px;
  padding: 5px;
  border-style: solid;
  border-radius: 10px;
  max-width: fit-content;
}
</style>