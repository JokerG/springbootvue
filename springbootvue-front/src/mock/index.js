/*mock/index.js*/
import Mock from 'mockjs'

//验证账号密码
let uTable = {
  'loginPwd': '123456',
  'loginId': 'admin',
  'token': 'admin123456'
};
const data = Mock.mock('http://user.com/login', (param) => {
  let dataJson = JSON.parse(param.body);
  if((dataJson.loginId === uTable.loginId) && (dataJson.loginPwd === uTable.loginPwd)) {
    return {
      'state':'ok',
      'jwtToken':uTable.token
    }
  } else {
    return {
      'state':'no',
      'jwtToken':''
    }
  }
});

export default {
  data
}
