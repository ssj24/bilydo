<template>
  <v-app>
    <v-card class="overflow-hidden">
      <v-app-bar
        afixed 
        app 
        color="#fff" 
        height="66"
        class="navBar"
      >
        <v-toolbar-title style="font-family: 'ON-IGothic'; color: #df4c73; font-size: 2em; font-weight: bolder;">빌리도</v-toolbar-title>

        <v-spacer></v-spacer>
        <v-btn icon style="color: black; font-size: 1em; font-weight: 600; margin-right: 20px;">
          둘러보기
        </v-btn>
        <v-btn icon v-if="true" style="color: black; font-size: 1em; font-weight: 600; margin-right: 10px;">
          로그인
        </v-btn>
        <v-btn icon v-else style="color: black; font-size: 1em; font-weight: 600; margin-right: 10px;">
          마이페이지
        </v-btn>
      </v-app-bar>
    </v-card>
    <v-content>
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
  border-top: 2px solid #df4c73 !important;
  box-shadow: 1px 1px 1px #ccc !important;
}
</style>
