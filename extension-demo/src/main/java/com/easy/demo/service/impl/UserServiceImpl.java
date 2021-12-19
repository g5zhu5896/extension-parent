package com.easy.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easy.demo.entity.User;
import com.easy.demo.mapper.UserMapper;
import com.easy.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author zzz
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
