package com.dawnyu.maple.controller;

import com.dawnyu.maple.bean.RespBean;
import com.dawnyu.maple.service.UserService;
import com.dawnyu.maple.websocket.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;

/**
 *@author dawnyu
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private MyWebSocket myWebSocket;

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

    @GetMapping("/test/websocket")
    public String testWebSocket() {
       MyWebSocket.sendInfo("大家好，我是vae","1002");
        return "haha";
    }
}
