<template>
  <div class="auction-page">
    <div class="auction-details">
      <h1>The {{ auction.auctionName }} Auction!</h1>
    
      <div id="item-list">
      <Auction :auction="auction" />
      </div>
    </div>

    <div class="bids-section">
      <DisplayBids />
    </div>
  </div>
</template>

<script>
import Auction from "../components/Auction.vue";
import auctionService from "@/services/AuctionsListService.js";
import DisplayBids from '../components/DisplayBids.vue';

export default {
  components: { Auction, DisplayBids },
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
.auction-page {
  display: flex;
  justify-content: center;
  align-items: flex-start; /* Align items to the top of the container */
  min-height: 100vh;
  background-color: #f3f5f7;
  padding: 20px;
}

.auction-details {
  flex: 2; /* Take twice the space of bids section */
  margin-right: 20px; /* Add spacing between the auction details and bids */
}

.bids-section {
  flex: 1; /* Take up 1 unit of space */
  background-color: #f0f0f0; /* Set the background color for the bids section */
  padding: 10px;
  border-radius: 5px;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
}


</style>