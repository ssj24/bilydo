import {StateData} from '@/models/rules/StateData'

export default {
  isLogin(state: StateData) {
    state.isLoggedIn = true;
  },
  isLogout(state: StateData) {
    state.isLoggedIn = false;
  },
  listOn(state: StateData) {
    state.mainList = true;
  },
  listOff(state: StateData) {
    state.mainList = false;
  },
  messagesPlus(state: StateData) {
    state.messages += 1
  },
  messagesReset(state: StateData) {
    state.messages = 0;
  },
  componentKeyPlus(state: StateData) {
    state.componentKey += 1;
  }
}