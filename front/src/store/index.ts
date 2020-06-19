import Vue from "vue";
import Vuex from "vuex";
import getters from './getters'
import mutations from './mutations'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    componentKey: 0,
    mainList: true,
    isLoggedIn: getters.isLogin(),
    token: getters.userToken() as string,
    id: getters.userId() as number,
    name: getters.userName() as string,
    location: getters.userLocation() as string,
    messages: 0
  },
  getters,
  mutations,
});
