
package com.gps.api.config;

import com.gps.api.modules.app.interceptor.AuthorizationInterceptor;
import com.gps.api.modules.app.resolver.LoginUserHandlerMethodArgumentResolver;
import com.gps.api.modules.cm.interceptor.ParameterInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * MVC配置
 *
 * @author Mark sunlightcs@gmail.com
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;
    @Autowired
    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;
    @Autowired
    private ParameterInterceptor parameterInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/app/**");
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/credit/**")
                .addPathPatterns("/wechat/**");
        registry.addInterceptor(parameterInterceptor).addPathPatterns("/sim3/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserHandlerMethodArgumentResolver);
    }
}