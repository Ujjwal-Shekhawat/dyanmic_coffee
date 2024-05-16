package com.kamisama.reflection.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class Runner implements CommandLineRunner {
//    private final PersonProp registered_bean_PersonProp;

//    private final CloudProp registered_bean_CloudProviderProp;

//    private final CarProp registered_bean_CarProp;

    @Override
    public void run(String... args) throws Exception {
//        System.out.println(registered_bean_PersonProp.getUrl());
//        System.out.println(registered_bean_CloudProviderProp.getUrl());
//        System.out.println(registered_bean_CarProp.getUrl());
    }
}
