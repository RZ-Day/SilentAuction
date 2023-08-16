import axios from 'axios';

export default {
    getBidsOfAuction(id) {
        return axios.get('/auctions/' + id + '/currentbids')
    },

    getBidsOfItem(id) {
        return axios.get('/bids/' + id)
    },

    addAuction(newAuction) {
        return axios.post('/create/auction', newAuction)
    },

    addBid(dto) {
        return axios.post('/bids/create', dto);
    }
}