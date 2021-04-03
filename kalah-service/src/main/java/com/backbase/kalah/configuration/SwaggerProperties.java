package com.backbase.kalah.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    private String title;
    private String description;
    private String version;
    private boolean enabled;
}
