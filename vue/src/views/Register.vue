<template>
<div class="page">
  <div class="main-container">
    <h1>Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
    <CardVertical>
      <form @submit.prevent="register">
        <text-input title="Name" inputType="text" isRequired="true" v-model="user.name" />
        <text-input title="Email" inputType="email" isRequired="true" v-model="user.email" />
        <text-input title="Phone" inputType="text" isRequired="true" v-model="user.phone" />
        <text-input title="Address" inputType="text" isRequired="true" v-model="user.address" />
        <text-input title="Username" inputType="text" isRequired="true" v-model="user.username" />
        <text-input title="Password" inputType="password" isRequired="true" v-model="user.password" />
        <text-input title="Confirm Password" inputType="password" isRequired="true" v-model="user.confirmPassword" />
        <Button buttonLabel="Register" buttonType="submit" />
      </form>
    </CardVertical>
    <p><router-link :to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
  </div>
</div>
</template>

<script>
import authService from '../services/AuthService';
import TextInput from '../components/building_blocks/TextInput.vue';
import CardVertical from '../components/building_blocks/CardVertical.vue';
import Button from '../components/building_blocks/Button.vue'

export default {
  name: 'register',
  data() {
    return {
      user: {
        name: '',
        email: '',
        phone: '',
        address: '',
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  components: {
    TextInput,
    CardVertical,
    Button
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style scoped>
  .page {
    height: 80vh;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .main-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    max-width: 500px;
  }

  h1 {
    font-family: Ariel, sans-serif;
  }

  div {
    font-family: sans-serif;
  }
</style>
