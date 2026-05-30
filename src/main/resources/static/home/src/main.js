import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import plugins from './plugins'
import Element from "element-ui";
import locale from "element-ui/lib/locale/lang/zh-CN";
// 图片轮播
import 'swiper/dist/css/swiper.min.css'
import 'swiper/dist/js/swiper.min'
import 'element-ui/lib/theme-chalk/index.css'
Vue.config.productionTip = false
// 全局插件注册
Vue.use(plugins).use(Element, {
  locale,
});
// 创建 Vue 实例
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
