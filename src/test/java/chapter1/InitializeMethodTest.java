package chapter1;

import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.annotation.PostConstruct;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class InitializeMethodTest {
    @Test
    public void useInitializeBeanInterface(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyClass1.class);
        MyClass1 cls = ac.getBean(MyClass1.class);
        assertThat(cls.log, is("constructor initialize"));
    }

    @Test
    public void useInitMethod() {
        ApplicationContext ac = new GenericXmlApplicationContext("/chapter1/initializeMethod.xml");
        MyClass2 cls = ac.getBean(MyClass2.class);
        assertThat(cls.log, is("constructor initialize"));
    }

    @Test
    public void usePostConstructor() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyClass3.class);
        MyClass3 cls = ac.getBean(MyClass3.class);
        assertThat(cls.log, is("constructor initialize"));
    }

    

    static class MyClass{
        String log;

        public MyClass(){
            log = "constructor";
        }
    }

    static class MyClass1 extends MyClass implements InitializingBean {
        @Override
        public void afterPropertiesSet() throws Exception {
            log += " initialize";
        }
    }

    static class MyClass2 extends MyClass{
        public void initialize(){
            log += " initialize";
        }
    }

    static class MyClass3 extends MyClass{
        @PostConstruct
        public void initialize(){
            log += " initialize";
        }
    }
}
