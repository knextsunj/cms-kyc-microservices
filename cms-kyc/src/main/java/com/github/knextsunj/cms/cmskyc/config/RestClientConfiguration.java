package com.github.knextsunj.cms.cmskyc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Import(JsonMappingConfiguration.class)
public class RestClientConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public RestTemplate restTemplate() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        messageConverters.add(mappingJackson2HttpMessageConverter);
        RestTemplate restTemplate = new RestTemplate(messageConverters);
        return restTemplate;
     }
}
