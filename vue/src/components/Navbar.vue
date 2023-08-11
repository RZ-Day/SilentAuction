<template>
  <header class="navbar">
    <div class="container">
      <nav>
        <ul>
          <li v-if="this.$store.state.token!=''">
            <router-link class="link" v-bind:to="{name: 'Messages', params: {currentUserId: this.$store.state.user.id, recipientId: 2}}">
                Inbox
            </router-link>
          </li>
          <li>
            <router-link class="link" v-bind:to="{ name: 'home' }">Home</router-link>
          </li>
          <li v-if="currentRoute != 'AuctionList'">
            <router-link class="link" :to="{ name: 'AuctionList' }">Auctions</router-link>
          </li>
          <dropdown title="Account" :items="accountOptions"/>
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
          link: "#"
        },
        {
          label: "Logout",
          link: "logout"
        }
      ],
      anonymousAccountOptions: [
        {
          label: "Login",
          link: "login"
        },
        {
          label: "Register",
          link: "register"
        }
      ]
    }
  },
  components: {
    Dropdown
  },
  computed: {
    currentRoute() {
      return this.$route.name;
    },
    accountOptions() {
      if(this.$store.state.token == '') {
        return this.anonymousAccountOptions;
      } else {
        return this.authorizedAccountOptions;
      }
    }
  }
}
</script>

<style scoped>
  .navbar {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    background-color: rgb(255, 255, 255);
    height: 50px;
    border-bottom: solid;
    border-width: 1px;
    border-color: rgb(223, 223, 223);
    
  }

  .container {
    display: flex;
    align-items: center;
    height: 100%;
    margin-right: 50px;
  }

  nav {
    display: flex;
    padding: 0px;
    height: 100%;
    width: auto;
  }

  nav ul {
    display: flex;
    justify-content: flex-end;
    height: 100%;
    list-style-type: none;
    width: 100%;
    margin: 0;
  }

  li {
    display: flex;
    cursor: pointer;
    align-items: center;
    height: 100%;
    background-color:rgb(255, 255, 255);
    padding-left: 10px;
    padding-right: 10px;
    text-align: center;
  }

  li.active,
  li:hover {
    background-color: rgb(231, 231, 231);
    transition: .2s ease;
    color:rgb(73, 201, 69) ;
  }

  li:hover .link {
    transition: .2s ease;
    color:rgb(73, 201, 69) ;
  }

  .link {
    display: flex;
    align-items: center;
    width: 100%;
    height: 100%;
    text-decoration-line: none;
    color: black;
  }

  
</style>