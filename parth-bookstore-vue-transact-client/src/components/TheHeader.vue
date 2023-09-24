<script setup lang="ts">
import TheHeaderDropdown from "@/components/TheHeaderDropdown.vue";
import { computed } from "vue";
import { useCartStore } from "@/stores/cart";

const cartStore = useCartStore();

const count = computed(() => {
  return cartStore.count;
});
</script>

<style scoped>
header {
  background: var(--secondary-background-color);
  display: flex;
  padding: 1rem;
  justify-content: center;
  gap: 13.5rem;
  overflow: visible;
  height: 5rem;
}

.header {
  padding: 0;
  display: flex;
  justify-content: space-between;
  height: fit-content;
  align-items: center;
}

.text-logo {
  font-family: var(--title-font-family);
  color: black;
  text-decoration: none;
}

.search-bar {
  padding: 0.5em;
  background: var(--grey-color);
  border-radius: 15px;
  border: none;
  position: relative;
}

@media (max-width: 600px) {
  header {
    flex-direction: column;
  }
}

.header-dropdown-and-cart {
  display: flex;
  gap: 1.2rem;
  align-items: center;
}

.bookstore-logo {
  display: flex;
  justify-content: center;
  align-items: center;
  height: min-content;
}

.bookstore-logo a {
  margin: 0;
}

.logo-image {
  height: 4rem;
}

.title-and-search-bar {
  position: relative;
  left: 2rem;
}

.search-button {
  position: absolute;
  background: none;
  top: 0.4rem;
  right: 0.4rem;
  border: none;
  color: rgb(134, 134, 134);
}

.search-button :hover {
  color: black;
  cursor: pointer;
}

.cart-and-user {
  display: flex;
  gap: 1.2rem;
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
.cart-and-user-button a {
  color: black;
  font-size: 1rem;
  font-weight: 400;
  text-decoration: none;
}
.cart-and-user-button img {
  position: absolute;
  top: -0.5rem;
  right: 0rem;
  height: 18px;
}

.cart-and-user-icon {
  color: black;
  font-size: 1.4rem;
}

.cart-and-user-button :hover {
  color: gray;
}

.counter-container {
  display: flex;
  /* position: relative; */
  justify-content: center;
  align-items: center;
}

.counter-container span {
  user-select: none;
  position: absolute;
  background: black;
  color: white;
  width: 1.3rem;
  height: 1.3rem;
  font-size: 0.9rem;
  font-weight: 600;
  border-radius: 50%;
  top: -0.5rem;
  right: 0rem;
}
</style>

<template>
  <header class="container header" style="padding: 0 2rem 0 2rem">
    <section class="bookstore-logo">
      <router-link to="/">
        <img
          class="logo-image"
          src="/site/bookstore-logo.png"
          alt="Another Bookstore Logo"
        />
      </router-link>

      <h1><router-link to="/" class="text-logo"> BookMark </router-link></h1>
    </section>
    <section class="title-and-search-bar">
      <div>
        <input type="text" class="search-bar" placeholder="Search" />
        <button class="search-button" value="Search">
          <font-awesome-icon icon="fa-solid fa-magnifying-glass" />
        </button>
      </div>
    </section>

    <section class="header-dropdown-and-cart">
      <the-header-dropdown></the-header-dropdown>
      <div class="cart-and-user">
        <button class="cart-and-user-button">
          <div class="shopping-cart">
            <router-link to="/cart">
              <div class="counter-container">
                <span id="counter">{{ count }}</span>
                <font-awesome-icon
                  icon="fa-solid fa-cart-shopping"
                  class="cart-and-user-icon"
                />
              </div>

              <span class="cartText">Cart</span>
            </router-link>
          </div>
        </button>
        <button class="cart-and-user-button">
          <font-awesome-icon
            icon="fa-solid fa-user"
            class="cart-and-user-icon"
          />

          <a href="#"> Parth</a>
        </button>
      </div>
    </section>
  </header>
</template>
