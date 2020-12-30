package chapter1;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class InitializeMethodTest {
    static String log;

    @Before
    public void setUp() throws Exception {
        log = "";
    }

    @Test
    public void useInterfaceToCheckWellInitAndDestroy(){
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(MyClass1.class);
        checkIsWellInitializeAndDestroy(ac);
    }

    @Test
    public void useXmlToSetInitAndDestroyMethod() {
        ConfigurableApplicationContext ac = new GenericXmlApplicationContext("/chapter1/initializeMethod.xml");
        checkIsWellInitializeAndDestroy(ac);
    }

    @Test
    public void usePostConstructorAndPreDestroy() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(MyClass3.class);
        checkIsWellInitializeAndDestroy(ac);
    }

    @Test
    public void useBeanAnnotationSetInitMethod() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(MyConfig.class);
        checkIsWellInitializeAndDestroy(ac);
    }

    private void checkIsWellInitializeAndDestroy(ConfigurableApplicationContext ac){
        checkIsWellInitialize();
        ac.close();
        checkIsWellDestroy();
    }

    private void checkIsWellInitialize(){
        assertThat(log, is("constructor initialize"));
    }

    private void checkIsWellDestroy() {
        assertThat(log, is("constructor initialize destroy"));
    }

    static class MyClass{
        public MyClass(){
            log = "constructor";
        }
    }

    static class MyClass1 extends MyClass implements InitializingBean, DisposableBean {
        @Override
        public void afterPropertiesSet() throws Exception {
            log += " initialize";
        }

        @Override
        public void destroy() throws Exception {
            log += " destroy";
        }
    }

    static class MyClass2 extends MyClass{
        public void initialize(){
            log += " initialize";
        }

        public void destroy(){
            log += " destroy";
        }
    }

    static class MyClass3 extends MyClass{
        @PostConstruct
        public void initialize(){
            log += " initialize";
        }

        @PreDestroy
        public void destroy(){
            log += " destroy";
        }
    }

    static class MyConfig{
        @Bean(initMethod = "initialize", destroyMethod = "destroy")
        public MyClass4 myClass(){
            return new MyClass4();
        }
    }

    static class MyClass4 extends MyClass{
        public void initialize(){
            log += " initialize";
        }

        public void destroy(){
            log += " destroy";
        }
    }
}
