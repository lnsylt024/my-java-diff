package com.test.diff2;

import java.util.List;
import java.util.function.BiPredicate;

import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.algorithm.myers.MyersDiff;
import com.github.difflib.patch.Patch;

public class FileDiff {

    public static <T> Patch<T> diff(List<T> original, List<T> revised, BiPredicate<T, T> equalizer)
            throws DiffException {
        if (equalizer != null) {
            return DiffUtils.diff(original, revised, new MyersDiff<>(equalizer));
        }
        return DiffUtils.diff(original, revised, new MyersDiff<>(equalizer));
    }

    public static void main(String[] args) {

    }

}
