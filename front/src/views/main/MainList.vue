<template>
  <v-row class="mainSection">
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
                    :to="{name:'Detail', params:{boardId:i}}"
                    >
                    <td width="80%" class="forTd2">
                      <section class="mb-4">{{region.product}}</section>
                      <p>{{region.category}}</p>
                      <p>{{region.price}}/{{region.duration}}</p>
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
          elevation="24"
          max-width="444"
          class="mx-auto"
        >
          <span class="ribbon3"><span>최근 게시글</span></span>
          <v-system-bar lights-out></v-system-bar>
          <v-carousel
            hide-delimiters
            height="400"
          >
            <v-carousel-item
              v-for="(recent, i) in recentBoards"
              :key="recent+i"
            >
              <v-sheet
              v-if="recent.imagesPath"
                height="300"
                tile
                :style="{'background-image': 'url('+recent.imagesPath[0]+')'}"
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
              <router-link 
                :to="{name:'Detail', params:{boardId:recent.id}}"
                >
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
  import cookie from '@/cookie'

  @Component
  export default class MainList extends Vue{
    @Prop() regionBoards!: object[]
    @Prop() recentBoards!: object[]
    private userAddress = '';
    created() {
      const Location = cookie.cookieLocation()
        if (Location) {
          const locations = Location.split(" ");
          this.userAddress = locations.slice(0, 2).join('\n');
        }
    }
  }
</script>

<style lang="scss" scoped>
.regionBoard {
  border-bottom: 1px solid black !important;
}
.forTd {
  text-align: center;
  color: #8c28b4;
  font-size: 2rem !important;
  text-shadow: 1px 1px 1px rgb(165, 165, 255);
}
.forTd2 {
  display: table;
  line-height: .5em;
  width: 90%;
}
.forTd2 > section {
  font-family: "Cafe24Dangdanghae";
  font-size: 1.1rem;
  padding-top: 20px;
}
.recentBoards .productName {
  font-size: 20px;
  font-weight: 900;
}

.ribbon1 {
  z-index: 2;
  position: absolute;
  top: -6.1px;
  right: 10px;
}
.ribbon1:after {
  position: absolute;
  content: "";
  width: 0;
  height: 0;
  border-left: 60px solid transparent;
  border-right: 60px solid transparent;
  border-top: 10px solid #e2f0f9;
}
.ribbon1 span {
  position: relative;
  display: block;
  text-align: center;
  background: #e2f0f9;
  font-size: 1em;
  font-weight: 500;
  line-height: 1;
  padding: 12px 8px 10px;
  border-top-right-radius: 8px;
  width: 120px;
}
.ribbon1 span:before, .ribbon1 span:after {
  position: absolute;
  content: "";
}
.ribbon1 span:before {
  height: 6px;
  width: 6px;
  left: -6px;
  top: 0;
  background: #e2f0f9;
}
.ribbon1 span:after {
  height: 6px;
  width: 8px;
  left: -8px;
  top: 0;
  border-radius: 8px 8px 0 0;
  background: #285fb4;
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
}
.ribbon2:before {
  height: 0;
  width: 0;
  right: -5.5px;
  top: 0.1px;
  border-bottom: 8px solid #FFEC81;
  border-right: 6px solid transparent;
}
.ribbon2:before, .ribbon2:after {
  content: "";
  position: absolute;
}
.ribbon2:after {
  height: 0;
  width: 0;
  bottom: -29.5px;
  left: 0;
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
.ribbon4 {
  position: absolute;
  top: 15px;
  padding: 8px 10px;
  background: #00B3ED;
  box-shadow: -1px 2px 3px rgba(0,0,0,.3);
}
.ribbon4:before, .ribbon4:after {
  content: "";
  position: absolute;
}
.ribbon4:before {
  width: 7px;
  height: 100%;
  top: 0;
  left: -6.5px;
  padding: 0 0 7px;
  background: inherit;
  border-radius: 5px 0 0 5px;
}
.ribbon4:after {
  width: 5px;
  height: 5px;
  bottom: -5px;
  left: -4.5px;
  background: lightblue;
  border-radius: 5px 0 0 5px;
 }
.ribbon5 {
  display: block;
  width: calc(100% + 20px);
  height: 50px;
  line-height: 50px;
  text-align: center;
  margin-left: -10px;
  margin-right: -10px;
  background: #EDBA19;
  position: relative;
  top: 20px;
}
.ribbon5:before, .ribbon5:after {
  content: "";
  position: absolute;
}
.ribbon5:before {
  height: 0;
  width: 0;
  bottom: -10px;
  left: 0;
  border-top: 10px solid #cd8d11;
  border-left: 10px solid transparent;
}
.ribbon5:after {
  height: 0;
  width: 0;
  right: 0;
  bottom: -10px;
  border-top: 10px solid #cd8d11;
  border-right: 10px solid transparent;
}
.wrap {
  width: 100%;
  height: 188px;
  position: absolute;
  top: -8px;
  left: 8px;
  overflow: hidden;
}
.wrap:before, .wrap:after {
  content: ""; 
  position: absolute;
}
.wrap:before {
  width: 40px;
  height: 8px;
  right: 100px;
  background: #4D6530;
  border-radius: 8px 8px 0px 0px;
}
.wrap:after {
  width: 8px;
  height: 40px;
  right: 0px;
  top: 100px;
  background: #4D6530;
  border-radius: 0px 8px 8px 0px;
}
.ribbon6 {
  width: 200px;
  height: 40px;
  line-height: 40px;
  position: absolute;
  top: 30px;
  right: -50px;
  z-index: 2;
  overflow: hidden;
  -webkit-transform: rotate(45deg);
  transform: rotate(45deg);
  border: 1px dashed;
  box-shadow:0 0 0 3px #57DD43,  0px 21px 5px -18px rgba(0,0,0,0.6);
  background: #57DD43;
  text-align: center;
}
@media (min-width: 500px) {
  .ribbons-wrapper {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
  }
  .ribbon {
    width: 48%;
  }
}

.ribbon{
  font-size:20px;
  position:relative;
  display:inline-block;
  text-align:center;
  width: 100%;
}
.text{
  display:inline-block;
  padding:0.5em 2em;
  width: 100%;
  line-height:1.2em;
  background: #e2f0f9;
  position:relative;
}
.ribbon:before,
.text:before,.text:after {
  content:'';
  position:absolute;
  border-style:solid;
}
.ribbon:before{
  top:0.3em; left:0.2em;
  width:100%; height:100%;
  border:none;
  background:#e2f0f9;
  z-index:-2;
}
.text:before{
  bottom:100%; left:0;
  border-width: .5em .7em 0 0;
  border-color: transparent #286fb4 transparent transparent;
}
.text:after{
  top:100%; right:0;
  border-width: .5em 2em 0 0;
  border-color: #286fb4 transparent transparent transparent;
}
// .ribbon:after, .bold:before{
//   top:0.5em; right: -5em;
//   border-width: 1.1em 1em 1.1em 3em;
//   border-color: #ff0000 transparent #e2f0f9 #e2f0f9;
//   z-index:-1;
// }
// .bold:before{
//   border-color: #e2f0f9 transparent #e2f0f9 #e2f0f9;
//   top:0.7em;
//   right:-2.3em;
// }
.carouselBottom {
  display: block !important;
  background: white;
  width: 100%;
  color: black !important;
}
</style>