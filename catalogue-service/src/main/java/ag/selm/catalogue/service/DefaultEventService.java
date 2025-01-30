package ag.selm.catalogue.service;

import ag.selm.catalogue.entity.Event;
import ag.selm.catalogue.entity.SelmagUser;
import ag.selm.catalogue.repository.EventRepository;
import ag.selm.catalogue.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultEventService implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public Iterable<Event> findAllEvent(String filter) {
        if (filter != null && !filter.isBlank()) {
            return this.eventRepository.findAllByTitleLikeIgnoreCase("%" + filter + "%");
        } else {
            return this.eventRepository.findAll();
        }
    }

    @Override
    @Transactional
    public Event createEvent(String title, String place, String type, LocalDateTime dateTime, Integer organizer_id) {
        SelmagUser selmagUser = userRepository.findById(organizer_id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + organizer_id));
        return this.eventRepository.save(new Event(null, title, place, type, dateTime, selmagUser));
    }

    @Override
    public Optional<Event> findEvent(int eventId) {
        return this.eventRepository.findById(eventId);
    }

    @Override
    @Transactional
    public void deleteEvent(Integer id) {
        this.eventRepository.deleteById(id);
    }
}
