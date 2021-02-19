package com.ratestart.integrator.init

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        [AppConfig]
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        [WebAppConfig]
    }

    @Override
    protected String[] getServletMappings() {
        ["/"]
    }
}
