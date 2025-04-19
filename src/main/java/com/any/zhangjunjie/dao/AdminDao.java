package com.any.zhangjunjie.dao;

import com.any.zhangjunjie.entity.Movie;
import com.any.zhangjunjie.entity.Result;

public interface AdminDao {
    boolean change(Movie movie);

    Result black(int userId);
}
