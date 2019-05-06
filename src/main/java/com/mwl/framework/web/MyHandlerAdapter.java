package com.mwl.framework.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author mawenlong
 * @date 2019/05/06
 * <p>
 * 处理请求
 */
public class MyHandlerAdapter {
    private List<String> params;

    public MyHandlerAdapter(List<String> params) {
        this.params = params;
    }


    public void handle(HttpServletRequest request, HttpServletResponse response,
                         MyHandlerMapping handler) throws Exception {

        Class<?>[] paramTypes = handler.getMethod().getParameterTypes();
        Object[] paramValues = new Object[paramTypes.length];
        Map<String, String[]> reqParameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> param : reqParameterMap.entrySet()) {
            if (params.contains(param.getKey())) {
                paramValues[params.indexOf(param.getKey())] =
                        castString(Arrays.toString(param.getValue()),
                                   paramTypes[params.indexOf(param.getKey())]);
            }
        }
        if (params.contains(HttpServletRequest.class.getName())) {
            paramValues[params.indexOf(HttpServletRequest.class.getName())] = request;
        }
        if (params.contains(HttpServletResponse.class.getName())) {
            paramValues[params.indexOf(HttpServletResponse.class.getName())] = response;
        }
        Object result = handler.getMethod().invoke(handler.getController(), paramValues);

        if (result instanceof String) {
            response.getWriter().write((String) result);
        }
        System.out.println(result);
    }

    private Object castString(String value, Class<?> clazz) {
        if (clazz == String.class) {
            return value;
        } else if (clazz == Integer.class || clazz == int.class) {
            return Integer.valueOf(value);
        } else {
            return null;
        }
    }

    public long getLastModified(HttpServletRequest request, Object handler) {
        return -1;
    }
}
