<script setup lang="ts">
import { provide } from "vue";
import { RouterView, useRoute } from "vue-router";
import type { CategoryItem } from "@/types";
import TheHeader from "@/components/TheHeader.vue";
import TheFooter from "@/components/TheFooter.vue";
import "@fortawesome/fontawesome-free/css/all.css";
import { useCategoryStore } from "./stores/Category";
const categoryStore = useCategoryStore();
const categoryList = categoryStore.fetchCategories();
const route = useRoute();
</script>

<style scoped>
#app {
  display: flex;
  flex-direction: column;
  height: 100vh;
}
::-webkit-scrollbar {
  width: 5px;
}
::-webkit-scrollbar-track {
  background-color: #f1f1f1;
}
::-webkit-scrollbar-thumb {
  background-color: rgb(240, 240, 240);
}
</style>

<template>
  <suspense>
    <div id="app">
      <the-header></the-header>
      <router-view :key="route.fullPath"> </router-view>
      <the-footer></the-footer>
    </div>
  </suspense>
</template>
