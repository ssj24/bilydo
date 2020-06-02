<template>
  <v-container>
    <div style="text-align: right; margin-right: 1rem;">
      <h2 style="font-family: 'ON-IGothic'; font-weight: 900; display: inline-block;">
        {{userName}}
      </h2>
      <h3 style="font-family: 'ON-IGothic'; display: inline-block;">님의 정보</h3>
    </div>
    
    <v-row justify="center">
      <v-col cols="12" sm="10" class="attrs">
        <v-row align="center">
          <v-col cols="6" sm="4">
            <div class="accountTitle">
              비밀번호
            </div>
          </v-col>
          <v-col cols="6" sm="8">
            <h3 @click="appendInput($event.target)">
              {{ userPassword }}
            </h3>
            <div class="accountUpdateDisplay">
              <input type="text" v-model="userPassword">
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
            <h3 @click="appendInput($event.target)">
              {{ userName }}
            </h3>
            <div class="accountUpdateDisplay">
              <input type="text" v-model="userName">
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
            <h3 @click="appendInput($event.target)">
              {{ userContact }}
            </h3>
            <div class="accountUpdateDisplay">
              <input type="text" v-model="userContact">
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
            <h3 @click="appendInput($event.target)">
              {{ userAddress }}
            </h3>
            <div class="accountUpdateDisplay">
              <input type="text" v-model="userAddress">
              <v-btn class="vBtn" @click="closeInput($event.target)">확인</v-btn>
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
            {{averagePoint}}
          </v-col>
        </v-row>
        <v-row align="center">
          <v-col cols="6" sm="4">
            <div class="accountTitle">
              진행 거래
            </div>
          </v-col>
          <v-col cols="6" sm="8">
            <v-btn
              color="#8c28b4"
              dark
              @click.stop="dialogPro = true"
            >
              {{contractProgress.length}}
            </v-btn>

            <v-dialog
              v-model="dialogPro"
              max-width="80%"
            >
              <v-card>
                <v-card-title class="modalTitle">
                  {{userName}}님의 진행 중인 거래 {{contractProgress.length}}건
                </v-card-title>

                <v-card-text v-for="(cont, i) in contractProgress" :key="i">
                  <a :href="cont.path">
                    {{cont.name}}
                  </a>
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>

                  <v-btn
                    color="#8c28b4"
                    text
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
              완료 거래
            </div>
          </v-col>
          <v-col cols="6" sm="8">
            <v-btn
              color="#8c28b4"
              dark
              @click.stop="dialogCom = true"
            >
              {{contractComplete.length}}
            </v-btn>

            <v-dialog
              v-model="dialogCom"
              max-width="80%"
            >
              <v-card>
                <v-card-title class="modalTitle">
                  {{userName}}님의 완료된 거래 {{contractComplete.length}}건
                </v-card-title>

                <v-card-text v-for="(cont, i) in contractComplete" :key="i">
                  <a :href="cont.path">
                    {{cont.name}}
                  </a>
                </v-card-text>

                <v-card-actions>
                  <v-spacer></v-spacer>

                  <v-btn
                    color="#8c28b4"
                    outlined
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
        <span>
          수정하기
        </span>
        <div class="corners top"></div>
        <div class="corners bottom"></div>
      </div>
    </v-row>
  </v-container>
</template>

<script lang="ts">
  import { Component, Vue } from 'vue-property-decorator'

  @Component
  export default class Account extends Vue {
    private userPassword = '********';
    private userName = 'SSA인';
    private userContact = '010-1234-5678';
    private userAddress = '대전시 유성구 전민동';
    private averagePoint = 5;
    private contractProgress: object[] = [
      {
        name: '1',
        path: '/',
      }
    ];
    private contractComplete: object[] = [
      {
        name: '1',
        path: '/',
      },
      {
        name: '2',
        path: '/',
      },
      {
        name: '3',
        path: '/',
      },
      {
        name: '4',
        path: '/',
      },
    ];
    private dialogPro = false;
    private dialogCom = false;

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
  }
</script>

<style lang="scss">
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
      input {
        position: relative;
        margin-right: 1.5rem;
        // left: 3rem;
        transition: 0.25s;
        border-bottom: 1px solid black;
      }
      &:hover {
        background-color: #fafafa;
        &:before {
          // opacity: 0.7;
        }
        h3 {
        }
        input {
        }
      }
    }
    
  }
}
.accountTitle + .accountTitle {
  // border-top: 1px solid rgba(197, 197, 197, 0.9);
}
.accountUpdateDisplay {
  display: none;
}

.modalTitle {
  text-shadow: 0px 0px 1px #888;
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

@mixin line-corners-hover($line-size: 7, $line-type: out, $line-distance: 6) {
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
  //opacity: 0;

  //will-change: transform, opacity;
  //animation: fadeIn 0.6s 0.5s forwards ease-out;
  
  // cyan theme
  &.cyann {
    $theme-color: rgb(63, 9, 58);
    span { color: $theme-color; }
    .corners {
      &:after,
      &:before { border-color: $theme-color; }
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
</style>
