package org.example.matters;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class TodoEvents {
    @Setter
    @Getter
    private int count;

    private List<String> events;

    public TodoEvents() {
        events = new ArrayList<>(64);
    }

    public String getEvents() {
        StringBuffer buffer = new StringBuffer();
        events.forEach(event -> {
            buffer.append(event + "\n");
        });
        return buffer.toString();
    }

    public void addEvent(String event) {
        count++;
        events.add(count + "、" + event);
    }
}
