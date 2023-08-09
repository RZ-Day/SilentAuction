import axios from 'axios';

export default {
    getAuctions() {
        return axios.get('/auctions')
    },

    addAuction() {
        return axios.post('/create-auction', this.newAuction)
    }
}