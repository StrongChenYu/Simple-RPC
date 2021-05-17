package com.csu.rpc.spring;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "rpc.config")
@PropertySource("classpath:rpc.properties")
public class CustomConfig {
    String name;

}
