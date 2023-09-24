<script setup lang="ts">
import type { CategoryItem } from "@/types";

import { useCategoryStore } from "../stores/Category";
const categoryStore = useCategoryStore();

const categoryImageFileName = function (category: CategoryItem): string {
  let name = category.name.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  return `${name}.png`;
};

const updateRecentCategory = function (category: string) {
  categoryStore.selectedCategoryName = category;
};
</script>

<style scoped>
.category-image-items {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  gap: 3em;

  padding-top: 2rem;
}

.category-image-items a {
  text-align: center;
  cursor: pointer;
  text-decoration: none;
}

.category-image-items img {
  width: 120px;
  height: 130px;

  border-color: var(--footer-color);
  box-shadow: 5px 5px 10px 0px rgba(0, 0, 0, 0.75);
}

li div {
  margin-bottom: -2em;
  padding: 0.5em 0;
  text-align: center;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  transform: translateY(-2.25em);
  transition: all 0.4s ease;
}

li div:hover {
  visibility: visible;
  background: rgb(0, 0, 0);
}
</style>

<template>
  <ul class="category-image-items">
    <li
      v-for="category in categoryStore.categoryList"
      :key="category.categoryId"
    >
      <router-link
        :to="'../category/' + category.name"
        @click="updateRecentCategory(category.name)"
      >
        <img
          :src="'categories/' + categoryImageFileName(category)"
          :alt="category.name + ' category'"
        />
        <div>{{ category.name }}</div>
      </router-link>
    </li>
  </ul>
</template>
