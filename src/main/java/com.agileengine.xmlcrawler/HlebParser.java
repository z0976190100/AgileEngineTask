package com.agileengine.xmlcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.charset.StandardCharsets.UTF_8;


public class HlebParser {

    private static Logger LOGGER = LoggerFactory.getLogger(HlebParser.class);
    private static String targetElementAttr = "href";
    private static String targetElementTag = "a";
    private static String requiredElementPath = "";
    private static String CHARSET_NAME = "utf8";
    private static String FILENAME_PREF = "BYPAGE-badlinks-list";
    private static String FILENAME_PREF_UNIQUE = "UNIQUE-badlinks-list";
    private static final String FILE_DIR = "C:/Users/admin/Google Диск/codeacademy/AgileEngineTask/src/main/resources/html";
    private static final String FILE_EXT = ".html";

    public static void main(String[] args) {


        String[] fileListOfDir = getFileList(FILE_DIR, FILE_EXT);
        Set<String> isValueUnique = new HashSet<>();

        if (fileListOfDir == null || fileListOfDir.length == 0) {

            System.out.println("Dir is empty");
            System.exit(0);
        }


        String fileId = LocalDateTime.now().toString().replace(":", "-");

        for (String fileName : fileListOfDir) {

            System.out.println("+++++++++ PAGE     " + fileName + "  has links to non-existing pages: ");

            toFileWriter("+++++++++ PAGE     " + fileName + "  has links to non-existing pages: ", FILE_DIR + File.separator + FILENAME_PREF + fileId + ".txt");

            boolean hasBadLinks = false;

            // searching for all required elements in exact file
            List<Element> requiredElementsList = findElementsByTag(new File(FILE_DIR + File.separator + fileName), targetElementTag);

            if (requiredElementsList != null) {
                for (Element tag : requiredElementsList) {

                    String attrValue = getAttrValue(tag, targetElementAttr);

                    // checking if this attribute valuable for us
                    if (attrValue != null && !attrValue.contains("http") && !attrValue.contains("fermer") && attrValue.contains(".html")) {

                        System.out.println("Cheking if exists : " + attrValue);

                        if (!ifFileExists(attrValue)) {
                            hasBadLinks = true;
                            toFileWriter(attrValue, FILE_DIR + File.separator + FILENAME_PREF + fileId + ".txt");

                            // writes to file that contains only unique fileName registries;
                            if (isValueUnique.add(attrValue))
                                toFileWriter(attrValue, FILE_DIR + File.separator + FILENAME_PREF_UNIQUE + fileId + ".txt");

                            System.out.println("FALSE");
                        } else {
                            System.out.println("TRUE");
                        }
                    }
                }
            }

            if (!hasBadLinks) toFileWriter("NO BAD LINKS!", FILE_DIR + File.separator + FILENAME_PREF + fileId + ".txt");

        }

    }


    private static String[] getFileList(String sourceFolder, String fileExtention) {
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


    private static String getAttrValue(Element element, String attr) {
        return element.attr(attr);
    }

    private static List<Element> findElementsByTag(File htmlFile, String targetElementTag) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return doc.getElementsByTag(targetElementTag);

        } catch (IOException e) {
            //LOGGER.error("Error reading file", htmlFile.getAbsolutePath(), e);
            System.err.println("Error reading file");
            return null;
        }
    }


//    private static Element findElementByAttr(File htmlFile, String targetElementAttr) {
//        try {
//            Document doc = Jsoup.parse(
//                    htmlFile,
//                    CHARSET_NAME,
//                    htmlFile.getAbsolutePath());
//
//            Elements elements = doc.getElementsByAttribute(targetElementAttr);
//            if (!elements.isEmpty()) {
//
//                for (Element elem : elements) {
//
//                    String attrVal = elem.attr("onclick");
//
//                    if (attrVal.contains(ONCLICK_DONE) || attrVal.contains(ONCLICK_COMPLETE) || attrVal.contains(ONCLICK_FINALIZE)) {
//
//                        return elem;
//                    }
//                }
//            }
//
//            return null;
//
//        } catch (IOException e) {
//            //LOGGER.error("Error reading file", htmlFile.getAbsolutePath(), e);
//            System.err.println("Error reading file");
//            return null;
//        }
//    }

    private static void getNextParentNodeName(Node childNode) {

        if (childNode.parentNode().nodeName().equals("html")) {
            requiredElementPath = "html > " + requiredElementPath;
            return;
        }
        requiredElementPath = (childNode.parentNode().nodeName()) + " > " + requiredElementPath;
        getNextParentNodeName(childNode.parentNode());
    }

    private static void toFileWriter(String data, String path) {
        try {
            Files.write(Paths.get(path), (data + System.lineSeparator()).getBytes(UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND); //(content + System.lineSeparator()).getBytes(UTF_8),StandardOpenOption.CREATE,StandardOpenOption.APPEND)
        } catch (IOException e) {
            System.out.println("toFileWriter is failed to execute.");
            e.printStackTrace();
        }
    }

    private static boolean ifFileExists(String fileName) {

        File f = new File(FILE_DIR + File.separator + fileName);

        return f.exists();

    }

}

