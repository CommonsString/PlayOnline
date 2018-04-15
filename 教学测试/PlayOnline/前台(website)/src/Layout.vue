<template>
  <div class="">
    <div class="block-top">
    </div>
    <vHeader ref="header" @onSwitchBlock='switchBlock' class='v-header'/>
    <div class="v-content">
      <keep-alive exclude="player">
        <router-view ref='view'
        @onclickcard="cardClickSet">
        </router-view>
        <!-- 
          事件触发来源分别为：
          Index.vue
          Index.vue/Block.vue
          Player.vue
        @playermounted="resPlayer"
          Block.vue
        @blockmounted="resBlock"
        -->
      </keep-alive>
    </div>
  </div>
</template>
<script>
import vHeader from '@/components/Header.vue'
export default {
  name: "",
  mounted() {
    this.$router.replace('/')
    let that = this
    document.addEventListener("plusready", function() {
      plus.key.addEventListener("backbutton",function(){
        try {
          if(that.$route.path=='/'){
            if(confirm('是否退出程序？')){
              plus.runtime.quit()
            }
          }
          else{
            window.history.back()
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
    vHeader
  },
  methods: {
//事件处理部分----------------------------------------
    //当界面为主页时清除头部导航模块被选中的效果
    switchBlock() {
//    if(blockId!=this.blockId){
        this.$router.push('/Block')
        // this.blockId = blockId
        this.$refs.view.page = 1
        let that = this
        setTimeout(function(){
          that.$refs.view.getFileGroups(true)
        },10)
        
//    }
    },
    cardClickSet(item){
      // alert('触发点击')
      this.$router.push('/Player')
      // this.currentFileGroup = item
      // console.log('in cardclickset')
      // console.log(item.fileGroup)
      // this.getFileGroup()
    },
    // resBlock(){
      // this.$refs.view.blockId = this.blockId
    // },
    //重置播放器页面数据
    // resPlayer(){
      // this.$refs.view.item = this.currentFileGroup.fileGroup
      // this.$refs.view.author = this.currentFileGroup.author
      // this.$refs.view.getFileGroup()
    // },
  },
  data() {
    return{
      // blockId: '',
      // currentFileGroup: {}
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
  background: rgb(218, 218, 218)
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
.v-content{
  position: absolute;
  width: 100%;
  top: 105px;
  bottom: 0px;
  overflow: auto;
  margin: 0px;
}
</style>
