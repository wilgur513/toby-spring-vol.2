package chapter3;

import chapter3.simple.controller.RequiredParams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class ViewTest extends AbstractDispatcherServletTest{
    @Configuration
    static class InternalResourceViewConfig {
        @Bean
        public ViewController controller() {
            return new ViewController();
        }
    }

    @Controller
    static class ViewController{
        @RequestMapping("/view/{type}")
        public ModelAndView internal(@PathVariable("type") String type,
                                     @RequiredParams("name") String name) {
            Map<String, Object> model = new HashMap<>();
            model.put("message", "Hello " + name);

            return new ModelAndView(view(type, "view url"), model);
        }
    }

    static View view(String type, String url) {
        return new InternalResourceView(url);
    }

    @Test
    public void internalResourceView() throws ServletException, IOException {
        setClasses(InternalResourceViewConfig.class);
        initRequest("/view/internal");
        runService();
        assertThat(getModelAndView().getView(), instanceOf(InternalResourceView.class));
    }
}
