<template>
  <div>
      <Item :item="item" />
  </div>
</template>

<script>
import Item from "../components/Item.vue"; // Import the Item component
import auctionService from "@/services/AuctionsListService.js";

export default {
  components: {
    Item
  },
  data() {
    return {
      auction: null,
      item: null // Initialize item as null
    };
  },
  created() {
    const activeItemID = parseInt(this.$route.params.currentItemID);
    const activeAuctionID = parseInt(this.$route.params.currentAuctionID);
    this.$store.commit("SET_ACTIVE_ITEM", activeItemID);
    auctionService.getAuctions().then(response => {
      if (response.status === 200) {
        this.auction = response.data.find(
          auction => auction.auctionId === activeAuctionID
        );

        if (this.auction) {
          // Find the specific item within the auction's items array
          this.item = this.auction.items.find(
            item => item.itemId === activeItemID
          );
        }
      }
    });
  }
};
</script>


<style>

</style>