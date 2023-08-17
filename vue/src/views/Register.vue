<template>
<div class="background-image">
  <div id="register" class="text-center">
    <form @submit.prevent="register">
      <h1>Create Account</h1>
      <div role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div class="inputs">
        <div class="form-input-group">
          <label for="name">Name</label>
          <input type="text" id="name" v-model="user.name" required autofocus />
        </div>
        <div class="form-input-group">
          <label for="email">Email</label>
          <input type="email" id="email" v-model="user.email" required />
        </div>
        <div class="form-input-group">
          <label for="phone">Phone Number</label>
          <input type="text" id="phone" v-model="user.phone" pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" placeholder="555-555-5555" required />
        </div>
        <div class="form-input-group">
          <label for="address">Address</label>
          <input type="text" id="address" v-model="user.address" required />
        </div>
        <div class="form-input-group">
          <label for="username">Username</label>
          <input type="text" id="username" v-model="user.username" required/>
        </div>
        <div class="form-input-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="user.password" required />
        </div>
        <div class="form-input-group">
          <label for="confirmPassword">Confirm Password</label>
          <input type="password" id="confirmPassword" v-model="user.confirmPassword" required />
        </div>
      </div>
      <button type="submit">Create Account</button>
      <p><router-link :to="{ name: 'login' }">Already have an account? Log in.</router-link></p>
    </form>
  </div>
</div>
</template>

<script>
import authService from '../services/AuthService';

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
.form-input-group {
  margin-bottom: 1rem;
}
label {
  margin-right: 0.5rem;
}

#register {
  max-width: 400px;
}

.inputs {
  display: flex;
  flex-direction: column;
  padding: 25px;
  margin-bottom: 30px;
  border-radius: 30px;
  background-color: rgb(216, 216, 216);
  box-shadow: 0 4px 6px rgb(60, 60, 60), inset 0 0 20px rgba(0, 0, 0, 0.5);
}

.form-input-group {
  display: flex;
  justify-content: space-between;
  font-size: 20px;
  height: 20px;
  border-radius: 5px;
}

.background-image {
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(rgb(0, 0, 0), rgb(255, 255, 255));
  height: 100vh;
  background-size: cover;
  background-position: center;
}

#register h1 {
  text-align: center;
  font-weight: bold;
}

#name {
  background: transparent;
  border: 2px solid black;
  border-radius: 5px;
  height: 100%;  
}

#email {
  background: transparent;
  border: 2px solid black;
  border-radius: 5px;
  height: 100%;  
}

#phone {
  background: transparent;
  border: 2px solid black;
  border-radius: 5px;
  height: 100%;  
}

#address {
  background: transparent;
  border: 2px solid black;
  border-radius: 5px;
  height: 100%;  
}

#username {
  background: transparent;
  border: 2px solid black;
  border-radius: 5px;
  height: 100%;  
}

#password {
  background: transparent;
  border: 2px solid black;
  border-radius: 5px;
  height: 100%;  
}

#confirmPassword {
  background: transparent;
  border: 2px solid black;
  border-radius: 5px;
  height: 100%;  
}

button{
  width: 55%;
  outline: none;
  border-radius: 5px;
  height: 30px;
  cursor: pointer;
  font-size: 18px;
  font-weight: bold;
  margin: 20px 80px;
  background: transparent;
  border: 2px solid black;
  border-radius: 10px;

}

p{
  text-align: center;
  font-size: 18px;
  text-decoration: none;
  color: black;
}
</style>
