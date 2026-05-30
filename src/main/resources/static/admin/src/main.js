import Vue from 'vue';
import axios from "axios";
import App from './App.vue'
import plugins from './plugins'
import router from './router'
import store from './store'
import echarts from './assets/js/echarts.min.js'

Vue.config.productionTip = false;
Vue.prototype.$axios = axios;
Vue.use(plugins, {
    host: `http://${window.location.hostname}:5000/`,
});

new Vue({
  router,
  store,
  echarts,
  render: h => h(App)
}).$mount('#app')
