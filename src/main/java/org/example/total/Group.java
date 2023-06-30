package org.example.total;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Group {
    D("D组"), E("E组"), F("F组");
    private String group;

    @Override
    public String toString() {
        return group;
    }
}
