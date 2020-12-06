package chapter1;

import chapter1.hello.Hello;

import java.util.List;
import java.util.Map;

public class CollectionData {
    List<String> names;
    Map<String, Integer> map;

    public void setNames(List<String> names) {
        this.names = names;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }
}
