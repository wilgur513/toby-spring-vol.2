package chapter4;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

@RequestMapping("/my")
public class MyController {
    @RequestMapping("/hello")
    public ModelAndView hello(){
        return new ModelAndView("/WEB-INF/view/hello.jsp");
    }

    @RequestMapping({"/hi", "/bye"})
    public ModelAndView multi() {
        return new ModelAndView("/WEB-INF/view/hello.jsp");
    }
}
