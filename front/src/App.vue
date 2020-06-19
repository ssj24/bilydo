<template>
  <v-app>
    <Nav></Nav>
    <v-content class="content-wrapper">
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script lang="ts">
import { Component, Vue} from 'vue-property-decorator'
import Nav from "@/views/main/Nav.vue"

@Component({
  components: {
    Nav
  }
})
export default class App extends Vue{
  
  
  
}
</script>
<style lang="scss">
.navBar {
  border-top: 3px solid #8c28b4 !important;
  box-shadow: 1px 1px 1px #ccc !important;
  .v-btn {
    padding: 0;
    span.v-btn__content {
      height: 48px;
          a {
        line-height: 48px;
        width: 48px;
        height:48px;
      }
      span.v-badge {
        width: 48px;
        height: 48px;
      }
    }
  }
}
a.navTitle, a:link.navTitle, a:visited.navTitle, a:hover.navTitle, a:active.navTitle {
  font-family: 'SangSangAnt'; 
  color: #2f023d !important;
  // text-shadow: 1px 1px 1px #8c28b4;
  font-size: 3em; 
  // font-weight: bolder;
}
.v-badge__badge {
  color: #000 !important;
}

.v-toolbar__title {
  position: relative;
  overflow: hidden;
}
.star4 {
  position: absolute;
  // background: radial-gradient(#f0da7c, #fff);
  background-image: url('./assets/images/yellowFlower.png');
  background-size: cover;
  width: 20px;
  height: 20px;
  top: 13px;
  right: 24px;
  
}
</style>
