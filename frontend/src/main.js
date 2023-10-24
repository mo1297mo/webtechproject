import Vue from 'vue'
import App from './App.vue'
import 'bootstrap/dist/css/bootstrap.css'
import router from './router/router.js'

new Vue({
  el: '#app',
  router,
  render: h => h(App)
})