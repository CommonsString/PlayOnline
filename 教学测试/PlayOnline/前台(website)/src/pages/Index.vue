<template>
  <div id="">
    <CardTop :item="TopFileGroup.fileGroup" v-if="TopFileGroup.fileGroup!=undefined"
    @click.native="cardClickSet(TopFileGroup)"/>
    <div class="block-title">人气王</div>
    <Card v-for="item in fileGroups" :key="item.fileGroupId"
    class="block-card"
    :item="item.fileGroup" @click.native="cardClickSet(item)"/>
  </div>
</template>
<script>
import IP from '@/assets/js/ip'
import CardTop from '@/components/CardTop'
import Card from '@/components/Card'
export default {
  name: "",
  components: {
    CardTop,
    Card
  },
  data: () => ({
    TopFileGroup: {},
    fileGroups: [],
    page: 1,
    onReq: false
  }),
  mounted() {
    this.getFileGroups()
  },
  methods: {
    //////////////////////事件处理部分//////////////////////
    cardClickSet(item){
      this.$router.push('/Sider/Player')
      this.$store.commit('setFileGroup',item)
    },
    reqingAlert(){
      alert('数据获取中，请稍候..')
    },
    //////////////////////请求部分//////////////////////
    getFileGroups() {
      this.onReq = true
      this.$http.post(
        IP+'/client',
        {
        	method : 'getAllFileGroups',
        	page: this.page
        },
        {emulateJSON: true}
      )
      .then(res=>{
        //取封翻页加载按钮
        this.onReq = false
        this.fileGroups = this.fileGroups
        .concat(res.data.fileGroups)
        this.page++
        if(!this.TopFileGroup.fileGroup)
        this.TopFileGroup = this.fileGroups.shift()
      })
      .catch(err=>{
        this.onReq = false
        ////////////////////////数据示例///////////////////////////
       this.fileGroups =  [
         {
            fileGroup: {
              fileGroupId: '00',
              fileGrounpStatus: '已完结',
              title: '视频标题',
              discribe: '视频详细描述',
              imgSrc: 'http://img.bizhi.sogou.com/images/2012/01/28/147706.jpg',
              flag: '1000',
              recomNum: '20'
            },
            author: {
              amdinId: 'adminid',
              name: 'author name'
            }
          },
          {
            fileGroup: {
              fileGroupId: '00',
              fileGrounpStatus: '已完结',
              title: '视频标题',
              discribe: '视频详细描述',
              imgSrc: 'http://img.bizhi.sogou.com/images/2012/01/28/147706.jpg',
              flag: '1000',
              recomNum: '20'
            },
            author: {
              amdinId: 'adminid',
              name: 'author name'
            }
          },
          {
            fileGroup: {
              fileGroupId: '00',
              fileGrounpStatus: '已完结',
              title: '视频标题',
              discribe: '视频详细描述',
              imgSrc: 'http://img.bizhi.sogou.com/images/2012/01/28/147706.jpg',
              flag: '1000',
              recomNum: '20'
            },
            author: {
              amdinId: 'adminid',
              name: 'author name'
            }
          }
       ]
        if(!this.TopFileGroup.fileGroup)
        this.TopFileGroup = this.fileGroups.shift()
      })
    }
  }
}
</script>
<style lang="css" scoped>
.block-title{
  padding-left: 10px;
  color: rgb(112, 112, 112);
  font-size: 15px;
}
.block-card{
  margin: 0px 5px 5px 5px;
}
.block-more{
  margin: 5px 5px;
  padding: 3px 0px;
  text-align: center;
  /*color: #BF0A10;*/
  color:rgb(112, 112, 112);
  font-size: 15px;
  /*border-bottom: 1px solid #BF0A10;*/
  border-bottom: 1px solid rgb(157, 155, 155);
}
</style>
