package ag.selm.mvc_app.controller.manager;

import ag.selm.mvc_app.client.BadRequestException;
import ag.selm.mvc_app.client.EventsRestClient;
import ag.selm.mvc_app.controller.manager.payload.FindEventPayload;
import ag.selm.mvc_app.entity.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import ag.selm.mvc_app.func.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("manager/events")
public class EventsController {

    private final EventsRestClient eventsRestClient;

    @GetMapping("list")
    public String getEventsList(Model model, FindEventPayload eventPayload, Principal principal) {
        String username = principal.getName();
        System.out.println("Current user: " + username);

        // Получение ролей пользователя
        SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .forEach(authority -> {
                    System.out.println("Role: " + authority.getAuthority());
                });
        model.addAttribute("events", this.eventsRestClient.findAllEvents(eventPayload.title()));
        return "manager/event/list";
    }

    @GetMapping("create")
    public String getNewProductPage() {
        return "manager/event/new_event";
    }

    @PostMapping("create")
    public void createProduct(@RequestParam Map<String, String> params,
                              HttpServletResponse response, @AuthenticationPrincipal User user) throws IOException {
        LocalDateTime dateTime = null;
        if (params.get("title").length() == 0){
            params.replace("title","",null);
        }
        if (params.get("place").length() == 0){
            params.replace("place","",null);
        }
        if (params.get("dateTime").length() == 0){
            params.replace("dateTime","",null);
        }else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            dateTime = LocalDateTime.parse(params.get("dateTime"), formatter);
        }
        if (params.get("type").length() == 0){
            params.replace("type","",null);
        }
        Integer price;
        try {
            price = Integer.parseInt(params.get("price"));
        }catch (Exception e){
            price = null;
        }
        try {
            Event event = this.eventsRestClient.createEvent(params.get("title"), params.get("place"), params.get("type"), dateTime, SeatParser.parseSeats(params),price, user);
            // Возвращаем перенаправление на новую страницу
            response.setStatus(HttpStatus.FOUND.value()); // Код 302
            response.setHeader("Location", "/manager/events/%d".formatted(event.id()));
        } catch (BadRequestException exception) {
            response.setStatus(HttpStatus.BAD_REQUEST.value()); // Код 400
            // Записываем ошибки в ответ в формате JSON
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Map.of("errors", exception.getErrors())));
        }
    }
}
