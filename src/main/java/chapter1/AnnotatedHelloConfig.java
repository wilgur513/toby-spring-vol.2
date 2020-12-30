package chapter1;

import chapter1.hello.AnnotatedHello;
import chapter1.hello.Hello;
import chapter1.printer.Printer;
import chapter1.printer.StringPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnotatedHelloConfig {
    @Bean
    public AnnotatedHello annotatedHello(){
        return new AnnotatedHello();
    }

    @Bean
    public Hello hello(Printer printer){
        Hello hello = new Hello();
        hello.setPrinter(printer);

        return hello;
    }

    @Bean
    public Printer printer(){
        return new StringPrinter();
    }
}
