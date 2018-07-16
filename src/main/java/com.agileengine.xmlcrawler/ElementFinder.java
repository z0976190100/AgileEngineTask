package com.agileengine.xmlcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class ElementFinder {

    private static Logger LOGGER = LoggerFactory.getLogger(ElementFinder.class);
    private static String targetElementId = "make-everything-ok-button";
    private static String targetElementAttr = "onclick";
    private static String XMLPath = "";
    private static final String ONCLICK_DONE = "window.okDone";
    private static final String ONCLICK_COMPLETE = "window.okComplete";
    private static final String ONCLICK_FINALIZE = "window.okFinalize";
    private static String onclickValue = "";
    private static String CHARSET_NAME = "utf8";

    public static void main(String[] args) {

        // Jsoup requires an absolute file path to resolve possible relative paths in HTML,
        // so providing InputStream through classpath resources is not a case
        String resourcePath = args[0];//"C:/Users/admin/Google Диск/codeacademy/AgileEngineTask/src/main/resources/html/sample-0-origin.html";
        String resourcePath2 = args[1];//"C:/Users/admin/Google Диск/codeacademy/AgileEngineTask/src/main/resources/html/sample-2-container-and-clone.html";//"C:/Users/admin/Google Диск/codeacademy/AgileEngineTask/html/sample-0-origin.html";


        setOriginCompairsonFactor(resourcePath, targetElementId);


        Element requiredEl = findElementByAttr(new File(resourcePath2), targetElementAttr);

        if (requiredEl != null) {
            XMLPath = requiredEl.tagName();
            Node elementParent = requiredEl.parent();
            getNextParentNodeName(elementParent);
            System.out.println("onclick Value is: " + onclickValue);
            System.out.println("Target element in diff-file XML path is: " + XMLPath);
            System.exit(0);
        }

        System.out.println("it seems, no match found");
        System.exit(0);


    }

    private static void setOriginCompairsonFactor(String resourcePath, String targetElementId) {

        Element originElement = findElementById(new File(resourcePath), targetElementId);
        if (originElement != null) onclickValue = originElement.attr(targetElementAttr);


    }

    private static Element findElementById(File htmlFile, String targetElementId) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return doc.getElementById(targetElementId);

        } catch (IOException e) {
            //LOGGER.error("Error reading file", htmlFile.getAbsolutePath(), e);
            System.err.println("Error reading file");
            return null;
        }
    }


    private static Element findElementByAttr(File htmlFile, String targetElementAttr) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            Elements elements = doc.getElementsByAttribute(targetElementAttr);
            if (!elements.isEmpty()) {

                for (Element elem : elements) {

                    String attrVal = elem.attr("onclick");

                    if (attrVal.contains(ONCLICK_DONE) || attrVal.contains(ONCLICK_COMPLETE) || attrVal.contains(ONCLICK_FINALIZE)) {

                        return elem;
                    }
                }
            }

            return null;

        } catch (IOException e) {
            //LOGGER.error("Error reading file", htmlFile.getAbsolutePath(), e);
            System.err.println("Error reading file");
            return null;
        }
    }

    private static void getNextParentNodeName(Node childNode) {

        if (childNode.parentNode().nodeName() == "html") {
            XMLPath = "html > " + XMLPath;
            return;
        }
        XMLPath = (childNode.parentNode().nodeName()) + " > " + XMLPath;
        getNextParentNodeName(childNode.parentNode());
    }

}

