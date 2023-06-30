package org.example.total;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 季度(4 5 6)总表:
 * totalScore: 三个月总分
 * addTotalScore: 三个月总加分
 * subTotalScore: 三个月总减分
 * <p>
 * aprilScore: 四月总分
 * mayScore: 五月总分
 * juneScore: 六月总分
 */

@Getter
@Setter
@Data
@NoArgsConstructor
public class AirSteward {

    private Title title;
    private Group group;
    private String name;
    private double totalScore;
    private double addTotalScore;
    private double subTotalScore;
    private double aprilScore;
    private double mayScore;
    private double juneScore;

    public AirSteward(Title title, Group group, String name) {
        this.title = title;
        this.group = group;
        this.name = name;
    }

    @Override
    public String toString() {
        return group + "\t" + name + "\t" + totalScore + "\t" + addTotalScore + "\t" + subTotalScore + "\t" + aprilScore + "\t" + mayScore + "\t" + juneScore;
    }
}