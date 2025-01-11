//package com.github.sats17.configuration;
//
//import java.net.http.HttpClient;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class EndpointConfig {
//
//	@Autowired
//	ConfigProperties configProperties;
//
//	@Bean
//	DownstreamEndpoint forecastHttpConfig() {
//		return new DownstreamEndpoint(configProperties.getForecast(), HttpClient.newHttpClient());
//	}
//	
//	@Bean
//	DownstreamEndpoint airQualityHttpConfig() {
//		return new DownstreamEndpoint(configProperties.getAirQuality(), HttpClient.newHttpClient());
//	}
//
//}