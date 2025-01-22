<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-toolbar-title>
          CurrencyCharts
        </q-toolbar-title>

      </q-toolbar>
    </q-header>

    <q-page-container>
      <router-view />
    </q-page-container>
    <q-footer>
      <q-toolbar >
        <q-toolbar class="footer-toolbar">
          &copy; 2025 CurrencyCharts
          Igor Kober, Kuba Drażan
        </q-toolbar>
      </q-toolbar>
    </q-footer>
  </q-layout>
</template>

<script setup lang="ts">
</script>

<style scoped>
.footer-toolbar {
  display: flex;
  justify-content: center;
  width: 100%;
}
</style>
