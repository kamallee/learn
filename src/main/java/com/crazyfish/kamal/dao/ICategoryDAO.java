package com.crazyfish.kamal.dao;

import com.crazyfish.kamal.domain.Category;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-07-05 下午2:15
 */
@Repository("categoryDAO")
public interface ICategoryDAO {
    @Insert("INSERT INTO connect_category (name,sort,valid,template) values(#{name},#{sort},#{valid},#{template})")
    int add(Category category);
}
