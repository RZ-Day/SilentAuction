<template>
    <div class="button-container">
    <router-link
        to="/auction/create"
        class="auctions-button"
        id="button-left">
        <v-button class="btn">
            Host An Auction
        </v-button>
    </router-link>
    
    <router-link 
        to="/auctions" 
        class="auctions-button" 
        id="button-center">
        <v-button class="btn">
            Bid On Live Auctions
        </v-button>
    </router-link>

    <router-link
        to="/auctions/search/private"
        class="auctions-button"
        id="button-right">
        <v-button class="btn">
            Join Private Auction
        </v-button>
    </router-link>
  </div>
</template>


<script>
import VButton from "../Utils/VButton.vue"
export default{
    name: 'home-button-container',
    components: {VButton}
}
</script>


<style scoped>
.button-container {
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
    min-width:10rem;
    min-height:10rem;
    max-width: 100%;
}
.auctions-button{
    display: flex;
    text-decoration: none;
    width: 15rem;
    height: 4rem;
    margin: 0 1rem;
}
.btn {
    font-size:larger;
    transition: 
        transform 0.12s ease-in-out,
        opacity 0.2s linear;
}
/* This opacity transition could be used to 'transition' the color using a second element hidding under the button -JD 8/29/23*/
.btn:hover{
    transform: scale(1.1);
    opacity: 0.9;
}


/* These animations play once then never again... -JD 8/24/23 */
/* Ditched. Should be implemented in Vue if we want them. - JD 8/29/23
/* #button-left {
    animation: bounceAndEnlarge 1s ease-in-out 3s, shake 2.5s ease-in-out 16s;
}

#button-mid {
    animation: bounceAndEnlarge 1s ease-in-out 3.5s, shake 2.5s ease-in-out 8.5s;
}

#button-right {
     animation: bounceAndEnlarge 1s ease-in-out 4s, shake 2.5s ease-in-out 32s;
} */

/* @keyframes bounceAndEnlarge {
    50% {
        transform: scale(1.1);
    }
    100% {
        transform: scale(1);
    }
    
}

@keyframes shake {
    33% {
        transform: translateX(5px);
    }
    66% {
        transform: translateX(-5px);
    }
    100% {
        transform: translateX(0);
    }
    
} */
</style>