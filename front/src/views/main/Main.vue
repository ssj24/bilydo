<template>
  <v-container>
    <v-row justify="center" class="searchSection">
      <v-col cols="8" class="searchBar">
        <v-select
          :items="categories"
          label="카테고리"
          color="#8c28b4"
        ></v-select>
        <input type="text" class="searchInput">
        <button @click="search">
          <v-icon class="ma-2 magnifyIcon">mdi-magnify</v-icon>
        </button>
      </v-col>
    </v-row>
    <span v-if="$store.state.mainList">
      <MainList :regionBoards="regionBoards" :recentBoards="recentBoards"></MainList>
    </span>
    <span v-else>
      <Result :regionBoards="regionBoards"></Result>
    </span>
  </v-container>
</template>

<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator'
  import "@/assets/css/basic.css"
  import MainList from "@/views/main/MainList.vue"
  import Result from "@/views/board/Result.vue"

  @Component({
    components: {
      MainList,
      Result
    }
  })
  export default class Main extends Vue {
    private categories: string[] = [
      "전자기기",
      "화장품",
      "책"
    ];
    private regionBoards: object[] = [
      {
        product: "노트북거치대",
        category: "가구",
        price: 2000,
        duration: "개월",
        pic: "https://cdn.vuetifyjs.com/images/cards/plane.jpg"
      },
      {
        product: "러닝파이썬",
        category: "책",
        price: 10000,
        duration: "개월",
        pic: "https://cdn.vuetifyjs.com/images/cards/store.jpg"
      },
      {
        product: "아이패드 6세대",
        category: "전자기기",
        price: 10000,
        duration: "주",
        pic: "https://cdn.vuetifyjs.com/images/cards/plane.jpg"
      },
      {
        product: "찬장",
        category: "가구",
        price: 5000,
        duration: "개월",
        pic: "https://cdn.vuetifyjs.com/images/cards/store.jpg"
      },
      {
        product: "한성 키보드 gk888b",
        category: "전자기기",
        price: 10000,
        duration: "개월",
        pic: "https://cdn.vuetifyjs.com/images/cards/plane.jpg"
      },
    ];
    private recentBoards: object[] = [
      {
        product: "러닝파이썬",
        category: "책",
        price: 10000,
        duration: "개월",
        pic: "https://cdn.vuetifyjs.com/images/cards/store.jpg"
      },
      {
        product: "아이패드 6세대",
        category: "전자기기",
        price: 10000,
        duration: "주",
        pic: "https://cdn.vuetifyjs.com/images/cards/plane.jpg"
      },
      {
        product: "찬장",
        category: "가구",
        price: 5000,
        duration: "개월",
        pic: "https://cdn.vuetifyjs.com/images/cards/store.jpg"
      },
      {
        product: "한성 키보드 gk888b",
        category: "전자기기",
        price: 10000,
        duration: "개월",
        pic: "https://cdn.vuetifyjs.com/images/cards/plane.jpg"
      },
    ];
    public search(): void {
      this.$store.commit('listOff');
    }
  }
</script>

<style lang="scss">
.searchBar .v-select {
  display: inline-block;
  width: 150px;
}
.searchBar .searchInput {
  width: 60%;
  padding: 20px 10px 4px 5%;
  border-bottom: 1px solid #ccc;
  display: relative;
  color: black;
  font-weight: 900;
}
.magnifyIcon {
  display: relative;
  left: -30px;
}
.searchSection {
  margin: 2rem 0;
}

</style>