<template>
  <div class="auction-page">
    
    <div class="auction-details">
    <h1 class="auction-details-header">The {{ auction.auctionName }} Auction</h1>  
    
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
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
  border-radius: 5px;
  
}

.auction-details {
  flex: 2; /* Take twice the space of bids section */
  margin: 0 5ch 5ch  5ch;
  border-radius: 5px;
  
}


.bids-section {
  flex: 1; /* Take up 1 unit of space */
  border-radius: 5px;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
  margin: 5ch 5ch 5ch  5ch;
   background-color: #f3f5f7;
}


</style>