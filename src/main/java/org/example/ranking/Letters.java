package org.example.ranking;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 1. 从names.txt 文件中读取名单
 * 2. 从letters.txt 中读取表扬数量计算分数
 */

public class Letters {

    private static List<AirHostess> readNames(String path) {
        List<AirHostess> names = new ArrayList<>(128);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                names.add(new AirHostess(line.trim()));
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("人员名单读取错误...");
        }
        return names;
    }

    private static List<AirHostess> letters(String namesPath, String letersPath) {
        List<AirHostess> names = readNames(namesPath);
        try (BufferedReader br = new BufferedReader(new FileReader(letersPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 3) {
                    String[] tmp = line.trim().split(":");
                    if ("key".equals(tmp[0])) {
                        for (String air : tmp[1].trim().split("、")) {
                            String[] nameScore = splitStr(air);
                            AirHostess airHostess = getAirHostessByName(names, nameScore[0]);
                            int score = Integer.parseInt(nameScore[1]) * 5;
                            if (Objects.isNull(airHostess)) {
                                System.out.println("key : " + Arrays.toString(nameScore));
                                continue;
                            }

                            airHostess.setKeyScore(score + airHostess.getKeyScore());
                        }
                    }
                    if ("over".equals(tmp[0])) {
                        for (String air : tmp[1].trim().split("、")) {
                            String[] nameScore = splitStr(air);
                            AirHostess airHostess = getAirHostessByName(names, nameScore[0]);
                            int score = Integer.parseInt(nameScore[1]) * 2;
                            if (Objects.isNull(airHostess)) {
                                System.out.println("over : " + Arrays.toString(nameScore));
                                continue;
                            }
                            airHostess.setNormalScore(score + airHostess.getNormalScore());
                        }
                    }
                    if ("under".equals(tmp[0])) {
                        for (String air : tmp[1].trim().split("、")) {
                            String[] nameScore = splitStr(air);
                            AirHostess airHostess = getAirHostessByName(names, nameScore[0]);
                            int score = Integer.parseInt(nameScore[1]);
                            if (Objects.isNull(airHostess)) {
                                System.out.println("under : " + Arrays.toString(nameScore));
                                continue;
                            }
                            airHostess.setNormalScore(score + airHostess.getNormalScore());
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    private static String[] splitStr(String val) {
        String[] nameScore = new String[2];
        StringBuilder name = new StringBuilder();
        StringBuilder socre = new StringBuilder();
        for (int i = 0; i < val.length(); i++) {
            char c = val.charAt(i);
            if (String.valueOf(c).matches("[0-9]")) {
                socre.append(c);
            } else {
                name.append(c);
            }
        }
        nameScore[0] = name.toString().trim();
        nameScore[1] = socre.toString().trim();
        return nameScore;
    }

    private static AirHostess getAirHostessByName(List<AirHostess> names, String name) {
        for (AirHostess air : names) {
            if (name.equals(air.getName())) {
                return air;
            }
        }
        return null;
    }

    private static void writeNames(String namePath, String letersPath) {
        List<AirHostess> airs = letters(namePath, letersPath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./name.txt"))) {
            bw.write("姓名 --- 重点航线 --- 普通航线\n");
            for (AirHostess air : airs) {
                bw.write(air.toString() + "\n");
            }
        } catch (IOException e) {
            //e.printStackTrace();
            //System.out.println("人员名单读取错误...");
        }
    }

    public static void main(String[] args) {
        //readNames("D:\\dev\\Java\\ELearning\\src\\main\\resources\\data\\names.txt");
        //letters("D:\\dev\\Java\\ELearning\\src\\main\\resources\\data\\names.txt", "D:\\dev\\Java\\ELearning\\src\\main\\resources\\data\\letters.txt");

        writeNames("D:\\dev\\Java\\ELearning\\src\\main\\resources\\data\\names.txt", "D:\\dev\\Java\\ELearning\\src\\main\\resources\\data\\letters.txt");
    }

}
