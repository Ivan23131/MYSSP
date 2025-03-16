package ag.selm.mvc_app.client;

import ag.selm.mvc_app.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;


@RequiredArgsConstructor
public class RestClientTicketsRestClient {
    private static final ParameterizedTypeReference<List<Ticket>> PRODUCTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };cwcwec

    private final RestClient restClient;
cwecwecw
    public List<Ticket> findTicketsByEventId(Integer eventId) {
        return this.restClient
                .get()
                .uri("event/{eventId}/ticketsList", eventId)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }
}
