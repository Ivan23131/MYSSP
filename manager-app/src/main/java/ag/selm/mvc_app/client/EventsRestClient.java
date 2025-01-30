package ag.selm.mvc_app.client;

import ag.selm.mvc_app.controller.manager.payload.NewTicketPayload;
import ag.selm.mvc_app.entity.Event;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventsRestClient {

    List<Event> findAllEvents(String filter);

    Event createEvent(String title, String place, String type, LocalDateTime dateTime,  List<NewTicketPayload> ticketList, Integer price, User organizer);

    Optional<Event> findEvent(int eventId);

    void deleteEvent(int eventId);
}
