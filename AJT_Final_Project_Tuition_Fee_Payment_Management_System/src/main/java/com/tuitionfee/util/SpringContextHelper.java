package com.tuitionfee.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring IoC Container Helper.
 * Loads and manages the Spring ApplicationContext from applicationContext.xml.
 * Allows Servlets to access Spring-managed service singletons with injected DAOs.
 */
public class SpringContextHelper {

    private static ApplicationContext context;

    static {
        try {
            // Initialize Spring Core IoC Container using XML configuration
            context = new ClassPathXmlApplicationContext("applicationContext.xml");
            System.out.println("[SpringContextHelper] Spring ApplicationContext initialized successfully.");
        } catch (Exception e) {
            System.err.println("[SpringContextHelper] Failed to initialize Spring ApplicationContext: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a Spring-managed bean by its ID and expected class type.
     * @param beanName Bean ID configured in applicationContext.xml
     * @param requiredType Expected Class type
     * @return Managed bean instance
     */
    public static <T> T getBean(String beanName, Class<T> requiredType) {
        if (context == null) {
            throw new IllegalStateException("Spring ApplicationContext is not initialized.");
        }
        return context.getBean(beanName, requiredType);
    }

    /**
     * Returns the underlying Spring ApplicationContext instance.
     * @return ApplicationContext
     */
    public static ApplicationContext getContext() {
        return context;
    }
}
