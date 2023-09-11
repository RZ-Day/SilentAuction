<template>
  <header class="navbar">
    <div>
      <a class="logo-link" href="/">
        <!-- Replace '/' with your desired link -->
        <img id="imgLogo" src="../../Assets/logo.png" />
      </a>
    </div>

    <div class="container">
      <nav>
        <ul>
          <li v-if="this.$store.state.token!=''">
            <router-link class="link" v-bind:to="{name: 'Messages', params: {currentUserId: this.$store.state.user.id}}">
                Inbox
            </router-link>
          </li>
          <li>
            <router-link class="link" v-bind:to="{ name: 'home' }"
              >Home</router-link
            >
          </li>
          <li>
            <router-link class="link" :to="{ name: 'AuctionList' }"
              >Auctions</router-link
            >
          </li>
          <dropdown title="Account" :items="accountOptions" />
        </ul>
      </nav>
    </div>
  </header>
</template>

<script>
import Dropdown from "../building_blocks/Dropdown.vue";

export default {
  name: "navbar",
  data() {
    return {
      authorizedAccountOptions: [
        {
          label: "Profile",
          link: "profile",
        },
        {
          label: "Logout",
          link: "logout",
        },
      ],
      anonymousAccountOptions: [
        {
          label: "Login",
          link: "login",
        },
        {
          label: "Register",
          link: "register",
        },
      ],
    };
  },
  components: {
    Dropdown,
  },
  computed: {
    currentRoute() {
      return this.$route.name;
    },
    accountOptions() {
      if (this.$store.state.token == "") {
        return this.anonymousAccountOptions;
      } else {
        return this.authorizedAccountOptions;
      }
    },
  },
};
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: rgb(255, 255, 255);
  height: 50px;
  border-bottom: solid;
  border-width: 2px;
  border-color: rgb(200, 200, 200);
}

.container {
  display: flex;
  height: 100%;
}

#imgLogo {
  width: 15em;
  transform: translateX(5%);
}
.logo-link {
  display: flex;
  align-items: center;
  text-decoration: none;
  color: black;
}
nav ul {
  display: flex;
  justify-content: flex-end;
  list-style-type: none;
  height: 100%;
  margin: 0;
  padding: 0;
}

li {
  display: flex;
  align-items: center;
  cursor: pointer;
  background-color: rgb(255, 255, 255);
  padding: 0 10px;
  text-align: center;
}

li.active,
li:hover {
  background-color: rgb(201, 201, 201);
  transition: 0.2s ease;
}

li:hover .link {
  transition: 0.2s ease;
  color: rgb(255, 255, 255);
}

.link {
  display: flex;
  height: 100%;
  align-items: center;
  text-decoration: none;
  color: rgb(0, 0, 0);
}
</style>
