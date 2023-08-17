<template>
<div class="background-image">
  <div id="login">
    <form @submit.prevent="login">
      <h1 >Please Sign In</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="form-input-group">
        <label for="username">Username</label>
        <input type="text" id="username" v-model="user.username" required autofocus />
      </div>
      <div class="form-input-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="user.password" required />
      </div>
      <button type="submit">Sign in</button>
      <p>
      <router-link :to="{ name: 'register' }">Need an account? Sign up.</router-link></p>
    </form>
  </div>
</div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    async login() {
      try {
        const response = await authService.login(this.user);

        if (response.status === 200) {
          this.$store.commit("SET_AUTH_TOKEN", response.data.token);
          this.$store.commit("SET_USER", response.data.user);

          await this.$store.dispatch("fetchUserData");

          this.$router.push("/");
        }
      } catch (error) {
        const response = error.response;

        if(response.status === 401) {
          this.invalidCredentials = true;
        }
      }
    }
  }
};
</script>

<style scoped>
.form-input-group {
  margin-bottom: 1rem;
}
label {
  margin-right: 0.5rem;
}
#login{
  border-radius: 25px;
  width: 200px;
  margin: 25px auto;
  padding: 80px;
  box-shadow: 0 4px 6px rgb(60, 60, 60), inset 0 0 20px rgba(0, 0, 0, 0.5);
}

#login h1{
  font-size: 35px;
  text-align: center;
}

.form-input-group{
  font-size: 20px;
  height: 50px;
}

#username{
  width: 100%;
  height: 50%;
  background: transparent;
  border: 2px solid black;
  border-radius: 10px;

}

#password{
  width: 100%;
  height: 50%;
  background: transparent;
  border: 2px solid black;
  border-radius: 10px;
}

button{
  width: 55%;
  outline: none;
  border-radius: 5px;
  height: 30px;
  cursor: pointer;
  font-size: 18px;
  font-weight: bold;
  margin: 20px auto;
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

.background-image {
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(rgb(0, 0, 0), rgb(255, 255, 255));
  height: 100vh;
  background-size: cover;
  background-position: center;

}

</style>