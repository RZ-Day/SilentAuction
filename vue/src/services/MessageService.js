import axios from 'axios';

export default {

  addMessage(message) {
    return axios.post('/create-message', message);
  },

  getMessagesByUserId(userId) {
    return axios.get('/message', userId);
  }

}