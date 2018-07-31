package com.agileengine.xmlcrawler;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HtmlFileEditor  {

    private static  String FILE_DIR_RESULT = "C:/Users/admin/Google Диск/codeacademy/AgileEngineTask/src/main/html_result/";
    private static String FILE_DIR_SOURCE = "C:/Users/admin/Google Диск/codeacademy/AgileEngineTask/src/main/html_source/";
    private static String CHARSET_NAME = "utf8";
    private static  String FILE_EXT = ".html";


    public static void main(String[] args) {

     // changeAttr();

        demolishScript();

       /* String[] fileListOfDir = getFileList(FILE_DIR_SOURCE, FILE_EXT);

        if (fileListOfDir == null || fileListOfDir.length == 0) {

            System.out.println("Dir is empty");
            System.exit(0);
        }

        for (String fileName : fileListOfDir) {

            Document doc = getDoc(new File(FILE_DIR_SOURCE+ fileName));



            //System.out.println(doc.outerHtml());
            if (doc != null) {

                // appending <script> TAG
                //List<Element> scriptList = doc.getElementsByTag("script");
                //scriptList.get(0).after("<script type=\"text/javascript\" src=\"form/form.js\"></script>");
                System.out.println("editing file   "+ fileName);
              Element contactForm = doc.getElementById("contactformquick");
              if(contactForm != null)
              contactForm.after("<div id=\"success_message\" style=\"width:100%; height:100%; display:none; \"> <h3>Мы приняли Ваш заказ в обработку!</h3> </div>\n" +
                      "<div id=\"error_message\" style=\"width:100%; height:100%; display:none; \"> <h3>Ошибочка!</h3>Проверьте, правильно ли заполнены поля.</div>");
//                <div id="success_message" style="width:100%; height:100%; display:none; "> <h3>Мы приняли Ваш заказ в обработку!</h3> </div>
//    <div id="error_message" style="width:100%; height:100%; display:none; "> <h3>Ошибочка!</h3>Проверьте, правильно ли заполнены поля.</div>

                toFileWriter(FILE_DIR_RESULT  + fileName, doc);
            }
        }*/
    }


    private static Document getDoc (File htmlFile) {
        try {
            Document doc = Jsoup.parse(
                    htmlFile,
                    CHARSET_NAME,
                    htmlFile.getAbsolutePath());

            return doc;

        } catch (IOException e) {
            //LOGGER.error("Error reading file", htmlFile.getAbsolutePath(), e);
            System.err.println("Error reading file");
            return null;
        }
    }

    private static String[] getFileList(String sourceFolder, String fileExtention) {
        HlebParser.GenericExtFilter filter = new HlebParser.GenericExtFilter(fileExtention);

        File dir = new File(sourceFolder);

        if (!dir.isDirectory()) {
            System.out.println("Directory does not exists : " + FILE_DIR_RESULT);
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


    private static void toFileWriter(String f, Document doc){
        try {
            FileUtils.writeStringToFile(new File(f), doc.outerHtml(), "UTF-8");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void demolishScript(){

        String[] fileListOfDir = getFileList(FILE_DIR_SOURCE, FILE_EXT);

        if (fileListOfDir == null || fileListOfDir.length == 0) {

            System.out.println("Dir is empty");
            System.exit(0);
        }

        for (String fileName : fileListOfDir) {

            Document doc = getDoc(new File(FILE_DIR_SOURCE+ fileName));



            //System.out.println(doc.outerHtml());
            if (doc != null) {

                System.out.println("editing file   "+ fileName);

               Elements scripts = doc.getElementsByTag("script");
               if(!scripts.isEmpty()) {
                   for (Element script : scripts) {
                       if (script.toString().contains("var widget_id = 866405")) {
                           script.html("");
                           break;
                       }
                   }
               }
                toFileWriter(FILE_DIR_RESULT  + fileName, doc);
            }
        }

    }

    private static void changeAttr(){


            String[] fileListOfDir = getFileList(FILE_DIR_SOURCE, FILE_EXT);

            if (fileListOfDir == null || fileListOfDir.length == 0) {

                System.out.println("Dir is empty");
                System.exit(0);
            }

            for (String fileName : fileListOfDir) {

                Document doc = getDoc(new File(FILE_DIR_SOURCE+ fileName));



                //System.out.println(doc.outerHtml());
                if (doc != null) {

                    System.out.println("editing file   "+ fileName);

                    Elements imgs = doc.getElementsByAttributeValue("src", "media/footer/original/footer-img.png");
                    if(!imgs.isEmpty()) {
                        for (Element img : imgs) {
                            if (img.attr("style").contains("margin-right: 10px; margin-left: 10px;")) {
                                img.attr("style", "margin-right: 10px;");
                                break;
                            }
                        }
                    }

                    toFileWriter(FILE_DIR_RESULT  + fileName, doc);
                }
            }


        }

}
