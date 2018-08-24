package com.joker.springboot.vue.back.service.impl;

import com.joker.springboot.vue.back.model.User;
import com.joker.springboot.vue.back.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
}
