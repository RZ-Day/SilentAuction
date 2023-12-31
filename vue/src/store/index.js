import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    storeMessages: [],
    activeAuctionID: 0,
    activeItemID: 0,
    activeConversationID: 0
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_ACTIVE_AUCTION(state, auctionID) {
      state.activeAuctionID = auctionID;
    },
    SET_ACTIVE_ITEM(state, itemID) {
      state.activeItemID = itemID;
    },
    SET_ACTIVE_CONVERSATION(state, conversationID) {
      state.activeConversationID = conversationID;
    },
    SET_STORE_MESSAGES(state, messageList) {
      state.storeMessages = messageList;
    },
    ADD_MESSAGE(state, message) {
      state.storeMessages.push(message);
    }
  },
  actions: {
    async fetchUserData ({ commit }) {
      try {
        const response = await axios.get('api/user/profile');
        commit('SET_USER', response.data);
      } catch {
        console.error('Error fetching user data');
      }
    }
  }
})
