package chapter4.webbinder;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.propertyeditors.CharsetEditor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WebBinderTest {
    MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new WebController())
                .setViewResolvers(viewResolver()).build();
    }

    @Test
    public void shouldAllowOnlyName() throws Exception {
        Member member = (Member)mvc.perform(
            get("/update")
                .param("id", "200")
                .param("name", "hello")
        ).andReturn().getModelAndView().getModel().get("member");

        assertThat(member.id, is(100));
        assertThat(member.name, is("hello"));
    }

    @Test
    public void shouldRequireName() throws Exception {
        mvc.perform(get("/update").param("name", ""))
            .andExpect(status().isBadRequest());
    }

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
