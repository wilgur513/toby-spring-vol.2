package chapter4.propertyediter;

import java.beans.PropertyEditorSupport;

public class LevelPropertyEditor extends PropertyEditorSupport {
    public String getAsText() {
        return String.valueOf(((Level)getValue()).intValue());
    }

    public void setAsText(String text) {
        this.setValue(Level.valueOf(Integer.parseInt(text.trim())));
    }
}
