import Vue from 'vue'
import Router from 'vue-router'
import Layout from '@/components/layout'
import Index from '@/pages/Index'
import Block from '@/pages/Block'

import Sider from '@/components/Sider'
import Player from '@/pages/Player'
import Login from '@/pages/Login'
import ResetPassword from '@/pages/resetPassword'

Vue.use(Router)
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/Layout',
      component: Layout,
      redirect: '/Layout/Index',
      children: [
        {
          path: 'Index',
          component: Index
        },
        {
          path: 'Block',
          component: Block
        },
      ]
    },
    {
      path: '/Sider',
      component: Sider,
      children: [
        {
          path: 'Player',
          name: 'player',
          component: Player
        },
        {
          path: 'Login',
          name: 'login',
          component: Login
        },
        {
          path: 'ResetPassword',
          name: 'resetPassword',
          component: ResetPassword
        }
      ]
    }
  ]
})
