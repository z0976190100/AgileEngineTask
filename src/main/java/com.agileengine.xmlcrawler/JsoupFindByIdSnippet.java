package com.agileengine.xmlcrawler;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.jsoup.nodes.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsoupFindByIdSnippet {

    private static Logger LOGGER = (Logger) LoggerFactory.getLogger(JsoupFindByIdSnippet.class);
    private static String nodeList = "";
    private static String CHARSET_NAME = "utf8";

    public static void main(String[] args) {


        // Jsoup requires an absolute file path to resolve possible relative paths in HTML,
        // so providing InputStream through classpath resources is not a case
        String resourcePath = "C:/Users/admin/Google Диск/codeacademy/AgileEngineTask/html/sample-0-origin.html";
        String targetElementId = "make-everything-ok-button";

        Element buttonOpt = findElementById(new File(resourcePath), targetElementId);

        if (buttonOpt == null) {
            System.out.println("No button is found.");
            System.exit(0);
        }

        Node buttonParent = buttonOpt.parentNode();
        getNextParentNodeName(buttonParent);

        System.out.println(nodeList);
    }

    private static Element findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return doc.getElementById(targetElementId);

        } catch (IOException e) {
            LOGGER.error("Error reading [{}] file", htmlFile.getAbsolutePath(), e);
            return null;
        }
    }

    private static void getNextParentNodeName(Node childNode) {

        if (childNode.parentNode().nodeName() == "html") {
            nodeList = "html > " + nodeList;
            return;
        }
            nodeList = (childNode.parentNode().nodeName()) + " > " + nodeList;
            getNextParentNodeName(childNode.parentNode());
        }



}

