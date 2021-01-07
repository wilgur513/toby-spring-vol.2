package chapter3;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public abstract class SimpleController implements Controller {
    private String viewName;
    private String[] params;

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if(viewName == null)
            throw new IllegalStateException();

        Map<String, String> params = new HashMap<>();
        for(String param : this.params){
            String value = req.getParameter(param);
            if(value == null)
                throw new IllegalStateException();
            params.put(param, value);
        }

        Map<String, Object> model = new HashMap<>();
        control(params, model);

        return new ModelAndView(viewName, model);
    }

    public abstract void control(Map<String, String> params, Map<String, Object> model);
}
