<template>
  <div class="centered-container">
    <!-- New Auctions Section -->
    <div class="section">
      <h2>New Auctions!</h2>
      <div class="auction-grid">
        <div
          v-for="auction in mostRecentAuctions"
          :key="auction.auctionId"
          class="auction-container"
        >
          <div class="auction-details">
            <h2>{{ auction.auctionName }}</h2>
          </div>

          <div class="auction-items">
            <h3>Items:</h3>
            <ul>
              <li v-for="item in auction.items" :key="item.itemId">
                <p id="item-name">{{ item.itemName }}</p>
                <p id="item-price">- Opening Price: ${{ item.currentPrice }}</p>
                <p id="item-price">
                  - Current Bid: ${{ item.currentPrice + 100 }}
                </p>
                <p>Pretend there's an image here.</p>
              </li>
            </ul>
          </div>
          <div class="auction-details-time">
            <p>Auction Ends: {{ formatDateTime(auction.endTime) }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- Ongoing Auctions Section -->
    <div class="section">
      <h2>Ongoing Auctions</h2>
      <div class="auction-grid">
        <div
          v-for="auction in ongoingAuctions"
          :key="auction.auctionId"
          class="auction-container"
        >
          <div class="auction-details">
            <h2>{{ auction.auctionName }}</h2>
          </div>

          <div class="auction-items">
            <h3>Items:</h3>
            <ul>
              <li v-for="item in auction.items" :key="item.itemId">
                <p id="item-name">{{ item.itemName }}</p>
                <p id="item-price">- Current Price: ${{ item.currentPrice }}</p>
                <p>Pretend there's an image here.</p>
              </li>
            </ul>
          </div>
          <div class="auction-details-time">
            <p>Auction Begins: {{ formatDateTime(auction.startTime) }}</p>
            <p>Auction Ends: {{ formatDateTime(auction.endTime) }}</p>
          </div>
        </div>
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
    auctions: Array,
  },
  computed: {
    mostRecentAuctions() {
      return this.auctions.slice(0, 4); // Get the four most recent auctions
    },
    ongoingAuctions() {
      return this.auctions.slice(4); // Get the ongoing auctions
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
  },
};
</script>

<style scoped>
.centered-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
}

.auction-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-gap: 20px;
}

.auction-container {
  border: 1px solid rgb(158, 137, 137);
  padding: 20px;
  box-sizing: border-box;
  background-color: #f8f8f8; /* background color */
  color: #333; /*text color */
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
}

.auction-container:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
}

.auction-details h2 {
  margin: 0;
  color: #192a3d;
  font-size: 1.2rem;
}

.auction-details-time {
  color: #ff0000;
}

.auction-items {
  margin-top: 10px;
}

.auction-items ul {
  list-style: none;
  padding: 0;
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
  /* bottom row  */
}

/* Section styling */
.section {
  margin-top: 30px;
  width: 100%;
}

.section h2 {
  font-size: 1.5rem;
  margin-bottom: 10px;
  color: #1c3463; /* section titles */
}
</style>
