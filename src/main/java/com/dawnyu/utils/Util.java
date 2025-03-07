package com.dawnyu.utils;

import com.dawnyu.bean.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *  @author dawnyu
 */
public class Util {
    public static User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
