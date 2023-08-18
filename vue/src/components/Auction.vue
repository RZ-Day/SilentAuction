<template>
  <div class="auction-page">
    <!-- <p>{{ formatDateTime(auction.startTime) }}</p>
    <p>{{ formatDateTime(auction.endTime) }}</p> -->
    <div class="spacer" >
      <h2>Up For Auction:</h2>
    </div>
    <ul class="item-list">
      <li v-for="item in auction.items" :key="item.itemId">
        <router-link
          :to="{ name: 'ItemIndex', params: { currentItemID: item.itemId } }"
          class="item-link"
        >
          <div class="item-container">
            <h3 class="item-name">{{ item.itemName }}</h3>
            <p class="price">${{ item.currentPrice }}.00</p>
            <!-- <img src="@/Assets/itemTemp.png" alt="Auction Icon" class="item-image" /> -->

            <!-- Images for each item -->
            <div
              v-for="(imageUrl, idx) in loadedImages[auction.auctionId][
                item.itemId
              ]"
              :key="idx"
              :style="{ 'background-image': `url(${imageUrl})` }"
              :class="{ active: currentImageIndices[item.itemId] === idx }"
              class="item-background"
            ></div>

            <p class="item-description">{{ item.description }}</p>
          </div>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "AuctionPage",
  props: {
    auction: Object,
  },
  data() {
    return {
      currentImageIndices: {},
      timers: [],
    };
  },
  computed: {
    loadedImages() {
      return this.$store.state.loadedImages;
    },
  },
  mounted() {
    this.startSlideshow();
  },
  beforeDestroy() {
    this.clearAllTimers();
  },
  methods: {
    formatDateTime(timestamp) {
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "numeric",
      };
      return new Date(timestamp).toLocaleString(undefined, options);
    },
    startSlideshow() {
      this.auction.items.forEach((item) => {
        if (
          this.loadedImages[this.auction.auctionId] &&
          this.loadedImages[this.auction.auctionId][item.itemId]
        ) {
          this.$set(this.currentImageIndices, item.itemId, 0); // Initialize current image index for each item
          this.nextImage(item.itemId);
        }
      });
    },
    nextImage(itemId) {
      const maxIdx =
        this.loadedImages[this.auction.auctionId][itemId].length - 1;
      const currentIdx = this.currentImageIndices[itemId];
      const nextIdx = currentIdx + 1 <= maxIdx ? currentIdx + 1 : 0;
      this.$set(this.currentImageIndices, itemId, nextIdx);

      const randomTime = Math.floor(Math.random() * (7000 - 4000 + 1)) + 4000;
      const timer = setTimeout(() => {
        this.nextImage(itemId);
      }, randomTime);

      this.timers.push(timer);
    },
    clearAllTimers() {
      this.timers.forEach((timer) => clearTimeout(timer));
    },
  },
};
</script>



<style scoped>
.auction-page {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  padding: 20px;
}

.item-link {
  text-decoration: none;
  color: inherit;
}

.spacer {
  border-width: 1px;
  border-bottom-style: solid;
  border-top-style: solid;
  margin-bottom: 20px;
  border-color: rgb(199, 199, 199);
}

.item-container {
  display: flex;
  flex-direction: column;
  /* border: 1px solid #ccc; */
  padding-left: 20px;
  padding-right: 20px;
  margin: 0px 0;
  background-color: #fff;
  position: relative;
  /* box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); */
  height: 200px;
  transition: 0.5s ease;
}

.item-description {
  position: absolute;
  padding-top: 200px;
  color: transparent;
  transition: 0.5s ease;
}

.item-container:hover {
  transform: scale(1.2);
  box-shadow: 0 60px 30px rgba(0, 0, 0, 0.212);
  z-index: 100;
}

.item-container:hover .item-description {
  color: white;
}



.price {
  display: flex;
  margin-top: 0;
  color: rgb(0, 241, 32);
  font-weight: bold;
  -webkit-text-stroke: 1px rgb(31, 175, 31);
  z-index: 2;
}

.item-name:hover .price {
  z-index: 101;
}

.item-name {
  color: white;
  font-weight: bold;
  text-transform: uppercase;
  -webkit-text-stroke: 1px rgb(0, 0, 0);
  z-index: 2;
}

.item-container:hover .item-name {
  z-index: 101;
}

.item-image {
  max-width: 100px;
  height: auto;
  margin: 10px auto;
}

.item-description {
  margin-top: 10px;
}

.item-list {
  list-style: none; /* Remove bullet points */
  padding: 0;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr;
}

.item-background {
  transition: opacity 1s ease-in-out;
  opacity: 0;
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background-size: cover; /* Will scale the image to cover the entire element. */
  background-position: center; /* Centers the image within the element. */
  background-repeat: no-repeat; /* Ensures the image doesn't repeat. */
}

.item-background.active {
  opacity: 1;
}
</style>

