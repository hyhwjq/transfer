package com.yjcloud.transfer.application;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    private static final String SERVICE_PROVIDER_XML = "applicationContext.xml";

    private ClassPathXmlApplicationContext context;

    private static Application instance = new Application();

    private Application() {
        context = new ClassPathXmlApplicationContext(new String[]{SERVICE_PROVIDER_XML});
    }

    public static Application getInstance() {
        return instance;
    }

    public ClassPathXmlApplicationContext getContext() {
        return context;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String bean) {
        return (T) getInstance().getContext().getBean(bean);
    }

    public static <T> T getBean(Class<T> type) {
        return getInstance().getContext().getBean(type);
    }

    public static <T> T getBean(String bean, Class<T> type) {
        return getInstance().getContext().getBean(bean, type);
    }
}
