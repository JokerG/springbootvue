package com.joker.springboot.vue.back.web;

import com.joker.springboot.vue.back.model.User;
import com.joker.springboot.vue.back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("list")
	public List<User> list(User user) {
		return userService.list(user);
	}

}
