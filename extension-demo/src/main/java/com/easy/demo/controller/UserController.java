package com.easy.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.easy.demo.dict.UserSourceDict;
import com.easy.demo.entity.User;
import com.easy.demo.enums.UserIsEnableEnum;
import com.easy.demo.service.UserService;
import com.easy.utils.EnumDictUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(description = "userCon")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "列表")
    @PostMapping("/list")
    public List<User> list(UserIsEnableEnum isEnable, @RequestParam("source") UserSourceDict source) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(isEnable != null, "is_enable", isEnable);
        wrapper.eq("source", source);
        return userService.list(wrapper);
    }

    @ApiOperation(value = "列表")
    @GetMapping("/list2")
    public List<User> list2(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(user.getIsEnable() != null, "is_enable", user.getIsEnable());
        wrapper.eq(user.getSource() != null, "source", user.getSource());
        return userService.list(wrapper);
    }

    @ApiOperation(value = "保存")
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody User user) {
        UserSourceDict tx = EnumDictUtils.convert("tx", UserSourceDict.class);
        UserIsEnableEnum convert = EnumDictUtils.convert("2", UserIsEnableEnum.class);

        return userService.saveOrUpdate(user);
    }
}
