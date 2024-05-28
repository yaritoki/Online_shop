package org.jp.config;

import org.jp.client.RestClientProductImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientProductImpl productRestClient(
            @Value("${applicationSh.service.catalogue.uri:}") String host,
            @Value("${applicationSh.service.catalogue.username:}") String RestServiceUsername,
            @Value("${applicationSh.service.catalogue.password:}" ) String RestServicePassword)
    {
        return new RestClientProductImpl(RestClient.builder()
                .baseUrl(host)
                .requestInterceptor(
                        new BasicAuthenticationInterceptor(RestServiceUsername,RestServicePassword))
                .build());
    }
}
