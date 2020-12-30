package chapter1;

import chapter1.printer.Printer;
import chapter1.printer.StringPrinter;
import org.springframework.context.annotation.Bean;

public class NotConfigureBeans {
    @Bean
    public Printer printer(){
        return new StringPrinter();
    }
}
