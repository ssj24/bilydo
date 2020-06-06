<template>
<div class="mainWrapper">
  <div class="cir1">
    <div class="cir2"></div>
  </div>
  <div class="cir3"></div>
  <v-container>
    
    <v-row justify="center" class="searchSection">
      <v-col cols="9" class="searchBar">
        <v-row justify="center">
          <v-col cols="6" sm="4" align-self="center">
            <v-select
              :items="categories"
              label="카테고리"
              color="#8c28b4"
              v-model="selectCate"
            ></v-select>
          </v-col>
          <v-col cols="11" sm="8" align-self="center">
            <span class="productField">
              <input type="text" placeholder="제품명" class="searchInput" v-model="searchedName" v-on:input="getBoardInfo($event)">
              <button @click="search" class="ml-n6">
                <!-- <v-icon class="ma-2 magnifyIcon">🔍</v-icon> -->
                🔍
              </button>
              <ul class="hide" v-bind:class="{ show: hasSearchValue}">
                <li v-for="searchCdd in searchCdds" v-bind:key = "searchCdd.productName" v-on:click="selectProduct(searchCdd.productName, searchCdd.cnt)">
                  <span>{{ searchCdd.productName }}({{searchCdd.cnt}})</span>
                </li>
              </ul>
            </span>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
      <span v-if="$store.state.mainList">
        <MainList :regionBoards="regionBoards" :recentBoards="recentBoards" :userAddress="userAddress"></MainList>
      </span>
      <span v-else>
        <Result :category="selectCate" :listSize="listSize" :productName="searchedName" :regionBoards="regionBoards"></Result>
      </span>
  </v-container>
  
</div>
</template>

<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator'
  import "@/assets/css/basic.css"
  import MainList from "@/views/main/MainList.vue"
  import Result from "@/views/board/Result.vue"
  import baseURL from "@/base-url"
  import cookie from "@/cookie"

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
    private userAddress = '대전시 유성구';
    private userLocation = '대전시 유성구';

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

    private regionBoards: object[] = [];
    private recentBoards: object[] = [];
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
        this.$store.commit('listOff');
        });
      } else {
        this.$store.commit('listOff');
      }
    }
    public selectProduct(productName: string, cnt: number): void{
      // vuex에 productName입력
      this.searchedName = productName;
      this.hasSearchValue = false;
      this.listSize = cnt;
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
      });
    }
    created() {
      const Location = cookie.cookieLocation()
      if (Location) {
        const locations = Location.split(" ");
        this.userAddress = locations.slice(0, 2).join('\n');
        this.userLocation = locations.slice(0, 2).join(' ');
      }
      if (this.userLocation) {
        baseURL('/boards?location='+this.userLocation+'&page=0&size=5')
        .then(res => {
          this.regionBoards = res.data.content;
        })
      }
      baseURL('/boards?page=0&size=10')
        .then(res => {
          this.recentBoards = res.data.content;
        })
    }

  }
</script>

<style lang="scss">
.mainWrapper {
  // background: linear-gradient(180deg, #fff 0%, rgb(234, 193, 247) 100%);
  width: 100%;
  height: 100%;
  position: relative;
  overflow: hidden;
  transform: translate3d(0, 0, 0);
  
  .cir1 {
    position: absolute;
    width: 400px;
    height: 400px;
    right: -80px;
    top: -200px;
    border-radius: 200px;
    background: #e3c5fc;
    .cir2 {
      z-index: -1;
      position: absolute;
      width: 180px;
      height: 180px;
      left: -100px;
      bottom: -30px;
      border: 2px solid #8c28b4;
      border-radius: 200px;
    }
  }
  .cir3 {
    position: absolute;
    width: 500px;
    height: 500px;
    border-radius: 350px;
    bottom: -100px;
    left: -100px;
    background: #8c28b4;
  }
}
.searchBar {
  position: relative;
  &:before {
    content: ' ';
    position: absolute;
    top: -100px;
    width: 50px;
    height: 50px;
    border-radius: 50px;
    // border: 2px dotted #530288;
    border: 2px dotted #ffd102;
  }
  &:after {
    content: ' ';
    position: absolute;
    right: 70px;
    bottom: -500px;
    z-index: -1;
    width: 250px;
    height: 250px;
    border-radius: 250px;
    background: #fae37b;
  }
}
.searchBar .v-select {
  display: inline-block;
  margin-top: 10px;
  width: 100%;
}
.searchBar .searchInput {
  width: 100%;
  border-bottom: 1px solid rgb(0, 0, 0);
  display: relative;
  font-weight: 900 !important;
  font-size: 1.4em !important;
}
.theme--light.v-select .v-select__selection--comma {
  color: black !important;
  font-weight: 900 !important;
  font-size: 1.3em !important;
}
.theme--light.v-input input {
  color: black !important;
  font-weight: 900 !important;
}
.theme--light.v-label {
  color: rgb(92, 92, 92) !important;
  font-weight: 600 !important;
}
.searchSection {
  margin: 2rem 0;
}
.productField {
  position: relative;
  ul {
    position: absolute;
    width: 100%;
    top: 25px;
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
        background-color: rgb(245, 234, 255);
        transform: scale(1, 1.05);
      }
      span {
        font-weight: 900;        
      }
    }
}

</style>