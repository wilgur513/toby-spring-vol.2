package chapter4.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
}
