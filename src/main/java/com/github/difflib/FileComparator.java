package com.github.difflib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.DeltaType;
import com.github.difflib.patch.Patch;

public class FileComparator {

    private final File original;

    private final File revised;

    public FileComparator(File original, File revised) {
        this.original = original;
        this.revised = revised;
    }

    public List<Chunk> getChangesFromOriginal() throws Exception {
        return getChunksByType(DeltaType.CHANGE);
    }

    public List<Chunk> getInsertsFromOriginal() throws Exception {
        return getChunksByType(DeltaType.INSERT);
    }

    public List<Chunk> getDeletesFromOriginal() throws Exception {
        return getChunksByType(DeltaType.DELETE);
    }

    private List<Chunk> getChunksByType(DeltaType type) throws Exception {
        final List<Chunk> listOfChanges = new ArrayList<Chunk>();
        final List<AbstractDelta> deltas = getDeltas();
        for (final AbstractDelta delta : deltas) {
            if (delta.getType() == type) {
                listOfChanges.add(delta.getTarget());
            }
        }
        return listOfChanges;
    }

    private List<AbstractDelta> getDeltas() throws Exception {

        final List<String> originalFileLines = fileToLines(original);
        final List<String> revisedFileLines = fileToLines(revised);

        final Patch patch = DiffUtils.diff(originalFileLines, revisedFileLines);

        return patch.getDeltas();
    }

    private List<String> fileToLines(File file) throws IOException {
        final List<String> lines = new ArrayList<String>();
        String line;
        final BufferedReader in = new BufferedReader(new FileReader(file));
        while ((line = in.readLine()) != null) {
            // System.out.println("【" + file.getName() + "】" + line);
            lines.add(line);
        }

        return lines;
    }

}