package com.mwl.demo.controller;

import com.mwl.demo.service.BookService;
import com.mwl.framework.annotation.MyAutowired;
import com.mwl.framework.annotation.MyController;
import com.mwl.framework.annotation.MyRequestMapping;
import com.mwl.framework.annotation.MyRequestParam;

/**
 * @author mawenlong
 * @date 2019/05/06
 */
@MyController
@MyRequestMapping("/book")
public class BookController {
    @MyAutowired
    private BookService bookService;
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILED = "FAILED";

    @MyRequestMapping("/get")
    public String get(@MyRequestParam("id") String id) {
        return bookService.get(id);
    }

    @MyRequestMapping("/add")
    public String add(@MyRequestParam("id") String id, @MyRequestParam("name") String name) {
        if (bookService.add(id, name)) {
            return SUCCESS;
        } else {
            return FAILED;
        }
    }

    @MyRequestMapping("/delete")
    public String delete(@MyRequestParam("id") String id) {
        if (bookService.delete(id)) {
            return SUCCESS;
        } else {
            return FAILED;
        }

    }

    @MyRequestMapping("/modify")
    public String modify(@MyRequestParam("id") String id, @MyRequestParam("name") String name) {
        if (bookService.modify(id, name)) {
            return SUCCESS;
        } else {
            return FAILED;
        }
    }

    @MyRequestMapping("/getAll")
    public String getAll() {
        return bookService.getAll().toString();
    }
}
