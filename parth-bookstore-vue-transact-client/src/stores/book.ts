import { defineStore } from "pinia";
import type { BookItem, CategoryItem } from "@/types";
import { apiUrl } from "@/api";
import { useCategoryStore } from "./Category";

export const useBookStore = defineStore("book", {
  state: () => ({
    bookList: [] as BookItem[],
  }),
  actions: {
    async fetchBooks(categoryName: string) {
      const categoryStore = useCategoryStore();

      let selectedCategoryName = categoryName;

      const selectedCategory = categoryStore.categoryList?.find(
        (category) => category.name === categoryName
      );
      if (selectedCategory) {
        selectedCategoryName = selectedCategory.name;
      }

      const url =
        apiUrl + "/categories/name/" + selectedCategoryName + "/books/";
      this.bookList = await fetch(url).then((response) => response.json());
    },
  },
});
