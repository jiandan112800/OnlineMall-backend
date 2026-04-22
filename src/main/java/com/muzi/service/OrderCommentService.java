package com.muzi.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muzi.constants.Constants;
import com.muzi.entity.Order;
import com.muzi.entity.OrderComment;
import com.muzi.entity.OrderGoods;
import com.muzi.entity.User;
import com.muzi.exception.ServiceException;
import com.muzi.mapper.OrderCommentMapper;
import com.muzi.mapper.OrderGoodsMapper;
import com.muzi.mapper.OrderMapper;
import com.muzi.utils.TokenUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderCommentService extends ServiceImpl<OrderCommentMapper, OrderComment> {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderGoodsMapper orderGoodsMapper;

    public List<OrderComment> listByGoodId(Long goodId) {
        return list(new QueryWrapper<OrderComment>()
                .eq("good_id", goodId)
                .orderByDesc("create_time"));
    }

    public void addComment(OrderComment comment) {
        if (comment == null || comment.getOrderId() == null || comment.getGoodId() == null) {
            throw new ServiceException(Constants.CODE_400, "评价信息不完整");
        }
        if (comment.getScore() == null || comment.getScore() < 1 || comment.getScore() > 5) {
            throw new ServiceException(Constants.CODE_400, "评分必须在1-5之间");
        }
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser == null) {
            throw new ServiceException(Constants.CODE_401, "请先登录");
        }
        Order order = orderMapper.selectById(comment.getOrderId());
        if (order == null || currentUser.getId() == null || order.getUserId() != currentUser.getId()) {
            throw new ServiceException(Constants.CODE_400, "订单不存在或无权评价");
        }
        if (!"已收货".equals(order.getState())) {
            throw new ServiceException(Constants.CODE_400, "订单未确认收货，无法评价");
        }
        long count = this.count(new QueryWrapper<OrderComment>().eq("order_id", comment.getOrderId()));
        if (count > 0) {
            throw new ServiceException(Constants.CODE_400, "该订单已评价");
        }
        Long goodsCount = orderGoodsMapper.selectCount(new QueryWrapper<OrderGoods>()
                .eq("order_id", comment.getOrderId())
                .eq("good_id", comment.getGoodId()));
        if (goodsCount == null || goodsCount == 0) {
            throw new ServiceException(Constants.CODE_400, "订单中不存在该商品");
        }

        comment.setOrderNo(order.getOrderNo());
        comment.setUserId(Long.valueOf(currentUser.getId()));
        String username = StringUtils.hasLength(currentUser.getNickname()) ? currentUser.getNickname() : currentUser.getUsername();
        comment.setUsername(username);
        comment.setCreateTime(DateUtil.now());
        save(comment);
    }

    public boolean hasComment(Long orderId) {
        return count(new QueryWrapper<OrderComment>().eq("order_id", orderId)) > 0;
    }
}

