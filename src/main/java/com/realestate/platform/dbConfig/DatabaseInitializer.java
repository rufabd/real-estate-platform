package com.realestate.platform.dbConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DatabaseInitializer {
    public static void initialize(String db_name) {
        String url = "jdbc:postgresql://localhost:5432/";
        String username = "postgres";
        String password = "Murad.123";

        DataSource dataSource = new DriverManagerDataSource(url, username, password);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        try {
            jdbcTemplate.execute("CREATE DATABASE " + db_name);
        } catch (Exception e) {
            log.warn("Database already exists!");
        }
    }
}
