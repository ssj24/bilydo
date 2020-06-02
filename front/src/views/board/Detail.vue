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
            <p>
              {{product}}
            </p>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="3">
            <p class="articleTitle articleNd">
              사용 기간
            </p>
          </v-col>
          <v-col cols="12" sm="9" class="articleContent">
            <p>
              {{used}}
            </p>
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
              <v-col cols="5" sm="3">
                <p style="text-align: center;">
                  {{startDate}} 
                </p>
              </v-col>
              <v-col cols="2" sm="3">
                <p style="text-align: center;">
                  ~ 
                </p>
              </v-col>
              <v-col cols="5" sm="3">
                <p style="text-align: center;">
                  {{endDate}}
                </p>
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
            <v-row style="padding-left: 20px;">
              <p>
                {{priceDay}}
              </p>
              <v-col>
                <span style="margin: 0 10px;">
                  / 일
                </span>
                <v-checkbox v-model="checkboxDay" label="" style="display: inline-block;"></v-checkbox>
              </v-col>
            </v-row>
            <v-row style="padding-left: 20px;">
              <p>
                {{priceMonth}}
              </p>
              <v-col>
                <span style="margin: 0 10px;">
                  / 월
                </span>
                <v-checkbox v-model="checkboxMonth" label="" style="display: inline-block;"></v-checkbox>
              </v-col>
            </v-row>
            <v-row style="padding-left: 20px;">
              <p>
                {{priceYear}}
              </p>
              <v-col>
                <span style="margin: 0 10px;">
                  / 년
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
            <p>
              {{description}}
            </p>
            
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
              <div v-for="(image, key) in images" :key="key" id="preview" style="display: inline-block;">
                <div class="imageMain">
                  <img :ref="'image'" :id="'test' + key" />
                </div>
              </div>
            </div>
          </v-col>
        </v-row>
        <v-row justify="center">
    <v-btn
      class="articleSubmit" 
      outlined 
      color="#f66"
      dark
      @click.stop="dialog = true"
    >
      대여하기
    </v-btn>

    <v-dialog
      v-model="dialog"
      max-width="290"
    >
      <v-card>
        <v-card-title class="headline">Use Google's location service?</v-card-title>

        <v-card-text>
          Let Google help apps determine location. This means sending anonymous location data to Google, even when no apps are running.
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            color="green darken-1"
            text
            @click="dialog = false"
          >
            Disagree
          </v-btn>

          <v-btn
            color="green darken-1"
            text
            @click="dialog = false"
          >
            Agree
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
        </v-row>
      </v-col>
    </v-row>
    
  </v-container>
</template>

<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator'

  @Component
  export default class Article extends Vue{
    private product = '갤럭시 노트10';
    private used = '개봉만 함';
    private startDate = new Date().toISOString().substr(0, 10);
    private endDate = new Date(+new Date() + (86400000 * 7)).toISOString().substr(0, 10);
    private startMenu = false;
    private endMenu = false;
    private checkboxDay = false;
    private checkboxMonth = false;
    private checkboxYear = false;
    private priceDay = 1000;
    private priceMonth = 10000;
    private priceYear = 100000;
    private description = '진짜 좋아요 진짜 좋은데 설명할 방법이 없네';
    private images: File[] = [];
    private dialog = false;
    
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
    background: -webkit-gradient(linear, left top, left bottom, from(#bae7de), color-stop(2%, #faf5b3));
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
      font-weight: 900;
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