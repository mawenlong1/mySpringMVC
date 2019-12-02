package com.mwl.framework.web;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author mawenlong
 * @date 2019/05/06
 *
 * 根据url获取处理器
 */
public class MyHandlerMapping {
    private Object controller;
    private Method method;
    private Pattern pattern;

    public MyHandlerMapping(Object controller, Method method, Pattern pattern) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
