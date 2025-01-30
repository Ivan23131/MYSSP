package ag.selm.catalogue.service;

import ag.selm.catalogue.entity.Event;
import ag.selm.catalogue.entity.SelmagUser;
import ag.selm.catalogue.entity.Ticket;
import ag.selm.catalogue.repository.TicketRepository;
import ag.selm.catalogue.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public Optional<Ticket> findTicket(Integer id) {
        return ticketRepository.findById(id);
    }

    public Iterable<Ticket> findTicketsByEvents(Integer eventId){
        return ticketRepository.findTicketsByEventId(eventId);
    }

    @Transactional
    public Ticket createTicket(Event event, int row, int place, int price) {
        Ticket ticket = new Ticket(null, event, "продается", row, place, price, null);
        return ticketRepository.save(ticket);
    }
}
