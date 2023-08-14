<template>
  <div class="bids-section">
    <h2>List of Bids</h2>
    <ul class="bid-list" v-show="this.bids.length > 0">
      <li v-for="bid in sortedBids" :key="bid.bidId">
        {{ bid.username }}: ${{ bid.bidAmount }} at {{ bid.bidTime }}
      </li>
    </ul>
    <p v-show="this.bids.length == 0">No bids have been made on this item yet. Maybe you can be the first?</p>
    
    <button @click="showBidForm">Place Bid</button>
    
    <form v-if="showForm" @submit.prevent="submitBid">
      <label for="bidderName">Bidder Name:</label>
      <input type="text" v-model="newBid.bidderName" required>
      
      <label for="bidAmount">Bid Amount:</label>
      <input type="number" v-model="newBid.amount" required>
      
      <button type="submit">Submit Bid</button>
    </form>
  </div>
</template>

<script>
import bidService from "@/services/BidService.js";
export default {
  data() {
    return {
      bids: [
        { bidId: 1, bidderName: 'UserA', amount: 150 },
        { bidId: 2, bidderName: 'UserB', amount: 180 },
        { bidId: 3, bidderName: 'UserC', amount: 200 },
        { bidId: 4, bidderName: 'UserD', amount: 220 },
      ],
      showForm: false,
      newBid: {
        bidderName: '',
        amount: null
      }
    };
  },
  created() {
    console.log(this.$store.state.user.username);
    console.log(this.$store.state);
    bidService.getBidsOfItem(this.$route.params.currentItemID).then((response) => { 
      this.bids = response.data;
      console.log(this.bids);
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
