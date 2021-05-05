package chapter4.requestmapping;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MyControllerTest {
    MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new MyController()).build();
    }

    @Test
    public void shouldMappingURL() throws Exception {
        expectStatusAndUrl(mvc.perform(get("/my/hello")), "viewName");
        expectStatusAndUrl(mvc.perform(get("/my/hello/")), "viewName");
        expectStatusAndUrl(mvc.perform(get("/my/hello.html")), "viewName");
        expectStatusAndUrl(mvc.perform(get("/my/hello.*")), "viewName");
    }

    @Test
    public void shouldRequestMapMultiUrl() throws Exception {
        expectStatusAndUrl(mvc.perform(get("/my/hi")), "hello");
        expectStatusAndUrl(mvc.perform(get("/my/bye")), "hello");
    }

    @Test
    public void shouldSettingRequestMethod() throws Exception {
        expectStatusAndUrl(mvc.perform(post("/my/post")), "hello");
        expectIsNotAllowed(mvc.perform(get("/my/post")));
    }

    @Test
    public void shouldMappingByParams() throws Exception {
        expectStatusAndUrl(mvc.perform(get("/my/edit?type=admin")), "admin");
        expectStatusAndUrl(mvc.perform(get("/my/edit?type=member")), "member");
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
