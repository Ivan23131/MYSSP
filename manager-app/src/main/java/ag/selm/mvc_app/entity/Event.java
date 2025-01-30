package ag.selm.mvc_app.entity;


import java.time.LocalDateTime;

public record Event(int id, String title, String place, String type, LocalDateTime dateTime){

}