package ag.selm.catalogue.controller.events.payload;

import ag.selm.catalogue.entity.Ticket;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record NewEventPayload(
        @NotNull(message = "{catalogue.events.create.errors.title_is_null}")
        @Size(min = 3, max = 50, message = "{catalogue.events.create.errors.title_size_is_invalid}")
        String title,

        @NotNull(message = "{catalogue.events.create.errors.place_is_null}")
        @Size(max = 1000, message = "{catalogue.events.create.errors.place_size_is_invalid}")
        String place,

        @NotNull(message = "{catalogue.events.create.errors.type_is_null}")
        @Size(max = 100, message = "{catalogue.events.create.errors.type_size_is_invalid}")
        String type,

        @NotNull(message = "{catalogue.events.create.errors.date_is_null}")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime dateTime,

        @NotNull(message = "{catalogue.events.create.errors.ticket_price_is_null}")
        Integer price,

        @NotNull(message = "{catalogue.events.create.errors.ticket_price_is_null}")
        Integer organizer_id,

        @Size(min = 1, message = "{catalogue.events.create.errors.no_tickets}")
        @NotNull(message = "{catalogue.events.create.errors.no_tickets}")
        List<Ticket> ticketList
        ) {
}