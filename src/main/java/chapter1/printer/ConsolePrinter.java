package chapter1.printer;

public class ConsolePrinter implements Printer{
    public void print(String message) {
        System.out.println(message);
    }
}
