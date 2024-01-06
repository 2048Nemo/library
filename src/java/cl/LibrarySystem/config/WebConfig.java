package cl.LibrarySystem.config;

import cl.LibrarySystem.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        JwtInterceptor jwtInterceptor = jwtInterceptor();
//        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**"); // 拦截所有请求，通过判断是否token决定是否需要登录
//        registry.addInterceptor(jwtInterceptor).excludePathPatterns("/**/export","/**/import");
    }
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
