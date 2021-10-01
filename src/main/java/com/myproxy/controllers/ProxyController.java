package com.myproxy.controllers;

import com.myproxy.data.UrlRequest;
import com.myproxy.services.RestClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ProxyController
{
    private final RestClientService restClientService;
    private final Logger logger = LoggerFactory.getLogger(ProxyController.class);
    
    @Autowired
    public ProxyController(RestClientService restClientService)
    {
        this.restClientService = restClientService;
    }
    
    @PostMapping(value = "/proxy" )
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> postRequest(@RequestBody UrlRequest request, @RequestHeader Map<String, String> headers)
    {
        HttpHeaders httpHeaders = new HttpHeaders();
        ResponseEntity<String> response = restClientService.getRequest(request.getUrl(), httpHeaders);

        logger.info("Response headers");
        response.getHeaders().forEach((k,v) -> {
            logger.info("{} -> {}", k, v);
        });

        return response;
    }
}
