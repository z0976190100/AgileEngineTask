package com.agileengine.xmlcrawler;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class HlebFtp {

    private String server = "hlebtech.ru";
    private int port = 21;
    private String user = "u0530654";
    private String password = "zTu!H7to";
    private FTPClient ftp;

    // constructor

    void open() throws IOException {
        ftp = new FTPClient();

        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        ftp.connect(server, port);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("Exception in connecting to FTP Server");
        }

        ftp.login(user, password);
    }

    void close() throws IOException {
        ftp.disconnect();
    }

    void putFileToPath(File file, String path) throws IOException {
        ftp.storeFile(path, new FileInputStream(file));
    }

    public static void main(String[] args) {

        HlebFtp hlebFtp = new HlebFtp();
        
        try {
            hlebFtp.open();
            hlebFtp.putFileToPath(new File(
                    "C:/Users/admin/Google Диск/codeacademy/AgileEngineTask/src/main/html_source1/aaaftptest.txt"), "/aaaftptest.txt");
            hlebFtp.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

