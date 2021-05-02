package chapter4;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

public class SubController extends SuperController{
    @Override
    public ModelAndView hello() {
        return new ModelAndView("sub-hello");
    }

    @Override
    @RequestMapping("/override")
    public ModelAndView override() {
        return new ModelAndView("sub-override");
    }
}
