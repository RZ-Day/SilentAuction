import axios from 'axios';



export default {

    getAuctions() {
        return axios.get('/auctions')
    },

    addAuction(newAuction) {
        return axios.post('/create/auction', newAuction)
    },

    getPrivateAuction(auctionPassword) {
        return axios.post(`/auctions/search/private/${auctionPassword}`);
    },
    
    getImages() {

        
         //   return axios.get(`https://wy8fdrlenh.execute-api.us-east-2.amazonaws.com/dev/auction-image-storage?filename=rolextryninemil_0_0.jpeg`)
        

    }


    
}