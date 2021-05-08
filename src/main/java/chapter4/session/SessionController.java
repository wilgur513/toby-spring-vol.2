package chapter4.session;

import chapter4.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("user")
public class SessionController {
    @RequestMapping("/form")
    public User form() {
        return User.of("id", "pwd", 1);
    }

    @RequestMapping("/submit")
    public void Submit(@ModelAttribute User user) {

    }
}
