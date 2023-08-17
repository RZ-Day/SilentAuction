import axios from 'axios';

export default {
    updateProfile(data) {
        return axios.put('/api/profile/update', data);
    },
    getProfile(username) { 
        return axios.get('/api/profile/?username=' + username);
    }
}