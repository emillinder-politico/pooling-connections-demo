package com.example.resttemplatecpdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

//    @Autowired
//    CloseableHttpClient httpClient;
//
//    @Bean
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
//        return restTemplate;
//    }
//
//    @Bean
//    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
//        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
////        clientHttpRequestFactory.setHttpClient(httpClient);
//        return clientHttpRequestFactory;
//    }

}
