package com.dawnyu.maple.controller;

import com.dawnyu.maple.bean.RespBean;
import com.dawnyu.maple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *@author dawnyu
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/currentUserName")
    public String currentUserName() {
        return "haha";
    }

    @RequestMapping("/currentUserId")
    public Long currentUserId() {
        return 102L;
    }

    @RequestMapping("/currentUserEmail")
    public String currentUserEmail() {
        return "haha";
    }



    @RequestMapping(value = "/updateUserEmail",method = RequestMethod.PUT)
    public RespBean updateUserEmail(String email) {
        if (userService.updateUserEmail(email) == 1) {
            return new RespBean("success", "开启成功!");
        }
        return new RespBean("error", "开启失败!");
    }
}
