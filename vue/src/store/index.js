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
    activeAuctionID: 0,
    activeItemID: 0,
    auctionUsersWatchlList: []
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
    SAVE_USERSWATCHLIST(state, auction){
      state.auctionUsersWatchlList.push(auction);
    },
    DELETE_USERSWATCHLIST(state,userswatchlistToDelete){
      state.userswatchlist = state.userswatchlist.filter((userswatchlist) => {
        return userswatchlist !== userswatchlistToDelete;
      })
    },
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
  },
})
