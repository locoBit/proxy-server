package com.myproxy.services;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClientService
{
    private final RestTemplate restTemplate;
    
    static final Logger logger = LoggerFactory.getLogger(RestClientService.class);
    
    RestClientService()
    {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(3000);
        
        this.restTemplate = new RestTemplate(requestFactory);
    }
    
    public ResponseEntity<String> getRequest(String url, HttpHeaders headers)
    {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        
        logger.info("Attempting to create GET request to: {}", url);
        
        return this.restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }
    
    private RestTemplate getRestTemplate()
    {
        return this.restTemplate;
    }
}
