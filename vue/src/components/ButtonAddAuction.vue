<template>
  <div class="add-auction-button">
    <router-link to="/add-auction" class="add-button">Add Auction</router-link>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.add-auction-button {
  display: flex; /* flexbox to align the button */
  align-items: center;
}

.add-button {
  padding: 16px 24px; /* button size */
  margin-top: 2px;
  font-size: 24px; /* font size for button text */
  background-color: #007bff; /* button background color */
  color: white; /* button text color */
  border: none;
  border-radius: 25px;
  cursor: pointer;
  text-decoration: none;
  transition: background-color 0.3s ease;
}

.add-button:hover {
  background-color: #0056b3; /* button background color on hover */
}
</style>