<template>
  <div class="search-container">
      <router-link :to="{ name: 'PrivateAuctionView' }" class="router-link" exact>
      <div class="rectangle">
        <p>Looking for a private auction? </p>
        <button @click="toggleForm">Click Here</button>
      </div>
    </router-link>
    <form v-if="showForm" class="dropdown-form">
      <input v-model="customAuctionCode" placeholder="Enter custom auction code" />
      <button @click="submitForm">Submit</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showForm: false,
      customAuctionCode: '',
    };
  },
  methods: {
    toggleForm() {
      this.$router.path('/auctions/search/private')
    },
    submitForm() {
      
      console.log('Custom auction code:', this.customAuctionCode);
    },
  },
};
</script>

<style scoped>
.search-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.rectangle {
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  padding: 20px;
  text-align: center;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.rectangle p {
  margin: 0;
  font-size: 16px;
}

.rectangle button {
  margin-top: 10px;
}

.dropdown-form {
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  margin-top: 10px;
  padding: 20px;
  text-align: center;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.dropdown-form input {
  padding: 5px;
  margin-bottom: 10px;
}

.dropdown-form button {
  padding: 5px 10px;
}

.router-link {
  text-decoration: none;
}
</style>
