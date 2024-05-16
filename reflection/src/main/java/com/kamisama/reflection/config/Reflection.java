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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//@Component
public class Reflection implements BeanDefinitionRegistryPostProcessor {

    private static final String PROP_BEAN_PREFIX = "registered_bean_";
    private final List<Class<? extends Prop>> clients;
    private final Environment environment;
    private final String packageToScan;

    @Autowired
    public Reflection(Environment environment, @Value("${reflection.package-to-scan}") String packageToScan) {
        this.environment = environment;
        this.packageToScan = packageToScan;
        this.clients = new ArrayList<>();
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // Use Reflections to scan classes in the specified package
        Reflections reflections = new Reflections(packageToScan);
        Set<Class<? extends Prop>> clientClasses = reflections.getSubTypesOf(Prop.class);

        // Instantiate classes found in the package
        for (Class<? extends Prop> clientClass : clientClasses) {
            if (clientClass.isAnnotationPresent(ConfigurationProperties.class)) {
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clientClass);
                registry.registerBeanDefinition(PROP_BEAN_PREFIX + getName(clientClass), beanDefinitionBuilder.getBeanDefinition());
                System.out.println("Registered bean: " + PROP_BEAN_PREFIX + getName(clientClass));
            } else {
                throw new RuntimeException("All the classes implementing Props interface should have ConfigurationProperties annotation");
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // No implementation needed here
    }

    private String getName(Class<?> clientClass) {
        try {
            Prop instance = (Prop) clientClass.getDeclaredConstructor().newInstance();
            String url = instance.getUrl();
            return url;
        } catch (Exception e) {
            throw new RuntimeException("Error while getting name for bean", e);
        }
    }
}
