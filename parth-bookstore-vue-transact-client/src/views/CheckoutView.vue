<script setup lang="ts">
import { reactive } from "vue";
import useVuelidate from "@vuelidate/core";
import { useCategoryStore } from "@/stores/Category";
import {
  helpers,
  maxLength,
  minLength,
  required,
  email,
} from "@vuelidate/validators";
import { useCartStore } from "@/stores/cart";
import { isCreditCard, isMobilePhone } from "../validators";

import router from "@/router";
import { ref } from "vue";
import type { OrderDetails, ServerErrorResponse } from "@/types";
import { asDollarsAndCents } from "../main";

const categoryStore = useCategoryStore();
const cartStore = useCartStore();

const cart = cartStore.cart;

const defaultServerErrorMessage =
  "An unexpected error occurred, please try again.";
const serverErrorMessage = ref(defaultServerErrorMessage);
const currentYear = new Date().getFullYear();
const maxYear = currentYear + 15; // Max expiry year is current year + 10
const years: number[] = [];

for (let year = currentYear; year <= maxYear; year++) {
  years.push(year);
}

const months: string[] = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

const form = reactive({
  name: "",
  address: "",
  phone: "",
  email: "",
  ccNumber: "",
  ccExpiryMonth: new Date().getMonth() + 1,
  ccExpiryYear: new Date().getFullYear(),
  checkoutStatus: "",
});

const rules = {
  name: {
    required: helpers.withMessage("Please provide a name.", required),
    minLength: helpers.withMessage(
      "Name must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Name can have at most 45 letters.",
      maxLength(45)
    ),
  },
  address: {
    required: helpers.withMessage("Please provide an address", required),
    minLength: helpers.withMessage(
      "Address must have at least 4 letters.",
      minLength(4)
    ),
    maxLength: helpers.withMessage(
      "Address can have at most 45 letters.",
      maxLength(45)
    ),
  },
  email: {
    required: helpers.withMessage("Please provide an email address", required),
    email: helpers.withMessage("Please enter a valid email address.", email),
  },
  phone: {
    required: helpers.withMessage("Please provide a phone number.", required),
    phone: helpers.withMessage(
      "Please enter a valid US mobile phone number.",
      (value: string) => {
        if (!value) {
          // If the phone number is empty or null, return true
          return true;
        }
        return isMobilePhone(value);
      }
    ),
  },
  ccNumber: {
    required: helpers.withMessage(
      "Please provide a credit card number.",
      required
    ),
    ccNumber: helpers.withMessage(
      "Please enter a valid Card number.",
      (value: string) => {
        if (!value) {
          // If the phone number is empty or null, return true
          return true;
        }
        return isCreditCard(value);
      }
    ),
  },
  ccExpiryMonth: {},
  ccExpiryYear: {},
};
const v$ = useVuelidate(rules, form);

async function submitOrder() {
  console.log("Submit order");
  const isFormCorrect = await v$.value.$validate();
  if (!isFormCorrect) {
    form.checkoutStatus = "ERROR";
  } else {
    try {
      form.checkoutStatus = "PENDING";

      serverErrorMessage.value = defaultServerErrorMessage;

      const placeOrderResponse: OrderDetails | ServerErrorResponse =
        await cartStore.placeOrder({
          name: form.name,
          address: form.address,
          phone: form.phone,
          email: form.email,
          ccNumber: form.ccNumber,
          ccExpiryMonth: form.ccExpiryMonth,
          ccExpiryYear: form.ccExpiryYear,
        });

      if ("error" in placeOrderResponse) {
        form.checkoutStatus = "SERVER_ERROR";
        serverErrorMessage.value = (
          placeOrderResponse as ServerErrorResponse
        ).message;
        console.log("Error placing order", placeOrderResponse);
      } else {
        form.checkoutStatus = "OK";
        await router.push({ name: "confirmation-view" });
      }
    } catch (e) {
      form.checkoutStatus = "SERVER_ERROR";
      serverErrorMessage.value = defaultServerErrorMessage;
      console.log("Error placing order", e);
    }
  }
}

function resetForm() {
  form.name = "";
  form.address = "";
  form.phone = "";
  form.email = "";
  form.ccNumber = "";
  form.ccExpiryMonth = new Date().getMonth() + 1;
  form.ccExpiryYear = new Date().getFullYear();
  form.checkoutStatus = "";
  v$.value.$reset();
}

/* NOTE: For example yearFrom(0) == <current_year> */
function yearFrom(index: number) {
  return new Date().getFullYear() + index;
}
</script>

<style scoped>
/* TODO: Adapt these styles to your page */
.checkout-page {
  padding: 2rem;
  background-color: #fff7e2;
  flex: 1;
}
.checkout-page-body {
  display: flex;
  padding: 0.5em;
  justify-content: center;
  align-items: center;
  margin: auto;
  flex-direction: column;
  gap: 2rem;
}

.formWrapper {
  background-color: #ffe9b1;
  display: flex;
  flex-direction: column;
  width: 28em;
  gap: 1rem;
  border: 1px solid #666666;
  border-radius: 10px;
  padding: 1.5rem;
  align-items: center;
}

form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
}

form > div > input,
form > div > select {
  background-color: #666666;
}

.inputField {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.inputArea {
  height: 40px;
  width: 15em;
  border-radius: 10px;
  background-color: #fffefb;
  border: none;
  border: 1px solid #666666;
}

.inputArea:focus {
  outline: none;
}

.inputline {
  display: flex;
  flex-direction: column;
}
.error {
  color: red;
  align-self: flex-end;
  font-style: italic;
}

.checkoutStatusBox {
  font-size: large;
  font-weight: 500;
  text-align: center;
  border: 1px solid #666666;
  border-radius: 10px;
  padding: 10px;
  background-color: #666666;
  color: white;
}

.emptyCart {
  margin: 1em auto;
  text-align: center;
  text-decoration: underline;
}

.head {
  margin-bottom: 20px;
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

.expiryDate {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.expiryInputArea {
  margin-left: 10px;
  height: 40px;
  border: 1px solid #666666;
  border-radius: 10px;
}

.expiryInputArea:focus {
  outline: none;
}

label {
  margin-left: 1.5rem;
  font-weight: 500;
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
  margin-top: 1rem;
}

.checkoutButton:hover {
  background-color: var(--add-to-cart-hover);
}

.checkoutButton[disabled] {
  cursor: not-allowed;
  opacity: 0.5;
}

.cartDetails {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.total {
  font-weight: 600;
  text-decoration: underline;
}

h1 {
  font-size: 30px;
}

.expiryInput {
  margin-right: 22px;
}
</style>

<template>
  <div class="checkout-page">
    <div class="emptyCart" v-if="cartStore.cart.items.length === 0">
      <h3 class="head">There are no items in cart...</h3>
      <router-link
        :to="{
          name: 'category-view',
          params: { name: categoryStore.selectedCategoryName },
        }"
        ><button class="continueShopping">Continue Shopping</button>
      </router-link>
    </div>

    <section class="checkout-page-body" v-if="!cart.empty">
      <h1>Checkout Page</h1>

      <div class="formWrapper">
        <form @submit.prevent="submitOrder">
          <div class="inputline">
            <div class="inputField">
              <label for="name" class="label">Name :</label>
              <input
                class="inputArea"
                type="text"
                size="20"
                id="name"
                name="name"
                v-model.lazy="v$.name.$model"
              />
            </div>
            <template v-if="v$.name.$error">
              <span
                class="error"
                v-for="error of v$.name.$errors"
                :key="error.$uid"
                >{{ error.$message }}</span
              >
            </template>
          </div>

          <div class="inputline">
            <div class="inputField">
              <label for="address">Address :</label>
              <input
                class="inputArea"
                type="text"
                size="20"
                id="address"
                name="address"
                v-model.lazy="v$.address.$model"
              />
            </div>
            <template v-if="v$.address.$error">
              <span
                class="error"
                v-for="error of v$.address.$errors"
                :key="error.$uid"
                >{{ error.$message }}</span
              >
            </template>
          </div>

          <div class="inputline">
            <div class="inputField">
              <label for="phone">Phone :</label>
              <input
                class="inputArea"
                type="text"
                size="20"
                id="phone"
                name="phone"
                v-model.lazy="v$.phone.$model"
              />
            </div>

            <template v-if="v$.phone.$error">
              <span
                class="error"
                v-for="error of v$.phone.$errors"
                :key="error.$uid"
                >{{ error.$message }}</span
              >
            </template>
          </div>

          <div class="inputline">
            <div class="inputField">
              <label for="email">Email :</label>
              <input
                class="inputArea"
                type="text"
                size="20"
                id="email"
                name="email"
                v-model.lazy="v$.email.$model"
              />
            </div>

            <template v-if="v$.email.$error">
              <span
                class="error"
                v-for="error of v$.email.$errors"
                :key="error.$uid"
                >{{ error.$message }}</span
              >
            </template>
          </div>

          <div class="inputline">
            <div class="inputField">
              <label for="ccNumber">Credit card :</label>
              <input
                class="inputArea"
                type="text"
                size="20"
                id="ccNumber"
                name="ccNumber"
                v-model.lazy="v$.ccNumber.$model"
              />
            </div>

            <template v-if="v$.ccNumber.$error">
              <span
                class="error"
                v-for="error of v$.ccNumber.$errors"
                :key="error.$uid"
                >{{ error.$message }}</span
              >
            </template>
          </div>

          <div class="expiryDate">
            <label>Exp Date :</label>
            <div class="expiryInput">
              <select v-model="v$.ccExpiryMonth.$model" class="expiryInputArea">
                <option
                  v-for="(month, index) in months"
                  :key="index"
                  :value="index + 1"
                >
                  {{ month }} ({{ index + 1 }})
                </option>
              </select>
              <select v-model="v$.ccExpiryYear.$model" class="expiryInputArea">
                <option v-for="year in years" :key="year" :value="year">
                  {{ year }}
                </option>
              </select>
            </div>
          </div>
        </form>
        <input
          id="checkoutButton"
          type="submit"
          name="submit"
          class="checkoutButton"
          :disabled="form.checkoutStatus === 'PENDING'"
          value="Complete Purchase"
          @click="submitOrder"
        />
        <button class="continueShopping" @click="resetForm">Reset Form</button>
        <div class="cartDetails">
          <span class="text"
            >Your credit card will be charged
            <span class="total">{{ asDollarsAndCents(cart.total) }}</span></span
          >
          <span class="text">
            (
            <span class="total"> {{ asDollarsAndCents(cart.subtotal) }}</span> +
            <span class="total">{{ asDollarsAndCents(cart.surcharge) }}</span>
            ) shipping</span
          >
        </div>
      </div>

      <section v-show="form.checkoutStatus !== ''" class="checkoutStatusBox">
        <div v-if="form.checkoutStatus === 'ERROR'">
          Error: Please fix the problems above and try again.
        </div>

        <div v-else-if="form.checkoutStatus === 'PENDING'">Processing...</div>

        <div v-else-if="form.checkoutStatus === 'OK'">Order placed...</div>

        <div v-else>{{ serverErrorMessage }}</div>
      </section>
    </section>
  </div>
</template>
