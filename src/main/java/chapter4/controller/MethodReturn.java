package chapter4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MethodReturn {
    @ModelAttribute("modelAttribute")
    public String myModel() {
        return "modelAttribute";
    }

    @RequestMapping("/methodAttributeTest")
    public void methodAttributeTest() {
    }

    @RequestMapping("/returnString")
    public String returnString() {
        return "hello";
    }

    @RequestMapping("/returnVoid")
    public void returnVoid(){}

    @RequestMapping("/user")
    public User user() {
        return User.of("id", "pwd", 1);
    }

    @RequestMapping("/returnModelMap")
    public ModelMap modelMap() {
        ModelMap map = new ModelMap();
        map.put("hello", "hello");
        return map;
    }
}
