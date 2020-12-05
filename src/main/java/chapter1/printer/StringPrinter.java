package chapter1.printer;

import org.springframework.stereotype.Component;

@Component("printer")
public class StringPrinter implements Printer{
    private StringBuffer buffer = new StringBuffer();

    public void print(String message) {
        buffer.append(message);
    }

    @Override
    public String toString() {
        return buffer.toString();
    }
}
