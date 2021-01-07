package chapter3.hello;

import chapter3.SimpleController;
import java.util.Map;

public class HelloController extends SimpleController {
    public HelloController() {
        setParams(new String[]{"name"});
        setViewName("/WEB-INF/view/hello.jsp");
    }

    @Override
    public void control(Map<String, String> params, Map<String, Object> model) {
        model.put("message", "Hello " + params.get("name"));
    }
}
