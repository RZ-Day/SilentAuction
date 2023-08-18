import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import DisplayAuctionListView from "@/views/DisplayAuctionListView.vue";
// import UserProfile from "@/views/UserProfile.vue";
import ProfileView from "@/views/ProfileView.vue";
import AuctionView from "@/views/AuctionView.vue";
import ItemView from "@/views/ItemView.vue";
import AddAuctionView from "@/views/AddAuctionView.vue";
import Messages from '../views/Messages.vue';
import PrivateAuctionView from "@/views/PrivateAuctionView.vue";
import WatchlistView from "@/views/WatchlistView.vue";


Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/auctions",
      name: "AuctionList",
      component: DisplayAuctionListView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView,
    },
    {
      path: "/auctions/:currentAuctionID",
      name: "AuctionIndex",
      component: AuctionView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/auctions/:currentAuctionID/item/:currentItemID",
      name: "ItemIndex",
      component: ItemView,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/auction/create",
      name: "AddAuction",
      component: AddAuctionView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/messages/user/:currentUserId",
      name: "Messages",
      component: Messages,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/auctions/search/private",
      name: "PrivateAuctionView",
      component: PrivateAuctionView
    },
    {
      path: "/profile/watchlist",
      name: "UsersWatchListView",
      component: WatchlistView,
      meta: {
        requiresAuth: true
      }
    },

  ]
});

router.beforeEach((to, from, next) => {
  console.log("Made it to router");
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
