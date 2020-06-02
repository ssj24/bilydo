<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="11" sm="10" class="articleMain page">
        <v-row align="center">
          <v-col cols="6" sm="3">
            <p class="articleTitle articleSt">
              제품명
            </p>
          </v-col>
          <v-col cols="12" sm="9" class="articleContent">
            <v-select color="#8c28b4" v-model="product">

            </v-select>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="3">
            <p class="articleTitle articleNd">
              사용 기간
            </p>
          </v-col>
          <v-col cols="12" sm="9" class="articleContent">
            <v-text-field color="#8c28b4" v-model="used">
              
            </v-text-field>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="3">
            <p class="articleTitle articleRd">
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
                      color="#8c28b4"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="startDate" no-title scrollable color="#8c28b4">
                    <v-spacer></v-spacer>
                    <v-btn text color="#8c28b4" @click="startMenu = false">Cancel</v-btn>
                    <v-btn text color="#8c28b4" @click="$refs.startMenu.save(startDate)">OK</v-btn>
                  </v-date-picker>
                </v-menu>
              </v-col>
              <v-spacer></v-spacer>
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
                      color="#8c28b4"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="endDate" no-title scrollable color="#8c28b4">
                    <v-spacer></v-spacer>
                    <v-btn text color="#8c28b4" @click="endMenu = false">Cancel</v-btn>
                    <v-btn text color="#8c28b4" @click="$refs.endMenu.save(endDate)">OK</v-btn>
                  </v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="3">
            <p class="articleTitle articleNd">
              기간별 대여가격
            </p>
          </v-col>
          <v-col cols="12" sm="9" class="articleContent articleDuration">
            <v-row>
              <v-col cols="6">
                <v-text-field type="number" min="0" step="100" color="#8c28b4" v-model="priceDay">
                </v-text-field>
              </v-col>
              <v-col>
                <span style="margin: 0 10px;">
                  * 일
                </span>
                <v-checkbox v-model="checkboxDay" label="" style="display: inline-block;"></v-checkbox>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="6">
                <v-text-field type="number" min="0" step="100" color="#8c28b4" v-model="priceMonth">
                </v-text-field>
              </v-col>
              <v-col>
                <span style="margin: 0 10px;">
                  * 월
                </span>
                <v-checkbox v-model="checkboxMonth" label="" style="display: inline-block;"></v-checkbox>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="6">
                <v-text-field type="number" min="0" step="100" color="#8c28b4" v-model="priceYear">
                </v-text-field>
              </v-col>
              <v-col>
                <span style="margin: 0 10px;">
                  * 년
                </span>
                <v-checkbox v-model="checkboxYear" label="" style="display: inline-block;"></v-checkbox>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="3">
            <p class="articleTitle articleRd">
              제품 설명
            </p>
          </v-col>
          <v-col cols="12" sm="9" class="articleContent">
            <v-textarea
              outlined
              label="자세한 설명 부탁드립니다^^"
              v-model="description"
              color="#8c28b4"
            ></v-textarea>
            
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="3">
            <p class="articleTitle articleNd">
              제품 사진
            </p>
          </v-col>
          <v-col cols="12" sm="9" class="articleContent">
            <div>
              <input type="file" multiple @change="onFileChange" style="display: block; margin-bottom: 10px;"/>
              <div v-for="(image, key) in images" :key="key" id="preview" style="display: inline-block;">
                <div class="imageMain">
                  <img :ref="'image'" />
                  <button @click.prevent="removeImage(key)" class="removeBtn">x</button>
                </div>
              </div>
            </div>
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-btn class="articleSubmit" outlined color="#f66">
            작성
          </v-btn>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator'

  @Component
  export default class Article extends Vue{
    private product = '';
    private used = '';
    private startDate = new Date().toISOString().substr(0, 10);
    private endDate = new Date(+new Date() + (86400000 * 7)).toISOString().substr(0, 10);
    private startMenu = false;
    private endMenu = false;
    private checkboxDay = true;
    private checkboxMonth = true;
    private checkboxYear = true;
    private priceDay = 0;
    private priceMonth = 0;
    private priceYear = 0;
    private description = '';
    private images: string[] = [];
    
    public previewImg(): void {
      for (let i = 0; i < this.images.length; i++) {
        const reader = new FileReader();
        reader.onload = () => {
          this.$refs.image[i].src = reader.result;
        }
        reader.readAsDataURL(this.images[i]);
      }
    }
    public onFileChange(e: Event): void {
      const files = e.target.files;
      for (let i = 0; i < files.length; i++) {
        this.images.push(files[i]);
      }
      this.previewImg()
      
    }
    public removeImage(key: number): void {
      this.images.splice(key, 1);
      this.previewImg()
    }
  }
</script>

<style lang="scss">

#preview {
  display: flex;
  justify-content: center;
  align-items: center;
}

#preview img {
  max-width: 100px;
  max-height: 100px;
}
  .container {
    margin-top: 50px;
    margin-bottom: 50px;
  }
  .articleMain {
    padding-top: 40px !important;
    // background-color: rgb(239, 220, 255);
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
      background-image: url('../../assets/images/tape.png');
      background-size: cover;
      width: 150px;
      height: 70px;
      position: absolute;
      top: -40px;
      left: 40%;
    }
    &:after {
      content: '';
      display:block;
      height: 45px;
      background-image:url('https://ecorelos.com/wp-content/uploads/2020/04/torn-border2.png');
      position:absolute;
      bottom:-15px;
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
.articleSubmit {
  margin-bottom: 20px;
  span {
    color: #000;
    font-size: 1.2rem;
    font-weight: 900;
  }
}
.imageMain {
  height: 120px;
  display: flex;
  align-items: center;
}
.removeBtn {
  padding: 10px;
  margin: 10px;
  margin-right: 20px;
  color: #f66;
  font-weight: 900;
}
.removeBtn:hover {
  color: rgb(255, 56, 56);
  font-size: 1.8rem;
  margin-left: 5px;
}
</style>