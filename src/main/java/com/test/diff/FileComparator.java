package com.test.diff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import difflib.Chunk;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

public class FileComparator {

    private final File original;

    private final File revised;

    private List<String> originalFileLines = null;
    private List<String> revisedFileLines = null;

    public int originalFileSizeForALL = 0;
    public int originalFileSizeForNULL = 0;
    public int revisedFileSizeForALL = 0;
    public int revisedFileSizeForNULL = 0;

    public FileComparator(File original, File revised) {
        this.original = original;
        this.revised = revised;
        originalFileLines = fileToLinesForOriginal(original);
        revisedFileLines = fileToLinesForRevised(revised);
    }

    public List<Chunk> getChangesFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.CHANGE);
    }

    public List<Chunk> getInsertsFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.INSERT);
    }

    public List<Chunk> getDeletesFromOriginal() throws IOException {
        return getChunksByType(Delta.TYPE.DELETE);
    }

    public List<Chunk> getChunksByType(Delta.TYPE type) throws IOException {
        final List<Chunk> listOfChanges = new ArrayList<Chunk>();
        final List<Delta> deltas = getDeltas();
        for (final Delta delta : deltas) {
            if (delta.getType() == type) {
                listOfChanges.add(delta.getRevised());
            }
        }
        return listOfChanges;
    }

    public List<Delta> getDeltas() throws IOException {

        final Patch patch = DiffUtils.diff(originalFileLines, revisedFileLines);

        return patch.getDeltas();
    }

    public List<String> fileToLinesForOriginal(File file) {
        final List<String> lines = new ArrayList<String>();
        if (!file.exists()) {
            return lines;
        }
        String line;
        try {
            final BufferedReader in = new BufferedReader(new FileReader(file));

            while ((line = in.readLine()) != null) {
                if (line.trim().length() == 0) {
                    originalFileSizeForNULL++;
                }
                lines.add(line);
            }
            originalFileSizeForALL = lines.size();
            in.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public List<String> fileToLinesForRevised(File file) {
        final List<String> lines = new ArrayList<String>();
        if (!file.exists()) {
            return lines;
        }
        String line;
        try {
            final BufferedReader in = new BufferedReader(new FileReader(file));

            while ((line = in.readLine()) != null) {
                if (line.trim().length() == 0) {
                    revisedFileSizeForNULL++;
                }
                lines.add(line);
            }
            revisedFileSizeForALL = lines.size();
            in.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}