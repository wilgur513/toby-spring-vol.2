package chapter4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MethodParametersController {
    @RequestMapping("/pathVariable/{value}")
    public void pathVariable(@PathVariable("value") String value, ModelMap model) {
        model.put("value", value);
    }
}
