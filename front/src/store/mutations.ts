import {StateData} from '@/models/rules/StateData'
import { LoginData } from "@/models/rules/LoginData"

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
  
  loggedIn(state: StateData, data: LoginData) {
    state.token = data.token;
    state.id = data.id;
    state.email = data.email;
    state.name = data.name;
    state.phone = data.phone;
    state.location = data.location;
  },
  loggedOut(state: StateData) {
    state.token = '';
    state.id = 0;
    state.email = '';
    state.name = '';
    state.phone = '';
    state.location = '';
  },
  messagesPlus(state: StateData) {
    state.messages += 1
  },
  messagesReset(state: StateData) {
    state.messages = 0;
  }
}