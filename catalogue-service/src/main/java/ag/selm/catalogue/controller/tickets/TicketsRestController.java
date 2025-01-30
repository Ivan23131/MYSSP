package ag.selm.catalogue.controller.tickets;


import ag.selm.catalogue.entity.Event;
import ag.selm.catalogue.entity.Ticket;
import ag.selm.catalogue.service.TicketServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("event/{eventId:\\d+}/ticketsList")
public class TicketsRestController {

    private final TicketServiceImpl ticketService;

    @ModelAttribute("ticketsList")
    public Iterable<Ticket> getTicketsList(@PathVariable("eventId") int eventId) {
        return this.ticketService.findTicketsByEvents(eventId);
    }

    @GetMapping
    public Iterable<Ticket> findTicketsList(@ModelAttribute("ticketsList") Iterable<Ticket> tickets) {
        return tickets;
    }


}
