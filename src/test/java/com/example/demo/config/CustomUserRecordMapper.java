package com.example.demo.config;

import com.example.demo.model.User;
import org.jooq.Record;
import org.jooq.RecordMapper;

import java.util.Map;

public class CustomUserRecordMapper implements RecordMapper<Record, User> {
    private final DataSourceProperties.UserMapping userMapping;

    public CustomUserRecordMapper(DataSourceProperties.UserMapping userMapping) {
        this.userMapping = userMapping;
    }

    @Override
    public User map(Record record) {
        User user = new User();

        user.setId(record.get(userMapping.getId(), String.class));
        user.setUsername(record.get(userMapping.getUsername(), String.class));
        user.setName(record.get(userMapping.getName(), String.class));
        user.setSurname(record.get(userMapping.getSurname(), String.class));

        return user;
    }
}

