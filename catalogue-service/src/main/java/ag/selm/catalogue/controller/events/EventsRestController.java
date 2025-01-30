package ag.selm.catalogue.controller.events;

import ag.selm.catalogue.controller.events.payload.NewEventPayload;
import ag.selm.catalogue.entity.Event;
import ag.selm.catalogue.entity.Ticket;
import ag.selm.catalogue.service.EventService;
import ag.selm.catalogue.service.TicketServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("managerRest-api/create-event")
public class EventsRestController {

    private final EventService eventService;
    private final TicketServiceImpl ticketService;

    @GetMapping
    public Iterable<Event> findEvents(@RequestParam(name = "filter", required = false) String filter) {
        return this.eventService.findAllEvent(filter);
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@Valid @RequestBody NewEventPayload payload,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder)
            throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Event product = this.eventService.createEvent(payload.title(), payload.place(), payload.type(), payload.dateTime(), payload.organizer_id());
            for(Ticket ticket: payload.ticketList()){ // по хорошему нужно создать TicketPayload
                                                      // потому что json пытается запарсить весь Ticket
                ticketService.createTicket(product, ticket.getRow(), ticket.getPlace(), payload.price());
            }
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/manager/create-event/{eventId}")
                            .build(Map.of("eventId", product.getId())))
                    .body(product);
        }
    }
}
