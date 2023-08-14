<template>
  <div class="bids-section">
    <h2>List of Bids</h2>
    <ul class="bid-list">
      <li v-for="bid in sortedBids" :key="bid.bidId">
        {{ bid.bidderName }}: ${{ bid.amount }}
      </li>
    </ul>
    
    <button @click="showBidForm">Place Bid</button>
    
    <form v-if="showForm" @submit.prevent="submitBid">
      <label for="bidAmount">Bid Amount:</label>
      <input type="number" v-model="newBid.amount" required>
      
      <button type="submit">Submit Bid</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      bids: [
        { bidId: 1, bidderName: 'UserA', amount: 150 },
        { bidId: 2, bidderName: 'UserB', amount: 180 },
        { bidId: 3, bidderName: 'UserC', amount: 200 },
        { bidId: 4, bidderName: 'UserD', amount: 220 },
      ],
      currentHighestBid: 0,
      showForm: false,
      newBid: {
        bidderName: '',
        amount: null
      }
    };
  },
  created() {
    bidService.getBidsOfItem(this.$route.params.currentItemID).then((response) => { 
      this.bids = response.data;
      // console.log(this.bids);

      // Update highest bid
      this.currentHighestBid = this.bids[0].bidAmount;
    });
  },
  computed: {
    sortedBids() {
      return this.bids.slice().sort((a, b) => b.amount - a.amount);
    },
    highestBid() {
      return this.bids[0].bidAmount;
    }
  },
  methods: {
    showBidForm() {
      this.showForm = true;
    },
    submitBid() {
      if (this.newBid.amount > this.highestBid) {
        // this.$store.state.user.username
        // this.$route.params.currentItemID

        let dto = { username: this.$store.state.user.username, itemId: this.$route.params.currentItemID, bidAmount: this.newBid.amount };
        
        bidService.addBid(dto).then(response => {
          console.log(response);
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
