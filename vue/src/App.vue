<template>
  <div id="app">
    <section class="topNav">
      <navbar />
    </section>
    <div class="spacing" />
    <router-view />
  </div>
</template>

<script>
import Navbar from './components/Navbar.vue';

  export default {
    components: {
      Navbar
    }
  }
</script>

<style scope>
  .topNav {
    width: 100%;
    margin: 0;
    position: fixed;
    min-height: 50px;
    z-index: 3000;
  }

  .spacing {
    min-height: 50px;
  }
</style>