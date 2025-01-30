package ag.selm.mvc_app.client;

import ag.selm.mvc_app.controller.manager.payload.NewEventPayload;
import ag.selm.mvc_app.controller.manager.payload.NewTicketPayload;
import ag.selm.mvc_app.entity.Event;
import ag.selm.mvc_app.repositoty.SelmagUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class RestClientEventsRestClient implements EventsRestClient {

    private static final ParameterizedTypeReference<List<Event>> PRODUCTS_TYPE_REFERENCE =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;
    private final SelmagUserRepository selmagUserRepository;

    private final ObjectMapper objectMapper;

    @Override
    public List<Event> findAllEvents(String filter) {
        return this.restClient
                .get()
                .uri("managerRest-api/create-event?filter={filter}", filter)
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }

    @Override
    public Event createEvent(String title, String place, String type, LocalDateTime dateTime, List<NewTicketPayload> ticketList, Integer price, User organizer)
    {
        Integer organizer_id = selmagUserRepository.findByUsername(organizer.getUsername()).orElseThrow(() -> new RuntimeException("User not found with id: ")).getId().intValue();
        NewEventPayload payload = new NewEventPayload(title, place, type, dateTime, ticketList, price, organizer_id);
        try {
            String jsonPayload = objectMapper.writeValueAsString(payload);
            System.out.println(payload);
            Event event =  this.restClient
                    .post()
                    .uri("managerRest-api/create-event")
                    .contentType(MediaType.APPLICATION_JSON)// по идеи можно просто вместо jsonPayload
                    // прописать new NewEventPayload(title, place, type, dateTime);
                    //хз почему я так сделал :)
                    .body(jsonPayload)
                    .retrieve()
                    .body(Event.class);
            return event;
        } catch (HttpClientErrorException.BadRequest exception) {
            ProblemDetail problemDetail = exception.getResponseBodyAs(ProblemDetail.class);
            throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public Optional<Event> findEvent(int eventId) {
        try {
            return Optional.ofNullable(this.restClient.get()
                    .uri("/managerRest-api/create-event/{eventId}", eventId)
                    .retrieve()
                    .body(Event.class));
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        }
    }


    @Override
    public void deleteEvent(int eventId) {
        try {
            this.restClient
                    .delete()
                    .uri("/managerRest-api/create-event/{eventId}", eventId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new NoSuchElementException(exception);
        }
    }
}
