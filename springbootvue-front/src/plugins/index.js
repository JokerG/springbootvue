//plugins/index.js
import Cookies from './components/cookies';

export default {
  install(Vue,options) {
    Vue.prototype.$Cookies = Cookies
  }
}
