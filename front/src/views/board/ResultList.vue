<template>
  <div>
    <v-row justify="center">
      <v-col 
        cols="10" sm="4" 
        v-for="item in items"
        :key="item.product">

	<router-link :to="{name:'Detail', params:{boardId:item.id}}">
          <div class="property-card">
            <div class="property-image" 
              :style="{'background-image': 'url('+'http://13.125.209.188:8080/' +item.imagesPath[0]+')'}"
            >
              <div class="property-image-title">
                {{ item.borrowableSrt.substring(0,10) }} / {{item.borrowableEnd.substring(0,10)}}
              </div>
            </div>
            <div class="property-description">
              <h3> {{item.productName}} </h3>
              <p>{{item.borrowableSrt.substring(0,10)}}/{{item.borrowableEnd.substring(0,10)}}</p>
            </div>
          </div>
        </router-link>

      </v-col>
    </v-row>
    <v-row justify="center">
      <div class="text-center">
        <v-pagination
          v-model="currentPage"
          :length="pageLen"
          @input="next"
        ></v-pagination>
      </div>
    </v-row>
  </div>
</template>

<script lang="ts">
  import { Component, Prop, Vue } from 'vue-property-decorator'
  import baseURL from "@/base-url"

	@Component
  export default class ResultList extends Vue{
    @Prop() items!: object[]
    @Prop() currentPage!: number
    @Prop() listSize!: number
    @Prop() category!: string
    @Prop() productName!: string
    @Prop() pageSize!: number

    private pageLen = 0;
    created(){
        this.pageLen = Math.ceil(this.listSize/this.pageSize);
        //this.pageLen = this.listSize/this.pageSize;
    }

    public next(page: number): void{
      if(this.category === "AuA��") this.category = '';
      if(this.productName === undefined) this.productName = '';

      baseURL.get('/boards', {
        params: {
          category:this.category,
          location:'',
          page:page-1,
          productName:this.productName,
          size:this.pageSize
        }
      }).then((response) => {
        this.items = response.data.content;
      });
    }
  }
</script>

<style lang="scss" scoped>
.property-card
{
  height:18em;
  width:100%;
  position:relative;
  -webkit-transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1);
  -o-transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1);
  border-radius:16px;
  overflow:hidden;
  -webkit-box-shadow:  15px 15px 27px #e1e1e3, -15px -15px 27px #ffffff;
  box-shadow:  15px 15px 27px #e1e1e3, -15px -15px 27px #ffffff;
}
/* ^-- The margin bottom is necessary for the drop shadow otherwise it gets clipped in certain cases. */

.property-card:hover {
  cursor: pointer;
}
/* Top Half of card, image. */

.property-image
{
  height:6em;
  width:100%;
  padding:1em 2em;
  position:Absolute;
  top:0px;
  -webkit-transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1);
  -o-transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1);
  background-image:url('https://cdn.photographylife.com/wp-content/uploads/2017/01/What-is-landscape-photography.jpg');
  background-size:cover;
  background-repeat:no-repeat;
}

/* Bottom Card Section */

.property-description
{
  background-color: #FAFAFC;
  height:12em;
  width:100%;
  position:absolute;
  bottom:0em;
  -webkit-transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1);
  -o-transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1);
  padding: 0.5em 1em;
  text-align:center;
}

/* Social Icons */

.property-social-icons
{
  width:1em;
  height:1em;
  background-color:black;
  position:absolute;
  bottom:1em;
  left:1em;
  -webkit-transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1);
  -o-transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1);
}

/* Property Cards Hover States */

.property-card:hover .property-description
{
  height:0em;
  padding:0px 1em;
}
.property-card:hover .property-image
{
  height:18em;
}

.property-image-title
{
text-align:center;
position:Relative;
top:30%;
opacity:0;
transition:all 0.4s cubic-bezier(0.645, 0.045, 0.355, 1) 0.2s;
color:white;
font-size:1.3em;
font-weight: 900;
text-shadow: 1px 1px 2px #000;
}

.property-card:hover .property-image-title
{
opacity:1;
}

</style>