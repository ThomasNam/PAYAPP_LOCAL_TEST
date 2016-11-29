package kr.co.udid.payapp.lt.model.payapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Created by Thomas on 2016. 11. 29..
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties (prefix = "payapp")
@Data
public class PayappProperty
{
	private String url;

	private String name;
}
