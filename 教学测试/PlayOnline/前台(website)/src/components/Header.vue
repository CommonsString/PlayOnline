<template>
  <div class="block-header">
    <Title @onShowMenu='showMenu'/>
    <div class="block-nav"><!-- 固定宽度的外壳 -->
      <div class="box-nav"><!-- 动态宽度的盒子 -->
        <span class="block-blocks" v-for="item in blocks" :key="item.blockId"
        :class="{active: item.blockId==activeBlockId}"
        @click="navClickSet(item)">
          {{ item.subject }}
        </span>
      </div>

    </div>
  </div>
</template>
<script>
import IP from '@/assets/js/ip'
import Title from '@/components/Title'
export default {
  name: "",
  components: {
    Title
  },
  created() {
    this.getBlocks()
  },
  updated() {
    this.computeNavWidth()
  },
  methods: {
    showMenu() {
      this.$emit('onShowMenu')
    },
    /////////////////////////////////事件处理部分/////////////////////////////////
    navClickSet(item){
      this.$store.commit('changeBlock', item.blockId)
      this.$emit('onSwitchBlock')
    },
    /////////////////////////////////界面处理部分/////////////////////////////////
    computeNavWidth(){
      let nav = document.getElementsByClassName('box-nav')[0]
      let navs = nav.getElementsByClassName('block-blocks')
      let width = 0
      for (let i = 0; i < navs.length; i++) {
        width += navs[i].clientWidth + 1
      }
      nav.style.width = width + 'px'
    },
    /////////////////////////////////请求部分/////////////////////////////////
    getBlocks(){
      this.$http.post(
        IP+'/client',
        {
          method: 'getBlocks'
        },
        {emulateJSON: true}
      )
      .then(res=>{
        this.blocks = res.data.blocks
      })
      .catch(err=>{
        this.blocks = [
          {blockId: '00', subject: '测试数据'},
          {blockId: '01', subject: '数学'},
          {blockId: '02', subject: '语文'},
          {blockId: '03', subject: '英语'},
          {blockId: '04', subject: '物理'},
          {blockId: '05', subject: '化学'},
          {blockId: '06', subject: '生物'},
          {blockId: '07', subject: '地理'},
          {blockId: '08', subject: '历史'},
          {blockId: '09', subject: '政治'},
          {blockId: '10', subject: '初高中衔接'}
        ]
      })
    }
  },
  computed: {
    activeBlockId() {
      return this.$store.state.activeBlockId
    },
  },
  data() {
    return{
      blocks: []
    }
  }
}
</script>
<style lang="css" scoped>
.block-header{
  height: 75px;
}
/*--------------------导航部分-----------------------*/
.block-nav{
  width: 100%;
  height: 40px;
  background: rgb(20, 20, 20);
  overflow-x: auto;
  font-size: 14px;
  /*overflow-y: visible;*/
}
.block-blocks{
  float: left;
  height: 40px;
  line-height: 40px;
  padding: 0px 17px;
  margin-right: 1px;
  background: rgb(10, 10, 10);
  color: rgb(240, 240, 240);
}
.active{
  height: 36px;
  color: white;
  border-bottom: 4px solid #BF0A10;
  z-index: 99;
}
</style>
