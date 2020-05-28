<template>
  <v-container>
    <h1 style="display: inline-block;">
      {{userName}}
    </h1>
    <h3 style="display: inline-block;">님</h3>
    <v-row>
      <v-col cols="12" sm="10" class="attrs">
        
        <div class="accountTitle" @click="appendInput($event)">
          <h3>
            
          </h3>
        </div>
        <div class="accountTitle">
          <h3 @click="appendInput($event)">
            {{userName}}
          </h3>
          <div class="accountUpdateDisplay">
            <input type="text" v-model="userName">
            <v-btn @click="closeInput($event)">확인</v-btn>
          </div>
        </div>
        <div class="accountTitle" @click="appendInput($event)">
          <h3>
            {{userContact}}
          </h3>
        </div>
        <div class="accountTitle" @click="appendInput($event)">
          <h3>
            {{userAddress}}
          </h3>
        </div>
        <div class="accountTitle">
          <h3>
            {{averagePoint}}
          </h3>
        </div>
        <div class="accountTitle">
          <h3>
            {{contractProgress}}
          </h3>
        </div>
        <div class="accountTitle">
          <h3>
            {{contractComplete}}
          </h3>
        </div>
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

<script>
export default {
  data() {
    return {
      userName: 'SSA인',
      userContact: '010-1234-5678',
      userAddress: '대전시 유성구 신성동',
      averagePoint: 5,
      contractProgress: 1,
      contractComplete: 10
    }
  },
  methods: {
    appendInput(e) {
      const appendTarget = e.target.parentNode;
      appendTarget.lastChild.classList.remove("accountUpdateDisplay")
    },
    closeInput(e) {
      const removeTarget = e.target.parentNode.parentNode
      removeTarget.classList.add("accountUpdateDisplay")
    }
  }

}
</script>>

<style lang="scss" scoped>
$lists: '비밀번호', '이름', '연락처', '주소', '평균 평점', '진행 거래', '완료 거래';
.container {
  padding: 2rem;
  .attrs {
    .accountTitle {
      padding: 0.5rem 2rem;
      display: flex;
      align-items: center;
      justify-content: flex-start;
      transition: 0.25s;
      @each $val in $lists {
        $i: index($lists, $val);
        &:nth-child(#{$i}) {
          &:before {
            content: '' + #{$val} + '';
            font-family: 'ON-IGothic'; 
            font-size: 1.5rem;
            font-weight: 900;
            background-image: linear-gradient(to bottom, rgb(230, 155, 85), rgb(178, 108, 185), rgb(65, 166, 173));
            background-attachment: fixed;
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            color: black;
            width: 40%;
            // opacity: 0.7;
            transition: 0.25s;
          }
        &:hover:not(:nth-child(n+5)) {
          &:before {
            content: '수정하기';
            position: relative;
            // opacity: 0.9;
          }
        }
        }
      }
      h3 {
        position: relative;
        left: -1rem;
        color: #3d3d3d;
        transition: 0.25s;
      }
      &:hover {
        background-color: #fafafa;
        cursor: pointer;
        &:before {
          // opacity: 0.7;
        }
        h3 {
          left: 0rem;
        }
        
      }
      input {
        position: relative;
        // left: 3rem;
        transition: 0.25s;
        border-bottom: 1px solid black;
      }
      &:hover {
        background-color: #fafafa;
        cursor: pointer;
        &:before {
          // opacity: 0.7;
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