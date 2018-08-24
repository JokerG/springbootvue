import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '@/components/user/Login'
import $Cookies from '@/plugins/components/cookies'

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld,
      //路由钩子，在跳转前进行判断
      beforeEnter: (to, from, next) => {
        console.log($Cookies.getCookie('jwtToken'));
        if($Cookies.getCookie('jwtToken')) {
          next();
        }else {
          next({path: '/login'});
        }
      }
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})
