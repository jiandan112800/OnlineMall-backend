package com.muzi.mapper;

import com.muzi.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends BaseMapper<Order> {
    @Update("update t_order set state = '已支付' where order_no = #{orderNo}")
    void payOrder(String orderNo);

    @MapKey("id")
    List<Map<String, Object>> selectByUserId(int userId);

    /**
     * 根据用户和查询条件筛选订单列表（我的订单查询）
     */
    @MapKey("id")
    List<Map<String, Object>> selectByUserAndCondition(Map<String, Object> params);

    @Update("update t_order set state = '已收货' where order_no = #{orderNo}")
    boolean receiveOrder(String orderNo);

    Map<String, Object> selectByOrderNo(String orderNo);
}
