package chapter1.hello;

import chapter1.printer.Printer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

public class Hello {
    private String name;
    @Autowired
    @Qualifier("stringPrinter")
    public Printer printer;

    public String sayHello(){
        return "Hello " + name;
    }

    public void print(){
        printer.print(sayHello());
    }

    @Value("Spring")
    public void setName(String name){
        this.name = name;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
