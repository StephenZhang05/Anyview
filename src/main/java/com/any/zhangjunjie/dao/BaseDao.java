package com.any.zhangjunjie.dao;

import java.util.LinkedList;

public interface BaseDao
{
    //分页查询
    LinkedList queryPages(String[] selectFields, String tableName, String limit, String offset);

}
