<template>
  <div class="users-watch-list">
    <h2>Here is a list of item(s) from your watch list.</h2>
    <ul class="watch-list">
      <li v-for="item in watchlist" :key="item.itemId">
        <div class="watch-list-item">
          <div class="item-details">
            <h3>{{ item.itemName }}</h3>
            <p class="watch-list-description">{{ item.description }}</p>
          </div>
          <div class="item-image">
            <img
              src="@/Assets/itemTemp.png"
              alt="Auction Icon"
              class="item-icon"
            />
          </div>
        </div>
      </li>
    </ul>
    <!-- <p v-if="watchlist.length === 0">Your current watch list is empty</p>
    <button class="btn deleteUsersWatchList" v-on:click="deleteUsersWatchList">
      Delete
    </button> -->
  </div>
</template>

<script>
export default {
  name: "UsersWatchList",
  props: {
    watchlist: Array,
  },
  methods: {
    saveToWatchlist() {},
    deleteFromWatchlist() {},
  },
};
</script>

<style>

.users-watch-list {
  font-family: Ariel, sans-serif;
  border: 1px solid rgb(155, 155, 155); /* Add a black border around the component */
  padding: 20px; /* Add some padding to create space around the component */
}

.users-watch-list h2 {
  text-align: center;
  font-size: 35px;
  
}

.users-watch-list p {
  text-align: center;
  font-size: 25px;
  
}

.deleteUsersWatchList {
  position: absolute;
  right: 250px;
  border: none;
  border-radius: 15px;
  padding: 15px;
  text-align: center;
  font-size: 14px;
  color: #272343;
}

.watch-list-item {
  background-color: rgb(250, 250, 250);
  border-radius: 5px;
  display: flex;
  align-items: center; /* Center vertically */
  margin: 10px;
  padding: 10px;
  position: relative; /* Add relative positioning */
   border: 1px solid rgba(0, 0, 0, 0.178);
   box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.item-details {
  flex: 1; /* Take up remaining space */
  padding-right: 10px; /* Add some spacing between details and image */
}

.item-image {
  position: absolute;
  top: 50%; /* Position the image vertically in the middle */
  transform: translateY(-50%); /* Adjust for vertical alignment */
  right: 50px; /* 20px in from the right edge */
}

.item-icon {
  max-width: 50px; /* Adjust the size of the image */
}

@media screen and (max-width: 768px) {
  /* Adjust styles for screens up to 768px wide */
  .users-watch-list h2 {
    font-size: 30px;
  }
  
  /* Add more adjustments as needed */
}

@media screen and (max-width: 480px) {
  /* Adjust styles for screens up to 480px wide */
  .users-watch-list h2 {
    font-size: 25px;
  }
}
</style>
