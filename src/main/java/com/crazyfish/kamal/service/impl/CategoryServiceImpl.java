package com.crazyfish.kamal.service.impl;

import com.crazyfish.kamal.dao.ICategoryDAO;
import com.crazyfish.kamal.domain.Category;
import com.crazyfish.kamal.service.ICategoryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-07-05 下午2:23
 */
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {
    @Resource
    @Qualifier("categoryDAO")
    private ICategoryDAO categoryDAO;

    @Override
    public boolean insert(Category category) {
        return 1 == categoryDAO.add(category);
    }
}
