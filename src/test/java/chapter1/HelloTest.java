package chapter1;

import chapter1.printer.Printer;
import chapter1.printer.StringPrinter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class HelloTest {
    @Test
    public void staticApplicationContext(){
        StaticApplicationContext ac = new StaticApplicationContext();
        ac.registerSingleton("hello1", Hello.class);

        Hello hello1 = ac.getBean("hello1", Hello.class);
        assertThat(hello1, is(notNullValue()));
    }

    @Test
    public void beanDefinition(){
        StaticApplicationContext ac = new StaticApplicationContext();
        ac.registerSingleton("hello1", Hello.class);

        BeanDefinition def = new RootBeanDefinition(Hello.class);
        def.getPropertyValues().addPropertyValue("name", "Spring");
        ac.registerBeanDefinition("hello2", def);

        Hello hello1 = ac.getBean("hello1", Hello.class);
        Hello hello2 = ac.getBean("hello2", Hello.class);

        assertThat(hello2.sayHello(), is("Hello Spring"));
        assertThat(hello2, is(not(hello1)));
        assertThat(ac.getBeanFactory().getBeanDefinitionCount(), is(2));
    }

    @Test
    public void registerBeanWithDependency(){
        StaticApplicationContext ac = new StaticApplicationContext();
        ac.registerBeanDefinition("printer", new RootBeanDefinition(StringPrinter.class));

        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));
        ac.registerBeanDefinition("hello", helloDef);

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();
        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }

    @Test
    public void genericApplicationContext(){
        GenericApplicationContext ac = new GenericApplicationContext();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
        reader.loadBeanDefinitions("/chapter1/context.xml");
        ac.refresh();

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();
        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }

    @Test
    public void genericXmlApplicationContext(){
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("classpath:/chapter1/context.xml");

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();
        assertThat(ac.getBean("printer").toString(), is("Hello Spring"));
    }

    @Test
    public void applicationContextTree() {
        ApplicationContext parent = new GenericXmlApplicationContext("classpath:/chapter1/parentContext.xml");
        GenericApplicationContext child = new GenericApplicationContext(parent);
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
        reader.loadBeanDefinitions("classpath:/chapter1/childContext.xml");
        child.refresh();

        Printer printer = child.getBean("printer", Printer.class);
        assertThat(printer, is(notNullValue()));

        Hello hello = child.getBean("hello", Hello.class);
        assertThat(hello.sayHello(), is("Hello Child"));

        hello = parent.getBean("hello", Hello.class);
        assertThat(hello.sayHello(), is("Hello Parent"));
    }
}
