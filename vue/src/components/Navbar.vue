<template>
  <header class="navbar">
    <div>
      <a class="logo-link" href="/">
        <!-- Replace '/' with your desired link -->
        <img id="imgLogo" src="../Assets/logo.png" />
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
          <li v-if="currentRoute != 'AuctionList'">
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
import Dropdown from "./Dropdown.vue";

export default {
  name: "navbar",
  data() {
    return {
      authorizedAccountOptions: [
        {
          label: "Profile",
          link: "/profile",
        },
        {
          label: "Logout",
          link: "/logout",
        },
      ],
      anonymousAccountOptions: [
        {
          label: "Login",
          link: "/login",
        },
        {
          label: "Register",
          link: "/register",
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
  margin-right: 15px;
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
  background-color: white;
  height: 100%;
  margin: 0;
  padding: 0;
}

li {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0 10px;
  height: 100%;
  text-align: center;
}

li:hover .link {
  transition: 0.2s ease;
  color: #253b6e;
}

.link {
  display: flex;
  align-items: center;
  text-decoration: none;
  color: black;
  height: 100%;
}
</style>
