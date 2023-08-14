import axios from 'axios';

export default {

    getAuction() {
        return axios.get('/auctions');
    },

    add(userswatchlist) {
        return axios.post('/profile/userswatchlist', userswatchlist);
    },

    getUsersWatchList() {
        return axios.get('/profile/userswatchlist');
    },

    deleteUsersWatchList() {
        return axios.delete('/profile/userswatchlist${itemId}');
    }



}


