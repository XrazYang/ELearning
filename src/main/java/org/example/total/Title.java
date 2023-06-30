package org.example.total;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Title {
    CHIEF("乘务长"), BUSINESS("公务舱"), TOURIST("普通舱");
    private String title;

    @Override
    public String toString() {
        return title;
    }
}
