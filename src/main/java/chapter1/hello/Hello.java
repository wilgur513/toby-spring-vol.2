package chapter1.hello;

import chapter1.printer.Printer;

public class Hello {
    String name;
    Printer printer;

    public Hello() {
    }

    public Hello(String name, Printer printer) {
        this.name = name;
        this.printer = printer;
    }

    public String sayHello(){
        return "Hello " + name;
    }

    public void print(){
        printer.print(sayHello());
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
