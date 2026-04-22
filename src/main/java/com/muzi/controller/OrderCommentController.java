package com.muzi.controller;

import com.muzi.annotation.Authority;
import com.muzi.common.Result;
import com.muzi.entity.AuthorityType;
import com.muzi.entity.OrderComment;
import com.muzi.service.OrderCommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/comment")
public class OrderCommentController {

    @Resource
    private OrderCommentService orderCommentService;

    @Authority(AuthorityType.noRequire)
    @GetMapping("/good/{goodId}")
    public Result listByGood(@PathVariable Long goodId) {
        return Result.success(orderCommentService.listByGoodId(goodId));
    }

    @Authority(AuthorityType.requireLogin)
    @PostMapping
    public Result save(@RequestBody OrderComment comment) {
        orderCommentService.addComment(comment);
        return Result.success();
    }
}




