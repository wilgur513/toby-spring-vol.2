package chapter4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/cookieValue")
    public String cookieValue(@CookieValue("value") String value, ModelMap model) {
        model.put("value", value);
        return "some view";
    }

    @RequestMapping("/requestHeader")
    public String requestHeader(@RequestHeader("Host") String host,
                                @RequestHeader("Keep-Alive") boolean alive,
                                Map<String, Object> model) {
        model.put("Host", host);
        model.put("Keep-Alive", alive);

        return "some view";
    }
}
