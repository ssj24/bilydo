<template>
  <v-container class="accountWrap">
    <v-row>
      <v-col cols="5" sm="8" md="9"></v-col>
      <v-col cols=7 sm="4" md="3">
        <h2 style="margin-top: 10px; margin-bottom: 10px; font-family: 'ON-IGothic'; font-weight: 900; display: inline-block;">
          {{userName}}
        </h2>
        <h3 style="font-family: 'ON-IGothic'; display: inline-block;">님의 정보</h3>
      </v-col>
    </v-row>
    <v-row justify="center">
      <v-col cols="12" sm="10" class="attrs">
        <v-row align="center">
          <v-col cols="6" sm="4">
            <div class="accountTitle">
              비밀번호
            </div>
          </v-col>
          <v-col cols="6" sm="8">
            <h3 @click="appendInput($event.target)" class="cursorE">
              {{ userPassword }}
            </h3>
            <div class="accountUpdateDisplay">
              <input class="updateInput" type="text" v-model="newPassword">
              <v-btn class="vBtn" @click="closeInput($event.target)">확인</v-btn>
            </div>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="4">
            <div class="accountTitle">
              이름
            </div>
          </v-col>
          <v-col cols="6" sm="8">
            <h3 @click="appendInput($event.target)" class="cursorE">
              {{ userName }}
            </h3>
            <div class="accountUpdateDisplay">
              <input class="updateInput" type="text" v-model="userName">
              <v-btn class="vBtn" @click="closeInput($event.target)">확인</v-btn>
            </div>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="4">
            <div class="accountTitle">
              연락처
            </div>
          </v-col>
          <v-col cols="6" sm="8">
            <h3 @click="appendInput($event.target)" class="cursorE">
              {{ userContact }}
            </h3>
            <div class="accountUpdateDisplay">
              <input class="updateInput" type="text" v-model="userContact">
              <v-btn class="vBtn" @click="closeInput($event.target)">확인</v-btn>
            </div>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="4">
            <div class="accountTitle">
              주소
            </div>
          </v-col>
          <v-col cols="6" sm="8">
            <h3 class="cursorE mr-6" style="display: inline-block;">
              {{ userAddress }}
            </h3>
              <div class="text-center" style="display: inline-block;">
              <v-dialog
									v-model="dialog"
									width="500"
								>
									<template v-slot:activator="{ on, attrs }">
										<v-btn
											color="#8c28b4"
											dark
											v-bind="attrs"
											v-on="on"
										>
											주소 변경
										</v-btn>
									</template>

									<v-card>
										<v-card-title
											class="headline"
											style="background-color: #8c28b4; color: #fff;"
											primary-title
										>
											주소: {{userAddress}}
											<v-spacer></v-spacer>
											<span @click="dialog = false" style="cursor: pointer;">
												확인
											</span>
										</v-card-title>
										<vue-daum-postcode @complete="userAddress = $event.address"/>
									</v-card>
								</v-dialog>
							</div>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="4">
            <div class="accountTitle">
              평균 평점
            </div>
          </v-col>
          <v-col cols="6" sm="8">
            <span v-if="averagePoint">
              <span v-for="i in averagePoint" :key="i">
                💖
              </span>
              {{averagePoint}}/5 
            </span>
            <span v-else>
              아직 점수가 없습니다
            </span>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="4">
            <div class="accountTitle">
              대여 요청
            </div>
          </v-col>
          <v-col cols="6" sm="8">
            <v-btn
              color="#8c28b4"
              outlined
              @click="messagesReset"
              @click.stop="dialogOffer = true;"
            >
              <span style="color: #000; font-size: 1.2em; font-weight: 900;">
                {{offers.length}}
              </span>
            </v-btn>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="4">
            <div class="accountTitle">
              대기 중
            </div>
          </v-col>
          <v-col cols="6" sm="8">
            <v-badge
              :content="$store.state.messages"
              :value="$store.state.messages"
              color="#FFEC81"
              bordered
              offset-x="10"
              offset-y="10"
            >
              <v-btn
                color="#8c28b4"
                outlined
                @click="messagesReset"
                @click.stop="dialogPro = true;"
              >
                <span style="color: #000; font-size: 1.2em; font-weight: 900;">
                  {{contractProgress.length}}
                </span>
              </v-btn>
            </v-badge>

            <v-dialog
              v-model="dialogPro"
              max-width="550px"
              >
              <v-card class="contModal">
                <v-card-title class="modalTitle">
                  📖{{userName}}님의 대기 중인 물품 {{contractProgress.length}}건
                </v-card-title>
                <p v-if="false" style="text-align: end; margin-right: 20px;">
                  <span style="background-color: #D9FFF2; font-weight: 900; padding: 5px; border-radius: 50px;">대여 요청이 있을 때</span>
                  <span style="background-color: #F3FFD2; font-weight: 900; padding: 5px; border-radius: 50px;">대여 요청이 없을 때</span>
                </p>
                <v-card-text v-for="(cont, i) in contractProgress" :key="i" style="color: black; font-size: 1rem;">
                  <v-row justify="center">
                    <v-col cols="11" md="8">
                      <v-row justify="center">
                        <v-col cols="11" sm="4" class="d-flex justify-center align-center t-center">
                          <router-link :to="{name:'Detail', params:{boardId:cont.id}}">
                            {{cont.productName}}
                          </router-link>
                        </v-col>
                        <v-col cols="11" sm="3" class="d-flex justify-center align-center t-center">
                          {{ cont.registDate }}
                        </v-col>
                        <v-col cols="11" sm="4" class="d-flex justify-center align-center t-center">
                          <v-btn
                            color="#EFFDF8"
                            @click.stop="showDialogReviewPro(cont)"
                            v-if="cont.arr.length" 
                            class="distRolePro"
                            style="font-weight: 900;"
                          >
                            대여 요청 보기
                          </v-btn>
                          <v-btn
                            color="#F9FDEF"
                            @click.stop="showDialogReviewPro(cont)"
                            v-else class="distRoleBo"
                            style="font-weight: 900;"
                          >
                            거래 대기 중
                          </v-btn>
                        </v-col>
                      </v-row>
                        <hr style="background-color: #888; margin-top: 10px; border-radius: 50px;">  

                    </v-col>
                  </v-row>
                </v-card-text>
                <v-dialog
                  v-model="dialogReviewPro"
                  max-width="680px"
                  >
                  <v-card class="reviewModal">
                    <span v-for="(offer, i) in data.arr" :key="i">
                      <span v-if="consumerData[i]">
                        
                        <v-card-title class="modalTitle">
                          <span style="font-size: 0.7em; color: #000; font-weight: 900;">⭕  신청인: &nbsp;&nbsp;&nbsp;  </span>
                            <router-link :to="{name:'Account', params:{userId: offer.consumerId}}">
                              {{consumerData[i].name}}
                            </router-link>
                        </v-card-title>
                        <v-card-text style="color: #000; font-weight: 900;">
                          <p v-if="data.role == '대여자'">⭕ 제공자 이름 - 연락처</p>
                          <p v-else>⭕ 연락처: 
                            {{consumerData[i].phone}}
                          </p>
                          <p>⭕ 대여 신청 기간: 
                            {{offer.borrowSrt}} ~ {{offer.borrowEnd}}
                          </p>
                        <p>
                            ⭕ 예상 가격: {{offer.realRentalFee}}
                          </p>
                          <label for="finalprice" style="margin-right: 20px;">⭕ 최종 가격: </label>
                          <input 
                            id="finalprice" 
                            type="number" min=0 step="100" 
                            v-model="finalPrice"
                            style="border-bottom: 1px solid black; margin-bottom: 20px;"
                          >
                          <v-row justify="center">
                            <v-btn
                              color="#f66"
                              outlined
                              fab
                              dark
                              style="margin-right: 20px;"
                              @click="refuseOffer(offer.id)"
                              >
                              <span style="color: #000; font-weight: 900; font-size: 1.1em;">
                                거절
                              </span>
                            </v-btn>
                            
                            <v-btn
                              color="green"
                              outlined
                              fab
                              dark
                              @click="acceptOffer(offer.id)"
                              >
                              <span style="color: #000; font-weight: 900; font-size: 1.1em;">
                                수락
                              </span>
                            </v-btn>
                          </v-row>
                        </v-card-text>
                        <hr>
                      </span>
                    </span>
                  </v-card>
                </v-dialog>

                <v-card-actions>
                  <v-spacer></v-spacer>

                  <v-btn
                    color="#000"
                    style="font-size: 1.05em; margin: 0 20px 20px;"
                    outlined
                    @click="dialogPro = false"
                  >
                    확인
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="4">
            <div class="accountTitle">
              대여 중
            </div>
          </v-col>
          <v-col cols="6" sm="8">
            <v-btn
              color="#8c28b4"
              outlined
              @click.stop="dialogCom = true"
            >
              <span style="color: #000; font-size: 1.2em; font-weight: 900;">
                {{contractComplete.length}}
              </span>

            </v-btn>

            <v-dialog
              v-model="dialogCom"
              max-width="550px"
            >
              <v-card class="contModal">
                <v-card-title class="modalTitle">
                  📚{{userName}}님이 대여 중인 물품 {{contractComplete.length}}건
                </v-card-title>
                <p v-if="false" style="text-align: end; margin-right: 20px;">
                  <span style="background-color: #D9FFF2; font-weight: 900; padding: 5px; border-radius: 50px;">제공자</span>
                  <span style="background-color: #F3FFD2; font-weight: 900; padding: 5px; border-radius: 50px;">대여자</span>
                </p>
                <v-card-text v-for="(cont, i) in contractComplete" :key="i" style="color: black; font-size: 1rem;">
                  <v-row justify="center">
                    <v-col cols="11" md="8">
                      <v-row justify="center">
                        <v-col cols="11" sm="4" class="d-flex justify-center align-center t-center">
                          <router-link :to="{name:'Detail', params:{boardId:cont.id}}">
                            {{cont.category}}
                          </router-link>
                          
                        </v-col>
                        <v-col cols="11" sm="3" class="d-flex justify-center align-center t-center">
                          {{cont.productName}}
                        </v-col>
                        <v-col cols="11" sm="4" class="d-flex justify-center align-center t-center">
                          <v-btn
                            color="#EFFDF8"
                            @click.stop="showDialogReview(cont)"
                            v-if="cont.role == '제공자'" 
                            class="distRolePro"
                            style="font-weight: 900;"
                          >
                            리뷰 작성
                          </v-btn>
                          <v-btn
                            color="#F9FDEF"
                            @click.stop="showDialogReview(cont)"
                            v-else class="distRoleBo"
                            style="font-weight: 900;"
                          >
                            리뷰 작성
                          </v-btn>
                        </v-col>
                      </v-row>
                      <hr style="background-color: #888; margin-top: 10px; border-radius: 50px;">  
                    </v-col>
                  </v-row>
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>

                  <v-btn
                    color="#000"
                    outlined
                    style="font-size: 1.05em; margin: 0 20px 20px;"
                    @click="dialogCom = false"
                  >
                    확인
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <v-row justify="center">
      <div class="bttn out cyann">
        <span @click="updateUser">
          수정하기
        </span>
        <div class="corners top"></div>
        <div class="corners bottom"></div>
      </div>
    </v-row>
    <v-dialog
      v-model="dialogReview"
      max-width="680px"
      >
      <v-card class="reviewModal">
        <v-card-title class="modalTitle" 
          v-if="data.role == '제공자'" >
          <a :href="data.path">[data.category]{{data.name}}</a>에 대한 리뷰를 남겨주세요
        </v-card-title>
        <v-card-title class="modalTitle" v-else>
          대여인!에 대한 리뷰를 남겨주세요
          <!-- {{offerData}} -->
        </v-card-title>
        <div class="starContainer">
          <div class="feedback">
            <div class="rating">
              <input type="radio" :name="'rating'+data.name" id="rating-5" value="5">
              <label for="rating-5"></label>
              <input type="radio" :name="'rating'+data.name" id="rating-4" value="4">
              <label for="rating-4"></label>
              <input type="radio" :name="'rating'+data.name" id="rating-3" value="3">
              <label for="rating-3"></label>
              <input type="radio" :name="'rating'+data.name" id="rating-2" value="2">
              <label for="rating-2"></label>
              <input type="radio" :name="'rating'+data.name" id="rating-1" value="1">
              <label for="rating-1"></label>
              <div class="emoji-wrapper">
                <div class="emoji">
                  <svg class="rating-0" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                    <circle cx="256" cy="256" r="256" fill="#ffd93b"/>
                    <path d="M512 256c0 141.44-114.64 256-256 256-80.48 0-152.32-37.12-199.28-95.28 43.92 35.52 99.84 56.72 160.72 56.72 141.36 0 256-114.56 256-256 0-60.88-21.2-116.8-56.72-160.72C474.8 103.68 512 175.52 512 256z" fill="#f4c534"/>
                    <ellipse transform="scale(-1) rotate(31.21 715.433 -595.455)" cx="166.318" cy="199.829" rx="56.146" ry="56.13" fill="#fff"/>
                    <ellipse transform="rotate(-148.804 180.87 175.82)" cx="180.871" cy="175.822" rx="28.048" ry="28.08" fill="#3e4347"/>
                    <ellipse transform="rotate(-113.778 194.434 165.995)" cx="194.433" cy="165.993" rx="8.016" ry="5.296" fill="#5a5f63"/>
                    <ellipse transform="scale(-1) rotate(31.21 715.397 -1237.664)" cx="345.695" cy="199.819" rx="56.146" ry="56.13" fill="#fff"/>
                    <ellipse transform="rotate(-148.804 360.25 175.837)" cx="360.252" cy="175.84" rx="28.048" ry="28.08" fill="#3e4347"/>
                    <ellipse transform="scale(-1) rotate(66.227 254.508 -573.138)" cx="373.794" cy="165.987" rx="8.016" ry="5.296" fill="#5a5f63"/>
                    <path d="M370.56 344.4c0 7.696-6.224 13.92-13.92 13.92H155.36c-7.616 0-13.92-6.224-13.92-13.92s6.304-13.92 13.92-13.92h201.296c7.696.016 13.904 6.224 13.904 13.92z" fill="#3e4347"/>
                  </svg>
                  <svg class="rating-1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                    <circle cx="256" cy="256" r="256" fill="#ffd93b"/>
                    <path d="M512 256A256 256 0 0 1 56.7 416.7a256 256 0 0 0 360-360c58.1 47 95.3 118.8 95.3 199.3z" fill="#f4c534"/>
                    <path d="M328.4 428a92.8 92.8 0 0 0-145-.1 6.8 6.8 0 0 1-12-5.8 86.6 86.6 0 0 1 84.5-69 86.6 86.6 0 0 1 84.7 69.8c1.3 6.9-7.7 10.6-12.2 5.1z" fill="#3e4347"/>
                    <path d="M269.2 222.3c5.3 62.8 52 113.9 104.8 113.9 52.3 0 90.8-51.1 85.6-113.9-2-25-10.8-47.9-23.7-66.7-4.1-6.1-12.2-8-18.5-4.2a111.8 111.8 0 0 1-60.1 16.2c-22.8 0-42.1-5.6-57.8-14.8-6.8-4-15.4-1.5-18.9 5.4-9 18.2-13.2 40.3-11.4 64.1z" fill="#f4c534"/>
                    <path d="M357 189.5c25.8 0 47-7.1 63.7-18.7 10 14.6 17 32.1 18.7 51.6 4 49.6-26.1 89.7-67.5 89.7-41.6 0-78.4-40.1-82.5-89.7A95 95 0 0 1 298 174c16 9.7 35.6 15.5 59 15.5z" fill="#fff"/>
                    <path d="M396.2 246.1a38.5 38.5 0 0 1-38.7 38.6 38.5 38.5 0 0 1-38.6-38.6 38.6 38.6 0 1 1 77.3 0z" fill="#3e4347"/>
                    <path d="M380.4 241.1c-3.2 3.2-9.9 1.7-14.9-3.2-4.8-4.8-6.2-11.5-3-14.7 3.3-3.4 10-2 14.9 2.9 4.9 5 6.4 11.7 3 15z" fill="#fff"/>
                    <path d="M242.8 222.3c-5.3 62.8-52 113.9-104.8 113.9-52.3 0-90.8-51.1-85.6-113.9 2-25 10.8-47.9 23.7-66.7 4.1-6.1 12.2-8 18.5-4.2 16.2 10.1 36.2 16.2 60.1 16.2 22.8 0 42.1-5.6 57.8-14.8 6.8-4 15.4-1.5 18.9 5.4 9 18.2 13.2 40.3 11.4 64.1z" fill="#f4c534"/>
                    <path d="M155 189.5c-25.8 0-47-7.1-63.7-18.7-10 14.6-17 32.1-18.7 51.6-4 49.6 26.1 89.7 67.5 89.7 41.6 0 78.4-40.1 82.5-89.7A95 95 0 0 0 214 174c-16 9.7-35.6 15.5-59 15.5z" fill="#fff"/>
                    <path d="M115.8 246.1a38.5 38.5 0 0 0 38.7 38.6 38.5 38.5 0 0 0 38.6-38.6 38.6 38.6 0 1 0-77.3 0z" fill="#3e4347"/>
                    <path d="M131.6 241.1c3.2 3.2 9.9 1.7 14.9-3.2 4.8-4.8 6.2-11.5 3-14.7-3.3-3.4-10-2-14.9 2.9-4.9 5-6.4 11.7-3 15z" fill="#fff"/>
                  </svg>
                  <svg class="rating-2" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                    <circle cx="256" cy="256" r="256" fill="#ffd93b"/>
                    <path d="M512 256A256 256 0 0 1 56.7 416.7a256 256 0 0 0 360-360c58.1 47 95.3 118.8 95.3 199.3z" fill="#f4c534"/>
                    <path d="M336.6 403.2c-6.5 8-16 10-25.5 5.2a117.6 117.6 0 0 0-110.2 0c-9.4 4.9-19 3.3-25.6-4.6-6.5-7.7-4.7-21.1 8.4-28 45.1-24 99.5-24 144.6 0 13 7 14.8 19.7 8.3 27.4z" fill="#3e4347"/>
                    <path d="M276.6 244.3a79.3 79.3 0 1 1 158.8 0 79.5 79.5 0 1 1-158.8 0z" fill="#fff"/>
                    <circle cx="340" cy="260.4" r="36.2" fill="#3e4347"/>
                    <g fill="#fff">
                      <ellipse transform="rotate(-135 326.4 246.6)" cx="326.4" cy="246.6" rx="6.5" ry="10"/>
                      <path d="M231.9 244.3a79.3 79.3 0 1 0-158.8 0 79.5 79.5 0 1 0 158.8 0z"/>
                    </g>
                    <circle cx="168.5" cy="260.4" r="36.2" fill="#3e4347"/>
                    <ellipse transform="rotate(-135 182.1 246.7)" cx="182.1" cy="246.7" rx="10" ry="6.5" fill="#fff"/>
                  </svg>
                  <svg class="rating-3" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                    <circle cx="256" cy="256" r="256" fill="#ffd93b"/>
                    <path d="M407.7 352.8a163.9 163.9 0 0 1-303.5 0c-2.3-5.5 1.5-12 7.5-13.2a780.8 780.8 0 0 1 288.4 0c6 1.2 9.9 7.7 7.6 13.2z" fill="#3e4347"/>
                    <path d="M512 256A256 256 0 0 1 56.7 416.7a256 256 0 0 0 360-360c58.1 47 95.3 118.8 95.3 199.3z" fill="#f4c534"/>
                    <g fill="#fff">
                      <path d="M115.3 339c18.2 29.6 75.1 32.8 143.1 32.8 67.1 0 124.2-3.2 143.2-31.6l-1.5-.6a780.6 780.6 0 0 0-284.8-.6z"/>
                      <ellipse cx="356.4" cy="205.3" rx="81.1" ry="81"/>
                    </g>
                    <ellipse cx="356.4" cy="205.3" rx="44.2" ry="44.2" fill="#3e4347"/>
                    <g fill="#fff">
                      <ellipse transform="scale(-1) rotate(45 454 -906)" cx="375.3" cy="188.1" rx="12" ry="8.1"/>
                      <ellipse cx="155.6" cy="205.3" rx="81.1" ry="81"/>
                    </g>
                    <ellipse cx="155.6" cy="205.3" rx="44.2" ry="44.2" fill="#3e4347"/>
                    <ellipse transform="scale(-1) rotate(45 454 -421.3)" cx="174.5" cy="188" rx="12" ry="8.1" fill="#fff"/>
                  </svg>
                  <svg class="rating-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                    <circle cx="256" cy="256" r="256" fill="#ffd93b"/>
                    <path d="M512 256A256 256 0 0 1 56.7 416.7a256 256 0 0 0 360-360c58.1 47 95.3 118.8 95.3 199.3z" fill="#f4c534"/>
                    <path d="M232.3 201.3c0 49.2-74.3 94.2-74.3 94.2s-74.4-45-74.4-94.2a38 38 0 0 1 74.4-11.1 38 38 0 0 1 74.3 11.1z" fill="#e24b4b"/>
                    <path d="M96.1 173.3a37.7 37.7 0 0 0-12.4 28c0 49.2 74.3 94.2 74.3 94.2C80.2 229.8 95.6 175.2 96 173.3z" fill="#d03f3f"/>
                    <path d="M215.2 200c-3.6 3-9.8 1-13.8-4.1-4.2-5.2-4.6-11.5-1.2-14.1 3.6-2.8 9.7-.7 13.9 4.4 4 5.2 4.6 11.4 1.1 13.8z" fill="#fff"/>
                    <path d="M428.4 201.3c0 49.2-74.4 94.2-74.4 94.2s-74.3-45-74.3-94.2a38 38 0 0 1 74.4-11.1 38 38 0 0 1 74.3 11.1z" fill="#e24b4b"/>
                    <path d="M292.2 173.3a37.7 37.7 0 0 0-12.4 28c0 49.2 74.3 94.2 74.3 94.2-77.8-65.7-62.4-120.3-61.9-122.2z" fill="#d03f3f"/>
                    <path d="M411.3 200c-3.6 3-9.8 1-13.8-4.1-4.2-5.2-4.6-11.5-1.2-14.1 3.6-2.8 9.7-.7 13.9 4.4 4 5.2 4.6 11.4 1.1 13.8z" fill="#fff"/>
                    <path d="M381.7 374.1c-30.2 35.9-75.3 64.4-125.7 64.4s-95.4-28.5-125.8-64.2a17.6 17.6 0 0 1 16.5-28.7 627.7 627.7 0 0 0 218.7-.1c16.2-2.7 27 16.1 16.3 28.6z" fill="#3e4347"/>
                    <path d="M256 438.5c25.7 0 50-7.5 71.7-19.5-9-33.7-40.7-43.3-62.6-31.7-29.7 15.8-62.8-4.7-75.6 34.3 20.3 10.4 42.8 17 66.5 17z" fill="#e24b4b"/>
                  </svg>
                  <svg class="rating-5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512">
                    <g fill="#ffd93b">
                      <circle cx="256" cy="256" r="256"/>
                      <path d="M512 256A256 256 0 0 1 56.8 416.7a256 256 0 0 0 360-360c58 47 95.2 118.8 95.2 199.3z"/>
                    </g>
                    <path d="M512 99.4v165.1c0 11-8.9 19.9-19.7 19.9h-187c-13 0-23.5-10.5-23.5-23.5v-21.3c0-12.9-8.9-24.8-21.6-26.7-16.2-2.5-30 10-30 25.5V261c0 13-10.5 23.5-23.5 23.5h-187A19.7 19.7 0 0 1 0 264.7V99.4c0-10.9 8.8-19.7 19.7-19.7h472.6c10.8 0 19.7 8.7 19.7 19.7z" fill="#e9eff4"/>
                    <path d="M204.6 138v88.2a23 23 0 0 1-23 23H58.2a23 23 0 0 1-23-23v-88.3a23 23 0 0 1 23-23h123.4a23 23 0 0 1 23 23z" fill="#45cbea"/>
                    <path d="M476.9 138v88.2a23 23 0 0 1-23 23H330.3a23 23 0 0 1-23-23v-88.3a23 23 0 0 1 23-23h123.4a23 23 0 0 1 23 23z" fill="#e84d88"/>
                    <g fill="#38c0dc">
                      <path d="M95.2 114.9l-60 60v15.2l75.2-75.2zM123.3 114.9L35.1 203v23.2c0 1.8.3 3.7.7 5.4l116.8-116.7h-29.3z"/>
                    </g>
                    <g fill="#d23f77">
                      <path d="M373.3 114.9l-66 66V196l81.3-81.2zM401.5 114.9l-94.1 94v17.3c0 3.5.8 6.8 2.2 9.8l121.1-121.1h-29.2z"/>
                    </g>
                    <path d="M329.5 395.2c0 44.7-33 81-73.4 81-40.7 0-73.5-36.3-73.5-81s32.8-81 73.5-81c40.5 0 73.4 36.3 73.4 81z" fill="#3e4347"/>
                    <path d="M256 476.2a70 70 0 0 0 53.3-25.5 34.6 34.6 0 0 0-58-25 34.4 34.4 0 0 0-47.8 26 69.9 69.9 0 0 0 52.6 24.5z" fill="#e24b4b"/>
                    <path d="M290.3 434.8c-1 3.4-5.8 5.2-11 3.9s-8.4-5.1-7.4-8.7c.8-3.3 5.7-5 10.7-3.8 5.1 1.4 8.5 5.3 7.7 8.6z" fill="#fff" opacity=".2"/>
                  </svg>
                </div>
              </div>
            </div>
          </div>
        </div>
        <v-card-text style="padding-bottom: 0px;">
          <v-textarea v-model="review.review" color="#8c28b4" placeholder="간단한 후기를 작성해주세요.">
          </v-textarea>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            color="#8c28b4"
            style="font-size: 1.05em; margin: 20px;"
            outlined
            @click="dialogReview = false; getChecked();"
          >
            확인
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog
      v-model="dialogOffer"
      max-width="680px"
      >
      <v-card class="contModal">
        <v-card-title class="modalTitle">
          📖{{userName}}님의 대여 요청 {{offers.length}}건
        </v-card-title>
        <p v-if="false" style="text-align: end; margin-right: 20px;">
          <span style="background-color: #D9FFF2; font-weight: 900; padding: 5px; border-radius: 50px;">제공자</span>
          <span style="background-color: #F3FFD2; font-weight: 900; padding: 5px; border-radius: 50px;">대여자</span>
        </p>
        <v-card-text v-for="(offer, i) in offers" :key="i" style="color: black; font-size: 1rem;">
          <v-row justify="center">
            <v-col cols="11" md="8">
              <v-row justify="center">
                <v-col cols="11" sm="4" class="d-flex justify-center align-center t-center">
                  <router-link :to="{name:'Detail', params:{boardId:offer.boardId}}">
                    {{offer.boardId}}
                  </router-link>
                </v-col>
                <v-col cols="11" sm="3" class="d-flex justify-center align-center t-center">
                  {{ offer.borrowSrt }}
                </v-col>
                <v-col cols="11" sm="4" class="d-flex justify-center align-center t-center">
                  {{offer.borrowEnd}}
                </v-col>
              </v-row>
                <hr style="background-color: #888; margin-top: 10px; border-radius: 50px;">  

            </v-col>
          </v-row>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn
            color="#000"
            style="font-size: 1.05em; margin: 0 20px 20px;"
            outlined
            @click="dialogOffer = false"
          >
            확인
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script lang="ts">
  import { Component, Vue, Watch } from 'vue-property-decorator'
  import cookie from "@/cookie" 
  import baseURL from '../../base-url';
  import { TradeRules } from '@/models/rules/TradeRules'
  import { OfferRules } from '@/models/rules/OfferRules'
  import { OfferData } from '@/models/rules/OfferData'
  import { ChainData } from '@/models/rules/ChainData'

  @Component
  export default class Account extends Vue {
    private userPassword = '비밀번호 변경';
    private newPassword = '';
    private userName = '';
    private userContact = '';
    private dialog = false;
    private userAddress = '';
    private averagePoint = 5;
    private contractProgress: TradeRules[] = [];
    private contractComplete: TradeRules[] = [];
    private contracts: TradeRules[] = [];
    private dialogOffer = false;
    private dialogPro = false;
    private dialogCom = false;
    private dialogReview = false;
    private dialogReviewPro = false;
    private data: TradeRules = {};
    private offerData: OfferData = {};
    private consumerData: OfferData[] = [];
    private chainData: ChainData[] = [];
    private offers: OfferRules[] = [];
    private review: object = {};
    private chk = 0;
    private chkTrade = 0;
    private finalPrice = 0;

    public appendInput(target: HTMLElement): void {
      const appendTarget: HTMLElement | null = target.parentElement;
      if (appendTarget && appendTarget.firstElementChild && appendTarget.lastElementChild) {
        appendTarget.firstElementChild.classList.add("accountUpdateDisplay")
        appendTarget.lastElementChild.classList.remove("accountUpdateDisplay")
      }
    }
    public closeInput(target: HTMLElement): void {
      const tempTarget: HTMLElement | null = target.parentElement;
      
      if (tempTarget) {
        const removeTarget: HTMLElement | null = (tempTarget.parentElement as HTMLElement).parentElement;
        if (removeTarget && removeTarget.firstElementChild && removeTarget.lastElementChild) {
          removeTarget.firstElementChild.classList.remove("accountUpdateDisplay")
          removeTarget.lastElementChild.classList.add("accountUpdateDisplay")
        }
      }
    }
    public showDialogReview(cont: TradeRules): void {
      if (cont.arr && cont.arr.length) {
        baseURL('/chains')
          .then(res => {
            this.chainData = res.data;
          })
        for (let i=0; i < cont.arr.length; i++) {
          for (let j=0; j < this.chainData.length; j++) {
            if (cont.arr[i].id == this.chainData[j].tradeId) {
              // cont.arr[i].borrowSrt = cont.arr[i].borrowSrt?.slice(0, 10);
              // cont.arr[i].borrowEnd = cont.arr[i].borrowEnd?.slice(0, 10);
              this.offerData = cont.arr[i]
            }
          }
        }
        if (this.offerData.id) {

          this.data = cont;
          this.dialogReview = true;
        }
      } else {
        alert("아직 대여 요청이 없습니다.")
      }
    }
    public showDialogReviewPro(cont: TradeRules): void {
      if (cont.arr && cont.arr.length) {
        for (let i=0; i < cont.arr.length; i++) {
          baseURL('/users/'+cont.arr[i].consumerId)
            .then(res => {
              this.consumerData.push(res.data);
            })
          cont.arr[i].borrowSrt = cont.arr[i].borrowSrt?.slice(0, 10);
          cont.arr[i].borrowEnd = cont.arr[i].borrowEnd?.slice(0, 10);
        }
        this.data = cont;
        // if (this.consumerData.length) {
        //   } 
          this.dialogReviewPro = true;
      } else {
        alert("아직 대여 요청이 없습니다.")
      }
      }
    public messagesReset(): void {
      this.$store.commit('messagesReset');
    }
    public getChecked(): void {
      const checkedV = document.getElementsByClassName('rating')[0].getElementsByTagName("input");
      for (let i = 0; i < checkedV.length; i++) {
        if (checkedV[i].type === 'radio' && checkedV[i].checked) {
          const cValue = checkedV[i].value;
          console.log(cValue)
          document.getElementsByClassName('rating')[0].getElementsByTagName("input")[i].checked=false;
        }
      }
    }
    public updateUser(): void {
      if (cookie.cookieId() && this.userAddress && this.userName) {
        const data = {
          id: cookie.cookieId(),
          location: this.userAddress,
          name: this.userName,
          password: this.newPassword,
          phone: this.userContact
        }
        const loginData = {
          token: cookie.token() as string,
          id: Number(data.id),
          location: data.location,
          name: data.name,
          password: data.password,
          phone: data.phone,
          email: ''
        }
        if (loginData.token) {
          baseURL.put('/users', data)
            .then(() => {
              cookie.setCookie(loginData);
              this.$store.commit('loggedIn', loginData);
              this.$store.commit('isLogin');
              alert("정보 수정이 완료되었습니다.")
            })
            .catch(() => {
              alert("잘못된 시도입니다.")
            })
        }
      }
    }
    @Watch('contracts')
    checkContracts() {
      if (this.contracts.length == this.chk) {
        for (let i = 0; i < this.contracts.length; i++) {
          if (this.contracts[i].state == "READY") {
            if (this.contracts[i].borrowableEnd && this.contracts[i].borrowableSrt) {
              this.contracts[i].borrowableEnd = this.contracts[i].borrowableEnd?.slice(0, 10);
              this.contracts[i].borrowableSrt = this.contracts[i].borrowableSrt?.slice(0, 10);
            }
            this.contracts[i].registDate = this.contracts[i].registDate?.slice(0, 10);
            baseURL('/boards/user/'+this.contracts[i].id+'/requests')
              .then(res=>{
                this.contracts[i].arr = res.data;
                // if (res.data.length) {
                //   for (let j = 0; j < res.data.length; j++) {
                //     console.log(i, 'd')
                //     baseURL('/users/'+res.data[j].consumerId)
                //     .then(response => {
                //       this.contracts[i].arr = response.data;
                //       })
                //   }
                // } 
              this.contractProgress.push(this.contracts[i]);
            })
          } else {
            if (this.contracts[i].borrowableEnd && this.contracts[i].borrowableSrt) {
              this.contracts[i].borrowableEnd = this.contracts[i].borrowableEnd?.slice(0, 10);
              this.contracts[i].borrowableSrt = this.contracts[i].borrowableSrt?.slice(0, 10);
            }
            this.contracts[i].registDate = this.contracts[i].registDate?.slice(0, 10);
            baseURL('/boards/user/'+this.contracts[i].id+'/requests')
              .then(res=>{
                this.contracts[i].arr = res.data;
              this.contractComplete.push(this.contracts[i])
            })
          }
        }
      }
    }
    @Watch('dialogReviewPro')
    dialogReviewProClosed() {
      if (!this.dialogReviewPro) {
        this.data = {};
        this.consumerData = [];
        this.offerData = {};
      }
    }
    @Watch('dialogReview')
    dialogReviewClosed() {
      if (!this.dialogReview) {
        this.data = {};
        this.consumerData = [];
        this.offerData = {};
      }
    }
    public getContracts(): void {
      baseURL('/users/user/boards?page=0&size=10')
        .then(res=> {
          // content의 board state를 보고 진행 중 거래와 진행 완료 거래로 나누기
          const totalPages = res.data.totalPages;
          this.contracts = res.data.content;
          this.chk = res.data.totalElements;
          if (totalPages > 1) {
            for (let i = 1; i < totalPages; i++) {
              baseURL('/users/user/boards?page='+i+'&size=10')
              .then(response => {
                this.contracts = this.contracts.concat(response.data.content);
              })
            }
          }
        })
    }
    public getTrades(): void {
      baseURL('/users/user/trades?page=0&size=10')
        .then(res=> {
          console.log(res)
          const totalPages = res.data.totalPages;
          this.offers = res.data.content;
          this.chkTrade = res.data.totalElements;
          if (totalPages > 1) {
            for (let i = 1; i < totalPages; i++) {
              baseURL('/users/user/trades?page='+i+'&size=10')
              .then(response => {
                this.offers = this.offers.concat(response.data.content);
              })
            }
          }
        })
    }
    public acceptOffer(id: number): void {
      if (confirm("이 요청을 수락하시겠습니까?")) {
        baseURL.put('/boards/user/requests/'+id)
        .then(() => {
          alert("요청을 수락하셨습니다")
          this.dialogPro = false;
          this.dialogReviewPro = false;
        })
        .catch(() => {
          alert("잘못된 시도입니다")
        })
      }
    }
    mounted() {
      this.$forceUpdate();
      this.getContracts();
      baseURL('/users/user')
      .then(res => {
        this.userName = res.data.name;
        this.newPassword = res.data.password;
        this.userContact = res.data.phone;
        this.userAddress = res.data.location;
      })
      baseURL('/users/'+cookie.cookieId())
      .then(res=>{
        this.averagePoint = res.data.score;
      })
      this.getTrades();
    }
  }
</script>

<style lang="scss">
.accountWrap {
  width: 100%;
  height: 100%;
  overflow-x: hidden;
  position: relative;
  // background-image: url('../../assets/images/goldBorder.png');
  background-size: cover;
  
}


.vBtn {
  position: relative;
  min-width: 50px !important;
  padding: 0 !important;
  span.v-btn__content {
    height: 36px;
  }
}
.contractCol {
  color: #000 !important;
  font-weight: 900;
}
.container {
  .attrs {
    margin-top: 20px; 
    background-color: rgba(255, 255, 255, 0.705);
    position: relative;
    // border-top: 5px solid;
    // border-image: linear-gradient(to left, #be85ff, rgb(255, 238, 0));
    // border-image-slice: 1;
    &:before {
      content: '';
      position: absolute;
      top: 0px;
      left: 0px;
      height: 3px;
      width: 100%;
      border-radius: 5px;
      background: linear-gradient(-45deg, #d68cf3, #ffd986, #ff96d3);
      background-size: 400% 400%;
      animation: gradient 15s ease infinite;
    }
    .accountTitle {
      padding: 0.5rem 2rem;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      transition: 0.25s;
      @for $i from 1 through 10 {
        &:nth-child(#{$i}) {
          font-family: 'ON-IGothic'; 
          font-size: 1.5rem;
          font-weight: 900;
          background-image: linear-gradient(to bottom, rgb(230, 155, 85), rgb(178, 108, 185), rgb(65, 166, 173));
          background-attachment: fixed;
          -webkit-background-clip: text;
          -webkit-text-fill-color: transparent;
          color: black;
          // opacity: 0.7;
          transition: 0.25s;
          }
      }
      h3 {
        position: relative;
        color: #3d3d3d;
        transition: 0.25s;
        cursor: pointer;
      }
      
      &:hover {
        background-color: #fafafa;
        &:before {
          // opacity: 0.7;
        }
      }
    }
    .updateInput {
      position: relative;
      margin-right: 1.5rem;
      // left: 3rem;
      transition: 0.25s;
      border-bottom: 1px solid black;
    }
  }
}
@keyframes gradient {
	0% {
		background-position: 0% 50%;
	}
	100% {
		background-position: 100% 50%;
	}
	100% {
		background-position: 0% 50%;
	}
}
.accountTitle + .accountTitle {
  // border-top: 1px solid rgba(197, 197, 197, 0.9);
}
.accountUpdateDisplay {
  display: none;
}
.cursorE {
  cursor: pointer;
}

.modalTitle {
  text-shadow: 0px 0px 1px #888;
}
.t-center {
  text-align: center;
}
.v-overlay__scrim {
  background-color: #fff !important;
}
.contModal {
  border: solid #fff9d6 !important;
}
.reviewModal {
  // background-color:rgb(252, 252, 237) !important;
  border-top: 5px solid #8905e0 !important;
  // border-bottom: 5px solid #b695cc !important;
}

@mixin borderGradient($from, $to, $weight: 0) {
  $mix-main: mix($from, $to);
  $mix-sub-from: mix($mix-main, $from);
  $mix-sub-to: mix($mix-main, $to);
  
  box-shadow: 0 1px 0 $weight rgba($mix-sub-to, .25),
              0 -1px 0 $weight rgba($mix-sub-from, .25),
              1px 0 0 $weight rgba($mix-sub-to, .25),
              -1px 0 0 $weight  rgba($mix-sub-from, .25),
              1px -1px 0 $weight rgba($mix-main, .5),
              -1px 1px 0 $weight rgba($mix-main, .5),
              1px 1px 0 $weight rgba($to, .75),
              -1px -1px 0 $weight rgba($from, .75);
}

/// BASIC EXAMPLE ///

.circle {
  border-radius: 100%;
  border: borderGradient(red, yellow) !important;
}

// settings
$text-color: #FFB902;
$line-color: #FFB902;
$line-size: 7;

//
//  Corners mixin (MAGIC BE HERE!)
//
@mixin line-corners($line-color: cyan, $line-size: 7, $line-type: out, $line-distance: 20) {
  position: absolute;
  width: 100%;
  
  transition-duration: 0.3s;
  
  // arrows
  &:before,
  &:after {
    content: '';
    
    position: absolute;
    
    width: $line-size + px;
    height: $line-size + px;
    
    border-color: $line-color;
    border-style: solid;
    
    transition-duration: 0.3s;
    transform: translateZ(0);
  }
  
  // top corners
  &.top {
    &:before { border-width: 1px 0 0 1px; }
    &:after { border-width: 1px 1px 0 0; }
  }
  
  // bottom corners
  &.bottom {
    &:before { border-width: 0 0 1px 1px; }
    &:after { border-width: 0 1px 1px 0; }
  }
  
  @if $line-type == out {
    &:before { left: 0; }
    &:after { right: 0; }
    &.top { top: 0; }
    &.bottom { bottom: $line-size + px; }    
  }
  
  @if $line-type == in {
    &:before { left: $line-distance + px; }
    &:after { right: $line-distance + px; }
    &.top { top: $line-distance + px; }
    &.bottom { bottom: ($line-distance + $line-size) + px; }
  }
}

@mixin line-corners-hover($line-size: 20, $line-type: out, $line-distance: 6) {
  @if $line-type == out {
    &:before { left: 0; }
    &:after { right: 0; }
    &.top { top: 0; }
    &.bottom { bottom: $line-size + px; }
  }
  
  @if $line-type == in {
    &:before { left: $line-distance + px; }
    &:after { right: $line-distance + px; }
    &.top { top: $line-distance + px; }
    &.bottom { bottom: ($line-distance + $line-size) + px; }
  }
}

// style: borders out (edge)
@mixin line-corners-out($line-color: cyan, $line-size: 7) {
  .corners { @include line-corners($line-color, $line-size, out); }
  &:hover {
    color: #8905e0;
    .corners { @include line-corners-hover($line-size, in); }
  }
}
  
// style: borders in
@mixin line-corners-in($line-color: cyan, $line-size: 7) {
  .corners { @include line-corners($line-color, $line-size, in); }
  &:hover {
    .corners { @include line-corners-hover($line-size, out); }
  }
}
//
// @end: Corners mixin
//

//
// magic buttons
//
.bttn {
  display: inline-block;
  vertical-align: middle;
  position: relative;
  
  margin: 20px;

  // text
  span {
    display: block;
    padding: 10px;
    font-family: 'ON-IGothic'; 
    font-size: 20px;
    font-weight: 900;
    color: $text-color;
    text-transform: uppercase;

    transition: all 0.3s ease-out;
  }
  
  &:hover {
    cursor: pointer;
  }
  
  // include corners
  &.out {
    @include line-corners-out(cyan);
  }
  
  &.in {
    @include line-corners-in($line-color);
  }
}

.bttn {
  // opacity: 0;

  // will-change: transform, opacity;
  // animation: fadeIn 0.6s 0.5s forwards ease-out;
  
  // cyan theme
  &.cyann {
    $theme-color: #8b3182;
    span { color: $theme-color; }
    .corners {
      &:after,
      &:before { border-color: $theme-color; }
    }
    &:hover {
      span {
        color: #52196d;
      }
    }
  }
}

//
//  Animations
//
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(50%);
  }
  
  to {
    opacity: 1;
    transform: translateY(0%);
  }
}
.starContainer {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: center;
  padding: 0 20px;
  }

  .rating {
    display: flex;
    width: 100%;
    justify-content: center;
    overflow: hidden;
    flex-direction: row-reverse;
    height: 150px;
    position: relative;
  }

  .rating-0 {
    filter: grayscale(100%);
  }

  .rating > input {
    display: none;
  }

  .rating > label {
    cursor: pointer;
    width: 40px;
    height: 40px;
    margin-top: auto;
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' width='126.729' height='126.73'%3e%3cpath fill='%23e3e3e3' d='M121.215 44.212l-34.899-3.3c-2.2-.2-4.101-1.6-5-3.7l-12.5-30.3c-2-5-9.101-5-11.101 0l-12.4 30.3c-.8 2.1-2.8 3.5-5 3.7l-34.9 3.3c-5.2.5-7.3 7-3.4 10.5l26.3 23.1c1.7 1.5 2.4 3.7 1.9 5.9l-7.9 32.399c-1.2 5.101 4.3 9.3 8.9 6.601l29.1-17.101c1.9-1.1 4.2-1.1 6.1 0l29.101 17.101c4.6 2.699 10.1-1.4 8.899-6.601l-7.8-32.399c-.5-2.2.2-4.4 1.9-5.9l26.3-23.1c3.8-3.5 1.6-10-3.6-10.5z'/%3e%3c/svg%3e");
    background-repeat: no-repeat;
    background-position: center;
    background-size: 76%;
    transition: .3s;
  }

  .rating > input:checked ~ label,
  .rating > input:checked ~ label ~ label {
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' width='126.729' height='126.73'%3e%3cpath fill='%23fcd93a' d='M121.215 44.212l-34.899-3.3c-2.2-.2-4.101-1.6-5-3.7l-12.5-30.3c-2-5-9.101-5-11.101 0l-12.4 30.3c-.8 2.1-2.8 3.5-5 3.7l-34.9 3.3c-5.2.5-7.3 7-3.4 10.5l26.3 23.1c1.7 1.5 2.4 3.7 1.9 5.9l-7.9 32.399c-1.2 5.101 4.3 9.3 8.9 6.601l29.1-17.101c1.9-1.1 4.2-1.1 6.1 0l29.101 17.101c4.6 2.699 10.1-1.4 8.899-6.601l-7.8-32.399c-.5-2.2.2-4.4 1.9-5.9l26.3-23.1c3.8-3.5 1.6-10-3.6-10.5z'/%3e%3c/svg%3e");
  }


  .rating > input:not(:checked) ~ label:hover,
  .rating > input:not(:checked) ~ label:hover ~ label {
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' width='126.729' height='126.73'%3e%3cpath fill='%23d8b11e' d='M121.215 44.212l-34.899-3.3c-2.2-.2-4.101-1.6-5-3.7l-12.5-30.3c-2-5-9.101-5-11.101 0l-12.4 30.3c-.8 2.1-2.8 3.5-5 3.7l-34.9 3.3c-5.2.5-7.3 7-3.4 10.5l26.3 23.1c1.7 1.5 2.4 3.7 1.9 5.9l-7.9 32.399c-1.2 5.101 4.3 9.3 8.9 6.601l29.1-17.101c1.9-1.1 4.2-1.1 6.1 0l29.101 17.101c4.6 2.699 10.1-1.4 8.899-6.601l-7.8-32.399c-.5-2.2.2-4.4 1.9-5.9l26.3-23.1c3.8-3.5 1.6-10-3.6-10.5z'/%3e%3c/svg%3e");
  }

  .emoji-wrapper {
    width: 100%;
    text-align: center;
    height: 100px;
    overflow: hidden;
    position: absolute;
    top: 0;
    left: 0;
  }

  .emoji-wrapper:before,
  .emoji-wrapper:after{
    content: "";
    height: 15px;
    width: 100%;
    position: absolute;
    left: 0;
    z-index: 1;
  }

  .emoji-wrapper:before {
    top: 0;
    background: linear-gradient(to bottom, rgba(255,255,255,1) 0%,rgba(255,255,255,1) 35%,rgba(255,255,255,0) 100%);
  }

  .emoji-wrapper:after{
    bottom: 0;
    background: linear-gradient(to top, rgba(255,255,255,1) 0%,rgba(255,255,255,1) 35%,rgba(255,255,255,0) 100%);
  }

  .emoji {
    display: flex;
    flex-direction: column;
    align-items: center;
    transition: .3s;
  }

  .emoji > svg {
    margin: 15px 0; 
    width: 70px;
    height: 70px;
    flex-shrink: 0;
  }

  #rating-1:checked ~ .emoji-wrapper > .emoji { transform: translateY(-100px); }
  #rating-2:checked ~ .emoji-wrapper > .emoji { transform: translateY(-200px); }
  #rating-3:checked ~ .emoji-wrapper > .emoji { transform: translateY(-300px); }
  #rating-4:checked ~ .emoji-wrapper > .emoji { transform: translateY(-400px); }
  #rating-5:checked ~ .emoji-wrapper > .emoji { transform: translateY(-500px); }

  .feedback {
    max-width: 360px;
    background-color: #fff;
    width: 100%;
    padding: 30px;
    border-radius: 8px;
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    align-items: center;
    box-shadow: 0 4px 30px rgba(0,0,0,.05);
  }

  .heart {
    position: relative;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    width: 170px;
    height: 30px;
    padding: 5px 10px;
    margin: auto;
    border-radius: 30px;
    background: #FFF;
    display: block;
    overflow: hidden;
    
    box-shadow: 0 1px #CCC,
                0 5px #DDD,
                0 9px 6px -3px #999;
    
    unicode-bidi: bidi-override;
    direction: rtl;
  }
  .heart:not(:checked) > input {
    display: none;
  }
  #like {
  }
  #like:not(:checked) > label {
    cursor:pointer;
    float: right;
    width: 30px;
    height: 30px;
    display: block;
    
    color: rgba(233, 54, 40, .4);
    line-height: 33px;
    text-align: center;
  }
  #like:not(:checked) > label:hover,
  #like:not(:checked) > label:hover ~ label {
    color: rgba(233, 54, 40, .6);
  }
  #like > input:checked + label:hover,
  #like > input:checked + label:hover ~ label,
  #like > input:checked ~ label:hover,
  #like > input:checked ~ label:hover ~ label,
  #like > label:hover ~ input:checked ~ label {
    color: rgba(233, 54, 40, .8);
  }
  #like > input:checked ~ label {
    color: rgb(233, 54, 40);
  }
</style>
