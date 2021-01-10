package chapter3;

import java.util.Map;

public class DefaultController extends SimpleController {

    public DefaultController() {
        setViewName("defaultPage");
        setParams(new String[]{});
    }

    @Override
    public void control(Map<String, String> params, Map<String, Object> model) {
        model.put("message", "Default Controller");
    }
}
