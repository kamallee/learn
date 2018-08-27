package com.crazyfish.kamal.service;

import com.crazyfish.kamal.domain.Category;
import org.springframework.stereotype.Service;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-07-05 下午2:21
 */
public interface ICategoryService {
    boolean insert(Category category);
}
