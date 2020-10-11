package ru.digitalhabbits.homework1.service;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.Arrays.stream;

public class FileEngine {
    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = "txt";

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) {
        // TODO: NotImplemented

        final String currentDir = System.getProperty("user.dir");
        final File resultDir = new File(currentDir + "/" + RESULT_DIR);

        if (resultDir.exists()) {
            resultDir.mkdir();
        }
        String fileName = resultDir.getName() + String.format(RESULT_FILE_PATTERN, pluginName);

        try (FileWriter fileWriter = new FileWriter(fileName, false)) {
            fileWriter.write(text.toLowerCase());
            fileWriter.flush();
            return true;
        } catch (IOException ex) {
            ex.getStackTrace();
            return false;
        }
    }

    public void cleanResultDir() {
        final String currentDir = System.getProperty("user.dir");
        final File resultDir = new File(currentDir + "/" + RESULT_DIR);
        stream(resultDir.list((dir, name) -> name.endsWith(RESULT_EXT)))
                .forEach(fileName -> new File(resultDir + "/" + fileName).delete());
    }
}
