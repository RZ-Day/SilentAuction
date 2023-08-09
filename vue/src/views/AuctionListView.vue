<template>
  <div class="page-container">
    <div class="header-container">
      <h1 class="centered-header">
        Silent Auctions
        <img src="@/Assets/shushLogo.jpg" alt="Auction Icon" class="header-icon" />
      </h1>
      <div class="add-auction-button">
        <router-link to="/add-auction" class="add-button">Add Auction</router-link>
      </div>
    </div>
    <div class="auction-list-container">
      <AuctionList :auctions="auctions" />
    </div>
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
      auctions: [], // Replace with your actual array of auction objects
    };
  },
  created() {
    auctionService.getAuctions().then((response) => {
      if (response.status === 200) {
        this.auctions = response.data;
      } else {
        console.log("If you're seeing this message then your code SUCKS");
      }
    });
  },
};
</script>

<style>
.page-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: rgb(255, 255, 255);
}

.header-container {
  display: flex;
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
  margin-top: 80px;
  margin-bottom: 20px;
  color: #4d352c; /* Set a color for the header */
  display: flex;
  align-items: center;
  font-size: 4rem;
  margin: 0;
  color: #4d352c; /* color for the header */
}

.header-icon {
  width: 60px;
  height: auto;
  margin-left: 25px;
}

.add-auction-button {
  display: flex; /* flexbox to align the button */
  align-items: center;
}

.add-button {
  padding: 16px 24px; /* button size */
  margin-top: 2px;
  font-size: 24px; /* font size for button text */
  background-color: #007bff; /* button background color */
  color: white; /* button text color */
  border: none;
  border-radius: 25px;
  cursor: pointer;
  text-decoration: none;
  transition: background-color 0.3s ease;
}

.add-button:hover {
  background-color: #0056b3; /* button background color on hover */
}

.auction-list-container {
  flex: 1; /* Expand and take remaining space */
  width: 100%;
  max-width: 1200px; /* Set a maximum width if needed */
}
</style>
