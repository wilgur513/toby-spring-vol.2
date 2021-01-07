package chapter3;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class AnnotatedControllerTest extends AbstractDispatcherServletTest{
    @Test
    public void annotatedControllerTest() throws ServletException, IOException {
        setClasses(AnnotatedController.class);
        initRequest("/hello").addParameter("name", "Spring");
        runService();

        assertViewName("/WEB-INF/view/hello.jsp");
        assertModel("message", "Hello Spring");

    }

    @Controller
    static class AnnotatedController{
        @RequestMapping("/hello")
        public String hello(@RequestParam("name") String name, ModelMap map){
            map.put("message", "Hello " + name);
            return "/WEB-INF/view/hello.jsp";
        }
    }
}
