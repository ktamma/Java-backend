package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {MvcConfig.class};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
                SecurityConfig.class,
                DbConfig.class };
    }


    @Override
    protected String[] getServletMappings() {
        return new String[] {"/api/*"};
    }
}
