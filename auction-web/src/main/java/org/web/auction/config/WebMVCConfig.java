package org.web.auction.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private FileProperties fileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println(fileProperties.getStaticAccessPath());
        System.out.println(fileProperties.getUploadFileFolder());
        //registry.addResourceHandler("/upload/**").addResourceLocations("file:d:/file/");
        registry.addResourceHandler(fileProperties.getStaticAccessPath()).addResourceLocations("file:"+fileProperties.getUploadFileFolder()+"/");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        List<String> paths = new ArrayList<>();
//        paths.add("/doLogin");
//        paths.add("/login");
//        paths.add("/defaultKaptcha");
//        paths.add("/css/**");
//        paths.add("/images/**");
//        paths.add("/js/**");
//        registry.addInterceptor(new CheckUserInterceptor()).addPathPatterns("/**").excludePathPatterns(paths);
//    }

}
