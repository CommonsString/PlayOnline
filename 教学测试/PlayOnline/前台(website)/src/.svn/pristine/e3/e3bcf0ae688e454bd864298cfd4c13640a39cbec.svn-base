<template>
  <div>
    <div class="form">
      <h5>第一步</h5>
      <div class="block-input">
        <span>账号</span><input v-model="email" type="email" placeholder="请输入邮箱地址">
      </div>
      <div class="submit" @click="getRectify">下一步</div>
    </div>
    <div class="form" v-if="flag.showRectify">
      <h5>第二步</h5>
      <div class="block-input" v-if="!showLogin">
        <span>密保问题1</span><input v-model="rectifyQueAndAns.question1" class="prolong" placeholder="" disabled>
      </div>
      <div class="block-input">
        <span>问题答案</span><input v-model="inputAns.answer1" class="prolong" type="email" placeholder="请填写答案">
      </div>
      <div class="block-input" v-if="!showLogin">
        <span>密保问题2</span><input v-model="rectifyQueAndAns.question2" class="prolong" placeholder="" disabled>
      </div>
      <div class="block-input">
        <span>问题答案</span><input v-model="inputAns.answer2" class="prolong" type="email" placeholder="请填写答案">
      </div>
      <div class="submit" @click="rectifyAns">验证</div>
    </div>
    <div class="form" v-if="flag.passRectify">
      <h5>第三步</h5>
      <div class="block-input">
        <span>设置新密码</span><input v-model="inputPassword.password" class="prolong" type="password" placeholder="请输入新密码">
      </div>
      <div class="block-input">
        <span>确认新密码</span><input v-model="inputPassword.passwordre" class="prolong" type="password" placeholder="请输入新密码">
      </div>
      <div class="submit" @click="resetPassword">提交</div>
    </div>
  </div>
</template>

<script>
import IP from '@/assets/js/ip'
export default {
  name: 'resetPassword',
  created() {
    this.$store.commit('setHeader', '重置密码')
  },
  data: function () {
    return {
      flag: {
        showRectify: false,
        passRectify: false
      },
      email: '',
      reEmail: '',
      rectifyQueAndAns: {
        question1: '',
        answer1: '',
        question2: '',
        answer2: ''
      },
      inputAns: {
        answer1: '',
        answer2: ''
      },
      inputPassword: {
        password: '',
        passwordre: ''
      }
    }
  },
  methods: {
    getRectify() {
      
      this.$http.post(
        IP+'/user',
        {
          method: 'getRectify',
          email: this.email
        },
        {emulateJSON: true}
      )
      .then(res=>{
        console.log('res.data.rectifyQueAndAns',res.data.rectifyQueAndAns)
        if(res.data.status==true){
          this.rectifyQueAndAns.question1 = res.data.rectifyQueAndAns.question1
          this.rectifyQueAndAns.answer1 = res.data.rectifyQueAndAns.answer1
          this.rectifyQueAndAns.question2 = res.data.rectifyQueAndAns.question2
          this.rectifyQueAndAns.answer2 = res.data.rectifyQueAndAns.answer2

          this.flag.showRectify = true
          this.flag.passRectify = false
          this.reEmail = this.email
        }
        else{
          alert(res.data.info)
        }
      })
      .catch(err=>{})
    },
    rectifyAns() {
      if(this.inputAns.answer1 == this.rectifyQueAndAns.answer1
      &&this.inputAns.answer2 == this.rectifyQueAndAns.answer2){
        this.flag.passRectify = true
        this.flag.showRectify = false
      }
      else{
        alert('答案不正确，验证不通过')
      }
    },
    resetPassword() {
      if(this.inputPassword.password!=this.inputPassword.passwordre){
        alert('两次密码不一致')
        return
      }
      if(this.email!=this.reEmail){
        alert('请勿更改验证邮箱')
        return
      }
      else{
        this.$http.post(
          IP+'/user',
          {
            method: 'resetPassword',
            email: this.email,
            password: this.inputPassword.password
          },
          {emulateJSON: true}
        )
        .then(res=>{
          if(res.data.status==true){
            alert('验证通过')
            this.$router.back()
          }
          else{
            alert(res.data.info)
          }
        })
        .catch(err=>{})
      }
    }
  }
}
</script>

<style scoped>
h5{
  color: gray;
}
.form{
  /* border: 1px solid black; */
  width: 330px;
  margin: 30px auto;
}
.submit{
  margin: 0px auto;
  border-radius: 5px;
  width: 130px; height: 35px;
  text-align: center; line-height: 35px;
  /* background: rgb(50, 50, 50); */
  border: 2px solid #BF0A10;
  color: #BF0A10;
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
.block-input input:focus{
  border-bottom: 2px solid #BF0A10;
}
.prolong{
  width: 300px;
}
</style>