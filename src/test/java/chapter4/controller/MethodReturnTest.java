package chapter4.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletConfig;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MethodReturnTest {
    MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new MethodReturn()).setViewResolvers(viewResolver()).build();
    }

    @Test
    public void shouldMakeModelByModelAttribute() throws Exception {
        mvc.perform(get("/methodAttributeTest"))
            .andExpect(model().attribute("modelAttribute", "modelAttribute"));
    }

    @Test
    public void shouldForwardUsingReturnedString() throws Exception {
        mvc.perform(get("/returnString"))
            .andExpect(forwardedUrl("/WEB-INF/view/hello.jsp"));
    }

    @Test
    public void shouldForwardUsingURLName() throws Exception {
        mvc.perform(get("/returnVoid"))
            .andExpect(forwardedUrl("/WEB-INF/view/returnVoid.jsp"));
    }

    @Test
    public void shouldAddReturnedModelObject() throws Exception {
        Object actual = mvc.perform(get("/user"))
            .andReturn().getModelAndView().getModel().get("user");
        assertThat(actual, is(User.of("id", "pwd", 1)));
    }

    @Test
    public void shouldAddToModelMapReturnedModelMapEntry() throws Exception {
        mvc.perform(get("/returnModelMap"))
            .andExpect(model().attribute("modelAttribute", "modelAttribute"))
            .andExpect(model().attribute("hello", "hello"));
    }

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
