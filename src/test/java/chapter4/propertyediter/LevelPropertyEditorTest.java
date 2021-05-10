package chapter4.propertyediter;

import org.junit.Test;
import org.springframework.web.bind.WebDataBinder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class LevelPropertyEditorTest {
    @Test
    public void shouldConvertTextToLevel() {
        LevelPropertyEditor editor = new LevelPropertyEditor();
        editor.setAsText("3");
        Level level = (Level)editor.getValue();

        assertThat(level, is(Level.GOLD));
    }

    @Test
    public void shouldConvertLevelToText() {
        LevelPropertyEditor editor = new LevelPropertyEditor();
        editor.setValue(Level.GOLD);
        String level = editor.getAsText();

        assertThat(level, is("3"));
    }

    @Test
    public void shouldRegisterEditorToWebDataBinder() {
        WebDataBinder binder = new WebDataBinder(null);
        binder.registerCustomEditor(Level.class, new LevelPropertyEditor());
        assertThat(binder.convertIfNecessary("1", Level.class), is(Level.BASIC));
    }
}
