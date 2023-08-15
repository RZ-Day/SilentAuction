<template>
  <div id="item-page">
    <div id="item-details">
      <Item :item="item" />
    </div>
    <div class="bids-section">
      <DisplayBids />
    </div>
  </div>
</template>

<script>
import Item from "../components/Item.vue"; // Import the Item component
import auctionService from "@/services/AuctionsListService.js";
import DisplayBids from "../components/DisplayBids.vue";

export default {
  components: {
    Item,
    DisplayBids,
  },
  data() {
    return {
      auction: null,
      item: null,
    };
  },
  created() {
    const activeItemID = parseInt(this.$route.params.currentItemID);
    const activeAuctionID = parseInt(this.$route.params.currentAuctionID);
    this.$store.commit("SET_ACTIVE_ITEM", activeItemID);
    auctionService.getAuctions().then((response) => {
      if (response.status === 200) {
        this.auction = response.data.find(
          (auction) => auction.auctionId === activeAuctionID
        );

        if (this.auction) {
          // Find the specific item within the auction's items array
          this.item = this.auction.items.find(
            (item) => item.itemId === activeItemID
          );
        }
      }
    });
  },
};
</script>


<style>
.item-page {
  display: flex;
}

.item-details {
  flex: 2; /* Take twice the space of bids section */
  margin: 0 5ch 5ch 5ch;
  border-radius: 5px;
}

.bids-section {
  flex: 1; /* Take up 1 unit of space */
  border-radius: 5px;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
  margin: 5ch 5ch 5ch 5ch;
  background-color: #f3f5f7;
}
</style>