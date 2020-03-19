package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.test.diff.FileComparator;

import difflib.Chunk;
import difflib.Delta;

public class FileReadTest {

    File sourceFile = null;
    File targetFile = null;
    List<String> sourceFileContentList = null;
    List<String> targetFileContentList = null;
    Map<String, String> map = new HashMap<String, String>();

    final String MAP_KEY_CHANGE_CNT = "map_key_change_cnt";
    final String MAP_KEY_CHANGE_CNT_ROW = "map_key_change_cnt_row";
    final String MAP_KEY_INSERT_CNT = "map_key_insert_cnt";
    final String MAP_KEY_INSERT_CNT_ROW = "map_key_insert_cnt_row";
    final String MAP_KEY_DELETE_CNT = "map_key_delete_cnt";
    final String MAP_KEY_DELETE_CNT_ROW = "map_key_delete_cnt_row";

    FileComparator comparator = null;

    StringBuilder sb = new StringBuilder("");

    int row = 0;

    Map<String, ArrayList<String>> outMap = new HashMap<String, ArrayList<String>>();

    public FileReadTest() {
        map.put(MAP_KEY_CHANGE_CNT, "0");
        map.put(MAP_KEY_CHANGE_CNT_ROW, "0");
        map.put(MAP_KEY_INSERT_CNT, "0");
        map.put(MAP_KEY_INSERT_CNT_ROW, "0");
        map.put(MAP_KEY_DELETE_CNT, "0");
        map.put(MAP_KEY_DELETE_CNT_ROW, "0");

        this.paths("C:/Users/B1361/Desktop/count_source/INES側/csm_new");
        this.printOutMap();
    }

    public void printFliterFile() {
        // final File file = new File("C:/Users/B1361/Desktop/count_source/INES側/path_master.txt");
        final File file = new File("C:/Users/B1361/Desktop/count_source/INES側/path_new.txt");
        File tmpfile = null;
        try {
            final BufferedReader br = new BufferedReader(new FileReader(file));
            String str = "";
            while ((str = br.readLine()) != null) {
                tmpfile = new File(str);
                if (tmpfile.isFile()) {
                    System.out.println(tmpfile.getAbsolutePath());
                }
            }
            br.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void paths(String path) {
        targetFile = new File(path);
        String suffix = "";
        if (targetFile.isDirectory()) {
            final File[] fileList = targetFile.listFiles();
            for (final File f2 : fileList) {
                this.paths(f2.getAbsolutePath());
            }
        } else {
            row++;
            sourceFile = new File(targetFile.getAbsolutePath().replace("csm_new", "csm_master"));
            comparator = new FileComparator(sourceFile, targetFile);
            suffix = targetFile.getName().substring(targetFile.getName().lastIndexOf(".") + 1);

            sb.append(suffix);
            sb.append(" ");
            sb.append(targetFile.getAbsolutePath());
            sb.append(" ");
            sb.append(comparator.originalFileSizeForALL);
            sb.append(" ");
            sb.append(comparator.originalFileSizeForNULL);
            sb.append(" ");
            sb.append(comparator.revisedFileSizeForALL);
            sb.append(" ");
            sb.append(comparator.revisedFileSizeForNULL);
            sb.append(" ");

            if (sourceFile.exists()) {
                this.getChangeContents();
                this.getInsertContents();
                this.getDeleteContents();
            }
            sb.append(map.get(MAP_KEY_CHANGE_CNT));
            sb.append(" ");
            sb.append(map.get(MAP_KEY_CHANGE_CNT_ROW));
            sb.append(" ");
            sb.append(map.get(MAP_KEY_INSERT_CNT));
            sb.append(" ");
            sb.append(map.get(MAP_KEY_INSERT_CNT_ROW));
            sb.append(" ");
            sb.append(map.get(MAP_KEY_DELETE_CNT));
            sb.append(" ");
            sb.append(map.get(MAP_KEY_DELETE_CNT_ROW));
            sb.append(" ");
            // System.out.println(sb.toString());
            this.setOutMap(suffix, sb.toString());
            sb.setLength(0);
        }
    }

    private void setOutMap(String key, String outstr) {
        ArrayList<String> list = null;
        if (outMap.get(key) == null) {
            list = new ArrayList<String>();
        } else {
            list = outMap.get(key);
        }
        list.add(outstr);
        outMap.put(key, list);
    }

    private void printOutMap() {
        int cnt = 0;
        for (final ArrayList<String> suffixList : outMap.values()) {
            for (final String str : suffixList) {
                cnt++;
                System.out.println(cnt + " " + str);
            }
        }
    }

    private void getChangeContents() {
        try {
            final List<Chunk> changesList = comparator.getChunksByType(Delta.TYPE.CHANGE);
            map.put(MAP_KEY_CHANGE_CNT, String.valueOf(changesList.size()));
            int cnt = 0;
            for (int i = 0; i < changesList.size(); i++) {
                cnt = cnt + changesList.get(i).size();
            }
            map.put(MAP_KEY_CHANGE_CNT_ROW, String.valueOf(cnt));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void getInsertContents() {
        try {
            final List<Chunk> insertList = comparator.getChunksByType(Delta.TYPE.INSERT);
            map.put(MAP_KEY_INSERT_CNT, String.valueOf(insertList.size()));
            int cnt = 0;
            for (int i = 0; i < insertList.size(); i++) {
                cnt = cnt + insertList.get(i).size();
            }
            map.put(MAP_KEY_INSERT_CNT_ROW, String.valueOf(cnt));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void getDeleteContents() {
        try {
            final List<Chunk> deleteList = comparator.getChunksByType(Delta.TYPE.DELETE);
            map.put(MAP_KEY_DELETE_CNT, String.valueOf(deleteList.size()));
            int cnt = 0;
            for (int i = 0; i < deleteList.size(); i++) {
                cnt = cnt + deleteList.get(i).size();
            }
            map.put(MAP_KEY_DELETE_CNT_ROW, String.valueOf(cnt));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new FileReadTest();
    }

}
