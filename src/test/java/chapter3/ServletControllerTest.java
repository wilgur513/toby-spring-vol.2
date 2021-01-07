package chapter3;

import chapter3.hello.HelloController;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class ServletControllerTest extends AbstractDispatcherServletTest{
    @Test
    public void helloServletController() throws IOException, ServletException {
        setClasses(SimpleServletHandlerAdapter.class, HelloServlet.class);
        initRequest("/hello").addParameter("name", "Spring");

        assertThat(runService().getContentAsString(), containsString("Hello Spring"));
    }

    @Test
    public void hiServletController() throws ServletException, IOException {
        setClasses(SimpleServletHandlerAdapter.class, HiServlet.class);
        initRequest("/hi").addParameter("name", "Spring");

        assertThat(runService().getContentAsString(), containsString("Hi Spring"));
    }

    @Test
    public void helloServletControllerWrongURL() throws ServletException, IOException {
        setClasses(SimpleServletHandlerAdapter.class, HelloServlet.class);
        initRequest("/wrongURL").addParameter("name", "Spring");

        String content = runService().getContentAsString();
        assertThat(content, not(containsString("Hello Spring")));
        assertThat(content, is(""));
    }

    @Test
    public void simpleHelloController() {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("name", "Spring");

        HelloController controller = new HelloController();
        controller.control(params, map);

        assertThat(map.get("message"), is("Hello Spring"));
    }

    @Component("/hello")
    static class HelloServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String name = req.getParameter("name");
            resp.getWriter().println("Hello " + name);
        }
    }

    @Component("/hi")
    static class HiServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String name = req.getParameter("name");
            resp.getWriter().println("Hi " + name);
        }
    }
}
