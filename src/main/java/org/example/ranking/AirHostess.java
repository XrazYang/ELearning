package org.example.ranking;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class AirHostess {
    private String name;
    private int keyScore;
    private int normalScore;

    public AirHostess(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirHostess that = (AirHostess) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("%s --- %s --- %s", name, keyScore, normalScore);
    }
}
