//设置cookie
const setCookie = (c_key, c_val, exdays) => {
  let exdate = new Date();
  exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * exdays);//保存的天数
  //字符串拼接cookie
  window.document.cookie = c_key + "=" + c_val + ";path=/;expires=" + exdate.toGMTString();
};

//获取cookie
const getCookie = (c_key) => {
  let arr, reg = new RegExp("(^| )" + c_key + "=([^;]*)(;|$)");

  if (arr = document.cookie.match(reg))

    return unescape(arr[2]);
  else
    return null;
};

//删除cookie
const delCookie = (c_key) => {
  let exp = new Date();
  exp.setTime(exp.getTime() - 1);
  let cval = getCookie(name);
  if (cval != null)
    document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
};

//对外暴露方法
export default {
  setCookie,
  getCookie,
  delCookie
};

