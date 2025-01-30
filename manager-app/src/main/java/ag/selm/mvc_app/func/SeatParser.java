package ag.selm.mvc_app.func;

import ag.selm.mvc_app.controller.manager.payload.NewTicketPayload;

import java.util.*;

public class SeatParser {

    public static List<NewTicketPayload> parseSeats(Map<String, String> data) {
        List<NewTicketPayload> seats = new ArrayList<>();

        // Проходим по всем ключам в Map
        for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Проверяем, соответствует ли ключ формату "seats[index]"
            if (key.startsWith("seats[")) {
                // Извлекаем индекс из ключа
                int index = Integer.parseInt(key.replace("seats[", "").replace("]", ""));

                // Разделяем значение на числа
                String[] numbers = value.split(",");
                List<Integer> seatNumbers = new ArrayList<>();
                for (String number : numbers) {
                    seatNumbers.add(Integer.parseInt(number));
                }

                // Добавляем числа в список по индексу
                seats.add(new NewTicketPayload(seatNumbers.get(0), seatNumbers.get(1)));
            }
        }

        return seats;
    }

    public static void main(String[] args) {
        // Пример Map с данными
        Map<String, String> data = new HashMap<>();
        data.put("title", "");
        data.put("place", "");
        data.put("dateTime", "2024-12-22T21:00");
        data.put("type", "");
        data.put("seats[0]", "1,1");
        data.put("seats[1]", "1,2");
        data.put("seats[2]", "2,2");
        data.put("seats[3]", "2,3");

    }
}