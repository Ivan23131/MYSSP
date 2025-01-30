package ag.selm.catalogue.service;

import ag.selm.catalogue.entity.Event;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface EventService {

    Iterable<Event> findAllEvent(String filter);

    Event createEvent(String title, String place, String type, LocalDateTime dateTime, Integer organizer_id);

    Optional<Event> findEvent(int eventId);

    void deleteEvent(Integer id);
}
