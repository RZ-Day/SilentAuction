<template>
    <div class="home">
        <div class="header">
            <h1>Explore Auctions With Silent Auction</h1>
            <p class="sub-header"> Simple. Fun. Reliable.</p>
        </div>
        <CoolButtons />
        <Slideshow />
    </div>
</template>


<script>
import Slideshow from '@/components/Home/HomeSlideshow.vue';
import CoolButtons from '@/components/Home/HomeButtonContainer.vue';
export default {
    name: "home",
    components: {Slideshow, CoolButtons},
};
</script>


<style scoped>
.home {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
        "Lucida Sans Unicode", Geneva, Verdana, sans-serif; 
    opacity: 0; /* Start with 0 opacity */
    animation: fadeIn 1.5s forwards;
    /* All the background settings */
    background-image: url('@/Assets/back4.jpg'); 
    background-size: cover;
    background-position: bottom center;
    background-repeat: no-repeat;
    background-attachment: fixed;
    min-width: 500px;
    min-height: 1000px;
    /* View Height/View Width */
    width: 100vw;
    height: 100vh; 
  }
@keyframes fadeIn {
    from {
        opacity: 0;
    }
    to {
        opacity: 1;
    }
}
.header {
    text-align: center;
    margin-bottom: 20px;
    margin-top:50px;
    color: #1e2842;
    font-size: 32px;
}

</style>

