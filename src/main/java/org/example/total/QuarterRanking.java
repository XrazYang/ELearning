package org.example.total;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class QuarterRanking {

    private static List<AirSteward> allStewards = new ArrayList<>(256);

    public static void parsePersonScore(String path, int month) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = null;
            /**
             * 1. 先读取6月数据： 人员名册（职务，小组，姓名）
             * 2. 6月 ： 总分，加分项总分，减分项总分
             * 3. 5月 ： 总分，加分项总分，减分项总分
             * 3. 4月 ： 总分，加分项总分，减分项总分
             */
            while ((line = br.readLine()) != null) {
                String[] airs = line.split("\t");
                if (airs.length > 3) {
                    switch (month) {
                        case 6 -> {
                            Title title = getTitle(airs[0]);
                            assert title != null;
                            Group group = getGroup(airs[1]);
                            assert group != null;
                            AirSteward air = new AirSteward(title, group, airs[2]);
                            //6月： 总分
                            air.setJuneScore(Double.parseDouble(airs[3]));

                            // 6月： 加分项总分，减分项总分
                            double[] score = calScore(airs, 5);
                            air.setAddTotalScore(score[0]);
                            air.setSubTotalScore(score[1]);
                            allStewards.add(air);
                        }
                        case 5, 4 -> {
                            AirSteward air = getAirStewardByName(allStewards, airs[1]);
                            if (Objects.nonNull(air)) {
                                //4 5月： 总分
                                if (month == 5) {
                                    air.setMayScore(Double.parseDouble(airs[2]));
                                } else {
                                    air.setAprilScore(Double.parseDouble(airs[2]));
                                }

                                //4 5月： 加分项总分，减分项总分
                                double[] score = calScore(airs, 4);
                                air.setAddTotalScore(score[0] + air.getAddTotalScore());
                                air.setSubTotalScore(score[1] + air.getSubTotalScore());
                            }
                        }
                        default -> {
                        }
                    }
                } else {
                    System.out.println(Arrays.toString(airs));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Title getTitle(String title) {
        return switch (title) {
            case "乘务长" -> Title.CHIEF;
            case "公务舱" -> Title.BUSINESS;
            case "普通舱" -> Title.TOURIST;
            default -> null;
        };
    }

    public static Group getGroup(String group) {
        return switch (group) {
            case "D组" -> Group.D;
            case "E组" -> Group.E;
            case "F组" -> Group.F;
            default -> null;
        };
    }

    public static double[] calScore(String[] airs, int start) {
        double[] socres = new double[]{0, 0};
        double addScore = 0;
        double subScore = 0;
        for (int i = start; i < airs.length; i++) {
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
        socres[0] = addScore;
        socres[1] = subScore;
        return socres;
    }


    public static AirSteward getAirStewardByName(List<AirSteward> allStewards, String name) {
        for (AirSteward air : allStewards) {
            if (name.equals(air.getName())) {
                return air;
            }
        }
        System.out.println(name);
        return null;
    }

    public static void formatOutput() {
        parsePersonScore("D:\\dev\\Java\\ELearning\\src\\main\\resources\\积分明细\\6.txt", 6);
        //parsePersonScore("D:\\dev\\Java\\ELearning\\src\\main\\resources\\积分明细\\5.txt", 5);
        parsePersonScore("D:\\dev\\Java\\ELearning\\src\\main\\resources\\积分明细\\4.txt", 4);
        //allStewards.forEach(System.out::println);
    }

    public static void main(String[] args) {
        formatOutput();
    }
}

