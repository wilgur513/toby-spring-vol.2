package chapter3;

import chapter3.hello.HelloController;
import chapter3.hello.HelloSpring;
import chapter3.servlet.fortest.ConfigDispatcherServlet;
import chapter3.simple.adapter.SimpleHandlerAdapter;
import org.junit.*;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class ConfigDispatcherServletTest extends AbstractDispatcherServletTest{
    @Test
    public void modelAndViewTestWithDispatcherServlet() throws ServletException, IOException {
        ConfigDispatcherServlet servlet = new ConfigDispatcherServlet();
        servlet.setLocations("/chapter3/spring-servlet.xml");
        servlet.setClasses(HelloSpring.class);
        servlet.init(new MockServletConfig("spring"));

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/hello");
        request.addParameter("name", "Spring");

        MockHttpServletResponse response = new MockHttpServletResponse();

        servlet.service(request, response);
        ModelAndView modelAndView = servlet.getModelAndView();

        assertThat(modelAndView.getViewName(), is("/WEB-INF/view/hello.jsp"));
        assertThat((String)modelAndView.getModel().get("message"), is("Hello Spring"));
    }

    @Test
    public void modelAndViewTestUsingAbstractDispatcherServletTest1() throws ServletException, IOException {
        ModelAndView modelAndView = setRelativeLocations("spring-servlet.xml")
                                    .setClasses(HelloSpring.class)
                                    .initRequest("/hello")
                                    .addParameter("name", "Spring")
                                    .runService()
                                    .getModelAndView();

        assertThat(modelAndView.getViewName(), is("/WEB-INF/view/hello.jsp"));
        assertThat((String)modelAndView.getModel().get("message"), is("Hello Spring"));
    }

    @Test
    public void modelAndViewTestUsingAbstractDispatcherServletTest2() throws ServletException, IOException {
        setRelativeLocations("spring-servlet.xml").setClasses(HelloSpring.class)
            .initRequest("/hello").addParameter("name", "Spring")
            .runService()
            .assertModel("message", "Hello Spring")
            .assertViewName("/WEB-INF/view/hello.jsp");
    }

    @Test
    public void modelAndViewTestUsingAbstractDispatcherServletTest3() throws ServletException, IOException {
        setRelativeLocations("spring-servlet.xml").setClasses(HelloSpring.class)
                .initRequest("/hello").addParameter("name", "Spring")
                .runService()
                .assertModel("message", "Hello Spring")
                .assertViewName("/WEB-INF/view/hello.jsp");
    }
}
