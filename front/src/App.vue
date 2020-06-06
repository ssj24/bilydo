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
          <div class="star1"></div>
          <div class="star2"></div>
          <div class="star3"></div>
          <div class="star4"></div>
          <div class="star5"></div>
          <router-link to="/" class="navTitle">
            빌 리 도
          </router-link>
        </v-toolbar-title>
        <v-spacer></v-spacer>
        
        <v-btn icon 
          style="color: black; font-size: 1em; font-weight: 600; margin-right: 5px;"
          title="둘러보기"
          @click="listOff"
        >
          <router-link to="/">
            🔎
          </router-link>
        </v-btn>
        <span v-if="isLogin">

          <v-btn icon 
            style="color: black; font-size: 1em; font-weight: 600; margin-right: 5px;"
            title="새 글 쓰기"
          >
            <router-link to="/article">
              ➕
            </router-link>
          </v-btn>
          <v-btn icon 
            @click="logout"
            style="color: black; font-size: 1em; font-weight: 600; margin-right: 5px;"
            title="로그아웃"
          >
            <router-link to="/">
              🔓
            </router-link>
          </v-btn>
          <v-btn icon 
            title="계정"
            style="color: black; font-size: 1em; font-weight: 600; margin-right: 5px;">
            <router-link :to="{name:'Account', params:{userId:userId}}" class="toMypage">
              <v-badge
                :content="$store.state.messages"
                :value="$store.state.messages"
                color="#FFEC81"
                bordered
                offset-x="8"
                offset-y="8"
              >
                  {{name}}
              </v-badge>
            </router-link>
          </v-btn>
          
        </span>
        <v-btn icon 
          v-else
          style="color: black; font-size: 1em; font-weight: 600; margin-right: 5px;"
          title="로그인"
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
  private name = 'my';
  private userId = '';
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
  created() {
    this.name = cookie.cookieName() as string;
    this.userId = cookie.cookieId() as string;
  }
}
</script>
<style lang="scss">
.navBar {
  border-top: 3px solid #8c28b4 !important;
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
      span.v-badge {
        width: 48px;
        height: 48px;
      }
    }
  }
}
a.navTitle, a:link.navTitle, a:visited.navTitle, a:hover.navTitle, a:active.navTitle {
  font-family: 'SangSangAnt'; 
  color: #2f023d !important;
  // text-shadow: 1px 1px 1px #8c28b4;
  font-size: 3em; 
  // font-weight: bolder;
}
.v-badge__badge {
  color: #000 !important;
}

.v-toolbar__title {
  position: relative;
  overflow: hidden;
}
// .star1 {
//   position: absolute;
//   background: radial-gradient(#ffd001, #fff);
//   width: 10px;
//   height: 10px;
//   top: 10px;
//   left: 0px;
//   animation: twinkle 2s ease-in infinite;
// }
// .star2 {
//   position: absolute;
//   background: radial-gradient(#fff0ae, #fff);
//   width: 10px;
//   height: 10px;
//   bottom: 20px;
//   left: -3px;
//   animation: twinkle 2s ease-in infinite;
//   animation-delay: 0.3s;
// }
// .star3 {
//   position: absolute;
//   background: radial-gradient(#ffd001, #fff);
//   width: 3px;
//   height: 3px;
//   top: 35px;
//   right: 0px;
//   animation: twinkle 2s ease-in infinite;
//   animation-delay: 0.5s;
// }
.star4 {
  position: absolute;
  // background: radial-gradient(#f0da7c, #fff);
  background-image: url('./assets/images/yellowFlower.png');
  background-size: cover;
  width: 20px;
  height: 20px;
  top: 13px;
  right: 24px;
  // animation: twinkle 2s ease-in-out infinite;
  // animation-delay: 0.2s;
}
// .star5 {
//   position: absolute;
//   background: radial-gradient(#ffd001, #fff);
//   width: 12px;
//   height: 12px;
//   bottom: 23px;
//   left: 35px;
//   animation: twinkle 2s ease-in infinite;
//   animation-delay: 0.8s;
// }
@keyframes twinkle {
  0% {
    opacity: 0;
  }
  // 30% {
  //   opacity: 0.4;
  // }
  50% {
    opacity: 1;
  }
  // 80% {
  //   opacity: 0.4;
  // }
  // 100% {
  //   opacity: 0;
  // }
}
</style>
