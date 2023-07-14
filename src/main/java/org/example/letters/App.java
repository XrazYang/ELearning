package org.example.letters;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        ExcelReader.readExcel(args[0]);
    }
}
