<template>
  <v-app>
    <v-card class="overflow-hidden">
      <v-app-bar
        afixed 
        app 
        color="#fff" 
        height="66"
      >
        <v-toolbar-title>빌리도</v-toolbar-title>

        <v-spacer></v-spacer>

        <v-btn icon>
          <v-icon>mdi-heart</v-icon>
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

</style>
