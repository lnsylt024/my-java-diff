package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class FilePath {

    List arrayAll = new ArrayList();
    Map extendsMap = new HashMap();
    Map subMap = new HashMap();
    int intCommonXML = 0;

    List colListT = null;
    List rowListT = null;
    List colList = null;
    List rowList = null;

    final String XML_FILE_PATH = "xmlFilePath";
    final String KEY_NAME = "keyName";
    final String USE_PAGE = "usePage";
    final String FRAME_PAGE = "framePage";
    final String IN_CLUDE_FILES = "inCludeFiles";
    final String COMMON_LIST = "commonList";

    public FilePath() {
        this.paths(
                "C:/Users/B1361/Desktop/feature-ines_RB-20200331_20200121ver/atd-app-feature-ines/RB/atd-csm-web/src/main");
        System.out.println(arrayAll.size());

        this.newExtendsMap((List<Map>) arrayAll.get(intCommonXML));

        this.setExtendsMapVal();

        this.printCommonXML((List<Map>) arrayAll.get(intCommonXML));

    }

    public void setExtendsMapVal() {

        List subList = null;
        for (int i = 0; i < arrayAll.size(); i++) {
            if (i == intCommonXML) {
                continue;
            }

            final Map subMap = (Map) arrayAll.get(i);
            subList = (ArrayList) extendsMap.get(subMap.get(FRAME_PAGE));
            if (subList != null) {
                subList.add(subMap.get(KEY_NAME));

            }
        }
    }

    public void newExtendsMap(List<Map> commonList) {
        for (final Map m : commonList) {
            extendsMap.put(m.get(KEY_NAME), new ArrayList());
        }
    }

    public void printCommonXML(List<Map> commonList) {

        colListT = new ArrayList();
        rowListT = new ArrayList();

        final Map cmap = new HashMap();

        // System.out.println(map.get(XML_FILE_PATH));
        try {
            final FileWriter fr = new FileWriter(new File("C:\\temp\\sourcelist\\【縦】共通一覧（子含む）.txt"));
            final BufferedWriter br = new BufferedWriter(fr);
            int no = 0;
            List usePageT = null;
            for (final Map map : commonList) {
                final String keyName = (String) map.get(KEY_NAME);
                final String framePage = (String) map.get(FRAME_PAGE);
                final List includeFiles = (List) map.get(IN_CLUDE_FILES);

                usePageT = (List) extendsMap.get(keyName);
                final List usePages = new ArrayList(new HashSet(usePageT));
                map.put(USE_PAGE, usePages);

                List sumList = new ArrayList();

                // 機能ID
                for (int j = 0; j < usePages.size(); j++) {
                    sumList = new ArrayList();
                    final List includeFilesSub = (List) subMap.get(usePages.get(j));

                    if ("DBAS0010P01P".equals(usePages.get(j))) {
                        System.out.println(usePages.get(j));
                    }

                    sumList.addAll(includeFiles);
                    sumList.addAll(includeFilesSub);

                    Map mt = null;
                    mt = new HashMap();

                    // 関連ソース
                    for (int i = 0; i < sumList.size(); i++) {
                        no++;
                        System.out.println(
                                i + ":" + keyName + " " + framePage + " " + usePages.get(j) + "  " + sumList.get(i));
                        br.append(no + ":" + keyName + " " + framePage + " " + usePages.get(j) + "  " + sumList.get(i));
                        br.newLine();
                        mt.put(sumList.get(i), "●");
                        cmap.put(usePages.get(j), mt);
                    }

                    colListT.addAll(sumList);
                }
                // colListT.addAll(includeFiles);
                rowListT.addAll(usePages);
            }
            br.close();
            fr.close();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

        colList = new ArrayList(new HashSet(colListT));
        rowList = new ArrayList(new HashSet(rowListT));

        Collections.sort(colList);// 関連ソース
        Collections.sort(rowList);// 機能ID

        final List jsList = new ArrayList();
        final List jspList = new ArrayList();
        final List cssList = new ArrayList();

        try {
            final FileWriter fr = new FileWriter(new File("C:\\temp\\sourcelist\\【横】共通一覧（子含む）.txt"));
            final BufferedWriter br = new BufferedWriter(fr);

            final FileWriter cssfr = new FileWriter(new File("C:\\temp\\sourcelist\\【横】共通一覧（子含むCSS）.txt"));
            final BufferedWriter cssbr = new BufferedWriter(cssfr);

            final FileWriter jspfr = new FileWriter(new File("C:\\temp\\sourcelist\\【横】共通一覧（子含むJSP）.txt"));
            final BufferedWriter jspbr = new BufferedWriter(jspfr);

            final FileWriter jsfr = new FileWriter(new File("C:\\temp\\sourcelist\\【横】共通一覧（子含むJavaScript）.txt"));
            final BufferedWriter jsbr = new BufferedWriter(jsfr);

            for (int j = 0; j < rowList.size() + 1; j++) {

                if (j == 0) {
                    System.out.print("Num:" + "ID");
                    br.append("Num:" + "ID");

                    cssbr.append("Num:" + "ID");
                    jspbr.append("Num:" + "ID");
                    jsbr.append("Num:" + "ID");

                    for (int i = 0; i < colList.size(); i++) {
                        if (!colList.get(i).equals("")) {
                            System.out.print(":" + colList.get(i));
                            br.append(":" + colList.get(i));
                            if (((String) colList.get(i)).endsWith(".js")) {
                                jsList.add(colList.get(i));
                                jsbr.append(":" + colList.get(i));
                            } else if (((String) colList.get(i)).endsWith(".jsp")) {
                                jspList.add(colList.get(i));
                                jspbr.append(":" + colList.get(i));
                            } else if (((String) colList.get(i)).endsWith(".css")) {
                                cssList.add(colList.get(i));
                                cssbr.append(":" + colList.get(i));
                            }
                        }
                    }
                } else {
                    System.out.print(j + ":" + rowList.get(j - 1));
                    br.append(j + ":" + rowList.get(j - 1));

                    cssbr.append(j + ":" + rowList.get(j - 1));
                    jspbr.append(j + ":" + rowList.get(j - 1));
                    jsbr.append(j + ":" + rowList.get(j - 1));

                    final Map imap = (Map) cmap.get(rowList.get(j - 1));
                    for (int i = 0; i < colList.size(); i++) {
                        if (!colList.get(i).equals("")) {
                            final String colstr = (String) colList.get(i);
                            if (!colstr.equals("")) {
                                if (imap.get(colstr) != null) {
                                    System.out.print(":" + imap.get(colstr));
                                    br.append(":" + imap.get(colstr));
                                } else {
                                    System.out.print(":" + "―");
                                    br.append(":" + "―");
                                }

                                if (colstr.endsWith(".css")) {
                                    if (imap.get(colstr) != null) {
                                        System.out.print(":" + imap.get(colstr));
                                        cssbr.append(":" + imap.get(colstr));
                                    } else {
                                        System.out.print(":" + "―");
                                        cssbr.append(":" + "―");
                                    }
                                } else if (colstr.endsWith(".jsp")) {
                                    if (imap.get(colstr) != null) {
                                        System.out.print(":" + imap.get(colstr));
                                        jspbr.append(":" + imap.get(colstr));
                                    } else {
                                        System.out.print(":" + "―");
                                        jspbr.append(":" + "―");
                                    }
                                } else if (colstr.endsWith(".js")) {
                                    if (imap.get(colstr) != null) {
                                        System.out.print(":" + imap.get(colstr));
                                        jsbr.append(":" + imap.get(colstr));
                                    } else {
                                        System.out.print(":" + "―");
                                        jsbr.append(":" + "―");
                                    }
                                }

                            }
                        }
                    }
                }
                System.out.println("");
                br.newLine();

                cssbr.newLine();
                jspbr.newLine();
                jsbr.newLine();
            }
            cssbr.close();
            cssfr.close();

            jspbr.close();
            jspfr.close();

            jsbr.close();
            jsfr.close();

            br.close();
            fr.close();
        } catch (final Exception ex) {
            ex.printStackTrace();
        }

    }

    public void paths(String path) {
        final File f1 = new File(path);
        if (f1.isDirectory()) {
            final File[] fileList = f1.listFiles();
            for (final File f2 : fileList) {
                this.paths(f2.getAbsolutePath());
            }
        } else {
            if (f1.getName().endsWith("tiles.xml")) {
                System.out.println(f1.getAbsolutePath().replace("\\", "/"));
                this.readXML(f1);
            }
        }
    }

    public void readXML(File file) {

        final String startStr = "<definition";
        final String middleStr = "add-attribute";
        final String endStr = "</definition>";
        final String incStr = "put-attribute";
        String[] strs = null;
        Map mapAll = new HashMap();
        final List commonList = new ArrayList();
        List includeList = null;
        int row = 0;
        mapAll.put(XML_FILE_PATH, file.getAbsolutePath().replace("\\", "/"));

        if (file.getAbsolutePath().contains("\\views\\common\\tiles.xml")) {
            intCommonXML = arrayAll.size();
            try {
                final FileReader fr = new FileReader(file);
                final BufferedReader br = new BufferedReader(fr);
                String str = "";
                final Map subMap = new HashMap();
                while ((str = br.readLine()) != null) {
                    row++;
                    str = str.trim();
                    strs = str.split("\"");
                    if (str.startsWith(startStr)) {
                        mapAll = new HashMap();
                        includeList = new ArrayList();
                        if (strs.length == 5) {
                            mapAll.put(KEY_NAME, strs[1]);
                            mapAll.put(FRAME_PAGE, strs[3]);

                            if (strs[3].endsWith(".jsp")) {
                                subMap.put(strs[1], strs[3]);
                            }
                            mapAll.put(USE_PAGE, new ArrayList());
                        } else {
                            System.out.println(row + ":length:" + strs.length);
                        }
                    } else if (str.contains(incStr)) {
                        if (strs.length >= 5) {
                            includeList.add(strs[3]);
                        } else {
                            System.out.println(row + ":length:" + strs.length);
                        }
                    } else if (str.contains(middleStr)) {
                        if (strs.length == 3) {
                            includeList.add(strs[1]);
                        } else {
                            System.out.println(row + ":length:" + strs.length);
                        }
                    } else if (str.startsWith(endStr)) {
                        mapAll.put(IN_CLUDE_FILES, includeList);
                        commonList.add(mapAll);
                    }
                }
                br.close();
                fr.close();
                changeFramePage(commonList, subMap);

                final List commonListT = new ArrayList(new HashSet(commonList));

                arrayAll.add(commonListT);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                final FileReader fr = new FileReader(file);
                final BufferedReader br = new BufferedReader(fr);
                String str = "";
                while ((str = br.readLine()) != null) {
                    row++;
                    str = str.trim();
                    strs = str.split("\"");
                    if (str.startsWith(startStr)) {
                        includeList = new ArrayList();
                        // System.out.println(str);
                        if (strs.length == 5) {
                            mapAll.put(KEY_NAME, strs[1]);
                            mapAll.put(FRAME_PAGE, strs[3]);
                        } else {
                            System.out.println(row + ":length:" + strs.length);
                        }
                    } else if (str.contains(incStr)) {
                        if (strs.length >= 5) {
                            if (strs[1].contains("content")) {
                                includeList.add(strs[3]);
                            }
                        } else {
                            System.out.println(row + ":length:" + strs.length);
                        }
                    } else if (str.contains(middleStr)) {
                        // System.out.println(str);
                        if (strs.length == 3) {
                            includeList.add(strs[1]);
                        } else {
                            System.out.println(row + ":length:" + strs.length);
                        }
                    } else if (str.startsWith(endStr)) {
                        mapAll.put(IN_CLUDE_FILES, includeList);
                    }
                }
                br.close();
                fr.close();

                subMap.put(mapAll.get(KEY_NAME), includeList);
                arrayAll.add(mapAll);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changeFramePage(List<Map> commonList, Map smap) {
        for (final Map map : commonList) {
            if (!((String) map.get(FRAME_PAGE)).endsWith(".jsp")) {
                map.put(FRAME_PAGE, smap.get(map.get(FRAME_PAGE)));
            }
        }
    }

    public static void main(String[] args) {

        new FilePath();
    }

}
