package chapter1;

import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.annotation.PostConstruct;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class InitializeMethodTest {
    @Test
    public void useInitializeBeanInterface(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyClass1.class);
        checkIsWellInitialize(ac);
    }

    @Test
    public void useInitMethod() {
        ApplicationContext ac = new GenericXmlApplicationContext("/chapter1/initializeMethod.xml");
        checkIsWellInitialize(ac);
    }

    @Test
    public void usePostConstructor() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyClass3.class);
        checkIsWellInitialize(ac);
    }

    @Test
    public void useBeanAnnotationSetInitMethod() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyConfig.class);
        checkIsWellInitialize(ac);
    }

    private void checkIsWellInitialize(ApplicationContext ac){
        MyClass cls = ac.getBean(MyClass.class);
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

    static class MyConfig{
        @Bean(initMethod = "initialize")
        public MyClass4 myClass(){
            return new MyClass4();
        }

    }

    static class MyClass4 extends MyClass{
        public void initialize(){
            log += " initialize";
        }
    }


}
