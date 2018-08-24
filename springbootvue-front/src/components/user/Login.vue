<template>
  <div class="login_page">
    <section class="form_contianer">
      <div class="manage_tip">
        <p>第一个后台管理系统</p>
      </div>
      <el-form>
        <el-form-item prop="loginId">
          <el-input placeholder="用户名" v-model="loginId"><span></span></el-input>
        </el-form-item>
        <el-form-item prop="loginPwd">
          <el-input type="password" placeholder="密码" v-model="loginPwd"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="submit_btn" @click="login" >登陆</el-button>
        </el-form-item>
      </el-form>
    </section>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        loginId:'',
        loginPwd:''
      }
    },
    methods: {
      login () {
        let self = this
        this.$http({
          method: 'get',
          url: 'http://user.com/login',
          data: {
            loginId: this.loginId,
            loginPwd: this.loginPwd
          }
        })
          .then((r) => {
            if(r.data.state === 'ok') {
              self.$Cookies.setCookie('jwtToken', r.data.token);
              self.$router.push({path:'/'})
            } else {
              self.$message.error('账号或密码错误，请重新填写');
            }
          })
      }
    }
  }
</script>

<style scoped>
  @import '../../assets/css/login.css';
</style>
