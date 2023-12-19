package com.example.demo;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public abstract class AbstractDBContainersBaseTest {

    private static final String DATABASE_NAME = "spring-app";
    private static final String SQL_INIT_SCRIPT1 = "init/init.sql";
    private static final String SQL_INIT_SCRIPT2 = "init/init2.sql";

    @Container
    static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER1 = new PostgreSQLContainer<>("postgres:11.1")
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME)
            .withInitScript(SQL_INIT_SCRIPT1);

    @Container
    static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:5.7")
            .withReuse(true)
            .withDatabaseName(DATABASE_NAME)
            .withInitScript(SQL_INIT_SCRIPT2);

    static {
        POSTGRESQL_CONTAINER1.start();
        MYSQL_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER1::getJdbcUrl);
        registry.add("CONTAINER1.URL", POSTGRESQL_CONTAINER1::getJdbcUrl);
        registry.add("CONTAINER1.USERNAME", POSTGRESQL_CONTAINER1::getUsername);
        registry.add("CONTAINER1.PASSWORD", POSTGRESQL_CONTAINER1::getPassword);

        registry.add("CONTAINER2.URL", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("CONTAINER2.USERNAME", MYSQL_CONTAINER::getUsername);
        registry.add("CONTAINER2.PASSWORD", MYSQL_CONTAINER::getPassword);
    }
}
