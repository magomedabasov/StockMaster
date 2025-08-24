package ru.abasov.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.abasov.manager.client.RestClientProductsRestClient;

@Configuration
public class ClientBeans {

    @Bean
    public RestClientProductsRestClient productsRestClient(
            @Value("${stockmaster.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri) {
        return new RestClientProductsRestClient(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .build());
    }
}