package com.mwl.demo.service;

import java.util.Set;

/**
 * @author mawenlong
 * @date 2019/05/06
 */
public interface BookService {
    boolean add(String id, String name);

    String get(String id);

    boolean delete(String id);

    boolean modify(String id, String name);

    Set<String> getAll();
}
