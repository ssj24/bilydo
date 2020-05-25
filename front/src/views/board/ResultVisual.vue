<template>
  <div>
    <h1>visual</h1>
    <v-card 
      v-for="item in items"
      :key="item.product"
    >
    </v-card>
  </div>
</template>

<script lang="ts">
  import { Component, Prop, Vue } from 'vue-property-decorator'
  
  @Component
  export default class ResultVisual extends Vue{
    @Prop() items!: object[]
  }
</script>

<style scoped>

</style>