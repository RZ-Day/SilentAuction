<template>
  <div class="slideshow-container">
    <div
      v-for="(slide, index) in slides"
      :key="index"
      class="slide"
      v-bind:class="{ active: currentIndex === index }"
    >
      <img
        :src="require(`@/Assets/${slide.image}`)"
        :alt="'Slide ' + (index + 1)"
      />
      <div class="slide-content">
        <h2>{{ slide.header }}</h2>
        <p>{{ slide.paragraph }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      slides: [
        {
          image: "bid.png",
          header: "Anonymous Bidding",
          paragraph: "Your identity is hidden from other bidders",
        },
        {
          image: "auctionLive.jpg",
          header: "Live Auctions",
          paragraph: "Organize Fundraisers, Plan Events",
        },
        {
          image: "silent-auction-bidder.jpg",
          header: "Unlimited Access",
          paragraph: "Bid and Win from your phone anywhere, anytime",
        },
      ],
      currentIndex: 0,
    };
  },
  methods: {
    showNextSlide() {
      this.currentIndex = (this.currentIndex + 1) % this.slides.length;
    },
  },
  mounted() {
    setInterval(this.showNextSlide, 5000); 
  },
};
</script>

<style scoped>
.slideshow-container {
  position: relative;
  max-width: 400px;
  margin-top: 30px;
  border: 2px solid rgb(68, 59, 59);
  overflow: hidden;
  width: 800px;
  height: 292px; 
  background-color: rgb(255, 255, 255);
}

.slide {
  display: none;
}

.slide img {
  width: 100%;
  height: 100%;
}


.slide-content {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  background-color: rgb(0, 0, 0);
  color: white;
  padding: 10px;
  text-align: cen;
}

h2,
p {
  margin: 0;
}

.active {
  display: block;
}

@keyframes fade {
  0% {
    opacity: 0;
  }
  20%,
  80% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
}
</style>
