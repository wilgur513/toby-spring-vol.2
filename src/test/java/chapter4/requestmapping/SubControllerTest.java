package chapter4.requestmapping;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SubControllerTest {
    MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new SubController()).build();
    }

    @Test
    public void shouldInheritRequestMappingAndMethod() throws Exception {
        expectStatusAndUrl(mvc.perform(get("/list")), "my-list");
    }

    @Test
    public void shouldInheritMappingInfo() throws Exception {
        expectStatusAndUrl(mvc.perform(get("/hello")), "sub-hello");
    }

    @Test
    public void shouldOverrideRequestMapping() throws Exception {
        expectStatusAndUrl(mvc.perform(get("/override")), "sub-override");
    }

    private void expectStatusAndUrl(ResultActions actions, String viewName) throws Exception {
        actions.andExpect(status().isOk())
                .andExpect(forwardedUrl(viewName));
    }

    private void expectIsNotAllowed(ResultActions actions) throws Exception {
        actions.andExpect(status().isMethodNotAllowed());
    }

    private void expectIsNotFound(ResultActions actions) throws Exception {
        actions.andExpect(status().isNotFound());
    }
}
