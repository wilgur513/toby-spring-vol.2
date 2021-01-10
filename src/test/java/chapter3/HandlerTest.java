package chapter3;

import chapter3.hello.HelloController;
import chapter3.hello.interceptor.AllHandleInterceptor;
import chapter3.hello.interceptor.OnlyPreHandleInterceptor;
import chapter3.simple.adapter.SimpleHandlerAdapter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class HandlerTest extends AbstractDispatcherServletTest{

    @Before
    public void setUp() throws Exception {
        setClasses(SimpleHandlerAdapter.class);
    }

    @Ignore
    @Test
    public void controllerBeanNameHandlerMapping1() throws ServletException, IOException {
        setLocations("/chapter3/controllerBeanName.xml");
        runService("/hello", "Spring");
        checkViewNameAndModel("Spring");
    }

    @Ignore
    @Test
    public void controllerBeanNameHandlerMapping2() throws ServletException, IOException {
        setLocations("/chapter3/controllerBeanName.xml");
        runService("/anythingWantId", "Anything");
        checkViewNameAndModel("Anything");
    }

    @Test
    public void beanNameUrlHandlerMappingUsingOneAsterisk1() throws ServletException, IOException {
        setLocations("/chapter3/beanNameUri.xml");
        runService("/start", "Start");
        checkViewNameAndModel("Start");
    }

    @Test
    public void beanNameUrlHandlerMappingUsingOneAsterisk2() throws ServletException, IOException {
        setLocations("/chapter3/beanNameUri.xml");
        runService("/subway", "Subway");
        checkViewNameAndModel("Subway");
    }

    @Test
    public void beanNameUrlHandlerMappingUsingTwoAsterisk() throws ServletException, IOException {
        setLocations("/chapter3/beanNameUri.xml");
        runService("/hi/a/b/c/d/hello", "ABCD");
        checkViewNameAndModel("ABCD");
    }

    @Ignore
    @Test
    public void controllerClassNameHandlerMapping() throws ServletException, IOException {
        setLocations("/chapter3/controllerClassName.xml");
        runService("/hello", "Spring");
        checkViewNameAndModel("Spring");
    }

    @Test
    public void simpleUrlHandlerMapping() throws ServletException, IOException {
        setLocations("/chapter3/simpleUrl.xml");
        runService("/hello", "Spring");
        checkViewNameAndModel("Spring");

        runService("/hi/a/b/c/d/e/hello", "ABCDE");
        checkViewNameAndModel("ABCDE");

        runService("/start", "Start");
        checkViewNameAndModel("Start");
    }

    @Test
    public void defaultHandler() throws ServletException, IOException {
        setLocations("/chapter3/defaultHandler.xml");
        setClasses(SimpleHandlerAdapter.class);
        runService("/default");

        assertViewName("defaultPage");
        assertModel("message", "Default Controller");
    }

    @Test
    public void onlyPreHandleInterceptor() throws ServletException, IOException {
        setLocations("/chapter3/onlyPreHandlerInterceptor.xml");
        initRequest("/hello").addParameter("name", "Spring");
        runService();

        OnlyPreHandleInterceptor interceptor = getBean(OnlyPreHandleInterceptor.class);
        assertThat(interceptor.preHandler, is(true));
        assertThat(interceptor.postHandler, is(false));
        assertThat(interceptor.afterCompletion, is(false));
    }

    @Test
    public void allHandleInterceptor() throws ServletException, IOException {
        setLocations("/chapter3/allHandlerInterceptor.xml");
        initRequest("/hello").addParameter("name", "Spring");
        runService();

        AllHandleInterceptor interceptor = getBean(AllHandleInterceptor.class);
        assertThat(interceptor.preHandler, is(true));
        assertThat(interceptor.postHandler, is(true));
        assertThat(interceptor.afterComplete, is(true));
    }

    @Test
    public void simpleHandlerAdapter() throws ServletException, IOException {
        setClasses(SimpleHandlerAdapter.class, HelloController.class);
        initRequest("/hello").addParameter("name", "Spring").runService();
        checkViewNameAndModel("Spring");
    }

    private void runService(String url, String name) throws ServletException, IOException {
        initRequest(url).addParameter("name", name).runService();
    }

    private void checkViewNameAndModel(String name){
        assertViewName("/WEB-INF/view/hello.jsp");
        assertModel("message", "Hello " + name);
    }
}
