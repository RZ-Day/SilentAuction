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
          <div class="form-group">
            <label for="images">Images:</label>
            <input type="file" multiple @change="handleImageUpload(index)" />
            <div v-for="(image, imgIndex) in item.images" :key="imgIndex" class="image-preview">
              <img :src="image.url" alt="Image Preview" />
              <button type="button" @click="removeImage(index, imgIndex)">Remove</button>
            </div>
            <p>Image Count: {{ totalImages[index] }}</p>
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
            currentPrice: 0,
            images: [],
          },
        ],
      },
    };
  },
  computed: {
    totalImages() {
      return this.newAuction.items.map(item => item.images.length);
    }
  },
  methods: {
    addItem() {
      this.newAuction.items.push({
        itemName: "",
        description: "",
        initialPrice: 0,
        currentPrice: 0,
        images: [],
      });
    },
    createAuction() {
      auctionService
        .addAuction(this.newAuction)
        .then(() => {
          this.resetForm();
          this.$router.push({ name: "AuctionList" });
        })
        .catch((error) => {
          console.error("Error creating auction:", error);
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
          images: [],
        },
      ];
    },
    handleImageUpload(itemIndex) {
      const input = event.target;
      if (input.files && input.files.length > 0) {
        for (let i = 0; i < input.files.length; i++) {
          const file = input.files[i];
          const imageUrl = URL.createObjectURL(file);
          this.newAuction.items[itemIndex].images.push({ file, url: imageUrl });
        }
      }
    },
    removeImage(itemIndex, imgIndex) {
      this.newAuction.items[itemIndex].images.splice(imgIndex, 1);
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

.image-preview {
  display: flex;
  align-items: center;
  margin-top: 5px;
}

.image-preview img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  margin-right: 10px;
}

.image-preview button {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}
</style>

