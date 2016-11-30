package kr.co.udid.payapp.lt.server.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Thomas on 2016. 11. 29..
 */
@Configuration
public class WebConfiguration
{
	@Bean
	public ServletRegistrationBean h2ServletRegistation ()
	{
		ServletRegistrationBean registration = new ServletRegistrationBean (new WebServlet ());

		registration.addUrlMappings ("/console/*");

		return registration;
	}
}
