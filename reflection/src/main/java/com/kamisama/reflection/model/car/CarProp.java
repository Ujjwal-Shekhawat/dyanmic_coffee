package com.kamisama.reflection.model.car;

import com.kamisama.reflection.model.Prop;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "car.company.name")
public class CarProp implements Prop {
    String url;
    @Override
    public String getUrl() {
        return "CarProp";
    }
}
