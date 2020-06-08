<template>
  <v-row class="mainSection" justify="center">
      <v-col
        class="regionTrade"
        cols="12"
        sm="6"
        style="position: relative;"
      >
        <span class="ribbon2"><span>{{userAddress}}</span></span>
        <!-- <p class="ribbon">
          <span class="text"><strong class="bold">지역별 현황</strong></span>
        </p> -->
        <v-simple-table>
          <template v-slot:default>
            <tbody>
              <tr
                class="regionBoard"
                v-for="(region,i) in regionBoards"
                :key="region+i">
                  <td width="20%" class="forTd">
                    {{'0'+(i+1)}}
                  </td>
                  <router-link 
                    :to="{name:'Detail', params:{boardId:region.id}}"
                    >
                    <td width="80%" class="forTd2">
                      <section class="mb-4">{{region.productName}}</section>
                      <p>{{region.category}}</p>
                      <p v-if="region.priceDay">{{region.priceDay}}/일</p>
                      <p v-else-if="region.priceWeek">{{region.priceWeek}}/주</p>
                      <p v-else-if="region.priceMonth">{{region.priceMonth}}/월</p>
                    </td>
                  </router-link>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
      <v-col
        v-if="true"
        class="recentBoards"
        cols="12"
        sm="6"
      >
        <v-card
          max-width="444"
          class="mx-auto"
        >
          <span class="ribbon3"><span>최근 게시글</span></span>
          <v-system-bar lights-out></v-system-bar>
          <v-carousel
            cycle
            hide-delimiters
            height="400"
          >
            <v-carousel-item
              v-for="(recent, i) in recentBoards"
              :key="recent+i"
            >
              <router-link 
                :to="{name:'Detail', params:{boardId:recent.id}}"
                >
                <v-sheet
                  v-if="recent.imagesPath"
                  height="300"
                  tile
                  :style="{'background-image': 'url(http://13.125.209.188:8080/'+recent.imagesPath[0]+')'}"
                  style="background-size: cover;"
                  class="imageSheet"
                >
                  <v-row
                    class="fill-height"
                    align="center"
                    justify="center"
                  >
                    <!-- <div
                      class="display-1"
                    >{{ recent.product }}</div> -->
                  </v-row>
                </v-sheet>
                <div class="carouselBottom pa-3">
                  <div class="overline mb-1">{{ recent.category }}</div>
                  <v-list-item-title class="mb-1 productName">{{ recent.productName }}</v-list-item-title>
                  <v-list-item-subtitle>
                    <span v-if="recent.priceDay">
                      {{ recent.priceDay }}/일
                    </span>
                    <span v-else-if="recent.priceWeek">
                      {{ recent.priceWeek }}/주
                    </span>
                    <span v-else-if="recent.priceMonth">
                      {{ recent.priceMonth }}/월
                    </span>
                  </v-list-item-subtitle>
                  <div class="cir4">
                    <div class="cir5"></div>
                  </div>
                </div>
              </router-link>
            </v-carousel-item>
          </v-carousel>
          
        </v-card>
      </v-col>
      <v-col 
        v-else
        class="recommendBoards"
        cols="12"
        sm="6"
      >
        <span class="ribbon3"><span>추천 게시글</span></span>
      </v-col>
    </v-row>
</template>

<script lang="ts">
  import { Component, Prop, Vue } from 'vue-property-decorator'

  @Component
  export default class MainList extends Vue{
    @Prop() regionBoards!: object[]
    @Prop() recentBoards!: object[]
    @Prop() userAddress!: string
  }
  
</script>

<style lang="scss">
.theme--light.v-data-table {
  // background-color:rgba(255, 255, 255, 0.822) !important;
  tr:hover {
    // background-color: rgb(248, 237, 255) !important;
    background-image: radial-gradient(closest-side, #fffaba, #fff) !important;
  }
}
.forTd {
  text-align: center;
  color: #560875;
  font-size: 3rem !important;
  text-shadow: 1px 1px 1px rgb(255, 249, 214);
  position: relative;
  &:after {
    background: linear-gradient(-45deg, #000000 3px, transparent 0), linear-gradient(45deg, #000 3px, transparent 0);
    background-position: left-bottom;
    background-repeat: repeat-x;
    background-size: 6px 6px;
    content: " ";
    display: block;
    position: absolute;
    bottom: 0px;
    left: 0px;
    width: 100%;
    height: 5px;
  }
}
.forTd2 {
  display: table;
  line-height: .5em;
  width: 90%;
  position: relative;
  font-weight: 900;
  &:after {
    background: linear-gradient(-45deg, #000 3px, transparent 0), linear-gradient(45deg, #000 3px, transparent 0);
    background-position: left-bottom;
    background-repeat: repeat-x;
    background-size: 6px 6px;
    content: " ";
    display: block;
    position: absolute;
    bottom: 0px;
    left: 0px;
    width: 100%;
    height: 5px;
  }
}
.forTd2 > section {
  font-weight: 800;
  font-size: 1.1rem;
  padding-top: 20px;
}
.recentBoards .v-card {
  border: 1px solid purple;
  .v-sheet {
    border-bottom: 1px solid rgb(253, 247, 255);
  }
  &:before {
    content: ' ';
    position: absolute;
    right: -90px;
    bottom: 50px;
    z-index: -1;
    width: 100px;
    height: 100px;
    border-radius: 100px;
    background-color: #560875;
  }
  &:hover {
    &:after {
      content: '보러가기';
      font-family: 'SangSangAnt'; 
      position: absolute;
      color: #ffbf00;
      text-shadow: 0px 0px 1px #ffbf00;
      right: 10px;
      bottom: 50px;
      font-size: 3em;
      // font-weight: 900;
    }
    .cir4 {
      position: absolute;
      width: 220px;
      height: 220px;
      right: -50px;
      bottom: -30px;
      border: 2px dashed #8c28b4;
      border-radius: 200px;
    }
  }
}
.v-carousel {
  position:relative;
  &:after {
    content: '';
    // background-image: url('../../assets/images/bottomBorder.png');
    // background-size: cover;
    // width: 78%;
    // height: 50px;
    // position: absolute;
    // left: 50px;
    // bottom: 90px;
  }
}
.recentBoards .productName {
  font-size: 20px;
  font-weight: 900;
}
.carouselBottom {
  display: block !important;
  width: 100%;
  border-top: 1px solid #f9eaff;
  color: black !important;
  position: relative;
  
}
.v-application .elevation-24 {
  box-shadow: none !important;
}

.ribbon2 {
  width: 70px;
  padding: 15px 10px 40px;
  position: absolute;
  top: -6px;
  right: 40px;
  font-size: 1.1em;
  font-weight: 600;
  text-align: center;
  border-top-left-radius: 3px;
  background: #fff8cf;
  box-shadow: 1px 1px 1px rgb(235, 235, 235);
  z-index: 2;
}
.ribbon2:before {
  height: 0;
  width: 0;
  right: -5.5px;
  top: 0.1px;
  border-bottom: 8px solid #FFEC81;
  border-right: 6px solid transparent;
  z-index: 2;
}
.ribbon2:before, .ribbon2:after {
  content: "";
  position: absolute;
  z-index: 2;
}
.ribbon2:after {
  height: 0;
  width: 0;
  bottom: -29.5px;
  left: 0;
  z-index: 2;
  border-left: 35px solid #fff8cf;
  border-right: 35px solid #fff8cf;
  border-bottom: 30px solid transparent;
  box-shadow: 2px 0px 1px -1px rgb(235, 235, 235);
}
.ribbon3 {
  z-index: 2;
  width: 130px;
  height: 50px;
  line-height: 50px;
  padding-left: 15px;
  position: absolute;
  left: -8px;
  top: 20px;
  font-size: 1.1em;
  font-weight: 600;
  background: #f9eaff;
  // border-bottom: 2px dashed #df4c73;
  // box-shadow: 1px 1px 1px rgb(235, 235, 235);
}
.ribbon3:before, .ribbon3:after {
  content: "";
  position: absolute;
}
.ribbon3:before {
  height: 0;
  width: 0;
  top: -8.5px;
  left: 0.1px;
  border-bottom: 9px solid #8c28b4;
  border-left: 9px solid transparent;
}
.ribbon3:after {
  height: 0;
  width: 0;
  right: -14.5px;
  border-top: 25px solid transparent;
  border-bottom: 25px solid transparent;
  border-left: 15px solid #f9eaff;
}


</style>