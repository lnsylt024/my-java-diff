package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class FileChange {

    public FileChange(String path) throws Exception {
        this.paths(path);
    }

    public void paths(String path) throws Exception {
        final File f1 = new File(path);
        if (f1.isDirectory()) {
            final File[] fileList = f1.listFiles();
            for (final File f2 : fileList) {
                this.paths(f2.getAbsolutePath());
            }
        } else {
            if (f1.getName().endsWith(".css")) {
                // System.out.println(f1.getAbsolutePath().replace("\\", "/"));
                this.isUTF8(f1);
            }
        }
    }

    public void isUTF8(File file) throws Exception {

        final InputStream in = new FileInputStream(file);
        final byte[] head = new byte[3];
        in.read(head);
        in.close();
        String code = "";
        code = "gb2312";
        System.out.println(">>" + head[0] + " " + head[1] + " " + head[2] + "<<");

        // if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
        // System.out.println("[UTF8]" + file.getName());
        // } else {
        // System.out.println("[OTHER]" + file.getName());
        // }
        if (head[0] == -1 && head[1] == -2) {
            code = "UTF-16";
        }
        if (head[0] == -2 && head[1] == -1) {
            code = "Unicode";
        }
        if (head[0] == -17 && head[1] == -69 && head[2] == -65) {
            code = "UTF-8";
        }
        System.out.println(file.getName() + ">>" + code);
    }

    public static void main(String[] args) throws Exception {

        new FileChange("C:\\asw-tour\\workspace\\csm_new\\WebContent\\resources\\css");

    }

}
