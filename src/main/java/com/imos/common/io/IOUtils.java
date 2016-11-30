package com.imos.common.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.java.Log;

/**
 *
 * @author Alok
 */
@Log
public class IOUtils {

    public static void searchListOfPath(String filePath, List<String> paths, String endSWith, String notEndSWith) {
        File directory = new File(filePath);
        if (directory.exists() && directory.isDirectory() && !directory.isHidden()) {
            for (File file : directory.listFiles()) {
                if (file.isFile() && file.getName().endsWith(endSWith) && !file.getName().endsWith(notEndSWith)) {
                    paths.add(file.getPath());
                } else if (file.isDirectory()) {
                    searchListOfPath(file.getPath(), paths, endSWith, notEndSWith);
                }
            }
        }
    }

    public static String readFileString(String fileName) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(IOUtils.class.getClassLoader().getResourceAsStream(fileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException ex) {
            log.severe(ex.getMessage());
        }

        return builder.toString();
    }

    public static List<String> readFileAsLines(String fileName) {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);
        if (file.exists() && file.isFile() && !file.isHidden()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException ex) {
                log.severe(ex.getMessage());
            }
        } else {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(IOUtils.class.getClassLoader().getResourceAsStream(fileName)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException ex) {
                log.severe(ex.getMessage());
            }
        }

        return lines;
    }

    public static void writeFileAsString(String filePath, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.append(data);
        } catch (IOException ex) {
            log.severe(ex.getMessage());
        }
    }

    public static void writeFileAsLines(String filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.append(line);
                writer.newLine();
            }
        } catch (IOException ex) {
            log.severe(ex.getMessage());
        }
    }
}
