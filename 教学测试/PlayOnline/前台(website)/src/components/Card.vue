<template>
  <div id="">
    <div class="card-block">
      <div class="card-img">
        <img :src="item.imgSrc" alt="加载中..">
      </div>
      <div class="card-discribe">
        <div class="title-discribe">
          {{ item.title }}
        </div>
        <div class="content-discribe">
          {{item.discribe}}{{item.discribe}}{{item.discribe}}{{item.discribe}}{{item.discribe}}{{item.discribe}}
        </div>
        <div class="box-discribe">
          <span class="share-discribe">
            <!-- <img src="../assets/icons/rec1.png" class="icon">-->
            <span class="flag-number">{{ item.recomNum }}</span> 
          </span>
          <span class="empty-discribe"></span><!-- 用来占位 -->
          <span class="flag-discribe">
            <!-- <img src="../assets/icons/price.png" class="icon"> -->
            <span class="flag-number">{{ item.flag }}元</span>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "",
  props: ['item'],
  methods: {
  },
  data(){
    return{
//"http://www.w3school.com.cn/i/movie.ogg"
    }
  }
}
</script>
<style lang="css" scoped>
.card-block{
  height: 80px;
  background: white;
  border-bottom: 2px solid rgb(200, 200, 200);
}
    .card-img{
      float: left;
      padding: 0px;
      height: 80px;
      width: 153px;
      overflow: hidden;
    }
        .card-img img{
          height: auto;
          width: 153px;
        }
    .card-discribe{
      position: relative;
      overflow: hidden;
      padding: 2.5px 7px;
      height: 75px;
    }
        .title-discribe{
          max-height: 2.5em;
          overflow: hidden;
          text-align: center;
          color: black;
          font-size: 15px;
          font-weight: 400;
        }
        .content-discribe{
          min-height: 32px;
          max-height: 32px;
          font-size: 12px;
          /* background: gray; */
          /*text-align: right;*/
          overflow: hidden;
          color: black;
        }
        .box-discribe{
          display: flex;
        }
        .empty-discribe{
          flex: 5;
          /*border: 1px solid white;*/
        }
        .share-discribe{
          flex:3;
          color: black;
          overflow: hidden;
        }
        .icon{
          position: relative;
          top: 3px;
          padding-right: 3px;
          width: auto;
          height: 15px;
        }
        .flag-number{
          /*position: absolute;
          top: 0px;*/
          display: inline-block;
          font-size: 10px;
          height: 15px;
          width: 50px;
          /*border: 1px solid white;*/
        }
        .flag-discribe{
          text-align: right;
          flex: 3;
          color: black;
          overflow: hidden;
        }
</style>
