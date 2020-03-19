package com.github.difflib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.ChangeDelta;
import com.github.difflib.patch.Chunk;
import com.github.difflib.patch.DeleteDelta;
import com.github.difflib.patch.InsertDelta;
import com.github.difflib.patch.Patch;

public class DiffUtilsTest {

    @Test
    public void testDiff_Insert() {
        try {
            final Patch<String> patch = DiffUtils.diff(Arrays.asList("hhh"), Arrays.asList("hhh", "jjj", "kkk"));
            assertNotNull(patch);
            assertEquals(1, patch.getDeltas().size());
            final AbstractDelta<String> delta = patch.getDeltas().get(0);
            assertTrue(delta instanceof InsertDelta);
            assertEquals(new Chunk<>(1, Collections.<String> emptyList()), delta.getSource());
            assertEquals(new Chunk<>(1, Arrays.asList("jjj", "kkk")), delta.getTarget());
        } catch (final DiffException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void testDiff_Delete() {
        try {
            final Patch<String> patch = DiffUtils.diff(Arrays.asList("ddd", "fff", "ggg"), Arrays.asList("ggg"));
            assertNotNull(patch);
            assertEquals(1, patch.getDeltas().size());
            final AbstractDelta<String> delta = patch.getDeltas().get(0);
            assertTrue(delta instanceof DeleteDelta);
            assertEquals(new Chunk<>(0, Arrays.asList("ddd", "fff")), delta.getSource());
            assertEquals(new Chunk<>(0, Collections.<String> emptyList()), delta.getTarget());
        } catch (final DiffException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDiff_Change() {
        try {
            final List<String> changeTest_from = Arrays.asList("aaa", "bbb", "ccc");
            final List<String> changeTest_to = Arrays.asList("aaa", "zzz", "ccc");

            final Patch<String> patch = DiffUtils.diff(changeTest_from, changeTest_to);
            assertNotNull(patch);
            assertEquals(1, patch.getDeltas().size());
            final AbstractDelta<String> delta = patch.getDeltas().get(0);
            assertTrue(delta instanceof ChangeDelta);
            assertEquals(new Chunk<>(1, Arrays.asList("bbb")), delta.getSource());
            assertEquals(new Chunk<>(1, Arrays.asList("zzz")), delta.getTarget());
        } catch (final DiffException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDiff_EmptyList() {
        try {
            final Patch<Object> patch = DiffUtils.diff(new ArrayList<>(), new ArrayList<>());
            assertNotNull(patch);
            assertEquals(0, patch.getDeltas().size());
        } catch (final DiffException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void testDiff_EmptyListWithNonEmpty() {
        try {
            final Patch<String> patch = DiffUtils.diff(new ArrayList<String>(), Arrays.asList("aaa"));
            assertNotNull(patch);
            assertEquals(1, patch.getDeltas().size());
            final AbstractDelta<String> delta = patch.getDeltas().get(0);
            assertTrue(delta instanceof InsertDelta);
        } catch (final DiffException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void testDiffInline() {
        try {
            final Patch<String> patch = DiffUtils.diffInline("", "test");
            assertEquals(1, patch.getDeltas().size());
            assertTrue(patch.getDeltas().get(0) instanceof InsertDelta);
            assertEquals(0, patch.getDeltas().get(0).getSource().getPosition());
            assertEquals(0, patch.getDeltas().get(0).getSource().getLines().size());
            assertEquals("test", patch.getDeltas().get(0).getTarget().getLines().get(0));
        } catch (final DiffException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void testDiffInline2() {
        try {
            final Patch<String> patch = DiffUtils.diffInline("es", "fest");
            assertEquals(2, patch.getDeltas().size());
            assertTrue(patch.getDeltas().get(0) instanceof InsertDelta);
            assertEquals(0, patch.getDeltas().get(0).getSource().getPosition());
            assertEquals(2, patch.getDeltas().get(1).getSource().getPosition());
            assertEquals(0, patch.getDeltas().get(0).getSource().getLines().size());
            assertEquals(0, patch.getDeltas().get(1).getSource().getLines().size());
            assertEquals("f", patch.getDeltas().get(0).getTarget().getLines().get(0));
            assertEquals("t", patch.getDeltas().get(1).getTarget().getLines().get(0));
        } catch (final DiffException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void testDiffIntegerList() {
        try {
            final List<Integer> original = Arrays.asList(1, 2, 3, 4, 5);
            final List<Integer> revised = Arrays.asList(2, 3, 4, 6);

            final Patch<Integer> patch = DiffUtils.diff(original, revised);

            for (final AbstractDelta delta : patch.getDeltas()) {
                System.out.println(delta);
            }

            assertEquals(2, patch.getDeltas().size());
            assertEquals("[DeleteDelta, position: 0, lines: [1]]", patch.getDeltas().get(0).toString());
            assertEquals("[ChangeDelta, position: 4, lines: [5] to [6]]", patch.getDeltas().get(1).toString());
        } catch (final DiffException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void testDiffMissesChangeForkDnaumenkoIssue31() {
        try {
            final List<String> original = Arrays.asList("line1", "line2", "line3");
            final List<String> revised = Arrays.asList("line1", "line2-2", "line4");

            final Patch<String> patch = DiffUtils.diff(original, revised);
            assertEquals(1, patch.getDeltas().size());
            assertEquals("[ChangeDelta, position: 1, lines: [line2, line3] to [line2-2, line4]]",
                    patch.getDeltas().get(0).toString());
        } catch (final DiffException e) {

            e.printStackTrace();
        }
    }

    // /**
    // * To test this, the greedy meyer algorithm is not suitable.
    // */
    // @Test
    // @Disabled
    // public void testPossibleDiffHangOnLargeDatasetDnaumenkoIssue26() throws IOException {
    // final ZipFile zip = new ZipFile(TestConstants.MOCK_FOLDER + "/large_dataset1.zip");
    //
    // final Patch<Object> patch = DiffUtils.diff(
    // readStringListFromInputStream(zip.getInputStream(zip.getEntry("ta"))),
    // readStringListFromInputStream(zip.getInputStream(zip.getEntry("tb"))));
    //
    // assertEquals(1, patch.getDeltas().size());
    // }
    //
    // public static List<Object> readStringListFromInputStream(InputStream is) throws IOException {
    // try (BufferedReader reader = new BufferedReader(
    // new InputStreamReader(is, Charset.forName(StandardCharsets.UTF_8.name())))) {
    //
    // return reader.lines().collect(toList());
    // }
    // }

    @Test
    public void testDiffMyersExample1() {
        try {
            final Patch<String> patch = DiffUtils.diff(Arrays.asList("A", "B", "C", "A", "B", "B", "A"),
                    Arrays.asList("C", "B", "A", "B", "A", "C"));
            assertNotNull(patch);
            assertEquals(4, patch.getDeltas().size());
            assertEquals(
                    "Patch{deltas=[[DeleteDelta, position: 0, lines: [A, B]], [InsertDelta, position: 3, lines: [B]], [DeleteDelta, position: 5, lines: [B]], [InsertDelta, position: 7, lines: [C]]]}",
                    patch.toString());
        } catch (final DiffException e) {

            e.printStackTrace();
        }
    }
}
