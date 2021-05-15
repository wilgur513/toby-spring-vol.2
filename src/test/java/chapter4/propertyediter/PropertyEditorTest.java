package chapter4.propertyediter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.propertyeditors.CharsetEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PropertyEditorTest {
    MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new PropertyEditorController())
                .setViewResolvers(viewResolver()).build();
    }

    @Test
    public void shouldBindingCharset() {
        CharsetEditor editor = new CharsetEditor();
        editor.setAsText("UTF-8");

        assertThat(editor.getValue(), is(instanceOf(Charset.class)));
        assertThat((Charset)editor.getValue(), is(Charset.forName("UTF-8")));
    }

    @Test
    public void shouldBindingLevel() throws Exception {
        mvc.perform(
            get("/search")
                .param("level", "1")
        ).andExpect(model().attribute("level", Level.BASIC));
    }

    @Test
    public void shouldMemberAgeUnder100() throws Exception {
        Member member = (Member)mvc.perform(
            get("/member")
                .param("id", "1000").param("age", "120")
        ).andReturn().getModelAndView().getModel().get("member");

        assertThat(member.age, is(100));
        assertThat(member.id, is(1000));
    }

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
