<template>
  <div class="bids-section">
    <h2>This Auction's Current Bids</h2>
    <ul class="bid-list" v-show="this.bids.length > 0">
      <li v-for="bid in this.bids" :key="bid.bidId"> <!-- switched from using sortedBids to bids -->
        {{ bid.bidItem.itemName }}: {{ !bid.bidAmount ? "No bids yet" : "$" + bid.bidAmount + " by " + bid.username }}
      </li>
    </ul>
    <p v-show="this.bids.length == 0">No bids have been made on any of these items yet. Maybe you can be the first?</p>
  </div>
</template>

<script>
import bidService from "@/services/BidService.js";
export default {
  data() {
    return {
      bids: [
      ],
    };
  },
  created() {
    bidService.getBidsOfAuction(this.$route.params.currentAuctionID).then((response) => { 
      this.bids = response.data;
    });
  },
  computed: {
    sortedBids() {
      return this.bids.slice().sort((a, b) => b.amount - a.amount);
    },
    highestBid() {
      return this.sortedBids.length > 0 ? this.sortedBids[0].amount : 0;
    }
  },
  methods: {
    showBidForm() {
      this.showForm = true;
    },
    submitBid() {
      if (
        this.newBid.bidderName &&
        this.newBid.amount &&
        this.newBid.amount > this.highestBid
      ) {
        const newBidId = this.bids.length + 1;
        this.bids.push({
          bidId: newBidId,
          bidderName: this.newBid.bidderName,
          amount: this.newBid.amount
        });
        
        this.newBid.bidderName = '';
        this.newBid.amount = null;
        this.showForm = false;
      } else {
          alert('Your bid must be greater than current higest bid!');
      }
    }
  }
}
</script>

<style scoped>
.bids-section {
  
}

.bid-list {
  
}
</style>
