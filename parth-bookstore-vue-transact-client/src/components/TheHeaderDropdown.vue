<script setup lang="ts">
import { useCategoryStore } from "../stores/Category";

const categoryStore = useCategoryStore();

const updateRecentCategory = function (category: string) {
  categoryStore.selectedCategoryName = category;
};
</script>

<style scoped>
.header-dropdown-and-cart {
  display: flex;
  gap: 1.2rem;
  align-items: center;
}
.cart-and-user-button {
  position: relative;
  background: none;
  border: none;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  gap: 0.2rem;
}
.cart-and-user-button :hover {
  color: gray;
}

.cart-and-user-icon {
  color: black;
  font-size: 1.4rem;
}
.header-dropdown {
  position: relative;
}

.categories-button {
  background: none;
  border: none;
  margin-top: 0.25em;
  border: none;
  padding: 0.5rem 2rem 0.8rem 0.5rem;
  border-radius: 15px;
  position: relative;
  top: 0.1rem;
  cursor: pointer;
}

.header-dropdown ul {
  background-color: var(--footer-color);

  position: absolute;
  visibility: hidden;
  opacity: 0;
  box-shadow: 0 0 10px #0008;
  transform: translateY(-1.5em);
  transition: transform 200ms ease-in, opacity 0.3s ease-in;
}

.header-dropdown li {
  padding: 0.5rem 1rem 0.5rem 0.5rem;
  background-color: var(--footer-color);
}

.header-dropdown a {
  text-decoration: none;
  color: white;
}

.header-dropdown:hover ul {
  visibility: visible;

  position: absolute;
  min-width: 7em;
  z-index: 1;
  cursor: pointer;
  border-radius: 15px;
  top: 4.6rem;
  transform: translateY(0);
  opacity: 1;
}

.header-dropdown li:hover {
  background: rgb(100, 100, 100);
}

.down-arrow {
  position: absolute;
  top: 2.3rem;
  right: 1rem;
}
</style>

<template>
  <div class="header-dropdown header-dropdown-and-cart">
    <button class="categories-button cart-and-user-button">
      <font-awesome-icon icon="fa-solid fa-book" class="cart-and-user-icon" />
      Categories
    </button>
    <font-awesome-icon icon="fa-solid fa-caret-down" class="down-arrow" />

    <ul class="droplist">
      <router-link
        @click="updateRecentCategory(category.name)"
        v-for="category in categoryStore.categoryList"
        :key="category.categoryId"
        :to="'/category/' + category.name"
      >
        <template #default="{ navigate }">
          <li @click="navigate">{{ category.name }}</li>
        </template>
      </router-link>
    </ul>
  </div>
</template>
