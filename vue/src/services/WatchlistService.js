import axios from 'axios';

export default {


    getUsersWatchList(userId) {
        return axios.get(`/profile/watchlist/${userId}`);
    },

    add(userId, itemId) {
        return axios.post(`/profile/watchlist/add/${userId}/${itemId}`);
    },

    deleteUsersWatchList(userId, itemId) {
        return axios.delete(`/profile/watchlist/delete/${userId}/${itemId}`);
    }


}
