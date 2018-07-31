package com.agileengine.xmlcrawler;

import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDateTime;
import java.util.Arrays;

import static com.agileengine.xmlcrawler.FileWrite.toFileWriter;

public class FileInFolderByExtension {


    private static final String FILE_DIR = "C:/Users/admin/Google Диск/codeacademy/AgileEngineTask/src/main/resources/html/test";
    private static final String FILE_TEXT_EXT = ".html";
    private static final String FILES_LIST_FILE_MASK = "C:/Users/admin/Google Диск/codeacademy/AgileEngineTask/src/main/resources/html/list-";
    private static int fileId = 0;

    public static void main(String args[]) {
        listFile(FILE_DIR, FILE_TEXT_EXT);
    }

    private static void listFile(String folder, String ext) {

        GenericExtFilter filter = new GenericExtFilter(ext);

        File dir = new File(folder);

        if (!dir.isDirectory()) {
            System.out.println("Directory does not exists : " + FILE_DIR);
            return;
        }

        // list out all the file name and filter by the extension
        String[] list = dir.list(filter);

        if (list.length == 0) {
            System.out.println("no files end with : " + ext);
            return;
        }

        String fId = LocalDateTime.now().toString().replace(":", "-");
        for (String file : list) {
            toFileWriter(file, FILES_LIST_FILE_MASK + fId + ".txt");
        }
    }

    public static String[] getFileList(String sourceFolder, String fileExtention) {
        GenericExtFilter filter = new GenericExtFilter(fileExtention);

        File dir = new File(sourceFolder);

        if (!dir.isDirectory()) {
            System.out.println("Directory does not exists : " + FILE_DIR);
            return null;
        }

        // list out all the file name and filter by the extension
        String[] list = dir.list(filter);

        if (list == null || list.length == 0) {
            System.out.println("no files end with : " + fileExtention);
            return null;
        }

           /* for (String file : list) {
                System.out.println(File.separator);
                String temp = new StringBuffer(FILE_DIR).append(File.separator)
                        .append(file).toString();
                System.out.println("file : " + temp);
            }*/

        return list;
    }

    // inner class, generic extension filter
    public static class GenericExtFilter implements FilenameFilter {

        private String ext;

        GenericExtFilter(String ext) {
            this.ext = ext;
        }

        public boolean accept(File dir, String name) {
            return (name.endsWith(ext));
        }
    }
}
