package chapter4.propertyediter;

import java.beans.PropertyEditorSupport;

public class MinMaxPropertyEditor extends PropertyEditorSupport {
    public String getAsText() {
        return String.valueOf(getValue());
    }

    public void setAsText(String text) {
        Integer value = Integer.parseInt(text);

        if(value < 0){
            setValue(0);
        } else if(value > 100) {
            setValue(100);
        }
    }
}
