package chapter4;

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
        expectStatusAndUrl(mvc.perform(get("/my/hello")));
        expectStatusAndUrl(mvc.perform(get("/my/hello/")));
        expectStatusAndUrl(mvc.perform(get("/my/hello.html")));
        expectStatusAndUrl(mvc.perform(get("/my/hello.*")));
    }

    @Test
    public void shouldRequestMapMultiUrl() throws Exception {
        expectStatusAndUrl(mvc.perform(get("/my/hi")));
        expectStatusAndUrl(mvc.perform(get("/my/bye")));
    }

    @Test
    public void shouldSettingRequestMethod() throws Exception {
        expectStatusAndUrl(mvc.perform(post("/my/post")));
        expectIsNotAllowed(mvc.perform(get("/my/post")));
    }

    private void expectStatusAndUrl(ResultActions actions) throws Exception {
        actions.andExpect(status().isOk())
            .andExpect(forwardedUrl("/WEB-INF/view/hello.jsp"));
    }

    private void expectIsNotAllowed(ResultActions actions) throws Exception {
        actions.andExpect(status().isMethodNotAllowed());
    }
}
