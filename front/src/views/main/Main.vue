<template>
  <v-container>
    <v-row justify="center" class="searchSection">
      <v-col cols="8" class="searchBar">
        <v-select
          :items="categories"
          label="카테고리"
          color="#8c28b4"
          v-model="selectCate"
        ></v-select>
        <span class="productField">
          <input type="text" class="searchInput" v-model="searchedName" v-on:input="getBoardInfo($event)">
          <button @click="search">
            <v-icon class="ma-2 magnifyIcon">mdi-magnify</v-icon>
          </button>
          <ul class="hide" v-bind:class="{ show: hasSearchValue}">
            <li v-for="searchCdd in searchCdds" v-bind:key = "searchCdd.productName" v-on:click="selectProduct(searchCdd.productName, searchCdd.cnt)">
              <span>{{ searchCdd.productName }}({{searchCdd.cnt}})</span>
            </li>
          </ul>
        </span>

      </v-col>
    </v-row>
    <span v-if="$store.state.mainList">
      <MainList :regionBoards="regionBoards" :recentBoards="recentBoards"></MainList>
    </span>
    <span v-else>
      <Result :category="selectCate" :listSize="listSize" :productName="searchedName" :regionBoards="regionBoards"></Result>
    </span>
  </v-container>
</template>

<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator'
  import "@/assets/css/basic.css"
  import MainList from "@/views/main/MainList.vue"
  import Result from "@/views/board/Result.vue"
  import baseURL from "@/base-url"

  @Component({
    components: {
      MainList,
      Result
    }
  })
  export default class Main extends Vue {
    private searchedName = "";
    private hasSearchValue = false;
    private searchCdds: object[] = [];
    private selectCate = "";
    private listSize = 0;

    private categories: string[] = [
      "전체",
      "패션의류/잡화",
      "뷰티",
      "출산/유아동",
      "식품",
      "주방용품",
      "생활용품",
      "홈인테리어",
      "가전디지털",
      "스포츠/레저",
      "자동차용품",
      "도서/음반/DVD",
      "완구/취미",
      "문구/오피스",
      "반려동물용품",
      "헬스/건강식품",
      "기타"
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
      // 1. 백엔드에 (카테고리, 검색이름) 보내서 결과값을 boardList에 저장
      // 2. boardList을 25번줄 regionBoard에 넣어줌
      if(this.searchedName == ''){
        this.listSize = 0;
        baseURL.get('/boards/name', {
        params: {
          productName:' '
        }
      }).then((response) => {
        for (let i = 0; i < response.data.length; i++) {
          this.listSize += response.data[i].cnt;          
        }
        console.log("listSize1 : ",this.listSize);
        this.$store.commit('listOff');
        });
      }
    }
    public selectProduct(productName: string, cnt: number): void{
      // vuex에 productName입력
      this.searchedName = productName;
      this.hasSearchValue = false;
      this.listSize = cnt;
      console.log("selectProduct : ", productName);
    }

    public async getBoardInfo() {
      if(this.searchedName == ''){
        this.hasSearchValue = false;
        return;
      }

      this.hasSearchValue = true;
      
      baseURL.get('/boards/name', {
        params: {
          productName: this.searchedName
        }
      }).then((response) => {
        this.searchCdds = response.data;
        console.log("searchCdds : ", this.searchCdds);
      });
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
.productField {
  position: relative;
  ul {
    padding: 0px !important;
  }
}
.hide {
  display: none;
}

.show{
  display: inherit;
  position: absolute;
  top: 30px;
  left: 0px;
  width: 90%;
  height: 156px;
  overflow-y: auto;
  z-index: 10;
    li {
    margin-top: -1px;
    padding: 0 20px;
    width: 100%;
    height: 40px;
    background-color: #fff;
    box-sizing: border-box;
    border: 1px solid #888;
    outline: none;
    font-size: 16px;
    line-height: 40px;
    cursor: pointer;
    &:hover, &.sel {
      background-color: darken(#fff, 5%);
    }
  }
}

</style>