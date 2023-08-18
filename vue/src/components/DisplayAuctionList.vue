<template>
  <div class="centered-container">
    <!-- New Auctions Section -->

    <div class="section">
      <h2></h2>
      <div class="auction-grid">
        <router-link
          v-for="auction in publicAuctions"
          :key="auction.auctionId"
          :to="{
            name: 'AuctionIndex',
            params: { currentAuctionID: auction.auctionId },
          }"
          class="auction-link"
        >
          <div class="auction-container">
            <div class="auction-details">
              <h2>{{ auction.auctionName }}</h2>
            </div>
            <div class="auction-items">
              <ul>
                <li v-for="item in auction.items" :key="item.itemId">
                  <div
                    class="image-display"
                    v-for="(imageUrl, imageIndex) in loadedImages[
                      auction.auctionId
                    ][item.itemId]"
                    :key="imageIndex"
                  >
                    <img :src="imageUrl" alt="Image of the item" />
                  </div>

                  <p id="item-name">{{ item.itemName }}</p>

                  <p id="item-price">
                    Current Bid: ${{ item.currentPrice + 100 }}
                  </p>
                </li>
              </ul>
            </div>
            <div class="auction-details-time">
              <p>Time Left: {{ calculateRemainingTime(auction.endTime) }}</p>
            </div>
          </div>
        </router-link>
      </div>
    </div>

    <div class="bottom-row">
      <!-- Add content for the bottom row here -->
    </div>
  </div>
</template>

<script>
export default {
  props: {
    auctions: Array
  },
  computed: {
    loadedImages() {
      return this.$store.state.loadedImages;
    },
    publicAuctions() {
      return this.auctions.filter((auction) => auction.isPrivate === false);
    },

    mostRecentAuctions() {
      return this.auctions.slice(0);
    },
    ongoingAuctions() {
      return this.auctions.slice(4);
    },
  },
  methods: {
    formatDateTime(timestamp) {
      const options = {
        year: "numeric",
        month: "long",
        day: "numeric",
        hour: "numeric",
        minute: "numeric",
        second: "numeric",
        timeZoneName: "short",
      };
      return new Date(timestamp).toLocaleString(undefined, options);
    },
    calculateRemainingTime(endTime) {
      const currentDate = new Date();
      const auctionEndTime = new Date(endTime);
      const timeDifference = auctionEndTime - currentDate;

      const millisecondsPerMinute = 60 * 1000;
      const millisecondsPerHour = 60 * millisecondsPerMinute;
      const millisecondsPerDay = 24 * millisecondsPerHour;

      if (timeDifference < millisecondsPerDay) {
        const hours = Math.floor(timeDifference / millisecondsPerHour);
        const minutes = Math.floor(
          (timeDifference % millisecondsPerHour) / millisecondsPerMinute
        );
        return `${hours * -1} hours ${minutes * -1} minutes`;
      } else {
        const remainingDays = Math.ceil(timeDifference / millisecondsPerDay);
        return `${remainingDays} days`;
      }
    },
  },
};
</script>
<style scoped>
.centered-container {
  display: grid;
  place-items: center;
  height: 100vh;
}

.auction-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-gap: 20px;
  justify-content: center; /* Center the grid items horizontally */
}

.auction-link {
  text-decoration: none;
  cursor: pointer;
}

.auction-container {
  border: 1px solid rgb(185, 177, 177);
  padding: 24px;
  box-sizing: border-box;
  background-color: #fffdfdfd;
  color: #333;
  border-radius: 0px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Adjust the values as needed */
  transition: transform 0.3s, box-shadow 0.75s;
  height: 100%;
  max-width: 400px;
  max-height: 400px;
  overflow: hidden;
}

.image-display img {
  max-width: 200px;
  max-height: 200px;
  display: block; /* to remove any unwanted spacing around the image */
  margin: 0 auto; /* to center the image if it's smaller than the container */
}

.auction-container:hover {
  transform: translateY(-8px);
  box-shadow: 0 16px 32px rgba(0, 0, 0, 0.1);
}

.auction-details h2 {
  margin: 0;
  color: #192a3d;
  font-size: 1.2rem;
}

.auction-details-time {
  color: #000000;
}

.auction-items {
  margin-top: 10px;
}

.auction-items ul {
  list-style: none;
  justify-content: center;
}

.auction-items li {
  margin-bottom: 5px;
}

#item-name {
  color: rgb(25, 25, 49);
}

#item-price {
  color: rgb(76, 136, 36);
}

.bottom-row {
  margin-top: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* Section styling */
.section {
  margin-top: 30px;
  width: 100%;
}

.section h2 {
  font-size: 1.5rem;
  margin-bottom: 10px;
  color: #1c3463;
}
</style>