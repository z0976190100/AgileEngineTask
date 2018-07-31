package com.agileengine.xmlcrawler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileWrite {

    public static void toFileWriter(String data, String path) {
        try {
            Files.write(Paths.get(path), (data + System.lineSeparator()).getBytes(UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND); //(content + System.lineSeparator()).getBytes(UTF_8),StandardOpenOption.CREATE,StandardOpenOption.APPEND)
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
