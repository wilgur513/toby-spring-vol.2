package chapter1.printer;

import org.springframework.stereotype.Component;

public class ConsolePrinter implements Printer{
    public void print(String message) {
        System.out.println(message);
    }
}
