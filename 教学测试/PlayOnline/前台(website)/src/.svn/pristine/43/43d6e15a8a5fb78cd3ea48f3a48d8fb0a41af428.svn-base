<template>
  <div class="module">
    <div v-if="isLogin">
      <div class="name">
        {{user.name?user.name:'未登录'}}
      </div>
      <div>
        {{user.email}}
      </div>
      <div class="btn reset-password"
      @click="toResetPassword">
        更改密码
      </div>
      <div class="btn logout"
      @click="logout">
        退出登陆
      </div>
    </div>
    <div v-else>
      <div class="btn reset-password"
      @click="toLogin">
        登陆/注册
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: '',
  data: function () {
    return {
      
    }
  },
  computed: {
    user() {
      return this.$store.getters.user
    },
    isLogin() {
      return this.$store.getters.isLogin
    }
  },
  methods: {
    toLogin() {
      this.$router.push('/Sider/Login')
    },
    toResetPassword() {
      this.$router.push('/Sider/ResetPassword')
    },
    logout() {
      this.$store.commit('logout')
      localStorage.removeItem('user')
      this.toLogin()
    }
  }
}
</script>

<style scoped>
.module{
  box-sizing: border-box;
  box-shadow: inset 5px 0px 10px -5px gray;
  padding: 10px;
  text-align: center;
  color: rgb(30,30,30);
}
.name{
  height: 30px; line-height: 30px;
  font-size: 20px;
  /* border: 1px solid black; */
  font-weight: bold;
}
.btn{
  margin: 10px 0px;
  height: 40px; line-height: 40px;
  border-radius: 5px;
  font-size: 17px;
}
.reset-password{
  background: white;
  color: #d9020a;
}
.logout{
  background: #d9020a;
  color: white;
}
</style>