package chapter1;

import org.junit.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ObjectFactoryCreatingFactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BeanScopeTest {
    @Test
    public void singletonScope() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class, SingletonClientBean.class);
        Set<SingletonBean> beans = new HashSet<>();

        beans.add(ac.getBean(SingletonBean.class));
        beans.add(ac.getBean(SingletonBean.class));
        assertThat(beans.size(), is(1));

        beans.add(ac.getBean(SingletonClientBean.class).bean1);
        beans.add(ac.getBean(SingletonClientBean.class).bean2);
        assertThat(beans.size(), is(1));
    }

    static class SingletonBean{}
    static class SingletonClientBean{
        @Autowired SingletonBean bean1;
        @Autowired SingletonBean bean2;
    }

    @Test
    public void prototypeScope() {
         ApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, PrototypeClientBean.class);
         Set<PrototypeBean> beans = new HashSet<>();

         beans.add(ac.getBean(PrototypeBean.class));
         assertThat(beans.size(), is(1));
         beans.add(ac.getBean(PrototypeBean.class));
         assertThat(beans.size(), is(2));

         beans.add(ac.getBean(PrototypeClientBean.class).bean1);
         assertThat(beans.size(), is(3));
         beans.add(ac.getBean(PrototypeClientBean.class).bean2);
         assertThat(beans.size(), is(4));
    }

    @Scope("prototype")
    static class PrototypeBean{}
    static class PrototypeClientBean{
        @Autowired PrototypeBean bean1;
        @Autowired PrototypeBean bean2;
    }

    @Test
    public void prototypeObjectFactory() throws Exception {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ObjectFactoryConfig.class);
        MyObject object = ac.getBean("myObject", MyObject.class);
        assertThat(object ,is(not(ac.getBean("myObject", MyObject.class))));

        ObjectFactory factory = ac.getBean("objectFactory", ObjectFactory.class);
        assertThat(object, is(not(factory.getObject())));
    }

    @Test
    public void singletonObjectFactory() throws Exception {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ObjectFactoryConfig.class);
        MyObject object = ac.getBean("singleton", MyObject.class);
        assertThat(object ,is(ac.getBean("singleton", MyObject.class)));

        ObjectFactory factory = ac.getBean("singletonFactory", ObjectFactory.class);
        assertThat(object, is(factory.getObject()));
    }

    @Test
    public void serviceLocatorFactoryBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ServiceLocatorConfig.class);
        MyObjectFactory factoryBean = ac.getBean(MyObjectFactory.class);
        MyObject object = factoryBean.createObject();

        assertThat(object, is(notNullValue()));
        assertThat(object, is(ac.getBean(MyObject.class)));

        ac = new GenericXmlApplicationContext("/chapter1/beanScope.xml");
        object = ac.getBean(MyObject.class);
        assertThat(object, is(not(ac.getBean(MyObject.class))));

        factoryBean = ac.getBean(MyObjectFactory.class);
        assertThat(object, is(not(factoryBean.createObject())));
        assertThat(factoryBean.createObject(), is(not(factoryBean.createObject())));
    }

    @Test
    public void lookupMethodTag() {
        ApplicationContext ac = new GenericXmlApplicationContext("/chapter1/beanScope.xml");
        assertThat(ac.getBean(ClassForLookUpTag.class).isMyObjectClass(), is(true));
    }

    @Configuration
    static class ObjectFactoryConfig{

        @Bean
        public ObjectFactoryCreatingFactoryBean objectFactory(){
            ObjectFactoryCreatingFactoryBean factory = new ObjectFactoryCreatingFactoryBean();
            factory.setTargetBeanName("myObject");
            return factory;
        }

        @Bean
        public ObjectFactoryCreatingFactoryBean singletonFactory(){
            ObjectFactoryCreatingFactoryBean factory = new ObjectFactoryCreatingFactoryBean();
            factory.setTargetBeanName("singleton");
            return factory;
        }

        @Bean
        @Scope("prototype")
        public MyObject myObject(){
            return new MyObject();
        }
        @Bean
        public MyObject singleton(){
            return new MyObject();
        }

    }

    static class MyObject{}

    @Configuration
    static class ServiceLocatorConfig{

        @Bean
        public ServiceLocatorFactoryBean serviceLocatorFactoryBean(){
            ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
            factoryBean.setServiceLocatorInterface(MyObjectFactory.class);
            return factoryBean;
        }
        @Bean
        public MyObject yourObject(){
            return new MyObject();
        }

    }

    interface MyObjectFactory{
        MyObject createObject();
    }

    static abstract class ClassForLookUpTag{
        public abstract MyObject myObject();

        public boolean isMyObjectClass(){
            return myObject().getClass().equals(MyObject.class);
        }
    }
}
