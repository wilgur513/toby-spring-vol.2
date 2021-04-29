package chapter3.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.xml.MarshallingView;

import javax.annotation.Resource;
import java.util.*;

@Controller
public class MarshallerView {
    @Resource
    MarshallingView marshallingView;

    @RequestMapping("/marshaller")
    public ModelAndView marshaller(@RequestParam String name) {
        Map<String, Object> map = new HashMap<>();
        map.put("info", new Info("Hello " + name));
        return new ModelAndView(marshallingView, map);
    }
}
