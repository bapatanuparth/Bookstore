import { createApp } from "vue";
import { createPinia } from "pinia";
/* import the fontawesome core */
import { library } from "@fortawesome/fontawesome-svg-core";

/* import font awesome icon component */
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

/* import specific icons */
import {
  faUserSecret,
  faCartShopping,
  faMagnifyingGlass,
  faBook,
  faCaretDown,
  faUser,
  faCartPlus,
} from "@fortawesome/free-solid-svg-icons";

/* add icons to the library */
library.add(faUserSecret);
library.add(faCartShopping);
library.add(faMagnifyingGlass);
library.add(faBook);
library.add(faCaretDown);
library.add(faUser);
library.add(faCartPlus);

import App from "./App.vue";
import router from "./router";

import "./assets/global.css";

const PriceFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  minimumFractionDigits: 2,
});

export const asDollarsAndCents = function (cents: number) {
  return PriceFormatter.format(cents / 100.0);
};

const app = createApp(App);

app.use(createPinia());
app.use(router);

app.component("font-awesome-icon", FontAwesomeIcon).mount("#app");
