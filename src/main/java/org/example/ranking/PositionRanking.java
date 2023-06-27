package org.example.ranking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PositionRanking {

    private static Steward[] positionRanking(String path) {
        List<Steward> stewards = new ArrayList<>(256);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] ranking = line.trim().split("\t");
                if (ranking.length == 3) {
                    stewards.add(new Steward(ranking[0].trim(), ranking[1].trim(), Double.parseDouble(ranking[2])));
                } else {
                    System.out.println("error - " + line);
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("人员名单读取错误...");
        }
        Steward[] airs = stewards.toArray(new Steward[0]);
        Arrays.sort(airs, Collections.reverseOrder(Comparator.comparingDouble(Steward::getScore)));
        return airs;
    }

    public static void main(String[] args) {
        Steward[] stewards = positionRanking("D:\\dev\\Java\\ELearning\\src\\main\\resources\\岗位排名\\stewards.txt");
        System.out.println(stewards.length);
        Arrays.stream(stewards).forEach(System.out::println);
    }
}
