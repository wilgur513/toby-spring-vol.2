package chapter4.propertyediter;

import org.springframework.core.convert.converter.Converter;

public class LevelToStringConverter implements Converter<Level, String> {
    @Override
    public String convert(Level level) {
        return String.valueOf(level.intValue());
    }
}
