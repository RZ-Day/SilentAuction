import axios from 'axios';

export default {

  addMessage(message) {
    return axios.post('/create-message', message);
  },

  getMessagesByUserId(userId) {
    return axios.get('/messages', { 
      params: {userId}
    });
  },

  getConversationsByUserId(userId) {
    return axios.get('/conversations', {
      params: {userId}
    })
  },

  startConversation(convo) {
    return axios.post('/conversations', convo);
  },

  getConversationId(convo) {
    return axios.get('/conversations/id', convo);
  }

}