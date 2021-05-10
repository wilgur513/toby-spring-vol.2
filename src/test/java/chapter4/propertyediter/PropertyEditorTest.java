package chapter4.propertyediter;

import org.junit.Test;
import org.springframework.beans.propertyeditors.CharsetEditor;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PropertyEditorTest {
    @Test
    public void shouldBindingCharset() {
        CharsetEditor editor = new CharsetEditor();
        editor.setAsText("UTF-8");

        assertThat(editor.getValue(), is(instanceOf(Charset.class)));
        assertThat((Charset)editor.getValue(), is(Charset.forName("UTF-8")));
    }
}
