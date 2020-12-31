package chapter3;

import chapter3.servletfortest.AfterRunService;
import chapter3.servletfortest.ConfigDispatcherServlet;
import org.junit.After;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

abstract public class AbstractDispatcherServletTest implements AfterRunService {
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;
    protected MockServletConfig config = new MockServletConfig("spring");
    protected MockHttpSession session;

    private ConfigDispatcherServlet servlet;
    private Class<?>[] classes;
    private String[] locations;
    private String[] relativeLocations;
    private String servletPath;

    public AbstractDispatcherServletTest setLocations(String ...locations) {
        this.locations = locations;
        return this;
    }

    public AbstractDispatcherServletTest setRelativeLocations(String ...relativeLocations) {
        this.relativeLocations = relativeLocations;
        return this;
    }

    public AbstractDispatcherServletTest setClasses(Class<?> ...classes) {
        this.classes = classes;
        return this;
    }

    public AbstractDispatcherServletTest setServletPath(String servletPath) {
        if(request == null)
            this.servletPath = servletPath;
        else
            request.setServletPath(servletPath);
        return this;
    }

    public AbstractDispatcherServletTest initRequest(String requestUri, String method){
        request = new MockHttpServletRequest(method, requestUri);
        response = new MockHttpServletResponse();
        if(servletPath != null)
            request.setServletPath(servletPath);
        return this;
    }

    public AbstractDispatcherServletTest initRequest(String requestUri, RequestMethod method){
        return initRequest(requestUri, method.toString());
    }

    public AbstractDispatcherServletTest initRequest(String requestUri){
        return initRequest(requestUri, RequestMethod.GET);
    }

    public AbstractDispatcherServletTest addParameter(String name, String value){
        if(request == null)
            throw new IllegalStateException("request가 초기화되지 않았습니다.");
        request.setParameter(name, value);
        return this;
    }

    public AbstractDispatcherServletTest buildDispatcherServlet() throws ServletException {
        if(classes == null && locations == null && relativeLocations == null)
            throw new IllegalStateException("class와 location 중 하나는 설정해야합니다.");

        servlet = new ConfigDispatcherServlet();
        servlet.setClasses(classes);
        servlet.setLocations(locations);
        if(relativeLocations != null)
            servlet.setRelativeLocations(getClass(), relativeLocations);
        servlet.init(config);

        return this;
    }

    public AfterRunService runService() throws ServletException, IOException {
        if(servlet == null)
            buildDispatcherServlet();
        if(request == null)
            throw new IllegalStateException("request가 준비되지 않았습니다.");

        servlet.service(request, response);
        return this;
    }

    public AfterRunService runService(String requestUri) throws ServletException, IOException {
        initRequest(requestUri);
        return runService();
    }

    @Override
    public WebApplicationContext getContext() {
        if(servlet == null)
            throw new IllegalStateException("servlet이 준비되지 않았습니다.");

        return servlet.getWebApplicationContext();
    }

    @Override
    public String getContentAsString() throws UnsupportedEncodingException {
        return response.getContentAsString();
    }


    @Override
    public <T> T getBean(Class<T> beanType) {
        return getContext().getBean(beanType);
    }

    @Override
    public ModelAndView getModelAndView() {
        return servlet.getModelAndView();
    }

    @Override
    public AfterRunService assertViewName(String viewName) {
        assertThat(getModelAndView().getViewName(), is(viewName));
        return this;
    }

    @Override
    public AfterRunService assertModel(String name, Object value) {
        assertThat(getModelAndView().getModel().get(name), is(value));
        return this;
    }

    @After
    public void closeServletContext(){
        if(servlet != null){
            ((ConfigurableApplicationContext)servlet.getWebApplicationContext()).close();
        }
    }
}
