package org.example.matters;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class OffsetEvents {
    @Setter
    @Getter
    private int score;

    @Getter
    @Setter
    private int count;

    private List<String> events;

    public OffsetEvents() {
        events = new ArrayList<>(64);
    }

    public String getEvents() {
        StringBuffer buffer = new StringBuffer();
        events.forEach(event -> {
            buffer.append(event + "\n");
        });
        return buffer.toString();
    }

    public void addEvent(String event, int score) {
        //抵消明细：更新合计 和 添加新的抵消事项
        events.set(0, "合计： " + (this.score + score));
        events.add(++count + "、" + event);
    }
}
