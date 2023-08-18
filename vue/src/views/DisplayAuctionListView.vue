<template>
  <div class="page-container">
    <div class="header-container">
      <h1 class="centered-header"></h1>
    </div>

    <div class="auction-list-container">
      <AuctionList :auctions="publicAuctions" :loadedImages="loadedImages" />
    </div>

    <div class="find-private-auction-container"></div>
  </div>
</template>

<script>
import AuctionList from "@/components/DisplayAuctionList";
import auctionService from "@/services/AuctionsListService.js";

export default {
  components: {
    AuctionList,
  },
  data() {
    return {
      auctions: [],
      loadedImages: {},
    };
  },
  computed: {
    publicAuctions() {
      return this.auctions.filter((auction) => !auction.isPrivate);
    },
  },
  created() {
    this.fetchAuctionsAndLoadImages();
  },
  methods: {
    async fetchAuctionsAndLoadImages() {
      try {
        const response = await auctionService.getAuctions();
        if (response.status === 200) {
          this.auctions = response.data;
          this.$store.state.auctions = this.auctions;
         

          // Load images after fetching auctions
          await this.loadImages();
        } else {
          console.log("If you're seeing this message then your code SUCKS");
        }
      } catch (error) {
        console.error("Error fetching auctions:", error);
      }
    },
    async loadImages() {
      this.loadedImages = {};

      for (const auction of this.auctions) {
        if (!this.loadedImages[auction.auctionId]) {
          this.loadedImages[auction.auctionId] = {}; // Initialize if it doesn't exist
        }
        for (const item of auction.items) {
          const itemNameWithoutSpaces = item.itemName.replace(/\s+/g, "");
          const numOfImages = item.numOfImages;
          const itemImages = [];

          for (let imageIndex = 0; imageIndex < numOfImages; imageIndex++) {
            try {
              const imageUrl = await this.fetchImageFromProxy(
                itemNameWithoutSpaces,
                imageIndex
              );
              itemImages.push(imageUrl);
            } catch (error) {
              console.error("Error loading images:", error);
            }
          }

          this.loadedImages[auction.auctionId][item.itemId] = itemImages;
        }
      }
      this.$store.commit("SET_LOADED_IMAGES", this.loadedImages);
    },

    async fetchImageFromProxy(itemNameWithoutSpaces, imageIndex) {
      const filename = `${itemNameWithoutSpaces}${imageIndex}.jpeg`;
      const proxyBaseUrl = "http://localhost:8081/api/proxy/get"; // Adjust the port if needed
      const fetchUrl = `${proxyBaseUrl}/${encodeURIComponent(filename)}`;

      try {
        const response = await fetch(fetchUrl);
        if (response.ok) {
         

          const blob = await response.blob();

          return URL.createObjectURL(blob);
        } else {
          console.error("Error fetching image:", response.status);
          throw new Error("Image fetch failed");
        }
      } catch (error) {
        console.error("Error fetching image:", error);
        throw error;
      }
    },
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
  background-image: url("@/Assets/back4.jpg");
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
