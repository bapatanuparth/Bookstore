<script setup lang="ts">
import TheHomeCategoryList from "@/components/TheHomeCategoryList.vue";
</script>
<style scoped>
main {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.home-page {
  display: flex;
  background-image: url("../../public/site/background-books.jpg");
  flex: 1;
  background-repeat: round;
  justify-content: end;
  height: 100%;
}

.welcome-text {
  width: 22em;

  font-size: x-large;
}

.category-images h3 {
  position: relative;
  width: 50%;
  top: 1rem;
  left: 18rem;
}

.category-image-items a {
  text-align: center;
  cursor: pointer;
}

.category-image-items img {
  width: 100px;
  height: 150px;
  border-color: var(--footer-color);
  box-shadow: 5px 5px 10px 0px rgba(0, 0, 0, 0.75);
}

.category-image-items div {
  padding: 0.5em 0;
  box-shadow: 5px 5px 10px 0px rgba(112, 112, 112, 0.521);
  color: white;
  transform: translateY(-2.25em);
  margin-bottom: -2em;
}

.backdrop-img {
  position: relative;
}

.home-text-images {
  width: fit-content;
  right: 0;
  padding-right: 10vw;
  padding-top: 5vh;
}

p {
  font-family: var(--title-font-family);
  font-size: large;
}

.call-to-action {
  padding-top: 2rem;
}

.cta,
.cta:visited {
  border-radius: 15px;
  display: inline-block;
  background: var(--cta-color);
  color: black;
  font-weight: 700;
  font-size: larger;
  text-decoration: none;
  padding: 0.5em 1em;
  cursor: pointer;
  box-shadow: 5px 5px 10px 0px rgba(112, 112, 112, 0.521);
  transition: background-color 0.4s, color 0.3s;
}

.cta:hover,
.cta:active {
  background: var(--primary-color-dark);
  color: white;
}
</style>

<template>
  <main>
    <div class="home-page">
      <section class="home-text-images">
        <section class="welcome-text flow-content">
          <h2>Welcome to The BookMark!</h2>
          <p>
            Browse our vast selection of curated fiction, non-fiction books and
            bestsellers.
          </p>
          <p>Find your next great read today!</p>
        </section>
        <section class="call-to-action">
          <router-link to="../category/Fiction" class="cta">
            SHOP NOW!
          </router-link>
        </section>
        <section class="category-images">
          <the-home-category-list></the-home-category-list>
        </section>
      </section>
    </div>
  </main>
</template>
