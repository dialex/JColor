package com.diogonunes.jcolor.tests.unit;

import org.junit.jupiter.api.Test;

import static com.diogonunes.jcolor.tests.unit.DataGenerator.NEWLINE;
import static com.diogonunes.jcolor.tests.unit.DataGenerator.countLines;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestDataGenerator {

    @Test
    public void CountLines_NoText() {
        String text = "";
        assertThat(countLines(text), equalTo(0));
    }

    @Test
    public void CountLines_Inline() {
        String text = "A sentence without a newline";
        assertThat(countLines(text), equalTo(0));
    }

    @Test
    public void CountLines_OneLine() {
        String text = "A single line" + NEWLINE;
        assertThat(countLines(text), equalTo(1));
    }

    @Test
    public void CountLines_MultipleEmptyLines() {
        String text = "A single line" + NEWLINE + NEWLINE + NEWLINE;
        assertThat(countLines(text), equalTo(3));
    }

    @Test
    public void CountLines_MultipleMiddleLines() {
        String text = "Preface" + NEWLINE + NEWLINE + "Body" + NEWLINE + "Ending";
        assertThat(countLines(text), equalTo(3));
    }
}
