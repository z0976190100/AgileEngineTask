package com.agileengine.xmlcrawler;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DOMParser {

    String resourcePath = "C:/Users/admin/Google Диск/codeacademy/AgileEngineTask/html/sample-0-origin.html";
    String targetElementId = "make-everything-ok-button";

Element findElementById(String resourcePath, String targetElementId){

    Element makeOkButton = null;

    try {

        File file = new File(resourcePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        doc.getDocumentElement().normalize();

        makeOkButton = doc.getElementById(targetElementId);

    } catch (Exception e) {
        e.printStackTrace();
    }

    return makeOkButton;
}

}



