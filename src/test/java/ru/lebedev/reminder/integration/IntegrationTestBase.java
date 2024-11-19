package ru.lebedev.reminder.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import ru.lebedev.reminder.annotation.IT;

@IT
@Sql(scripts = "classpath:sql/data.sql")
@Slf4j
public abstract class IntegrationTestBase {

    private static final PostgreSQLContainer<?> postgis = new PostgreSQLContainer<>(
            DockerImageName.parse("postgis/postgis:16-3.4-alpine")
                           .asCompatibleSubstituteFor("postgres")
    );

    @BeforeAll
    static void startContainer() {
        postgis.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgis::getJdbcUrl);
        log.info(postgis.getJdbcUrl());
        log.info(postgis.getUsername());
        log.info(postgis.getPassword());
    }

}
