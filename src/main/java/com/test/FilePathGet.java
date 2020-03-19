package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilePathGet {

    public List<String> keylst = new ArrayList<String>();
    public String directoryName = "";
    public List<String> outKeylst = new ArrayList<String>();
    public List<String> pathlst = null;
    public Map<String, List<String>> map = new HashMap<String, List<String>>();

    public FilePathGet(String path) {
        try {
            this.setKeylst();
            this.paths(path);
            // this.chkKey();
            this.outVal();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void paths(String path) throws Exception {
        final File f1 = new File(path);
        if (f1.isDirectory()) {
            if (keylst.contains(f1.getName())) {
                // System.err.println("f1.getName()->>" + f1.getName());
                directoryName = f1.getName();
            }
            final File[] fileList = f1.listFiles();
            for (final File f2 : fileList) {
                this.paths(f2.getAbsolutePath());
            }
        } else {
            if (!directoryName.isEmpty() && f1.getAbsolutePath().contains(directoryName)) {
                pathlst = map.get(directoryName);
                pathlst.add(f1.getAbsolutePath());
                // System.out.println(directoryName + ":" + f1.getAbsolutePath().replace("\\", "/"));
            }
        }
    }

    public void chkKey() {
        for (int i = 0; i < keylst.size(); i++) {
            if (outKeylst.contains(keylst.get(i))) {
                System.out.println(keylst.get(i) + " " + outKeylst.contains(keylst.get(i)));
            } else {
                System.err.println(keylst.get(i) + " " + outKeylst.contains(keylst.get(i)));
            }
        }
    }

    public void outVal() throws Exception {
        File tmpfile = null;
        for (int i = 0; i < keylst.size(); i++) {
            final String key = keylst.get(i);
            final List<String> pathlist = map.get(key);
            if (pathlist.size() > 0) {
                for (int j = 0; j < pathlist.size(); j++) {
                    tmpfile = new File(pathlist.get(j));

                    final BufferedReader read = new BufferedReader(new FileReader(tmpfile));
                    String str = null;
                    long cnt = 0L;
                    long spacecnt = 0L;
                    while ((str = read.readLine()) != null) {
                        if (str.trim().length() == 0) {
                            spacecnt++;
                        }
                        cnt++;
                    }
                    System.out.println(key + " file://" + tmpfile.getAbsolutePath() + " "
                            + tmpfile.getName().substring(tmpfile.getName().indexOf(".") + 1) + " " + cnt + " "
                            + spacecnt);
                }
            } else {
                System.out.println(key);
            }
        }
    }

    public void setKeylst() {
        keylst.add("DSBS0010P01P");
        keylst.add("DSBS0020P01P");
        keylst.add("DSBS0040P01P");
        keylst.add("DSBS0070P01P");
        keylst.add("DSBS0080P01P");
        keylst.add("DSBS0100P01P");
        keylst.add("DSBS0110P01P");
        keylst.add("DSBS0120P01P");
        keylst.add("DSBS0180P01P");
        keylst.add("DSBS0050P01P");
        keylst.add("DSBS0130P01P");
        keylst.add("DSBS0190P01P");
        keylst.add("DSBS0220P01P");
        keylst.add("DSBS0250P01P");
        keylst.add("DSBS0270P01P");
        keylst.add("DSBS0310P01P");
        keylst.add("DSBS0330P01P");
        keylst.add("DSBS0350P01P");
        keylst.add("DSBS0370P01P");
        keylst.add("DSES0120P01P");
        keylst.add("DSAS0010P01P");
        keylst.add("DSAS0020P01P");
        keylst.add("DSAS0030P01P");
        keylst.add("DSAS0040P01P");
        keylst.add("DSHS0010P01P");
        keylst.add("DSHS0020P01P");
        keylst.add("DSHS0030P01P");
        keylst.add("DSHS0040P01P");
        keylst.add("DSHS0050P01P");
        keylst.add("DSES0080P01P");
        keylst.add("DMAS0050P01P");
        keylst.add("DMAS0090P01P");
        keylst.add("DMAS0110P01P");
        keylst.add("DMAS0120P01P");
        keylst.add("DMAS0130P01P");

        for (int i = 0; i < keylst.size(); i++) {
            map.put(keylst.get(i), new ArrayList<String>());
        }

    }

    public static void main(String[] args) {
        // new FilePathGet("C:\\Users\\B1361\\Desktop\\count_source\\20190926差");
        new FilePathGet("C:\\Users\\B1361\\Desktop\\count_source\\20200316→20190926");
    }

}
