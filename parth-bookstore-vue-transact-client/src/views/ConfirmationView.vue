<script setup lang="ts">
import CartTable from "@/components/CartTable.vue";
import { useCartStore } from "@/stores/cart";
import { useCategoryStore } from "@/stores/Category";
import ConfirmationTable from "@/components/ConfirmationTable.vue";

import { useOrderDetailsStore } from "@/stores/OrderDetails";
import { computed } from "vue";
const orderDetailsStore = useOrderDetailsStore();
const categoryStore = useCategoryStore();
const orderDetails = orderDetailsStore.orderDetails;

const orderDate = computed(function () {
  let date = new Date(orderDetails.order.dateCreated);
  return date.toDateString();
});
const orderTime = computed(function () {
  let date = new Date(orderDetails.order.dateCreated);
  return date.toLocaleTimeString();
});

const ccExpDate = computed(function (): Date {
  return new Date(orderDetails.customer.ccExpDate);
});

const ccExpYear = computed(function (): number {
  return ccExpDate.value.getFullYear();
});

const ccExpMonth = computed(function (): string {
  let month = ccExpDate.value.getMonth() + 1;
  let result = "" + month;
  if (month < 10) {
    result = "0" + month;
  }
  return result;
});

const maskedCCNumber = function () {
  const cc = orderDetails.customer.ccNumber;
  const maskedCC = "**** **** **** " + cc.slice(-4);

  return maskedCC;
};
</script>
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
.confirmationView {
  display: flex;

  gap: 5rem;
}
ul {
  text-align: left;
}
ul > li {
  margin: 0.25em;
}

.confirmationDetailsWrapper {
  display: flex;
  flex-direction: column;

  gap: 1rem;
}

.confirmationMetadata {
  padding: 1rem;
  border: 1px solid darkgray;
  border-radius: 10px;
  background-color: #ffcd4e;
}

.customerDetails {
  padding: 1rem;
  border: 1px solid darkgray;
  border-radius: 10px;
  background-color: #ffcd4e;
}
.title {
  font-weight: 600;
}
.customerDetailHeading {
  font-weight: 700;
  font-size: large;
}

.emptyOrder {
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

.wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>

<template>
  <div class="checkout">
    <div
      v-if="!orderDetails || !orderDetailsStore.hasOrderDetails()"
      class="emptyOrder"
    >
      <h1>We are sorry, the order you requested could not be found</h1>
      <router-link
        :to="{
          name: 'category-view',
          params: { name: categoryStore.selectedCategoryName },
        }"
        ><button class="continueShopping">Continue Shopping</button>
      </router-link>
    </div>
    <div class="wrapper" v-else>
      <h1>Purchase confirmed</h1>
      <div class="confirmationView">
        <div class="confirmationDetailsWrapper">
          <ul class="confirmationMetadata">
            <li class="customerDetailHeading">Order Details</li>
            <li>
              <span class="title">Confirmation #:</span>
              {{ orderDetails.order.confirmationNumber }}
            </li>
            <li>
              <span class="title">Time :</span> {{ orderDate }} -
              {{ orderTime }}
            </li>
          </ul>
          <ul class="customerDetails">
            <li class="customerDetailHeading">Customer Details</li>
            <li>
              <span class="title">Name :</span>
              {{ orderDetails.customer.customerName }}
            </li>
            <li>
              <span class="title">Address :</span>
              {{ orderDetails.customer.address }}
            </li>
            <li>
              <span class="title">Email :</span>
              {{ orderDetails.customer.email }}
            </li>
            <li>
              <span class="title">phone :</span>
              {{ orderDetails.customer.phone }}
            </li>
            <li>
              <span class="title">Credit card details :</span>
              {{ maskedCCNumber() }} ({{ ccExpMonth }} - {{ ccExpYear }})
            </li>
          </ul>
        </div>
        <div class="tableWrapper">
          <span class="customerDetailHeading">Books you ordered:</span>
          <confirmation-table> </confirmation-table>
        </div>
      </div>
      <router-link
        :to="{
          name: 'category-view',
          params: { name: categoryStore.selectedCategoryName },
        }"
        ><button class="continueShopping">Continue Shopping</button>
      </router-link>
    </div>
  </div>
</template>
