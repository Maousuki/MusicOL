package com.rjkf.music.config;

import com.rjkf.music.constant.Constants;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author zhn
 * @version 1.0
 * @description: TODO
 * @date 2022/6/13 20:32
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * @description 跨域配置
     * @param registry
     * @return void
     * @author zhn
     * @date 2022/6/13 20:32
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 放行全部请求类型
                .allowedMethods("*")
                // .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 是否发送Cookie
                .allowCredentials(true)
                //暴露哪些头部信息
                .exposedHeaders("*");
    }

    /**
     * @description 静态资源地址映射
     * @param registry
     * @return void
     * @author zhn
     * @date 2022/6/13 20:46
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/singerPic/**")
                .addResourceLocations(Constants.SINGER_PIC_PATH);
        registry.addResourceHandler("/img/songPic/**")
                .addResourceLocations(Constants.SONG_PIC_PATH);
        registry.addResourceHandler("/songs/**")
                .addResourceLocations(Constants.SONG_PATH);
        registry.addResourceHandler("/img/songListPic/**")
                .addResourceLocations(Constants.SONG_LIST_PIC_PATH);
        registry.addResourceHandler("/img/avatarImages/**")
                .addResourceLocations(Constants.AVATAR_IMAGES_PATH);
    }

    /**
     * 乱码处理
     */
    public HttpMessageConverter<String> responseBodyConverter() {
        final StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converter.setWriteAcceptCharset(false);
        return converter;
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        if (converters.size() > 0) {
            converters.add(converters.get(0));
            converters.set(0, responseBodyConverter());
        } else {
            converters.add(responseBodyConverter());
        }
    }

}
