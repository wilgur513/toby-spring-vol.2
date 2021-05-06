package chapter4.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MethodParametersTest {
    MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new MethodParametersController()).build();
    }

    @Test
    public void pathVariableAnnotation() throws Exception {
        mvc.perform(get("/pathVariable/hello"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("value", "hello"));
    }

    @Test
    public void badRequestPathVariable() throws Exception {
        mvc.perform(get("/errorPathVariable/notNumber"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void requestParam() throws Exception {
        mvc.perform(
            get("/requestParam")
                .param("id", String.valueOf(100))
                .param("name", "Lee")
        ).andExpect(model().attribute("id", 100))
            .andExpect(model().attribute("name", "Lee"));
    }

    @Test
    public void requestParamUsingMap() throws Exception {
        mvc.perform(
                get("/requestParamUsingMap")
                        .param("id", String.valueOf(100))
                        .param("name", "Lee")
        ).andExpect(model().attribute("id", 100))
            .andExpect(model().attribute("name", "Lee"));
    }

    @Test
    public void cookieValue() throws Exception {
        mvc.perform(
            get("/cookieValue")
                .cookie(new Cookie("value", "hello"))
        ).andExpect(model().attribute("value", "hello"));
    }

    @Test
    public void requestHeader() throws Exception {
        mvc.perform(
                get("/requestHeader")
                    .header("Host", "host")
                    .header("Keep-Alive", true)
        ).andExpect(model().attribute("Host", "host"))
        .andExpect(model().attribute("Keep-Alive", true));
    }
}
