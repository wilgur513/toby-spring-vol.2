package chapter4;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;

@RequestMapping("/my")
public class MyController {
    @RequestMapping("/hello")
    public ModelAndView hello(){
        return new ModelAndView("viewName");
    }

    @RequestMapping({"/hi", "/bye"})
    public ModelAndView multi() {
        return new ModelAndView("hello");
    }

    @RequestMapping(value="/post", method= RequestMethod.POST)
    public ModelAndView post() {
        return new ModelAndView("hello");
    }

    @RequestMapping(value="/edit", params="type=admin")
    public ModelAndView editAdmin() {
        return new ModelAndView("admin");
    }

    @RequestMapping(value="/edit", params="type=member")
    public ModelAndView editMember() {
        return new ModelAndView("member");
    }
}
