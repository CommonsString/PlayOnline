<template>
  <div>
    <div class="block-top"></div>
    <v-Title/>
    <router-view class="view"/>
  </div>
</template>

<script>
import Title from '@/components/Title'
export default {
  name: 'sider',
  components: {
    'v-Title': Title
  }
}
</script>

<style scoped>
.block-top{
  width: 100%;
  height: 20px;
  background: #c0020a;
}
.view{
  position: absolute;
  width: 100%;
  top: 66px;
  bottom: 0px;
  overflow-y: scroll;
}
</style>