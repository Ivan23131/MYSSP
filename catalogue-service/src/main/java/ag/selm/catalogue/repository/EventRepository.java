package ag.selm.catalogue.repository;

import ag.selm.catalogue.entity.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer> {

    Iterable<Event> findAllByTitleLikeIgnoreCase(String filter);
}
