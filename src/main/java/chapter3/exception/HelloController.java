package chapter3.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class HelloController {
    @RequestMapping("/hello")
    public ModelAndView hello() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ModelAndView exceptionHandler() {
        Map<String, Object> model = new HashMap<>();
        model.put("message", "error!");

        return new ModelAndView("/WEB-INF/view/hello.jsp", model);
    }

}
