package com.kamisama.reflection.config;

import com.kamisama.reflection.model.Prop;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

//@Component
public class Reflection implements BeanDefinitionRegistryPostProcessor {

    private static final String PROP_BEAN_PREFIX = "registered_bean_";
    private final List<Class<? extends Prop>> clients;
    private final Environment environment;
    private final String packageToScan;

    @Autowired
    public Reflection(Environment environment, String packageToScan) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.environment = environment;
        this.packageToScan = packageToScan;
        this.clients = new ArrayList<>();

        // Use Reflections to scan classes in the specified package
        Reflections reflections = new Reflections(packageToScan);
        Set<Class<? extends Prop>> clientClasses = reflections.getSubTypesOf(Prop.class);
        this.clients.addAll(clientClasses);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // Instantiate classes found in the package
        for (Class<? extends Prop> clientClass : clients) {
            if (clientClass.isAnnotationPresent(ConfigurationProperties.class)) {
                String prefix = clientClass.getAnnotation(ConfigurationProperties.class).prefix();
                String url = environment.getProperty(prefix + ".url", clientClass.getSimpleName());
                System.out.println(prefix + "url");
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clientClass);
                registry.registerBeanDefinition(PROP_BEAN_PREFIX + url, beanDefinitionBuilder.getBeanDefinition());
                System.out.println("Registered bean: " + PROP_BEAN_PREFIX + url);
            } else {
                throw new RuntimeException("All the classes implementing Props interface should have ConfigurationProperties annotation");
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // No implementation needed here
    }
}
