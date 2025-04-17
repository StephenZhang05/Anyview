package com.any.zhangjunjie.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

/**
 * @author zhangjunjie
 * 结果泛型
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Result {
    private Boolean success;
    private String message;
    private Object data;
    private Long total;

    private Result(Boolean success, String message, Long total) {
        this.success = success;
        this.message = message;
        this.total = total;
    }

    // 成功方法组
    public static Result ok() {
        return new Result(true, "操作成功", null);
    }

    public static Result ok(String message) {
        return new Result(true, message, null);
    }

    public static Result ok(String message, Long total) {
        return new Result(true, message, total);
    }

    // 失败方法
    public static Result fail(String message) {
        return new Result(false, message, null);
    }
}
