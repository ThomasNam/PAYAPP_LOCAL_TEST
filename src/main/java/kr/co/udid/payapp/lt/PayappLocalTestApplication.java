package kr.co.udid.payapp.lt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

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

	/*@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

	@Override
	protected SpringApplicationBuilder configure (SpringApplicationBuilder builder)
	{
		return builder.sources (PayappLocalTestApplication.class);
	}*/
}
