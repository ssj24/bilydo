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
          <v-col cols="8" sm="3">
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
          <v-col cols="8" sm="3">
            <p class="articleTitle articleRd">
              기간별 대여가격
            </p>
          </v-col>
          <v-col cols="12" sm="9" class="articleContent articleDuration">
            <v-radio-group v-model="radios" :mandatory="false">
              <v-row v-if="priceDay" style="padding-left: 20px; font-size: 1.05em;">
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
              <v-row v-if="priceWeek" style="padding-left: 20px; font-size: 1.05em;">
                <p>
                  {{priceWeek}}
                </p>
                <v-col>
                  <span style="margin: 0 10px;">
                    / 주
                  </span>
                  <v-radio color="#f45e61" value="checkboxWeek" style="display: inline-block; margin-top: 10px;"></v-radio>
                </v-col>
              </v-row>
              <v-row v-if="priceMonth" style="padding-left: 20px; font-size: 1.05em;">
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
              <div v-for="(image, key) in images" :key="key" class="images">
                <img :src="'https://k02b2071.p.ssafy.io/'+image" :id="'test' + key" />
              </div>
            </div>
          </v-col>
        </v-row>
        <v-row justify="center" class="buttons">
          <button class="articleSubmit draw meet" @click.stop="chkRadios">
            대여하기
          </button>
        </v-row>
      </v-col>
    </v-row>
          <v-dialog
            v-model="dialog"
            max-width="512px"
            >
            <v-card class="contModal backModal">
              <v-card-title class="modalTitle">
                <router-link :to="{name:'Account', params:{userId:producerId}}" class="toAccount">
                  {{producer.name}}
                </router-link>
                님께 대여를 요청합니다
              </v-card-title>
                
              <v-card-text style="color: black; font-size: 1rem;">
                <div>
                  
                  <v-row>
                    <v-col cols="12" sm="6" md="4">
                      <v-menu
                        ref="startMenuModal"
                        v-model="startMenuModal"
                        :close-on-content-click="false"
                        :return-value.sync="startDateModal"
                        transition="scale-transition"
                        offset-y
                        min-width="290px"
                      >
                        <template v-slot:activator="{ on }">
                          <v-text-field
                            v-model="startDateModal"
                            label="대여 시작일"
                            readonly
                            v-on="on"
                            color="#f92"
                          ></v-text-field>
                        </template>
                        <v-date-picker v-model="startDateModal" no-title scrollable color="#f45e61">
                          <v-spacer></v-spacer>
                          <v-btn text color="#f45e61" @click="$refs.startMenuModal.save(startDateModal)">OK</v-btn>
                        </v-date-picker>
                      </v-menu>
                    </v-col>
                    <v-spacer></v-spacer>
                    <v-col cols="12" sm="6" md="4">
                      <v-menu
                        ref="endMenuModal"
                        v-model="endMenuModal"
                        :close-on-content-click="false"
                        :return-value.sync="endDateModal"
                        transition="scale-transition"
                        offset-y
                        min-width="290px"
                      >
                        <template v-slot:activator="{ on }">
                          <v-text-field
                            v-model="endDateModal"
                            label="대여 종료일"
                            readonly
                            v-on="on"
                            color="#f92"
                          ></v-text-field>
                        </template>
                        <v-date-picker v-model="endDateModal" no-title scrollable color="#f45e61">
                          <v-spacer></v-spacer>
                          <v-btn text color="#f45e61" @click="$refs.endMenuModal.save(endDateModal)">OK</v-btn>
                        </v-date-picker>
                      </v-menu>
                    </v-col>
                  </v-row>
                  <p>
                    예상가격: {{price}}
                  </p>
                  <p>
                    연락처: {{producer.phone}}
                  </p>
                  <p>
                    <router-link :to="{name:'Account', params:{userId:producerId}}" class="toAccount">
                      {{producer.name}}
                    </router-link>
                    님의 거래 평점: {{producer.score}}점
                  </p>
                </div>
              </v-card-text>
              <v-card-actions class="d-flex justify-center">
                <v-btn
                  color="#000"
                  style="font-size: 1.05em; margin-bottom: 20px;"
                  class="submitBtn"
                  outlined
                  @click="offerCon"
                >
                  대여 요청
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
    
  </v-container>
</template>

<script lang="ts">
  import { Component, Vue, Watch } from 'vue-property-decorator'
  import baseUrl from '@/base-url';

  @Component
  export default class Article extends Vue{
    private product = '';
    private used = '';
    private address = '';
    private startDate = '';
    private startDateModal = '';
    private endDate = '';
    private endDateModal = '';
    private startMenu = false;
    private startMenuModal = false;
    private endMenu = false;
    private endMenuModal = false;
    private radios = '';
    private priceDay = 0;
    private priceWeek = 0;
    private priceMonth = 0;
    private description = '';
    private images: string[] = [];
    private dialog = false;
    private price = 0;
    private boardId = 0;
    private producerId = 0;
    private producer: object[] = [];
    
    public offerCon(): void {
      const data = {
        borrowEnd: this.endDateModal + 'T00:00:00.099Z',
        borrowSrt: this.startDateModal + 'T00:00:00.099Z',
        boardId: this.boardId,
        productId: 1,
        realRentalFee: this.price
      }
      baseUrl.post('/boards/user/'+this.boardId+'/request', data)
      .then(res => {
        console.log(res)
        alert(`${this.product} 대여를 요청하셨습니다.`)
        this.dialog = false;
      })
      .catch(() => {
        alert("잘못된 시도입니다. 대여 가능 기간을 확인해 주세요.")
      })
    }

    @Watch('startDateModal', {
      immediate: true
    })
    startDateChanged() {
      const dateStart = new Date(Date.parse(this.startDateModal));
      const dateEnd = new Date(Date.parse(this.endDateModal));
      const diffDate = dateEnd.getDate() - dateStart.getDate();
      const diffMonth = dateEnd.getMonth() - dateStart.getMonth();
      const diffYear = dateEnd.getFullYear() - dateStart.getFullYear();
      if (this.radios == 'checkboxDay') {
          this.price = (this.priceDay * diffDate) + (this.priceDay * diffMonth * 30) + (this.priceDay * diffYear * 365)
          Math.floor(this.price)
      } else if (this.radios == 'checkboxWeek') {
          this.price = (this.priceWeek / 7) * diffDate + (this.priceWeek * 4 * diffMonth) + (this.priceWeek * 4 * 12 * diffYear)
          Math.floor(this.price)
      } else {
          this.price = (this.priceMonth / 30) * diffDate + (this.priceMonth * diffMonth) + (this.priceMonth * 12 * diffYear)
          Math.floor(this.price)
      }
      if (this.price < 0) {
        alert("날짜를 다시 설정해주세요")
      }
    }
    @Watch('endDateModal', {
      immediate: true
    })
    endDateChanged() {
      const dateStart = new Date(Date.parse(this.startDateModal));
      const dateEnd = new Date(Date.parse(this.endDateModal));
      const diffDate = dateEnd.getDate() - dateStart.getDate();
      const diffMonth = dateEnd.getMonth() - dateStart.getMonth();
      const diffYear = dateEnd.getFullYear() - dateStart.getFullYear();
      if (this.radios == 'checkboxDay') {
          this.price = (this.priceDay * diffDate) + (this.priceDay * diffMonth * 30) + (this.priceDay * diffYear * 365)
          this.price = Math.floor(this.price);
      } else if (this.radios == 'checkboxWeek') {
          this.price = (this.priceWeek / 7) * diffDate + (this.priceWeek * 4 * diffMonth) + (this.priceWeek * 4 * 12 * diffYear)
          this.price = Math.floor(this.price);
      } else {
          this.price = (this.priceMonth / 30) * diffDate + (this.priceMonth * diffMonth) + (this.priceMonth * 12 * diffYear)
         this.price =  Math.floor(this.price);
      }
      if (this.price < 0) {
        this.price = 0;
        alert("날짜를 다시 설정해주세요.")
      }
    }
    public chkRadios(): boolean {
      if (this.radios) {
        return this.dialog = true;
      } else {
        alert("기간별 대여가격을 선택해주세요.")
        return this.dialog = false;
      }
    }
    @Watch('dialog', {
      immediate: true
    })
    dialogClosed() {
      this.price = 0;
    }
    created() {
      this.boardId = Number(this.$route.params.boardId);
      baseUrl('/boards/'+this.boardId)
        .then(res => {
          this.product = res.data.productName;
          this.used = res.data.usedTime;
          this.address = res.data.location;
          this.priceDay = res.data.priceDay;
          this.priceWeek = res.data.priceWeek;
          this.priceMonth = res.data.priceMonth;
          this.description = res.data.subscript;
          this.images = res.data.imagesPath;
          this.producerId = res.data.producerId;
          if (res.data.borrowableSrt && res.data.borrowableEnd) {
            this.startDate = res.data.borrowableSrt.slice(0, 10);
            this.endDate = res.data.borrowableEnd.slice(0, 10);
          }
          baseUrl('/users/'+res.data.producerId)
          .then(res=>{
            this.producer = res.data;
          })
        })
    }
  }
</script>

<style lang="scss">
.page {
  font-weight: 900;
  font-size: 1.15em;
}
.images {
  display: inline-block;
  position: relative;
  margin: 10px;
  img {
    width: 200px;
    height: 200px;
  }
  &:before {
    content: '';
    width: 100%;
    height: 100%;
    // background: url('../../assets/images/goldBorder.png');
    background: url('../../assets/images/crayonBorder.png');
    background-size: 210px 200px;
    background-repeat: no-repeat;
    position: absolute;
    top: 0;

  }
}
.backModal {
  background-image: url('../../assets/images/goldBorder.png');
  background-size: 100% 100%;
  padding: 60px 20px 20px;
  .submitBtn {
    &:hover {
      font-weight: 900;
      transform: scale(1.05);
    }
  }
}
.contModal .modalTitle {
  font-size: 1.5em !important;
}
.contModal p {
  font-size: 1.1em;
  font-weight: 600;
}
.toAccount:hover {
  transform: scale(1.1);
  font-weight: 900;
}
</style>