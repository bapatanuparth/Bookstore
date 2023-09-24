<script setup lang="ts">
import CartTable from "@/components/CartTable.vue";
</script>
<style scoped>
h1 {
  margin: 1em auto;
  text-align: center;
}
.cartpage {
  background-color: #fff7e2;
  padding: 1em;
  overflow: scroll;
  flex: 1;
}
</style>

<template>
  <div class="cartpage">
    <h1>Cart Page</h1>
    <cart-table></cart-table>
  </div>
</template>
