package org.example.ranking;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
class EAir {
    private String name;
    private double score;

    public EAir(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "\t" + score;
    }
}

public class EStudy {
    private static List<EAir> readNames(String path) {
        List<EAir> names = new ArrayList<>(128);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                names.add(new EAir(line.trim()));
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("人员名单读取错误...");
        }
        return names;
    }

    private static List<EAir> workMeeting(String namesPath, String letersPath) {
        List<EAir> names = readNames(namesPath);
        try (BufferedReader br = new BufferedReader(new FileReader(letersPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 3) {
                    String[] tmp = line.trim().split("：");
                    if ("e".equals(tmp[0])) {
                        for (String air : tmp[1].trim().split("、")) {
                            EAir e = getEAirByName(names, air.trim());
                            if (Objects.nonNull(e)) {
                                double score = e.getScore() - 2.5;
                                e.setScore(score);
                            } else {
                                System.out.println("e : " + air);
                                continue;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return names;
    }

    private static EAir getEAirByName(List<EAir> names, String name) {
        for (EAir air : names) {
            if (name.equals(air.getName())) {
                return air;
            }
        }
        return null;
    }

    private static void writeNames(String namePath, String letersPath) {
        List<EAir> airMeetings = workMeeting(namePath, letersPath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./name.txt"))) {
            bw.write("姓名 --- 分数 \n");
            for (EAir air : airMeetings) {
                bw.write(air.toString() + "\n");
            }
        } catch (IOException e) {
            //e.printStackTrace();
            //System.out.println("人员名单读取错误...");
        }
    }

    public static void main(String[] args) {
        writeNames("/Users/xrazyang/Java/ELearning/src/main/resources/data/names.txt", "/Users/xrazyang/Java/ELearning/src/main/resources/data/e学院.txt");
    }

}
