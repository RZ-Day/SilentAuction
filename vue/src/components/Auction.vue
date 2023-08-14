<template>
  <div class="auction-page">
    


    
    
    <div class="watch-list-label">
      <input type="checkbox" v-model="isChecked" />
      <span class="checkmark">Add To WatchList</span>
      </div>
    
    <p>Auction Begins: {{ formatDateTime(auction.startTime) }}</p>
    <p>Auction Ends: {{ formatDateTime(auction.endTime) }}</p>

    <h2>Items in this Auction:</h2>
    <ul class="item-list">
      <li v-for="item in auction.items" :key="item.itemId">
        <router-link :to="{ name: 'ItemIndex', params: { currentItemID: item.itemId } }" class="item-link">
          <div class="item-container">
            <h3>{{ item.itemName }}</h3>
            <img src="@/Assets/itemTemp.png" alt="Auction Icon" class="item-image" />
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
  data(){
    return {
      showWatchListMessage: false,
      isChecked: false
    };
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
    watchListChange(){
      this.showWatchListMessage = false;
    },
    addToWatchList(auction){
      this.addToWatchList= !this.addToWatchList;
      this.showWatchListMessage = true;
      this.usersWatchList(auction);
      window.alert("Your watch list is added " + auction.auctionName);
    },
    usersWatchList(auction){
      this.$store.commit("SAVE_USERSWATCHLIST", auction);
    }
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
  border: 1px solid #ccc;
  padding: 20px;
  margin: 20px 0;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
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
}

.watch-list-checkbox{
  margin-right: 10px;
}

.checkmark{
  font-weight: bold;
}
</style>

