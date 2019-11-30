package com.mwl.framework.web;

import com.mwl.framework.annotation.MyController;
import com.mwl.framework.annotation.MyRequestMapping;
import com.mwl.framework.annotation.MyRequestParam;
import com.mwl.framework.context.MyApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author mawenlong
 * @date 2019/05/06
 *
 * DispatcherServlet
 */
public class MyDispatcherServlet extends HttpServlet {

    private static final String LOCATION = "contextConfigLocation";
    private List<MyHandlerMapping> handlerMappings;
    private Map<MyHandlerMapping, MyHandlerAdapter> handlerAdapters;

    @Override
    public void init(ServletConfig config) throws ServletException {
        MyApplicationContext context =
                new MyApplicationContext(config.getInitParameter(LOCATION));
        initStrategies(context);
    }

    protected void initStrategies(MyApplicationContext context) {
        initMultipartResolver(context);
        initLocaleResolver(context);
        initThemeResolver(context);
        initHandlerMappings(context);
        initHandlerAdapters(context);
        initHandlerExceptionResolvers(context);
        initRequestToViewNameTranslator(context);
        initViewResolvers(context);
        initFlashMapManager(context);
    }

    private void initMultipartResolver(MyApplicationContext context) {
        // TODO
    }

    private void initLocaleResolver(MyApplicationContext context) {
        // TODO
    }

    private void initThemeResolver(MyApplicationContext context) {
        // TODO
    }

    private void initHandlerMappings(MyApplicationContext context) {
        handlerMappings = new ArrayList<MyHandlerMapping>();
        try {
            for (String beanName : context.getBeanNames()) {
                Object object = context.getBean(beanName);
                if (!object.getClass().isAnnotationPresent(MyController.class)) {
                    continue;
                }
                String baseUrl = "";
                if (object.getClass().isAnnotationPresent(MyRequestMapping.class)) {
                    baseUrl = object.getClass().getAnnotation(MyRequestMapping.class).value();
                }
                for (Method method : object.getClass().getMethods()) {
                    if (!method.isAnnotationPresent(MyRequestMapping.class)) {
                        continue;
                    }
                    String reg =baseUrl+ method.getAnnotation(MyRequestMapping.class).value();
                    Pattern pattern = Pattern.compile(reg);
                    handlerMappings.add(new MyHandlerMapping(object, method, pattern));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initHandlerAdapters(MyApplicationContext context) {
        handlerAdapters = new HashMap<MyHandlerMapping, MyHandlerAdapter>();
        for (MyHandlerMapping handler : handlerMappings) {
            List<String> params = new ArrayList<String>();
            Parameter[] parameters = handler.getMethod().getParameters();
            for (Parameter parameter : parameters) {
                if (parameter.getType().getName() == HttpServletRequest.class.getName() ||
                    parameter.getType().getName() == HttpServletResponse.class.getName()) {
                    params.add(parameter.getType().getName());
                    continue;
                }
                if (parameter.isAnnotationPresent(MyRequestParam.class)) {
                    params.add(parameter.getAnnotation(MyRequestParam.class).value());
                }
            }
            handlerAdapters.put(handler, new MyHandlerAdapter(params));
        }
    }

    private void initHandlerExceptionResolvers(MyApplicationContext context) {
        // TODO
    }

    private void initRequestToViewNameTranslator(MyApplicationContext context) {
        // TODO
    }

    private void initViewResolvers(MyApplicationContext context) {
        // TODO
    }

    private void initFlashMapManager(MyApplicationContext context) {
        // TODO
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        MyHandlerMapping handler = getHandler(req);
        if (handler == null) {
            return;
        }
        MyHandlerAdapter ha = getHandlerAdapter(handler);
        if (ha == null) {
            return;
        }
        ha.handle(req, resp, handler);
    }

    private MyHandlerMapping getHandler(HttpServletRequest req) {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        for (MyHandlerMapping handler : handlerMappings) {
            if (handler.getPattern().matcher(url).matches()) {
                return handler;
            }
        }
        return null;
    }

    private MyHandlerAdapter getHandlerAdapter(MyHandlerMapping handler) {
        return handlerAdapters.get(handler);
    }
}