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
      }
}