<template>
  <div class="background-image">
    <div class="container">
      <div class="form-container">
        <h1>Enter the Private Auction Password</h1>
        <p>Please contact the auction owner if you do not have the password</p>
        <form @submit.prevent="submitForm" class="auction-form">
          <input
            v-model="auctionPassword"
            type="text"
            placeholder="Enter Auction Password"
            class="input-field"
          />
          <button type="submit" class="custom-button">Go to Auction</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import auctionService from "@/services/AuctionsListService.js";
export default {
  data() {
    return {
      auctionPassword: "",
      searchAuctionId: null,
    };
  },
  methods: {
    submitForm() {
      auctionService
        .getPrivateAuction(this.auctionPassword)
        .then((response) => {
          const auction = response.data;
          if (auction.auctionId > 0) {
            this.$router.push(`/auctions/${auction.auctionId}`);
          } else {
            alert("The password may be incorrect. Please try again. ");
          }
        })
        .catch((error) => {
          alert("The password may be incorrect. Please try again. If your issue persists, please contact the auction owner. ");
          console.error("Error getting the auction", error);
        });
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50%;
}

.form-container {
  background-color: rgba(255, 255, 255, 0.911);
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.auction-form {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.input-field {
  text-align: center; /* Center the text in the input box */
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-bottom: 10px;
}

.custom-button {
  margin-top: 10px;
  background-color: black;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.background-image {
  background-image: linear-gradient(rgb(0, 0, 0), rgba(253, 251, 251, 0.24) 5px),
    url("@/Assets/privacy.jpeg");
  background-size: cover;
  background-position: center center;
  background-repeat: no-repeat;
  position: fixed;
  width: 100%;
  height: 100%;
}
</style>
