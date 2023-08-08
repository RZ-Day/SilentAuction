<template>
  <header class="navbar">
    <div class="container">
      <nav>
        <ul>
          <li><router-link v-bind:to="{ name: 'home' }">Home</router-link></li>
          <li>Link Two</li>
          <li>Account</li>
          <!-- SHOULD BE CONTAINED IN PROFILE DROPDOWN <li><router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link></li> -->
        </ul>
      </nav>
    </div>
  </header>
</template>

<script>
export default {
  name: "navbar"
}
</script>

<style scoped>
  .navbar {
    display: flex;
    justify-content: flex-end;
    background-color: grey;
    height: 50px;
    
    }

  .container {
    
  }

  nav ul {
    display: flex;
    justify-content: center;
    height: 100%;
    background-color: cadetblue;
    list-style-type: none;
    width: 100%;
  }

  li {
    height: 100%;
    background-color:brown;
    padding-left: 5px;
    padding-right: 5px;
    }

  nav {
    display: flex;
    background: burlywood;
  }
</style>