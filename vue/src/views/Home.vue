<template>
  <div class="home">
    <div class="header">
      <h1>Explore Auctions With Silent Auction</h1>
      <p class="sub-header"> Simple. Fun. Reliable.</p>
    </div>
    <div class="content">



      <div class="button-container">
        <router-link
          to="/auction/create"
          class="auctions-button"
          id="button-left"
          >Host An Auction</router-link
        >
        <router-link to="/auctions" class="auctions-button" id="button-mid"
          >Bid On Live Auctions</router-link
        >
        <router-link
          to="/auctions/search/private"
          class="auctions-button"
          id="button-right"
          >Join Private Auction</router-link
        >
      </div>
    </div>

    <div class="info-section">
      <Slideshow />
    </div>


  </div>
</template>

<script>
import Slideshow from '@/components/HomeSlideshow.vue';
export default {
  name: "home",
  components: {Slideshow},
};
</script>

<style scoped>
.home {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
  opacity: 0; /* Start with 0 opacity */
  animation: fadeIn 3s forwards;
}
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

.header {
  text-align: center;
  margin-bottom: 20px;
  color: #1e2842;
  font-size: 32px;
}

.auctions-button {
  display: inline-block;
  padding: 10px 20px;
  background-color: #141b2c;
  color: #ffffff;
  border: none;
  border-radius: 2px;
  text-decoration: wavy;
  cursor: pointer;
  transition: background-color 0.3s;
  max-height: 50px;
  width: 20%;
  height: 20;
  box-shadow: 0 16px 32px rgba(0, 0, 0, 0.1);
  animation: fadeIn 6s;

  transition: transform 0.3s ease-in-out;
}

#button-left {
  animation: bounceAndEnlarge 1s ease-in-out 3s, shake 2.5s ease-in-out 16s;
}

#button-mid {
  animation: bounceAndEnlarge 1s ease-in-out 3.5s, shake 2.5s ease-in-out 8.5s;
}

#button-right {
  animation: bounceAndEnlarge 1s ease-in-out 4s, shake 2.5s ease-in-out 32s;
}
@keyframes bounceAndEnlarge {
  0%,
  100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
}

@keyframes shake {
  0%,
  100% {
    transform: translateX(0);
  }
  10%,
  30%,
  50%,
  70%,
  90% {
    transform: translateX(-5px);
  }
  20%,
  40%,
  60%,
  80% {
    transform: translateX(5px);
  }
}
.auctions-button:hover {
  background-color: #253b6e;
  transform: scale(1.1);
}

.content {
  text-align: center;
}

.content p {
  font-size: 64px;
}

.button-container {
  display: flex;
  flex: 1;
  justify-content: space-around;
  margin-top: 20px;
  max-width: 100%;
}

.info-section {
  margin-top: 50px;
}


</style>

