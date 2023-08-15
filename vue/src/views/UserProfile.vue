<template>
  <div class="user-profile">
    <h2>User Profile Settings</h2>

    <!-- Display User Profile Data -->
    <div class="user-info black-box">
      <h2>{{ user.username }}'s Profile</h2>
      <p>Email:
        <span v-if="!editEmailMode">{{ user.email }}</span>
        <input v-else v-model="editedEmail" />
        <button @click="toggleEdit('email')">Edit</button>
        <button v-if="editEmailMode" @click="saveEditedEmail">Save</button>
      </p>
      <p>Phone:
        <span v-if="!editPhoneMode">{{ user.phone }}</span>
        <input v-else v-model="editedPhone" />
        <button @click="toggleEdit('phone')">Edit</button>
        <button v-if="editPhoneMode" @click="saveEditedPhone">Save</button>
      </p>
      <!-- Allow Anonymous Setting -->
      <div class="form-group">
        <label>
          <input type="checkbox" v-model="allowAnonymous" />
          Allow Anonymous Messaging
        </label>
        <button @click="updateAllowAnonymous">Update Anonymous Messaging Setting</button>
      </div>
    </div>

    <!-- Display Billing and Shipping Addresses -->
    <div class="address-info black-box">
      <h2>Billing & Shipping Addresses</h2>
      <p>Billing Address:
        <span v-if="!editBillingAddressMode">{{ user.billingAddress }}</span>
        <input v-else v-model="editedBillingAddress" />
        <button @click="toggleEdit('billingAddress')">Edit</button>
        <button v-if="editBillingAddressMode" @click="saveEditedBillingAddress">Save</button>
      </p>
      <p>Shipping Address:
        <span v-if="!editShippingAddressMode">{{ user.shippingAddress }}</span>
        <input v-else v-model="editedShippingAddress" />
        <button @click="toggleEdit('shippingAddress')">Edit</button>
        <button v-if="editShippingAddressMode" @click="saveEditedShippingAddress">Save</button>
      </p>
    </div>
  </div>
</template>

<script>
import userProfileService from "@/services/UserProfileService.js";

export default {
  data() {
    return {
      allowAnonymous: false,
      user: {},
      originalEmail: '',
      originalPhone: '',
      originalBillingAddress: '',
      originalShippingAddress: '',
      editEmailMode: false,
      editPhoneMode: false,
      editBillingAddressMode: false,
      editShippingAddressMode: false,
    };
  },
  methods: {
    async updateAllowAnonymous() {
      try {
        await this.$axios.put('/api/profile/update', {
          username: this.user.username,
          allowAnonymous: this.allowAnonymous,
        });
        console.log('Anonymous Setting updated successfully!');
      } catch (error) {
        console.error('Error updating Anonymous Setting:', error);
      }
    },
    fetchUserProfile() {
      userProfileService.getProfile(this.$store.state.user.username).then(response => {
        // console.log(response);
        this.user = response.data;
      });
    },
    /*
    async fetchUserProfile() {
    try {
    const response = await this.$axios.get(`/api/profile/`, {
      params: { username: this.$store.state.user.username },
    });
    this.user = response.data;

    // Initialize the original values
    this.originalEmail = this.user.email;
    this.originalPhone = this.user.phone;
    this.originalBillingAddress = this.user.billingAddress;
    this.originalShippingAddress = this.user.shippingAddress;

    // Now that the data is fetched and assigned, set the edit mode for fields
    this.editEmailMode = false;
    this.editPhoneMode = false;
    this.editBillingAddressMode = false;
    this.editShippingAddressMode = false;
  } catch (error) {
    console.error('Error fetching user profile:', error);
  }
},
*/

    toggleEdit(field) {
      this[field + 'Mode'] = !this[field + 'Mode'];
    },
    saveEditedEmail() {
      const updatedData = {
        username: this.user.username,
        email: this.editedEmail,
      };
      userProfileService.updateProfile(updatedData).then(response => {
        console.log(response);
        this.user.email = this.editedEmail;
        this.editEmailMode = false;
      });
    },
    saveEditedPhone() {
      const updatedData = {
        username: this.user.username,
        phone: this.editedPhone,
      };
      userProfileService.updateProfile(updatedData).then(response => {
        console.log(response);
        this.user.phone = this.editedPhone;
        this.editPhoneMode = false;
      });
    },
    saveEditedBillingAddress() {
      const updatedData = {
        username: this.user.username,
        billingAddress: this.editedBillingAddress,
      };
      userProfileService.updateProfile(updatedData).then(response => {
        console.log(response);
        this.user.billingAddress = this.editedBillingAddress;
        this.editBillingAddressMode = false;
      });
    },
    saveEditedShippingAddress() {
      const updatedData = {
        username: this.user.username,
        shippingAddress: this.editedShippingAddress,
      };
      userProfileService.updateProfile(updatedData).then(response => {
        console.log(response);
        this.user.shippingAddress = this.editedShippingAddress;
        this.editShippingAddressMode = false;
      });
    },
    cancelEdit() {
      this.editEmailMode = false;
      this.editPhoneMode = false;
      this.editBillingAddressMode = false;
      this.editShippingAddressMode = false;
      this.editedEmail = this.originalEmail;
      this.editedPhone = this.originalPhone;
      this.editedBillingAddress = this.originalBillingAddress;
      this.editedShippingAddress = this.originalShippingAddress;
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
/* Style for the black boxes */
.black-box {
  background-color: #333; /* Dark gray color */
  color: white;
  padding: 20px;
  border-radius: 5px;
  margin-bottom: 20px;
}
/* Style for the edit buttons */
.black-box button {
  margin-left: 10px;
}
</style>

