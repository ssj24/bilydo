<template>
  <v-card class="overflow-hidden">
      <v-app-bar
        afixed 
        app 
        color="#fff" 
        height="70"
        class="navBar"
        :key="$store.state.componentKey"
      >
        <v-toolbar-title @click="listOn">
          <div class="star1"></div>
          <div class="star2"></div>
          <div class="star3"></div>
          <div class="star4"></div>
          <div class="star5"></div>
          <router-link to="/" class="navTitle"
        
          >
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
</template>

<script lang="ts">
  import { Component, Vue, Watch } from 'vue-property-decorator'
  import cookie from '@/cookie'

  @Component
  export default class Nav extends Vue{
    private name = 'my';
    private userId = '';
    public listOn(): void {
      this.$store.commit('listOn');
    }
    public listOff(): void {
      this.$store.commit('listOff');
    }
    public logout(): void {
      const date = new Date();
      // Set it
      document.cookie = "jwt_auth_token= ; expires=" + date.toUTCString() + "; path=/";
      document.cookie = "user_id= ; expires=" + date.toUTCString() + "; path=/";
      document.cookie = "user_name= ; expires=" + date.toUTCString() + "; path=/";
      document.cookie = "user_location= ; expires=" + date.toUTCString() + "; path=/";
      this.$store.commit('isLogout');
    }
    get isLogin() {
      return this.$store.state.isLoggedIn;
    }
    @Watch('$store.state.componentKey')
    componentKeyChanged() {
      this.userId = cookie.cookieId() as string;
      this.name = cookie.cookieName() as string;
    }
    mounted() {
      this.userId = cookie.cookieId() as string;
      this.name = cookie.cookieName() as string;
    }
  }
</script>

<style scoped>

</style>