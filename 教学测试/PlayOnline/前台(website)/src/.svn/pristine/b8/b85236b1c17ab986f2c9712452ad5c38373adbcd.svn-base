<template>
  <div class="title-header">
      <img v-if="showBack" class="img-back" src="../assets/icons/back.png"
      @click="backClickSet">
      <img v-if="showMenu" class="img-menu" src="../assets/icons/menu.png"
      :class="{'menu_on': flag.showMenu}" @click="menuClickSet">
      <!-- <div class="" @click="routeClickSets({'title':'index', 'map': '/'})">
        返回
      </div> -->
      <div class="block-title">
        {{ blockTitle }}
      </div>
    </div>
</template>

<script>
export default {
  name: '',
  data: function () {
    return {
      flag: {
        showMenu: false
      }
    }
  },
  methods: {
    backClickSet(){
      if(this.$route.path=='/Layout/Block')
        this.$store.commit('changeBlock','')
      this.$store.commit('setHeader')
      window.history.back()
    },
    menuClickSet(){
      this.flag.showMenu = !this.flag.showMenu
      this.$emit('onShowMenu')
    },
  },
  computed: {
    showBack() {
      return this.$route.path!='/Layout/Index'
    },
    showMenu() {
      return this.$route.path=='/Layout/Index'
    },
    blockTitle() {
      return this.$store.getters.header
    }
  }
}
</script>

<style scoped>
/*-----------------------标题部分-----------------------*/
.title-header{
  position: relative;
  width: 100%;
  height: 45px;
  background: #d9020a;
  color: white;
  font-size: 17px;
}
.block-title{
  position: absolute;
  width: 100%;
  height: 45px;
  line-height: 47px;
  text-align: center;
}
img{
  position: absolute;
  width: auto;
  z-index: 2;
}
.img-back{
  left: 0px;
  padding: 10px;
  height: 25px;
}
.img-menu{
  right: 0px;
  padding: 12.5px 10px;
  height: 20px;
  transition: all .2s;
}
.menu_on{
  /* border: 3px solid black; */
  background: rgb(170, 30, 30);
}
</style>