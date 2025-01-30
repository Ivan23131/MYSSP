package ag.selm.mvc_app.config;

import ag.selm.mvc_app.client.RestClientEventsRestClient;
import ag.selm.mvc_app.client.RestClientTicketsRestClient;
import ag.selm.mvc_app.repositoty.SelmagUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;


@AllArgsConstructor
@Configuration
public class ClientBeans {

    private final SelmagUserRepository selmagUserRepository;

    @Bean
    public RestClientEventsRestClient eventsRestClient(
            @Value("${selmag.service.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
            @Value("event_service_user") String catalogueUserName,
            @Value("password") String cataloguePassword,
            ObjectMapper objectMapper) {
        RestClient restClient = RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .requestInterceptor(new BasicAuthenticationInterceptor(catalogueUserName, cataloguePassword))
                .build();
        return new RestClientEventsRestClient(restClient, selmagUserRepository,objectMapper);
    }
    @Bean
    public RestClientTicketsRestClient ticketsRestClient(
            @Value("${selmag.service.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
            @Value("event_service_user") String catalogueUserName,
            @Value("password") String cataloguePassword,
            ObjectMapper objectMapper) {
        RestClient restClient = RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .requestInterceptor(new BasicAuthenticationInterceptor(catalogueUserName, cataloguePassword))
                .build();
        return new RestClientTicketsRestClient(restClient);
    }
}
