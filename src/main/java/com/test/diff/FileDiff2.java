package com.test.diff;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

public class FileDiff2 {

    public FileDiff2() {

        try {
            final List<String> oldLines = Files.readAllLines(
                    FileSystems.getDefault().getPath("C:/Users/B1361/Desktop/count_source/diff/source.txt"),
                    Charset.defaultCharset());
            final List<String> newLines = Files.readAllLines(
                    FileSystems.getDefault().getPath("C:/Users/B1361/Desktop/count_source/diff/target.txt"),
                    Charset.defaultCharset());
            final Patch patch = DiffUtils.diff(oldLines, newLines);

            for (int i = 0; i < patch.getDeltas().size(); i++) {
                final Delta delta = (Delta) patch.getDeltas().get(i);
                System.out.println(String.format("[変更前(%d)行目]", delta.getOriginal().getPosition() + 1));
                for (final Object line : delta.getOriginal().getLines()) {
                    System.out.println(line);
                }
                System.out.println("　↓");
                System.out.println(String.format("[変更後(%d)行目]", delta.getRevised().getPosition() + 1));
                for (final Object line : delta.getRevised().getLines()) {
                    System.out.println(line);
                }
                System.out.println();
            }
        } catch (final Exception e) {
            e.getStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new FileDiff2();
    }

}