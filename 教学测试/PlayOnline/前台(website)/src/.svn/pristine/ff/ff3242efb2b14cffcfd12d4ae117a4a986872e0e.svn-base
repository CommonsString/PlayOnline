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
        <div class="box-discribe">
          <span class="share-discribe">
            {{ item.recomNum }}
          </span>
          <span class="flag-discribe">
            {{ item.flag }}
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

    }
  }
}
</script>
<style lang="css" scoped>
.card-block{
  height: auto;
  background: rgb(250, 250, 250);
  border-bottom: 2px solid rgb(200, 200, 200);
}
    .card-img{
      width: 100%;
      padding: 0px;
      height: auto;
    }
        .card-img img{
          height: auto;
          width: 100%;
        }
    .card-discribe{
      position: relative;
      padding: 0px 12px;
      height: auto;
    }
        .title-discribe{
          text-align: center;
          color: black;
          font-size: 20px;
          font-weight: bold;
        }
        .box-discribe{
          display: flex;
          padding-bottom: 5px;
        }
        .share-discribe{
          flex:15;
          color: black;
          overflow: hidden;
        }
        .flag-discribe{
          flex: 2;
          color: black;
        }
</style>
