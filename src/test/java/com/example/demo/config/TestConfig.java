package com.example.demo.config;

import com.example.demo.dao.UserDao;
import com.example.demo.model.DbDrivers;
import com.example.demo.model.User;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.table;

@Configuration
@ComponentScan("com.example")
public class TestConfig {

    @Value("${CONTAINER1.URL}")
    private String container1Url;

    @Value("${CONTAINER1.USERNAME}")
    private String container1Username;

    @Value("${CONTAINER1.PASSWORD}")
    private String container1Password;

    @Value("${CONTAINER2.URL}")
    private String container2Url;

    @Value("${CONTAINER2.USERNAME}")
    private String container2Username;

    @Value("${CONTAINER2.PASSWORD}")
    private String container2Password;

    @Bean
    @Primary
    public List<UserDao> testDataSourceList(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.getDataSources().stream().map(dataSourceProperty -> {
            String driveClassName = defineDriverClassName(dataSourceProperty.getStrategy());

            DataSource dataSource = DataSourceBuilder.create()
                    .driverClassName(driveClassName)
                    .url(container1Url)
                    .username(container1Username)
                    .password(container1Password)
                    .build();

            if (dataSourceProperty.getTable().equals("user_table")) {
                dataSource = DataSourceBuilder.create()
                        .driverClassName(driveClassName)
                        .url(container2Url)
                        .username(container2Username)
                        .password(container2Password)
                        .build();
            }

            DSLContext dslContext = DSL.using(dataSource, SQLDialect.valueOf(dataSourceProperty.getStrategy().toUpperCase()));

            RecordMapper<Record, User> mapper = new com.example.demo.mapper.CustomUserRecordMapper(dataSourceProperty.getMapping());

            return new UserDao() {
                @Override
                public List<User> findAll() {
                    return dslContext
                            .selectFrom(table(dataSourceProperty.getTable()))
                            .fetch(mapper);
                }
            };
        }).collect(Collectors.toList());
    }

    private String defineDriverClassName(String strategy) {
        if (!DbDrivers.DRIVERS.containsKey(strategy)) {
            throw new RuntimeException("There isn`t such driver for this strategy");
        }
        return DbDrivers.DRIVERS.get(strategy);
    }
}
