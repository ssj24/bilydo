<template>
  <v-app>
    <v-card class="overflow-hidden">
      <v-app-bar
        afixed 
        app 
        color="#fff" 
        height="70"
        class="navBar"
      >
        <v-toolbar-title @click="listOn">
          <router-link to="/" class="navTitle">
            빌리도
          </router-link>
        </v-toolbar-title>
        <v-spacer></v-spacer>
        
        <v-btn icon 
          style="color: black; font-size: 1em; font-weight: 600; margin-right: 10px;"
          @click="listOff"
        >
          🔎
        </v-btn>
        <span v-if="isLogin">

          <v-btn icon 
            style="color: black; font-size: 1em; font-weight: 600; margin-right: 10px;"
          >
            <router-link to="/article">
              ➕
            </router-link>
          </v-btn>
          <v-btn icon 
            @click="logout"
            style="color: black; font-size: 1em; font-weight: 600; margin-right: 10px;"
          >
            🔓
          </v-btn>
          <v-btn icon style="color: black; font-size: 1em; font-weight: 600; margin-right: 10px;">
            <v-badge
              :content="$store.state.messages"
              :value="$store.state.messages"
              color="#FFEC81"
              bordered
              offset-x="8"
              offset-y="15"
            >
              <router-link to="/account">
                {{name}}
              </router-link>
            </v-badge>
          </v-btn>
          
        </span>
        <v-btn icon 
          v-else
          style="color: black; font-size: 1em; font-weight: 600; margin-right: 10px;"
        >
          <router-link to="/auth">
            🔐
          </router-link>
        </v-btn>
        
      </v-app-bar>
    </v-card>
    <v-content class="content-wrapper">
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import cookie from "@/cookie"

@Component
export default class App extends Vue{
  private name = cookie.cookieName();
  public listOn(): void {
    this.$store.commit('listOn');
  }
  public listOff(): void {
    this.$store.commit('listOff');
  }
  public logout(): void {
    this.$store.commit('loggedOut');
    this.$store.commit('isLogout');
    const date = new Date();
    // Set it
    document.cookie = "jwt_auth_token=" + "= " + "; expires=" + date.toUTCString() + "; path=/";
    document.cookie = "user_id=" + "= " + "; expires=" + date.toUTCString() + "; path=/";
    document.cookie = "user_name=" + "= " + "; expires=" + date.toUTCString() + "; path=/";
    document.cookie = "user_location=" + "= " + "; expires=" + date.toUTCString() + "; path=/";
  }
  get isLogin() {
    return this.$store.state.isLoggedIn;
  }
}
</script>
<style lang="scss">
.navBar {
  border-top: 2px solid #8c28b4 !important;
  box-shadow: 1px 1px 1px #ccc !important;
  .v-btn {
    padding: 0;
    span.v-btn__content {
      height: 48px;
      a {
        line-height: 48px;
        width: 48px;
        height:48px;
      }
    }
  }
}
a.navTitle, a:link.navTitle, a:visited.navTitle, a:hover.navTitle, a:active.navTitle {
  font-family: 'ON-IGothic'; 
  color: #3d3d3d !important;
  // text-shadow: 1px 1px 1px #8c28b4;
  font-size: 2em; 
  font-weight: bolder;
}
.v-badge__badge {
  color: #000 !important;
}

</style>
