<template>
  <div class="user-profile">
    <h2>User Profile Settings</h2>
    
    <!-- Contact Information Update Form -->
    <div class="form-group">
      <label>New Contact Information:</label>
      <input v-model="newContactInfo" />
      <button @click="updateContactInformation">Update Contact Info</button>
    </div>
    
    <!-- Allow Anonymous Setting -->
    <div class="form-group">
      <label>
        <input type="checkbox" v-model="allowAnonymous" />
        Allow Anonymous
      </label>
      <button @click="updateAllowAnonymous">Update Anonymous Setting</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      newContactInfo: '',
      allowAnonymous: false,
    };
  },
  methods: {
    async updateContactInformation() {
      try {
        await this.$axios.put('/api/profile/contact', {
          username: this.$store.state.username, // Get the logged-in user's username from the store
          contactInformation: this.newContactInfo,
        });
        // Handle success: update UI or show a success message
      } catch (error) {
        // Handle error: show an error message
      }
    },
    async updateAllowAnonymous() {
      try {
        await this.$axios.put('/api/profile/anonymous', {
          username: this.$store.state.username, // Get the logged-in user's username from the store
          allowAnonymous: this.allowAnonymous,
        });
        // Handle success: update UI or show a success message
      } catch (error) {
        // Handle error: show an error message
      }
    },
  },
};
</script>

<style scoped>
/* Add component-specific styles here */
.user-profile {
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
.form-group {
  margin-bottom: 10px;
}
</style>
