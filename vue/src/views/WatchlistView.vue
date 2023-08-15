<template>
  <div>
       <Watchlist  :watchlist="watchlist" />
      
    </div>
</template>

<script>
import Watchlist from '@/components/Watchlist.vue';
import WatchListService from '@/services/WatchlistService.js';



export default {
    name: "WatchListView",
    components: {Watchlist},

    data() {
    return {
      watchlist: [], 
    };
  },


    created() {
        WatchListService.getUsersWatchList(this.$store.state.user.id).then((response) => {
            if (response.status === 200) {
                this.watchlist = response.data;
            } else {
                console.log("badbafd")
                alert("Watchlist response not good");
            }
        });
    
    },

};
</script>

<style>
</style>
