<template>
  <div class="item-page">
    <div>
    <button type="button" class="custom-button" @click="addToWatchList">Add to my Watchlist</button>
    
    </div>
    <div class="item-details">
      <h2>{{ item.itemName }}</h2>
      <p>Description: {{ item.description }}</p>
    </div>

    <div class="item-image">
      <img src="@/Assets/itemTemp.png" alt="Auction Icon" class="item-icon" />
    </div>
  </div>
</template>

<script>

import watchListService from '@/services/WatchlistService.js';

export default {
  name: "ItemPage",
  props: {
    item: Object,
  },
  methods: {
    addToWatchlist() {
      watchListService.add(this.$store.state.user.id, this.$store.state.activeItemId).then(() => {
        alert("item added");
      })
    },
  },
};
</script>

<style scoped>
.item-page {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}


.item-details,
.item-image {
  margin: 20px; 
}

.item-details {
  text-align: center;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.item-details h2 {
  margin: 0;
  font-size: 1.5rem;
  color: #192a3d;
}

.item-image {
  margin-left: 20px;
}

.item-icon {
  width: 400px;
  height: auto;
}

.custom-button {
  margin-top: 10px;
  background-color: black;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

</style>
