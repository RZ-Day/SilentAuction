<template>
    <div class="user-profile">
    <h2>User Profile Settings</h2>

    <!-- Display User Profile Data -->
    <div class="user-info black-box">
      <h2>{{ user.username }}'s Profile</h2>
      <p>Email:
        <input @input="logModification" id="textEmail" v-model="user.email"/>
      </p>
      <p>Phone:
        <input @input="logModification" maxlength="10" id="textPhone" v-model="user.phone"/>
      </p>
      <!-- Allow Anonymous Setting -->
      <div class="form-group">
        <label>
          <input @input="logModification" type="checkbox" id="checkboxAnonymous" v-model="user.allowAnonymous" />
          Allow Anonymous Messaging
        </label>
      </div>
    </div>

    <!-- Display Billing and Shipping Addresses -->
    <div class="address-info black-box">
      <h2>Billing & Shipping Addresses</h2>
      <p>Billing Address:
        <input @input="logModification" id="textBillingAddress" v-model="user.billingAddress" class="txt-long"/>
      </p>
      <p>Shipping Address:
        <input @input="logModification" id="textShippingAddress" v-model="user.shippingAddress" class="txt-long"/>
      </p>
    </div>

    <form @submit.prevent="saveChanges">
      <button id="buttonSaveChanges" type="submit">Save Changes</button>
    </form>
  </div>
</template>

<script>
import userProfileService from "@/services/UserProfileService.js";

export default {
  data() {
    return {
      allowAnonymous: false,
      user: {},
      originalUser: {}, // to check whether changes have been made
      modified: false,
    };
  },
  watch: {
    // watch method to watch variables
    // NEVER MIND not needed
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
      console.log('1');
      userProfileService.getProfile(this.$store.state.user.username).then(response => {
        // console.log(response);
        this.user = response.data;
        this.originalUser = this.user;
      });
    },
    logModification(event) {
      console.log(event);
      if (!this.modified) {
        console.log("Modified set to true");
        this.modified = true;
        // Set it so that if navigating away from page, ask user to confirm potential loss of changes
        window.onbeforeunload = function() {
          return true;
        };
      }
    },
    saveChanges() {
      // First ensure validation
      // Use regexes to do so cause we love regexes so much (ty regex101)
      
      // Check email
      // RFC 5322 compliant but doesn't fix 00 bug in dot-delimited addresses //JD I'm wondering what he is even refering to here...
      if (this.user.email.value) {
        alert("Invalid e-mail address entered!");
        return;
      }

      // Check phone number
      // This is easier
      // Too lazy to write regex for valid area code so [0-9]{10} will have to do
      if (!/^[0-9]{10}$/.test(this.user.phone)) {
        alert("Invalid phone number entered!");
        return;
      }

      // Addresses aren't standard language so we're not going to use regex to check against them, just check if blank
      if (this.user.shippingAddress == null || this.user.shippingAddress.trim() == "") {
        alert("Shipping address cannot be empty!");
        return;
      }
      if (this.user.billingAddress == null || this.user.billingAddress.trim() == "") {
        alert("Billing address cannot be empty!");
        return;
      }

      // Finalize submit
      console.log("Submit done");

      // Construct object representing updated values
      const updatedData = {
        username: this.user.username,
        email: this.user.email,
        phone: this.user.phone,
        allowAnonymous: this.user.allowAnonymous,
        billingAddress: this.user.billingAddress,
        shippingAddress: this.user.shippingAddress
      };

      // Submit updated user values to endpoint
      userProfileService.updateProfile(updatedData).then(response => {
        console.log(response);
      });

      // Disable confirm from navigation away
      window.onbeforeunload = null;
      console.log("Before Unload event unregistered");
      this.modified = false;
      console.log("Modified set to false");

      alert("Your profile has been saved.");
    },
    /*
    async fetchUserProfile() {
    try {
    const response = await this.$axios.get(`/api/profile`, {
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
    /*

    toggleEdit(field) {
      console.log(field);
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
    */
  },
  mounted() {
    this.fetchUserProfile();
  },
};
</script>

<style scoped>
/* Add component-specific styles here */

.txt-long {
  width: 45em;
}

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