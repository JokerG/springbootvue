package com.joker.springboot.vue.back.contorller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserRestController {

	private RestTemplate restTemplate;
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JSONObject login(String loginId, String loginPwd) {
		JSONObject result = new JSONObject();
		result.put("state", "ok");
		result.put("token", "111");
		return result;
	}
}
