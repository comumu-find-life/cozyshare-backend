//package com.core.config;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ElasticsearchConfig {
//
//    @Value("${spring.data.elasticsearch.uris}")
//    private String elasticsearchURL;
//
//    @Bean
//    public RestClient restClient() {
//        return RestClient.builder(
//                new HttpHost(elasticsearchURL, 9200, "http")
//        ).build();
//    }
//}