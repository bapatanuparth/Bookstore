<script setup lang="ts">
import { useCartStore } from "@/stores/cart";
import { useCategoryStore } from "@/stores/Category";
import type { BookItem } from "@/types";
import { asDollarsAndCents } from "../main";
const cartStore = useCartStore();
const categoryStore = useCategoryStore();

const cart = cartStore.cart;

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
const updateCart = function (book: BookItem, quantity: number) {
  cartStore.updateBookQuantity(book, quantity);
};

const item_or_items = function () {
  return cart.numberOfItems == 1 ? "item" : "items";
};

const is_or_are = function () {
  return cart.numberOfItems == 1 ? "is" : "are";
};
</script>

<style scoped>
.cart-table {
  display: grid;
  grid-template-columns: max-content minmax(10em, 20em) repeat(3, max-content);
  row-gap: 1em;
  width: fit-content;
  margin: 0 auto;
  background-color: var(--card-background-color);

  border: 2px solid;
  border-bottom: none;
}

ul {
  display: contents;
}

ul > li {
  display: contents;
}

.table-heading {
  background-color: #333;
  color: white;
}

.table-heading > * {
  background-color: #333;
  padding: 0.5em;
}

.heading-book {
  grid-column: 1 / 2;
}

.booktitle {
  grid-column: 2/3;
}

.heading-price {
  grid-column: 3 / 4;
}

.heading-quantity {
  grid-column: 4/5;
}

.heading-subtotal {
  text-align: right;
  grid-column: 5/6;
}

.cart-book-image > * {
  margin-left: auto;
  margin-right: 0;
}

img {
  display: block;
  width: 100px;
  height: auto;
}

.square {
  width: 100px;
  height: 100px;
}

.wide-rect {
  width: 125px;
  height: 100px;
}

.cart-book-price {
  padding: 0 0.5em;
  text-align: left;
}

.cart-book-quantity {
  padding-left: 1em;
}

.cart-book-subtotal {
  text-align: right;
  padding-left: 1em;
  padding-right: 1em;
  width: 50px;
}

.line-sep {
  display: block;
  height: 2px;
  background-color: gray;
  grid-column: 1 / -1;
}

.icon-button {
  border: none;
  background: none;
  cursor: pointer;
  font-size: 1em;
}

.inc-button {
  margin-right: 0.1em;
}

.inc-button:hover {
  color: var(--primary-color-dark);
}

.dec-button {
  font-size: 1rem;
  margin-left: 0.1em;
}

.dec-button:hover {
  color: #aaa;
}

input[type="number"] {
  width: 4em;
}

select {
  background-color: var(--primary-color);
  color: white;
  border: 2px solid var(--primary-color-dark);
  border-radius: 3px;
}
.cart-book-image {
  padding: 0 10px;
}

.book-image {
  height: 80px;
  width: 80px;
}

.cart-book-title {
  padding-left: 10px;
}

.clear-cart-button {
  text-decoration: underline;
  font-weight: 600;
  color: #db8c14;
  cursor: pointer;
}

.clear-cart-button:hover {
  color: var(--add-to-cart-hover);
}

.cart-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 30px;
}

.checkoutButtons {
  display: flex;
  align-items: center;
  gap: inherit;
}

.checkoutButton {
  border-radius: 15px;
  padding: 0.5rem 1rem;
  background-color: var(--primary-color);
  border: none;
  width: fit-content;
  height: 40px;
  cursor: pointer;
  transition: 0.4s;
  font-weight: 600;
  color: rgb(66, 66, 66);
}

.checkoutButton:hover {
  background-color: var(--add-to-cart-hover);
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

.cartTotalText {
  font-size: large;
  font-weight: 400;
}
.cartTotalText span {
  font-weight: 600;
  text-decoration: underline;
}

.emptyCart {
  margin: 1em auto;
  text-align: center;
  text-decoration: underline;
}

.head {
  margin-bottom: 20px;
}

.displayText {
  display: flex;
  flex-direction: column;
}
</style>

<template>
  <div class="emptyCart" v-if="cart.items.length === 0">
    <h3 class="head">There are no items in cart...</h3>
    <router-link
      :to="{
        name: 'category-view',
        params: { name: categoryStore.selectedCategoryName },
      }"
      ><button class="continueShopping">Continue Shopping</button>
    </router-link>
  </div>
  <div v-else class="cart-container">
    <div class="checkoutButtons">
      <router-link
        :to="{
          name: 'category-view',
          params: { name: categoryStore.selectedCategoryName },
        }"
        ><button class="continueShopping">Continue Shopping</button>
      </router-link>
      <router-link to="/checkout">
        <button class="checkoutButton">Proceed to Checkout</button>
      </router-link>
    </div>
    <div class="displayText">
      <span class="cartTotalText"
        >There {{ is_or_are() }}
        <span> {{ cart.numberOfItems }}</span>
        {{ item_or_items() }} in your cart and total amount is
        <span>{{ asDollarsAndCents(cart.subtotal) }}</span>
      </span>
    </div>
    <ul class="cart-table">
      <li class="table-heading">
        <div class="heading-book">Book</div>
        <div class="booktitle">Book title</div>
        <div class="heading-price">Price</div>
        <div class="heading-quantity">Quantity</div>
        <div class="heading-subtotal">Amount</div>
      </li>

      <template v-for="item in cart.items" :key="item.book.bookId">
        <li>
          <div class="cart-book-image">
            <img
              class="book-image"
              :src="bookImageUrl(bookImageFileName(item.book))"
              :alt="item.book.title"
            />
          </div>
          <div class="cart-book-title">{{ item.book.title }}</div>

          <div class="cart-book-price">
            <span class="quantity">{{
              asDollarsAndCents(item.book.price)
            }}</span
            >&nbsp;
          </div>
          <div>
            <button
              class="icon-button inc-button"
              @click="updateCart(item.book, item.quantity + 1)"
            >
              <i class="fas fa-plus-circle"></i>
            </button>
            <span class="quantity">{{ item.quantity }}</span>
            <button
              class="icon-button dec-button"
              @click="updateCart(item.book, item.quantity - 1)"
            >
              <i class="fas fa-minus-circle"></i>
            </button>
          </div>

          <div class="cart-book-subtotal">
            {{ asDollarsAndCents(item.quantity * item.book.price) }}
          </div>
        </li>
        <li class="line-sep"></li>
      </template>
    </ul>

    <span class="clear-cart-button" @click="cartStore.clearCart()">
      Clear Cart
    </span>
  </div>
</template>
