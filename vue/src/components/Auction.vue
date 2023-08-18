<template>
  <div class="auction-page">
    
    <p>From: {{ formatDateTime(auction.startTime) }}</p>
    <p>To: {{ formatDateTime(auction.endTime) }}</p>

    <h2>Up For Auction:</h2>
    <ul class="item-list">
      <li v-for="item in auction.items" :key="item.itemId">
        <router-link :to="{ name: 'ItemIndex', params: { currentItemID: item.itemId } }" class="item-link">
          <div class="item-container">
            <h3>{{ item.itemName }}</h3>
            <p class="price">${{item.currentPrice}}.00</p>
            <!-- <img src="@/Assets/itemTemp.png" alt="Auction Icon" class="item-image" /> -->
            
            <!-- Images for each item -->
            <div class="item-images" v-if="loadedImages[auction.auctionId] && loadedImages[auction.auctionId][item.itemId]">
              <img v-for="(imageUrl, idx) in loadedImages[auction.auctionId][item.itemId]" 
                   :key="idx" 
                   :src="imageUrl" 
                   alt="Image of the item" />
            </div>
            
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
  computed: {
    loadedImages() {
      return this.$store.state.loadedImages;
    },
  },
  methods: {
    formatDateTime(timestamp) {
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "numeric",
        // minute: "numeric",
        // second: "numeric",
        // timeZoneName: "short",
      };
      return new Date(timestamp).toLocaleString(undefined, options);
    },
  },
};
</script>


<style scoped>
.auction-page {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: #f3f5f7;
  padding: 20px;
}

.item-link {
  text-decoration: none;
  color: inherit;
}

.item-container {
  display: flex;
  flex-direction: column;
  border: 1px solid #ccc;
  padding-left: 20px;
  padding-right: 20px;
  margin: 0px 0;
  background-color: #fff;
  /* box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); */
  height:200px;
  transition: 0.5s ease;
}

.item-description {
  position: absolute;
  padding-top: 200px;
  color: transparent;
  transition: .5s ease;
}

.item-container:hover {
  transform: scale(1.2);
  box-shadow: 0 60px 30px rgba(0, 0, 0, 0.212);
}

.item-container:hover .item-description {
  color: white;
}

.price {
  margin-top: 0;
  color: rgb(0, 241, 32);
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
</style>

