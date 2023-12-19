package com.example.demo.model;

import java.util.HashMap;
import java.util.Map;

public class DbDrivers {
    public static final Map<String, String> DRIVERS;

    static {
        DRIVERS = new HashMap<>();
        DRIVERS.put("postgres", "org.postgresql.Driver");
        DRIVERS.put("h2", "org.h2.Driver");
        DRIVERS.put("mariadb", "org.mariadb.jdbc.Driver");
        DRIVERS.put("oracle", "oracle.jdbc.driver.OracleDriver");
        DRIVERS.put("mysql", "com.mysql.cj.jdbc.Driver");
        DRIVERS.put("derby", "org.apache.derby.jdbc.ClientDriver");
        DRIVERS.put("microsoft", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
    }
}
