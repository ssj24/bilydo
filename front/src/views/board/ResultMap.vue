<template>
  <div>
    <h1>map</h1>
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
  export default class ResultMap extends Vue{
    @Prop() items!: object[]
  }
</script>

<style scoped>

</style>