import type { OrderDetails } from "../types";
// });
import { defineStore } from "pinia";

const ORDER_DETAIL_STORAGE_KEY = "orederDetails";

export const useOrderDetailsStore = defineStore("orderDetailsStore", {
  state: () => {
    let orderDetails = {} as OrderDetails;
    const initOrderString: string | null = sessionStorage.getItem(
      ORDER_DETAIL_STORAGE_KEY
    );
    if (initOrderString != null) {
      const orderFormStorage = JSON.parse(initOrderString) as OrderDetails;
      orderDetails = Object.assign({}, orderFormStorage);
    }

    return {
      orderDetails: orderDetails,
    };
  },
  actions: {
    clearOrderDetails() {
      sessionStorage.removeItem(ORDER_DETAIL_STORAGE_KEY);
      this.orderDetails = {} as OrderDetails;
    },
    setOrderDetails(orderDetails: OrderDetails) {
      this.orderDetails = orderDetails;
      sessionStorage.setItem(
        ORDER_DETAIL_STORAGE_KEY,
        JSON.stringify(orderDetails)
      );
    },
    hasOrderDetails() {
      return sessionStorage.getItem(ORDER_DETAIL_STORAGE_KEY) != null;
    },
  },
});
