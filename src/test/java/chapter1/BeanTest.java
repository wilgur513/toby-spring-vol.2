package chapter1;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

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
