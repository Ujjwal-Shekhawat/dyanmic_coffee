package com.kamisama.reflection.config;

import com.kamisama.reflection.model.Prop;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Reflection implements BeanDefinitionRegistryPostProcessor {
    private static final String PROP_BEAN_PREFIX = "registered_bean_";
    private List<Class<? extends Prop>> clients;

    public Reflection(Environment environment, String packageToScan) throws Exception {
        this.clients = new ArrayList<>();

        // Use Reflections to scan classes in the specified package
        Reflections reflections = new Reflections(packageToScan);
        Set<Class<? extends Prop>> clientClasses = reflections.getSubTypesOf(Prop.class);

        // Instantiate classes found in the package
        for (Class<? extends Prop> clientClass : clientClasses) {
            if (clientClass.isAnnotationPresent(ConfigurationProperties.class)) {
                this.clients.add(clientClass);
            } else {
                throw new Exception("All the classes implementing Props intergace should have ConfigurationProperties annotation");
            }
        }
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        for (Class<? extends Prop> clientClass : clients) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clientClass);
            registry.registerBeanDefinition(PROP_BEAN_PREFIX + getName(clientClass), beanDefinitionBuilder.getBeanDefinition());
            System.out.println("Registered bean: " + PROP_BEAN_PREFIX + getName(clientClass));
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    private String getName(Class<?> clientClass) {
        try {
            Prop instance = (Prop) clientClass.newInstance();
            String url = instance.getUrl();
            return url;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
