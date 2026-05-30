export default {
  //管理 Vue 应用中的状态
  state() {
    return {
      active_index: 1,
	  auth: [],
    }
  },
  mutations: {
    //合并 state.alert 和传入的 obj，更新状态
    set_alert(state, obj) {
      state.alert = Object.assign(state.alert, obj);
    },
	set_auth(state,list){
		state.auth = list;
	},
  }
}
