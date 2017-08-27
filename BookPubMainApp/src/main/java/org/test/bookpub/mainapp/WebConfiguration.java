package org.test.bookpub.mainapp;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.test.bookpub.mainapp.formatters.BookFormatter;
import org.test.bookpub.mainapp.repository.BookRepository;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private BookRepository bookRepository;

	/**
	 * * A filter is an object that performs filtering tasks on either the request
	 * to a resource (a servlet or static content), or on the response from a
	 * resource, or both.
	 * 
	 * @return
	 */
//	@Bean
//	public RemoteIpFilter remoteIpFilter() {
//		return new RemoteIpFilter();
//	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		return new LocaleChangeInterceptor();
	}

	@Bean
	public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
		return new ByteArrayHttpMessageConverter();
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new ByteArrayHttpMessageConverter());
	}

	// @Override
	// public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
	// {
	// converters.clear();
	// converters.add(new ByteArrayHttpMessageConverter());
	// }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new BookFormatter(bookRepository));
	}

	/**
	 * used to enable . notation in route parameters gives us an ability to set our
	 * own behavior in how we want Spring to match the request URL path to the
	 * controller parameters:
	 * 
	 */
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(false).setUseTrailingSlashMatch(true);
	}
	
	/**
	 * Exposes the contents of the resources folder to the outside world
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/internal/**").addResourceLocations("classpath:/");
	}

	@Bean
	public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
		return new EmbeddedServletContainerCustomizer() {
			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				container.setSessionTimeout(1, TimeUnit.MINUTES);
			}
		};
	}


}
