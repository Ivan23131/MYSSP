package ag.selm.catalogue.repository;

import ag.selm.catalogue.entity.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {

    Iterable<Ticket> findTicketsByEventId(Integer eventId);

}
