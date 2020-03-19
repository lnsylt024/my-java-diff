package com.test.diff;

import java.io.File;
import java.io.IOException;
import java.util.List;

import difflib.Chunk;

public class FileDiff {

    // source
    private final File original = new File("C:/Users/B1361/Desktop/count_source/diff/originalFile.txt");

    // target
    private final File revised = new File("C:/Users/B1361/Desktop/count_source/diff/revisedFile.txt");

    private FileComparator comparator = null;

    public FileDiff() {

        // source, target
        comparator = new FileComparator(original, revised);
        // Change
        this.getChangeContents();

        // Insert
        this.getInsertContents();

        // Delete
        this.getDeleteContents();
    }

    /**
     * 
     */
    private void getChangeContents() {
        try {
            final List<Chunk> changesList = comparator.getChangesFromOriginal();
            System.out.println("change size:" + changesList.size());
            for (int i = 0; i < changesList.size(); i++) {
                final Chunk chk = changesList.get(i);
                System.out.println("----------change【" + i + "】---------");
                System.out.println("【position】" + (chk.getPosition() + 1));
                System.out.println("【size】" + chk.size());
                for (int j = 0; j < chk.getLines().size(); j++) {
                    System.out.println("【changecontent】" + chk.getLines().get(j).toString());
                }

            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     */
    private void getInsertContents() {
        try {
            final List<Chunk> insertList = comparator.getInsertsFromOriginal();
            System.out.println("insert size:" + insertList.size());
            for (int i = 0; i < insertList.size(); i++) {
                final Chunk chk = insertList.get(i);
                System.out.println("+++++++++++insert【" + i + "】++++++++++++");
                System.out.println("【position】" + (chk.getPosition() + 1));
                System.out.println("【size】" + chk.size());
                for (int j = 0; j < chk.getLines().size(); j++) {
                    System.out.println("【insertcontent】" + chk.getLines().get(j).toString());
                }

            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     */
    private void getDeleteContents() {
        try {
            final List<Chunk> deleteList = comparator.getDeletesFromOriginal();
            System.out.println("delete size:" + deleteList.size());
            for (int i = 0; i < deleteList.size(); i++) {
                final Chunk chk = deleteList.get(i);
                System.out.println(">>>>>>>>>>>>delete【" + i + "】<<<<<<<<<<<");
                System.out.println("【position】" + (chk.getPosition() + 1));
                System.out.println("【size】" + chk.size());
            }

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FileDiff();
    }

}
