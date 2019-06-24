package com.mwl.demo.service;

import java.util.Set;

/**
 * @author mawenlong
 * @date 2019/05/06
 */
public interface BookService {
    /**
     * 添加
     */
    boolean add(String id, String name);
    /**
     * 根据id查询
     */
    String get(String id);
    /**
     * 根据id删除
     */
    boolean delete(String id);
    /**
     * 修改
     */
    boolean modify(String id, String name);
    /**
     * 获取所有名称
     */
    Set<String> getAll();
}
