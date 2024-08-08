package ru.rsc.clicker_kombat.config;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EntityScan(basePackages = {"ru.rsc.clicker_kombat.model.domain"})
@EnableJpaRepositories(basePackages = {"ru.rsc.clicker_kombat.repository"})
@EnableScheduling
public class JpaConfig {

    @Bean(initMethod = "migrate")
    Flyway flyway(HikariDataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:migration")
                .baselineOnMigrate(false)
                .table("schema_version")
                .load();
    }
}
