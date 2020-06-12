<template>
  <v-container class="articleWrap">
    <v-form ref="articleForm" v-model="articleValid" lazy-validation @submit.prevent>
      <v-row justify="center">
        <v-col cols="11" sm="10" class="articleMain page">
          <v-row align="center">
            <v-col cols="6" sm="3">
              <p class="articleTitle articleSt">
                제품명
              </p>
            </v-col>
            <v-col cols="12" sm="9" class="articleContent">
              <v-row style="padding: 10px;">
                <v-col cols="3">
                  <v-select label="카테고리" required color="#f66" 
                    v-model="category" 
                    :items="categories"
                    :rules="requiredRules"
                    >
                  </v-select>
                </v-col>
                <v-col cols="1">
                </v-col>
                <v-col cols="8" class="productName">
                  <v-text-field label="제품명" required color="#f66" v-model="product" v-on:input="getBoardInfo($event)" :rules="requiredRules">
                  </v-text-field>
                  <ul class="hide" v-bind:class="{ show: hasSearchValue}">
                    <li v-for="searchCdd in searchCdds" v-bind:key = "searchCdd.productName" v-on:click="selectProduct(searchCdd.productName)">
                      <span>{{ searchCdd.productName }}({{searchCdd.cnt}})</span>
                    </li>
                  </ul>
                </v-col> 
              </v-row>

            </v-col>
          </v-row>
          <v-row align="center">
            <v-col cols="6" sm="3">
              <p class="articleTitle articleNd">
                사용 기간
              </p>
            </v-col>
            <v-col cols="12" sm="9" class="articleContent">
              <v-text-field color="#f92" v-model="used" :rules="requiredRules">
                
              </v-text-field>
            </v-col>
          </v-row>
          <v-row align="center">
            <v-col cols="6" sm="3">
              <p class="articleTitle articleRd">
                주소
              </p>
            </v-col>
            <v-col cols="12" sm="9" class="articleContent">
              <v-row class="pl-4" align="center">
                <v-col cols="12" sm="8">
									<v-text-field
										type="text" 
										class="input"
										color="black" 
										label="주소"
										v-model="address"
										:rules="contactRules"
										required
										disabled
										>
									</v-text-field>
								</v-col>
								<v-col>
									<div class="text-center">
                    <v-dialog
                      v-model="dialog"
                      width="500"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          color="#f66"
                          dark
                          v-bind="attrs"
                          v-on="on"
                        >
                          주소 찾기
                        </v-btn>
                      </template>

                      <v-card>
                        <v-card-title
                          class="headline"
                          style="background-color: #f66; color: #fff;"
                          primary-title
                        >
                          주소: {{address}}
                          <v-spacer></v-spacer>
                          <span @click="dialog = false" style="cursor: pointer;">
                            확인
                          </span>
                        </v-card-title>
                        <vue-daum-postcode @complete="address = $event.address"/>
                      </v-card>
                    </v-dialog>
                  </div>
								</v-col>
              </v-row>
              
            </v-col>
          </v-row>
          <v-row align="center">
            <v-col cols="6" sm="3">
              <p class="articleTitle articleNd">
                대여 가능 기간
              </p>
            </v-col>
            <v-col cols="12" sm="9" class="articleContent">
              <v-row>
                <v-col cols="12" sm="6" md="4">
                  <v-menu
                    ref="startMenu"
                    v-model="startMenu"
                    :close-on-content-click="false"
                    :return-value.sync="startDate"
                    transition="scale-transition"
                    offset-y
                    min-width="290px"
                  >
                    <template v-slot:activator="{ on }">
                      <v-text-field
                        v-model="startDate"
                        label="시작일"
                        readonly
                        v-on="on"
                        color="#f92"
                        :rules="requiredRules"
                      ></v-text-field>
                    </template>
                    <v-date-picker v-model="startDate" no-title scrollable color="#f45e61">
                      <v-spacer></v-spacer>
                      <v-btn text color="#f45e61" @click="$refs.startMenu.save(startDate)">OK</v-btn>
                    </v-date-picker>
                  </v-menu>
                </v-col>
                <v-col cols="12" sm="6" md="4">
                  <v-menu
                    ref="endMenu"
                    v-model="endMenu"
                    :close-on-content-click="false"
                    :return-value.sync="endDate"
                    transition="scale-transition"
                    offset-y
                    min-width="290px"
                  >
                    <template v-slot:activator="{ on }">
                      <v-text-field
                        v-model="endDate"
                        label="종료일"
                        readonly
                        v-on="on"
                        color="#f92"
                        :rules="requiredRules"
                      ></v-text-field>
                    </template>
                    <v-date-picker v-model="endDate" no-title scrollable color="#f45e61">
                      <v-spacer></v-spacer>
                      <v-btn text color="#f45e61" @click="$refs.endMenu.save(endDate)">OK</v-btn>
                    </v-date-picker>
                  </v-menu>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
          <v-row align="center">
            <v-col cols="6" sm="3">
              <p class="articleTitle articleRd">
                기간별 대여가격
              </p>
            </v-col>
            <v-col cols="12" sm="9" class="articleContent articleDuration">
              <v-row>
                <v-col cols="6">
                  <v-text-field type="number" min="0" step="100" color="#fc2" v-model="priceDay">
                  </v-text-field>
                </v-col>
                <v-col>
                  <span style="margin: 0 10px;">
                    * 일
                  </span>
                  <v-checkbox color="#f45e61" class="checkDay" v-model="checkboxDay" label="" style="display: inline-block;"></v-checkbox>
                </v-col>
              </v-row>
              <v-row>
                <v-col cols="6">
                  <v-text-field type="number" min="0" step="100" color="#fc2" v-model="priceWeek">
                  </v-text-field>
                </v-col>
                <v-col>
                  <span style="margin: 0 10px;">
                    * 주
                  </span>
                  <v-checkbox color="#f45e61" class="checkWeek" v-model="checkboxWeek" label="" style="display: inline-block;"></v-checkbox>
                </v-col>
              </v-row>
              <v-row>
                <v-col cols="6">
                  <v-text-field type="number" min="0" step="100" color="#fc2" v-model="priceMonth">
                  </v-text-field>
                </v-col>
                <v-col>
                  <span style="margin: 0 10px;">
                    * 월
                  </span>
                  <v-checkbox color="#f45e61" class="checkMonth" v-model="checkboxMonth" label="" style="display: inline-block;"></v-checkbox>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
          <v-row align="center">
            <v-col cols="6" sm="3">
              <p class="articleTitle articleNd">
                제품 설명
              </p>
            </v-col>
            <v-col cols="12" sm="9" class="articleContent">
              <v-textarea
                required
                outlined
                label="자세한 설명 부탁드립니다^^"
                v-model="description"
                color="#f92"
                :rules="requiredRules"
              ></v-textarea>
              
            </v-col>
          </v-row>
          <v-row align="center">
            <v-col cols="6" sm="3">
              <p class="articleTitle articleRd">
                제품 사진
              </p>
            </v-col>
            <v-col cols="12" sm="9" class="articleContent">
              <div>
                <input 
                  type="file"
                  required
                  :rules="requiredRules"
                  multiple 
                  @change="onFileChange($event.target)"
                  accept="image/*" 
                  style="display: block; margin-bottom: 10px;"/>
                <div v-for="(image, key) in images" :key="key" id="preview" style="display: inline-block;">
                  <div class="imageMain">
                    <img :ref="'image'" :id="'test' + key" />
                    <button @click.prevent="removeImage(key)" class="removeBtn">x</button>
                  </div>
                </div>
              </div>
            </v-col>
          </v-row>
          <v-row justify="center" class="buttons">
            <button class="articleSubmit draw meet" @click="submitArticle">
              작성
            </button>
          </v-row>
        </v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator'
  import baseURL from "@/base-url"
  import cookie from "@/cookie"

  @Component
  export default class Article extends Vue{
    private articleValid = false;
    private requiredRules = [
      (v: Vue) => !!v || '입력해주세요',
    ];
    private product = '';
    private category = '';
    private used = '';
    private address = '';
    private dialog = false;
    private startDate = new Date().toISOString().substr(0, 10);
    private endDate = new Date(+new Date() + (86400000 * 7)).toISOString().substr(0, 10);
    private startMenu = false;
    private endMenu = false;
    private checkboxDay = true;
    private checkboxWeek = true;
    private checkboxMonth = true;
    private priceDay = 0;
    private priceWeek = 0;
    private priceMonth = 0;
    private description = '';
    private images: File[] = [];
    private hasSearchValue = false;
    private searchCdds: object[] = [];
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
    // private userId: cookie.cookieId();

    public previewImg(): void {
      for (let i = 0; i < this.images.length; i++) {
        const reader = new FileReader();
        reader.onload = () => {
          const test = document.getElementById("test"+i)
          const result = reader.result;
          if (test && result) {
            if (typeof(result) != 'string') {
              result.toString()
            } else {
              test.setAttribute('src', result) 
            }
          }
        }
        reader.readAsDataURL(this.images[i]);
      }
    }
    public onFileChange(target: HTMLInputElement): void {
      const files = target.files;
      if (files) {
        for (let i = 0; i < files.length; i++) {
          this.images.push(files[i]);
        }
        this.previewImg()
      }
    }
      
    public removeImage(key: number): void {
      this.images.splice(key, 1);
      this.previewImg()
    }
    public selectProduct(productName: string): void{
      // vuex에 productName입력
      this.product = productName;
      this.hasSearchValue = false;
    }

    public async getBoardInfo() {
      if(this.product == ''){
        this.hasSearchValue = false;
        return;
      }

      this.hasSearchValue = true;
      
      baseURL.get('/boards/name', {
        params: {
          productName: this.product
        }
      }).then((response) => {
        this.searchCdds = response.data;
      });
    }
    public submitArticle(): void {
      if ((this.$refs.articleForm as Vue & { validate: () => boolean }).validate()) {
        const formData = new FormData();
        for (let i = 0; i < this.images.length; i++) {
          formData.append("files", this.images[i])
        }
        let pDay = 0;
        let pWeek = 0;
        let pMonth = 0;
        if (this.checkboxDay) {
          pDay = this.priceDay;
        }
        if (this.checkboxWeek) {
          pWeek = this.priceWeek;
        }
        if (this.checkboxMonth) {
          pMonth = this.priceMonth;
        }
        const data = `{
          productName : "${this.product}", category: "${this.category}", producerId : "${cookie.cookieId()}", usedTime : "${this.used}", state : "READY", location : "${this.address}", borrowableSrt: "${this.startDate}T00:00:00", borrowableEnd: "${this.endDate}T00:00:00", subscript: "${this.description}", priceDay: "${pDay}", priceWeek: "${pWeek}", priceMonth: "${pMonth}"
        }`
        formData.append("boardString", data)
        
        baseURL.post('/boards/board', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          }
        }).then(() => {
          alert("물품이 등록되었습니다")
          this.$store.commit('listOff');
          this.$router.push({
            name: "Main"
          })
        }).catch(()=>{
          alert("잘못된 시도입니다.")
        })
      }
    }
  }
</script>

<style lang="scss">
  .articleWrap {
    overflow: hidden;
    
  }

  #preview {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .imageMain img {
    width: 75px;
    height: 75px;
  }
  
  .articleMain {
    // background-color: rgb(239, 220, 255);
    margin: 50px auto;
    z-index: 2;
    .articleTitle {
      color: #fff;
      padding: 5%;
      text-align: center;
      object-fit: cover;
      text-shadow: 0px 0px 2px #999;
      font-weight: 900;
    }
    .articleSt {
      background: #f66;
      border-radius: 50% 50% 50% 70%/50% 50% 70% 60%;
    }
    .articleNd {
      background: #f92;
      color: #000;
      border-radius: 80% 30% 50% 50%/50%;
    }
    .articleRd {
      background: #fc2;
      color: #000;
      border-radius: 40% 40% 50% 40%/30% 50% 50% 50%;
    }
  }
  .page {
    position: relative;
    padding: 50px 40px !important;
    width: 700px;
    color: #212121;
    border-top-left-radius: 20px 60px;
    border-top-right-radius: 500px 20px;
    // border-bottom-left-radius: 0px;
    // border-bottom-right-radius: 5px 100px;
    background: #faf5b3;
    background: -webkit-gradient(linear, left top, left bottom, from(#81cbbc), color-stop(2%, #faf5b3));
    -webkit-background-size: 100% 40px;
    -webkit-box-shadow: 0 2px 10px 1px rgba(0, 0, 0, .2);
    &:before {
      content: ' ';
      background-image: url('../../assets/images/masking2.png');
      background-size: cover;
      width: 35%;
      height: 50px;
      position: absolute;
      top: -25px;
      left: 32%;
      transform: rotate(357deg);
    }
    &:after {
      content: '';
      display:block;
      height: 45px;
      background-image:url('https://ecorelos.com/wp-content/uploads/2020/04/torn-border2.png');
      position:absolute;
      bottom:-13px;
      width:100%;
      left:0px;   
    }
    p {
      line-height: 40px;
      margin-bottom: 40px;
    }
  }
.col {
  padding: 0 !important;
}
.articleDuration .row{
  padding-left: 10px !important;
}
.imageMain {
  height: 120px;
  display: flex;
  align-items: center;
}
.removeBtn {
  padding: 10px;
  margin: 5px 10px 5px 5px !important;
  color: #f66;
  font-weight: 900;
  
}
.removeBtn:hover {
  color: rgb(255, 56, 56);
  font-size: 1.8rem;
  margin-left: 0px !important;
}
$red: #f45e61;
$purple: #6477b9;
$blue: #0eb7da;
$cyan: #60daaa;
$yellow: #fbca67;
$orange: #ff8a30;
.buttons {
  isolation: isolate;
}

.articleSubmit {
  background: none;
  border: 0;
  box-sizing: border-box;
  margin: 1em;
  padding: 10px 20px;

  // Using inset box-shadow instead of border for sizing simplicity
  box-shadow: inset 0 0 0 2px $orange;
  color: black;
  font-size: 1.2em;
  font-weight: 700;

  // Required, since we're setting absolute on pseudo-elements
  position: relative;
  vertical-align: middle;

  &::before,
  &::after {
    box-sizing: inherit;
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
  }
}
.draw {
    transition: color 0.25s;

  &::before,
  &::after {
    // Set border to invisible, so we don't see a 4px border on a 0x0 element before the transition starts
    border: 2px solid transparent;
    width: 0;
    height: 0;
  }

  // This covers the top & right borders (expands right, then down)
  &::before {
    top: 0;
    left: 0;
  }

  // And this the bottom & left borders (expands left, then up)
  &::after {
    bottom: 0;
    right: 0;
  }
  
  &:hover {
    color: $cyan;
  }

  // Hover styles
  &:hover::before,
  &:hover::after {
    width: 100%;
    height: 100%;
  }

  &:hover::before {
    border-top-color: $cyan; // Make borders visible
    border-right-color: $cyan;
    transition:
      width 0.25s ease-out, // Width expands first
      height 0.25s ease-out 0.25s; // And then height
  }

  &:hover::after {
    border-bottom-color: $cyan; // Make borders visible
    border-left-color: $cyan;
    transition:
      border-color 0s ease-out 0.5s, // Wait for ::before to finish before showing border
      width 0.25s ease-out 0.5s, // And then exanding width
      height 0.25s ease-out 0.75s; // And finally height
  }
}

// Inherits from .draw
.meet {
  
  &:hover {
    color: black;
    text-shadow: 0px 0px 1px #999;
    background: #ffffff2c;
  }

  // Start ::after in same position as ::before
  &::after {
    top: 0;
    left: 0;
  }

  // Change colors
  &:hover::before {
    border-width: 3px;
    border-top-color: $red;
    border-right-color: $red;
  }

  &:hover::after {
    border-width: 3px;
    border-bottom-color: $red;
    border-left-color: $red;
    transition: // Animate height first, then width
      height 0.25s ease-out,
      width 0.25s ease-out 0.25s;
  }

}
.spin {
  width: 5em;
  height: 3em;
  padding: 0;
  
  &:hover {
    color: $blue;
  }

  &::before,
  &::after {
    top: 0;
    left: 0;
  }

  &::before {
    border: 2px solid transparent; // We're animating border-color again
  }

  &:hover::before {
    border-top-color: $blue; // Show borders
    border-right-color: $blue;
    border-bottom-color: $blue;

    transition:
      border-top-color 0.15s linear, // Stagger border appearances
      border-right-color 0.15s linear 0.10s,
      border-bottom-color 0.15s linear 0.20s;
  }

  &::after {
    border: 0 solid transparent; // Makes border thinner at the edges? I forgot what I was doing
  }

  &:hover::after {
    border-top: 2px solid $blue; // Shows border
    border-left-width: 2px; // Solid edges, invisible borders
    border-right-width: 2px; // Solid edges, invisible borders
    transform: rotate(270deg); // Rotate around circle
    transition:
      transform 0.4s linear 0s,
      border-left-width 0s linear 0.35s; // Solid edge post-rotation
  }
}

.circle {
  border-radius: 100%;
  box-shadow: none;
    
  &::before,
  &::after {
    border-radius: 100%;
  }
}

.thick {
  &:hover {
    color: #fff;
    font-weight: 700;
  }

  &::before {
    border: 1.5em solid transparent;
    z-index: -1;
  }

  &::after {
    mix-blend-mode: color-dodge;
    z-index: -1;
  }

  &:hover::before {
    background: $red;
    border-top-color: $red;
    border-right-color: $red;
    border-bottom-color: $red;
    transition:
      background 0s linear 0.4s,
      border-top-color 0.15s linear,
      border-right-color 0.15s linear 0.15s,
      border-bottom-color 0.15s linear 0.25s;
  }

  &:hover::after {
    border-top: 1.5em solid $red;
    border-left-width: 1.5em;
    border-right-width: 1.5em;
  }
}
.articleContent {
  .hide {
    display: none;
  }
  .productName {
    position: relative;
  }
  ul{
    padding-left: 0px !important;
  }

  .show{
    display: inherit;
    position: absolute;
    top: 50px;
    width: 100%;
    height: 156px;
    overflow-y: auto;
    z-index: 1;
      li {
      margin-top: -1px;
      // padding: 0 20px;
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
}
</style>