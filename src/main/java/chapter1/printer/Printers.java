package chapter1.printer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Printers {
    @Autowired
    private List<Printer> list;

    public int size() {
        return list.size();
    }
}
