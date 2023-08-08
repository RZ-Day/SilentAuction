<template>
  <div class="home">
    <h1>Home</h1>
    <p>You must be authenticated to see this</p>
    <router-link to="/auctions" class="auctions-button">Go to Auctions</router-link>
  </div>
</template>

<script>
export default {
  name: "home"
};
</script>

<style scoped>
.auctions-button {
  display: inline-block;
  padding: 10px 20px;
  margin-top: 20px;
  background-color: #1c3463; /* Dark blue color for button background */
  color: #ffffff; /* White text color */
  border: none;
  border-radius: 5px;
  text-decoration: none;
  cursor: pointer;
  transition: background-color 0.3s;
}

.auctions-button:hover {
  background-color: #253b6e; /* Darken the button color on hover */
}
</style>
