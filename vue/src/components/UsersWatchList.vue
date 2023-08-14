<template>
  <div class="users-watch-list">
    <h2>Here is list of item(s) from your watch list.</h2>
    <ul class="watch-list">
      <li v-for="item in watchList" :key="item.itemId">
        <div class="watch-list-item">
          <h3>{{ item.itemName }}</h3>
          <p class="watch-list-description">{{ item.description }}</p>
        </div>
      </li>
    </ul>
    <p v-if="watchList.length === 0">Your current watch list is empty</p>
    <button class="btn deleteUsersWatchList" v-on:click="deleteUsersWatchList">
      Delete
    </button>
  </div>
</template>

<script>
import UsersWatchListService from "../services/UsersWatchListService";

export default {
  name: "UsersWatchList",
  props:{
    watchList: [],
  },
  data() {
    return {
      isChecked: false,
      // watchList: [],
      // // usersWatchList:[],
    };
  },

  methods: {
    // getUsersWatchList() {
    //   UsersWatchListService.getUsersWatchList()
    //     .then((response) => {
    //       this.watchList = response.data;
    //     })
    //     .catch((error) => {
    //       console.error("Error fetching: ", error);
    //     });
    // },

    retrieveUsersWatchList() {
      UsersWatchListService.getUsersWatchList().then((response) => {
        this.$store.commit("SAVE_USERSWATCHLIST", response.data);
        this.isLoading = false;

        if (this.$store.state.UsersWatchListService.length > 0) {
          const userId = response.data[1].user_id;
          this.$store.commit("SAVE_ACTIVE_USERSWATCHLIST", userId);
          this.$router.push({ name: "USERSWATCHLIST", params: { id: userId } });
        } else {
          this.$store.commit("SAVE_ACTIVE_USERSWATCHLIST", null);
        }
      });
    },

    saveNewUsersWatchList() {
      const newUsersWatchList = JSON.parse(
        JSON.stringify(this.newUsersWatchList)
      );
      UsersWatchListService.addUsersWatchList(newUsersWatchList).then(
        (response) => {
          if (response.status === 201) {
            this.retrieveUsersWatchList();
            this.resetInputs();
          }
        }
      );
    },

    deleteUsersWatchList(){
      if(confirm(
        "Delete the item from the watch list. The action cannot be undone."
      )
      ){
        UsersWatchListService
          .deleteUsersWatchList(this.UsersWatchList)
          .then(response => {
            if(response.status === 200){
              alert("Users Watch List item succesfully deleted");
              this.$router.push(`/UsersWatchListView/${this.UsersWatchList}`);
            }
          })
          .card(error => {
            if(error.response){
              this.errorMsg = 
              "Error deleting the item. Response received was ' " + error.response.statusText + " '. ";
            } else if(error.request) {
              this.errorMsg = "Error deleting card. Server could not be reached.";
            } else {
              this.errorMsg = "Error deleting card. Request could not be created.";
            }
          });
      }
    }
  },
  
  created() {
    this.getUsersWatchList();
   
  },

};
</script>

<style>

.users-watch-list h2{
    text-align: center;
    font-size: 35px;
}

.users-watch-list p{
    text-align: center;
    font-size: 25px;
}

.deleteUsersWatchList{
    position: absolute;
    right: 250px;
    border: none;
    border-radius: 15px;
    padding: 15px;
    text-align: center;
    font-size: 14px;
    color: #272343;
}
</style>