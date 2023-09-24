<script setup lang="ts">
import { useCartStore } from "@/stores/cart";

import { asDollarsAndCents } from "../main";

import { useOrderDetailsStore } from "@/stores/OrderDetails";
import type { BookItem, OrderDetails, ShoppingCart } from "@/types";

const cartStore = useCartStore();
const cart = cartStore.cart;
const orderDetailsStore = useOrderDetailsStore();
const orderDetails: OrderDetails = orderDetailsStore.orderDetails;

const bookAt = function (orderDetails: OrderDetails, index: number): BookItem {
  return orderDetails.books[index];
};
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
</script>

<style scoped>
table {
  border: 1px black solid;
  width: fit-content;
  margin-top: 1em;
  margin-bottom: 1em;
  background-color: var(--card-background-color);
  border: 1px solid;
  border-radius: 10px;
}

td {
  display: table-cell;
  padding: 0.5rem 1.5rem;
  vertical-align: middle;
  border: 1px solid black;
}

thead {
  border-bottom: 1px solid black;
}

tr:nth-child(even) > td {
  background-color: rgb(245, 201, 134);
}

td:nth-child(1) {
  text-align: center;
}

td:nth-child(2) {
  text-align: center;
}

td:nth-child(3) {
  text-align: right;
}
td:not(:last-child) {
  border-right: 1px solid black;
}
.cart-book-image {
  padding: 0 10px;
}

.book-image {
  height: 80px;
  width: 80px;
}
.title {
  font-weight: 600;
}

.confirmationTableWrapper {
  display: flex;
  flex-direction: column;
}

.totalDetails {
  display: flex;
  flex-direction: column;
  align-self: flex-end;
  align-items: end;
}
</style>

<template>
  <div class="confirmationTableWrapper">
    <table class="confirmation-table">
      <tbody>
        <tr v-for="(item, index) in orderDetails.lineItems" :key="item.bookId">
          <td>
            <div class="cart-book-image">
              <img
                class="book-image"
                :src="
                  bookImageUrl(bookImageFileName(bookAt(orderDetails, index)))
                "
                :alt="bookAt(orderDetails, index).title"
              />
            </div>
          </td>
          <td>
            {{ bookAt(orderDetails, index).title }}
          </td>
          <td>{{ item.quantity }}</td>
          <td>
            {{
              asDollarsAndCents(
                bookAt(orderDetails, index).price * item.quantity
              )
            }}
          </td>
        </tr>
      </tbody>
    </table>
    <div class="totalDetails">
      <div class="subtotal">
        <span class="title">Subtotal :</span>
        {{ asDollarsAndCents(orderDetails.order.amount - cart.surcharge) }}
      </div>
      <div class="surcharge">
        <span class="title">Surcharge :</span>
        {{ asDollarsAndCents(cart.surcharge) }}
      </div>
      <div class="total">
        <span class="title">Total Amount :</span>
        {{ asDollarsAndCents(orderDetails.order.amount) }}
      </div>
    </div>
  </div>
</template>
