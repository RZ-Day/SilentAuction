<template>
  <div id="app">
    <section class="topNav">
      <navbar />
    </section>
    <div class="spacing"></div>
    <div class="background-image"></div>
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

<style scoped>
  .topNav {
    width: 100%;
    margin: 0;
    position: fixed;
    min-height: 50px;
    z-index: 3000;
  }
  .router-view {
  position: relative;
  z-index: 1; /* Adjust as needed to ensure content is above the background */
}

.spacing {
  min-height: 50px;
}



</style>