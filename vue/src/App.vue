<template>
  <div id="app">
    <section class="topNav">
      <navbar />
    </section>
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
    min-height: 150px;
  }
</style>