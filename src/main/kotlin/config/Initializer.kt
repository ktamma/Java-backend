package config

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class Initializer : AbstractAnnotationConfigDispatcherServletInitializer() {
    override fun getRootConfigClasses(): Array<Class<*>> {
        return arrayOf()
    }

    override fun getServletConfigClasses(): Array<Class<*>> {
        return arrayOf(MvcConfig::class.java)
    }

    override fun getServletMappings(): Array<String> {
        return arrayOf("/api/*")
    }
}