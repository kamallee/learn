package com.crazyfish.kamal.service;

import com.crazyfish.kamal.test.testaop.Guardian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-06-24 上午11:52
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:conf/applicationContext.xml")
public class TestClass {
    @Resource
    private Guardian guardian;
    @Test
    public void testSome(){
        guardian.foundMonkey();
    }
}
