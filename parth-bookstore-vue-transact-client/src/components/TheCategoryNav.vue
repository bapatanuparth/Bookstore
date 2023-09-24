<script setup lang="ts">
import { useCategoryStore } from "../stores/Category";
const categoryStore = useCategoryStore();

const updateRecentCategory = function (category: string) {
  categoryStore.selectedCategoryName = category;
};
</script>

<style scoped>
.category-page {
  background-color: #fff7e2;
}

.category-buttons {
  display: flex;
  flex-direction: row;
  text-align: center;
  background-color: var(--grey-color);
  justify-content: space-evenly;
  gap: 11.2rem;
}

.button.selected-category-button {
  background-color: var(--footer-color);
  color: white;
  transition: 0.3s;
}

.button.unselected-category-button,
.button.unselected-category-button:visited {
  background-color: var(--grey-color);
  color: black;
  font-weight: 400;
}

.button.unselected-category-button:hover,
.button.unselected-category-button:active {
  background-color: var(--footer-color);
  color: white;
}
</style>

<template>
  <nav class="category-nav">
    <ul class="category-buttons">
      <li
        v-for="category in categoryStore.categoryList"
        :key="category.categoryId"
      >
        <router-link
          @click="updateRecentCategory(category.name)"
          :to="'/category/' + category.name"
          class="button"
        >
          {{ category.name }}
        </router-link>
      </li>
    </ul>
  </nav>
</template>
