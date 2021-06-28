package com.project.love_data.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.*;

import java.util.*;
import java.util.stream.StreamSupport;

@Log4j2
@Configuration
@RequiredArgsConstructor
@PropertySource("classpath:properties/LocationTag.properties")
public class TagPropertiesReader {
    @Autowired
    private final Environment environment;

    public String getTag (String key){
        return environment.getProperty(key);
    }

    public Properties getTags() {
        Map<String, Object> tagMap = new HashMap<>();

        Properties props = new Properties();
        MutablePropertySources propSrcs = ((AbstractEnvironment) environment).getPropertySources();
        StreamSupport.stream(propSrcs.spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
                .flatMap(Arrays::<String>stream)
                .forEach(propName -> props.setProperty(propName, environment.getProperty(propName)));

        return props;
    }
}
