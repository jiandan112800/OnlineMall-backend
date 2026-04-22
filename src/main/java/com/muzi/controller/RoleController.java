package com.muzi.controller;

import com.muzi.common.Result;
import com.muzi.entity.User;
import com.muzi.utils.TokenUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @PostMapping("/role")
    public Result getUserRole(){
        User currentUser = TokenUtils.getCurrentUser();
        return Result.success(currentUser.getRole());
    }
}
