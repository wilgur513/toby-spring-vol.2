package chapter4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MethodParametersController {
    @RequestMapping("/pathVariable/{value}")
    public void pathVariable(@PathVariable("value") String value, ModelMap model) {
        model.put("value", value);
    }

    @RequestMapping("/errorPathVariable/{value}")
    public void errorPathVariable(@PathVariable("value") int value) { }

    @RequestMapping("/requestParam")
    public String requestParam(@RequestParam("id") int id, @RequestParam("name") String name, ModelMap model) {
        model.put("id", id);
        model.put("name", name);

        return "some view";
    }

    @RequestMapping("/requestParamUsingMap")
    public String requestParamUsingMap(@RequestParam Map<String, String> params, ModelMap model) {
        model.put("id", Integer.parseInt(params.get("id")));
        model.put("name", params.get("name"));

        return "some view";
    }
}
