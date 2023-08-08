import axios from 'axios';

export default {
    getAuctions() {
        return axios.get('/auctions')
    }
}