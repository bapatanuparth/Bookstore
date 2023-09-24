<script setup lang="ts"></script>
<style scoped>
h1 {
  margin: 1em auto;
  text-align: center;
}
.checkout {
  background-color: #fff7e2;
  padding: 1em;
  overflow: scroll;
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.continueShopping {
  width: fit-content;
  padding: 0.2rem 1rem;
  height: 40px;
  border: 3px solid var(--add-to-cart-hover);
  border-radius: 15px;
  background-color: #fff7e2;
  color: var(--add-to-cart-hover);
  transition: 0.5s;
  cursor: pointer;
  font-weight: 600;
}
.continueShopping:hover {
  border-color: rgb(66, 66, 66);
  color: rgb(66, 66, 66);
}
</style>

<template>
  <div class="checkout">
    <h1>Page Not Found 404</h1>
    <router-link to="/"
      ><button class="continueShopping">Home</button></router-link
    >
  </div>
</template>
