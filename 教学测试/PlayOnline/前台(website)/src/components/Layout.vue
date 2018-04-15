<template>
  <div>
    <div class="block-top" @click="showMenu"></div>
    <vHeader ref="header" class='v-header'
    @onSwitchBlock='switchBlock'
    @onShowMenu='showMenu'/>
    <!-- <transition name="fade"> -->
    <vMenu class="menu" :class="{'show_menu': flag.showMenu}"/>
    <!-- </transition> -->
    <div class="v-content" :class="{'show_menu': flag.showMenu}">
      <keep-alive>
        <router-view ref='view'>
        </router-view>
      </keep-alive>
    </div>
  </div>
</template>
<script>
import vHeader from '@/components/Header.vue'
import vMenu from '@/components/Menu.vue'
export default {
  name: "",
  data() {
    return {
      flag: {
        showMenu: false
      }
    }
  },
  mounted() {
    let that = this
    document.addEventListener("plusready", function() {
      plus.key.addEventListener("backbutton",function(){
        try {
          if(that.$route.path=='/Layout/Index'){
            if(confirm('是否退出程序？')){
              plus.runtime.quit()
            }
          }
          else{
            document.getElementsByClassName('img-back')[0].click()
          }
          if(that.$route.path=='/Layout/Index'||that.$route.path=='/Layout/Block'){
            that.$store.commit()
          }
        } catch (e) {
          alert(e.toString())
        }
      })
    })
  },
  updated() {
    console.log('aa' ,this.$refs.view.page)
    if(this.$route.path=='/'){
      this.$store.commit('changeBlock','')
    }
  },
  components: {
    vHeader, vMenu
  },
  methods: {
//事件处理部分----------------------------------------
    //当界面为主页时清除头部导航模块被选中的效果
    switchBlock() {
      this.$router.push('/Layout/Block')
      this.$refs.view.page = 1
      let that = this
      setTimeout(function(){
        that.$refs.view.getFileGroups(true)
      },10)
    },
    showMenu() {
      this.flag.showMenu = !this.flag.showMenu
    }
  }
}
</script>
<style lang="css">
::-webkit-scrollbar{
  height: 0.1px;
  width: 0px;
}
body{
  margin: 0px; height: 0px;
  background: rgb(245, 245, 245);
  overflow: hidden;
}
.block-top{
  width: 100%;
  height: 20px;
  background: #c0020a;
}
.v-header{
  position: absolute;
  height: 78px;
  width: 100%;
}
.menu{
  position: absolute;
  z-index: 10;
  /* border: 1px solid black; */
  top: 105px; right: -200px; bottom: 0px;
  width: 200px;
  transition: all .3s;
}
.show_menu{
  transform: translateX(-200px);
}
.v-content{
  position: absolute;
  width: 100%;
  top: 105px;
  bottom: 0px;
  overflow: auto;
  margin: 0px;
  transition: all .3s;
}
</style>