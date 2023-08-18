<template>
  <div id="login" class="login">
    <form class="form" @submit.prevent="login">
      <h1 >User Login</h1>
      <div role="alert" v-if="invalidCredentials">
        Invalid username and password!
      </div>
      <div role="alert" v-if="this.$route.query.registration">
        Thank you for registering, please sign in.
      </div>
      <div class="inputs">
        <div class="input-box">
          <input type="text" class="input" id="username" placeholder="Username" v-model="user.username" required="required" />
        </div>
        <div class="input-box">
          <input type="password" class="input" id="password" placeholder="Password" v-model="user.password" required="required" />
        </div>
      </div>
      <button class="submit" type="submit">Sign In</button>
      <div class="spacer">
        <div class="spacer-line" />
      </div>
      <p>
        <router-link class="sign-up" :to="{ name: 'register' }">Need an account? Sign up.</router-link>
      </p>
    </form>
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

.spacer {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 40%;
  width: 100%;
}

.spacer-line {
  margin-top: 90%;
  width: 70%;
  border-top: solid;
  border-width: 1px;
  border-color: rgb(168, 168, 168);
}

.form {
  height: 100%;
}

.submit {
  color: white;
  height: 40px;
  width: 100%;
  border-radius: 5px;
  border-width: 1px;
  background-color: rgb(53, 53, 68);
}

.login {
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  padding: 0;
  font-family: Ariel, sans-serif;
  height: 100vh;
  width: 100vw;
}

.input:focus {
  background-color: rgb(245, 245, 245);
  border-width: 1px;
  transition: background-color ;
}

.input-box {
  display: block;
  border-radius: 5px;
  height: 30px;
  margin-bottom: 20px;
}

.input-box input {
  height: 100%;
  width: 100%;
  border-radius: 5px;
  border-width: 1px;
}

.input-box span {
  position: absolute;
  left: 0;
  padding-left: 10px;
  pointer-events: none;
}

.inputs {
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  position: relative;
  /* justify-content: center; */
  /* align-items: center; */
  width: 100%;
}

label {
  margin-right: 0.5rem;
}
</style>