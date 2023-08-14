import axios from 'axios';

export default {
    updateProfile(data) {
        return axios.put('/api/profile/update', data);
    },
}