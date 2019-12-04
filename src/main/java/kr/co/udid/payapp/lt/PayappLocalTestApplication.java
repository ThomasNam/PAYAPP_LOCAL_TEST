package kr.co.udid.payapp.lt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;

/**
 * Created by Thomas on 2016. 11. 29..
 */
@SpringBootApplication
// @EntityScan (basePackageClasses = {PayappLocalTestApplication.class})
public class PayappLocalTestApplication extends SpringBootServletInitializer
{
	public static void main (String[] args)
	{
		SpringApplication.run (PayappLocalTestApplication.class, args);
	}

	@Bean
	public HttpMessageConverter<String> responseBodyConverter ()
	{
		return new StringHttpMessageConverter (StandardCharsets.UTF_8);
	}

	@Bean
	public Filter characterEncodingFilter ()
	{
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter ();
		characterEncodingFilter.setEncoding ("UTF-8");
		characterEncodingFilter.setForceEncoding (true);
		return characterEncodingFilter;
	}

	@Bean
	public FilterRegistrationBean filterRegistrationBean ()
	{
		FilterRegistrationBean registrationBean = new FilterRegistrationBean ();

		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter ();
		registrationBean.setFilter (characterEncodingFilter);
		characterEncodingFilter.setEncoding ("UTF-8");
		characterEncodingFilter.setForceEncoding (true);
		registrationBean.setOrder (Integer.MIN_VALUE);
		registrationBean.addUrlPatterns ("/*");
		return registrationBean;
	}

	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

	@Override
	protected SpringApplicationBuilder configure (SpringApplicationBuilder builder)
	{
		return builder.sources (PayappLocalTestApplication.class);
	}
}
