<template>
  <div class="page-container">
    <div class="form-container">
      <h2>Create a New Auction</h2>
      <form v-on:submit.prevent="createAuction">
        <div class="form-group">
          <label for="auctionName">Auction Name:</label>
          <input v-model="newAuction.auctionName" required />
        </div>
        <div class="form-group">
          <label for="startTime">Start Time:</label>
          <input
            type="datetime-local"
            v-model="newAuction.startTime"
            required
          />
        </div>
        <div class="form-group">
          <label for="endTime">End Time:</label>
          <input type="datetime-local" v-model="newAuction.endTime" required />
        </div>

        <h3>Add Items</h3>
        <div
          v-for="(item, index) in newAuction.items"
          :key="index"
          class="item-form"
        >
          <div class="form-group">
            <label for="itemName">Item Name:</label>
            <input v-model="item.itemName" required />
          </div>
          <div class="form-group">
            <label for="description">Description:</label>
            <textarea v-model="item.description"></textarea>
          </div>
          <div class="form-group">
            <label for="initialPrice">Initial Price:</label>
            <input type="number" v-model="item.initialPrice" required />
          </div>
          <div class="form-group">
            <label for="currentPrice">Current Price:</label>
            <input type="number" v-model="item.currentPrice" required />
          </div>
        </div>
        <button type="button" @click="addItem">Add Item</button>

        <input type="submit" value="Create Auction">
      </form>
    </div>
  </div>
</template>

<script>
import auctionService from "@/services/AuctionsListService.js";
export default {
  data() {
    return {
      newAuction: {
        auctionName: "",
        startTime: "",
        endTime: "",
        items: [
          {
            itemName: "",
            description: "",
            initialPrice: 0,
            currentPrice: 0
          },
        ],
      },
    };
  },
  methods: {
    addItem() {
      this.newAuction.items.push({
        itemName: "",
        description: "",
        initialPrice: 0,
        currentPrice: 0,
      });
    },
    createAuction() {
      auctionService.addAuction(this.newAuction)
        .then(() => {
          this.resetForm();
          this.$router.push({ name: "AuctionList" });
        })
        .catch(error => {
          console.error('Error creating auction:', error);
        });
    },
    resetForm() {
      this.newAuction.auctionName = "";
      this.newAuction.startTime = "";
      this.newAuction.endTime = "";
      this.newAuction.items = [
        {
          itemName: "",
          description: "",
          initialPrice: 0,
          currentPrice: 0,
        },
      ];
    },
  },
};
</script>

<style>
.form-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #fff;
}

.form-group {
  margin-bottom: 10px;
}

.item-form {
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 10px;
  margin-top: 10px;
}
</style>
