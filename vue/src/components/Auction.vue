<template>
  <div class="auction-page">
    <h1>{{ auction.auctionName }}</h1>
    <p>Auction Begins: {{ formatDateTime(auction.startTime) }}</p>
    <p>Auction Ends: {{ formatDateTime(auction.endTime) }}</p>
    
    <h2>Items in this Auction:</h2>
    <ul>
      <li v-for="item in auction.items" :key="item.itemId">
        <router-link :to="{name: 'ItemIndex', params: { currentItemID: item.itemId}}" class="item-link">
        <div class="item-container">
          <h3>{{ item.itemName }}</h3>
           <p><img src="@/Assets/itemTemp.png" alt="Auction Icon" class="header-icon" /></p>
          <p>{{ item.description }}</p>
          <p>Initial Price: ${{ item.initialPrice }}</p>
          <p>Current Price: ${{ item.currentPrice }}</p>
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
    auction: Object
  },
  methods: {
    formatDateTime(timestamp) {
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "numeric",
        minute: "numeric",
        second: "numeric",
        timeZoneName: "short",
      };
      return new Date(timestamp).toLocaleString(undefined, options);
    },
  },
};
</script>

<style scoped>
.auction-page {
  padding: 20px;
}

.item-container {
  border: 1px solid #ccc;
  padding: 10px;
  margin: 10px 0;
}
</style>
