package com.crazyfish.kamal.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-07-05 下午2:18
 */
public class Category {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created;
    private String name;
    private int sort;
    private int valid;
    private int template;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
