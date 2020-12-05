package chapter1;

import chapter1.hello.Hello;
import chapter1.printer.Printer;
import chapter1.printer.StringPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class NotConfigureBeans {
    @Bean
    public Printer printer(){
        return new StringPrinter();
    }
}
