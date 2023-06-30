package org.example.total;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuarterRanking {
    public static void parsePersonScore(String path) {
        List<AirSteward> allStewards = new ArrayList<>(256);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] airs = line.split("\t");
                //System.out.println(Arrays.toString(airs));
                if (airs.length > 3) {
                    AirSteward air = new AirSteward(Title.CHIEF, Group.D, airs[1]);
                    air.setJuneScore(Double.parseDouble(airs[2]));
                    double addScore = 0;
                    double subScore = 0;
                    for (int i = 4; i < airs.length; i++) {
                        String tmp = airs[i];
                        if (StringUtils.isNotEmpty(tmp)) {
                            double score = Double.parseDouble(tmp);
                            if (Double.compare(score, 0) <= 0) {
                                subScore += score;
                            } else {
                                addScore += score;
                            }
                        }
                    }
                    air.setAddTotalScore(addScore);
                    air.setSubTotalScore(subScore);
                    allStewards.add(air);
                } else {
                    System.out.println(Arrays.toString(airs));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        allStewards.forEach(air->{
            System.out.println(air);
        });
    }


    public static void main(String[] args) {
        QuarterRanking.parsePersonScore("D:\\dev\\Java\\ELearning\\src\\main\\resources\\积分明细\\6.txt");
    }
}

