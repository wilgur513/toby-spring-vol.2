package chapter4.propertyediter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PropertyEditorController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Level.class, new LevelPropertyEditor());
    }

    @RequestMapping("/search")
    public void search(@ModelAttribute("level") Level level, Model model) {
        model.addAttribute(level);
    }
}
