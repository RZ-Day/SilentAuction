<template>
<div class="login-page">
  <div class="main-container">
    <h1 >Sign In</h1>
    <div role="alert" v-if="invalidCredentials">
      Invalid username and password!
    </div>
    <div role="alert" v-if="this.$route.query.registration">
      Thank you for registering, please sign in.
    </div>
    <CardVertical>
      <form @submit.prevent="login">
        <TextInput inputType="text" isRequired="true" title="Username" v-model="user.username"/>
        <TextInput inputType="password" isRequired="true" title="Password" v-model="user.password"/>
        <Button buttonType="submit" buttonLabel="Login" />
      </form>
    </CardVertical>

    <p>
      <router-link :to="{ name: 'register' }">Need an account? Sign up.</router-link>
    </p>
  </div>
</div>
</template>

<script>
import TextInput from "../components/building_blocks/TextInput.vue";
import CardVertical from "../components/building_blocks/CardVertical.vue";
import Button from "../components/building_blocks/Button.vue";
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {
    TextInput,
    CardVertical,
    Button
  },
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
  .login-page {
    display: flex;
    justify-content: center;
  }

  .main-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 65px;
  }

  h1 {
    font-family: sans-serif;
  }

  div {
    font-family: sans-serif;
  }

</style>