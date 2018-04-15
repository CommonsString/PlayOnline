<template>
  <keep-alive exclude="sider">
    <router-view id="app"/>
  </keep-alive>
</template>

<script>
import IP from '@/assets/js/ip'
export default {
  name: '',
  created (){
    this.$router.replace('/Layout')
    this.autoLogin()
  },
  methods: {
    autoLogin() {
      if(localStorage.user){
        let user = JSON.parse(localStorage.user)
        console.log(user)
        this.$http.post(
          IP+'/user',
          {
            method: 'loginUser',
            email: user.email,
            password: user.password
          },
          {emulateJSON: true}
        )
        .then(res=>{
          console.log(res.data)
          if(res.data.status==true){
            console.log('自动登录成功')
            this.$store.commit('login', res.data.user)
          }
          else{
            console.log('自动登陆失败')
            localStorage.removeItem('user')
          }
        })
      }
    }
  },
}
</script>

<style>
body{
  font-family: 微软雅黑;
}
</style>