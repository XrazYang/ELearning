package org.example.ranking;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
@Data
class Helper {
    private String name;

    private int help;

    private double paper;

    public Helper(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "\t" + help;
    }
}

public class HelpPaper {
    private static List<Helper> readNames(String path) {
        List<Helper> names = new ArrayList<>(128);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                names.add(new Helper(line.trim()));
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("人员名单读取错误...");
        }
        return names;
    }

    private static List<Helper> workMeeting(String namesPath, String letersPath) {
        List<Helper> names = readNames(namesPath);
        try (BufferedReader br = new BufferedReader(new FileReader(letersPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 3) {
                    String[] tmp = line.trim().split("：");
                    if ("help".equals(tmp[0])) {
                        for (String air : tmp[1].trim().split("、")) {
                            String[] helpScore = splitStr(air);
                            Helper helper = getHelperByName(names, helpScore[0]);
                            if (Objects.isNull(helper)) {
                                System.out.println("help : " + Arrays.toString(helpScore));
                                continue;
                            }
                            //System.out.println(Arrays.toString(helpScore));
                            int help = helper.getHelp() + Integer.parseInt(helpScore[1]);
                            helper.setHelp(help);
                        }
                    }
                    if ("paper".equals(tmp[0])) {
                        for (String air : tmp[1].trim().split("、")) {
                            String[] paperScore = splitStr(air);
                            Helper helper = getHelperByName(names, paperScore[0]);
                            if (Objects.isNull(helper)) {
                                System.out.println("paper : " + Arrays.toString(paperScore));
                                continue;
                            }
                            //System.out.println(Arrays.toString(nameReply));
                            double paper = helper.getPaper() + Double.parseDouble(paperScore[1]);
                            helper.setPaper(paper);
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

    private static Helper getHelperByName(List<Helper> names, String name) {
        for (Helper air : names) {
            if (name.equals(air.getName())) {
                return air;
            }
        }
        return null;
    }

    private static void writeNames(String namePath, String letersPath) {
        List<Helper> airMeetings = workMeeting(namePath, letersPath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./name.txt"))) {
            bw.write("姓名 --- 协助参加工作 --- 投稿\n");
            for (Helper air : airMeetings) {
                bw.write(air.toString() + "\n");
            }
        } catch (IOException e) {
            //e.printStackTrace();
            //System.out.println("人员名单读取错误...");
        }
    }

    public static void main(String[] args) {
        writeNames("D:\\dev\\Java\\ELearning\\src\\main\\resources\\data\\names.txt", "D:\\dev\\Java\\ELearning\\src\\main\\resources\\data\\投稿协助工作.txt");
    }


}
