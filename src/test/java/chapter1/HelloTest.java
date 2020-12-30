package chapter1;

import chapter1.hello.AnnotatedHello;
import chapter1.hello.Hello;
import chapter1.printer.Printer;
import chapter1.printer.StringPrinter;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

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
        assertThat(ac.getBean(Printer.class).toString(), is("Hello Spring"));
    }

    @Test
    public void genericApplicationContext(){
        GenericApplicationContext ac = new GenericApplicationContext();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(ac);
        reader.loadBeanDefinitions("/chapter1/context.xml");
        ac.refresh();

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();
        assertThat(hello.printer.toString(), is("Hello Spring"));
    }

    @Test
    public void genericXmlApplicationContext(){
        GenericXmlApplicationContext ac = new GenericXmlApplicationContext("classpath:/chapter1/context.xml");

        Hello hello = ac.getBean("hello", Hello.class);
        hello.print();
        assertThat(hello.printer.toString(), is("Hello Spring"));
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

    @Test
    public void annotationConfigApplicationContext() {
        ApplicationContext ac = new AnnotationConfigApplicationContext("chapter1.hello");

        try {
            AnnotatedHello hello = ac.getBean("annotatedHello", AnnotatedHello.class);
            fail();
        }catch (NoSuchBeanDefinitionException e){

        }

        AnnotatedHello hello = ac.getBean("hello", AnnotatedHello.class);
        assertThat(hello, is(notNullValue()));
    }

    @Test
    public void annotationConfiguration() {
         ApplicationContext ac = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);
         AnnotatedHello hello = ac.getBean(AnnotatedHello.class);
         AnnotatedHelloConfig config = ac.getBean(AnnotatedHelloConfig.class);

         assertThat(hello, is(notNullValue()));
         assertThat(config, is(notNullValue()));
         assertThat(config.annotatedHello(), is(sameInstance(hello)));
    }

    @Test
    public void notConfigureClassBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(NotConfigureBeans.class);
        Printer printer1 = ac.getBean(Printer.class);
        Printer printer2 = ac.getBean(Printer.class);

        assertThat(printer1, is(notNullValue()));
        assertThat(printer1, is(sameInstance(printer2)));
    }

    @Test
    public void beanMethodDI(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);
        Hello hello = ac.getBean("hello", Hello.class);
        hello.setName("AnnotatedHello");

        assertThat(hello.sayHello(), is("Hello AnnotatedHello"));
        hello.print();
        assertThat(ac.getBean(Printer.class).toString(), is("Hello AnnotatedHello"));
    }

    static class ValueTestClass{
        @Value("1, 2, 3, 4")
        int[] list;
        @Value("1.2")
        double d;
    }

    @Test
    public void valueAnnotationTest() {
      ApplicationContext ac = new AnnotationConfigApplicationContext(ValueTestClass.class);
      ValueTestClass cls = ac.getBean(ValueTestClass.class);

      assertThat(cls.list, is(new int[]{1, 2, 3, 4}));
      assertThat(cls.d, is(1.2));
    }

    @Test
    public void collectionContext() {
        ApplicationContext ac = new GenericXmlApplicationContext("/chapter1/collectionContext.xml");
        CollectionData data = ac.getBean(CollectionData.class);

        assertThat(data.value, is("Spring"));

        assertThat(data.names.size(), is(3));
        assertThat(data.names.get(0), is("Spring"));
        assertThat(data.names.get(1), is("IoC"));
        assertThat(data.names.get(2), is("DI"));

        assertThat(data.map.size(), is(2));
        assertThat(data.map.get("Kim"), is(30));
        assertThat(data.map.get("Lee"), is(35));
    }
}
