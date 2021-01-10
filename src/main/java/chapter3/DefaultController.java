package chapter3;

import chapter3.simple.controller.RequiredParams;
import chapter3.simple.controller.SimpleController;
import chapter3.simple.controller.ViewName;

import java.util.Map;

public class DefaultController implements SimpleController {
    @ViewName("defaultPage")
    @RequiredParams({})
    @Override
    public void control(Map<String, String> params, Map<String, Object> model) {
        model.put("message", "Default Controller");
    }
}
