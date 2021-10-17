package config;


import org.springframework.context.annotation.*;

import org.springframework.jdbc.core.JdbcTemplate;


import javax.sql.DataSource;


@Configuration
@PropertySource("classpath:/application.properties")
@ComponentScan(basePackages = {"dao", "config", "servlet", "framework"})
public class Config {


    @Bean
    public JdbcTemplate getTemplate(DataSource ds) {
        return new JdbcTemplate(ds);
    }



}