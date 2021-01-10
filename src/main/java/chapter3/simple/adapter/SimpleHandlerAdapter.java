package chapter3.simple.adapter;

import chapter3.simple.controller.RequiredParams;
import chapter3.simple.controller.SimpleController;
import chapter3.simple.controller.ViewName;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SimpleHandlerAdapter implements HandlerAdapter {
    @Override
    public boolean supports(Object o) {
        return o instanceof SimpleController;
    }

    @Override
    public ModelAndView handle(HttpServletRequest req, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Method m = ReflectionUtils.findMethod(o.getClass(), "control", Map.class, Map.class);
        ViewName viewName = AnnotationUtils.getAnnotation(m, ViewName.class);
        RequiredParams requiredParams = AnnotationUtils.getAnnotation(m, RequiredParams.class);

        Map<String, String> params = new HashMap<>();
        for(String param : requiredParams.value()){
            String value = req.getParameter(param);
            if(value == null)
                throw new IllegalStateException();
            params.put(param, value);
        }

        Map<String, Object> model = new HashMap<>();
        ((SimpleController)o).control(params, model);

        return new ModelAndView(viewName.value(), model);
    }

    @Override
    public long getLastModified(HttpServletRequest httpServletRequest, Object o) {
        return -1;
    }
}
