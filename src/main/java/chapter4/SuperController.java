package chapter4;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SuperController {
    @RequestMapping("/list")
    public ModelAndView list() {
        return new ModelAndView("my-list");
    }

    @RequestMapping("/hello")
    public ModelAndView hello() {
        return new ModelAndView("super-hello");
    }
}
