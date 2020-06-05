<template>
  <v-row>
    <v-col 
      cols="12"
      sm="3"
      class="d-flex align-center left">
      <ul class="flex-column">
        <li>
          <v-btn @click="btn1Clicked()" class="result-btn">
            리스트
          </v-btn>
        </li>
        <li>
          <v-btn @click="btn2Clicked()" class="result-btn">
            지도
          </v-btn>
        </li>
        <li>
          <v-btn @click="btn3Clicked()" class="result-btn">
            시각화
          </v-btn>
        </li>
      </ul>
    </v-col>
    <v-col class="right">
      <span v-if="btn1">
        <ResultList :items="regionBoards"></ResultList>
      </span>
      <span v-if="btn2">
        <ResultMap :items="regionBoards"></ResultMap>
      </span>
      <span v-if="btn3">
        <ResultVisual :items="regionBoards"></ResultVisual>
      </span>
    </v-col>
  </v-row>
</template>

<script lang="ts">
  import { Component, Prop, Vue } from 'vue-property-decorator'
  import ResultList from "@/views/board/ResultList.vue"
  import ResultMap from "@/views/board/ResultMap.vue"
  import ResultVisual from "@/views/board/ResultVisual.vue"
  
  @Component({
    components: {
      ResultList,
      ResultMap,
      ResultVisual
    }
  })
  export default class Result extends Vue{
    @Prop() regionBoards!: object[]
    @Prop() category!: string
    @Prop() listSize!: number
    @Prop() productName!: string
    
    private pageSize = 5;
    private currentPage = 0;

    private btn1 = true;
    private btn2 = false;
    private btn3 = false;
    public btn1Clicked(): void {
      this.btn1 = true;
      this.btn2 = false;
      this.btn3 = false;
    }
    public btn2Clicked(): void {
      this.btn1 = false;
      this.btn2 = true;
      this.btn3 = false;
    }
    public btn3Clicked(): void {
      this.btn1 = false;
      this.btn2 = false;
      this.btn3 = true;
    }
    created() {
      this.currentPage = 0;
      console.log("category : ",this.$props.category);
      console.log("listSize : ",this.$props.listSize);
      console.log("productName : ",this.$props.productName);

      //api/boards 에 요청~
    }

  }
</script>

<style scoped>
.left {
  display: inline-block;
}
.right {
  display: inline-block;
}
.result-btn {
  background-color: rgb(251, 241, 255) !important;
  margin: 5px;
  font-size: 1.1em !important;
  font-weight: 900 !important;
}
.result-btn:hover {
  background-color:rgb(255, 228, 240) !important;
}
.v-btn:before {
  opacity: 0 !important;
}
</style>