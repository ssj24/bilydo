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
        <v-toolbar-title>
          <router-link to="/" class="navTitle">
            빌리도
          </router-link>
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn icon 
          style="color: black; font-size: 1em; font-weight: 600; margin-right: 20px;"
        >
          둘러보기
        </v-btn>
        <v-btn icon 
          v-if="true" 
          style="color: black; font-size: 1em; font-weight: 600; margin-right: 10px;"
        >
          <router-link to="/auth">
            로그인
          </router-link>
        </v-btn>
        <v-btn icon v-else style="color: black; font-size: 1em; font-weight: 600; margin-right: 10px;">
          <router-link to="">
            마이페이지
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
import Vue from 'vue';

export default Vue.extend({
  name: 'App',

  components: {
  },

  data: () => ({
    //
  }),
});
</script>
<style lang="scss">
.navBar {
  border-top: 2px solid #8c28b4 !important;
  box-shadow: 1px 1px 1px #ccc !important;
}
a.navTitle, a:link.navTitle, a:visited.navTitle, a:hover.navTitle, a:active.navTitle {
  font-family: 'ON-IGothic'; 
  color: #3d3d3d !important;
  // text-shadow: 1px 1px 1px #8c28b4;
  font-size: 2em; 
  font-weight: bolder;
}
</style>
