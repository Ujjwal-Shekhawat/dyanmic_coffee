package com.kamisama.reflection.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.provider")
public class CloudProp implements Prop {
    private String aws;
    private String azure;
//    private String url;

    @Override
    public String getUrl() {
        return this.aws;
    }
}
