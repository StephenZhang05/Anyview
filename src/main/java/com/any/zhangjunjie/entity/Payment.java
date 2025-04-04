package com.any.zhangjunjie.entity;
/**
 * @author zhangjunjie
 * 支付实体类
 * 包含支付信息，如支付金额、支付方式、支付状态等
 * 与订单实体类关联，一个订单可能包含多个支付信息
 */
public class Payment {
    private int paymentId;
    private int method;
    private int status;
    private Long money;
}
