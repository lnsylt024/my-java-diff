package com.github.difflib;

import java.util.Arrays;
import java.util.List;

import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;

public class MyDiffUtilsTest {

    public MyDiffUtilsTest() {
        this.chkInsert();
        System.out.println("-----------------");
        this.chkDelete();
        System.out.println("-----------------");
        this.chkChange();
    }

    public void chkInsert() {
        try {
            final Patch<String> patch = DiffUtils.diff(Arrays.asList("hhh"), Arrays.asList("hhh", "jjj", "kkk"));
            System.out.println("insert size:" + patch.getDeltas().size());
            for (final AbstractDelta<String> delta : patch.getDeltas()) {
                System.out.println("source:" + delta.getSource());
                System.out.println("target:" + delta.getTarget());
            }
        } catch (final DiffException e) {
            e.printStackTrace();
        }
    }

    public void chkDelete() {
        try {
            final Patch<String> patch = DiffUtils.diff(Arrays.asList("ddd", "fff", "ggg", "xxx", "yyy", "zzz"),
                    Arrays.asList("ggg", "xxx", "zzz"));
            System.out.println("delete size:" + patch.getDeltas().size());
            for (final AbstractDelta<String> delta : patch.getDeltas()) {
                System.out.println("source:" + delta.getSource());
                System.out.println("target:" + delta.getTarget());
            }
        } catch (final DiffException e) {
            e.printStackTrace();
        }
    }

    public void chkChange() {
        try {
            final List<String> changeTest_from = Arrays.asList("aaa", "bbb", "ccc");
            final List<String> changeTest_to = Arrays.asList("aaa", "zzz", "ccc");
            final Patch<String> patch = DiffUtils.diff(changeTest_from, changeTest_to);

            System.out.println("change size:" + patch.getDeltas().size());
            for (final AbstractDelta<String> delta : patch.getDeltas()) {
                System.out.println("source:" + delta.getSource());
                System.out.println("target:" + delta.getTarget());
            }
        } catch (final DiffException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyDiffUtilsTest();
    }

}
