package ag.selm.mvc_app.controller.client;


import ag.selm.mvc_app.client.EventsRestClient;
import ag.selm.mvc_app.client.RestClientTicketsRestClient;
import ag.selm.mvc_app.controller.manager.payload.FindEventPayload;
import ag.selm.mvc_app.entity.Event;
import ag.selm.mvc_app.entity.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.NoSuchElementException;


@AllArgsConstructor
@RequestMapping("client")
@Controller
public class ClientController {

    private final EventsRestClient eventsRestClient;
    private final RestClientTicketsRestClient ticketsRestClient;

    @PreAuthorize("hasAnyAuthority('client')")
    @GetMapping("events/list")
    public String getEventsList(Model model, FindEventPayload eventPayload) {
        model.addAttribute("events", this.eventsRestClient.findAllEvents(eventPayload.title()));
        return "client/event/list_events";
    }

    @PreAuthorize("hasAnyAuthority('client')")
    @GetMapping("events/{eventId:\\d+}")
    public String getNewOrderPage(Model model, @PathVariable("eventId") int eventId) {
        List<Ticket> ticketList = this.ticketsRestClient.findTicketsByEventId(eventId);
        Event event = this.eventsRestClient.findEvent(eventId)
                .orElseThrow(() -> new NoSuchElementException("catalogue.errors.product.not_found"));
        model.addAttribute("event", event);
        model.addAttribute("eventId", eventId);
        model.addAttribute("eventTickets", ticketList);
        return "client/event/event";
    }

}