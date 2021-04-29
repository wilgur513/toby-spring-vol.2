package chapter3.view;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.InternalResourceView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class InternalResourceViewController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("message", "Hello " + httpServletRequest.getParameter("name"));
        return new ModelAndView(new InternalResourceView("/WEB-INF/view/hello.jsp"), model);
    }
}
