<template>
  <div>
    <div class="title" @click="switchForm">
      <div :class="{'active': showLogin==true}">登录</div>
      <div :class="{'active': showLogin==false}">注册</div>
    </div>
    <!-- {{isLogin}} -->
    <div class="form">
      <h5 v-if="!showLogin">基本信息</h5>
      <div class="block-input" v-if="!showLogin">
        <span>昵称</span><input v-model="form.name" placeholder="请输入姓名或常用昵称">
      </div>
      <div class="block-input">
        <span>账号</span><input type="email" v-model="form.email" placeholder="请输入邮箱地址">
      </div>
      <div class="block-input">
        <span>密码</span><input type="password" v-model="form.password" placeholder="请输入密码">
      </div>
      <div class="block-input" v-if="!showLogin">
        <span>确认</span><input type="password" v-model="form.passwordre" placeholder="请输入确认密码">
      </div>
      <h5 style="padding-left: 10px;" @click="linkTo('/Sider/ResetPassword')">忘记密码？</h5>
    </div>
    <div v-if="!showLogin" class="form">
      <h5>密保信息</h5>
      <div class="block-input" v-if="!showLogin">
        <span>设置密保问题1</span><input class="prolong" v-model="form.question1" placeholder="请设置密保问题">
      </div>
      <div class="block-input">
        <span>设置答案</span><input class="prolong" type="email" v-model="form.answer1" placeholder="请设置答案">
      </div>
      <div class="block-input" v-if="!showLogin">
        <span>设置密保问题2</span><input class="prolong" v-model="form.question2" placeholder="请设置密保问题">
      </div>
      <div class="block-input">
        <span>设置答案</span><input class="prolong" type="email" v-model="form.answer2" placeholder="请设置答案">
      </div>
      <div>密保问题将作为找回密码的根据，请认真填写</div>
    </div>
    <div class="submit" @click="loginEnroll">提交</div>
  </div>
</template>

<script>
import IP from '@/assets/js/ip'
export default {
  name: '',
  data: function () {
    return {
      showLogin: true,
      // showLoginBtn: true,
      form: {
        name: '',
        email: '1149674138@qq.com',
        password: '123456',
        passwordre: '123456',
        question1: '',
        answer1: '',
        question2: '',
        answer2: ''
      }
    }
  },
  created(){
    this.$store.commit('setHeader','账号验证')
  },
  computed: {
    isLogin() {
      return this.$store.getters.isLogin
    }
  },
  methods: {
    switchForm() {
      this.showLogin=!this.showLogin
    },
    loginEnroll() {
      console.log('form', this.form)
      let method = this.showLogin==true?'loginUser':'enroll'
      if(method=='enroll'){
        if(this.form.password!=this.form.passwordre){
          alert('两次密码不一致')
          return
        }
        if(this.form.question1==""){
          alert('请设置密保问题1')
          return
        }
        if(this.form.answer1==""){
          alert('请设置问题1答案')
          return
        }
        if(this.form.question2==""){
          alert('请设置密保问题2')
          return
        }
        if(this.form.answer2==""){
          alert('请设置问题2答案')
          return
        }
        if(this.form.question2==this.form.question1){
          alert('请勿设置相同问题')
          return
        }
        if(this.form.question1.length>20||this.form.question2.length>20
        ||this.form.answer1.length>20||this.form.answer2.length>20){
          alert("问题或答案过长")
          return
        }
      }
      this.$http.post(
        IP+'/user',
        {
          method: method,
          name: this.form.name,
          email: this.form.email,
          password: this.form.password,
          question1: this.form.question1,
          question2: this.form.question2,
          answer1: this.form.answer1,
          answer2: this.form.answer2
        },
        {emulateJSON: true}
      )
      .then(res=>{
        if(res.data.status==true){
        	console.log('res.data.user',res.data.user)
          if(this.showLogin){
            localStorage.user = JSON.stringify({
              email: this.form.email,
              password: this.form.password
            })
            this.$store.commit('login',res.data.user)
            this.$router.back()
          }
          else{
            alert('激活邮件已发送到邮箱\n请激活后再账号')
            this.switchForm()
          }
        }
        else{
          alert(res.data.info)
        }
      })
      .catch(err=>{
//      this.$store.commit('login',{
//        name: '张三'
//      })
//      this.$router.back()
      })
    },
    linkTo(path){
      this.$router.push(path)
    }
  }
}
</script>

<style scoped>
h5{
  color: gray;
}
.title{
  text-align: center;
  margin-top: 60px;
}
.title div{
  display: inline-block;
  padding: 3px;
  margin: 30px 0px;
}
.active{
  font-size: 30px;
  border-bottom: 2px solid #BF0A10;
}
.form{
  /* border: 1px solid black; */
  width: 330px;
  margin: 0px auto;
}
.block-input{
  /* height: 30px; */
  margin: 30px 0px;
  /* border: 1px solid red; */
  font-size: 20px;
}
.block-input span{
  padding: 10px;
}
.block-input input{
  padding: 3px 10px;
  height: auto;
  border: 0px;
  border-bottom: 2px solid rgb(190,190,190);
  background: transparent;
  outline: none;
  font-size: 17px;
}
.prolong{
  width: 300px;
}
.block-input input:focus{
  border-bottom: 2px solid #BF0A10;
}
.submit{
  margin: 23px auto;
  border-radius: 5px;
  width: 130px; height: 35px;
  text-align: center; line-height: 35px;
  /* background: rgb(50, 50, 50); */
  border: 2px solid #BF0A10;
  color: #BF0A10;
}
</style>