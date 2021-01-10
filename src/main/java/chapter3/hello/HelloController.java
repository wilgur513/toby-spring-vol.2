package chapter3.hello;

import chapter3.simple.controller.RequiredParams;
import chapter3.simple.controller.SimpleController;
import chapter3.simple.controller.ViewName;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("/hello")
public class HelloController implements SimpleController {
    @ViewName("/WEB-INF/view/hello.jsp")
    @RequiredParams({"name"})
    @Override
    public void control(Map<String, String> params, Map<String, Object> model) {
        model.put("message", "Hello " + params.get("name"));
    }
}
