<template>
  <div>
    <h1>This auction: {{ auction.auctionName }}</h1>
    <Auction :auction="auction" />
  </div>
</template>

<script>
import Auction from "../components/Auction.vue";
import auctionService from "@/services/AuctionsListService.js";

export default {
  components: { Auction },
  data() {
    return {
      auction: null 
    };
  },
  created() {
    const activeAuctionID = parseInt(this.$route.params.currentAuctionID);
    this.$store.commit("SET_ACTIVE_AUCTION", activeAuctionID);
    auctionService.getAuctions().then(response => {
      if (response.status === 200) {
        this.auction = response.data.find(auction => auction.auctionId === activeAuctionID);
      } else {
        console.log("Error fetching auctions");
      }
    });
  },
};
</script>

<style>
</style>