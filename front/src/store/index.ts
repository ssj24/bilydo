import Vue from "vue";
import Vuex from "vuex";
// import cookie from "@/cookie";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    mainList: true,
  },
  getters: {
    
  },
  mutations: {
    listOn(state) {
      state.mainList = true;
    },
    listOff(state) {
      state.mainList = false;
    }
  },
  actions: {},
  modules: {}
});
