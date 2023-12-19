package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@ConfigurationProperties
@Configuration
public class DataSourceProperties {
    private List<DataSourceProperty> dataSources;

    @Data
    public static class DataSourceProperty {
        private String name;
        private String strategy;
        private String url;
        private String table;
        private String user;
        private String password;
        private UserMapping mapping;
    }

    @Data
    public static class UserMapping {
        private String id;
        private String username;
        private String name;
        private String surname;
    }
}
