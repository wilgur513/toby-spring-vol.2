package chapter4.controller;

import org.springframework.stereotype.Controller;
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
}
