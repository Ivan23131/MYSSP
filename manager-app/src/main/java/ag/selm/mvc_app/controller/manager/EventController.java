package ag.selm.mvc_app.controller.manager;

import ag.selm.mvc_app.client.EventsRestClient;
import ag.selm.mvc_app.client.RestClientTicketsRestClient;
import ag.selm.mvc_app.entity.Event;
import ag.selm.mvc_app.entity.Ticket;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("manager/events/{eventId:\\d+}")
public class EventController {

    private final EventsRestClient eventsRestClient;
    private final RestClientTicketsRestClient ticketsRestClient;

    private final MessageSource messageSource;

    @ModelAttribute("event")
    public Event event(@PathVariable("eventId") int eventId) {
        return this.eventsRestClient.findEvent(eventId)
                .orElseThrow(() -> new NoSuchElementException("catalogue.errors.product.not_found"));
    }

    @ModelAttribute("eventTickets")
    public List<Ticket> ticketList(@PathVariable("eventId") int eventId){
        return this.ticketsRestClient.findTicketsByEventId(eventId);
    }

    @GetMapping
    public String getNewProductPage() {
        return "manager/event/event";
    }

    @GetMapping("edit")
    public String getEventEditPage() {
        return "manager/event/edit";
    }


    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("event") Event event) {
        this.eventsRestClient.deleteEvent(event.id());
        return "redirect:/manager/events/list";
    }


    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model,
                                               HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error",
                this.messageSource.getMessage(exception.getMessage(), new Object[0],
                        exception.getMessage(), locale));
        return "errors/404";
    }
}
