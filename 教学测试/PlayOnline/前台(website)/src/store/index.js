import vue from 'vue'
import Vuex from 'vuex'
import state from './state'
import getters from './getters'
import mutations from './mutations'

vue.use(Vuex)

const store = new Vuex.Store({
	state,
	getters,
	mutations
})

export default store