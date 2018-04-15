<template>
  <div class="block-player">
  <div id="top"></div>
    <div class="card-block">
      <div class="card-img">
        <video :src='nowFile.fileSrc'
         autoplay controls="controls"
         width='100%' height='auto'>
        </video>
      </div>
      <div class="card-discribe">
        <div class="box-discribe">
          <span class="share-discribe">
            描述：{{ fileItem.discribe }}
          </span>
          <span class="flag-discribe">
            <div class="flag-item">
              <img src="../assets/icons/price.png" class="icon">
              <span class="">{{ fileItem.flag }}</span>
            </div>
            <div class="flag-item" @click="toRecommend">
              <img v-if="flag.recommended" src="../assets/icons/rec1.png" class="icon">
              <img v-else src="../assets/icons/rec0.png" class="icon">
              <span class="">{{flag.recommended?'取消推荐':'点击推荐'}}</span>
            </div>
          </span>
        </div>
      </div>
    </div>
    <div class="block-list">
      <div class="section">
        当前：{{ nowFile.title }}
        <span class="btn-list" @click="showList">选集</span>
      </div>
      <ul :class="{'fold': !flag.showList,'unfold': flag.showList}">
        <li v-for="item in itemList" :key="item.date" class="video-tag"
        @click="toPlay(item)" :class="{active: nowFile.fileSrc==item.fileSrc}">
          {{ item.title }}
        </li>
      </ul>
    </div>
    <div class="block-list">
      <div class="section">
        评论区
        <span class="btn-list" @click="showInput">添加评论</span>
        <span class="btn-list" @click="showAllComments">查看所有</span>
      </div>

      <div class="edit" v-show="flag.showInput">
        <textarea class="input-comment" v-model="myComment"></textarea>
        <div class="submit" @click="comment">发表</div>
      </div>

      <ul :class="{'unfold': flag.showComments}">
        <li v-for="(i,index) in comments" :key="i.user.userId"
        v-show="index<3|flag.showComments"  class="comment-tag">
          <div class="user">{{i.user.name}}</div>
          <div class="content">{{i.content}}</div>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
import IP from '@/assets/js/ip'
import Comments from '@/components/Comments'
// import Listener from '@/assets/listener'
export default {
  name: "player",
  created() {
    this.recommend(false)
    this.getFileGroup()
  },
  mounted() {
    // this.$emit('playermounted')
    this.$store.commit('setHeader',this.fileItem.title)
  },
  data() {
    return {
      // item: {},
      itemList: [],
      flag: {
        showList: false,
        recommended:false,
        showInput: false,
        showComments: false
      },
      nowFile: {},
      comments: [],
      myComment: ''
    }
  },
  computed: {
    item() {
      return this.$store.getters.currentFileGroup
    },
    fileItem() {
      return this.item.fileGroup
    },
    author() {
      return this.item.author
    },
    isLogin() {
      return this.$store.getters.isLogin
    }
  },
  methods: {
    defaultPlay() {
      //读取该视频组上次播放的集数
      let fileInfo = JSON.parse(localStorage.getItem(this.fileItem.fileGroupId))
      if(fileInfo == null){
        fileInfo = this.itemList[0]
      }
      this.toPlay(fileInfo)//默认播放第一个视频
    },
    toPlay(file) {
      this.nowFile = file
      console.log('this.nowFile',this.nowFile)
      localStorage.setItem(this.fileItem.fileGroupId,JSON.stringify(file))
      this.getComments()
    },
    showList(){
      this.flag.showList = !this.flag.showList
    },
    toRecommend() {
      if(this.isLogin){
        this.recommend(true)
      }
      else{
        console.log('登陆')
        this.$router.push('/Sider/Login')
      }
    },
    showAllComments() {
      this.flag.showComments = !this.flag.showComments
    },
    showInput() {
      if(!this.isLogin){
        console.log('登陆')
        this.$router.push('/Sider/Login')
      }
      else{
        this.flag.showInput = !this.flag.showInput
      }
    },
    //请求部分------------------------------------------
    getFileGroup() {
      this.$http.post(
        IP+'/client',
        {
        	method: 'getFileGroup',
          fileGroupId: this.fileItem.fileGroupId
        },
        {emulateJSON: true}
      )
      .then(res=>{
        this.itemList = res.data.fileGroup
        this.defaultPlay()
      })
      .catch(err=>{
        // this.defaultPlay()
      })
    },
    //查询是否已经推荐以及进行推荐操作
    recommend(tf){
      this.$http.post(
        IP+'/user',
        {
          method: 'recommend',
          userId: this.$store.getters.user.userId,
          fileGroupId: this.fileItem.fileGroupId,
          //当tf为true时要更改推荐记录，false时只查询
          change: tf
        },
        {emulateJSON: true}
      )
      .then(res=>{
        if(res.data.recommended==true){
          this.flag.recommended = true
        }
        else{
          this.flag.recommended = false
        }
      })
      .catch(err=>{
        // this.flag.recommended = !this.flag.recommended
      })
    },
    //获取评论
    getComments(){
    	console.log('this.nowFile',this.nowFile)
      this.$http.post(
        IP+'/user',
        {
          method: 'getComments',
          fileId: this.nowFile.fileId
        },
        {emulateJSON: true}
      )
      .then(res=>{
      	console.log('res.data in comm',res.data.comments)
				this.comments = res.data.comments
      })
      .catch(err=>{
      	alert('获取评论失败')
      })
    },
    comment(){
      this.$http.post(
        IP+'/user',
        {
          method: 'comment',
          userId: this.$store.getters.user.userId,
          fileId: this.nowFile.fileId,
          content: this.myComment
        },
        {emulateJSON: true}
      )
      .then(res=>{
      	console.log('res.data in comments', res.data)
        if(res.data.status==true){
          this.flag.showInput = false
          this.comments.unshift({
            user: this.$store.getters.user,
            content: this.myComment
          })
          this.myComment = ''
        }
        else{
          alert(res.data.info)
        }
      })
      .catch(err=>{
        this.comments.unshift({
          user: this.$store.getters.user,
          content: this.myComment
        })
        alert('失败')
      })
    }
  },
  components: {
    Comments
  }
}
</script>
<style lang="css" scoped>
::-webkit-scrollbar{
  height: 0px;
  width: 10px;
}
::-webkit-scrollbar-thumb{
  background-color: rgba(100, 100, 100, 0.35);
  border-radius: 4px;
}
.card-block{
  width: 100%;
  height: auto;
  /* background: rgb(20, 20, 20); */
  background: white;
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
  padding: 0px 12px 5px 12px;
  height: auto;
}
.title-discribe{
  text-align: center;
  color: white;
  font-size: 18px;
  font-weight: 400;
}
.box-discribe{
  display: flex;
  /*padding-bottom: 5px;*/
}
.share-discribe{
  flex:12;
  color: rgb(30, 30, 30);
  overflow: hidden;
}
.icon{
  position: relative;
  top: 1px;
  padding-right: 3px;
  width: auto;
  height: 15px;
}
.flag-discribe{
  flex: 5;
  color: rgb(30, 30, 30);
}
.flag-item{
  height: 25px; line-height: 25px;
}
.block-list{
  position: relative;
  margin: 2px 0px;
  background: rgb(240, 240, 240);
  overflow: hidden;
  font-size: 15px;
}
.section{
  margin: 0px 0px 1px 0px;
  padding: 7px 0px 7px 20px;
  height: 30px;
  line-height: 30px;
  color: rgb(30, 30, 30);
  background: rgba(177, 177, 177, 0.11);
}
.btn-list{
  float: right;
  min-width: 60px;
  text-align: center;
  padding: 0px 10px;
  border-left: 1px solid rgb(200, 200, 200);
}
.block-list ul{
  position: relative;
  /*border: 1px solid red;*/
  /*border-left: 3px solid rgb(175, 175, 175);*/
  list-style: none;
  margin: 0px;
  padding: 0px;
  top: 0px;
  overflow: scroll;
}
.video-tag{
  padding: 15px 20px;
}
.comment-tag{
  padding: 5px 20px;
}
.block-list li{
  margin: 2px 0px;
  color: rgb(30, 30, 30);
  /* background: rgba(77, 77, 77, 0.1); */
  border-bottom: 1px solid rgba(77, 77, 77, 0.1);
}
.active{
  border-left: 5px solid rgba(230, 0, 0, 0.9);
  transition: all 0.3s;
}
.fold{
  height: 0px;
  /* transition: height 0.3s; */
}
.unfold{
  height: auto;
  max-height: 270px;
  transition: height 0.3s;
}
.block-comment{
  margin: 5px 0px;
}
.title-comment{
  padding: 5px;
  height: 1.5em;
}
.edit{
  box-sizing: border-box;
  position: relative;
  border-bottom: 1px solid gray;
  background: rgba(200, 200, 200, 0.3);
  border-radius: 0px;
  width: 100%;
}
.input-comment{
  box-sizing: border-box;
  width: 100%; height: 80px;
  padding: 5px 7px 20px 7px;
  background: transparent;
  outline: none;
  resize: none;
  border: 0px;
  font-size: 15px;
}
.submit{
  position: absolute;
  padding: 3px 10px;
  right: 3px;
  bottom: 3px;
  background: gray;
  color: white;
  border-radius: 3px;
}
.comment{
  margin: 1px 0px;
  padding: 5px 10px;
  background: white;
  color: black;
}
.user{
  font-size: 17px;
  padding: 5px 0px;
}
</style>
