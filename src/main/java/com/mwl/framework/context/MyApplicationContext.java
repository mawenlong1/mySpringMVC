package com.mwl.framework.context;

import com.mwl.framework.annotation.MyAutowired;
import com.mwl.framework.annotation.MyController;
import com.mwl.framework.annotation.MyService;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mawenlong
 * @date 2019/05/06
 */
public class MyApplicationContext {
    private final String configLocation;
    private MyBeanDefinitionParser parser;
    /**
     * key为实现类或接口的名称，value为对应的实现类
     */
    private final Map<String, Object> cacheBeans = new HashMap<String, Object>();
    /**
     * 所有的类
     */
    private final List<String> beanNames = new ArrayList<String>();

    public MyApplicationContext(String configLocation) {
        this.configLocation = configLocation;
        refresh();
    }

    public void refresh() {
        parser = new MyBeanDefinitionParser(configLocation);
        doRegisty(parser.getRegistyClassNames());
        doAutowrited();
    }

    private void doAutowrited() {
        for (Map.Entry<String, Object> beanEntry : cacheBeans.entrySet()) {
            Class<?> clazz = beanEntry.getValue().getClass();
            if (clazz.isAnnotationPresent(MyController.class) ||
                clazz.isAnnotationPresent(MyService.class)) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(MyAutowired.class)) {
                        String autowiredBeanName =
                                field.getAnnotation(MyAutowired.class).value().trim();
                        if ("".equals(autowiredBeanName)) {
                            autowiredBeanName = field.getType().getName();
                        }
                        field.setAccessible(true);
                        try {
                            field.set(beanEntry.getValue(), getBean(autowiredBeanName));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    private void doRegisty(List<String> classNames) {
        try {
            for (String className : classNames) {
                Class<?> beanClass = Class.forName(className);
                if (beanClass.isInterface()) {
                    continue;
                }
                Object instance = beanClass.newInstance();
                cacheBeans.put(beanClass.getName(), instance);
                beanNames.add(beanClass.getName());
                for (Class<?> inter : beanClass.getInterfaces()) {
                    cacheBeans.put(inter.getName(), instance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String className) {
        if (cacheBeans.containsKey(className)) {
            return cacheBeans.get(className);
        } else {
            return null;
        }
    }

    public List<String> getBeanNames() {
        return beanNames;
    }
}
