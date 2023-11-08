package org.example.assignment;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class AssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    void initTable() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS data (
                    source VARCHAR(255),
                    codeListCode VARCHAR(255),
                    code VARCHAR(255) PRIMARY KEY,
                    displayValue VARCHAR(255),
                    longDescription CHARACTER VARYING,
                    fromDate DATE,
                    toDate DATE,
                    sortingPriority INTEGER
                );
                """);
    }

}
