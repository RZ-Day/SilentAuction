<template>
  <div class="page-container">
    <h1 class="centered-header">Auctions</h1>
    <AuctionList :auctions="auctions" />
  </div>
</template>

<script>
import AuctionList from "@/components/AuctionList"; // Import the AuctionList component
import auctionService from "@/services/AuctionsListService.js";

export default {
  components: {
    AuctionList
  },
  data() {
    return {
      auctions: [] // Replace with your actual array of auction objects
    };
  },
  created() {
      auctionService.getAuctions().then(response => {
          if (response.status === 200) {
              this.auctions = response.data;
          } else {
              console.log("your a bitch");
          }
      })
  }
};
</script>

<style>
.page-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: rgb(210, 217, 219);
}

.centered-header {
  text-align: center;
  font-size: 2rem;
  margin-bottom: 20px;
  color: #4d352c; /* Set a color for the header */
}
</style>