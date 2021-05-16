package chapter4.propertyediter;

import org.springframework.core.convert.converter.Converter;

public class StringToLevelConverter implements Converter<String, Level> {
    @Override
    public Level convert(String s) {
        return Level.valueOf(Integer.parseInt(s));
    }
}
