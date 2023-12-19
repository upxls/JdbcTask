package com.example.demo;

import com.example.demo.config.TestConfig;
import com.example.demo.openapi.model.UserDto;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Testcontainers
@EnableAutoConfiguration
@ContextConfiguration(classes = {UserService.class, TestConfig.class})
public class UserDataIntegrationTest extends AbstractDBContainersBaseTest {

    @Autowired
    private UserService userService;

    @Test
    public void testFindAllUsers() {

        List<UserDto> users = userService.findAllUsers();

        assertNotNull(users);
        assertEquals(8, users.size());
        assertTrue(users.stream()
                .anyMatch(user -> user.getId().equals("3")
                        && "btaylor".equals(user.getUsername())
                        && "Bob".equals(user.getName())
                        && "Taylor".equals(user.getSurname())));
    }
}
