<template>
  <div class="page-container">
    <div class="header-container">

      <h1 class="centered-header">
        
      </h1>
      
    </div>

    <div class="auction-list-container">
      <AuctionList :auctions="publicAuctions" />
    </div>
    
    <div class="find-private-auction-container">
      
    </div>

  </div>
</template>

<script>
import AuctionList from "@/components/DisplayAuctionList"; 
import auctionService from "@/services/AuctionsListService.js";


export default {
  components: {
    AuctionList
    
  },
  data() {
    return {
      auctions: [], 
    };
  },
  computed: {
    publicAuctions() {
      return this.auctions.filter(auction => !auction.isPrivate);
    },
  },
  created() {
    auctionService.getAuctions().then((response) => {
      if (response.status === 200) {
        this.auctions = response.data;
        console.log(this.auctions); // Debug statement
      } else {
        console.log("If you're seeing this message then your code SUCKS");
      }
    });
  },
};
</script>

<style scoped>
.page-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
 
}

.header-container {

  justify-content: space-between;
  align-items: center;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
  margin-top: 10px;
}

.centered-header {
  text-align: center;
  font-size: 2rem;
  
  
  color: #000000; 
  

}

.header-icon {
  width: 60px;
  height: auto;
  margin-left: 25px;
}


.auction-list-container {
  flex: 1; 
  width: 100%;
  max-width: 1200px; 
}

.find-private-auction-container {
  position: absolute;
  top: -250px; 
  right: 100px; 
}

.background-image {
  background-image: url('@/Assets/back4.jpg'); 
  background-size: cover;
  background-position: bottom center;
  background-repeat: no-repeat;
  position: fixed;
  width: 100%;
  height: 100%; 
  bottom: 0; /* Anchors the image to the bottom of the screen */
  z-index: -1; /* Place the image behind other content */
}

</style>
