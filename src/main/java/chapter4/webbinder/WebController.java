package chapter4.webbinder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
public class WebController {
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setAllowedFields("name");
        binder.setRequiredFields("name");
    }

    @ModelAttribute
    public Member member() {
        return new Member(100, "null");
    }

    @RequestMapping("/update")
    public void update(@ModelAttribute Member member) {

    }
}
