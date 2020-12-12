package chapter1;

import org.junit.Test;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class FactoryBeanTest {
    @Test
    public void useFactoryBeanInterface() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyFactory1.class);
        MyClass cls = ac.getBean(MyClass.class);
        assertThat(cls.log, is("use Factory"));

        MyClass cls2 = ac.getBean(MyClass.class);
        assertThat(cls, is(not(cls2)));
    }

    @Test
    public void useXmlSetFactoryMethod() {
        ApplicationContext ac = new GenericXmlApplicationContext("/chapter1/factoryBean.xml");
        MyClass cls = ac.getBean("myClass1", MyClass.class);
        assertThat(cls.log, is("use Factory"));
    }

    @Test
    public void useXmlSetFactoryBeanAndMethod() {
        ApplicationContext ac = new GenericXmlApplicationContext("/chapter1/factoryBean.xml");
        MyClass cls = ac.getBean("myClass2", MyClass.class);
        assertThat(cls.log, is("use Factory"));
    }

    static class MyFactory1 implements FactoryBean<MyClass>{
        @Override
        public MyClass getObject() throws Exception {
            MyClass cls = new MyClass();
            cls.init();

            return cls;
        }

        @Override
        public Class<?> getObjectType() {
            return MyClass.class;
        }

        @Override
        public boolean isSingleton() {
            return false;
        }
    }

    static class MyFactory2{
        public MyClass create(){
            MyClass cls = new MyClass();
            cls.init();

            return cls;
        }
    }

    static class MyClass{
        String log;

        public static MyClass create(){
            MyClass cls = new MyClass();
            cls.init();
            return cls;
        }

        public MyClass(){
            log = "not Use Factory";
        }

        public void init(){
            log = "use Factory";
        }
    }
}
