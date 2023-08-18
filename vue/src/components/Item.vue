<template>
  <div class="item-page">
    <div class="content-wrapper">
      <div class="image-viewer">
        <div class="button-headers">
          <button
            type="button"
            class="custom-button"
            @click="startConversation"
          >
            Message Vendor
          </button>
          <button type="button" class="custom-button" @click="addToWatchList">
            Add to my Watchlist
          </button>
        </div>
        <div v-if="currentItemImage">
          <img
            :src="currentItemImage"
            alt="Main Item Image"
            class="main-image"
          />
        </div>
        <div
          class="thumbnails"
          v-if="currentItemImages && currentItemImages.length"
        >
          <img
            v-for="(imageSrc, index) in currentItemImages"
            :key="index"
            :src="imageSrc"
            @click="updateMainImage(imageSrc)"
            alt="Thumbnail"
            class="thumbnail"
          />
        </div>
        <div class="bid-section">
          <h2>{{ item.itemName }}</h2>
          <p>Description: {{ item.description }}</p>
        </div>
      </div>

      <div class="item-details">
        <h3>Current Bid: ${{ currentBidAmount }}.00</h3>

        <div class="bid-input">
          <button @click="placeBid(1)" class="bid-button bid-button-increment">
            Bid +$1
          </button>
          <button @click="placeBid(5)" class="bid-button bid-button-increment">
            Bid +$5
          </button>
          <button @click="placeBid(10)" class="bid-button bid-button-increment">
            Bid +$10
          </button>
          <button @click="placeBid(20)" class="bid-button bid-button-increment">
            Bid +$20
          </button>
        </div>
      </div>
    </div>
  </div>
</template>



<script>
import watchListService from "@/services/WatchlistService.js";
import messageService from "@/services/MessageService.js";

export default {
  name: "ItemPage",
  mounted() {
    this.currentBidAmount = this.currentItem.currentPrice;
  },

  computed: {
    loadedImages() {
      return this.$store.state.loadedImages;
    },
    currentItemIndex() {
      return this.$store.state.currentItemIndex;
    },
    currentItemImage() {
      return this.activeMainImage || this.initialItemImage;
    },
    initialItemImage() {
      const auctionId = this.$store.state.activeAuctionID;
      const itemId = this.$store.state.activeItemID;

      if (
        this.loadedImages[auctionId] &&
        this.loadedImages[auctionId][itemId]
      ) {
        return this.loadedImages[auctionId][itemId][0]; // First image URL for initialization
      }

      return null;
    },

    currentItemImages() {
      const auctionId = this.$store.state.activeAuctionID;
      const itemId = this.$store.state.activeItemID;

      if (
        this.loadedImages[auctionId] &&
        this.loadedImages[auctionId][itemId]
      ) {
        return this.loadedImages[auctionId][itemId];
      }

      return [];
    },
    currentItem() {
      const auction = this.$store.state.auctions.find(
        (a) => a.auctionId === this.$store.state.activeAuctionID
      );
      return auction
        ? auction.items.find((i) => i.itemId === this.$store.state.activeItemID)
        : null;
    },
    numberOfItemImages() {
      const item = this.currentItem;
      return item ? item.numOfImages : 0;
    },
  },
  props: {
    item: Object,
    itemId: Number,
  },
  data() {
    return {
      bidAmount: 0,
      activeMainImage: this.initialItemImage,
      currentBidAmount: 0, // initialize with starting bid or current bid
    };
  },

  methods: {
    addToWatchList() {
      console.log(this.itemId);
      console.log(this.$store.state.user.id);
      watchListService.add(this.$store.state.user.id, this.itemId).then(() => {
        alert("item added");
      });
    },
    updateMainImage(src) {
      this.activeMainImage = src;
      this.$forceUpdate();
    },

    placeBid(increment = 0) {
      const newBidAmount = this.currentItem.currentPrice + increment;
      if (newBidAmount > this.currentItem.currentPrice) {
        this.currentBidAmount = newBidAmount;
        this.currentItem.currentPrice = newBidAmount; // Update the main source
        // alert(`Placed a bid of $${newBidAmount}`);
      } else {
        alert("Your bid should be higher than the current price!");
      }
      this.$forceUpdate();
    },
  },
  startConversation() {
    //CREATE NEW CONVERSATION IN BACK-END
    console.log(this.item);
    console.log(this.item.userId);

    messageService
      .startConversation({
        sellerId: this.item.userId,
        buyerId: this.$store.state.user.id,
        itemId: this.item.itemId,
        itemName: this.item.itemName,
      })
      .then((response) => {
        if (response.status == 200) {
          //SET ACTIVE CONVERSATION ID TO NEWLY CREATED CONVERSATION, OR TO EXISTING CONVERSATION ID
          const newConvoId = response.data.conversationId;
          console.log("New convo id: " + newConvoId);

          this.$store.commit("SET_ACTIVE_CONVERSATION", newConvoId);
          console.log("Active ID committed");

          //REDIRECT TO ACTIVE CONVERSATION IN INBOX
          this.$router.push({
            name: "Messages",
            params: { currentUserId: this.$store.state.user.id },
          });
        }
      });
  },
};
</script>



<style scoped>
.button-headers {
  margin-bottom: 25px;
  margin-top: -35px;
}
.button-headers .custom-button:not(:last-child) {
  margin-right: 75px; /* Adjust this value based on your needs */
}
.item-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  padding: 2em;
  background-color: #f5f7fa;
  width: 100%;
}

.content-wrapper {
  display: flex;
  flex-direction: row;
  width: 100%;
  max-width: 1200px;
  justify-content: center;
  align-items: flex-start;
  margin: 2em 0;
}

.image-viewer {
  flex: 1.5;
  text-align: center;
  padding-right: 2em;
}

.item-details {
  flex: 1;
  text-align: left;
  padding: 20px;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
}

.main-image {
  width: 600px;
  height: auto;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 1em;
}

.thumbnails {
  display: flex;
  justify-content: center;
  gap: 1em;
}

.thumbnail {
  width: 60px;
  height: 60px;
  border: 2px solid transparent;
  border-radius: 5px;
  cursor: pointer;
  transition: border 0.3s;
}

.thumbnail:hover {
  border: 2px solid #000;
}

.custom-button {
  padding: 12px 25px;
  background-color: #333;
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

.custom-button:hover {
  background-color: #555;
}

.bid-section {
  width: 100%;
  padding: 1em;
  background-color: #fff;
  margin-top: 2em;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.bid-input {
  display: flex;
  gap: 1em;
  align-items: center;
  margin-top: 1em;
}

.bid-button {
  padding: 10px 20px;
  background-color: #333;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}
.bid-button-increment {
  margin-right: 10px;
  background-color: #444;
}

.bid-button-increment:hover {
  background-color: #666;
}

.bid-button:hover {
  background-color: #555;
}
</style>
