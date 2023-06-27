package org.example.points;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Steward {
    private String group;
    private String name;
    private double score;

    @Override
    public String toString() {
        return group + "\t" + name + "\t" + score;
    }
}
