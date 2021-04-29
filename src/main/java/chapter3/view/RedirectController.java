package chapter3.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RedirectController {
    @RequestMapping("/re1")
    public ModelAndView redirect1(@RequestParam String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return new ModelAndView(new RedirectView("/toby2/app/hello"), map);
    }

    @RequestMapping("/re2")
    public ModelAndView redirect2(@RequestParam String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        return new ModelAndView("redirect:/app/hello", map);
    }
}
