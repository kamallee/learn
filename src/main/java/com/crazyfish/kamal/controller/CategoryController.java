package com.crazyfish.kamal.controller;

import com.crazyfish.kamal.domain.Category;
import com.crazyfish.kamal.service.ICategoryService;
import com.crazyfish.kamal.test.TestThrift.HelloWorldServiceImple;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-07-05 下午2:25
 */
@RestController("categoryController")
public class CategoryController {
    @Resource
    @Qualifier("categoryService")
    private ICategoryService categoryService;
    @Resource(name = "testTask")
    private AsyncTaskExecutor testTask;
    @Resource
    private IHelloWorldService.Iface helloService;

    @RequestMapping("/api/category/insert.json")
    public String insert(@RequestParam(value = "date",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        Category category = new Category();
        category.setName("kamal");
        category.setSort(2);
        category.setTemplate(3);
        category.setValid(1);

        categoryService.insert(category);
        try {
            System.out.print(helloService.sayHello("xxx"));
        }catch (Exception e){
            e.printStackTrace();
        }

        testTask.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
            }
        });

        return "xx";
    }
}
