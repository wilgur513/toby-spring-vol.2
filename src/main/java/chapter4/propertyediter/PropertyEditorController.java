package chapter4.propertyediter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

class Member {
    public int id;
    public int age;

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

@Controller
public class PropertyEditorController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Level.class, new LevelPropertyEditor());
        binder.registerCustomEditor(Integer.class, "age", new MinMaxPropertyEditor());
    }

    @RequestMapping("/search")
    public void search(@ModelAttribute Level level) {
    }

    @RequestMapping("/member")
    public void member(@ModelAttribute Member member){
    }
}
