package chapter3;

import chapter3.servletfortest.SimpleGetAndPostServlet;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.*;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class SimpleGetAndPostServletTest {
    @Test
    public void getWithParameter() throws ServletException, IOException {
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/hello");
        req.addParameter("name", "Spring");

        MockHttpServletResponse resp = new MockHttpServletResponse();

        HttpServlet servlet = new SimpleGetAndPostServlet();
        servlet.service(req, resp);

        assertThat(resp.getContentAsString(), is("<html><body>\nHello Spring\n</body></html>\n"));
    }

    @Test
    public void postWithSession() throws ServletException, IOException {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("value", "Post Servlet");

        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/hello");
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        HttpServlet servlet = new SimpleGetAndPostServlet();
        servlet.service(request, response);

        assertThat(response.getContentAsString(), is("<html><body>Hello Post Servlet</body></html>\n"));
        assertThat(session.getAttribute("result"), is("Hi!"));
    }
}