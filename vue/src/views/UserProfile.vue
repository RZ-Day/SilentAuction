<template>
  <div class="user-profile">
    <h2>User Profile Settings</h2>

    <!-- Contact Type Dropdown -->
    <div class="form-group">
      <label>Select Contact Type:</label>
      <select v-model="selectedType">
        <option value="email">Email</option>
        <option value="phone">Phone</option>
        <option value="address">Address</option>
      </select>
    </div>
    
    <!-- Contact Information Update Form -->
    <div class="form-group">
      <label>New Contact Information:</label>
      <input v-model="newContactInfo" />
      <button @click="updateContactInfo">Update Contact Info</button>
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
      selectedType: 'email',
      newContactInfo: '',
      allowAnonymous: false,
      isLoading: false,
      errorMessage: '',
      successMessage: '',
      user: {},
      phone: '',
      email: '',
      address: '',
    };
  },
  methods: {
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
          address: this.address,
        };
        await this.$axios.put('/profile/editaddress', data);
        this.successMessage = 'Address updated successfully!';
        this.fetchUserProfile();
      } catch (error) {
        this.errorMessage = 'An error occurred. Please try again later.';
      }
    },
    async updateAllowAnonymous() {
      try {
        await this.$axios.put('/profile/editanonymous', {
          username: this.$store.state.user.username,
          allowAnonymous: this.allowAnonymous,
        });
        this.successMessage = 'Anonymous Setting updated successfully!';
      } catch (error) {
        this.errorMessage = 'An error occurred. Please try again later.';
      }
    },
    async fetchUserProfile() {
      try {
        if (this.$store.state.user.username) {
        const response = await this.$axios.get('/api/profile/update', {
          params: { username: this.$store.state.user.username },
        });
        this.user = response.data;
      }
      } catch (error) {
        console.error('Error fetching user profile:', error);
      }
    },
  },
  mounted() {
    this.fetchUserProfile();
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
.user-info {
  margin-top: 20px;
}
</style>
