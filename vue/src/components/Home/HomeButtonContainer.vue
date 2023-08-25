<template>
    <div class="button-container">
    <router-link
        to="/auction/create"
        class="auctions-button"
        id="button-left">
        Host An Auction</router-link>
    
    <router-link to="/auctions" class="auctions-button" id="button-mid">
        Bid On Live Auctions</router-link>
    
    <router-link
        to="/auctions/search/private"
        class="auctions-button"
        id="button-right">
        Join Private Auction</router-link>
  </div>
</template>

<style scoped>
.button-container {
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
    min-width:10rem;
    min-height:10rem;
    max-width: 100%;
}

.auctions-button {
    display: flex;
    text-align:center;
    align-items:center;
    padding: 10px 20px;
    background-color: #141b2c;
    color: #ffffff;
    border-radius: 15px;
    text-decoration: none;
    width: 20%;
    height: 30%;
    cursor: pointer;
    transition: 
        transform 0.3s ease-in-out,
        background-color 0.3s linear;

}

.auctions-button:hover {
    background-color: #253b6e;
    transform: scale(1.1);
}

/* These animations play once then never again... -JD 8/24/23 */
#button-left {
    animation: bounceAndEnlarge 1s ease-in-out 3s, shake 2.5s ease-in-out 16s;
}

#button-mid {
    animation: bounceAndEnlarge 1s ease-in-out 3.5s, shake 2.5s ease-in-out 8.5s;
}

#button-right {
     animation: bounceAndEnlarge 1s ease-in-out 4s, shake 2.5s ease-in-out 32s;
}

@keyframes bounceAndEnlarge {
    0%,
    100% {
        transform: scale(1);
    }
    50% {
        transform: scale(1.1);
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
    
}
</style>