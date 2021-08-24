package com.example.generactive_api_v2.util;


import com.example.generactive_api_v2.exceptions.AppRuntimeException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private CSVReader() {
    }

    public static List<String> getDataFromFIle(File file) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            while (bf.ready()) {
                String line = bf.readLine();
                lines.add(line);
            }
        } catch (IOException e) {
            throw new AppRuntimeException("wrong file path");
        }
        return lines;
    }
}
