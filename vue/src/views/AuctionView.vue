<template>
  <div class="auction-page">
    
    <div class="auction-details">
      <!-- ADDED =========================================================== -->
      <div class="auction-controls">
        <h1 class="auction-details-header">{{ auction.auctionName }}</h1>
        <button v-if="showDelete" @click="deleteAuction" class="delete-button" title="Delete Auction">Delete</button>  
      </div>
      <!-- ================================================================= -->
      <div id="item-list">
      <Auction :auction="auction" />
      </div>
    </div>

    <!-- <div class="bids-section">
      <DisplayAuctionBids />
    </div> -->
  </div>
</template>

<script>
import Auction from "../components/Auction.vue";
import auctionService from "@/services/AuctionsListService.js";
//import DisplayAuctionBids from '../components/DisplayAuctionBids.vue';

export default {
  components: { Auction, 
                //DisplayAuctionBids
              },
  data() {
    return {
      auction: null,
      deleteAuctionWarning: false
    };
  },
  created() {
    const activeAuctionID = parseInt(this.$route.params.currentAuctionID);
    this.$store.commit("SET_ACTIVE_AUCTION", activeAuctionID);
    auctionService.getAuctions().then(response => {
      if (response.status === 200) {
        this.auction = response.data.find(auction => auction.auctionId === activeAuctionID);
        console.log(this.isAdmin);
      } else {
        console.log("Error fetching auctions");
      }
    });
  },
  //ADDED ====================================
  //==========================================
  computed: {
    showDelete() {
      if (this.auction.items) {
        if (this.auction.items[0].userId == this.$store.state.user.id || this.isAdmin) {
          return true;
        }
      }
      return false;
    },
    isAdmin() {
      const userAuthorities = this.$store.state.user.authorities;

      if (userAuthorities) {
        const isAdmin = userAuthorities.find((obj) => {
          return obj.name == "ROLE_ADMIN";
        });
        
        return isAdmin;
      }

      return false;
    }
  },
  methods: {
    deleteAuction() {
      const activeAuctionID = parseInt(this.$route.params.currentAuctionID)

      auctionService.deleteAuction(activeAuctionID).then((response) => {
        if (response.status == 200) {
          this.$router.push("/auctions");
        }
      });
    }
  }
  //==========================================
  //==========================================
};
</script>

<style>
.auction-page {
  display: flex;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
  border-radius: 5px;
  font-family: Ariel, sans-serif;
  
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

.auction-controls {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.delete-button {
  height: 30px;
  width: 80px;
  border-radius: 20px;
  background-color: red;
  border-style: none;
  color: white;
}

.delete-button:hover {
  background-color: rgb(197, 23, 23);
}


</style>