package org.example.ranking;

import lombok.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
class AirMeeting {
    private String name;
    private double card;
    private int reply;

    public AirMeeting(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "\t" + card + "\t" + reply;
    }
}

public class WorkMeeting {

    private static List<AirMeeting> readNames(String path) {
        List<AirMeeting> names = new ArrayList<>(128);
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                names.add(new AirMeeting(line.trim()));
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("人员名单读取错误...");
        }
        return names;
    }

    private static List<AirMeeting> workMeeting(String namesPath, String letersPath) {
        List<AirMeeting> names = readNames(namesPath);
        try (BufferedReader br = new BufferedReader(new FileReader(letersPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.length() > 3) {
                    String[] tmp = line.trim().split("：");
                    if ("card".equals(tmp[0])) {
                        for (String air : tmp[1].trim().split("、")) {
                            AirMeeting meeting = getAirMeetingByName(names, air.trim());
                            if (Objects.nonNull(meeting)) {
                                double card = meeting.getCard() + 0.5;
                                meeting.setCard(card);
                            } else {
                                System.out.println("card : " + air);
                                continue;
                            }
                        }
                    }
                    if ("reply".equals(tmp[0])) {
                        for (String air : tmp[1].trim().split("、")) {
                            String[] nameReply = splitStr(air);
                            AirMeeting meeting = getAirMeetingByName(names, nameReply[0]);
                            if (Objects.isNull(meeting)) {
                                System.out.println("reply : " + Arrays.toString(nameReply));
                                continue;
                            }
                            //System.out.println(Arrays.toString(nameReply));
                            int reply = meeting.getReply() + Integer.parseInt(nameReply[1]);
                            meeting.setReply(reply);
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

    private static AirMeeting getAirMeetingByName(List<AirMeeting> names, String name) {
        for (AirMeeting air : names) {
            if (name.equals(air.getName())) {
                return air;
            }
        }
        return null;
    }

    private static void writeNames(String namePath, String letersPath) {
        List<AirMeeting> airMeetings = workMeeting(namePath, letersPath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./name.txt"))) {
            bw.write("姓名 --- 签到前五 --- 高赞回复\n");
            for (AirMeeting air : airMeetings) {
                bw.write(air.toString() + "\n");
            }
        } catch (IOException e) {
            //e.printStackTrace();
            //System.out.println("人员名单读取错误...");
        }
    }

    public static void main(String[] args) {
        writeNames("D:\\dev\\Java\\ELearning\\src\\main\\resources\\data\\names.txt", "D:\\dev\\Java\\ELearning\\src\\main\\resources\\data\\workMeeting.txt");
    }


}
