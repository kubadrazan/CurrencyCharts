<!--    https://stackoverflow.com/questions/73575535/how-to-implement-qdate-range-selection-with-qinput-->
<template>
  <q-input filled :model-value="`${(dateRange !== null && dateRange.from !== null) ? dateRange.from : ''} - ${(dateRange !== null && dateRange.to !== null) ? dateRange.to : ''}`">
    <template v-slot:append>
      <q-icon name="event" class="cursor-pointer">
        <q-popup-proxy cover transition-show="scale" transition-hide="scale">
          <q-date v-model="dateRange" range mask="YYYY-MM-DD" @update:model-value="updateDateRange">
            <div class="row items-center justify-end">
              <q-btn v-close-popup label="Close" color="primary" flat />
            </div>
          </q-date>
        </q-popup-proxy>
      </q-icon>
    </template>
  </q-input>
</template>

<script lang="ts">
import type Currency from "src/interfaces/Currency";

export default {
  props: {
    modelValue: {
      type: Object as () => { from: string; to: string },
      required: true,
    },
  },
  data() {
    return {
      currencies: [] as Currency[],
      dateRange: {from: '2024-07-08', to: '2024-07-17'},
    };
  },
  methods: {
    updateDateRange() {
      if(this.dateRange != null && this.dateRange.from != null && this.dateRange.to != null)
        this.$emit('update:modelValue', this.dateRange);
    },
  },
};
</script>
<style scoped>

</style>
