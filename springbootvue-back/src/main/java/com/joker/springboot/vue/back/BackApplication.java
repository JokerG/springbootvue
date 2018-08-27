package com.joker.springboot.vue.back;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.joker.springboot.vue.back.filter.RewriteFilter;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan(basePackages = "com.joker.springboot.vue.back.mapper")
public class BackApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}
	/**
	 * json转换使用fastjson
	 *
	 * @return
	 */
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		// 1. 需要定义一个converter转换消息的对象
		FastJsonHttpMessageConverter fasHttpMessageConverter = new FastJsonHttpMessageConverter();

		// 2. 添加fastjson的配置信息，比如:是否需要格式化返回的json的数据
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

		// 3. 在converter中添加配置信息
		fasHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
		List<MediaType> mediaTypeList = new ArrayList<MediaType>();
		mediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
		fasHttpMessageConverter.setSupportedMediaTypes(mediaTypeList);
		HttpMessageConverter<?> converter = fasHttpMessageConverter;
		return new HttpMessageConverters(converter);
	}

	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/errors/401.html");
				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/errors/404.html");
				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/errors/500.html");
				configurableEmbeddedServletContainer.addErrorPages(error401Page, error404Page, error500Page);
			}
		};
	}

	@Bean
	public FilterRegistrationBean testFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new RewriteFilter());// 注册rewrite过滤器
		registration.addUrlPatterns("/*");
		registration.addInitParameter(RewriteFilter.REWRITE_TO, "/index.html");
		registration.addInitParameter(RewriteFilter.REWRITE_PATTERNS, "/ui/*");
		registration.setName("rewriteFilter");
		registration.setOrder(1);
		return registration;
	}
}
