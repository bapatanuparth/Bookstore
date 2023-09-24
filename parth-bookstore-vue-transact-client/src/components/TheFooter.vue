<script setup lang="ts"></script>

<style scoped>
footer {
  background: var(--footer-color);
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 999;
}

.links {
  display: flex;
  flex-direction: row;
  gap: 0.3rem;
  position: relative;
  right: 2rem;
}

.social-media-icons {
  display: flex;
  flex-direction: row;
  gap: 1.2rem;
  position: relative;
  padding: 0 2rem;
}

.links a,
.links p {
  color: white;
  font-weight: 100;
}

.social-media-icons {
  text-align: center;
  gap: 1.25em;
}

.copyright {
  color: white;
  font-size: small;
  padding: 0 2rem;
}
</style>

<template>
  <footer class="container">
    <div class="copyright">© 2023 BookMark, Inc.</div>
    <section class="links">
      <router-link to="#">about</router-link>
      <p>|</p>
      <router-link to="#">contact</router-link>
      <p>|</p>
      <router-link to="#">directions</router-link>
    </section>
    <section class="social-media-icons">
      <router-link to="/"
        ><img src="/site/facebook.png" alt="facebook"
      /></router-link>
      <router-link to="/"
        ><img src="/site/twitter.png" alt="twitter"
      /></router-link>
    </section>
  </footer>
</template>
