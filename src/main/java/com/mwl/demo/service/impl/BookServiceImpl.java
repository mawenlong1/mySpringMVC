package com.mwl.demo.service.impl;

import com.mwl.demo.service.BookService;
import com.mwl.framework.annotation.MyService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author mawenlong
 * @date 2019/05/06
 */
@MyService
public class BookServiceImpl implements BookService {
    private Map<String, String> cache = new HashMap<String, String>();

    public boolean add(String id, String name) {
        if (cache.containsKey(id)) {
            return false;
        } else {
            cache.put(id, name);
            return true;
        }
    }

    public String get(String id) {
        System.out.println(id);
        return cache.get(id);
    }

    public boolean delete(String id) {
        if (cache.containsKey(id)) {
            cache.remove(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean modify(String id, String name) {
        if (cache.containsKey(id)) {
            cache.put(id, name);
            return true;
        } else {
            return false;
        }
    }

    public Set<String> getAll() {
        return cache.keySet();
    }
}
