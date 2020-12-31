package chapter3.servletfortest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConfigDispatcherServlet extends DispatcherServlet {
    private Class<?>[] classes;
    private String[] locations;
    private ModelAndView modelAndView;

    public ConfigDispatcherServlet(String[] locations) {
        this.locations = locations;
    }

    public ConfigDispatcherServlet(Class<?> ...classes) {
        this.classes = classes;
    }

    public void setLocations(String ...locations){
        this.locations = locations;
    }

    public void setRelativeLocations(Class<?> clazz, String ...relativeLocations){
        String[] locations = new String[relativeLocations.length];
        String currentPath = ClassUtils.classPackageAsResourcePath(clazz) + "/";

        for(int i = 0; i < locations.length; i++)
            locations[i] = currentPath + relativeLocations[i];

        setLocations(locations);
    }

    public void setClasses(Class<?> ...classes){
        this.classes = classes;
    }

    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        modelAndView = null;
        super.service(req, resp);
    }

    @Override
    protected WebApplicationContext createWebApplicationContext(ApplicationContext parent) {
        AbstractRefreshableWebApplicationContext wac = new AbstractRefreshableWebApplicationContext() {
            @Override
            protected void loadBeanDefinitions(DefaultListableBeanFactory defaultListableBeanFactory) throws BeansException, IOException {
                if(locations != null){
                    XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
                    xmlReader.loadBeanDefinitions(locations);
                }

                if(classes != null){
                    AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(defaultListableBeanFactory);
                    reader.register(classes);
                }
            }
        };

        wac.setServletConfig(getServletConfig());
        wac.setServletContext(getServletContext());
        wac.refresh();

        return wac;
    }

    protected void render(ModelAndView mv, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        modelAndView = mv;
        super.render(mv, req, resp);
    }

    public ModelAndView getModelAndView(){
        return modelAndView;
    }
}
