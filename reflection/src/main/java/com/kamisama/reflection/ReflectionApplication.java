package com.kamisama.reflection;

import com.kamisama.reflection.config.Reflection;
import com.kamisama.reflection.model.CloudProp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class ReflectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReflectionApplication.class, args);
    }

    @Bean
    public Reflection reflection(ConfigurableEnvironment environment) throws Exception {
        return new Reflection(environment, "com.kamisama.reflection.model");
    }
}
