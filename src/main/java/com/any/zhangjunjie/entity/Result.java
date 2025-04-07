package com.any.zhangjunjie.entity;

import java.util.List;

/**
 * @author zhangjunjie
 * 结果泛型
 */
public class Result {
    private Boolean success;
    private String errorMsg;
    private Object data;
    private Long total;

    public Result(boolean b, Object o, Object o1, Object o2) {
    }

    public static Result ok(){
        return new Result(true, null, null, null);
    }
    public static Result ok(Object data){
        return new Result(true, null, data, null);
    }
    public static Result ok(List<?> data, Long total){
        return new Result(true, null, data, total);
    }
    public static Result fail(String errorMsg){
        return new Result(false, errorMsg, null, null);
    }
}
