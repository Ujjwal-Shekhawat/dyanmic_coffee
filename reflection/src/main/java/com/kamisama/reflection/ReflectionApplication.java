package com.kamisama.reflection;

import com.kamisama.reflection.config.Reflection;
import com.kamisama.reflection.model.CloudProp;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
@AllArgsConstructor
public class ReflectionApplication {
    private CloudProp cloudProp;

    public static void main(String[] args) {
        SpringApplication.run(ReflectionApplication.class, args);
    }

    // Make it lazy (specifically the webclient part), inject all the other props with another configuration class hmm
    @Lazy
    public Reflection reflection(ConfigurableEnvironment environment) throws Exception {
        return new Reflection(environment, "com.kamisama.reflection.model");
    }

    @GetMapping("/")
    public String xyz() {

        return cloudProp.getUrl();
    }
}
