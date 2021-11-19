package config;

import config.security.ApiAuthenticationFilter;
import config.security.handlers.ApiAccessDeniedHandler;
import config.security.handlers.ApiEntryPoint;
import config.security.handlers.ApiLogoutSuccessHandler;
import config.security.jwt.JwtAuthenticationFilter;
import config.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:/application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.signing.key}")
    private String jwtKey;

    private DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //annab tokeni puudumisel vea kui on sissel[litatud
        http.authorizeRequests()
                .antMatchers("/api/login", "/api/version").permitAll()
                .antMatchers("/api/**").authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(new ApiEntryPoint());

        http.exceptionHandling()
                .accessDeniedHandler(new ApiAccessDeniedHandler());

        http.logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(new ApiLogoutSuccessHandler());

        var apiLoginFilter = new JwtAuthenticationFilter(
                authenticationManager(), "/api/login", jwtKey);


        http.addFilterAfter(apiLoginFilter, LogoutFilter.class);

        var jwtAuthFilter = new JwtAuthorizationFilter(authenticationManager(), jwtKey);
        http.addFilterBefore(jwtAuthFilter, LogoutFilter.class);

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder());
    }


}