package config

import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
@EnableWebMvc
@PropertySource("classpath:/application.properties")
@ComponentScan(basePackages = ["controller", "dao", "config"])
open class MvcConfig {
    @Bean
    open fun dataSource(env: Environment): DataSource {
        val ds = DriverManagerDataSource()
        ds.setDriverClassName("org.hsqldb.jdbcDriver")
        ds.url = env.getProperty("hsql.url")
        val populator = ResourceDatabasePopulator(
                ClassPathResource("schema.sql"),
                ClassPathResource("data.sql"))
        DatabasePopulatorUtils.execute(populator, ds)
        return ds
    }

    @Bean
    open fun getTemplate(ds: DataSource?): JdbcTemplate {
        return JdbcTemplate(ds)
    }
}