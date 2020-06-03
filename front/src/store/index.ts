import Vue from "vue";
import Vuex from "vuex";
import getters from './getters'
import mutations from './mutations'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    mainList: true,
    isLoggedIn: getters.isLogin(),
    token: '',
    id: 0,
    email: '',
    name: '',
    phone: '',
    location: '',
    messages: 1
  },
  getters,
  mutations,
});
