<template>
  <div class="users-watch-list-view">
       <UsersWatchList :watchList="watchList" />
      <ul>
          <li v-for="item in usersWatchList" :key="item.item_id">
              {{ item.item_id }}
            </li>
        </ul>
    </div>
</template>

<script>
import UsersWatchList from '../components/UsersWatchList.vue';
import UsersWatchListService from '../services/UsersWatchListService';


export default {
    name: "UsersWatchListView",

    data() {
        return {
            watchList: [],
        }
    },
    components: {
        UsersWatchList,

    },
    // computed: {
    //     usersWatchList(){
    //         return this.$store.state.usersWatchList;
    //     },
    // },
    created(){
        
       UsersWatchListService.getUsersWatchList().then((response) => {
           if (response.status === 200) {
               this.watchList = response.data;
           } else {
               console.log("Error gettig watchlist data");
           }
       })
    
    }
};
</script>

<style>
</style>