<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="8" class="searchBar">
        <v-icon class="ma-2">mdi-magnify</v-icon>
        <v-select
          :items="categories"
          label="Category"
        ></v-select>
        <input type="text">
      </v-col>
    </v-row>
    <v-row class="mainSection">
      <v-col
        v-if="true"
        class="regionTrade"
        sm="6"  
      >
        지역 트레이드
      </v-col>
      <v-col
        v-else
        class="recentBoards"
        sm="6"
      >
        최근 게시물
      </v-col>
      <v-col 
        class="recommendBoards"
        sm= "6"
      >
        추천 게시물
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
  import Vue from 'vue'

  export default Vue.extend({
    data() {
      return {
        categories: [
          "전자기기",
          "화장품",
          "책"
        ]
      }
    }
  })
</script>

<style lang="scss">
.searchBar {
}
.searchBar .v-select {
  display: inline-block;
  width: 150px;
}
.searchBar input {
  width: 50%;
  padding: 10px;
  padding-left: 5%;
}
</style>