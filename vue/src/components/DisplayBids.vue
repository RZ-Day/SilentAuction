<template>
  <div class="bids-section">
    <h2>List of Bids</h2>
    <!-- Show this if there are bids on this item and the user has made at least 1 bid on it. -->
    <p v-show="this.bids.length > 0 && this.totalBidsOfUser > 0">You currently have made {{ (this.totalBidsOfUser + " bid") + (this.totalBidsOfUser == 1 ? "" : "s") }} on this item.</p>
    <!-- Show this if there are bids on this item but the user hasn't made a bid on it. -->
    <p v-show="this.bids.length > 0 && this.totalBidsOfUser == 0">You don't have any bids on this item yet.</p>
    <div id="bid-display">
      <div class="bid-list">
        <h3>All Latest Bids</h3>
        <ul id="bid-list-all" v-show="this.bids.length > 0">
          <li class="bid-list-item" v-for="bid in sortedBids" :key="bid.bidId">
            {{ bid.username }} bid ${{ bid.bidAmount }} at {{ bid.bidTime }}
          </li>
        </ul>
      </div>
      <div class="bid-list">
        <h3>Your Latest Bids</h3>
        <ul id="bid-list-self" v-show="this.bids.length > 0">
          <li v-for="bid in sortedBidsOfUser" :key="bid.bidId">
            ${{ bid.bidAmount }} at {{ bid.bidTime }}
          </li>
        </ul>
      </div>
    </div>
    <p v-show="this.bids.length == 0">No bids have been made on this item yet. Maybe you can be the first?</p>
    
    <button @click="showBidForm">Place Bid</button>
    
    <form v-if="showForm" @submit.prevent="submitBid">
      
      <br>
      <button @click="bidButton" id="increaseBid1" type="submit">Bid $1 More</button>&nbsp;
      <button @click="bidButton" id="increaseBid5" type="submit">Bid $5 More</button>&nbsp;
      <button @click="bidButton" id="increaseBid10" type="submit">Bid +$10 More</button>&nbsp;
      <button @click="bidButton" id="increaseBid20" type="submit">Bid +$20 More</button>
      <br>
      <br>
      <label for="bidAmount">Custom Bid Amount:</label>
      <input type="number" v-model="newBid.amount" required>
      
      <button id="increaseBidCustom" type="submit">Submit Bid</button>
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
        /* bidAmount, bidId, bidItem, bidTime, userId, username */
      ],
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
    });
  },
  computed: {
    sortedBids() {
      return this.bids.slice().sort((a, b) => b.amount - a.amount);
    },
    sortedBidsOfUser() {
      return this.sortedBids.filter(obj => obj.username == this.$store.state.user.username);
    },
    highestBid() {
      return this.bids[0].bidAmount; // is this redundant?
    },
    totalBidsOfUser() {
      return this.bids.filter(obj => obj.username == this.$store.state.user.username).length;
    },
  },
  methods: {
    showBidForm() {
      this.showForm = true;
    },
    bidButton(event) {
      console.log("bidButton() called");
      console.log(event);

      // When a static-amount bid button (not custom one) is clicked
      // it will populate this.newBid.amount and then proceed to the submitBid() event


      // event.target.id
      let bidAddition = parseInt(event.target.innerText.replace(/[^0-9]/g, '')); // Use regex instead of .replace('+$', '') for more robustness :D

      // Get current top bid and add bidAddition to it
      let totalBidAmount = this.highestBid + bidAddition;

      this.newBid.amount = totalBidAmount;
    },
    submitBid(event) {
      // console.log(event);

      // First check the id of which button fired this event
      let submitter = event.submitter.id;
      
      // did they press the custom bid and is it empty / null / zero?
      if (submitter == 'increaseBidCustom' && !this.newBid.amount) {
        // alert("You must input a value"); // ?
        console.log("No :<");
        return;
      }

      if (this.newBid.amount > this.highestBid) {
        // this.$store.state.user.username
        // this.$route.params.currentItemID

        // TODO: Ask user to confirm bid if more than double the amount


        let dto = { username: this.$store.state.user.username, itemId: this.$route.params.currentItemID, bidAmount: this.newBid.amount };

        bidService.addBid(dto).then(response => {
          console.log(response);
          // Update local bids array
          this.bids.unshift(response.data);
        });

        this.newBid.bidderName = '';
        this.newBid.amount = null;
        this.showForm = false;
      } else {
          alert('Your bid must be greater than the current highest bid!');
      }
    }
  }
}
</script>

<style scoped>

#bid-display {
  display: flex;
  justify-content: space-between;
}

.bid-list {
  flex: 1;
  padding: 20px;
}
</style>
