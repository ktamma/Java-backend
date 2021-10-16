package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;


@Configuration
@PropertySource("classpath:/application.properties")
@ComponentScan(basePackages = {"dao", "config", "servlet"})
public class Config {


    @Bean
    public JdbcTemplate getTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }

}