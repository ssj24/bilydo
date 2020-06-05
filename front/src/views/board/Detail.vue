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
              주소
            </p>
          </v-col>
          <v-col cols="12" sm="9" class="articleContent">
            <p>
              {{address}}
            </p>
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
            <p class="articleTitle articleRd">
              기간별 대여가격
            </p>
          </v-col>
          <v-col cols="12" sm="9" class="articleContent articleDuration">
            <v-radio-group v-model="radios" :mandatory="false">
              <v-row style="padding-left: 20px;">
                <p>
                  {{priceDay}}
                </p>
                <v-col>
                  <span style="margin: 0 10px;">
                    / 일
                  </span>
                  <v-radio color="#f45e61" value="checkboxDay" style="display: inline-block; margin-top: 10px;"></v-radio>
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
                  <v-radio color="#f45e61" value="checkboxMonth" style="display: inline-block; margin-top: 10px;"></v-radio>
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
                  <v-radio color="#f45e61" value="checkboxYear" style="display: inline-block; margin-top: 10px;"></v-radio>
                </v-col>
              </v-row>
            </v-radio-group>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="3">
            <p class="articleTitle articleNd">
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
            <p class="articleTitle articleRd">
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
        <v-row justify="center" class="buttons">
          <button class="articleSubmit draw meet" @click.stop="dialog = true">
            대여하기
          </button>
          <v-dialog
            v-model="dialog"
            width="60%"
            max-width="80%"
            >
            <v-card class="contModal">
              <v-card-title class="modalTitle">
                ✍️(제공자)님께 대여를 요청합니다
              </v-card-title>
              <v-card-text style="color: black; font-size: 1rem;">
                <div>
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
                            label="대여 시작일"
                            readonly
                            v-on="on"
                            color="#f92"
                          ></v-text-field>
                        </template>
                        <v-date-picker v-model="startDate" no-title scrollable color="#f45e61">
                          <v-spacer></v-spacer>
                          <v-btn text color="#f45e61" @click="$refs.startMenu.save(startDate)">OK</v-btn>
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
                            label="대여 종료일"
                            readonly
                            v-on="on"
                            color="#f92"
                          ></v-text-field>
                        </template>
                        <v-date-picker v-model="endDate" no-title scrollable color="#f45e61">
                          <v-spacer></v-spacer>
                          <v-btn text color="#f45e61" @click="$refs.endMenu.save(endDate)">OK</v-btn>
                        </v-date-picker>
                      </v-menu>
                    </v-col>
                  </v-row>
                  <p>
                    예상가격: {{price}}
                  </p>
                  <p>
                    제공자(path), 평점
                  </p>
                </div>
                <hr>
              </v-card-text>
              <v-card-actions class="d-flex justify-center">
                <v-btn
                  color="#000"
                  style="font-size: 1.05em; margin-bottom: 20px;"
                  outlined
                  @click="dialogPro = false"
                >
                  대여 요청
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
  import { Component, Vue, Watch } from 'vue-property-decorator'

  @Component
  export default class Article extends Vue{
    private product = '갤럭시 노트10';
    private used = '개봉만 함';
    private address = '대전시 유성구 도룡동';
    private startDate = new Date().toISOString().substr(0, 10);
    private endDate = new Date(+new Date() + (86400000 * 7)).toISOString().substr(0, 10);
    private startMenu = false;
    private endMenu = false;
    private radios = 'checkboxDay';
    private priceDay = 1000;
    private priceMonth = 10000;
    private priceYear = 100000;
    private description = '진짜 좋아요 진짜 좋은데 설명할 방법이 없네';
    private images: File[] = [];
    private dialog = false;
    private price = 0;
    
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
    @Watch('startDate', {
      immediate: true
    })
    startDateChanged() {
      const dateStart = new Date(Date.parse(this.startDate));
      const dateEnd = new Date(Date.parse(this.endDate));
      const diffDate = dateEnd.getDate() - dateStart.getDate();
      const diffMonth = dateEnd.getMonth() - dateStart.getMonth();
      const diffYear = dateEnd.getFullYear() - dateStart.getFullYear();
      if (this.radios == 'checkboxDay') {
        if (diffMonth || diffYear) {
          this.price = (this.priceDay * diffDate) + (this.priceDay * diffMonth * 30) + (this.priceDay * diffYear * 365)
        } else {
          this.price = this.priceDay * diffDate
        }
      } else if (this.radios == 'checkboxMonth') {
        if (diffDate || diffYear) {
          this.price = (this.priceMonth / 30) * diffDate + (this.priceMonth * diffMonth) + (this.priceMonth * 12 * diffYear)
        } else {
          this.price = this.priceMonth * diffMonth
        }
      } else {
        if (diffMonth || diffYear) {
          this.price = (this.priceYear / 365) * diffDate + (this.priceYear / 12) * diffMonth + (this.priceYear * this.priceYear)
        } else {
          this.price = this.priceYear * diffYear
        }
      }
    }
    @Watch('endDate', {
      immediate: true
    })
    endDateChanged() {
      const dateStart = new Date(Date.parse(this.startDate));
      const dateEnd = new Date(Date.parse(this.endDate));
      const diffDate = dateEnd.getDate() - dateStart.getDate();
      const diffMonth = dateEnd.getMonth() - dateStart.getMonth();
      const diffYear = dateEnd.getFullYear() - dateStart.getFullYear();
      if (this.radios == 'checkboxDay') {
        if (diffMonth || diffYear) {
          this.price = (this.priceDay * diffDate) + (this.priceDay * diffMonth * 30) + (this.priceDay * diffYear * 365)
        } else {
          this.price = this.priceDay * diffDate
        }
      } else if (this.radios == 'checkboxMonth') {
        if (diffDate || diffYear) {
          this.price = (this.priceMonth / 30) * diffDate + (this.priceMonth * diffMonth) + (this.priceMonth * 12 * diffYear)
        } else {
          this.price = this.priceMonth * diffMonth
        }
      } else {
        if (diffDate || diffMonth) {
          this.price = (this.priceYear / 365) * diffDate + (this.priceYear / 12) * diffMonth + (this.priceYear * diffYear)
          console.log(typeof diffDate)
        } else {
          this.price = this.priceYear * diffYear
        }
      }
    }
    @Watch('dialog', {
      immediate: true
    })
    dialogClosed() {
      this.startDate = new Date().toISOString().substr(0, 10);
      this.endDate = new Date(+new Date() + (86400000 * 7)).toISOString().substr(0, 10);
      this.price = 0;
    }
  }
</script>

<style lang="scss" scoped>
.page {
  font-weight: 900;
}
</style>