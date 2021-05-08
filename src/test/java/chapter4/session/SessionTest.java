package chapter4.session;

import chapter4.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class SessionTest {
    MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new SessionController())
                .setViewResolvers(viewResolver()).build();
    }

    @Test
    public void shouldModelObjectAddToSession() throws Exception {
        Object user = mvc.perform(get("/form"))
            .andReturn().getRequest().getSession().getAttribute("user");

        assertThat(user, is(User.of("id", "pwd", 1)));
    }

    @Test
    public void shouldSessionAttributeBindingToParameter() throws Exception {
        Object user = mvc.perform(
            post("/submit")
                .sessionAttr("user", User.of("id", "pwd", 1))
                .param("level", "2")
        ).andReturn().getModelAndView().getModel().get("user");

        assertThat(user, is(User.of("id", "pwd", 2)));
    }


    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
