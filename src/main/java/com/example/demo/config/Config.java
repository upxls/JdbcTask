package com.example.demo.config;

import com.example.demo.dao.UserDao;
import com.example.demo.mapper.CustomUserRecordMapper;
import com.example.demo.model.DbDrivers;
import com.example.demo.model.User;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.table;

@Configuration
public class Config {

    @Bean
    public List<UserDao> dataSourceList(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.getDataSources().stream().map(dataSourceProperty -> {
            String driveClassName = defineDriverClassName(dataSourceProperty.getStrategy());

            DataSource dataSource = DataSourceBuilder.create()
                    .driverClassName(driveClassName)
                    .url(dataSourceProperty.getUrl())
                    .username(dataSourceProperty.getName())
                    .password(dataSourceProperty.getPassword())
                    .build();

            DSLContext dslContext = DSL.using(dataSource, SQLDialect.valueOf(dataSourceProperty.getStrategy().toUpperCase()));

            RecordMapper<Record, User> mapper = new CustomUserRecordMapper(dataSourceProperty.getMapping());

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