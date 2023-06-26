package org.example.commendation;

import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ExcelReader {
    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook;
        if (XLS.equalsIgnoreCase(fileType)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (XLSX.equalsIgnoreCase(fileType)) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("文件格式错误...");
        }
        return workbook;
    }


    public static void readExcel(String fileName) throws IOException {
        Workbook workbook;

        String fileType = Files.getFileExtension(fileName);
        FileInputStream input = FileUtils.openInputStream(new File(fileName));

        workbook = getWorkbook(input, fileType);

        // 读取excel中的数据
        Set<Worker> workers = parseExcel(workbook);
        Worker[] woks = workers.toArray(Worker[]::new);
        Arrays.sort(woks);
        formatInfo(woks);
    }

    public static void formatInfo(Worker[] workers) {
        //重点航线，没有过100
        StringBuffer keys = new StringBuffer();
        keys.append("【网络渠道➕5】: ");

        //不是重点航线，没有过100
        StringBuffer normals = new StringBuffer();
        normals.append("【网络渠道不满100➕1】: ");

        //重点航向，过100
        StringBuffer normalsOverHundred = new StringBuffer();
        normalsOverHundred.append("【网络渠道满100➕2】: ");

        //重点航线合并
        for (Worker worker : workers) {
            //将重点航线，其过百
            if (worker.isKey() && worker.isOverHundred()) {
                for (Worker worker1 : workers) {
                    if (worker.getName().equals(worker1.getName()) && worker1.isKey() && !worker1.isOverHundred()) {
                        int num = worker.getNums() + worker1.getNums();
                        worker1.setNums(num);
                    }
                }
            }
        }

        Arrays.stream(workers).forEach(worker -> {
            if (worker.isKey()) {
                if (!worker.isOverHundred()) {
                    keys.append(worker.getName() + "" + worker.getNums() + "、");
                }
            } else {
                if (worker.isOverHundred()) {
                    normalsOverHundred.append(worker.getName() + "" + worker.getNums() + "、");
                } else {
                    normals.append(worker.getName() + "" + worker.getNums() + "、");
                }
            }
        });
        System.out.println("\uD83D\uDD3A重点航线（5月25日-6月1日数据）:");
        System.out.println(keys);

        System.out.println("\uD83D\uDD3A普通航线:");
        System.out.println(normalsOverHundred);
        System.out.println(normals);
    }


    private static Set<Worker> parseExcel(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        Set<Worker> workers = new HashSet<>(512);
        for (int i = sheet.getFirstRowNum() + 1; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            //处理乘务组姓名
            Cell groups = row.getCell(9);
            groups.setCellType(CellType.STRING);

            //表扬信数量
            Cell num = row.getCell(10);
            num.setCellType(CellType.STRING);

            //是否超过一百字
            Cell overHundred = row.getCell(11);
            overHundred.setCellType(CellType.STRING);

            //是否重点测评航线
            Cell key = row.getCell(12);
            key.setCellType(CellType.STRING);
            parserName(workers, groups.getStringCellValue(), key.getStringCellValue(), overHundred.getStringCellValue(), num.getStringCellValue());
        }
        return workers;
    }

    private static void parserName(Set<Worker> workers, String groups, String key, String overHundred, String num) {
        for (String s : formatStr(groups)) {
            String[] tmp = s.replace("）", "").split("（");
            //System.out.println(Arrays.toString(tmp));
            if ("YN1".equalsIgnoreCase(tmp[1])) {

                //姓名相同，且航线相同,且是是否超过一百字相同，才更新nums
                //否则 新添加数据
                Worker worker = new Worker();
                worker.setName(tmp[0]);
                worker.setKey(isKey(key));
                worker.setOverHundred(isKey(overHundred));

                Worker sameWorker = getSameWorker(workers, worker);
                if (sameWorker != null) {
                    sameWorker.setNums(sameWorker.getNums() + getNum(num));
                } else {
                    worker.setNums(getNum(num));
                    workers.add(worker);
                }
            }
        }
    }

    private static Worker getSameWorker(Set<Worker> workers, Worker worker) {
        for (Worker tmp : workers) {
            if (tmp.equals(worker)) {
                return tmp;
            }
        }
        return null;
    }

    private static String[] formatStr(String str) {
        str = str.replaceAll(" ", "");
        str = str.replaceAll("、", "");
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                buffer.append("（");
            } else if (c == ')') {
                buffer.append("）");
            } else {
                buffer.append(c);
            }
        }
        str = buffer.toString();
        str = str.replaceAll("）", "） ");
        return str.split(" ");
    }

    private static boolean isKey(String key) {
        key = key.trim();
        return "是".equalsIgnoreCase(key);
    }

    private static int getNum(String num) {
        num = num.trim();
        return Integer.parseInt(num);
    }
}
