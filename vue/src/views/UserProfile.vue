<template>
  <div class="user-profile">
    <h2>User Profile Settings</h2>

    <!-- Contact Type Dropdown -->
    <div class="form-group">
      <label>Select Contact Type:</label>
      <select v-model="selectedType">
        <option value="email">Email</option>
        <option value="phone">Phone</option>
      </select>
    </div>
    
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

    <!-- Display User Profile Data -->
    <div class="user-info">
      <h2>{{ user.username }}'s Profile</h2>
      <p>Email: {{ user.email }}</p>
      <p>Address: {{ user.address }}</p>
      <p>Phone Number: {{ user.phoneNumber }}</p>
      <h2>{{ this.$store.state.user.username }}'s Profile</h2>
      <!-- Add more fields as needed -->
    </div>
  </div>
</template>

<script>
import userProfileService from "@/services/UserProfileService.js";
export default {
  data() {
    return {
      selectedType: 'email', // Default selected type
      newContactInfo: '',
      allowAnonymous: false,
      isLoading: false,
      errorMessage: '',
      successMessage: '',
      user: {}, // Initialize an empty user object
    };
  },
  methods: {
    async updateContactInformation() {
            try {
        this.isLoading = true;
        this.errorMessage = '';
        this.successMessage = '';

    async updateContactInfo() {
      await this.updateDetail(this.selectedType);
    },
    async updateDetail(infoType) {
      let data = { username: this.$store.state.user.username };
      switch (infoType) {
        case 'phone':
          data.phone = this.newContactInfo;
          break;
        case 'email':
          data.email = this.newContactInfo;
          break;
        case 'address':
          data.address = this.newContactInfo;
          break;
        default:
          break;
      }
      userProfileService.updateProfile(data).then(response => console.log(response));
    },
    async updateUserPhone() {
      try {
        const data = {
          username: this.$store.state.user.username,
          phone: this.phone,
        };
        await this.$axios.put('/profile/editphone', data);
        this.successMessage = 'Phone Number updated successfully!';
        this.fetchUserProfile();
      } catch (error) {
        this.errorMessage = 'An error occurred. Please try again later.';
      }
    },
    async updateUserEmail() {
      try {
        const data = {
          username: this.$store.state.user.username,
          email: this.email,
        };
        await this.$axios.put('/profile/editemail', data);
        this.successMessage = 'Email Address updated successfully!';
        this.fetchUserProfile();
      } catch (error) {
        this.errorMessage = 'An error occurred. Please try again later.';
      }
    },
    async updateUserAddress() {
      try {
        const data = {
          username: this.$store.state.user.username,
          contactType: this.selectedType,
          contactInformation: this.newContactInfo,
        };

        await this.$axios.put('/api/profile/contact', data);

        this.successMessage = `Contact ${this.selectedType} updated successfully!`;
        this.newContactInfo = ''; // Clear the input field
      } catch (error) {
        this.errorMessage = 'An error occurred. Please try again later.';
      } finally {
        this.isLoading = false;
      }
    },
    async updateAllowAnonymous() {
      try {
        await this.$axios.put('/api/profile/anonymous', {
          username: this.$store.state.user.username,
          allowAnonymous: this.allowAnonymous,
        });
        // Handle success: update UI or show a success message
      } catch (error) {
        // Handle error: show an error message
      }
    },
    async fetchUserProfile() {
      try {
        const response = await this.$axios.get('/api/profile/user', {
          params: { username: this.$store.state.user.username }, // Modify as needed
        if (this.$store.state.user.username) {
        const response = await this.$axios.get('/api/profile/update', {
          params: { username: this.$store.state.user.username },
        });
        this.user = response.data; // Set the fetched user profile data
      } catch (error) {
        console.error('Error fetching user profile:', error);
      }
    },
  mounted() {
    this.fetchUserProfile(); // Fetch user profile data when the component is mounted
  },
},
}
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
.user-info {
  margin-top: 20px;
}
</style>

