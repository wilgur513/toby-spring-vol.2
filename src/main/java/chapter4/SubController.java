package chapter4;

import org.springframework.web.servlet.ModelAndView;

public class SubController extends SuperController{
    @Override
    public ModelAndView hello() {
        return new ModelAndView("sub-hello");
    }
}
