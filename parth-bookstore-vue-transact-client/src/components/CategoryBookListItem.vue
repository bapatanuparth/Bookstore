<script setup lang="ts">
import { defineProps } from "vue";
import type { BookItem } from "@/types";
import { useCartStore } from "../stores/cart";
import { asDollarsAndCents } from "../main";
const cartStore = useCartStore();
const props = defineProps<{
  book: BookItem;
}>();
const bookImageFileName = function (book: BookItem): string {
  let name = book.title.toLowerCase();
  name = name.replace(/ /g, "-");
  name = name.replace(/'/g, "");
  return `${name}.png`;
};
function bookImageUrl(imageFileName: string) {
  return new URL(`../assets/books-images/${imageFileName}`, import.meta.url)
    .href;
}
const addToCart = (book: BookItem) => {
  cartStore.addToCart(book);
};
</script>
<style scoped>
.padding {
  background-color: none;
  padding: 0.8vw;
}

.book-box {
  display: flex;
  flex-direction: column;
  background-color: var(--card-background-color);
  padding: 1em;
  gap: 5px;

  justify-content: center;
  align-items: center;
  position: relative;
  border-radius: 15px;
  width: 250px;
}

.readnow-button {
  background: none;
  border: none;
  position: absolute;
  cursor: pointer;
}

.book-box .readnow {
  height: 28px;
  width: 28px;
  position: relative;
  top: 0.5em;
  left: 6.7em;
}

.book-box img {
  width: 150px;
  height: 225px;
}

.book-title {
  font-weight: 700;
  cursor: pointer;
}

.book-author {
  font-weight: bold;
  color: #c05c32;
}
.price {
  font-weight: 700;
}
.add-to-cart-button {
  border-radius: 15px;
  padding: 0.4rem;
  background-color: var(--primary-color);
  border: none;
  width: 138px;
  height: 40px;
  cursor: pointer;
  transition: 0.4s;
}

.add-to-cart-button:hover {
  background-color: var(--add-to-cart-hover);
}
</style>

<template>
  <div class="padding">
    <li class="book-box">
      <div class="book-image">
        <button class="readnow-button">
          <img class="readnow" src="/site/ReadNow2.png" v-if="book.isPublic" />
        </button>
        <a href="#">
          <img
            :src="bookImageUrl(bookImageFileName(props.book))"
            :alt="book.title"
          />
        </a>
      </div>
      <div class="book-title">{{ book.title }}</div>
      <a href="#"
        ><div class="book-author">{{ book.author }}</div></a
      >
      <div class="book-price">
        Buy at
        <span class="price">{{ asDollarsAndCents(book.price) }}</span>
      </div>
      <button class="add-to-cart-button" @click="addToCart(book)">
        Add to Cart
        <font-awesome-icon icon="fa-solid fa-cart-plus" />
      </button>
    </li>
  </div>
</template>
