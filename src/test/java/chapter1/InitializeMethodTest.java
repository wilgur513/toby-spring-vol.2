package chapter1;

import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class InitializeMethodTest {
    @Test
    public void useInitializeBeanInterface(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyClass.class);
        MyClass cls = ac.getBean(MyClass.class);
        assertThat(cls.log, is("constructor initialize"));
    }

    static class MyClass implements InitializingBean {
        String log;

        public MyClass() {
            log = "constructor";
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            log += " initialize";
        }
    }
}
