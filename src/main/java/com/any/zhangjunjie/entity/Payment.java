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
    public Payment() {

    }
    public Payment(int paymentId, int method, int status, Long money) {
        this.paymentId = paymentId;
        this.method = method;
        this.status = status;
        this.money = money;
    }
    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }
}
