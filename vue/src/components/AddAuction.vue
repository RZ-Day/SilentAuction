<template>
  <div class="page-container">
    <div class="form-container">
      <h2>Create a New Auction</h2>
      <form v-on:submit.prevent="createAuction">
        <div class="form-group">
          <label for="auctionName"></label>
          <input
            v-model="newAuction.auctionName"
            required
            placeholder="Enter Auction Name"
          />
        </div>
        <div class="form-group">
          <label for="startTime">Start Time:</label>
          <input
            type="datetime-local"
            v-model="newAuction.startTime"
            required
            placeholder="Select Start Time"
          />
        </div>
        <div class="form-group">
          <label for="endTime">End Time:</label>
          <input type="datetime-local" v-model="newAuction.endTime" required />
        </div>

        <div class="form-group">
          <label for="privateAuction">Private Auction:</label>
          <input
            type="checkbox"
            v-model="newAuction.privateAuction"
            @change="togglePrivatePassword"
          />
        </div>

        <div v-if="newAuction.privateAuction" class="form-group">
          <label for="privatePassword">Private Auction Password:</label>
          <input type="password" placeHolder="Length 1-32" v-model="newAuction.privatePassword" />
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
            <div
              v-for="(image, imgIndex) in item.images"
              :key="imgIndex"
              class="image-preview"
            >
              <img :src="image.url" alt="Image Preview" />
              <button type="button" @click="removeImage(index, imgIndex)">
                Remove
              </button>
            </div>
            <p>Image Count: {{ totalImages[index] }}</p>
          </div>
        </div>
        <button type="button" @click="addItem">Add Item</button>

        <input type="submit" value="Create Auction" />
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
        privateAuction: false,
        privatePassword: "",
        items: [
          {
            itemName: "",
            description: "",
            initialPrice: 0,
            currentPrice: 0,
            userId: this.$store.state.user.id,
            images: [],
          },
        ],
      },
      editedItemIndex: -1,
    };
  },
  computed: {
    totalImages() {
      return this.newAuction.items.map((item) => item.images.length);
    },
  },
  methods: {
    addItem() {
      this.newAuction.items.push({
        itemName: "",
        description: "",
        initialPrice: 0,
        currentPrice: 0,
        userId: this.$store.state.user.id,
        images: [],
      });
    },
    toggleItem(index) {
      this.newAuction.items[index].open = !this.newAuction.items[index].open;
    },
    editItem(index) {
      this.editedItemIndex = index;
      this.newAuction.items[index].open = true;
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


<style scoped>
.form-container {
  background-color: rgba(255, 255, 255, 0.911);
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  
}

.page-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh; /* Set a minimum height to cover the entire viewport */
  background-image: url("@/Assets/chessFocus.jpg");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  z-index: 1;
}

.form-container p {
  text-align: center;
}

.form-group {
  margin-bottom: 10px;
}

.item-form {
  padding: 10px;
  margin: 10px;
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

.item-button-container {
  display: flex;
  justify-content: center;
  margin-bottom: 10px;
}

.item-header {
  display: flex;
  justify-content: space-between;
  cursor: pointer;
  padding: 5px;
  background-color: #eee;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-bottom: 5px;
}

.item-details {
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
  background-color: #f9f9f9;
  margin-top: 5px;
}

.bottom-buttons {
  display: flex;
  justify-content: space-evenly;
}

.save-me {
  color: #ff1100;
}
</style>
