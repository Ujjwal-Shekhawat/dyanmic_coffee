package com.kamisama.reflection.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "person")
public class PersonProp implements Prop {
    private String kami;
    private String kamisama;
    private String url;

    @Override
    public String getUrl() {
        return "PersonProp";
    }
}
