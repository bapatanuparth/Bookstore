<script setup lang="ts">
import TheCategoryNav from "@/components/TheCategoryNav.vue";
import TheCategoryBookList from "@/components/TheCategoryBookList.vue";
import { useRoute } from "vue-router";

import { useBookStore } from "@/stores/book";
import { watch } from "vue";
import { useCategoryStore } from "@/stores/Category";
import router from "@/router";
const route = useRoute();
const bookStore = useBookStore();
const categoryStore = useCategoryStore();

watch(
  () => route.params.name,
  (newName) => {
    categoryStore.setSelectedCategoryName(newName as string);
    bookStore.fetchBooks(newName as string).catch(() => {
      router.push("/not-found");
    });
  },
  { immediate: true }
);
</script>

<style scoped>
.category-page {
  background-color: #fff7e2;
  display: flex;
  flex-direction: column;
  flex: 1;
}
</style>

<template>
  <div class="category-page">
    <the-category-nav></the-category-nav>
    <the-category-book-list> </the-category-book-list>
  </div>
</template>
