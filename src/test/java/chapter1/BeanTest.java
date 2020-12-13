package chapter1;

import chapter1.classforscan.MyClass;
import chapter1.classforscan.marker.ClassWithMarker;
import chapter1.classforscan.marker.Marker;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.annotation.PostConstruct;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BeanTest {
    @Test
    public void xmlNotUseSpecialTags() {
        ApplicationContext ac = new GenericXmlApplicationContext("/chapter1/notUseSpecialTags.xml");
        SimpleConfig config = ac.getBean(SimpleConfig.class);
        assertThat(config.hello, is(nullValue()));
        assertThat(config.hello().log, is("constructor"));
    }

    @Test
    public void xmlUseSpecialTags() {
        ApplicationContext ac = new GenericXmlApplicationContext("/chapter1/useSpecialTags.xml");
        SimpleConfig config = ac.getBean(SimpleConfig.class);
        assertThat(config.hello.log, is("constructor initialize"));
        assertThat(config.hello().log, is("constructor"));
    }

    @Test
    public void useComponentScanAnnotation() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        MyClass cls = ac.getBean(MyClass.class);
        ClassWithMarker clsWithMarker = ac.getBean(ClassWithMarker.class);
        Marker marker = ac.getBean(Marker.class);

        assertThat(cls, is(notNullValue()));
        assertThat(clsWithMarker, is(notNullValue()));
        assertThat(marker, is(notNullValue()));
    }

    @Test
    public void useComponentScanAnnotationSetMarker() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ComponentScanConfigSetMarker.class);
        Marker marker = ac.getBean(Marker.class);
        ClassWithMarker cls = ac.getBean(ClassWithMarker.class);
        assertThat(marker, is(notNullValue()));
        assertThat(cls, is(notNullValue()));
    }

    @Configuration
    @ComponentScan("chapter1.classforscan")
    static class ComponentScanConfig{

    }

    @Configuration
    @ComponentScan(basePackageClasses = {chapter1.classforscan.marker.Marker.class})
    static class ComponentScanConfigSetMarker{

    }

    @Configuration
    static class SimpleConfig{
        @Autowired
        Hello hello;

        @Bean
        public Hello hello(){
            return new Hello();
        }
    }

    static class Hello{
        String log;

        public Hello(){
            log = "constructor";
        }

        @PostConstruct
        public void init(){
            log += " initialize";
        }
    }

}
