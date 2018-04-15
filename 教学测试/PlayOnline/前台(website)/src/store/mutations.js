export default{
  setHeader(state, header){
    state.header = header?header:'状元王'
  },
  setFileGroup(state, item){
    state.currentFileGroup = item
  },
  changeBlock(state, blockId){
    state.activeBlockId = blockId
  },
  login(state, user) {
    state.user = user
  },
  logout(state, user) {
    state.user = {}
  }
}